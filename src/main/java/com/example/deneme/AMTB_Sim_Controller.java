package com.example.deneme;
import com.example.deneme.BusinessLayer.ListeningSerial;
import com.example.deneme.BusinessLayer.MessageProcessor;
import com.example.deneme.BusinessLayer.MessageWithSource;
import com.example.deneme.ControllerClasses.AMTBState;
import com.example.deneme.ControllerClasses.InventoryCellState;
import com.example.deneme.BusinessLayer.SerialReader;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;


import com.fazecast.jSerialComm.SerialPort;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import com.example.deneme.ControllerClasses.AmtbDiscreteLinesMessage;


import com.example.deneme.SocketConnection.SerialConnectionforKKY;
import com.example.deneme.AMTBDiscreteLines;

public class AMTB_Sim_Controller implements Initializable {

    public static List<AMTBState> AMTBStateList = new ArrayList<>();
    public static List<InventoryCellState> InventoryCellStateList = new ArrayList<>();
    public static int hucreNo = 0;

    public static List<Integer> aktifAmtbIdList = new ArrayList<>();

    //public static Map<Integer, List<String>> AmtbState = new HashMap<>();

    private KonfigurasyonSecimController configController;

    private AMTBState data;  // ← field

    private static AMTB_Sim_Controller instance;

    public AMTB_Sim_Controller() {
        instance = this;  // Controller oluşturulunca statik referans atanıyor
    }

    public static AMTB_Sim_Controller getInstance() {
        return instance;  // Başka yerlerden erişmek için
    }
    public static List<AMTBState> getAMTBStateList() {
        return AMTBStateList;
    }


    @FXML
    private Button butonKonf;

/*    @FXML
    private Button baglanButton;*/

    @FXML
    private TextArea logTextArea;

    @FXML
    private ComboBox<String> hat1PortComboBox;

    @FXML
    private ComboBox<String> hat2PortComboBox;

    @FXML
    private ComboBox<String> hat3PortComboBox;

    @FXML
    private ComboBox<String> hat4PortComboBox;

    @FXML
    private Button baglanbutton;

    @FXML
    private Button kapatbutton;

    public static List<Integer> getAktifAmtbIdList() {
        return aktifAmtbIdList;
    }

    private SerialConnectionforKKY serialConnection;

    private final List<ListeningSerial> listeners = new ArrayList<>();

    // ExecutorService
    private ExecutorService executorService;
//    private final List<SerialReader> readerTasks = new ArrayList<>();
    private static AMTBDiscreteLines amtbDiscreteline;

    public static void setDiscreteData(AMTBDiscreteLines controller){
        amtbDiscreteline=controller;
    }
    public static AMTBDiscreteLines getDiscreteData(){ return amtbDiscreteline; }



    private SerialReader serialReader;
    public static MessageProcessor messageProcessor;

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        for (int i = 0; i < 32; i++) {
            AMTBStateList.add(new AMTBState(i));
            for (int j = 0; j < 30; ++j) {
                InventoryCellStateList.add(new InventoryCellState(j));
            }
        }
        appendLog("Simulator başlatildi...");
        redirectSystemStreams();
        populateAvailablePorts(hat1PortComboBox);
        populateAvailablePorts(hat2PortComboBox);
        populateAvailablePorts(hat3PortComboBox);
        populateAvailablePorts(hat4PortComboBox);

        // Select first port by default if available
        if (!hat1PortComboBox.getItems().isEmpty()) hat1PortComboBox.getSelectionModel().select(0);
        if (!hat2PortComboBox.getItems().isEmpty()) hat2PortComboBox.getSelectionModel().select(0);
        if (!hat3PortComboBox.getItems().isEmpty()) hat3PortComboBox.getSelectionModel().select(0);
        if (!hat4PortComboBox.getItems().isEmpty()) hat4PortComboBox.getSelectionModel().select(0);
        appendLog("portlar cekildi");

