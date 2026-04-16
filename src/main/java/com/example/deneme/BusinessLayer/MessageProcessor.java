package com.example.deneme.BusinessLayer;



import com.example.deneme.AMTBStateController;
import com.example.deneme.AMTB_Sim_Controller;
import com.example.deneme.BusinessLayer.ListeningSerial;
import com.example.deneme.ControllerClasses.*;
import com.example.deneme.GVDSystemParser;
import com.example.deneme.MessageIDConstans.AMTBMessageState;
import com.example.deneme.MessageIDConstans.AMTBMsgIDConstants;
import com.example.deneme.MessageIDConstans.AmtbFunctions;
import com.example.deneme.MessageIDConstans.AmtbSubFunctions;
import com.fazecast.jSerialComm.SerialPort;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import static com.example.deneme.AMTBStateController.*;
import static com.example.deneme.AMTB_Sim_Controller.InventoryCellStateList;
import com.example.deneme.ControllerClasses.GVDMessage;

public class MessageProcessor implements Runnable {

    private final BlockingQueue<MessageWithSource> messageQueue;  // Değişti
    public final List<ListeningSerial> listeners;
    private volatile boolean running = true;
    private final Map<Integer, GVDMessage.PortMessageState> gvdStates = new ConcurrentHashMap<>();

    public MessageProcessor(BlockingQueue<MessageWithSource> messageQueue, List<ListeningSerial> listeners) {
        this.messageQueue = messageQueue;
        this.listeners = listeners;
    }


    private static final byte AMTB_MSG_HEADER = (byte) 0xA5;
    private static final byte MSG_HEADER_LEN = (byte) 1;
    private static final byte MSG_AMTB_ID_LEN = (byte) 1;
    private static final byte MSG_LEN_DATA_LEN = (byte) 2;
    private static final byte MSG_COMMAND_LEN = (byte) 1;
    private static final byte MSG_SUBCOMMAND_LEN = (byte) 1;
    private static final byte MSG_CHECKSUM_LEN = (byte) 1;

    final int GVD_SIZE = 4096;
    final int MAG_SIZE = 512;
    final int AMMO_SIZE = 2048;
    final int TOTAL_SIZE = GVD_SIZE + MAG_SIZE + AMMO_SIZE;

    private static final int MIN_MSG_SIZE_ALL = MSG_HEADER_LEN + MSG_AMTB_ID_LEN + MSG_LEN_DATA_LEN
            + MSG_COMMAND_LEN + MSG_SUBCOMMAND_LEN + MSG_CHECKSUM_LEN;

    public static byte getCheckSum(byte[] msg) {

        long result = 0;
        for (int ndx = 0; ndx < msg.length; ndx++) {
            result += (long) msg[ndx];
        }
        return (byte) (result % 256);
    }

    public static byte[] getLen(int len) {

        byte[] result = new byte[2];
        result[0] = (byte) (len & 0xff);
        result[1] = (byte) ((len >> 8) & 0xff);
        return result;
    }

    @Override
    public void run() {
        System.out.println("MessageProcessor (işleme thread) başladı.");

        while (running) {
            try {
                MessageWithSource msgWithSource = messageQueue.take(); // Blocking call
                if (msgWithSource != null) {
                    ListeningSerial sourceListener = msgWithSource.source;
                    byte[] message = msgWithSource.message;

                    // Artık isPacketMine kontrolüne gerek yok, çünkü doğrudan kaynak belli.
                    try {
                        processMessage(sourceListener.getHatNo(), sourceListener, message);
                    } catch (IOException e) {
                        System.err.println("processMessage hatası: " + e.getMessage());
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                stop();
            }
        }
    }

    public void stop() {
        running = false;
    }
    private short readShort(InputStream ins) throws IOException {
        int high = ins.read();
        int low = ins.read();
        if (high == -1 || low == -1) throw new EOFException();
        return (short) ((high << 8) | low);
    }


    private boolean isAmtbIdForSocket(int index, int amtbId) {
        return (index == 1 && amtbId >= 0 && amtbId <= 7)   ||
                (index == 2 && amtbId >= 8 && amtbId <= 15)  ||
                (index == 3 && amtbId >= 16 && amtbId <= 23) ||
                (index == 4 && amtbId >= 24 && amtbId <= 31);
    }
    private short readShortLE(InputStream in) throws IOException {
        int lo = in.read(); // düşük byte önce
        int hi = in.read(); // yüksek byte sonra
        if (lo < 0 || hi < 0) throw new EOFException();
        return (short) ((hi << 8) | lo); // little-endian -> doğru short
    }

    public static void send2KKY(int amtbHat, int amtbID, short fullCmd, OutputStream outs) {

        if (outs == null) {
            System.err.println("ERROR: OutputStream is null. Cannot send to KKY.");
            return;
        }

        try {

            int msgLen = MIN_MSG_SIZE_ALL;
            int dataLen = MSG_COMMAND_LEN + MSG_SUBCOMMAND_LEN;

            ByteBuffer tmp = ByteBuffer.allocate(msgLen);
            tmp.put(AMTB_MSG_HEADER);
            tmp.put((byte) amtbID);
            tmp.put(getLen(dataLen));
            tmp.putShort(fullCmd);
            tmp.put(getCheckSum(tmp.array()));
            outs.write(tmp.array(), 0, tmp.position());
            outs.flush();

            //System.out.println("AMTB'den KKY'ye gonderilen mesaj: " + Hex.encodeHexString(tmp.array())); H.S tarafSndan kapatSldS
        } catch (IOException e) {
            System.out.println("KKY'ye bilgi gonderme islemi gerceklesmedi: " + fullCmd);
        }
    }



    public static void send2KKY(int amtbHat, int amtbID, short fullCmd, byte info, OutputStream outs) {

        try {

            int msgLen  = 1 + MIN_MSG_SIZE_ALL;
            int dataLen = 1 + MSG_COMMAND_LEN + MSG_SUBCOMMAND_LEN;

            ByteBuffer tmp = ByteBuffer.allocate(msgLen);
            tmp.put(AMTB_MSG_HEADER);
            tmp.put((byte) amtbID);
            tmp.put(getLen(dataLen));
            tmp.putShort(fullCmd);
            tmp.put(info);
            tmp.put(getCheckSum(tmp.array()));
            outs.write(tmp.array(), 0, msgLen);
            outs.flush();

            //System.out.println("AMTB'den KKY'ye gonderilen mesaj: " + Hex.encodeHexString(tmp.array()));
        } catch (IOException e) {
            System.out.println("KKY'ye bilgi gonderme islemi gerceklesmedi: " + fullCmd);
            //assertTrue("KKY'ye bilgi gonderme islemi gerceklesmedi: " + fullCmd, false);
        }
    }

    public static void send2KKY(int amtbHat, int amtbID, short fullCmd, byte[] info, OutputStream outs) {

        try {

            int msgLen  = info.length + MIN_MSG_SIZE_ALL;
            int dataLen = info.length + MSG_COMMAND_LEN + MSG_SUBCOMMAND_LEN;

            ByteBuffer tmp = ByteBuffer.allocate(msgLen);
            tmp.put(AMTB_MSG_HEADER);
            tmp.put((byte) amtbID);
            tmp.put(getLen(dataLen));
            //	tmp.putInt(30);
            tmp.putShort(fullCmd);
            tmp.put(info);
            tmp.put(getCheckSum(tmp.array()));
            outs.write(tmp.array(), 0, msgLen);
            outs.flush();
            System.out.println("mesaj gonderiliyooooor");
            //System.out.println("AMTB'den KKY'ye gonderilen mesaj: " + tmp.array());
            //System.out.println("AMTB'den KKY'ye gonderilen mesaj: " + Hex.encodeHexString(tmp.array()));
        } catch (IOException e) {
            System.out.println("KKY'ye bilgi gonderme islemi gerceklesmedi: " + fullCmd);
        }
    }
    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X ", b));
        }
        return sb.toString();
    }

    private void readFully(InputStream in, byte[] buffer, int offset, int length) throws IOException {
        int readSoFar = 0;
        while (readSoFar < length) {
            int n = in.read(buffer, offset + readSoFar, length - readSoFar);
            if (n == -1) throw new IOException("Stream ended unexpectedly");
            readSoFar += n;
        }
    }


