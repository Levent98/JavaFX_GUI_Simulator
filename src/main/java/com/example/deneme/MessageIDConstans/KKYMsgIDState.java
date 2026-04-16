package com.example.deneme.MessageIDConstans;

import java.io.DataInputStream;
import java.io.IOException;

public class KKYMsgIDState {

    public static String getSocObj(DataInputStream dis, int msgID, int msgLen) throws IOException{

        byte[] content = new byte[msgLen];
        dis.readFully(content);
        String result = "";

        System.out.println("Gelen Mesaj: " + msgID + " - " + msgLen);

        return result;
    }
}
