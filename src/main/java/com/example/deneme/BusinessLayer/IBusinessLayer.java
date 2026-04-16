package com.example.deneme.BusinessLayer;

public interface IBusinessLayer {

    public void send2KKY(int msgID);
    public void send2KKY(int msgID, byte info);
    public void send2KKY(int msgID, byte[] info);

}