    private void processMessage(int index, ListeningSerial soc, byte[] fullPacket) throws IOException {
        ByteArrayInputStream in = new ByteArrayInputStream(fullPacket);

        if (soc.port == null || !soc.port.isOpen()) {
            System.out.println("Serial port " + index + " bagli degil.");
            return;
        }

        // Seril port timeout ayarı (gerekli olmayabilir çünkü burada gerçek stream değil)
        //soc.port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 100, 0);

        // HEADER senkronizasyonu - ByteArrayInputStream olduğu için gerekli değil ama kalabilir
        int b;
        do {
            b = in.read();
            if (b == -1) throw new IOException("Stream sonlandi!");
        } while ((byte) b != AMTB_MSG_HEADER);
        byte HEADER1 = (byte) b;

        // DestID
        int destIDRaw = in.read();
        if (destIDRaw == -1) throw new IOException("Stream sonlandi! (destinationID)");
        byte destinationID = (byte) destIDRaw;

        short msgLen = readShortLE(in);
        short msgId  = readShort(in);

        if (msgLen < 2) throw new IOException("Geçersiz mesaj uzunluğu: " + msgLen);

        int payloadLen = msgLen - 2; // checksum hariç
        byte[] payloadd = new byte[payloadLen];
        if (payloadLen > 0) {
            readFully(in, payloadd, 0, payloadLen);
        }

        int checksumRaw = in.read();
        if (checksumRaw == -1) throw new IOException("Stream sonlandi! (checksum)");
        byte checksum = (byte) checksumRaw;



        List<Integer> aktifAmtbIdlist = AMTB_Sim_Controller.getAktifAmtbIdList();

/*            switch (msgId){
                case AMTBMsgIDConstants.AMTB_STATE_GET_ID:
                    System.out.println("switch case AMTB_STATE_GET");
                    break;

                default:
                    System.err.println("Undefined amtb MSG id !!");
                    break;
}*/
        switch(msgId){
            case AMTBMsgIDConstants.AMTB_STATE_GET_ID :

            for (int i = 0; i < aktifAmtbIdlist.size(); ++i) {
                System.out.println("Socket " + index + " aktif amtb[" + i + "] : " + aktifAmtbIdlist.get(i));

                int amtbId = aktifAmtbIdlist.get(i);

                if (isAmtbIdForSocket(index, amtbId)) {
                    //AMTBState data = AMTB_Sim_Controller.AMTBStateList.get(i);
                    AMTBState data = AMTB_Sim_Controller.AMTBStateList.get(amtbId);

                    System.out.println(" i : " + i + " , index : " + index);

                        /*
                        //MMT 10.07.2025; Bilgiler byte - byte isteniyorsa bu tarz gonderilebilir.
                        ByteBuffer buf = ByteBuffer.allocate(21);
                        buf.put((byte) data.getAmtbMode().getValue());
                        buf.put((byte) data.getJettisonState().getValue());
                        buf.put((byte) data.getArmedState().getValue());
                        buf.put((byte) data.getIsArmed().getValue());
                        buf.put((byte) data.getWowState().getValue());
                        buf.put((byte) data.getDiscreteTest().getValue());
                        buf.put((byte) data.getMdfState().getValue());
                        buf.put((byte) data.getMdfLoadingState().getValue());
                        buf.put((byte) data.getMagCoverState().getValue());
                        buf.put((byte) data.getIbit().getValue());
                        buf.put((byte) data.getFailState().getValue());
                        buf.put((byte) data.getNewFail().getValue());
                        buf.put((byte) data.getSqbPower().getValue());
                        buf.put((byte) data.getRtc().getValue());
                        buf.put((byte) data.getStcConnected().getValue());
                        buf.put((byte) data.getBlankIR().getValue());
                        buf.put((byte) data.getBlankRF().getValue());
                        buf.put((byte) data.getIsAMTB6x5().getValue());
                        buf.put((byte) data.getMisfire().getValue());
                        buf.put((byte) data.getNewMisfire().getValue());
                        buf.put((byte) data.getIsFiringActive().getValue());
                        */

                    //MMT 10.07.2025; Bilgiler bit seviyesinde gonderilmek isteniyorsa bu tarz gonderilebilir.
                    int totalAmtbStateStructByteCount = 4;
                    ByteBuffer buf = ByteBuffer.allocate(totalAmtbStateStructByteCount);
                    byte xByte = 0;

                    int amtbMode      = data.getAmtbMode().getValue();
                    int jettisonState = data.getJettisonState().getValue();
                    int armed         = data.getArmedState().getValue();
                    int isArmed       = data.getIsArmed().getValue();
                    int woW           = data.getWowState().getValue();

                    xByte |= (byte) (((amtbMode      & 0x07) << 0) & 0xFF); // bit 0-2
                    xByte |= (byte) (((jettisonState & 0x03) << 3) & 0xFF); // bit 3-4
                    xByte |= (byte) (((armed         & 0x01) << 5) & 0xFF); // bit 5
                    xByte |= (byte) (((isArmed       & 0x01) << 6) & 0xFF);  // bit 6
                    xByte |= (byte) (((woW           & 0x01) << 7) & 0xFF);  // bit 7

                    // MMT 06.08.2025 : Bit degerleri kontrolu icin eklendi
                    //System.out.println("amtbMode : " + amtbMode + " , jettisonState : " + jettisonState + " , armed : " + armed
                    //        + " , isArmed : " + isArmed + " , wow : " + woW + " , xByte : " + xByte);

                    buf.put(0, xByte);

                    byte yByte = 0;

                    int discreteTest    = data.getDiscreteTest().getValue();
                    int mdfState        = data.getMdfState().getValue();
                    int mdfLoadingState = data.getMdfLoadingState().getValue();
                    int magCoverState   = data.getMagCoverState().getValue();
                    int ibit            = data.getIbit().getValue();
                    int failState       = data.getFailState().getValue();
                    int newFail         = data.getNewFail().getValue();
                    int sqbPower        = data.getSqbPower().getValue();

                    yByte |= (byte) (((discreteTest    & 0x01) << 0) & 0xFF);  // bit 0
                    yByte |= (byte) (((mdfState        & 0x01) << 1) & 0xFF);  // bit 1
                    yByte |= (byte) (((mdfLoadingState & 0x01) << 2) & 0xFF);  // bit 2
                    yByte |= (byte) (((magCoverState   & 0x01) << 3) & 0xFF);  // bit 3
                    yByte |= (byte) (((ibit            & 0x01) << 4) & 0xFF);  // bit 4
                    yByte |= (byte) (((failState       & 0x01) << 5) & 0xFF);  // bit 5
                    yByte |= (byte) (((newFail         & 0x01) << 6) & 0xFF);  // bit 6
                    yByte |= (byte) (((sqbPower        & 0x01) << 7) & 0xFF);  // bit 7

                    // MMT 06.08.2025 : Bit degerleri kontrolu icin eklendi
                    //System.out.println("discreteTest : " + discreteTest + " , mdfState : " + mdfState + " , mdfLoadingState : " + mdfLoadingState
                    //        + " , magCoverState : " + magCoverState + " , ibit : " + ibit + " , failState : " + failState +
                    //        ", newFail : " + newFail + " , sqbPower : " + sqbPower + " , yByte : " + yByte);

                    buf.put(1, yByte);

                    byte zByte = 0;

                    int rtc            = data.getRtc().getValue();
                    int stcConnected   = data.getStcConnected().getValue();
                    int blankIR        = data.getBlankIR().getValue();
                    int blankRF        = data.getBlankRF().getValue();
                    int isAMTB6x5      = data.getIsAMTB6x5().getValue();
                    int misFire        = data.getMisfire().getValue();
                    int newMisfire     = data.getNewMisfire().getValue();
                    int isFiringActive = data.getIsFiringActive().getValue();

                    zByte |= (byte) (((rtc            & 0x01) << 0) & 0xFF);  // bit 0
                    zByte |= (byte) (((stcConnected   & 0x01) << 1) & 0xFF);  // bit 1
                    zByte |= (byte) (((blankIR        & 0x01) << 2) & 0xFF);  // bit 2
                    zByte |= (byte) (((blankRF        & 0x01) << 3) & 0xFF);  // bit 3
                    zByte |= (byte) (((isAMTB6x5      & 0x01) << 4) & 0xFF);  // bit 4
                    zByte |= (byte) (((misFire        & 0x01) << 5) & 0xFF);  // bit 5
                    zByte |= (byte) (((newMisfire     & 0x01) << 6) & 0xFF);  // bit 6
                    zByte |= (byte) (((isFiringActive & 0x01) << 7) & 0xFF);  // bit 7

                    // MMT 06.08.2025 : Bit degerleri kontrolu icin eklendi
                    //System.out.println("rtc : " + rtc + " , stcConnected : " + stcConnected + " , blankIR : " + blankIR
                    //        + " , blankRF : " + blankRF + " , isAMTB6x5 : " + isAMTB6x5 + " , misFire : " + misFire +
                    //        ", newMisfire : " + newMisfire + " , isFiringActive : " + isFiringActive + " , zByte : " + zByte);

                    buf.put(2, zByte);

                    byte reservedByte = 0;
                    buf.put(3, reservedByte);
/*                        System.out.println("xByte        : " + xByte);
                        System.out.println("yByte        : " + yByte);
                        System.out.println("zByte        : " + zByte);
                        System.out.println("reservedByte : " + reservedByte);*/
                    //send2KKY(soc.getPort(), aktifAmtbIdlist.get(i), AMTBMsgIDConstants.AMTB_STATE_GET_ID, buf.array(), outs);
                    send2KKY(soc.hatNo-1, 0, AMTBMsgIDConstants.AMTB_STATE_GET_ID, buf.array(), soc.outputStream);

                    System.out.println("Socket " + soc.hatNo + " , id(AMTBState) : " + AMTBMsgIDConstants.AMTB_STATE_GET_ID + " mesaj gönderildi."
                            + " aktif amtb[" + i + "] : " + aktifAmtbIdlist.get(i));

                } else {
                    System.out.println("Bu hatta Uygun AMTB Secilmedigi icin mesaj gonderilmedi(AmtbStateGet). Socket " + soc.hatNo + " PORT : " + String.valueOf(soc.hatNo-1));
                }
            }
            break;

            case AMTBMsgIDConstants.AMTB_STATE_SET_ID :


            for (int i = 0; i < aktifAmtbIdlist.size(); ++i) {

                int amtbId = aktifAmtbIdlist.get(i);

                if (isAmtbIdForSocket(index, amtbId)) {
                    System.out.println("AMTBState Change Message start.");
                    //DataInputStream dataIn = new DataInputStream(soc.inputStream);
                    ByteArrayInputStream payloadStream = new ByteArrayInputStream(fullPacket, 6, fullPacket.length - 7);
                    String amtbArmState = AmtbFunctions.getAmtbStateChangeStruct_t(payloadStream);


                    AMTBState data = AMTB_Sim_Controller.AMTBStateList.get(amtbId);

                    // IA 07.08.2025 ;
                    //KKY tarafindan gelen state change mesaji sonrasi gönderilen datayi gelene uygun olarak degistirmek icin asagidaki komutlari yazdik

                    boolean amtbArmStateFlag = amtbArmState.equals("DISARM") ? false : true;

                    if(amtbArmStateFlag){
                        data.setWowState(AMTBState.WOWState.HAVADA);
                        data.setAmtbMode(AMTBState.AmtbMode.AMTB_MODE_ALIVE);
                        data.setIsArmed(AMTBState.IsArmed.ARM);
                        data.setArmedState(AMTBState.ArmedState.ARM);
                    }

                    System.out.println(amtbArmState);
                    System.out.println("AMTBState Change end.");

                    //send2KKY(soc.getPort(), aktifAmtbIdlist.get(i), AMTBMsgIDConstants.AMTB_STATE_SET_ID, outs);
                    send2KKY(soc.hatNo-1, 0, AMTBMsgIDConstants.AMTB_STATE_SET_ID, soc.outputStream);

                    System.out.println("Socket " + index + " , id(AMTBState Change) : " + AMTBMsgIDConstants.AMTB_STATE_SET_ID + " mesaj gönderildi."
                            + "aktif amtb[" + i + "] : " + aktifAmtbIdlist.get(i));
                } else {
                    System.out.println("Bu hatta Uygun AMTB Secilmedigi icin mesaj gonderilmedi(StateSet). Socket " + index + " PORT : " + String.valueOf(soc.hatNo-1));
                }


            }
        break;

            case AMTBMsgIDConstants.AMTB_FIRMWARE_VERSION_GET_ID :

            for (int i = 0; i < aktifAmtbIdlist.size(); ++i) {
                System.out.println("Socket " + soc.hatNo + " aktif amtb[" + i + "] : " + aktifAmtbIdlist.get(i));

                int amtbId = aktifAmtbIdlist.get(i);

                if (isAmtbIdForSocket(soc.hatNo, amtbId)) {

                    int totalFirmwareMessageStructByteCount = 48;
                    ByteBuffer bufFirmware = ByteBuffer.allocate(totalFirmwareMessageStructByteCount);

                    FirmwareMessage firmwareMessageData = FirmwareMessageList.get(amtbNo);

 /*                       int buildNumber = 4;
                        short major = 1;
                        short minor = 2;

                        byte[] configVersion = new byte[16];

                        bufFirmware.putInt(buildNumber);
                        bufFirmware.putShort(major);
                        bufFirmware.putShort(minor);

                        bufFirmware.put(configVersion);


                        bufFirmware.putInt(buildNumber);
                        bufFirmware.putShort(major);
                        bufFirmware.putShort(minor);

                        bufFirmware.put(configVersion);*/



                    //send2KKY(soc.getPort(), aktifAmtbIdlist.get(i), AMTBMsgIDConstants.AMTB_FIRMWARE_VERSION_GET_ID, bufFirmware.array(), outs);
                    send2KKY(soc.hatNo-1, 0, AMTBMsgIDConstants.AMTB_FIRMWARE_VERSION_GET_ID, AmtbFunctions.getFirmwareAmtbVersionStruct(), soc.outputStream);


                    //switch ()firmwareMessageData.getConfiguration()


/*                        ByteBuffer bufVersion = ByteBuffer.allocate(8);
                        bufVersion.order(ByteOrder.LITTLE_ENDIAN);


                        String hexString = "76-5F-30-00-00-00-00-00-00-00-00-00-00-00-00-00";
                        String[] hexParts = hexString.split("-");
                        byte[] configurationBytes = new byte[hexParts.length];
                        for (int j = 0; j < hexParts.length; j++) {
                            configurationBytes[i] = (byte) Integer.parseInt(hexParts[i], 16);
                        }


                        bufVersion.putInt(buildNumber); // 4 byte
                        bufVersion.putShort(major);     // 2 byte
                        bufVersion.putShort(minor);     // 2 byte
                        bufVersion.put(configurationBytes); // 16 byte*/

                    System.out.println("Socket " + index + " , id(FIRMWARE) : " + AMTBMsgIDConstants.AMTB_FIRMWARE_VERSION_GET_ID + " mesaj gönderildi."
                            + " aktif amtb[" + i + "] : " + aktifAmtbIdlist.get(i));

                } else {
                    System.out.println("Bu hatta Uygun AMTB Secilmedigi icin mesaj gonderilmedi(FIRMWARE). Socket " + index + " PORT : " + String.valueOf(soc.hatNo-1));
                }

            }
        break;

            case AMTBMsgIDConstants.AMTB_GVD_VERSION_GET_ID :

            for (int i = 0; i < aktifAmtbIdlist.size(); ++i) {

                System.out.println("Socket " + index + " aktif amtb[" + i + "] : " + aktifAmtbIdlist.get(i));

                int amtbId = aktifAmtbIdlist.get(i);

                if (isAmtbIdForSocket(index, amtbId)) {

                    int totalGvdVersionStructByteCount = 34;
                    ByteBuffer bufGvdVersion = ByteBuffer.allocate(totalGvdVersionStructByteCount);

                   /* int gvdUniqueIdDummy = 0x47564453;

                    bufGvdVersion.putInt(gvdUniqueIdDummy);*/
                    byte[] data = new byte[] {
                            (byte)0x47,(byte)0x56,(byte)0x44,(byte)0x53,
                            (byte) 0x55, (byte) 0x54,
                            (byte) 0x30, (byte) 0x33, (byte) 0x33, (byte) 0x00,
                            (byte) 0x00, (byte) 0x00, (byte) 0x31, (byte) 0x37,
                            (byte) 0x2E, (byte) 0x30, (byte) 0x37, (byte) 0x2E,
                            (byte) 0x32, (byte) 0x30, (byte) 0x32, (byte) 0x35,
                            (byte) 0x20, (byte) 0x31, (byte) 0x33, (byte) 0x3A,
                            (byte) 0x35, (byte) 0x37, (byte) 0x3A, (byte) 0x31,
                            (byte) 0x32, (byte) 0x00, (byte) 0x00

                    };

                    bufGvdVersion.position(0);
                    bufGvdVersion.put(data);

                    //byte[] gvdId = new byte[12];

                    /*for(int j = 0 ; j < 3 ; ++j){
                        int offset = 4 * j;
                        gvdId[offset] = 73;
                        gvdId[offset + 1] = 82;
                        gvdId[offset + 2] = 69;
                        gvdId[offset + 3] = 77;
                    }*/


                    /*byte[] gvdId = new byte[] {
                            0x55,0x54,0x30,0x33,0x33,0x00,0x00,0x00,0x31,0x37,0x2E,0x30
                    };

                    byte[] gvdProductionData = new byte[] {
                            0x32, 0x33, 0x2E, 0x30, 0x39, 0x2E, 0x32, 0x30,
                            0x32, 0x35, 0x20, 0x30, 0x39, 0x3A, 0x34, 0x30,
                            0x3A, 0x31, 0x35
                    };
                    byte gvdVersionHi=0x00;
                    byte gvdVerisonLo=0x00;*/

                /*    bufGvdVersion.position(4);
                    bufGvdVersion.put(gvdId);
                    bufGvdVersion.position(16);
                    bufGvdVersion.put(gvdProductionData);
                    bufGvdVersion.put(35,gvdVersionHi);
                    bufGvdVersion.put(36,gvdVerisonLo);*/


                    if (isAmtbIdForSocket(1,amtbId)){
                        bufGvdVersion.put(33,(byte)0x00);
                        send2KKY(soc.hatNo-1, 0, AMTBMsgIDConstants.AMTB_GVD_VERSION_GET_ID, bufGvdVersion.array(), soc.outputStream);
                    }
                    else if (isAmtbIdForSocket(2,amtbId)){
                        bufGvdVersion.put(33,(byte)0x01);
                        send2KKY(soc.hatNo-1, 0, AMTBMsgIDConstants.AMTB_GVD_VERSION_GET_ID, bufGvdVersion.array(), soc.outputStream);
                    }
                    else if (isAmtbIdForSocket(3,amtbId)){
                        bufGvdVersion.put(33,(byte)0x02);
                        send2KKY(soc.hatNo-1, 0, AMTBMsgIDConstants.AMTB_GVD_VERSION_GET_ID, bufGvdVersion.array(), soc.outputStream);
                    }
                    else if (isAmtbIdForSocket(4,amtbId)){
                        bufGvdVersion.put(33,(byte)0x03);
                        send2KKY(soc.hatNo-1, 0, AMTBMsgIDConstants.AMTB_GVD_VERSION_GET_ID, bufGvdVersion.array(), soc.outputStream);
                    }
                    //send2KKY(soc.hatNo-1, 0, AMTBMsgIDConstants.AMTB_GVD_VERSION_GET_ID, bufGvdVersion.array(), soc.outputStream);
                    else {System.err.println("amtbID hatalı"); return ;}

                    System.out.println("Socket " + index + " , id(GVD) : " + AMTBMsgIDConstants.AMTB_GVD_VERSION_GET_ID + " mesaj gönderildi."
                            + " aktif amtb[" + i + "] : " + aktifAmtbIdlist.get(i));

                } else {
                    System.out.println("Bu hatta Uygun AMTB Secilmedigi icin mesaj gonderilmedi(GVD). Socket " + index + " PORT : " + String.valueOf(soc.hatNo-1));
                }

            }

        break;

            case AMTBMsgIDConstants.AMTB_BIT_START_ID :

            for (int i = 0; i < aktifAmtbIdlist.size(); ++i) {

                int amtbId = aktifAmtbIdlist.get(i);

                if (isAmtbIdForSocket(index, amtbId)) {
                    System.out.println("BIT START ID Message start.");

                    //send2KKY(soc.getPort(), aktifAmtbIdlist.get(i), AMTBMsgIDConstants.AMTB_STATE_SET_ID, outs);
                    send2KKY(soc.hatNo-1, 0, AMTBMsgIDConstants.AMTB_BIT_START_ID,soc.outputStream);

                    System.out.println("BIT START ID end.");


                    System.out.println("Socket " + index + " , id(BIT START ID) : " + AMTBMsgIDConstants.AMTB_BIT_START_ID + " mesaj gönderildi."
                            + "aktif amtb[" + i + "] : " + aktifAmtbIdlist.get(i));
                } else {
                    System.out.println("Bu hatta Uygun AMTB Secilmedigi icin mesaj gonderilmedi(BIT START ID). Socket " + index + " PORT : " + String.valueOf(soc.hatNo-1));
                }
            }
        break;
            case AMTBMsgIDConstants.AMTB_GVD_UPDATE_START_ID :

                for (int i = 0; i < aktifAmtbIdlist.size(); ++i) {

                    System.out.println("Socket " + index + " aktif amtb[" + i + "] : " + aktifAmtbIdlist.get(i));

                    int amtbId = aktifAmtbIdlist.get(i);

                    if (isAmtbIdForSocket(index, amtbId)) {


                        send2KKY(soc.hatNo-1, 0, AMTBMsgIDConstants.AMTB_GVD_UPDATE_START_ID, soc.outputStream);

                        System.out.println("Socket " + index + " , id(GVD) : " + AMTBMsgIDConstants.AMTB_GVD_UPDATE_START_ID + " mesaj gönderildi."
                                + " aktif amtb[" + i + "] : " + aktifAmtbIdlist.get(i));

                    } else {
                        System.out.println("Bu hatta Uygun AMTB Secilmedigi icin mesaj gonderilmedi(GVD). Socket " + index + " PORT : " + index);
                    }

                }
                break;

            case AMTBMsgIDConstants.AMTB_GVD_UPDATE_DATA_ID: {
                int hatNo = index;
                int amtbIdFromPacket = fullPacket[1] & 0xFF; // Header'dan AMTB ID'yi oku

                System.out.printf("📨 Gelen GVD paketi - Hat: %d, AMTB ID: %d%n", hatNo, amtbIdFromPacket);

                int headerSize = 10;
                int checksumSize = 1;
                int payloadLength = fullPacket.length - headerSize - checksumSize;

                for (int amtbId : aktifAmtbIdlist) {
                    if (isAmtbIdForSocket(hatNo, amtbId)) {
                        if ((amtbId % 8) == amtbIdFromPacket) {

                            // 🎯 DEĞİŞEN KISIM: Her AMTB için ayrı state
                            GVDMessage.PortMessageState state = GVDMessage.getOrCreateState(hatNo, amtbIdFromPacket);

                /*System.out.printf("🔄 Hat %d - AMTB %d - Before: parts=%d/%d, totalBytes=%d, completed=%d%n",
                        hatNo, amtbIdFromPacket, state.getPartCount(), GVDMessage.EXPECTED_PARTS,
                        state.getBufferSize(), state.getCompletedMessagesCount());*/

                            // Parça ekle
                            state.addPart(fullPacket, headerSize, payloadLength);

                            System.out.printf("🔄 Hat %d - AMTB %d - After: parts=%d/%d, totalBytes=%d, completed=%d%n",
                                    hatNo, amtbIdFromPacket, state.getPartCount(), GVDMessage.EXPECTED_PARTS,
                                    state.getBufferSize(), state.getCompletedMessagesCount());

                            // KKY gönderimi
                            send2KKY(soc.hatNo - 1, amtbId % 8,
                                    AMTBMsgIDConstants.AMTB_GVD_UPDATE_DATA_ID, (byte)0x00,
                                    soc.outputStream);
                            System.out.println("📤 GVD request sent to AMTB: " + amtbId);

                            // 🎯 DEĞİŞEN KISIM: Her AMTB için ayrı kontrol
                            if (state.getCompletedMessagesCount() > 0) {
                                System.out.printf("🎯 Processing completed GVD messages for Hat %d - AMTB %d...%n",
                                        hatNo, amtbIdFromPacket);
                                GVDMessage.parseCompletedMessages(state, GVD_SIZE, MAG_SIZE, AMMO_SIZE);
                            }


                        }

                    }
                }
                break;
            }

            case AMTBMsgIDConstants.AMTB_GVD_UPDATE_END_ID :

                for (int i = 0; i < aktifAmtbIdlist.size(); ++i) {

                    System.out.println("Socket " + index + " aktif amtb[" + i + "] : " + aktifAmtbIdlist.get(i));

                    int amtbId = aktifAmtbIdlist.get(i);

                    if (isAmtbIdForSocket(index, amtbId)) {


                        send2KKY(soc.hatNo-1, 0, AMTBMsgIDConstants.AMTB_GVD_UPDATE_END_ID, soc.outputStream);

                        System.out.println("Socket " + index + " , id(GVD) : " + AMTBMsgIDConstants.AMTB_GVD_UPDATE_END_ID + " mesaj gönderildi."
                                + " aktif amtb[" + i + "] : " + aktifAmtbIdlist.get(i));

                    } else {
                        System.out.println("Bu hatta Uygun AMTB Secilmedigi icin mesaj gonderilmedi(GVD End). Socket " + index + " PORT : " + index);
                    }

                }
            break;

            case AMTBMsgIDConstants.AMTB_BIT_GET_RESULT_ID  :

            for (int i = 0; i < aktifAmtbIdlist.size(); ++i) {

                int amtbId = aktifAmtbIdlist.get(i);

                if (isAmtbIdForSocket(index, amtbId)) {

                    int totalBITMessageStructByteCount = 9;
                    ByteBuffer bufBIT = ByteBuffer.allocate(totalBITMessageStructByteCount);
                    ByteArrayInputStream payloadStream3 = new ByteArrayInputStream(fullPacket, 6, fullPacket.length - 7);
                    byte amtbBit_e = (byte) payloadStream3.read();

                    bufBIT.put(0, amtbBit_e);

                    byte xByte = 0;

                    //BITResult bitResultData = BITResultList.get(amtbNo);
                    BITResult bitResultData = BITResultList.get(amtbId);

                    int eeprom      = bitResultData.getEeprom().getValue();
                    int mybcomm     = bitResultData.getMybComm().getValue();
                    int scanVoltage = bitResultData.getScanVoltage().getValue();
                    int smartcomm1  = bitResultData.getSmartComm1().getValue();


                    xByte |= (byte) (((eeprom      & 0x03) << 0) & 0xFF);  // bit 0-1
                    xByte |= (byte) (((mybcomm     & 0x03) << 2) & 0xFF);  // bit 2-3
                    xByte |= (byte) (((scanVoltage & 0x03) << 4) & 0xFF);  // bit 4-5
                    xByte |= (byte) (((smartcomm1  & 0x03) << 6) & 0xFF);  // bit 6-7

                    // MMT 06.08.2025 : Bit degerleri kontrolu icin eklendi
                    //System.out.println("eeprom : " + eeprom + " , mybcomm : " + mybcomm + " , scanVoltage : " + scanVoltage
                    //        + " , smartcomm1 : " + smartcomm1 + " , xByte : " + xByte);

                    bufBIT.put(1, xByte);

                    byte yByte = 0;

                    int smartcomm2  = bitResultData.getSmartComm2().getValue();
                    int sqbcurrent1 = bitResultData.getSqbCurrent1().getValue();
                    int sqbcurrent2 = bitResultData.getSqbCurrent2().getValue();
                    int sqbvoltage1 = bitResultData.getSqbVoltage1().getValue();

                    yByte |= (byte) (((smartcomm2  & 0x03) << 0) & 0xFF);  // bit 0-2
                    yByte |= (byte) (((sqbcurrent1 & 0x03) << 2) & 0xFF);  // bit 3-4
                    yByte |= (byte) (((sqbcurrent2 & 0x03) << 4) & 0xFF);  // bit 5 6
                    yByte |= (byte) (((sqbvoltage1 & 0x03) << 6) & 0xFF);  // bit 7 8

                    // MMT 06.08.2025 : Bit degerleri kontrolu icin eklendi
                    //System.out.println("smartcomm2 : " + smartcomm2 + " , sqbcurrent1 : " + sqbcurrent1 + " , sqbcurrent2 : " + sqbcurrent2
                    //        + " , sqbvoltage1 : " + sqbvoltage1 + " , yByte : " + yByte);

                    bufBIT.put(2, yByte);

                    byte zByte = 0;

                    int sqbvoltage2   = bitResultData.getSqbVoltage2().getValue();
                    int sqbvoltagein  = bitResultData.getsqbVoltageIn().getValue();
                    int sqbvoltagearm = bitResultData.getSqbVoltageArm().getValue();
                    int loadch1       = bitResultData.getLoadCh1().getValue();

                    zByte |= (byte) (((sqbvoltage2   & 0x03) << 0) & 0xFF);  // bit 0-2
                    zByte |= (byte) (((sqbvoltagein  & 0x03) << 2) & 0xFF);  // bit 3-4
                    zByte |= (byte) (((sqbvoltagearm & 0x03) << 4) & 0xFF);  // bit 5 6
                    zByte |= (byte) (((loadch1       & 0x03) << 6) & 0xFF);  // bit 7 8

                    // MMT 06.08.2025 : Bit degerleri kontrolu icin eklendi
                    //System.out.println("sqbvoltage2 : " + sqbvoltage2 + " , sqbvoltagein : " + sqbvoltagein + " , sqbvoltagearm : " + sqbvoltagearm
                    //        + " , loadch1 : " + loadch1 + " , zByte : " + zByte);

                    bufBIT.put(3, zByte);

                    byte tByte = 0;

                    int loadch2         = bitResultData.getLoadCh2().getValue();
                    int voltageleakch1  = bitResultData.getVoltageLeakCh1().getValue();
                    int voltageleakch2  = bitResultData.getVoltageLeakCh2().getValue();
                    int currentabovech1 = bitResultData.getCurrentAboveCh1().getValue();

                    tByte |= (byte) (((loadch2         & 0x03) << 0) & 0xFF);  // bit 0-1
                    tByte |= (byte) (((voltageleakch1  & 0x03) << 2) & 0xFF);  // bit 2-3
                    tByte |= (byte) (((voltageleakch2  & 0x03) << 4) & 0xFF);  // bit 4-5
                    tByte |= (byte) (((currentabovech1 & 0x03) << 6) & 0xFF);  // bit 6-7

                    // MMT 06.08.2025 : Bit degerleri kontrolu icin eklendi
                    //System.out.println("loadch2 : " + loadch2 + " , voltageleakch1 : " + voltageleakch1 + " , voltageleakch2 : " + voltageleakch2
                    //        + " , currentabovech1 : " + currentabovech1 + " , tByte : " + tByte);

                    bufBIT.put(4, tByte);

                    byte kByte = 0;

                    int currentabovech2 = bitResultData.getCurrentAboveCh2().getValue();
                    int currentbelowch1 = bitResultData.getCurrentBelowCh1().getValue();
                    int currentbelowch2 = bitResultData.getCurrentBelowCh2().getValue();
                    int dischargech1    = bitResultData.getDischargeCh1().getValue();

                    kByte |= (byte) (((currentabovech2 & 0x03) << 0) & 0xFF);  // bit 0-1
                    kByte |= (byte) (((currentbelowch1 & 0x03) << 2) & 0xFF);  // bit 2-3
                    kByte |= (byte) (((currentbelowch2 & 0x03) << 4) & 0xFF);  // bit 4-5
                    kByte |= (byte) (((dischargech1    & 0x03) << 6) & 0xFF);  // bit 6-7

                    // MMT 06.08.2025 : Bit degerleri kontrolu icin eklendi
                    //System.out.println("currentabovech2 : " + currentabovech2 + " , currentbelowch1 : " + currentbelowch1 + " , currentbelowch2 : " + currentbelowch2
                    //        + " , dischargech1 : " + dischargech1 + " , kByte : " + kByte);

                    bufBIT.put(5, kByte);

                    byte mByte = 0;

                    int dischargech2   = bitResultData.getDischargeCh2().getValue();
                    int currentleakch1 = bitResultData.getCurrentLeakCh1().getValue();
                    int currentleakch2 = bitResultData.getCurrentLeakCh2().getValue();
                    int eepromwrite    = bitResultData.getEepromWrite().getValue();

                    mByte |= (byte) (((dischargech2   & 0x03) << 0) & 0xFF);  // bit 0-1
                    mByte |= (byte) (((currentleakch1 & 0x03) << 2) & 0xFF);  // bit 2-3
                    mByte |= (byte) (((currentleakch2 & 0x03) << 4) & 0xFF);  // bit 4-5
                    mByte |= (byte) (((eepromwrite    & 0x03) << 6) & 0xFF);  // bit 6-7

                    // MMT 06.08.2025 : Bit degerleri kontrolu icin eklendi
                    //System.out.println("dischargech2 : " + dischargech2 + " , currentleakch1 : " + currentleakch1 + " , currentleakch2 : " + currentleakch2
                    //        + " , eepromwrite : " + eepromwrite + " , mByte : " + mByte);

                    bufBIT.put(6, mByte);

                    byte dByte = 0;
                    int reserved = 1;

                    int magazinecover = bitResultData.getMagazineCover().getValue();
                    int logbackup     = bitResultData.getLogbackup().getValue();
                    int ram           = bitResultData.getRAM().getValue();

                    // IA 05.08.2025
                        /*
                            KKY-AMTB Mesajlasma Yapisi 29_08_2023 dosyasına gore reserved byte 6 bitten 2 ye dustu
                         dByte |= (byte) (((magazinecover & 0x03) << 0) & 0xFF);  // bit 0-1
                         dByte |= (byte) (((reserved      & 0x3F) << 2) & 0xFF);  // bit 2-7
                         */

                    dByte |= (byte) (((magazinecover  & 0x03) << 0) & 0xFF);  // bit 0-1
                    dByte |= (byte) (((logbackup      & 0x03) << 2) & 0xFF);  // bit 2-3
                    dByte |= (byte) (((ram            & 0x03) << 4) & 0xFF);  // bit 4-5
                    dByte |= (byte) (((reserved       & 0x03) << 6) & 0xFF);  // bit 6-7

                    // MMT 06.08.2025 : Bit degerleri kontrolu icin eklendi
                    //System.out.println("magazinecover : " + magazinecover + " , currentleakch1 : " + currentleakch1 + " , currentleakch2 : " + currentleakch2
                    //        + " , reserved : " + reserved + " , dByte : " + dByte);

                    bufBIT.put(7, dByte);

                    byte reservedByte = 0;
                    bufBIT.put(8, reservedByte);

/*                        System.out.println("amtbBit_e    : " + amtbBit_e);
                        System.out.println("xByte        : " + xByte);
                        System.out.println("yByte        : " + yByte);
                        System.out.println("zByte        : " + zByte);
                        System.out.println("tByte        : " + tByte);
                        System.out.println("kByte        : " + kByte);
                        System.out.println("mByte        : " + mByte);
                        System.out.println("dByte        : " + dByte);
                        System.out.println("reservedByte : " + reservedByte);*/

                    //send2KKY(soc.getPort(), BITResultList.get(i).getAmtbNo(), AMTBMsgIDConstants.AMTB_BIT_GET_RESULT_ID, bufBIT.array(), outs);
                    //send2KKY(soc.getPort(), aktifAmtbIdlist.get(i), AMTBMsgIDConstants.AMTB_BIT_GET_RESULT_ID, bufBIT.array(), outs);
                    send2KKY(soc.hatNo-1, 0, AMTBMsgIDConstants.AMTB_BIT_GET_RESULT_ID, bufBIT.array(), soc.outputStream);

                    System.out.println("Socket " + index + " , id(BIT) : " + AMTBMsgIDConstants.AMTB_STATE_GET_ID + " mesaj gönderildi. "
                            + "aktif amtb[" + i + "] : " + aktifAmtbIdlist.get(i));
                } else {
                    System.out.println("Bu hatta Uygun AMTB Secilmedigi icin mesaj gonderilmedi(BITGet). Socket " + index + " PORT : " + String.valueOf(soc.hatNo-1));
                }
            }
        break;

            case AMTBMsgIDConstants.AMTB_RTC_SET_ID :
            System.out.println("RTC Set Message start.");
            //DataInputStream dis = new DataInputStream(soc.inputStream);
            String date = AmtbFunctions.getRtcStruct(in);


            System.out.println(date);
            System.out.println("RTC Set Message end.");
            for (int i = 0; i < aktifAmtbIdlist.size(); ++i) {

                int amtbId = aktifAmtbIdlist.get(i);

                if (isAmtbIdForSocket(index, amtbId)) {

                    AMTBState data = AMTB_Sim_Controller.AMTBStateList.get(amtbId);

                    if(AmtbFunctions.rtcSetFlag){
                        data.setRtc(AMTBState.RTC.SET_EDILMIS);
                    }

                    //send2KKY(soc.getPort(), aktifAmtbIdlist.get(i), AMTBMsgIDConstants.AMTB_RTC_SET_ID, outs);
                    send2KKY(soc.hatNo-1, 0, AMTBMsgIDConstants.AMTB_RTC_SET_ID,soc.outputStream);
                    System.out.println("Socket " + index + " , id(RTC) : " + AMTBMsgIDConstants.AMTB_RTC_SET_ID + " mesaj gönderildi."
                            + "aktif amtb[" + i + "] : " + aktifAmtbIdlist.get(i));

                } else {
                    System.out.println("Bu hatta Uygun AMTB Secilmedigi icin mesaj gonderilmedi(RTCSet). Socket " + index + " PORT : " + String.valueOf(soc.hatNo-1));
                }
            }
        break;

            case AMTBMsgIDConstants.AMTB_RTC_GET_ID :

            System.out.println("RTC Get Message start.");

            for (int i = 0; i < aktifAmtbIdlist.size(); ++i) {
                int amtbId = aktifAmtbIdlist.get(i);

                if (isAmtbIdForSocket(index, amtbId)) {


                       /*
                        bufRTCStruct.put(AmtbFunctions.getRtcBuffer());
                        send2KKY(soc.getPort(), 0, AMTBMsgIDConstants.AMTB_RTC_GET_ID, bufRTCStruct.array(), outs);
                        */

                    int totalRTCStructByteCount = 6;

                    ByteBuffer bufRTCStruct = ByteBuffer.allocate(totalRTCStructByteCount);
                    byte[] rtcStructByte = AmtbFunctions.getRtcBuffer();

                        /*
                        bufRTCStruct.put(0,rtcStructByte[0]);
                        bufRTCStruct.put(1,rtcStructByte[1]);
                        bufRTCStruct.put(2,rtcStructByte[2]);
                        bufRTCStruct.put(3,rtcStructByte[3]);
                        bufRTCStruct.put(4,rtcStructByte[4]);
                        bufRTCStruct.put(5,rtcStructByte[5]);
                        */

                    bufRTCStruct.put(rtcStructByte);

                        /* for(int a = 0 ; a < 6 ; ++a)
                        {
                            System.out.println("rtc[" + a + "] , : " + rtcStructByte[a]);
                        }*/

                    send2KKY(soc.hatNo-1, 0, AMTBMsgIDConstants.AMTB_RTC_GET_ID, bufRTCStruct.array(), soc.outputStream);
                    System.out.println("Socket " + index + " , id(RTC Get) : " + AMTBMsgIDConstants.AMTB_RTC_GET_ID + " mesaj gönderildi."
                            + " aktif amtb[" + i + "] : " + aktifAmtbIdlist.get(i));

                } else {
                    System.out.println("Bu hatta Uygun AMTB Secilmedigi icin mesaj gonderilmedi(RTC Get). Socket " + index + " PORT : " + String.valueOf(soc.hatNo-1));
                }

            }
        break;

            case AMTBMsgIDConstants.AMTB_BLANKING_SET_ID :
                System.out.println("AMTB BLANKING Set Message start.");
                ByteArrayInputStream payloadStream = new ByteArrayInputStream(fullPacket, 6, fullPacket.length - 7);
                String blankingMessage = AmtbFunctions.getBlankingMessage(payloadStream);

                System.out.println(blankingMessage);
                System.out.println("AMTB BLANKING Set Message end.");
                for (int i = 0; i < aktifAmtbIdlist.size(); ++i) {

                    int amtbId = aktifAmtbIdlist.get(i);

                    if (isAmtbIdForSocket(index, amtbId)) {

                        BlankingMessage blankingdata = AMTBStateController.BlankingMessageList.get(amtbId);

                        if(AmtbFunctions.blankingSetFlag){
                           // blankingdata.setcategory(BlankingMessage.category.);
                        }

                        //send2KKY(soc.getPort(), aktifAmtbIdlist.get(i), AMTBMsgIDConstants.AMTB_RTC_SET_ID, outs);
                        send2KKY(soc.hatNo-1, 0, AMTBMsgIDConstants.AMTB_RTC_SET_ID, soc.outputStream);
                        System.out.println("Socket " + index + " , id(RTC) : " + AMTBMsgIDConstants.AMTB_RTC_SET_ID + " mesaj gönderildi."
                                + "aktif amtb[" + i + "] : " + aktifAmtbIdlist.get(i));

                    } else {
                        System.out.println("Bu hatta Uygun AMTB Secilmedigi icin mesaj gonderilmedi(RTCSet). Socket " + index + " PORT : " + String.valueOf(soc.hatNo-1));
                    }
                }

            break;

            case AMTBMsgIDConstants.AMTB_BLANKING_GET_ID :
            System.out.println("Blanking Set Message start.");

            for (int i = 0; i < aktifAmtbIdlist.size(); ++i) {

                int amtbId = aktifAmtbIdlist.get(i);

                if (isAmtbIdForSocket(index, amtbId)) {

                    int totalBlankingMessageStructByteCount = 3;
                    ByteBuffer bufBlankingMessage = ByteBuffer.allocate(totalBlankingMessageStructByteCount);

                    byte xByte = 0;
                    byte yByte = 0;
                    byte zByte = 0;

                    BlankingMessage blankingMessageData = BlankingMessageList.get(amtbNo);

                    int category = blankingMessageData.getcategory().getValue();
                    int direction = blankingMessageData.getdirection().getValue();
                    int operation = blankingMessageData.getoperation().getValue();


                    xByte |= (byte) ((category & 0xFF));// bit 0-7

                    yByte |= (byte) ((direction & 0xFF)); // bit 0-7

                    zByte |= (byte) ((operation & 0xFF)); // bit 0-7

                    bufBlankingMessage.put(0, xByte);

                    bufBlankingMessage.put(1, yByte);

                    bufBlankingMessage.put(2, zByte);

                    send2KKY(soc.hatNo-1, aktifAmtbIdlist.get(i), AMTBMsgIDConstants.AMTB_BLANKING_GET_ID, bufBlankingMessage.array(), soc.outputStream);
                }
            }
        break;

            case AMTBMsgIDConstants.AMTB_INVENTORY_GET_CATEGORY_TYPE_ID :

            for (int i = 0; i < aktifAmtbIdlist.size(); ++i) {

                int amtbId = aktifAmtbIdlist.get(i);

                if (isAmtbIdForSocket(index, amtbId)) {

                    System.out.println("INV Message_Type and Category Message start.");

                    //AMTBState data = AMTB_Sim_Controller.AMTBStateList.get(amtbId);

                    //MMT 10.07.2025; Bilgiler bit seviyesinde gonderilmek isteniyorsa bu tarz gonderilebilir.
                    int totalMessageInventoryCategoryTypeStructByteCount = 4 + 64; // Category type 4 byte , Payload[64]
                    ByteBuffer bufInventoryMessageTypeAndCategory = ByteBuffer.allocate(totalMessageInventoryCategoryTypeStructByteCount);

                        /*byte ashCount   = 5; // Dummy girildi temsili.
                        byte cat2Count  = 10;
                        byte chaffCount = 12;
                        byte flareCount = 3;
                        */

                    byte ashCount   = 0; // Dummy girildi temsili.
                    byte cat2Count  = 0;
                    byte chaffCount = 0;
                    byte flareCount = 30;

                    bufInventoryMessageTypeAndCategory.put(0, ashCount);
                    bufInventoryMessageTypeAndCategory.put(1, cat2Count);
                    bufInventoryMessageTypeAndCategory.put(2, chaffCount);
                    bufInventoryMessageTypeAndCategory.put(3, flareCount);

                    int payloadSize = 64;
                    byte[] payload = new byte[payloadSize];

                    for(int payloadIndex = 0 ; payloadIndex < payloadSize ; ++payloadIndex){
                        //payload[payloadIndex] = 17;
                        payload[payloadIndex] = 0;
                        if(payloadIndex == 2){
                            payload[payloadIndex] = 30;
                        }
                    }

                    bufInventoryMessageTypeAndCategory.put(4,payload);

                    send2KKY(soc.hatNo-1, 0, AMTBMsgIDConstants.AMTB_INVENTORY_GET_CATEGORY_TYPE_ID, bufInventoryMessageTypeAndCategory.array(), soc.outputStream);

                    System.out.println("Socket " + index + " , id(INV Message_Type and Category) : " + AMTBMsgIDConstants.AMTB_INVENTORY_GET_CATEGORY_TYPE_ID + " mesaj gönderildi."
                            + " aktif amtb[" + i + "] : " + aktifAmtbIdlist.get(i));

                } else {
                    System.out.println("Bu hatta Uygun AMTB Secilmedigi icin mesaj gonderilmedi(INV Message_Type and Category). Socket " + index + " PORT : " + String.valueOf(soc.hatNo-1));
                }
            }

        break;

            case AMTBMsgIDConstants.AMTB_INVENTORY_GET_CELL_ID :
            System.out.println("AMTB Inventory Get Cell Message start.");

            for (int i = 0; i < aktifAmtbIdlist.size(); ++i) {
                int amtbId = aktifAmtbIdlist.get(i);

                if (isAmtbIdForSocket(index, amtbId)) {
                    System.out.println("Socket " + index + " aktif amtb[" + i + "] : " + aktifAmtbIdlist.get(i));
                    //int totalBITMessageStructByteCount = 4;
                    int totalBITMessageStructByteCount = 4 * 30; //Her hucre icin 4 byte.

                    ByteBuffer bufInventory = ByteBuffer.allocate(totalBITMessageStructByteCount);
                    for (int hucreNo = 0; hucreNo < 30; hucreNo++) {

                        int offSet = hucreNo * 4;

                        byte xByte = 0;

                        //System.out.println("ListeningSocket Inventory  thread başladi. Hucre No : " + hucreNo + " offSet : " + offSet);

                        InventoryCellState inventoryCellStatedata = InventoryCellStateList.get(hucreNo);

                        int category       = inventoryCellStatedata.getCategory().getValue();
                        int numberofPieces = inventoryCellStatedata.getNumberofPieces().getValue();
                        int dolulukDurumu  = inventoryCellStatedata.getDolulukDurumu().getValue();
                        int isMisfired     = inventoryCellStatedata.getIsMisfired().getValue();
                        int isMultiple     = inventoryCellStatedata.getIsMultiple().getValue();
                        int isSmart        = inventoryCellStatedata.getIsSmart().getValue();

                        xByte |= (byte) (((category       & 0x03) << 0) & 0xFF);  // bit 0-1
                        xByte |= (byte) (((numberofPieces & 0x03) << 2) & 0xFF);  // bit 2-3
                        xByte |= (byte) (((dolulukDurumu  & 0x01) << 4) & 0xFF);  // bit 4
                        xByte |= (byte) (((isMisfired     & 0x01) << 5) & 0xFF);  // bit 5
                        xByte |= (byte) (((isMultiple     & 0x01) << 6) & 0xFF);  // bit 6
                        xByte |= (byte) (((isSmart        & 0x01) << 7) & 0xFF);  // bit 7

                        // MMT 06.08.2025 : Bit degerleri kontrolu icin eklendi
                        //System.out.println("category : " + category + " , numberofPieces : " + numberofPieces + " , dolulukDurumu : " + dolulukDurumu
                        //        + " , isMisfired : " + isMisfired + " , isMultiple : " + isMultiple + " , isSmart : " + isSmart +
                        //        " , xByte : " + xByte);

                        bufInventory.put(offSet, xByte);

                        byte yByte = 0;

                        int tipBilgisi = inventoryCellStatedata.getTipBilgisi().getValue();
                        int tekCift    = inventoryCellStatedata.getTekCift().getValue();

                        yByte |= (byte) (((tipBilgisi & 0x3F) << 0) & 0xFF);  // bit 0-5
                        yByte |= (byte) (((tekCift    & 0x03) << 6) & 0xFF);  // bit 6-7

                        // MMT 06.08.2025 : Bit degerleri kontrolu icin eklendi
                        //System.out.println("tipBilgisi : " + tipBilgisi + " , tekCift : " + tekCift + " , yByte : " + yByte);

                        bufInventory.put(offSet + 1, yByte);

                        byte zByte = 0;
                        int reserved = 0;

                        int kalanOmurKritiklik = inventoryCellStatedata.getKalanOmurKrtiklikDurumu().getValue();
                        int uyumsuzlukDurumu   = inventoryCellStatedata.getUyumsuzlukDurumu().getValue();


                        zByte |= (byte) (((kalanOmurKritiklik & 0x01) << 0) & 0xFF);  // bit 0
                        zByte |= (byte) (((uyumsuzlukDurumu   & 0x01) << 1) & 0xFF);  // bit 1
                        zByte |= (byte) (((reserved           & 0x3F) << 2) & 0xFF);  // bit 2-7

                        // MMT 06.08.2025 : Bit degerleri kontrolu icin eklendi
                        //System.out.println("kalanOmurKritiklik : " + kalanOmurKritiklik + " , uyumsuzlukDurumu : " + uyumsuzlukDurumu +
                        //        " , reserved : " + reserved + " , zByte : " + zByte);

                        bufInventory.put(offSet + 2, zByte);

                        byte ASHBildirilen = 1;

                        // MMT 06.08.2025 : Bit degerleri kontrolu icin eklendi
                        //System.out.println("ASHBildirilen : " + ASHBildirilen);

                        bufInventory.put(offSet + 3, ASHBildirilen);

/*                            System.out.println("HucreNo[" + hucreNo + "]" + "xByte         : " + xByte);
                            System.out.println("HucreNo[" + hucreNo + "]" + "yByte         : " + yByte);
                            System.out.println("HucreNo[" + hucreNo + "]" + "zByte         : " + zByte);
                            System.out.println("HucreNo[" + hucreNo + "]" + "ASHBildirilen : " + ASHBildirilen);*/

/*                        for(int k = 0; k < 120 ; ++k)
                        {
                            System.out.println("bufInventory.get(ic)(" + k + ") : " + bufInventory.get(k));
                        }*/


                    }
                    System.out.println("Socket " + index + " , id(INVENTORY Get Cell) : " + AMTBMsgIDConstants.AMTB_INVENTORY_GET_CELL_ID + " mesaj gönderildi. "
                            + "aktif amtb[" + i + "] : " + aktifAmtbIdlist.get(i));


/*                    for(int k = 0; k < 120 ; ++k)
                    {
                        System.out.println("bufInventory.get(" + k + ") : " + bufInventory.get(k));
                    }*/

                    //send2KKY(soc.getPort(), aktifAmtbIdlist.get(i), AMTBMsgIDConstants.AMTB_INVENTORY_GET_CELL_ID, bufInventory.array(), outs);
                    send2KKY(soc.hatNo-1, 0, AMTBMsgIDConstants.AMTB_INVENTORY_GET_CELL_ID, bufInventory.array(), soc.outputStream);

                } else {
                    System.out.println("Bu hatta Uygun AMTB Secilmedigi icin mesaj gonderilmedi(InventoryGetCell). Socket " + index + " PORT : " + String.valueOf(soc.hatNo-1));
                }

            }
        break;

            case AMTBMsgIDConstants.AMTB_INVENTORY_GET_MAGAZINE_ID :

            for (int i = 0; i < aktifAmtbIdlist.size(); ++i) {

                int amtbId = aktifAmtbIdlist.get(i);

                if (isAmtbIdForSocket(index, amtbId)) {
                    System.out.println("INV Magazin ID Message start.");

                    //send2KKY(soc.getPort(), aktifAmtbIdlist.get(i), AMTBMsgIDConstants.AMTB_STATE_SET_ID, outs);
                    byte magazinIdData = 1;
                    send2KKY(soc.hatNo-1, 0, AMTBMsgIDConstants.AMTB_INVENTORY_GET_MAGAZINE_ID, magazinIdData,soc.outputStream);

                    System.out.println("INV Magazin ID end.");


                    System.out.println("Socket " + index + " , id(INV Magazin ID) : " + AMTBMsgIDConstants.AMTB_INVENTORY_GET_MAGAZINE_ID + " mesaj gönderildi."
                            + "aktif amtb[" + i + "] : " + aktifAmtbIdlist.get(i));
                } else {
                    System.out.println("Bu hatta Uygun AMTB Secilmedigi icin mesaj gonderilmedi(INV Magazin ID). Socket " + index + " PORT : " + String.valueOf(soc.hatNo-1));
                }
            }
        break;

            case AMTBMsgIDConstants.AMTB_ZEROIZE_START_ID  :
            for (int i = 0; i < aktifAmtbIdlist.size(); ++i) {

                int amtbId = aktifAmtbIdlist.get(i);

                if (isAmtbIdForSocket(index, amtbId)) {
                    System.out.println("Zeroize Message start.");

                    send2KKY(soc.hatNo-1,aktifAmtbIdlist.get(i), AMTBMsgIDConstants.AMTB_ZEROIZE_START_ID, soc.outputStream);
                    System.out.println("Socket " + index + " , id : " + AMTBMsgIDConstants.AMTB_ZEROIZE_START_ID + "mesaj gönderildi. AMTB_ZEROIZE_START");
                } else {
                    System.out.println("Bu hatta Uygun AMTB Secilmedigi icin mesaj gonderilmedi(StateSet). Socket " + index + " PORT : " + String.valueOf(soc.hatNo-1));
                }

            }
        break;

            case AMTBMsgIDConstants.AMTB_ZEROIZE_RESULT_ID :
            System.out.println("ZEROIZE RESULT Message start.");
            for (int i = 0; i < aktifAmtbIdlist.size(); ++i) {

                int amtbId = aktifAmtbIdlist.get(i);

                if (isAmtbIdForSocket(index, amtbId)) {

                    ZeroizeResult zeroizeResultData = ZeroizeResultList.get(amtbNo);

                    byte zeroizeResult = (byte) zeroizeResultData.getResults().getValue();

                    send2KKY(soc.hatNo-1, aktifAmtbIdlist.get(i), AMTBMsgIDConstants.AMTB_ZEROIZE_RESULT_ID, zeroizeResult, soc.outputStream);
                }
            }
        break;

            case AMTBMsgIDConstants.AMTB_ASH_COMMAND_FIRMWARE_UPDATE_START_ID:
            for (int i = 0; i < aktifAmtbIdlist.size(); ++i) {

                int amtbId = aktifAmtbIdlist.get(i);

                if (isAmtbIdForSocket(index, amtbId)) {
                    System.out.println("AMTB_ASH_COMMAND_FIRMWARE_UPDATE_START Message start.");
                    //DataInputStream dis = new DataInputStream(soc.inputStream);
                    ByteArrayInputStream payloadStream1 = new ByteArrayInputStream(fullPacket, 6, fullPacket.length - 7);
                    String amtbAshFirmwareUpdateStart = AmtbFunctions.getASHFirmwareUpdateStart_t(payloadStream1);

                    System.out.println(amtbAshFirmwareUpdateStart);
                    System.out.println("amtbAshFirmwareUpdateStart  end.");

                    send2KKY(soc.hatNo-1, aktifAmtbIdlist.get(i), AMTBMsgIDConstants.AMTB_ASH_COMMAND_FIRMWARE_UPDATE_START_ID, soc.outputStream);
                    System.out.println("Socket " + index + " , id : " + AMTBMsgIDConstants.AMTB_ASH_COMMAND_FIRMWARE_UPDATE_START_ID + "mesaj gönderildi. AMTB_ASH_COMMAND_FIRMWARE_UPDATE_START");
                } else {
                    System.out.println("Bu hatta Uygun AMTB Secilmedigi icin mesaj gonderilmedi(StateSet). Socket " + index + " PORT : " + String.valueOf(soc.hatNo-1));
                }
            }
        break;

            case AMTBMsgIDConstants.AMTB_ASH_COMMAND_FIRMWARE_UPDATE_END_ID :
            for (int i = 0; i < aktifAmtbIdlist.size(); ++i) {

                int amtbId = aktifAmtbIdlist.get(i);

                if (isAmtbIdForSocket(index, amtbId)) {
                    System.out.println("AMTB_ASH_COMMAND_FIRMWARE_UPDATE_END Message start.");
                    //DataInputStream dis = new DataInputStream(soc.inputStream);
                    ByteArrayInputStream payloadStream2 = new ByteArrayInputStream(fullPacket, 6, fullPacket.length - 7);
                    String amtbAshFirmwareUpdateEnd = AmtbFunctions.getASHFirmwareUpdateEnd_t(payloadStream2);

                    System.out.println(amtbAshFirmwareUpdateEnd);
                    System.out.println("amtbAshFirmwareUpdateEnd  end.");

                    send2KKY(soc.hatNo-1, aktifAmtbIdlist.get(i), AMTBMsgIDConstants.AMTB_ASH_COMMAND_FIRMWARE_UPDATE_END_ID, soc.outputStream);
                    System.out.println("Socket " + index + " , id : " + AMTBMsgIDConstants.AMTB_ASH_COMMAND_FIRMWARE_UPDATE_END_ID + "mesaj gönderildi. AMTB_ASH_COMMAND_FIRMWARE_UPDATE_END");
                } else {
                    System.out.println("Bu hatta Uygun AMTB Secilmedigi icin mesaj gonderilmedi(StateSet). Socket " + index + " PORT : " + String.valueOf(soc.hatNo-1));
                }
            }
        break;

            case AMTBMsgIDConstants.AMTB_FIRE_MISFIRE_DATA_ID :

            System.out.println("MISFIRE DATA ID Message start.");

            for (int i = 0; i < aktifAmtbIdlist.size(); ++i) {
                int amtbId = aktifAmtbIdlist.get(i);

                if (isAmtbIdForSocket(index, amtbId)) {
                    int totalMisfireDataMessageStructByteCount = 3 * 30 + 1; //Her hucre icin 3 byte. //Hatali atim sayisi icin 1 byte

                    ByteBuffer bufMisfireData= ByteBuffer.allocate(totalMisfireDataMessageStructByteCount);

                    byte hataliAtimSayisi = 0;

                    bufMisfireData.put(0,hataliAtimSayisi);

                    for (int hucreNo = 0; hucreNo < 30; hucreNo++) {

                        int offSet = hucreNo * 3;

                        byte xByte = 0;
                        //System.out.println("Hatali atim for loop başladi. Hucre No : " + hucreNo + " offSet : " + offSet);

                        int misfireReason = AmtbSubFunctions.A2KMisfireReason(AMTBMessageState.AMTB_MISFIRE_REASON_EARLY);
                        int reservedByte  = 0;

                        xByte |= (byte) (((misfireReason & 0x0F) << 0) & 0xFF);  // bit 0-3
                        xByte |= (byte) (((reservedByte  & 0x0F) << 4) & 0xFF);  // bit 4-7

                        bufMisfireData.put( offSet + 1, xByte);

                        byte yByte = 0;

                        int storeType = 0;

                        bufMisfireData.put(offSet + 2 , yByte);

                        byte zByte = 0;

                        int kalanOmurKritiklik = 1;
                        int uyumsuzlukDurumu   = 1;
                        int reserved           = 1;

                        zByte |= (byte) (((kalanOmurKritiklik & 0x1F) << 0) & 0xFF);  // bit 0-4
                        zByte |= (byte) (((uyumsuzlukDurumu   & 0x03) << 5) & 0xFF);  // bit 5-6
                        zByte |= (byte) (((reserved           & 0x01) << 7) & 0xFF);  // bit 7

                        bufMisfireData.put(offSet + 3, zByte);

                    }

                    send2KKY(soc.hatNo-1, 0, AMTBMsgIDConstants.AMTB_FIRE_MISFIRE_DATA_ID, bufMisfireData.array(), soc.outputStream);
                    System.out.println("Socket " + index + " , id(_MISFIRE) : " + AMTBMsgIDConstants.AMTB_FIRE_MISFIRE_DATA_ID + " mesaj gönderildi."
                            + " aktif amtb[" + i + "] : " + aktifAmtbIdlist.get(i));

                } else {
                    System.out.println("Bu hatta Uygun AMTB Secilmedigi icin mesaj gonderilmedi(_MISFIRE). Socket " + index + " PORT : " + String.valueOf(soc.hatNo-1));
                }

            }
        break;

            case AMTBMsgIDConstants.AMTB_RecordSendCount :
            for (int i = 0; i < aktifAmtbIdlist.size(); ++i) {

                int amtbId = aktifAmtbIdlist.get(i);


            }
        break;
        // MMT : 05.08.2025 added.
            case AMTBMsgIDConstants.AMTB_MESSAGE_COMMAND_WORKINGTIME :

            for (int i = 0; i < aktifAmtbIdlist.size(); ++i) {

                int amtbId = aktifAmtbIdlist.get(i);

                if (isAmtbIdForSocket(index, amtbId)) {
                    System.out.println("WORKING TIME Message start.");

                    //send2KKY(soc.getPort(), aktifAmtbIdlist.get(i), AMTBMsgIDConstants.AMTB_STATE_SET_ID, outs);
                    byte magazinIdData = 1;
                    send2KKY(soc.hatNo-1, 0, AMTBMsgIDConstants.AMTB_MESSAGE_COMMAND_WORKINGTIME, magazinIdData,soc.outputStream);

                    System.out.println("WORKING TIME end.");


                    System.out.println("Socket " + index + " , id(WORKING TIME) : " + AMTBMsgIDConstants.AMTB_MESSAGE_COMMAND_WORKINGTIME + " mesaj gönderildi."
                            + "aktif amtb[" + i + "] : " + aktifAmtbIdlist.get(i));
                } else {
                    System.out.println("Bu hatta Uygun AMTB Secilmedigi icin mesaj gonderilmedi(WORKING TIME). Socket " + index + " PORT : " + String.valueOf(soc.hatNo-1));
                }
            }
        break;

        case AMTBMsgIDConstants.AMTB_SET_KKY_CONFIG :
            System.out.println("AMTB SET KKY CONFIG start.");

            // Payload kısmı zaten fullPacket içindeydi
            // Bu yüzden sadece header (1) + destID (1) + msgLen (2) + msgId (2) = 6 byte atla
            ByteArrayInputStream payloadStream3 = new ByteArrayInputStream(fullPacket, 6, fullPacket.length - 7); // Son byte checksum

            String amtbKKYPlatform_t = AmtbFunctions.setAMTBKKYConfigStruct_t(payloadStream3);

            System.out.println(amtbKKYPlatform_t);
            System.out.println("AMTB SET KKY CONFIG end.");

            // Mesaj gönderme işlemi devam eder
            for (int i = 0; i < aktifAmtbIdlist.size(); ++i) {
                int amtbId = aktifAmtbIdlist.get(i);

                if (isAmtbIdForSocket(soc.hatNo, amtbId)) {
                    send2KKY(soc.hatNo - 1, 0, AMTBMsgIDConstants.AMTB_SET_KKY_CONFIG, soc.outputStream);
                    System.out.println("Socket " + index + " , id : " + AMTBMsgIDConstants.AMTB_SET_KKY_CONFIG +
                            " mesaj gönderildi. SET KKY CONFIG . amtbNo : " + amtbId);
                } else {
                    System.out.println("Bu hatta Uygun AMTB Secilmedigi icin mesaj gonderilmedi(SET KKY CONFIG). Socket " +
                            index + " PORT : " + (soc.hatNo - 1));
                }
            }
         break;

            case AMTBMsgIDConstants.AMTB_MESSAGE_COMMAND_ALIVE:
                System.out.println("AMTB MESSAGE COMMAND ALIVE  start");


                for (int i = 0; i < aktifAmtbIdlist.size(); ++i) {
                    int amtbId = aktifAmtbIdlist.get(i);

                    if (isAmtbIdForSocket(soc.hatNo, amtbId)) {

                        AMTBState data = AMTB_Sim_Controller.getAMTBStateList().get(amtbId);
                        int amtbMode =  data.getAmtbMode().getValue();

                        if(amtbMode != 2){
                            data.setAmtbMode(AMTBState.AmtbMode.AMTB_MODE_ALIVE);
                            data.setArmedState(AMTBState.ArmedState.ARM);

                        }

                        send2KKY(soc.hatNo - 1, 0, AMTBMsgIDConstants.AMTB_MESSAGE_COMMAND_ALIVE, soc.outputStream);
                        System.out.println("Socket " + index + " , id : " + AMTBMsgIDConstants.AMTB_MESSAGE_COMMAND_ALIVE +
                                " mesaj gönderildi. ALIVE Message gönderildi. amtbNo : " + amtbId);
                    } else {
                        System.out.println("Bu hatta Uygun AMTB Secilmedigi icin mesaj gonderilmedi(Alive Message ). Socket " +
                                index + " PORT : " + (soc.hatNo - 1));
                    }
                }

            case AMTBMsgIDConstants.AMTB_DISCRETE_SET_OUTPUTS_ID  :

                System.out.println("DISCRETE SET OUTPUTS  Message start.");

                for (int i = 0; i < aktifAmtbIdlist.size(); ++i) {
                    int amtbId = aktifAmtbIdlist.get(i);

                    if (isAmtbIdForSocket(index, amtbId)) {
                        int totalDiscreteSetOutputStructByteCount = 21;

                        ByteBuffer bufDiscreteSetOutput= ByteBuffer.allocate(totalDiscreteSetOutputStructByteCount);


                        send2KKY(soc.hatNo-1, 0, AMTBMsgIDConstants.AMTB_DISCRETE_SET_OUTPUTS_ID, bufDiscreteSetOutput.array(), soc.outputStream);
                        System.out.println("Socket " + index + " , id(AMTB_DISCRETE_) : " + AMTBMsgIDConstants.AMTB_DISCRETE_SET_OUTPUTS_ID + " mesaj gönderildi."
                                + " aktif amtb[" + i + "] : " + aktifAmtbIdlist.get(i));

                    } else {
                        System.out.println("Bu hatta Uygun AMTB Secilmedigi icin mesaj gonderilmedi(DISCRETE_SET_OUTPUTS). Socket " + index + " PORT : " + String.valueOf(soc.hatNo-1));
                    }

                }
                break;
            default:
                return ;
            }}