        serialConnection = new SerialConnectionforKKY(this);
    }


    public static int getamtbHatId() {
        return g_amtbHatId;
    }

    static int g_amtbHatId = 0;

    public static int getAmtbNo() {
        return g_amtbNo;
    }

    static int g_amtbNo = 0;
    @FXML
    private void fetchallports(ActionEvent event){
        populateAvailablePorts(hat1PortComboBox);
        populateAvailablePorts(hat2PortComboBox);
        populateAvailablePorts(hat3PortComboBox);
        populateAvailablePorts(hat4PortComboBox);

        // Select first port by default if available
        if (!hat1PortComboBox.getItems().isEmpty()) hat1PortComboBox.getSelectionModel().select(0);
        if (!hat2PortComboBox.getItems().isEmpty()) hat2PortComboBox.getSelectionModel().select(0);
        if (!hat3PortComboBox.getItems().isEmpty()) hat3PortComboBox.getSelectionModel().select(0);
        if (!hat4PortComboBox.getItems().isEmpty()) hat4PortComboBox.getSelectionModel().select(0);

    }
    @FXML
    private void LogTemizleButton (ActionEvent event){
        logTextArea.clear();
    }
    @FXML
    private void onButtonKonfClick(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("KonfigurasyonSecim.fxml"));
            HBox popupContent = loader.load();

            KonfigurasyonSecimController configController = loader.getController();
            configController.initializeButtonToCheckbox();
            if (configController.isHatOneCheckBoxSelected()) {
                configController.hatOneCheckBox.setSelected(true);
                System.out.println("Hat1 secildi ve kalici olarak checkbox isaretlendi.");
            }

            if (configController.isHatTwoCheckBoxSelected()) {
                configController.hatTwoCheckBox.setSelected(true);
                System.out.println("Hat2 secildi ve kalici olarak checkbox isaretlendi.");
            }

            if (configController.isHatThreeCheckBoxSelected()) {
                configController.hatThreeCheckBox.setSelected(true);
                System.out.println("Hat3 secildi ve kalici olarak checkbox isaretlendi.");
            }

            if (configController.isHatFourCheckBoxSelected()) {
                configController.hatFourCheckBox.setSelected(true);
                System.out.println("Hat4 secildi ve kalici olarak checkbox isaretlendi.");
            }

            Scene popupScene = new Scene(popupContent);
            Stage popupStage = new Stage();
            popupStage.setScene(popupScene);
            popupStage.setTitle("Konfigurasyon Secimi");
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.show();

            // istege bagli
            popupStage.setOnHiding(e -> {
                        /* 27.05.2025 - MMT;
                            Ekran kapandiginda yani X tusuna basildiginda buradaki komutlar icra edilir.
                            Fakat GUI uzerinde herhangi bir degislik yapmak istenirse (Buton , CheckBox vs.)
                            burada yapilmamasi gereklidir. Bunun nedeni bu fonksiyonu cagirdigimizda
                            basta .fxml yuklenmekte ve bu .fmxl dosyalari her yuklendiginde default degeri olarak
                            yuklenmekte. Buda yaptigimiz degisiklikleri GUI'de yani arayuzde gormemize
                            engel olmakta. GUI'de kalici degisiklik yapmak istedigimizde bu fonksiyonun icindeki gibi
                            .fxml sahnesi yuklenmeden kalici degisiklikler yapilmalidir !!!
                         */
                        System.out.println("KonfigurasyonSecim.fxml setOnHiding kapandi.");
                    }
            );

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

/*    @FXML
    void onAmtbBaglanButtonClicked(ActionEvent event) {
        System.out.println("onAmtbBaglanButtonClicked : ");
        listeningSocket.setRunning(true);
        createSocketForRunning.setSocketRunning(true);
        CreateSocket createSocket = new CreateSocket(configController,socketConnection);
        createSocket.setSocketThread();
    }*/

