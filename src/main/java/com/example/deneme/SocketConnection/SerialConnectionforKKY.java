package com.example.deneme.SocketConnection;

import com.example.deneme.AMTB_Sim_Controller;
import com.example.deneme.KonfigurasyonSecimController;
import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class SerialConnectionforKKY implements IConnection {

    private static final int DEFAULT_BAUDRATE = 115200;

    public List<OutputStream> outputList = new ArrayList<>();

    // Port isimleri (kendi sistemine göre ayarla)
    private String PORT_AMTB_HAT1 = "COM1";
    private String PORT_AMTB_HAT2 = "COM2";
    private String PORT_AMTB_HAT3 = "COM3";
    private String PORT_AMTB_HAT4 = "COM4";

 /*   private ListeningSerial listenerHat1 = null;
    private ListeningSerial listenerHat2 = null;
    private ListeningSerial listenerHat3 = null;
    private ListeningSerial listenerHat4 = null;
*/
    private int baudRate = DEFAULT_BAUDRATE;

    // Portlar ve streamler
    private SerialPort amtbHat1Port = null;
    private InputStream amtbHat1ins = null;
    private OutputStream amtbHat1outs = null;

    private SerialPort amtbHat2Port = null;
    private InputStream amtbHat2ins = null;
    private OutputStream amtbHat2outs = null;

    private SerialPort amtbHat3Port = null;
    private InputStream amtbHat3ins = null;
    private OutputStream amtbHat3outs = null;

    private SerialPort amtbHat4Port = null;
    private InputStream amtbHat4ins = null;
    private OutputStream amtbHat4outs = null;

    public boolean connectionState_hat1 = false;
    public boolean connectionState_hat2 = false;
    public boolean connectionState_hat3 = false;
    public boolean connectionState_hat4 = false;

    private static final byte AMTB_MSG_HEADER     = (byte) 0xA5;
    private static final byte MSG_HEADER_LEN      = 1;
    private static final byte MSG_AMTB_ID_LEN     = 1;
    private static final byte MSG_LEN_DATA_LEN    = 2;
    private static final byte MSG_COMMAND_LEN     = 1;
    private static final byte MSG_SUBCOMMAND_LEN  = 1;
    private static final byte MSG_CHECKSUM_LEN    = 1;

    private static final int MIN_MSG_SIZE_ALL = MSG_HEADER_LEN + MSG_AMTB_ID_LEN + MSG_LEN_DATA_LEN
            + MSG_COMMAND_LEN + MSG_SUBCOMMAND_LEN + MSG_CHECKSUM_LEN;

    // Constructors
    private AMTB_Sim_Controller main;

    public SerialConnectionforKKY(AMTB_Sim_Controller main) {
        this.main = main;
        instance = this;
    }
    public SerialConnectionforKKY(String port1, String port2, String port3, String port4) {
        this.PORT_AMTB_HAT1 = port1;
        this.PORT_AMTB_HAT2 = port2;
        this.PORT_AMTB_HAT3 = port3;
        this.PORT_AMTB_HAT4 = port4;
    }
    private static SerialConnectionforKKY instance;


    public static SerialConnectionforKKY getInstance() {
        return instance;  // Başka yerlerden erişmek için
    }

    private void updateOutputList() {
        outputList.clear();
        if (connectionState_hat1 && amtbHat1outs != null) outputList.add(amtbHat1outs);
        if (connectionState_hat2 && amtbHat2outs != null) outputList.add(amtbHat2outs);
        if (connectionState_hat3 && amtbHat3outs != null) outputList.add(amtbHat3outs);
        if (connectionState_hat4 && amtbHat4outs != null) outputList.add(amtbHat4outs);
    }

    @Override
    public void createSocket() {
        try {
            KonfigurasyonSecimController config = KonfigurasyonSecimController.getInstance();

            // HAT 1
            if (config.isHatOneCheckBoxSelected()) {
                String portName = main.getPortNameForHat(1);
                if (portName != null && !portName.equalsIgnoreCase("None")) {
                    if (amtbHat1Port != null && amtbHat1Port.isOpen()) {
                        System.out.println("AMTB HAT 1 port zaten açık: " + amtbHat1Port.getSystemPortName());
                        connectionState_hat1 = true;
                    } else {
                        amtbHat1Port = SerialPort.getCommPort(portName);
                        amtbHat1Port.setComPortParameters(baudRate, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
                        amtbHat1Port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 300, 300);
                        amtbHat1Port.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
                        if (amtbHat1Port.openPort()) {
                            amtbHat1ins = amtbHat1Port.getInputStream();
                            amtbHat1outs = amtbHat1Port.getOutputStream();
                            System.out.println("OutputStream null? " + (amtbHat1outs == null));
                            connectionState_hat1 = true;
                            updateOutputList();
                            System.out.println("AMTB HAT 1 port opened: " + portName);
                        } else {
                            System.err.println("AMTB HAT 1 port failed to open: " + portName);
                            connectionState_hat1 = false;
                        }
                    }
                } else {
                    connectionState_hat1 = false;
                }
            } else {
                connectionState_hat1 = false;
            }

            // HAT 2
            if (config.isHatTwoCheckBoxSelected()) {
                String portName = main.getPortNameForHat(2);
                if (portName != null && !portName.equalsIgnoreCase("None")) {
                    if (amtbHat2Port != null && amtbHat2Port.isOpen()) {
                        System.out.println("AMTB HAT 2 port zaten açık: " + amtbHat2Port.getSystemPortName());
                        connectionState_hat2 = true;
                    } else {
                        amtbHat2Port = SerialPort.getCommPort(portName);
                        amtbHat2Port.setComPortParameters(baudRate, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
                        amtbHat2Port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 300, 300);

                        amtbHat2Port.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
                        if (amtbHat2Port.openPort()) {
                            amtbHat2ins = amtbHat2Port.getInputStream();
                            amtbHat2outs = amtbHat2Port.getOutputStream();
                            System.out.println("OutputStream null? " + (amtbHat2outs == null));
                            connectionState_hat2 = true;
                            updateOutputList();
                            System.out.println("AMTB HAT 2 port opened: " + portName);
                        } else {
                            System.err.println("AMTB HAT 2 port failed to open: " + portName);
                            connectionState_hat2 = false;
                        }
                    }
                } else {
                    connectionState_hat2 = false;
                }
            } else {
                connectionState_hat2 = false;
            }

            // HAT 3
            if (config.isHatThreeCheckBoxSelected()) {
                String portName = main.getPortNameForHat(3);
                if (portName != null && !portName.equalsIgnoreCase("None")) {
                    if (amtbHat3Port != null && amtbHat3Port.isOpen()) {
                        System.out.println("AMTB HAT 3 port zaten açık: " + amtbHat3Port.getSystemPortName());
                        connectionState_hat3 = true;
                    } else {
                        amtbHat3Port = SerialPort.getCommPort(portName);
                        amtbHat3Port.setComPortParameters(baudRate, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
                        amtbHat3Port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 300, 300);
                        amtbHat3Port.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
                        if (amtbHat3Port.openPort()) {
                            amtbHat3ins = amtbHat3Port.getInputStream();
                            amtbHat3outs = amtbHat3Port.getOutputStream();
                            connectionState_hat3 = true;
                            updateOutputList();
                            System.out.println("AMTB HAT 3 port opened: " + portName);
                        } else {
                            System.err.println("AMTB HAT 3 port failed to open: " + portName);
                            connectionState_hat3 = false;
                        }
                    }
                } else {
                    connectionState_hat3 = false;
                }
            } else {
                connectionState_hat3 = false;
            }

            // HAT 4
            if (config.isHatFourCheckBoxSelected()) {
                String portName = main.getPortNameForHat(4);
                if (portName != null && !portName.equalsIgnoreCase("None")) {
                    if (amtbHat4Port != null && amtbHat4Port.isOpen()) {
                        System.out.println("AMTB HAT 4 port zaten açık: " + amtbHat4Port.getSystemPortName());
                        connectionState_hat4 = true;
                    } else {
                        amtbHat4Port = SerialPort.getCommPort(portName);
                        amtbHat4Port.setComPortParameters(baudRate, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
                        amtbHat4Port.setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 300, 300);
                        //amtbHat4Port.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, 1000, 1000);
                        amtbHat4Port.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
                        if (amtbHat4Port.openPort()) {
                            amtbHat4ins = amtbHat4Port.getInputStream();
                            amtbHat4outs = amtbHat4Port.getOutputStream();
                            connectionState_hat4 = true;
                            updateOutputList();
                            System.out.println("AMTB HAT 4 port opened: " + portName);
                        } else {
                            System.err.println("AMTB HAT 4 port failed to open: " + portName);
                            connectionState_hat4 = false;
                        }
                    }
                } else {
                    connectionState_hat4 = false;
                }
            } else {
                connectionState_hat4 = false;
            }

            // En az bir port başarıyla açıldıysa mesaj göster
            if (connectionState_hat1 || connectionState_hat2 || connectionState_hat3 || connectionState_hat4) {
                System.out.println("Serial COM initialized.");
            }

            //updateOutputList();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Seri port açılırken hata oluştu!");
        }
    }



    @Override
    public void closeSocket() {
        if (main != null) {
            main.stopListeners();
        }

        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        for (int i = 1; i <= 4; i++) {
            tryClosePort(i);
        }

        System.out.println("All ports closed.");
    }

    private void tryClosePort(int hatNo) {
        try {
            switch (hatNo) {
                case 1:
                    if (amtbHat1ins != null) {
                        amtbHat1ins.close();
                        amtbHat1ins = null;
                    }
                    if (amtbHat1outs != null) {
                        amtbHat1outs.close();
                        amtbHat1outs = null;
                    }
                    if (amtbHat1Port != null && amtbHat1Port.isOpen()) {
                        if (amtbHat1Port.closePort()) {
                            System.out.println("HAT 1 port başarıyla kapatıldı.");
                        } else {
                            System.err.println("HAT 1 port kapatılamadı.");
                        }
                        amtbHat1Port = null;
                    }
                    connectionState_hat1 = false;
                    break;
                case 2:
                    if (amtbHat2ins != null) {
                        amtbHat2ins.close();
                        amtbHat2ins = null;
                    }
                    if (amtbHat2outs != null) {
                        amtbHat2outs.close();
                        amtbHat2outs = null;
                    }
                    if (amtbHat2Port != null && amtbHat2Port.isOpen()) {
                        if (amtbHat2Port.closePort()) {
                            System.out.println("HAT 2 port başarıyla kapatıldı.");
                        } else {
                            System.err.println("HAT 2 port kapatılamadı.");
                        }
                        amtbHat2Port = null;
                    }
                    connectionState_hat2 = false;
                    break;
                case 3:
                    if (amtbHat3ins != null) {
                        amtbHat3ins.close();
                        amtbHat3ins = null;
                    }
                    if (amtbHat3outs != null) {
                        amtbHat3outs.close();
                        amtbHat3outs = null;
                    }
                    if (amtbHat3Port != null && amtbHat3Port.isOpen()) {
                        if (amtbHat3Port.closePort()) {
                            System.out.println("HAT 3 port başarıyla kapatıldı.");
                        } else {
                            System.err.println("HAT 3 port kapatılamadı.");
                        }
                        amtbHat3Port = null;
                    }
                    connectionState_hat3 = false;
                    break;
                case 4:
                    if (amtbHat4ins != null) {
                        amtbHat4ins.close();
                        amtbHat4ins = null;
                    }
                    if (amtbHat4outs != null) {
                        amtbHat4outs.close();
                        amtbHat4outs = null;
                    }
                    if (amtbHat4Port != null && amtbHat4Port.isOpen()) {
                        if (amtbHat4Port.closePort()) {
                            System.out.println("HAT 4 port başarıyla kapatıldı.");
                        } else {
                            System.err.println("HAT 4 port kapatılamadı.");
                        }
                        amtbHat4Port = null;
                    }
                    connectionState_hat4 = false;
                    break;
            }
        } catch (IOException e) {
            System.err.println("HAT " + hatNo + " port kapatılırken hata: " + e.getMessage());
        }
    }


    // Diğer metodlar ve getter'lar değişmeden kalabilir


    // Getter'lar
    public SerialPort getPort1() { return amtbHat1Port; }
    public InputStream getIn1() { return amtbHat1ins; }
    public OutputStream getOut1() { return amtbHat1outs; }

    public SerialPort getPort2() { return amtbHat2Port; }
    public InputStream getIn2() { return amtbHat2ins; }
    public OutputStream getOut2() { return amtbHat2outs; }

    public SerialPort getPort3() { return amtbHat3Port; }
    public InputStream getIn3() { return amtbHat3ins; }
    public OutputStream getOut3() { return amtbHat3outs; }

    public SerialPort getPort4() { return amtbHat4Port; }
    public InputStream getIn4() { return amtbHat4ins; }
    public OutputStream getOut4() { return amtbHat4outs; }
}