    private byte[] readFullPacketFromStream(InputStream in) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        int b;
        int attempts = 0;
        // Header senkronizasyonu (max 1000 deneme gibi limit ekledim)
        do {
            b = in.read();
            if (b == -1) throw new IOException("Stream sonlandi!");
            attempts++;
            if (attempts > 1000) throw new IOException("Header bulunamadi");
        } while ((byte) b != AMTB_MSG_HEADER);

        baos.write((byte) b);

        // DestID
        int destID = in.read();
        if (destID == -1) throw new IOException("Stream sonlandi (destID)");
        baos.write(destID);

        // msgLen
        byte[] msgLenBytes = new byte[2];
        readFully(in, msgLenBytes, 0, 2);
        baos.write(msgLenBytes);
        short msgLen = ByteBuffer.wrap(msgLenBytes).order(ByteOrder.LITTLE_ENDIAN).getShort();

        // msgId
        byte[] msgIdBytes = new byte[2];
        readFully(in, msgIdBytes, 0, 2);
        baos.write(msgIdBytes);

        int payloadLen = msgLen - 2; // checksum çıkarıldı
        if (payloadLen < 0) throw new IOException("Geçersiz mesaj uzunluğu");

        byte[] payload = new byte[payloadLen];
        if (payloadLen > 0) readFully(in, payload, 0, payloadLen);
        baos.write(payload);

        int checksum = in.read();
        if (checksum == -1) throw new IOException("Stream sonlandi (checksum)");
        baos.write(checksum);

        return baos.toByteArray();
    }



}
