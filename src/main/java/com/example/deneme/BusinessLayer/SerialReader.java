package com.example.deneme.ServiceLayer;



import com.example.deneme.AMTB_Sim_Controller;
import com.example.deneme.ControllerClasses.*;
import com.example.deneme.MessageIDConstans.AMTBMessageState;
import com.example.deneme.MessageIDConstans.AMTBMsgIDConstants;
import com.example.deneme.MessageIDConstans.AmtbFunctions;
import com.example.deneme.MessageIDConstans.AmtbSubFunctions;
import com.example.deneme.SocketConnection.SerialConnectionforKKY;
import com.fazecast.jSerialComm.SerialPort;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.example.deneme.AMTBStateController.*;
import static com.example.deneme.AMTB_Sim_Controller.*;

public class SerialReader implements Runnable {


    private final BlockingQueue<MessageWithSource> messageQueue;  // Tip değişti
    private volatile boolean running = true;
    private final List<ListeningSerial> listeners;

    public SerialReader(List<ListeningSerial> listeners, BlockingQueue<MessageWithSource> messageQueue) {
        this.listeners = listeners;
        this.messageQueue = messageQueue;
    }

    @Override
    public void run() {
        System.out.println("SerialReader (read thread) başladı.");
        while (running) {
            for (ListeningSerial listener : listeners) {
                try {
                    byte[] packet = listener.readFullPacketFromStream();
                    if (packet != null) {
                        // Artık mesajı, kaynağıyla birlikte kuyruğa koyuyoruz:
                        messageQueue.offer(new MessageWithSource(packet, listener));
                    }
                } catch (IOException e) {
                    System.err.println("HATA [HAT " + listener.getHatNo() + "]: " + e.getMessage());
                }
            }
            // CPU'yu boşu boşuna yormamak için kısa bekleme koyduk
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                stop();
            }
        }
    }



    public void stop() {
        running = false;
    }


}