/*    @FXML
    void onAmtbKapatButtonClicked(ActionEvent event) {
        System.out.println("onAmtbKapatButtonClicked : ");
        socketConnection.closeSocket();
        listeningSocket.setRunning(false);
        //Kapat butonuna basıldığında running fonksiyonu durduruluyor cunku false cekilmez ise socket olusturmaya devam ediyor.hatayı onlemek için eklendi.
        createSocketForRunning.setSocketRunning(false);
    }*/
@FXML
public void discreteLinebutton(ActionEvent event) {
    try {
        FXMLLoader fxmlLoader = new FXMLLoader(AMTBDiscreteLines.class.getResource("AMTBDiscreteLines.fxml"));
        AnchorPane popupContent = fxmlLoader.load();  // Önce yükle
        AMTBDiscreteLines controller = fxmlLoader.getController();  // Sonra controller al
        AmtbDiscreteLinesMessage data = AMTBDiscreteLines.discretelinelist.get(AMTBDiscreteLines.hatnu);

        controller.AMTB_DISCRETE_LINE_WOW.setValue(data.getWow().toString());               // ← Enum'dan String'e
        controller.AMTB_DISCRETE_LINE_DISPENSE.setValue(data.getDispense().toString());
        controller.AMTB_DISCRETE_LINE_JETTISON.setValue(data.getJettison().toString());
        controller.AMTB_DISCRETE_LINE_ZEROIZE.setValue(data.getZeroize().toString());
        controller.AMTB_DISCRETE_LINE_BYPASS_MODE.setValue(data.getBypassMode().toString());
        controller.AMTB_DISCRETE_LINE_BYPASS_FIS_MODE.setValue(data.getBypassFisMode().toString());
        controller.AMTB_DISCRETE_LINE_BYPASS_RIAS_MODE.setValue(data.getBypassRiasMode().toString());
        controller.AMTB_DISCRETE_LINE_BYP_FIS_DRCT0.setValue(data.getBypFisDrct0().toString());
        controller.AMTB_DISCRETE_LINE_BYP_FIS_DRCT1.setValue(data.getBypFisDrct1().toString());
        controller.AMTB_DISCRETE_LINE_BYP_FIS_DRCT2.setValue(data.getBypFisDrct2().toString());
        controller.AMTB_DISCRETE_LINE_BYP_RIAS_DETECT.setValue(data.getBypRiasDetect().toString());
        controller.AMTB_DISCRETE_LINE_RF_BLANK_OUT.setValue(data.getRfBlankOut().toString());
        controller.AMTB_DISCRETE_LINE_IR_BLANK_OUT.setValue(data.getIrBlankOut().toString());
        controller.AMTB_DISCRETE_LINE_RF_BLANK_IN.setValue(data.getRfBlankIn().toString());
        controller.AMTB_DISCRETE_LINE_IR_BLANK_IN.setValue(data.getIrBlankIn().toString());
        controller.AMTB_DISCRETE_LINE_NO_CHAFF.setValue(data.getNoChaff().toString());
        controller.AMTB_DISCRETE_LINE_NO_FLARE.setValue(data.getNoFlare().toString());
        controller.AMTB_DISCRETE_LINE_IR_BLANK_OUT_IN.setValue(data.getIrBlankOutIn().toString());
        controller.AMTB_DISCRETE_LINE_RF_BLANK_OUT_IN.setValue(data.getRfBlankOutIn().toString());
        controller.AMTB_DISCRETE_LINE_NO_CHAFF_IN.setValue(data.getNoChaffIn().toString());
        controller.AMTB_DISCRETE_LINE_NO_FLARE_IN.setValue(data.getNoFlareIn().toString());
        controller.AYRIKHATNOCB.setValue(data.getHatno().toString());

        Scene popupScene = new Scene(popupContent);
        Stage popupStage = new Stage();
        popupStage.setScene(popupScene);
        popupStage.setTitle("Ayrık Hat Sinyalleri");
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.show();

        setDiscreteData(controller);

        controller.AYRIKHATNOCB.setOnAction(e -> {
            try {
                Integer secilenHatNo = AmtbDiscreteLinesMessage.HatNo.valueOf(controller.AYRIKHATNOCB.getValue()).getValue();
                if (secilenHatNo != null && secilenHatNo >= 0 && secilenHatNo < AMTBDiscreteLines.discretelinelist.size()) {
                    AmtbDiscreteLinesMessage yeniData = AMTBDiscreteLines.discretelinelist.get(secilenHatNo);
                    controller.loadDiscreteData(yeniData);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

    } catch (IOException e) {
        e.printStackTrace();
    }
}


    @FXML
    void clickEnvanterBilgisiGoruntuleBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/deneme/AMTBInventoryInfoShow.fxml"));
            AnchorPane popupContent = loader.load();

            AMTBInventoryController amtbSimController = loader.getController();
            Scene popupScene = new Scene(popupContent);
            Stage popupStage = new Stage();

            popupStage.setScene(popupScene);
            popupStage.setTitle("ENVANTER BILGISI GORUNTULEME");
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected static void onAmtbButtonClicked(int hatNo, int amtbNo, Button button) {


        CheckBox ch = KonfigurasyonSecimController.getButtonToCheckboxMap().get(button);
        List<Button> buttonsInOrder = new ArrayList<Button>(KonfigurasyonSecimController.getButtonToCheckboxMap().keySet());


        if (ch != null && ch.isSelected()) {
            // Checkbox is ticked — continue normal flow
            System.out.println("Checkbox is ticked, proceeding...");

        } else {
            // Checkbox is not ticked or no mapping found — show message and exit
            System.out.println("Checkbox should be ticked!");
            return;
            // Exit the program

        }
        AMTBState data = AMTBStateList.get(amtbNo);
        g_amtbNo = amtbNo;
        g_amtbHatId = hatNo;

        try {
            FXMLLoader loader = new FXMLLoader(AMTB_Sim_Controller.class.getResource("AMTBState.fxml"));
            AnchorPane popupContent = loader.load();

            // Controller alınıyor
            AMTBStateController amtbStateController = loader.getController();
            // Data nesnesini controller’a enjekte et
            amtbStateController.setData(data);

            // Mevcut değerleri GUI’ye yükle
            amtbStateController.AmtbDurumBilgiCB.setValue(String.valueOf(data.getStateInfo()));
            amtbStateController.AmtbModSecimCB.setValue(String.valueOf(data.getAmtbMode()));
            amtbStateController.JettisonStateCB.setValue(String.valueOf(data.getJettisonState()));
            amtbStateController.ArmedCB.setValue(String.valueOf(data.getArmedState()));
            amtbStateController.IsarmedCB.setValue(String.valueOf(data.getIsArmed()));
            amtbStateController.BlankIRCB.setValue(String.valueOf(data.getBlankIR()));
            amtbStateController.BlankRFCB.setValue(String.valueOf(data.getBlankRF()));
            amtbStateController.DiscreteTestCB.setValue(String.valueOf(data.getDiscreteTest()));
            amtbStateController.FAILCB.setValue(String.valueOf(data.getFailState()));
            amtbStateController.IBITCB.setValue(String.valueOf(data.getIbit()));
            amtbStateController.IsAMTB6x5CB.setValue(String.valueOf(data.getIsAMTB6x5()));
            amtbStateController.IsFiringActiveCB.setValue(String.valueOf(data.getIsFiringActive()));
            amtbStateController.MDFCB.setValue(String.valueOf(data.getMdfState()));
            amtbStateController.MDFLoadingCB.setValue(String.valueOf(data.getMdfLoadingState()));
            amtbStateController.MagCoverCB.setValue(String.valueOf(data.getMagCoverState()));
            amtbStateController.MisfireCB.setValue(String.valueOf(data.getMisfire()));
            amtbStateController.NewFAILCB.setValue(String.valueOf(data.getNewFail()));
            amtbStateController.NewMisfireCB.setValue(String.valueOf(data.getNewMisfire()));
            amtbStateController.RTCCB.setValue(String.valueOf(data.getRtc()));
            amtbStateController.SQBPowerCB.setValue(String.valueOf(data.getSqbPower()));
            amtbStateController.STCConnectedCB.setValue(String.valueOf(data.getStcConnected()));
            amtbStateController.WOWCB.setValue(String.valueOf(data.getWowState()));

            // Popup aç
            Scene popupScene = new Scene(popupContent);
            Stage popupStage = new Stage();
            popupStage.setScene(popupScene);
            popupStage.setTitle("HAT " + hatNo + " AMTB ID" + amtbNo);
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.show();

            for (Button b : buttonsInOrder) {
                b.setDisable(false);
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }



  /*  public static List<String> getActiveUnitIndexes(int g_amtbNo) {
        List<String> activeIndexes = new ArrayList<>();

        // 32 birimlik AMTBState listesi bu no ile alınır
        List<AMTBState> amtbStates = AMTBStateList.getst(g_amtbNo);
        if (amtbStates == null) {
            System.out.println("AMTB No bulunamadı: " + g_amtbNo);
            return activeIndexes;
        }

        for (int i = 0; i < amtbStates.size(); i++) {
            AMTBState state = amtbStates.get(i);
            if (state.getStateInfo() == AMTBState.StateInfo.AKTIF) {
                activeIndexes.add(g_amtbNo + "_" + i);
            }
        }

        return activeIndexes;
    }*/


/*    public void setDataFromList(int amtbNo, List<AMTBState> AMTBStateList) {
        data = AMTBStateList.get(amtbNo);  // Liste içinden veriyi alır
    }*/

    protected static void onAmtbButtonHucreBazliEnvanterClicked(int hatNo, int amtbNo, int hucreNo, Button button) {
        System.out.println("buttona basildi hatNo : " + hatNo + " amtbNo : " + amtbNo + "hucreNo: " + hucreNo);

        try {
            FXMLLoader loader = new FXMLLoader(InventoryCellBasedDefinitionController.class.getResource("InventoryCellBasedDefinition.fxml"));
            AnchorPane popupContent = loader.load();

            InventoryCellBasedDefinitionController inventoryCellBasedDefinitionController = loader.getController();

            InventoryCellState inventorydata = InventoryCellStateList.get(hucreNo);

            inventoryCellBasedDefinitionController.setData(inventorydata, hucreNo);

            String Category = String.valueOf(inventorydata.getCategory());
            String numberofpieces = String.valueOf(inventorydata.getNumberofPieces());
            String dolulukdurumu = String.valueOf(inventorydata.getDolulukDurumu());
            String IsMisfired = String.valueOf(inventorydata.getIsMisfired());
            String IsMultiple = String.valueOf(inventorydata.getIsMultiple());
            String IsSmart = String.valueOf(inventorydata.getIsSmart());
            String TekCift = String.valueOf(inventorydata.getTekCift());
            String KalanOmurKrtiklikDurumu = String.valueOf(inventorydata.getKalanOmurKrtiklikDurumu());
            String UyumsuzlukDurumu = String.valueOf(inventorydata.getUyumsuzlukDurumu());
            String tipBilgisi = String.valueOf(inventorydata.getTipBilgisi());


            inventoryCellBasedDefinitionController.categoryCB.setValue(Category);
            inventoryCellBasedDefinitionController.muhimmatParcaCB.setValue(numberofpieces);
            inventoryCellBasedDefinitionController.dolulukDurumuCB.setValue(dolulukdurumu);
            inventoryCellBasedDefinitionController.isMisfiredCB.setValue(IsMisfired);
            inventoryCellBasedDefinitionController.isMultipleCB.setValue(IsMultiple);
            inventoryCellBasedDefinitionController.isSmartCB.setValue(IsSmart);
            inventoryCellBasedDefinitionController.tekCiftDurumCB.setValue(TekCift);
            inventoryCellBasedDefinitionController.kalanOmurKritiklikDurumuCB.setValue(KalanOmurKrtiklikDurumu);
            inventoryCellBasedDefinitionController.uyumsuzlukCB.setValue(UyumsuzlukDurumu);
            inventoryCellBasedDefinitionController.tipBilgisiCB.setValue(tipBilgisi);


            Scene popupScene = new Scene(popupContent);
            Stage popupStage = new Stage();
            popupStage.setScene(popupScene);
            popupStage.setTitle("HAT " + hatNo + " AMTB ID " + amtbNo + " HUCRE NO : " + hucreNo);
            // popupStage.setTitle("InventoryCellBasedDefinitionController ");
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.show();
            /*
            popupStage.setOnHiding(windowEvent -> {
                InventoryCellState.category newCategory = InventoryCellState.category.valueOf(inventoryCellBasedDefinitionController.categoryCB.getValue());
                InventoryCellState.numberofPieces newnumberofPieces = InventoryCellState.numberofPieces.valueOf(inventoryCellBasedDefinitionController.muhimmatParcaCB.getValue());
                InventoryCellState.dolulukDurumu newdolulukDurumu = InventoryCellState.dolulukDurumu.valueOf(inventoryCellBasedDefinitionController.dolulukDurumuCB.getValue());
                InventoryCellState.isMisfired newisMisfired = InventoryCellState.isMisfired.valueOf(inventoryCellBasedDefinitionController.isMisfiredCB.getValue());
                InventoryCellState.isMultiple newisMultiple = InventoryCellState.isMultiple.valueOf(inventoryCellBasedDefinitionController.isMultipleCB.getValue());
                InventoryCellState.isSmart newisSmart = InventoryCellState.isSmart.valueOf(inventoryCellBasedDefinitionController.isSmartCB.getValue());
                InventoryCellState.TekCift newTekCift = InventoryCellState.TekCift.valueOf(inventoryCellBasedDefinitionController.tekCiftDurumCB.getValue());
                InventoryCellState.kalanOmurKrtiklikDurumu newkalanOmurKritiklikDurumu = InventoryCellState.kalanOmurKrtiklikDurumu.valueOf(inventoryCellBasedDefinitionController.kalanOmurKritiklikDurumuCB.getValue());
                InventoryCellState.uyumsuzlukDurumu newuyumsuzlukDurumu =InventoryCellState.uyumsuzlukDurumu.valueOf(inventoryCellBasedDefinitionController.uyumsuzlukCB.getValue());
                InventoryCellState.tipBilgisi newtipBilgisi = InventoryCellState.tipBilgisi.valueOf(inventoryCellBasedDefinitionController.tipBilgisiCB.getValue());

                inventorydata.setCategory(newCategory);
                inventorydata.setNumberofPieces(newnumberofPieces);
                inventorydata.setDolulukDurumu(newdolulukDurumu);
                inventorydata.setIsMisfired(newisMisfired);
                inventorydata.setIsMultiple(newisMultiple);
                inventorydata.setIsSmart(newisSmart);
                inventorydata.setTekCift(newTekCift);
                inventorydata.setKalanOmurKrtiklikDurumu(newkalanOmurKritiklikDurumu);
                inventorydata.setUyumsuzlukDurumu(newuyumsuzlukDurumu);
                inventorydata.setTipBilgisi(newtipBilgisi);


                System.out.println("amtb : " + amtbNo + " hucreno: " + hucreNo + " Category : " + Category + " Numberofpieces : " + numberofpieces);
            });
          */
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void hucreControlButtonClicked(ActionEvent event) {

        for (int i = 0; i < 32; i++) {
            InventoryCellState inventorydata = InventoryCellStateList.get(i);

            for (int j = 1; j < 31; ++j) {
                System.out.println("AMTB no : " + i + " , Hucre no : " + j + " ,InventoryCellState.category : " + inventorydata.getCategory());
                System.out.println("AMTB no : " + i + " , Hucre no : " + j + " ,InventoryCellState.getIsMultiple : " + inventorydata.getIsMultiple());

            }
        }
    }

    public void appendLog(String message) {
        Platform.runLater(() -> {
            logTextArea.appendText(message + "\n");
            logTextArea.setScrollTop(Double.MAX_VALUE);
        });
    }

    private void redirectSystemStreams() {
        OutputStream out = new OutputStream() {
            private final StringBuilder buffer = new StringBuilder();

            @Override
            public void write(int b) {
                if (b == '\n') {
                    String message = buffer.toString();
                    buffer.setLength(0);
                    appendLog(message); // satır sonu geldikçe log'a yaz
                } else {
                    buffer.append((char) b);
                }
            }
        };

        PrintStream ps = new PrintStream(out, true);
        System.setOut(ps);
        System.setErr(ps);
    }

    @FXML
    private void handleDisconnectButton() {
        if (serialConnection != null) {
            stopListeners(); // Thread'leri durdur
            serialConnection.closeSocket();
            serialConnection = null;
        }
    }

    @FXML
    private void InitializeSerialCom(ActionEvent event) {
        if (serialConnection == null) {
            serialConnection = new SerialConnectionforKKY(this);
        }
        serialConnection.createSocket(); // Bağlantıları kur
        startListeners(); // Dinleme thread'lerini başlat
    }

    private void populateAvailablePorts(ComboBox<String> comboBox) {
        comboBox.getItems().clear();
        comboBox.getItems().add("None");
        SerialPort[] ports = SerialPort.getCommPorts();
        for (SerialPort port : ports) {
            comboBox.getItems().add(port.getSystemPortName());
        }
        //comboBox.getSelectionModel().select("None");
    }


    // Class değişkenleri:

    private BlockingQueue<MessageWithSource> messageQueue;


    public void startListeners() {
        stopListeners();
        listeners.clear();

        if (serialConnection.connectionState_hat1) {
            listeners.add(new ListeningSerial(1, serialConnection.getPort1(), serialConnection.getIn1(), serialConnection.getOut1()));
        }
        if (serialConnection.connectionState_hat2) {
            listeners.add(new ListeningSerial(2, serialConnection.getPort2(), serialConnection.getIn2(), serialConnection.getOut2()));
        }
        if (serialConnection.connectionState_hat3) {
            listeners.add(new ListeningSerial(3, serialConnection.getPort3(), serialConnection.getIn3(), serialConnection.getOut3()));
        }
        if (serialConnection.connectionState_hat4) {
            listeners.add(new ListeningSerial(4, serialConnection.getPort4(), serialConnection.getIn4(), serialConnection.getOut4()));
        }

        messageQueue = new LinkedBlockingQueue<MessageWithSource>();

        serialReader = new SerialReader(listeners, messageQueue);
        messageProcessor = new MessageProcessor(messageQueue, listeners);

        executorService = Executors.newFixedThreadPool(2);
        executorService.submit(serialReader);
        executorService.submit(messageProcessor);
    }



    public void stopListeners() {
/*        if (readerTasks != null) {
            readerTasks.forEach(SerialReader::stop);
            readerTasks.clear();
        }*/
        if (executorService != null && !executorService.isShutdown()) {
            executorService.shutdownNow();
        }
        listeners.clear();
    }

    public String getPortNameForHat(int hatNumber) {
        switch (hatNumber) {
            case 1:
                return hat1PortComboBox.getValue();
            case 2:
                return hat2PortComboBox.getValue();
            case 3:
                return hat3PortComboBox.getValue();
            case 4:
                return hat4PortComboBox.getValue();
            default:
                return null;
        }
    }

}




