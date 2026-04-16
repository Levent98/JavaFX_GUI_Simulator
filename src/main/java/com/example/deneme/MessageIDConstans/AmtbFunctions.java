package com.example.deneme.MessageIDConstans;

import java.io.*;
import java.nio.ByteBuffer;

public class AmtbFunctions {

    // ------- 11.08.2025 MMT; KKY'den gelen bilgiler geri gondermek icin bufferda saklama metodlari.
    static int RTC_BUFF_SIZE      = 6;
    static int BLANKING_BUFF_SIZE = 3;

    // 11.08.2025 MMT; KKY'den gelen bilgiler geri gondermek icin bufferda saklama metodlari.
    public static byte[] getRtcBuffer() {
        return rtcBuffer;
    }
    private static byte[] rtcBuffer = new byte[RTC_BUFF_SIZE];
    public static boolean rtcSetFlag = false;

    //13.08.2025 I.A.
    public static byte[] getBlankingBuffer() {
        return blankingBuffer;
    }
    private static byte[] blankingBuffer = new byte[BLANKING_BUFF_SIZE];
    public static boolean blankingSetFlag = false;
    // -------


    public static String getRtcStruct(ByteArrayInputStream bis) throws IOException {

        StringBuilder str = new StringBuilder();

        int sec   = bis.read();
        int min   = bis.read();
        int hour  = bis.read();
        int day   = bis.read();
        int month = bis.read();
        int year  = bis.read();

        rtcBuffer[0] = (byte) sec;
        rtcBuffer[1] = (byte) min;
        rtcBuffer[2] = (byte) hour;
        rtcBuffer[3] = (byte) day;
        rtcBuffer[4] = (byte) month;
        rtcBuffer[5] = (byte) year;

        //		str.append("Saniye = " + sec);
        //		str.append("Dakika = " + min);
        //		str.append("Saat = " + hour);
        //		str.append("Gün = " + day);
        //		str.append("Ay = " + month);
        //		str.append("Yıl = " + year);

        str.append("\nTarih Bilgisi = " + day + "/" + month + "/" + year);
        str.append("\nSaat Bilgisi = " + hour + ":" + min + ":" + sec);

        rtcSetFlag = true;

        return str.toString();

    }

    public static String getAmtbStateChangeStruct_t(ByteArrayInputStream bis) throws IOException {

        StringBuilder str = new StringBuilder();

        int amtbArmState =(int) bis.read();
        str.append("\nArm Durumu = " + amtbArmState);

        return str.toString();
    }


    public static String getUpdateGVDStruct(ByteArrayInputStream bis) throws IOException {

        StringBuilder str = new StringBuilder();

        int amtbUpdateGVD =(int) bis.read();
        str.append("\nGVD Update Durumu = " + amtbUpdateGVD);

        return str.toString();
    }

    public static String getZeroizeStart_t(ByteArrayInputStream bis) throws IOException {

        StringBuilder str = new StringBuilder();

        int amtbZeroizeStart = (int)bis.read();
        str.append("\nZeroizeStart = " + amtbZeroizeStart);


        return str.toString();

    }

    public static String getASHFirmwareUpdateStart_t(ByteArrayInputStream bis) throws IOException {

        StringBuilder str = new StringBuilder();

        int amtbASHFirmwareUpdateStart = bis.read();
        str.append("\nASHFirmwareUpdateStart = " + amtbASHFirmwareUpdateStart);

        return str.toString();

    }

    public static String getASHFirmwareUpdateEnd_t(ByteArrayInputStream bis) throws IOException {

        StringBuilder str = new StringBuilder();

        int amtbASHFirmwareUpdateEnd =(int) bis.read();
        str.append("\nAmtbASHFirmwareUpdateEnd = " + amtbASHFirmwareUpdateEnd);

        return str.toString();

    }

    // MMT : 05.08.2025
    public static String setAMTBKKYConfigStruct_t(ByteArrayInputStream in) throws IOException {
        StringBuilder str = new StringBuilder();

        int fisLineCount = in.read();
        int magCoverAvailable = in.read();

        if (fisLineCount == -1 || magCoverAvailable == -1) {
            throw new EOFException("Veri eksik veya bağlantı kapandı.");
        }

        str.append("\nFisline Count : ").append(fisLineCount);

        String magCoverAvailableStr = (magCoverAvailable == 0) ? "KAPAK YOK" : "KAPAK VAR";
        str.append("\nMagazin Cover(Kapak Durumu) = ")
                .append(magCoverAvailable)
                .append(" , : ")
                .append(magCoverAvailableStr);

        return str.toString();
    }





    public static byte[] getFirmwareAmtbVersionStruct() {

        /**
         * VersionDataStruct_t bootloader
         * uint8_t configuration[AMTB_VERSION_MAX_LENGTH=16]
         * VersionDataStruct_t firmware; // major version 1 olmalı
         * uint8_t hardware[AMTB_VERSION_MAX_LENGTH=16]
         * */

        //"128.13.4;   1.3.4.5.3.2.3.33.4. 32;12 9.45.3; 2314254222"

        //String[] vals = parseString(val);

        ByteBuffer tmp = ByteBuffer.allocate(48);

        byte arr[] = new byte[48];

        for (int i = 0; i < 48; i++) {
            //tmp.put(getVersionDataStruct(vals[0]));
            arr[i] = (byte) 0;
        }
        //tmp.put((byte)1);}
        //		tmp.put(0)

        // TODO Auto-generated method stub
        //byte arr[] = new byte[] {1};  /// burasi dolmali
        arr[28] = (byte) 1;

        return (arr);

    }

    public static byte[] getMisfireDataIDStruct() {


        ByteBuffer mdi = ByteBuffer.allocate(4);

        byte arr[] = new byte[4];

        for (int i = 0; i < 3; i++) {
            //tmp.put(getVersionDataStruct(vals[0]));
            arr[i] = (byte) 0;
        }
        //tmp.put((byte)1);}
        //		tmp.put(0)

        // TODO Auto-generated method stub
        //byte arr[] = new byte[] {1};  /// burasi dolmali
        //  arr[3]=(byte)1;

        return (arr);

    }

    public static byte[] getDiscreteSetOutputs() {


        ByteBuffer discrete = ByteBuffer.allocate(21);

        byte arr[] = new byte[21];

        for (int i = 0; i < 20; i++) {
            //tmp.put(getVersionDataStruct(vals[0]));
            arr[i] = (byte) 0;
        }
        //tmp.put((byte)1);}
        //		tmp.put(0)

        // TODO Auto-generated method stub
        //byte arr[] = new byte[] {1};  /// burasi dolmali
        //  arr[3]=(byte)1;

        return new byte[0];
    }


    public static String getBlankingMessage(ByteArrayInputStream bis) throws IOException {

        StringBuilder str = new StringBuilder();

        int category = bis.read();
        int direction = bis.read();
        int operation = bis.read();

        blankingBuffer[0] = (byte) category;
        blankingBuffer[1] = (byte) direction;
        blankingBuffer[2] = (byte) operation;

        str.append("\nBlanking bilgisi = " + category + "/" + direction + "/" + operation);

        blankingSetFlag = true;

        return str.toString();

    }



}
