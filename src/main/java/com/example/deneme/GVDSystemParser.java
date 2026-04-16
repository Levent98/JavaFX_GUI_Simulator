package com.example.deneme;


import javafx.application.Application;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class GVDSystemParser {

    // Ana GVD Sistemi - Tüm veri yapılarını birleştirir
    public static class CompleteGVDSystem {
        private GVDData gvdData;           // 4096 bytes
        private MagazineData magazineData; // 512 bytes
        private AmmunitionData ammunitionData; // 2048 bytes

        public CompleteGVDSystem() {
            this.gvdData = new GVDData();
            this.magazineData = new MagazineData();
            this.ammunitionData = new AmmunitionData();
        }

        public static CompleteGVDSystem parseCompleteData(byte[] gvdData, byte[] magazineData, byte[] ammunitionData) {
            CompleteGVDSystem system = new CompleteGVDSystem();

            if (gvdData != null) {
                system.gvdData = GVDData.parse(gvdData);
            }

            if (magazineData != null) {
                system.magazineData = MagazineData.parse(magazineData);
            }

            if (ammunitionData != null) {
                system.ammunitionData = AmmunitionData.parse(ammunitionData);
            }

            return system;
        }

        // Getter and Setter methods
        public GVDData getGvdData() {
            return gvdData;
        }

        public void setGvdData(GVDData gvdData) {
            this.gvdData = gvdData;
        }

        public MagazineData getMagazineData() {
            return magazineData;
        }

        public void setMagazineData(MagazineData magazineData) {
            this.magazineData = magazineData;
        }

        public AmmunitionData getAmmunitionData() {
            return ammunitionData;
        }

        public void setAmmunitionData(AmmunitionData ammunitionData) {
            this.ammunitionData = ammunitionData;
        }

        @Override
        public String toString() {
            return "CompleteGVDSystem{\n" +
                    "gvdData=" + gvdData + "\n" +
                    ", magazineData=" + magazineData + "\n" +
                    ", ammunitionData=" + ammunitionData + "\n" +
                    '}';
        }
    }

    // 1. GVD Ana Veri Yapısı (4096 Bytes)
    public static class GVDData {
        private GVDMetaData gvdMetaData;
        private ProgramData programData;
        private AMTBData amtbData;
        private LauncherPriorities launcherPriorities;
        private SameProgramStartRule sameProgramStartRule;
        private ZeroizeInfo zeroizeInfo;
        private ProgramNumbers programNumbers;
        private LineNumber lineNumber;
        private Reserved reserved;
        private Checksum checksum;

        public GVDData() {
            this.gvdMetaData = new GVDMetaData();
            this.programData = new ProgramData();
            this.amtbData = new AMTBData();
            this.launcherPriorities = new LauncherPriorities();
            this.sameProgramStartRule = new SameProgramStartRule();
            this.zeroizeInfo = new ZeroizeInfo();
            this.programNumbers = new ProgramNumbers();
            this.lineNumber = new LineNumber();
            this.reserved = new Reserved();
            this.checksum = new Checksum();
        }

        public static GVDData parse(byte[] data) {
            if (data.length != 4096) {
                throw new IllegalArgumentException("GVDData must be exactly 4096 bytes");
            }

            GVDData gvdData = new GVDData();
            ByteBuffer buffer = ByteBuffer.wrap(data);
            buffer.order(ByteOrder.LITTLE_ENDIAN);

            // GVD Meta Data (128 bytes)
            byte[] gvdMetaBytes = new byte[128];
            buffer.get(gvdMetaBytes);
            gvdData.gvdMetaData = GVDMetaData.parse(gvdMetaBytes);

            // Program Data (3554 bytes)
            gvdData.programData = ProgramData.parse(buffer);

            // AMTB Data (200 bytes)
            gvdData.amtbData = AMTBData.parse(buffer);

            // Launcher Priorities (18 bytes)
            gvdData.launcherPriorities = LauncherPriorities.parse(buffer);

            // Same Program Start Rule (1 byte)
            gvdData.sameProgramStartRule = new SameProgramStartRule(buffer.get());

            // Zeroize Info (1 byte)
            gvdData.zeroizeInfo = new ZeroizeInfo(buffer.get());

            // Program Numbers (12 bytes)
            gvdData.programNumbers = ProgramNumbers.parse(buffer);

            // Line Number (1 byte)
            gvdData.lineNumber = new LineNumber(buffer.get());

            // Reserved (180 bytes)
            byte[] reservedBytes = new byte[180];
            buffer.get(reservedBytes);
            gvdData.reserved = new Reserved(reservedBytes);

            // Checksum (1 byte)
            gvdData.checksum = new Checksum(buffer.get());

            return gvdData;
        }

        // Getter and Setter methods
        public GVDMetaData getGvdMetaData() {
            return gvdMetaData;
        }

        public void setGvdMetaData(GVDMetaData gvdMetaData) {
            this.gvdMetaData = gvdMetaData;
        }

        public ProgramData getProgramData() {
            return programData;
        }

        public void setProgramData(ProgramData programData) {
            this.programData = programData;
        }

        public AMTBData getAmtbData() {
            return amtbData;
        }

        public void setAmtbData(AMTBData amtbData) {
            this.amtbData = amtbData;
        }

        public LauncherPriorities getLauncherPriorities() {
            return launcherPriorities;
        }

        public void setLauncherPriorities(LauncherPriorities launcherPriorities) {
            this.launcherPriorities = launcherPriorities;
        }

        public SameProgramStartRule getSameProgramStartRule() {
            return sameProgramStartRule;
        }

        public void setSameProgramStartRule(SameProgramStartRule sameProgramStartRule) {
            this.sameProgramStartRule = sameProgramStartRule;
        }

        public ZeroizeInfo getZeroizeInfo() {
            return zeroizeInfo;
        }

        public void setZeroizeInfo(ZeroizeInfo zeroizeInfo) {
            this.zeroizeInfo = zeroizeInfo;
        }

        public ProgramNumbers getProgramNumbers() {
            return programNumbers;
        }

        public void setProgramNumbers(ProgramNumbers programNumbers) {
            this.programNumbers = programNumbers;
        }

        public LineNumber getLineNumber() {
            return lineNumber;
        }

        public void setLineNumber(LineNumber lineNumber) {
            this.lineNumber = lineNumber;
        }

        public Reserved getReserved() {
            return reserved;
        }

        public void setReserved(Reserved reserved) {
            this.reserved = reserved;
        }

        public Checksum getChecksum() {
            return checksum;
        }

        public void setChecksum(Checksum checksum) {
            this.checksum = checksum;
        }

        @Override
        public String toString() {
            return "GVDData{\n" +
                    "  gvdMetaData=" + gvdMetaData + "\n" +
                    "  programData=" + programData + "\n" +
                    "  amtbData=" + amtbData + "\n" +
                    "  launcherPriorities=" + launcherPriorities + "\n" +
                    "  sameProgramStartRule=" + sameProgramStartRule + "\n" +
                    "  zeroizeInfo=" + zeroizeInfo + "\n" +
                    "  programNumbers=" + programNumbers + "\n" +
                    "  lineNumber=" + lineNumber + "\n" +
                    "  reserved=" + reserved + "\n" +
                    "  checksum=" + checksum + "\n" +
                    '}';
        }
    }

    // 1.1 GVD Meta Veri Tablosu (128 Byte)
    public static class GVDMetaData {
        public static final short GVD_SIGNATURE = 0x4843; // Big Endian: 0x48 0x43

        private short signature;           // 2 bytes - artık short
        private short majorVersion;         // 1 byte
        private short minorVersion;         // 1 byte
        private int buildNumber;            // 2 bytes
        private int uniqueHashCode;         // 4 bytes
        private String name;                // 12 bytes
        private byte[] reserved1;           // 4 bytes
        private int gvdId;                  // 2 bytes
        private int interpreterVersion;     // 2 bytes
        private String lastUpdateDate;      // 19 bytes
        private String username;            // 8 bytes
        private short hiVersion;            // 1 byte
        private short loVersion;            // 1 byte
        private short revisionCount;        // 1 byte
        private byte[] reserved2;           // 67 bytes
        private byte checksum;              // 1 byte

        public GVDMetaData() {
            this.reserved1 = new byte[4];
            this.reserved2 = new byte[67];
        }

        public static GVDMetaData parse(byte[] data) {
            if (data.length != 128) {
                throw new IllegalArgumentException("GVDMetaData must be exactly 128 bytes");
            }

            GVDMetaData metaData = new GVDMetaData();
            ByteBuffer buffer = ByteBuffer.wrap(data);
            buffer.order(ByteOrder.LITTLE_ENDIAN);

            // GVD İmzası (2 bytes) - getShort() ile okuyoruz
            metaData.signature = buffer.getShort();

            // GVDHY Versiyonu (4 bytes)
            metaData.majorVersion = (short) (buffer.get() & 0xFF);
            metaData.minorVersion = (short) (buffer.get() & 0xFF);
            metaData.buildNumber = buffer.getShort() & 0xFFFF;

            // GVD Biricik Hash Kodu (4 bytes)
            metaData.uniqueHashCode = buffer.getInt();

            // GVD Adı (12 bytes)
            byte[] nameBytes = new byte[12];
            buffer.get(nameBytes);
            metaData.name = new String(nameBytes, StandardCharsets.US_ASCII).trim();

            // Ayrılmış (4 bytes)
            buffer.get(metaData.reserved1);

            // GVD ID (2 bytes)
            metaData.gvdId = buffer.getShort() & 0xFFFF;

            // GVD Anlamlandırıcı Versiyonu (2 bytes)
            metaData.interpreterVersion = buffer.getShort() & 0xFFFF;

            // GVD Üretim Tarihi (19 bytes)
            byte[] dateBytes = new byte[19];
            buffer.get(dateBytes);
            metaData.lastUpdateDate = new String(dateBytes, StandardCharsets.US_ASCII).trim();

            // Kullanıcı Adı (8 bytes)
            byte[] userBytes = new byte[8];
            buffer.get(userBytes);
            metaData.username = new String(userBytes, StandardCharsets.US_ASCII).trim();

            // GVD Versiyonu (2 bytes)
            metaData.hiVersion = (short) (buffer.get() & 0xFF);
            metaData.loVersion = (short) (buffer.get() & 0xFF);

            // Düzeltme Sayısı (1 byte)
            metaData.revisionCount = (short) (buffer.get() & 0xFF);

            // Ayrılmış (67 bytes)
            buffer.get(metaData.reserved2);

            // Sağlama (1 byte)
            metaData.checksum = buffer.get();

            return metaData;
        }

        public boolean isSignatureValid() {
            return signature == GVD_SIGNATURE;
        }

        public String getFormattedVersion() {
            return String.format("%d.%d.%d", majorVersion, minorVersion, buildNumber);
        }

        public String getFormattedGVDVersion() {
            return String.format("%d.%d", hiVersion, loVersion);
        }

        public Date getLastUpdateDateAsDate() {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
                return dateFormat.parse(lastUpdateDate);
            } catch (Exception e) {
                return null;
            }
        }

        // Getter and Setter methods
        public short getSignature() {
            return signature;
        }

        public void setSignature(short signature) {
            this.signature = signature;
        }

        public short getMajorVersion() {
            return majorVersion;
        }

        public void setMajorVersion(short majorVersion) {
            this.majorVersion = majorVersion;
        }

        public short getMinorVersion() {
            return minorVersion;
        }

        public void setMinorVersion(short minorVersion) {
            this.minorVersion = minorVersion;
        }

        public int getBuildNumber() {
            return buildNumber;
        }

        public void setBuildNumber(int buildNumber) {
            this.buildNumber = buildNumber;
        }

        public int getUniqueHashCode() {
            return uniqueHashCode;
        }

        public void setUniqueHashCode(int uniqueHashCode) {
            this.uniqueHashCode = uniqueHashCode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            if (name != null && name.getBytes(StandardCharsets.US_ASCII).length > 12) {
                throw new IllegalArgumentException("Name must be max 12 bytes in ASCII");
            }
            this.name = name;
        }

        public byte[] getReserved1() {
            return reserved1.clone();
        }

        public void setReserved1(byte[] reserved1) {
            if (reserved1.length != 4) throw new IllegalArgumentException("Reserved1 must be exactly 4 bytes");
            this.reserved1 = reserved1.clone();
        }

        public int getGvdId() {
            return gvdId;
        }

        public void setGvdId(int gvdId) {
            this.gvdId = gvdId;
        }

        public int getInterpreterVersion() {
            return interpreterVersion;
        }

        public void setInterpreterVersion(int interpreterVersion) {
            this.interpreterVersion = interpreterVersion;
        }

        public String getLastUpdateDate() {
            return lastUpdateDate;
        }

        public void setLastUpdateDate(String lastUpdateDate) {
            if (lastUpdateDate != null && lastUpdateDate.getBytes(StandardCharsets.US_ASCII).length > 19) {
                throw new IllegalArgumentException("Last update date must be max 19 bytes in ASCII");
            }
            this.lastUpdateDate = lastUpdateDate;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            if (username != null && username.getBytes(StandardCharsets.US_ASCII).length > 8) {
                throw new IllegalArgumentException("Username must be max 8 bytes in ASCII");
            }
            this.username = username;
        }

        public short getHiVersion() {
            return hiVersion;
        }

        public void setHiVersion(short hiVersion) {
            if (hiVersion < 0 || hiVersion > 99) throw new IllegalArgumentException("Hi version must be between 0-99");
            this.hiVersion = hiVersion;
        }

        public short getLoVersion() {
            return loVersion;
        }

        public void setLoVersion(short loVersion) {
            if (loVersion < 0 || loVersion > 99) throw new IllegalArgumentException("Lo version must be between 0-99");
            this.loVersion = loVersion;
        }

        public short getRevisionCount() {
            return revisionCount;
        }

        public void setRevisionCount(short revisionCount) {
            if (revisionCount < 0 || revisionCount > 5)
                throw new IllegalArgumentException("Revision count must be between 0-5");
            this.revisionCount = revisionCount;
        }

        public byte[] getReserved2() {
            return reserved2.clone();
        }

        public void setReserved2(byte[] reserved2) {
            if (reserved2.length != 67) throw new IllegalArgumentException("Reserved2 must be exactly 67 bytes");
            this.reserved2 = reserved2.clone();
        }

        public byte getChecksum() {
            return checksum;
        }

        public void setChecksum(byte checksum) {
            this.checksum = checksum;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("----------GVD METAVERİ----------\n");


            sb.append("GVD İmzası: [").append(signature>> 8 & 0xFF).append(signature & 0xFF).append("] \n");

            // GVDHY Versiyonu
            sb.append("GVDHY Versiyonu Major: [").append(majorVersion).append("] Minor: [").append(minorVersion)
                    .append("] Build: [").append(buildNumber).append("] \n");

            // GVD Hash Kodu
            sb.append("GVD Hash Kodu: [").append(uniqueHashCode).append("] \n");

            // GVD Adı
            sb.append("GVD Adı: [").append(name).append("] \n");

            // GVD Hafıza Haritası Versiyonu (GVD ID)
            sb.append("GVD Hafıza Haritası Versiyonu: [").append(gvdId).append("] \n");

            // GVD Anlamlandırıcı Versiyonu
            sb.append("GVD Anlamlandırıcı Versiyonu: [").append(interpreterVersion).append("] \n");

            // GVD Üretim Tarihi
            sb.append("GVD Üretim Tarihi: [").append(lastUpdateDate).append("] \n");

            // Kullanıcı Adı
            sb.append("Kullanıcı Adı: [").append(username).append("] \n");

            // GVD Versiyonu
            sb.append("GVD Versiyonu High: [").append(hiVersion).append("] Low: [").append(loVersion).append("] \n");

            // Düzeltme Sayısı
            sb.append("Düzeltme Sayısı: [").append(revisionCount).append("]");

            return sb.toString();
        }
    }

    // 1.2 Program Verileri

    public static class ProgramData {
        private byte[] bypassMaster;   // 379 bytes - Sadece Tip2 program eklenebilir
        private byte[] rwrProgram1;    // 635 bytes - 3 Tip program da eklenebilir
        private byte[] mwsProgram1;    // 635 bytes - 3 Tip program da eklenebilir
        private byte[] mwsProgram2;    // 635 bytes
        private byte[] mwsProgram3;    // 635 bytes
        private byte[] mwsProgram4;    // 635 bytes

        // Parse edilmiş programlar - her alan için ayrı
        private Object bypassMasterProgram;
        private Object rwrProgram1Program;
        private Object mwsProgram1Program;
        private Object mwsProgram2Program;
        private Object mwsProgram3Program;
        private Object mwsProgram4Program;

        public ProgramData() {
            this.bypassMaster = new byte[379];
            this.rwrProgram1 = new byte[635];
            this.mwsProgram1 = new byte[635];
            this.mwsProgram2 = new byte[635];
            this.mwsProgram3 = new byte[635];
            this.mwsProgram4 = new byte[635];
        }

        public static ProgramData parse(ByteBuffer buffer) {
            ProgramData programData = new ProgramData();

            buffer.order(ByteOrder.LITTLE_ENDIAN);

            // Byte array'leri okuma
            programData.bypassMaster = new byte[379];
            buffer.get(programData.bypassMaster);

            programData.rwrProgram1 = new byte[635];
            buffer.get(programData.rwrProgram1);

            programData.mwsProgram1 = new byte[635];
            buffer.get(programData.mwsProgram1);

            programData.mwsProgram2 = new byte[635];
            buffer.get(programData.mwsProgram2);

            programData.mwsProgram3 = new byte[635];
            buffer.get(programData.mwsProgram3);

            programData.mwsProgram4 = new byte[635];
            buffer.get(programData.mwsProgram4);

            // Byte array'leri parse et
            programData.parseProgramTypes();

            return programData;
        }

        private void parseProgramTypes() {
            // Bypass Master - Sadece Tip2 (379 bytes)
            this.bypassMasterProgram = parseProgramFromBytes(bypassMaster, 379, "Bypass Master");

            // RWR Program 1 - 3 Tip program da olabilir (635 bytes)
            this.rwrProgram1Program = parseProgramFromBytes(rwrProgram1, 635, "RWR Program 1");

            // MWS Program 1 - 3 Tip program da olabilir (635 bytes)
            this.mwsProgram1Program = parseProgramFromBytes(mwsProgram1, 635, "MWS Program 1");

            // Diğer MWS programları
            this.mwsProgram2Program = parseProgramFromBytes(mwsProgram2, 635, "MWS Program 2");
            this.mwsProgram3Program = parseProgramFromBytes(mwsProgram3, 635, "MWS Program 3");
            this.mwsProgram4Program = parseProgramFromBytes(mwsProgram4, 635, "MWS Program 4");
        }

        private Object parseProgramFromBytes(byte[] data, int expectedSize, String fieldName) {
            if (data.length != expectedSize) {
                return "Boyut uyumsuz: " + data.length + " (beklenen: " + expectedSize + ")";
            }

            try {
                ByteBuffer buffer = ByteBuffer.wrap(data);
                buffer.order(ByteOrder.LITTLE_ENDIAN);

                byte programType = buffer.get();
                buffer.rewind();

                // BYPASS MASTER ÖZEL KONTROLÜ - Sadece Tip2 olabilir
                if (fieldName.equals("Bypass Master") && programType != (byte) 0x22) {
                    return "Bypass Master'da sadece Program Tip 2 olabilir. Bulunan: 0x" +
                            String.format("%02X", programType);
                }

                switch (programType) {
                    case (byte) 0x11:
                        // Program Tip 1 - 44 bytes
                        if (expectedSize >= 44) {
                            byte[] tip1Data = new byte[44];
                            System.arraycopy(data, 0, tip1Data, 0, 44);
                            ByteBuffer tip1Buffer = ByteBuffer.wrap(tip1Data);
                            tip1Buffer.order(ByteOrder.LITTLE_ENDIAN);
                            return ProgramType1.parse(tip1Buffer);
                        }
                        break;
                    case (byte) 0x22:
                        // Program Tip 2 - 379 bytes, ama 635 byte'lık alanda da olabilir
                        if (expectedSize >= 379) {
                            byte[] tip2Data = new byte[379];
                            System.arraycopy(data, 0, tip2Data, 0, 379);
                            ByteBuffer tip2Buffer = ByteBuffer.wrap(tip2Data);
                            tip2Buffer.order(ByteOrder.LITTLE_ENDIAN);
                            return ProgramType2.parse(tip2Buffer);
                        }
                        break;
                    case (byte) 0x33:
                        // Program Tip 3 - 635 bytes
                        if (expectedSize == 635) {
                            return ProgramType3.parse(buffer);
                        }
                        break;
                    default:
                        // İlk byte 0 ise boş alan olabilir
                        if (programType == 0x00) {
                            // Tüm byte'lar 0 mı kontrol et
                            boolean allZero = true;
                            for (byte b : data) {
                                if (b != 0) {
                                    allZero = false;
                                    break;
                                }
                            }
                            return allZero ? "Boş program alanı" : "Geçersiz program verisi";
                        }
                        return "Bilinmeyen program tipi: 0x" + String.format("%02X", programType);
                }
            } catch (Exception e) {
                return "Parse hatası: " + e.getMessage() + " - İlk byte: 0x" +
                        String.format("%02X", data.length > 0 ? data[0] : 0);
            }

            return "Geçersiz program verisi - İlk byte: 0x" +
                    String.format("%02X", data.length > 0 ? data[0] : 0);
        }
        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("=== PROGRAM DATA DETAYLARI ===\n\n");

            sb.append("BYTES BOYUTLARI:\n");
            sb.append("Bypass Master: ").append(bypassMaster.length).append(" bytes\n");
            sb.append("RWR Program 1: ").append(rwrProgram1.length).append(" bytes\n");
            sb.append("MWS Program 1: ").append(mwsProgram1.length).append(" bytes\n");
            sb.append("MWS Program 2: ").append(mwsProgram2.length).append(" bytes\n");
            sb.append("MWS Program 3: ").append(mwsProgram3.length).append(" bytes\n");
            sb.append("MWS Program 4: ").append(mwsProgram4.length).append(" bytes\n\n");

            sb.append("PROGRAM İÇERİKLERİ:\n");
            sb.append("===================\n\n");

            sb.append("1. BYPASS MASTER (379 bytes):\n");
            sb.append(formatProgramInfo(bypassMasterProgram)).append("\n\n");

            sb.append("2. RWR PROGRAM 1 (635 bytes):\n");
            sb.append(formatProgramInfo(rwrProgram1Program)).append("\n\n");

            sb.append("3. MWS PROGRAM 1 (635 bytes):\n");
            sb.append(formatProgramInfo(mwsProgram1Program)).append("\n\n");

            sb.append("4. MWS PROGRAM 2 (635 bytes):\n");
            sb.append(formatProgramInfo(mwsProgram2Program)).append("\n\n");

            sb.append("5. MWS PROGRAM 3 (635 bytes):\n");
            sb.append(formatProgramInfo(mwsProgram3Program)).append("\n\n");

            sb.append("6. MWS PROGRAM 4 (635 bytes):\n");
            sb.append(formatProgramInfo(mwsProgram4Program)).append("\n");

            return sb.toString();
        }

        private String formatProgramInfo(Object program) {
            if (program instanceof ProgramType1) {
                ProgramType1 p = (ProgramType1) program;
                String programName = new String(p.getProgramName()).trim();
                return String.format("   Program Tip 1: '%s' (Öncelik: %d, Alt Program: %d)\n   %s",
                        programName, p.getPriority(), p.getSubProgramCount(), p.toString());
            } else if (program instanceof ProgramType2) {
                ProgramType2 p = (ProgramType2) program;
                String programName = new String(p.getProgramName()).trim();
                return String.format("   Program Tip 2: '%s' (Öncelik: %d, Atım Sayısı: %d)\n   %s",
                        programName, p.getPriority(), p.getShotCount(), p.toString());
            } else if (program instanceof ProgramType3) {
                ProgramType3 p = (ProgramType3) program;
                String programName = new String(p.getProgramName()).trim();
                return String.format("   Program Tip 3: '%s' (Öncelik: %d, Atım Sayısı: %d)\n   %s",
                        programName, p.getPriority(), p.getShotCount(), p.toString());
            } else if (program instanceof String) {
                return "   " + program;
            } else {
                return "   Boş veya tanımsız program verisi";
            }
        }

        // Getter metodları
        public Object getBypassMasterProgram() { return bypassMasterProgram; }
        public Object getRwrProgram1Program() { return rwrProgram1Program; }
        public Object getMwsProgram1Program() { return mwsProgram1Program; }
        public Object getMwsProgram2Program() { return mwsProgram2Program; }
        public Object getMwsProgram3Program() { return mwsProgram3Program; }
        public Object getMwsProgram4Program() { return mwsProgram4Program; }

        // Program Type sınıfları aynen kalacak...
        // ProgramType1, ProgramType2, ProgramType3 sınıfları buraya gelecek
        // (Yukarıdaki kodun aynısı)

        public static class ProgramType1 {
            private byte programType;      // 1 Byte - Sabit 0x11
            private byte[] programName;    // 16 Bytes
            private byte priority;         // 1 Byte
            private byte subProgramCount;  // 1 Byte
            private SubProgramData[] subPrograms; // Alt program verileri
            private byte checksum;         // 1 Byte

            public static class SubProgramData {
                private byte ammunitionIndex;  // 1 Byte
                private byte simultaneousShotCount; // 4 Bit
                private short startDelay;      // 2 Bytes
                private short endDelay;        // 2 Bytes
                private short shotInterval;    // 2 Bytes
                private byte shotCount;        // 1 Byte
                private short groupInterval;   // 2 Bytes
                private byte groupCount;       // 1 Byte

                public static SubProgramData parse(ByteBuffer buffer) {
                    SubProgramData subProgram = new SubProgramData();
                    buffer.order(ByteOrder.LITTLE_ENDIAN);

                    // Mühimmat Bilgisi - 2 Bytes
                    subProgram.ammunitionIndex = buffer.get();
                    byte simultaneousShotByte = buffer.get();
                    subProgram.simultaneousShotCount = (byte) (simultaneousShotByte & 0x0F);

                    // Gecikme Bilgisi - 4 Bytes
                    subProgram.startDelay = buffer.getShort();
                    subProgram.endDelay = buffer.getShort();

                    // Atım Bilgisi - 3 Bytes
                    subProgram.shotInterval = buffer.getShort();
                    subProgram.shotCount = buffer.get();

                    // Grup Bilgisi - 3 Bytes
                    subProgram.groupInterval = buffer.getShort();
                    subProgram.groupCount = buffer.get();

                    return subProgram;
                }

                // Getter ve Setter metodları - SubProgramData içinde olmalı
                public byte getAmmunitionIndex() { return ammunitionIndex; }
                public void setAmmunitionIndex(byte ammunitionIndex) { this.ammunitionIndex = ammunitionIndex; }

                public byte getSimultaneousShotCount() { return simultaneousShotCount; }
                public void setSimultaneousShotCount(byte simultaneousShotCount) {
                    this.simultaneousShotCount = (byte) (simultaneousShotCount & 0x0F);
                }

                public short getStartDelay() { return startDelay; }
                public void setStartDelay(short startDelay) { this.startDelay = startDelay; }

                public short getEndDelay() { return endDelay; }
                public void setEndDelay(short endDelay) { this.endDelay = endDelay; }

                public short getShotInterval() { return shotInterval; }
                public void setShotInterval(short shotInterval) { this.shotInterval = shotInterval; }

                public byte getShotCount() { return shotCount; }
                public void setShotCount(byte shotCount) { this.shotCount = shotCount; }

                public short getGroupInterval() { return groupInterval; }
                public void setGroupInterval(short groupInterval) { this.groupInterval = groupInterval; }

                public byte getGroupCount() { return groupCount; }
                public void setGroupCount(byte groupCount) { this.groupCount = groupCount; }

                @Override
                public String toString() {
                    return "SubProgram{ammoIndex=" + (ammunitionIndex & 0xFF) +
                            ", simultaneousShots=" + simultaneousShotCount +
                            ", startDelay=" + startDelay + " ms" +
                            ", endDelay=" + endDelay + " ms" +
                            ", shotInterval=" + shotInterval + " ms" +
                            ", shotCount=" + shotCount +
                            ", groupInterval=" + groupInterval + " ms" +
                            ", groupCount=" + groupCount + "}";
                }
            }

            public ProgramType1() {
                this.programType = (byte) 0x11;
                this.programName = new byte[16];
                this.subPrograms = new SubProgramData[0];
            }

            public static ProgramType1 parse(ByteBuffer buffer) {
                ProgramType1 program = new ProgramType1();

                buffer.order(ByteOrder.LITTLE_ENDIAN);

                program.programType = buffer.get();
                if (program.programType != 0x11) {
                    throw new IllegalArgumentException("Program Tip 1 için geçersiz program tipi: 0x" +
                            String.format("%02X", program.programType));
                }

                program.programName = new byte[16];
                buffer.get(program.programName);

                program.priority = buffer.get();
                program.subProgramCount = buffer.get();

                // Alt program sayısına göre alt program verilerini oku
                program.subPrograms = new SubProgramData[program.subProgramCount];
                for (int i = 0; i < program.subProgramCount; i++) {
                    program.subPrograms[i] = SubProgramData.parse(buffer);
                }

                program.checksum = buffer.get();

                return program;
            }

            // Getter ve Setter metodları - ProgramType1 için
            public byte getProgramType() { return programType; }

            public byte[] getProgramName() { return programName.clone(); }
            public void setProgramName(byte[] programName) {
                if (programName.length != 16)
                    throw new IllegalArgumentException("Program name must be exactly 16 bytes");
                this.programName = programName.clone();
            }

            public byte getPriority() { return priority; }
            public void setPriority(byte priority) { this.priority = priority; }

            public byte getSubProgramCount() { return subProgramCount; }
            public void setSubProgramCount(byte subProgramCount) {
                this.subProgramCount = subProgramCount;
            }

            public SubProgramData[] getSubPrograms() { return subPrograms.clone(); }
            public void setSubPrograms(SubProgramData[] subPrograms) {
                this.subPrograms = subPrograms.clone();
            }

            public byte getChecksum() { return checksum; }
            public void setChecksum(byte checksum) { this.checksum = checksum; }

            @Override
            public String toString() {
                String programNameStr = new String(programName).trim();
                StringBuilder sb = new StringBuilder();
                sb.append("ProgramType1{")
                        .append("programType=0x").append(String.format("%02X", programType))
                        .append(", programName='").append(programNameStr).append("'")
                        .append(", priority=").append(priority)
                        .append(", subProgramCount=").append(subProgramCount);

                if (subPrograms.length > 0) {
                    sb.append(", subPrograms=[");
                    for (int i = 0; i < subPrograms.length; i++) {
                        if (i > 0) sb.append(", ");
                        sb.append(subPrograms[i].toString());
                    }
                    sb.append("]");
                }

                sb.append(", checksum=0x").append(String.format("%02X", checksum))
                        .append('}');
                return sb.toString();
            }
        }

        public static class ProgramType2 {
            private byte programType;      // 1 Byte - Sabit 0x22
            private byte[] programName;    // 16 Bytes
            private byte priority;         // 1 Byte
            private short startDelay;      // 2 Bytes - 0-10000 ms
            private short endDelay;        // 2 Bytes - 0-10000 ms
            private short groupInterval;   // 2 Bytes - 50-65535 ms
            private byte groupCount;       // 1 Byte - 1-60
            private byte shotCount;        // 1 Byte - 1-32

            // 32 Atım için
            private ShotData[] shots;      // 32 × 11 bytes = 352 bytes

            private byte checksum;         // 1 Byte

            public static class ShotData {
                private byte simultaneousShotCount; // 1 Byte - 1-8
                private byte[] ammunitionIndexes;   // 8 Bytes - Mühimmat indexleri
                private short shotInterval;         // 2 Bytes - 0 veya 15-65535 ms

                public ShotData() {
                    this.ammunitionIndexes = new byte[8];
                }

                public static ShotData parse(ByteBuffer buffer) {
                    ShotData shot = new ShotData();
                    buffer.order(ByteOrder.LITTLE_ENDIAN);

                    shot.simultaneousShotCount = buffer.get();
                    shot.ammunitionIndexes = new byte[8];
                    buffer.get(shot.ammunitionIndexes);
                    shot.shotInterval = buffer.getShort();

                    return shot;
                }

                // Getter ve Setter metodları
                public byte getSimultaneousShotCount() { return simultaneousShotCount; }
                public void setSimultaneousShotCount(byte simultaneousShotCount) {
                    this.simultaneousShotCount = simultaneousShotCount;
                }

                public byte[] getAmmunitionIndexes() { return ammunitionIndexes.clone(); }
                public void setAmmunitionIndexes(byte[] ammunitionIndexes) {
                    if (ammunitionIndexes.length != 8)
                        throw new IllegalArgumentException("Ammunition indexes must be exactly 8 bytes");
                    this.ammunitionIndexes = ammunitionIndexes.clone();
                }

                public short getShotInterval() { return shotInterval; }
                public void setShotInterval(short shotInterval) { this.shotInterval = shotInterval; }

                @Override
                public String toString() {
                    // Eş zamanlı atım sayısına göre sadece gereken mühimmat indexlerini göster
                    StringBuilder ammoBuilder = new StringBuilder("[");
                    for (int i = 0; i < simultaneousShotCount && i < ammunitionIndexes.length; i++) {
                        if (i > 0) ammoBuilder.append(", ");
                        ammoBuilder.append(ammunitionIndexes[i] & 0xFF); // Unsigned byte olarak göster
                    }
                    ammoBuilder.append("]");

                    return "ShotData{simultaneousShots=" + simultaneousShotCount +
                            ", ammoIndexes=" + ammoBuilder.toString() +
                            ", shotInterval=" + shotInterval + " ms}";
                }
            }

            public ProgramType2() {
                this.programType = (byte) 0x22;
                this.programName = new byte[16];
                this.shots = new ShotData[32];
                for (int i = 0; i < 32; i++) {
                    this.shots[i] = new ShotData();
                }
            }

            public static ProgramType2 parse(ByteBuffer buffer) {
                ProgramType2 program = new ProgramType2();

                buffer.order(ByteOrder.LITTLE_ENDIAN);

                program.programType = buffer.get();
                if (program.programType != 0x22) {
                    throw new IllegalArgumentException("Program Tip 2 için geçersiz program tipi: 0x" +
                            String.format("%02X", program.programType));
                }

                program.programName = new byte[16];
                buffer.get(program.programName);

                program.priority = buffer.get();
                program.startDelay = buffer.getShort();
                program.endDelay = buffer.getShort();
                program.groupInterval = buffer.getShort();
                program.groupCount = buffer.get();
                program.shotCount = buffer.get();

                // 32 Atım için verileri oku (32 × 11 bytes = 352 bytes)
                program.shots = new ShotData[32];
                for (int i = 0; i < 32; i++) {
                    program.shots[i] = ShotData.parse(buffer);
                }

                program.checksum = buffer.get();

                return program;
            }

            // Getter ve Setter metodları
            public byte getProgramType() { return programType; }

            public byte[] getProgramName() { return programName.clone(); }
            public void setProgramName(byte[] programName) {
                if (programName.length != 16)
                    throw new IllegalArgumentException("Program name must be exactly 16 bytes");
                this.programName = programName.clone();
            }

            public byte getPriority() { return priority; }
            public void setPriority(byte priority) { this.priority = priority; }

            public short getStartDelay() { return startDelay; }
            public void setStartDelay(short startDelay) { this.startDelay = startDelay; }

            public short getEndDelay() { return endDelay; }
            public void setEndDelay(short endDelay) { this.endDelay = endDelay; }

            public short getGroupInterval() { return groupInterval; }
            public void setGroupInterval(short groupInterval) { this.groupInterval = groupInterval; }

            public byte getGroupCount() { return groupCount; }
            public void setGroupCount(byte groupCount) { this.groupCount = groupCount; }

            public byte getShotCount() { return shotCount; }
            public void setShotCount(byte shotCount) { this.shotCount = shotCount; }

            public ShotData[] getShots() { return shots.clone(); }
            public void setShots(ShotData[] shots) {
                if (shots.length != 32)
                    throw new IllegalArgumentException("Shots array must have exactly 32 elements");
                this.shots = shots.clone();
            }

            // Sadece aktif atımları getir (shotCount kadar)
            public ShotData[] getActiveShots() {
                ShotData[] activeShots = new ShotData[shotCount];
                System.arraycopy(shots, 0, activeShots, 0, shotCount);
                return activeShots;
            }

            public byte getChecksum() { return checksum; }
            public void setChecksum(byte checksum) { this.checksum = checksum; }

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                String programNameStr = new String(programName).trim();
                sb.append("ProgramType2{")
                        .append("programType=0x").append(String.format("%02X", programType))
                        .append(", programName='").append(programNameStr).append("'")
                        .append(", priority=").append(priority)
                        .append(", startDelay=").append(startDelay).append(" ms")
                        .append(", endDelay=").append(endDelay).append(" ms")
                        .append(", groupInterval=").append(groupInterval).append(" ms")
                        .append(", groupCount=").append(groupCount)
                        .append(", shotCount=").append(shotCount)
                        .append(", shots=[");

                // Sadece aktif atımları göster (shotCount kadar)
                for (int i = 0; i < shotCount && i < shots.length; i++) {
                    if (i > 0) sb.append(", ");
                    sb.append(shots[i].toString());
                }
                sb.append("]")
                        .append(", checksum=0x").append(String.format("%02X", checksum))
                        .append('}');
                return sb.toString();
            }
        }

        public static class ProgramType3 {
            private byte programType;      // 1 Byte - Sabit 0x33
            private byte[] programName;    // 16 Bytes
            private byte priority;         // 1 Byte
            private short startDelay;      // 2 Bytes - 0-10000 ms
            private short endDelay;        // 2 Bytes - 0-10000 ms
            private short groupInterval;   // 2 Bytes - 50-65535 ms
            private byte groupCount;       // 1 Byte - 1-60
            private byte shotCount;        // 1 Byte - 1-32

            // 32 Atım için
            private ShotData[] shots;      // 32 × 19 bytes = 608 bytes

            private byte checksum;         // 1 Byte

            public static class ShotData {
                private byte simultaneousShotCount; // 1 Byte - 1-8
                private AmmunitionPair[] ammunitionPairs; // 8 çift (index + bölge) - 16 Bytes
                private short shotInterval;         // 2 Bytes - 0 veya 15-65535 ms

                public static class AmmunitionPair {
                    private byte index;    // 1 Byte - 0-63
                    private byte zone;     // 1 Byte - 0-8

                    public AmmunitionPair(byte index, byte zone) {
                        this.index = index;
                        this.zone = zone;
                    }

                    public byte getIndex() { return index; }
                    public void setIndex(byte index) { this.index = index; }

                    public byte getZone() { return zone; }
                    public void setZone(byte zone) { this.zone = zone; }

                    @Override
                    public String toString() {
                        return "Ammo[index=" + (index & 0xFF) + ", zone=" + zone + "]";
                    }
                }

                public ShotData() {
                    this.ammunitionPairs = new AmmunitionPair[8];
                    for (int i = 0; i < 8; i++) {
                        this.ammunitionPairs[i] = new AmmunitionPair((byte)0, (byte)0);
                    }
                }

                public static ShotData parse(ByteBuffer buffer) {
                    ShotData shot = new ShotData();
                    buffer.order(ByteOrder.LITTLE_ENDIAN);

                    // Eş zamanlı atım sayısı - 1 Byte
                    shot.simultaneousShotCount = buffer.get();

                    // 8 çift mühimmat index/bölge oku - 16 Bytes
                    for (int i = 0; i < 8; i++) {
                        byte index = buffer.get();
                        byte zone = buffer.get();
                        shot.ammunitionPairs[i] = new AmmunitionPair(index, zone);
                    }

                    // Atım aralığı - 2 Bytes
                    shot.shotInterval = buffer.getShort();

                    return shot;
                }

                // Getter ve Setter metodları
                public byte getSimultaneousShotCount() { return simultaneousShotCount; }
                public void setSimultaneousShotCount(byte simultaneousShotCount) {
                    this.simultaneousShotCount = simultaneousShotCount;
                }

                public AmmunitionPair[] getAmmunitionPairs() { return ammunitionPairs.clone(); }
                public void setAmmunitionPairs(AmmunitionPair[] ammunitionPairs) {
                    if (ammunitionPairs.length != 8)
                        throw new IllegalArgumentException("Ammunition pairs must have exactly 8 elements");
                    this.ammunitionPairs = ammunitionPairs.clone();
                }

                public short getShotInterval() { return shotInterval; }
                public void setShotInterval(short shotInterval) { this.shotInterval = shotInterval; }

                @Override
                public String toString() {
                    // Sadece eş zamanlı atım sayısı kadar mühimmat çiftini göster
                    StringBuilder ammoBuilder = new StringBuilder("[");
                    for (int i = 0; i < simultaneousShotCount && i < ammunitionPairs.length; i++) {
                        if (i > 0) ammoBuilder.append(", ");
                        ammoBuilder.append(ammunitionPairs[i].toString());
                    }
                    ammoBuilder.append("]");

                    return "ShotData{simultaneousShots=" + simultaneousShotCount +
                            ", ammoPairs=" + ammoBuilder.toString() +
                            ", shotInterval=" + shotInterval + " ms}";
                }
            }

            public ProgramType3() {
                this.programType = (byte) 0x33;
                this.programName = new byte[16];
                this.shots = new ShotData[32];
                for (int i = 0; i < 32; i++) {
                    this.shots[i] = new ShotData();
                }
            }

            public static ProgramType3 parse(ByteBuffer buffer) {
                ProgramType3 program = new ProgramType3();

                buffer.order(ByteOrder.LITTLE_ENDIAN);

                program.programType = buffer.get();
                if (program.programType != 0x33) {
                    throw new IllegalArgumentException("Program Tip 3 için geçersiz program tipi: 0x" +
                            String.format("%02X", program.programType));
                }

                program.programName = new byte[16];
                buffer.get(program.programName);

                program.priority = buffer.get();
                program.startDelay = buffer.getShort();
                program.endDelay = buffer.getShort();
                program.groupInterval = buffer.getShort();
                program.groupCount = buffer.get();
                program.shotCount = buffer.get();

                // 32 Atım için verileri oku (32 × 19 bytes = 608 bytes)
                program.shots = new ShotData[32];
                for (int i = 0; i < 32; i++) {
                    program.shots[i] = ShotData.parse(buffer);
                }

                program.checksum = buffer.get();

                return program;
            }

            // Getter ve Setter metodları
            public byte getProgramType() { return programType; }

            public byte[] getProgramName() { return programName.clone(); }
            public void setProgramName(byte[] programName) {
                if (programName.length != 16)
                    throw new IllegalArgumentException("Program name must be exactly 16 bytes");
                this.programName = programName.clone();
            }

            public byte getPriority() { return priority; }
            public void setPriority(byte priority) { this.priority = priority; }

            public short getStartDelay() { return startDelay; }
            public void setStartDelay(short startDelay) { this.startDelay = startDelay; }

            public short getEndDelay() { return endDelay; }
            public void setEndDelay(short endDelay) { this.endDelay = endDelay; }

            public short getGroupInterval() { return groupInterval; }
            public void setGroupInterval(short groupInterval) { this.groupInterval = groupInterval; }

            public byte getGroupCount() { return groupCount; }
            public void setGroupCount(byte groupCount) { this.groupCount = groupCount; }

            public byte getShotCount() { return shotCount; }
            public void setShotCount(byte shotCount) { this.shotCount = shotCount; }

            public ShotData[] getShots() { return shots.clone(); }
            public void setShots(ShotData[] shots) {
                if (shots.length != 32)
                    throw new IllegalArgumentException("Shots array must have exactly 32 elements");
                this.shots = shots.clone();
            }

            // Sadece aktif atımları getir (shotCount kadar)
            public ShotData[] getActiveShots() {
                ShotData[] activeShots = new ShotData[shotCount];
                System.arraycopy(shots, 0, activeShots, 0, shotCount);
                return activeShots;
            }

            public byte getChecksum() { return checksum; }
            public void setChecksum(byte checksum) { this.checksum = checksum; }

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                String programNameStr = new String(programName).trim();
                sb.append("ProgramType3{")
                        .append("programType=0x").append(String.format("%02X", programType))
                        .append(", programName='").append(programNameStr).append("'")
                        .append(", priority=").append(priority)
                        .append(", startDelay=").append(startDelay).append(" ms")
                        .append(", endDelay=").append(endDelay).append(" ms")
                        .append(", groupInterval=").append(groupInterval).append(" ms")
                        .append(", groupCount=").append(groupCount)
                        .append(", shotCount=").append(shotCount)
                        .append(", shots=[");

                // Sadece aktif atımları göster (shotCount kadar)
                for (int i = 0; i < shotCount && i < shots.length; i++) {
                    if (i > 0) sb.append(", ");
                    sb.append(shots[i].toString());
                }
                sb.append("]")
                        .append(", checksum=0x").append(String.format("%02X", checksum))
                        .append('}');
                return sb.toString();
            }
        }
    }

    // 1.3 AMTB Veri Yapısı (200 Bytes - 8 AMTB * 25 Bytes)
    public static class AMTBData {
        private AMTB[] amtbs;

        public AMTBData() {
            this.amtbs = new AMTB[8];
            for (int i = 0; i < 8; i++) {
                this.amtbs[i] = new AMTB();
            }
        }

        public static AMTBData parse(ByteBuffer buffer) {
            AMTBData amtbData = new AMTBData();

            for (int i = 0; i < 8; i++) {
                amtbData.amtbs[i] = AMTB.parse(buffer);
            }

            return amtbData;
        }

        public AMTB[] getAmtbs() {
            return amtbs.clone();
        }

        public void setAmtbs(AMTB[] amtbs) {
            if (amtbs.length != 8) throw new IllegalArgumentException("Must have exactly 8 AMTB entries");
            this.amtbs = amtbs.clone();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("AMTB'ler: \n");

            for (int i = 0; i < amtbs.length; i++) {
                sb.append(amtbs[i].toString());
                if (i < amtbs.length - 1) {
                    sb.append("\n");
                }
            }

            return sb.toString();
        }

        public static class AMTB {
            private int region;           // 4 bit (0-8)
            private int sector;           // 4 bit (0-4)
            private int startDelay;       // 2 bytes (25-10000 ms)
            private int shotInterval;     // 2 bytes (25-66350 ms)
            private FailedShotNumbers failedShotNumbers;

            public AMTB() {
                this.failedShotNumbers = new FailedShotNumbers();
            }

            public static AMTB parse(ByteBuffer buffer) {
                AMTB amtb = new AMTB();

                byte firstByte = buffer.get();
                amtb.region = (firstByte >> 4) & 0x0F;
                amtb.sector = firstByte & 0x0F;

                amtb.startDelay = Short.toUnsignedInt(buffer.getShort());
                amtb.shotInterval = Short.toUnsignedInt(buffer.getShort());

                amtb.failedShotNumbers = FailedShotNumbers.parse(buffer);

                return amtb;
            }

            public int getRegion() {
                return region;
            }

            public void setRegion(int region) {
                if (region < 0 || region > 8) throw new IllegalArgumentException("Region must be between 0-8");
                this.region = region;
            }

            public int getSector() {
                return sector;
            }

            public void setSector(int sector) {
                if (sector < 0 || sector > 4) throw new IllegalArgumentException("Sector must be between 0-4");
                this.sector = sector;
            }

            public int getStartDelay() {
                return startDelay;
            }

            public void setStartDelay(int startDelay) {
                if (startDelay < 25 || startDelay > 10000)
                    throw new IllegalArgumentException("Start delay must be between 25-10000 ms");
                this.startDelay = startDelay;
            }

            public int getShotInterval() {
                return shotInterval;
            }

            public void setShotInterval(int shotInterval) {
                if (shotInterval < 25 || shotInterval > 66350)
                    throw new IllegalArgumentException("Shot interval must be between 25-66350 ms");
                this.shotInterval = shotInterval;
            }

            public FailedShotNumbers getFailedShotNumbers() {
                return failedShotNumbers;
            }

            public void setFailedShotNumbers(FailedShotNumbers failedShotNumbers) {
                this.failedShotNumbers = failedShotNumbers;
            }

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append("{\n");

                String regionDesc = getRegionDescription(region);
                String sectorDesc = getSectorDescription(sector);

                sb.append("\tBölge: [").append(region).append(" - ").append(regionDesc).append("] \n");
                sb.append("\tSektör: [").append(sector).append(" - ").append(sectorDesc).append("] \n");
                sb.append("\tJettison Başlama Gecikmesi: [").append(startDelay).append("] \n");
                sb.append("\tJettison Atım Aralığı: [").append(shotInterval).append("] \n");
                sb.append("\tEğitim Modu Değerleri: \n");
                sb.append(failedShotNumbers.toString());

                sb.append("}");
                return sb.toString();
            }

            private String getRegionDescription(int region) {
                switch (region) {
                    case 0: return "Aktif Değil";
                    case 1: return "B1";
                    case 2: return "B2";
                    case 3: return "B3";
                    case 4: return "B4";
                    case 5: return "B5";
                    case 6: return "B6";
                    case 7: return "B7";
                    case 8: return "B8";
                    default: return "Bilinmeyen";
                }
            }

            private String getSectorDescription(int sector) {
                switch (sector) {
                    case 0: return "Tanımsız";
                    case 1: return "S1";
                    case 2: return "S2";
                    case 3: return "S3";
                    case 4: return "S4";
                    default: return "Bilinmeyen";
                }
            }
        }

        public static class FailedShotNumbers {
            private int[] cat1 = new int[5];
            private int[] cat2 = new int[5];
            private int[] cat3 = new int[5];
            private int[] cat4 = new int[5];

            public FailedShotNumbers() {
                Arrays.fill(cat1, 0);
                Arrays.fill(cat2, 0);
                Arrays.fill(cat3, 0);
                Arrays.fill(cat4, 0);
            }

            public static FailedShotNumbers parse(ByteBuffer buffer) {
                FailedShotNumbers failedShotNumbers = new FailedShotNumbers();

                for (int i = 0; i < 5; i++) failedShotNumbers.cat1[i] = buffer.get() & 0xFF;
                for (int i = 0; i < 5; i++) failedShotNumbers.cat2[i] = buffer.get() & 0xFF;
                for (int i = 0; i < 5; i++) failedShotNumbers.cat3[i] = buffer.get() & 0xFF;
                for (int i = 0; i < 5; i++) failedShotNumbers.cat4[i] = buffer.get() & 0xFF;

                return failedShotNumbers;
            }

            public int[] getCat1() {
                return cat1.clone();
            }

            public void setCat1(int[] cat1) {
                if (cat1.length != 5) throw new IllegalArgumentException("CAT1 must have exactly 5 entries");
                this.cat1 = cat1.clone();
            }

            public int[] getCat2() {
                return cat2.clone();
            }

            public void setCat2(int[] cat2) {
                if (cat2.length != 5) throw new IllegalArgumentException("CAT2 must have exactly 5 entries");
                this.cat2 = cat2.clone();
            }

            public int[] getCat3() {
                return cat3.clone();
            }

            public void setCat3(int[] cat3) {
                if (cat3.length != 5) throw new IllegalArgumentException("CAT3 must have exactly 5 entries");
                this.cat3 = cat3.clone();
            }

            public int[] getCat4() {
                return cat4.clone();
            }

            public void setCat4(int[] cat4) {
                if (cat4.length != 5) throw new IllegalArgumentException("CAT4 must have exactly 5 entries");
                this.cat4 = cat4.clone();
            }

            @Override
            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append("\t{\n");

                // CAT1 (CHAFF)
                sb.append("\t\tCHAFF: [");
                for (int i = 0; i < cat1.length; i++) {
                    sb.append(cat1[i]);
                    if (i < cat1.length - 1) sb.append("]     [");
                }
                sb.append("] \n");

                // CAT2 (FLARE)
                sb.append("\t\tFLARE: [");
                for (int i = 0; i < cat2.length; i++) {
                    sb.append(cat2[i]);
                    if (i < cat2.length - 1) sb.append("]     [");
                }
                sb.append("] \n");

                // CAT3 (ASH)
                sb.append("\t\tASH: [");
                for (int i = 0; i < cat3.length; i++) {
                    sb.append(cat3[i]);
                    if (i < cat3.length - 1) sb.append("]     [");
                }
                sb.append("] \n");

                // CAT4 (CAT4)
                sb.append("\t\tCAT4: [");
                for (int i = 0; i < cat4.length; i++) {
                    sb.append(cat4[i]);
                    if (i < cat4.length - 1) sb.append("]     [");
                }
                sb.append("] \n");

                sb.append("\t}");
                return sb.toString();
            }
        }
    }

    // 1.4-1.10 Diğer GVD Bileşenleri
    public static class LauncherPriorities {
        private Launcher[] launchers = new Launcher[9];

        public static LauncherPriorities parse(ByteBuffer buffer) {
            LauncherPriorities priorities = new LauncherPriorities();
            for (int i = 0; i < 9; i++) priorities.launchers[i] = Launcher.parse(buffer);
            return priorities;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("Başlatıcı Bilgileri: \n");
            sb.append("{\n");

            String[] launcherNames = {"MWS", "MWS", "MWS", "MWS", "Pickle/Trigger", "Dispense", "Escape", "Jettison", "Jettison"};

            for (int i = 0; i < launchers.length; i++) {
                String name = launcherNames[i];
                if (i < 4) {
                    name = "MWS Program " + (i + 1);
                }
                sb.append("\t").append(name).append(" - ");
                sb.append(launchers[i].toString());
                if (i < launchers.length - 1) {
                    sb.append("\n");
                }
            }

            sb.append("\n}");
            return sb.toString();
        }

        public static class Launcher {
            private int priority;
            private int startRule;
            private int interruptRule;

            public static Launcher parse(ByteBuffer buffer) {
                Launcher launcher = new Launcher();
                launcher.priority = buffer.get() & 0xFF;
                byte rulesByte = buffer.get();
                launcher.startRule = (rulesByte >> 4) & 0x0F;
                launcher.interruptRule = rulesByte & 0x0F;
                return launcher;
            }

            @Override
            public String toString() {
                String startRuleDesc = getStartRuleDescription(startRule);
                String interruptRuleDesc = getInterruptRuleDescription(interruptRule);

                return "Öncelik: [" + priority + "]     Hemen Başlamazsa: [" + startRule + " - " + startRuleDesc +
                        "]     Kesme Kuralı: [" + interruptRule + " - " + interruptRuleDesc + "]";
            }

            private String getStartRuleDescription(int rule) {
                switch (rule) {
                    case 0: return "İptal";
                    case 1: return "Sonra Başla";
                    default: return "Bilinmeyen";
                }
            }

            private String getInterruptRuleDescription(int rule) {
                switch (rule) {
                    case 0: return "İptal";
                    case 1: return "Sonra Devam Et";
                    default: return "Bilinmeyen";
                }
            }
        }
    }

    public static class SameProgramStartRule {
        private int rule;

        public SameProgramStartRule() {
        }

        public SameProgramStartRule(byte rule) {
            this.rule = rule & 0xFF;
        }

        public int getRule() {
            return rule;
        }

        public void setRule(int rule) {
            this.rule = rule;
        }

        @Override
        public String toString() {
            String ruleDesc = getRuleDescription(rule);
            return "Aynı Program Başlatma Kuralı: [" + rule + " - " + ruleDesc + "]";
        }

        private String getRuleDescription(int rule) {
            switch (rule) {
                case 0: return "Yeniden Başlatma";
                case 1: return "Yeniden Başlat";
                default: return "Bilinmeyen";
            }
        }
    }

    public static class ZeroizeInfo {
        private int action;

        public ZeroizeInfo() {
        }

        public ZeroizeInfo(byte action) {
            this.action = action & 0xFF;
        }

        public int getAction() {
            return action;
        }

        public void setAction(int action) {
            this.action = action;
        }

        @Override
        public String toString() {
            String actionDesc = getActionDescription(action);
            return "Zeroize: [" + action + " - " + actionDesc + "]";
        }

        private String getActionDescription(int action) {
            switch (action) {
                case 0: return "GVD";
                case 1: return "GVD & Firmware";
                case 2: return "GVD & Logo";
                case 3: return "Tamamen";
                default: return "Bilinmeyen";
            }
        }
    }

    public static class ProgramNumbers {
        private int[] programNumbers = new int[6];

        public static ProgramNumbers parse(ByteBuffer buffer) {
            ProgramNumbers pn = new ProgramNumbers();
            for (int i = 0; i < 6; i++) pn.programNumbers[i] = Short.toUnsignedInt(buffer.getShort());
            return pn;
        }

        @Override
        public String toString() {
            String[] programNames = {"Bypass", "RİAS Program", "FİS Program 1 (S0)", "FİS Program 2 (S1)", "FİS Program 3 (S2)", "FİS Program 4 (S3)"};

            StringBuilder sb = new StringBuilder();
            sb.append("Program Numaraları: \n");
            sb.append("{\n");

            for (int i = 0; i < programNumbers.length; i++) {
                sb.append("\t").append(programNames[i]).append(": [").append(programNumbers[i]).append("]");
                if (i < programNumbers.length - 1) {
                    sb.append(" \n");
                }
            }

            sb.append("\n}");
            return sb.toString();
        }
    }

    public static class LineNumber {
        private int lineNumber;

        public LineNumber() {
        }

        public LineNumber(byte lineNumber) {
            this.lineNumber = lineNumber & 0xFF;
        }

        public int getLineNumber() {
            return lineNumber;
        }

        public void setLineNumber(int lineNumber) {
            this.lineNumber = lineNumber;
        }

        @Override
        public String toString() {
            String lineDesc = getLineDescription(lineNumber);
            return "Hat No: [" + lineNumber + " - " + lineDesc + "]";
        }

        private String getLineDescription(int lineNumber) {
            switch (lineNumber) {
                case 0: return "Hat 0";
                case 1: return "Hat 1";
                case 2: return "Hat 2";
                case 3: return "Hat 3";
                default: return "Bilinmeyen";
            }
        }
    }

    public static class Reserved {
        private byte[] data;

        public Reserved() {
            this.data = new byte[180];
        }

        public Reserved(byte[] data) {
            this.data = data.clone();
        }

        public byte[] getData() {
            return data.clone();
        }

        public void setData(byte[] data) {
            this.data = data.clone();
        }

        @Override
        public String toString() {
            return "Ayrılmış: [" + data.length + " bytes]";
        }
    }

    public static class Checksum {
        private byte checksum;

        public Checksum() {
        }

        public Checksum(byte checksum) {
            this.checksum = checksum;
        }

        public byte getChecksum() {
            return checksum;
        }

        public void setChecksum(byte checksum) {
            this.checksum = checksum;
        }

        @Override
        public String toString() {
            return "Sağlama: [" + checksum + "]";
        }
    }

    // 2. Magazin Veri Yapısı (512 Bytes)
    public static class MagazineData {
        private Magazine[] magazines = new Magazine[16];
        private byte[] reserved = new byte[15];
        private byte checksum;

        public MagazineData() {
            for (int i = 0; i < 16; i++) {
                this.magazines[i] = new Magazine();
            }
            Arrays.fill(reserved, (byte) 0x00);
        }

        public static MagazineData parse(byte[] data) {
            if (data.length != 512) {
                throw new IllegalArgumentException("MagazineData must be exactly 512 bytes");
            }

            MagazineData magazineData = new MagazineData();
            ByteBuffer buffer = ByteBuffer.wrap(data);
            buffer.order(ByteOrder.LITTLE_ENDIAN);

            // 16 Magazin
            for (int i = 0; i < 16; i++) {
                magazineData.magazines[i] = Magazine.parse(buffer);
            }

            // Ayrılmış (15 bytes)
            buffer.get(magazineData.reserved);

            // Sağlama (1 byte)
            magazineData.checksum = buffer.get();

            return magazineData;
        }

        public Magazine[] getMagazines() {
            return magazines.clone();
        }

        public void setMagazines(Magazine[] magazines) {
            if (magazines.length != 16) throw new IllegalArgumentException("Must have exactly 16 magazines");
            this.magazines = magazines.clone();
        }

        public byte[] getReserved() {
            return reserved.clone();
        }

        public void setReserved(byte[] reserved) {
            if (reserved.length != 15) throw new IllegalArgumentException("Reserved must be exactly 15 bytes");
            this.reserved = reserved.clone();
        }

        public byte getChecksum() {
            return checksum;
        }

        public void setChecksum(byte checksum) {
            this.checksum = checksum;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("----------MAGAZİN TANIMLAMA ALANI----------\n");
            sb.append("Magazinler: \n");

            for (int i = 0; i < magazines.length; i++) {
                sb.append(magazines[i].toString(i));
                if (i < magazines.length - 1) {
                    sb.append("\n");
                }
            }

            return sb.toString();
        }

        public static class Magazine {
            private int magazineType; // 1 byte
            private Cartridge[] cartridges; // 30 cartridges

            public Magazine() {
                this.cartridges = new Cartridge[30];
                for (int i = 0; i < 30; i++) {
                    this.cartridges[i] = new Cartridge();
                }
            }

            public static Magazine parse(ByteBuffer buffer) {
                Magazine magazine = new Magazine();

                // Magazin Tipi (1 byte)
                magazine.magazineType = buffer.get() & 0xFF;

                // 30 Fişek
                for (int i = 0; i < 30; i++) {
                    magazine.cartridges[i] = Cartridge.parse(buffer);
                }

                return magazine;
            }

            public int getMagazineType() {
                return magazineType;
            }

            public void setMagazineType(int magazineType) {
                if (magazineType < 0 || magazineType > 6)
                    throw new IllegalArgumentException("Magazine type must be between 0-6");
                this.magazineType = magazineType;
            }

            public Cartridge[] getCartridges() {
                return cartridges.clone();
            }

            public void setCartridges(Cartridge[] cartridges) {
                if (cartridges.length != 30) throw new IllegalArgumentException("Must have exactly 30 cartridges");
                this.cartridges = cartridges.clone();
            }

            public String getMagazineTypeDescription() {
                switch (magazineType) {
                    case 0:
                        return "Tanımlı Değil";
                    case 1:
                        return "3x10 (1x1)";
                    case 2:
                        return "6x5 (1x1)";
                    case 3:
                        return "3x10 (2x1)";
                    case 4:
                        return "6x5 (2x1)";
                    case 5:
                        return "3x10 karma";
                    case 6:
                        return "6x5 karma";
                    default:
                        return "Bilinmeyen";
                }
            }

            public String toString(int magazineIndex) {
                StringBuilder sb = new StringBuilder();
                sb.append("{\n");
                sb.append("\tMagazin: [").append(magazineIndex).append("] \n");
                sb.append("\tMagazin Tipi: [").append(magazineType).append(" - ").append(getMagazineTypeDescription()).append("] \n");

                // Eğer magazin tanımlı değilse sadece temel bilgileri göster
                if (magazineType == 0) {
                    sb.append("}");
                    return sb.toString();
                }

                sb.append("\tMühimmatlar: \n");
                for (int i = 0; i < cartridges.length; i++) {
                    sb.append(cartridges[i].toString(i + 1));
                    if (i < cartridges.length - 1) {
                        sb.append("\n");
                    }
                }
                sb.append("\n}");

                return sb.toString();
            }

            @Override
            public String toString() {
                return toString(0);
            }
        }

        public static class Cartridge {
            private boolean defined; // 1 bit
            private int ammunitionIndex; // 6 bits

            public Cartridge() {
                this.defined = false;
                this.ammunitionIndex = 0;
            }

            public static Cartridge parse(ByteBuffer buffer) {
                Cartridge cartridge = new Cartridge();
                byte data = buffer.get();

                cartridge.defined = ((data >> 7) & 0x01) == 1; // 1. bit
                // 2. bit ayrılmış (atlanıyor)
                cartridge.ammunitionIndex = data & 0x3F; // 6 bit

                return cartridge;
            }

            public boolean isDefined() {
                return defined;
            }

            public void setDefined(boolean defined) {
                this.defined = defined;
            }

            public int getAmmunitionIndex() {
                return ammunitionIndex;
            }

            public void setAmmunitionIndex(int ammunitionIndex) {
                if (ammunitionIndex < 0 || ammunitionIndex > 63)
                    throw new IllegalArgumentException("Ammunition index must be between 0-63");
                this.ammunitionIndex = ammunitionIndex;
            }

            public String toString(int cellNumber) {
                return "\t{\n" +
                        "\t\tHücre No: [" + cellNumber + "] \n" +
                        "\t\tMühimmat Durumu: [" + (defined ? "1 - Dolu" : "0 - Boş") + "] \n" +
                        "\t\tMühimmat İndeksi: [" + ammunitionIndex + "] \n" +
                        "\t}";
            }

            @Override
            public String toString() {
                return toString(0);
            }
        }}


    // 3. Mühimmat Veri Yapısı (2048 Bytes) - Önceki koddan

    public static class AmmunitionData {
        private Category[] categories;
        private Munition[] munitions;
        private byte[] reserved;
        private byte checksum;

        public AmmunitionData() {
            this.categories = new Category[4];
            for (int i = 0; i < 4; i++) this.categories[i] = new Category();
            this.munitions = new Munition[64];
            for (int i = 0; i < 64; i++) this.munitions[i] = new Munition();
            this.reserved = new byte[555];
            Arrays.fill(this.reserved, (byte) 0x00);
        }

        public static AmmunitionData parse(byte[] data) {
            if (data.length != 2048) throw new IllegalArgumentException("AmmunitionData must be exactly 2048 bytes");
            AmmunitionData ammoData = new AmmunitionData();
            ByteBuffer buffer = ByteBuffer.wrap(data);
            buffer.order(ByteOrder.LITTLE_ENDIAN);

            // Kategori Tanımlama (20 bytes)
            for (int i = 0; i < 4; i++) {
                byte[] nameBytes = new byte[5];
                buffer.get(nameBytes);
                ammoData.categories[i] = new Category(new String(nameBytes, StandardCharsets.US_ASCII).trim());
            }

            // 64 Mühimmat (her biri 23 bytes = 1472 bytes)
            for (int i = 0; i < 64; i++) {
                ammoData.munitions[i] = Munition.parse(buffer);
            }

            // Ayrılmış (555 bytes)
            buffer.get(ammoData.reserved);

            // Sağlama (1 byte)
            ammoData.checksum = buffer.get();

            return ammoData;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("----------MÜHİMMATLAR----------\n");

            // Kategoriler
            for (int i = 0; i < categories.length; i++) {
                sb.append("Kategori ").append(i + 1).append(": [").append(categories[i].getName()).append("] \n");
            }

            sb.append("Mühimmatlar: \n");
            for (int i = 0; i < munitions.length; i++) {
                sb.append(munitions[i].toString(categories));
                if (i < munitions.length - 1) {
                    sb.append("\n");
                }
            }

            return sb.toString();
        }

        public static class Category {
            private String name;

            public Category() {
                this.name = "";
            }

            public Category(String name) {
                setName(name);
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name != null ? name : "";
            }
        }

        public static class Munition {
            private String name;
            private MunitionInfo1 info1;
            private MunitionInfo2 info2;
            private MunitionInfo3 info3;
            private MunitionInfo4 info4;

            public Munition() {
                this.name = "";
                this.info1 = new MunitionInfo1();
                this.info2 = new MunitionInfo2();
                this.info3 = new MunitionInfo3();
                this.info4 = new MunitionInfo4();
            }

            public static Munition parse(ByteBuffer buffer) {
                Munition munition = new Munition();

                // Mühimmat Adı (5 bytes)
                byte[] nameBytes = new byte[5];
                buffer.get(nameBytes);
                munition.name = new String(nameBytes, StandardCharsets.US_ASCII).trim();

                munition.info1 = MunitionInfo1.parse(buffer);
                munition.info2 = MunitionInfo2.parse(buffer);
                munition.info3 = MunitionInfo3.parse(buffer);
                munition.info4 = MunitionInfo4.parse(buffer);

                return munition;
            }

            public String toString(Category[] categories) {
                StringBuilder sb = new StringBuilder();
                sb.append("{\n");

                if (!info1.defined) {
                    sb.append("\tTanımlı mı: [0 - Tanımsız] \n");
                    sb.append("}");
                    return sb.toString();
                }

                sb.append("\tTanımlı mı: [1 - Tanımlı] \n");
                sb.append("\tAdı: [").append(name).append("] \n");

                // Kategori ismini al
                String categoryName = "Bilinmeyen";
                if (info1.categoryIndex >= 0 && info1.categoryIndex < categories.length) {
                    categoryName = categories[info1.categoryIndex].getName();
                }
                sb.append("\tKategori Indeksi: [").append(info1.categoryIndex).append(" - ").append(categoryName).append("] \n");

                sb.append("\tMühimmat Indeksi: [").append(info1.munitionIndex).append("] \n");

                // Alt Kategori tanımı
                String subCategoryDesc = getSubCategoryDescription(info1.subCategory);
                sb.append("\tAlt Kategori Indeksi: [").append(info1.subCategory).append(" - ").append(subCategoryDesc).append("] \n");

                sb.append("\tErken Hatalı Atımı Kontrol Et: [").append(info2.earlyFaultCheck ? "1 - Kontrol Et" : "0 - Kontrol Etme").append("] \n");
                sb.append("\tSmart Mühimmat mı: [").append(info2.isSmart ? "1 - Smart" : "0 - Smart Değil").append("] \n");
                sb.append("\tMühimmat Boyutu: [").append(info2.size ? "1 - 2x1" : "0 - 1x1").append("] \n");
                sb.append("\tJettison Yapılabilir mi: [").append(info2.jettisonable ? "1 - Evet" : "0 - Hayır").append("] \n");
                sb.append("\tMuadil Mühimmat 1 Indeksi: [").append(info2.equivalentMunition1).append("] \n");
                sb.append("\tMuadil Mühimmat 2 Indeksi: [").append(info2.equivalentMunition2).append("] \n");
                sb.append("\tMuadil Mühimmat 3 Indeksi: [").append(info2.equivalentMunition3).append("] \n");
                sb.append("\tAteşleme Akımı: [").append(info3.firingCurrent).append(" mA] \n");
                sb.append("\tHatalı Atım Eşik Seviyesi: [").append(info3.faultCurrentThreshold).append(" mA] \n");
                sb.append("\tAtım Sonrası Eşik Seviyesi: [").append(info3.postFireThreshold).append(" mA] \n");
                sb.append("\tAteşleme Darbe Genişliği: [").append(info3.firingPulseWidth).append(" ms] \n");
                sb.append("\tAlt-Üst Kısım Atım Aralığı: [").append(info3.firingInterval).append(" ms] \n");
                sb.append("\tZener Voltaj Değeri: [").append(info3.zenerVoltage).append(" mV] \n");

                String pieceCountDesc = info4.pieceCount == 1 ? "1 - Tek" : "2 - Çift";
                sb.append("\tParça Sayısı: [").append(pieceCountDesc).append("] \n");

                sb.append("}");
                return sb.toString();
            }

            private String getSubCategoryDescription(int subCategory) {
                switch (subCategory) {
                    case 0: return "Area Device";
                    case 1: return "Aeronörazmite";
                    case 2: return "Properbite";
                    case 3: return "Standard MTV";
                    case 4: return "Spectral";
                    case 5: return "Pyrophoric";
                    case 6: return "Diff / Inert";
                    case 7: return "Multiple Payloads";
                    case 255: return "Undefined";
                    default: return "Bilinmeyen";
                }
            }
        }

        public static class MunitionInfo1 {
            private boolean defined;
            private int categoryIndex;
            private int munitionIndex;
            private int subCategory;

            public static MunitionInfo1 parse(ByteBuffer buffer) {
                MunitionInfo1 info1 = new MunitionInfo1();

                // İlk byte: [Ayrılmış(5 bit) | Tanımlı(1 bit) | Kategori Index(2 bit)]
                byte firstByte = buffer.get();
                info1.defined = ((firstByte >> 2) & 0x01) == 1;
                info1.categoryIndex = firstByte & 0x03;

                info1.munitionIndex = buffer.get() & 0xFF;
                info1.subCategory = buffer.get() & 0xFF;

                return info1;
            }
        }

        public static class MunitionInfo2 {
            private boolean earlyFaultCheck;
            private boolean isSmart;
            private boolean size;
            private boolean jettisonable;
            private int equivalentMunition1;
            private int equivalentMunition2;
            private int equivalentMunition3;

            public static MunitionInfo2 parse(ByteBuffer buffer) {
                MunitionInfo2 info2 = new MunitionInfo2();

                // İlk byte: [Ayrılmış(4 bit) | Erken Hatalı(1) | Smart(1) | Size(1) | Jettison(1)]
                byte firstByte = buffer.get();
                info2.earlyFaultCheck = ((firstByte >> 3) & 0x01) == 1;
                info2.isSmart = ((firstByte >> 2) & 0x01) == 1;
                info2.size = ((firstByte >> 1) & 0x01) == 1;
                info2.jettisonable = (firstByte & 0x01) == 1;

                info2.equivalentMunition1 = buffer.get() & 0xFF;
                info2.equivalentMunition2 = buffer.get() & 0xFF;
                info2.equivalentMunition3 = buffer.get() & 0xFF;

                return info2;
            }
        }

        public static class MunitionInfo3 {
            private int firingCurrent;
            private int faultCurrentThreshold;
            private int postFireThreshold;
            private int firingPulseWidth;
            private int firingInterval;
            private int zenerVoltage;

            public static MunitionInfo3 parse(ByteBuffer buffer) {
                MunitionInfo3 info3 = new MunitionInfo3();
                info3.firingCurrent = buffer.getShort() & 0xFFFF;
                info3.faultCurrentThreshold = buffer.getShort() & 0xFFFF;
                info3.postFireThreshold = buffer.getShort() & 0xFFFF;
                info3.firingPulseWidth = buffer.get() & 0xFF;
                info3.firingInterval = buffer.get() & 0xFF;
                info3.zenerVoltage = buffer.getShort() & 0xFFFF;
                return info3;
            }
        }

        public static class MunitionInfo4 {
            private int pieceCount;

            public MunitionInfo4() {
                this.pieceCount = 1;
            }

            public static MunitionInfo4 parse(ByteBuffer buffer) {
                MunitionInfo4 info4 = new MunitionInfo4();
                info4.pieceCount = buffer.get() & 0xFF;
                return info4;
            }
        }
    }

        public static void main(String[] args) {
            final int GVD_SIZE = 4096;
            final int MAG_SIZE = 512;
            final int AMMO_SIZE = 2048;
            final int TOTAL_SIZE = GVD_SIZE + MAG_SIZE + AMMO_SIZE;

            try {
                // ✅ Hex string dosyasını oku
                Path filePath = Path.of("C:\\Users\\levent.keskin\\Desktop\\GVDDATAX.txt");
                //Path filePath = Path.of("C:\\Users\\levent\\Desktop\\GVDDATAX.txt");
                String hexText = Files.readString(filePath);

                // 🔁 Hex string -> byte[]
                byte[] allData = hexStringToByteArray(hexText);

                if (allData.length != TOTAL_SIZE) {
                    System.err.println("Hatalı byte sayısı: " + allData.length + " (beklenen: " + TOTAL_SIZE + ")");
                    return;
                }

                byte[] gvdData = Arrays.copyOfRange(allData, 0, GVD_SIZE);
                byte[] magazineData = Arrays.copyOfRange(allData, GVD_SIZE, GVD_SIZE + MAG_SIZE);
                byte[] ammunitionData = Arrays.copyOfRange(allData, GVD_SIZE + MAG_SIZE, TOTAL_SIZE);

                // Parse et
                CompleteGVDSystem system = CompleteGVDSystem.parseCompleteData(gvdData, magazineData, ammunitionData);
                System.out.println("✔ GVD parse başarılı:");
                System.out.println(system);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // Yardımcı fonksiyon (hex metni byte dizisine çevirir)
        public static byte[] hexStringToByteArray(String s) {
            s = s.replaceAll("[^0-9A-Fa-f]", "");
            int len = s.length();
            byte[] data = new byte[len / 2];
            for (int i = 0; i < len; i += 2) {
                data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                        + Character.digit(s.charAt(i+1), 16));
            }
            return data;
        }

}