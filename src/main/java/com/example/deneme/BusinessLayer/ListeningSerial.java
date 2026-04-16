package com.example.deneme.InfrastructureLayer;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

public class ListeningSerial {
    public final int hatNo;
    public final SerialPort port;
    public final InputStream inputStream;
    public final OutputStream outputStream;
    private final byte[] buffer=new byte[3000];

    public ListeningSerial(int hatNo, SerialPort port, InputStream inputStream, OutputStream outputStream) {
        this.hatNo = hatNo;
        this.port = port;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    public int getHatNo() {
        return hatNo;
    }
    //public static byte[] buffer = new byte[2500];

    public byte[] readFullPacketFromStream() throws IOException {
        if (inputStream.available() > 0) {
            //byte[] buffer = new byte[2500];
            int len = inputStream.read(buffer);
            if (len > 0) {
                return Arrays.copyOf(buffer, len);
            }
        }
        return null;
    }

}
