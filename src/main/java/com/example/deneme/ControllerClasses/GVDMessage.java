package com.example.deneme.ControllerClasses;

import com.example.deneme.GVDSystemParser;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GVDMessage {

    public static final int MAX_BUFFER_SIZE = 7000;
    public static final int EXPECTED_PARTS = 3;
    private volatile boolean GV_ID_SET_FLAG = false;

    // 🎯 2 BOYUTLU MAP: HatNo -> (AMTB_ID -> State)
    private static final Map<Integer, Map<Integer, PortMessageState>> stateMap = new ConcurrentHashMap<>();

    public boolean getGVDIDsetflag() {
        return GV_ID_SET_FLAG;
    }

    public void setGVDIDsetflag(boolean flag) {
        GV_ID_SET_FLAG = flag;
    }

    // 🎯 LAZY LOADING ile state al/oluştur
    public static PortMessageState getOrCreateState(int hatNo, int amtbId) {
        return stateMap
                .computeIfAbsent(hatNo, k -> new ConcurrentHashMap<>())
                .computeIfAbsent(amtbId, k -> new PortMessageState());
    }

    // 🎯 State'i doğrudan alma (sadece okuma için)
    public static PortMessageState getState(int hatNo, int amtbId) {
        Map<Integer, PortMessageState> hatStates = stateMap.get(hatNo);
        return (hatStates != null) ? hatStates.get(amtbId) : null;
    }

    // 🎯 State var mı kontrolü
    public static boolean hasState(int hatNo, int amtbId) {
        Map<Integer, PortMessageState> hatStates = stateMap.get(hatNo);
        return hatStates != null && hatStates.containsKey(amtbId);
    }

    // 🎯 Temizleme metodları
    public static void cleanupHat(int hatNo) {
        Map<Integer, PortMessageState> hatStates = stateMap.remove(hatNo);
        if (hatStates != null) {
            hatStates.clear();
            System.out.printf("🧹 Hat %d bufferları temizlendi%n", hatNo);
        }
    }

    public static void cleanupAmtb(int hatNo, int amtbId) {
        Map<Integer, PortMessageState> hatStates = stateMap.get(hatNo);
        if (hatStates != null) {
            hatStates.remove(amtbId);
            System.out.printf("🧹 Hat %d - AMTB %d buffer temizlendi%n", hatNo, amtbId);
        }
    }

    // 🎯 Merkezi processMessage metodu
    public static void processMessage(int hatNo, int amtbId, byte[] packet,
                                      int GVD_SIZE, int MAG_SIZE, int AMMO_SIZE) {
        PortMessageState state = getOrCreateState(hatNo, amtbId);

        int headerSize = 10;
        int checksumSize = 1;
        int payloadLength = packet.length - headerSize - checksumSize;

        System.out.printf("📥 Hat %d - AMTB %d: Payload %d bytes%n",
                hatNo, amtbId, payloadLength);

        state.addPart(packet, headerSize, payloadLength);

        System.out.printf("🔄 Hat %d - AMTB %d: parts=%d/%d, completed=%d%n",
                hatNo, amtbId, state.getPartCount(), EXPECTED_PARTS,
                state.getCompletedMessagesCount());

        if (state.getCompletedMessagesCount() > 0) {
            System.out.printf("🎯 Parsing completed messages for Hat %d - AMTB %d...%n",
                    hatNo, amtbId);
            parseCompletedMessages(state, GVD_SIZE, MAG_SIZE, AMMO_SIZE);
        }
    }

    public static class PortMessageState {
        private final List<byte[]> parts = new ArrayList<>();
        private int totalBytes = 0;
        private final List<byte[]> completedMessages = new CopyOnWriteArrayList<>();

        public synchronized void addPart(byte[] array, int offset, int length) {
            // Giriş validasyonu
            if (array == null || length <= 0 || offset < 0 || offset >= array.length) {
                System.err.println("Invalid parameters in addPart");
                return;
            }

            int actualLength = Math.min(length, array.length - offset);
            if (actualLength <= 0) {
                return;
            }

            // Buffer overflow kontrolü
            if (totalBytes + actualLength > MAX_BUFFER_SIZE) {
                System.err.println("Buffer overflow detected, resetting...");
                reset();
                return;
            }

            byte[] part = Arrays.copyOfRange(array, offset, offset + actualLength);
            parts.add(part);
            totalBytes += actualLength;

            System.out.printf("Part %d/%d added. Current total: %d bytes%n",
                    parts.size(), EXPECTED_PARTS, totalBytes);

            // 3 parça tamamlandığında mesajı işle
            if (parts.size() >= EXPECTED_PARTS) {
                processCompletedMessage();
            }
        }

        private synchronized void processCompletedMessage() {
            try {
                byte[] fullMessage = new byte[totalBytes];
                int currentPos = 0;

                for (byte[] part : parts) {
                    System.arraycopy(part, 0, fullMessage, currentPos, part.length);
                    currentPos += part.length;
                }

                completedMessages.add(fullMessage);
                System.out.printf("✅ Full message stored. Total completed: %d%n",
                        completedMessages.size());

                // DEBUG: Tam mesajın başlangıcı
                System.out.print("Full message hex (first 32): ");
                for (int i = 0; i < Math.min(32, fullMessage.length); i++) {
                    System.out.printf("%02X ", fullMessage[i]);
                }
                System.out.println();

            } catch (Exception e) {
                System.err.println("❌ Error combining parts: " + e.getMessage());
                e.printStackTrace();
            } finally {
                reset();
            }
        }

        public synchronized boolean isComplete() {
            return parts.size() >= EXPECTED_PARTS;
        }

        public synchronized byte[] getFullMessage() {
            if (parts.isEmpty()) return new byte[0];

            byte[] result = new byte[totalBytes];
            int currentPos = 0;
            for (byte[] part : parts) {
                System.arraycopy(part, 0, result, currentPos, part.length);
                currentPos += part.length;
            }
            return result;
        }

        public synchronized void reset() {
            parts.clear();
            totalBytes = 0;
            System.out.println("🔄 Buffer reset");
        }

        public List<byte[]> getCompletedMessages() {
            return new ArrayList<>(completedMessages);
        }

        public synchronized void clearCompletedMessages() {
            completedMessages.clear();
            System.out.println("🧹 Completed messages cleared");
        }

        public synchronized int getPartCount() {
            return parts.size();
        }

        public synchronized int getBufferSize() {
            return totalBytes;
        }

        public int getCompletedMessagesCount() {
            return completedMessages.size();
        }
    }

    public static void parseCompletedMessages(PortMessageState state, int GVD_SIZE, int MAG_SIZE, int AMMO_SIZE) {
        List<byte[]> messages = state.getCompletedMessages();

        if (messages.isEmpty()) {
            System.out.println("ℹ️ No completed messages to parse");
            return;
        }

        System.out.printf("🔨 Starting to parse %d completed messages%n", messages.size());

        for (int i = 0; i < messages.size(); i++) {
            byte[] fullMsg = messages.get(i);
            System.out.printf("📦 Processing message %d/%d: %d bytes%n",
                    i + 1, messages.size(), fullMsg.length);

            try {
                // ESNEK BOYUT HESAPLAMA
                int availableGvdSize = Math.min(GVD_SIZE, fullMsg.length);
                int availableMagSize = Math.min(MAG_SIZE, Math.max(0, fullMsg.length - GVD_SIZE));
                int availableAmmoSize = Math.min(AMMO_SIZE, Math.max(0, fullMsg.length - GVD_SIZE - MAG_SIZE));

                System.out.printf("📐 Available sizes - GVD: %d, Magazine: %d, Ammo: %d%n",
                        availableGvdSize, availableMagSize, availableAmmoSize);

                if (availableGvdSize < 100) {
                    System.out.println("⚠️ GVD data too small, skipping");
                    continue;
                }

                byte[] gvdData = Arrays.copyOfRange(fullMsg, 0, availableGvdSize);
                byte[] magazineData = availableMagSize > 0 ?
                        Arrays.copyOfRange(fullMsg, GVD_SIZE, GVD_SIZE + availableMagSize) : new byte[0];
                byte[] ammunitionData = availableAmmoSize > 0 ?
                        Arrays.copyOfRange(fullMsg, GVD_SIZE + MAG_SIZE,
                                GVD_SIZE + MAG_SIZE + availableAmmoSize) : new byte[0];

                System.out.printf("📊 Parsing - GVD: %d, Magazine: %d, Ammo: %d bytes%n",
                        gvdData.length, magazineData.length, ammunitionData.length);

                // Parser'ı çağır
                GVDSystemParser.CompleteGVDSystem system =
                        GVDSystemParser.CompleteGVDSystem.parseCompleteData(gvdData, magazineData, ammunitionData);

                if (system != null) {
                    System.out.println("✅ GVD Parse Successful!");
                    // Kısa özet göster
                    if (system.getGvdData() != null && system.getGvdData().getGvdMetaData() != null) {
                        System.out.println(system);
                    }
                } else {
                    System.out.println("❌ GVD Parse returned null");
                }

            } catch (Exception e) {
                System.err.println("💥 Parse error in message " + (i + 1) + ": " + e.getMessage());
                e.printStackTrace();
            }
        }

        state.clearCompletedMessages();
    }

    // 🎯 Sistem durumunu göster
    public static void printSystemStatus() {
        System.out.println("=== GVD Message System Status ===");
        for (Map.Entry<Integer, Map<Integer, PortMessageState>> hatEntry : stateMap.entrySet()) {
            int hatNo = hatEntry.getKey();
            Map<Integer, PortMessageState> hatStates = hatEntry.getValue();
            System.out.printf("Hat %d: %d AMTB aktif%n", hatNo, hatStates.size());

            for (Map.Entry<Integer, PortMessageState> amtbEntry : hatStates.entrySet()) {
                int amtbId = amtbEntry.getKey();
                PortMessageState state = amtbEntry.getValue();
                System.out.printf("  - AMTB %d: parts=%d/%d, completed=%d%n",
                        amtbId, state.getPartCount(), EXPECTED_PARTS, state.getCompletedMessagesCount());
            }
        }
        System.out.println("=================================");
    }
}