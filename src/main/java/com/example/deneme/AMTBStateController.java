package com.example.deneme;

import com.example.deneme.ControllerClasses.*;
import com.example.deneme.MessageIDConstans.AMTBEnums;
import com.example.deneme.MessageIDConstans.AMTBMsgIDConstants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.Collections;
import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.example.deneme.FirmwareMessageController;


public class AMTBStateController implements Initializable {

    @FXML public ComboBox<String> AmtbDurumBilgiCB = new ComboBox<>();

    @FXML public ComboBox<String> AmtbModSecimCB = new ComboBox<>();

    @FXML public ComboBox<String> JettisonStateCB = new ComboBox<>();

    @FXML public ComboBox<String> ArmedCB = new ComboBox<>();

    @FXML public ComboBox<String> IsarmedCB = new ComboBox<>();

    @FXML public ComboBox<String> BlankIRCB = new ComboBox<>();

    @FXML public ComboBox<String> BlankRFCB = new ComboBox<>();

    @FXML public ComboBox<String> DiscreteTestCB = new ComboBox<>();

    @FXML public ComboBox<String> FAILCB = new ComboBox<>();

    @FXML public ComboBox<String> IBITCB = new ComboBox<>();

    @FXML public ComboBox<String> IsAMTB6x5CB = new ComboBox<>();

    @FXML public ComboBox<String> IsFiringActiveCB = new ComboBox<>();

    @FXML public ComboBox<String> MDFCB = new ComboBox<>();

    @FXML public ComboBox<String> MDFLoadingCB = new ComboBox<>();

    @FXML public ComboBox<String> MagCoverCB = new ComboBox<>();

    @FXML public ComboBox<String> MisfireCB = new ComboBox<>();

    @FXML public ComboBox<String> NewFAILCB = new ComboBox<>();

    @FXML public ComboBox<String> NewMisfireCB = new ComboBox<>();

    @FXML public ComboBox<String> RTCCB = new ComboBox<>();

    @FXML public ComboBox<String> SQBPowerCB = new ComboBox<>();

    @FXML public ComboBox<String> STCConnectedCB = new ComboBox<>();

    @FXML public ComboBox<String> WOWCB = new ComboBox<>();

    @FXML public Button firmwareTanımlamaBttn;

    @FXML public Button BITMessageClickButton;

    @FXML public Button blankingMessageBttn;

    @FXML public Button envanterBilgisiTanımlamaBtn;

    @FXML public Button hucreEnvanterTanimiBtn;

    @FXML public Button recordMessageBtn;

    public static int amtbNo = 0;

    public static List<BITResult> BITResultList = new ArrayList<>();

    public static List<FirmwareMessage> FirmwareMessageList = new ArrayList<>();

    public static List<BlankingMessage> BlankingMessageList = new ArrayList<>();

    public static List<ZeroizeResult> ZeroizeResultList = new ArrayList<>();

    public static List<RecordMessageStructure> RecordMessageList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        AmtbDurumBilgiCB.getItems().addAll("AKTIF", "PASIF");
        AmtbModSecimCB.getItems().addAll("AMTB_MODE_OFF", "AMTB_MODE_STBY", "AMTB_MODE_ALIVE", "AMTB_MODE_BYPASS", "AMTB_MODE_ULTRA_BYPASS_FIS", "AMTB_MODE_ULTRA_BYPASS_RIAS", "AMTB_MODE_ULTRA_BYPASS_FIS_RIAS");
        JettisonStateCB.getItems().addAll("JETTISON_STATE_NOT_STARTED", "JETTISON_STATE_RUNNING", "JETTISON_STATE_FINISHED");
        ArmedCB.getItems().addAll("DISARM", "ARM");
        IsarmedCB.getItems().addAll("DISARM", "ARM");
        WOWCB.getItems().addAll("HAVADA", "YERDE");
        DiscreteTestCB.getItems().addAll("PASIF", "AKTIF");
        MDFCB.getItems().addAll("VAR", "YOK");
        MDFLoadingCB.getItems().addAll("VARSAYILAN", "YUKLENMEKTE");
        MagCoverCB.getItems().addAll("KAPALI", "ACIK");
        IBITCB.getItems().addAll("TAMAMLANMADI", "TAMAMLANDI");
        FAILCB.getItems().addAll("ARIZA_YOK", "ARIZA_VAR");
        NewFAILCB.getItems().addAll("YENI_ARIZA_OLUSTU", "YENI_ARIZA_OLUSMADI");
        SQBPowerCB.getItems().addAll("YOK", "VAR");
        RTCCB.getItems().addAll("SET_EDILMEMIS", "SET_EDILMIS");
        STCConnectedCB.getItems().addAll("BAGLI_DEGIL", "BAGLI");
        BlankIRCB.getItems().addAll("PASIF", "AKTIF");
        BlankRFCB.getItems().addAll("PASIF", "AKTIF");
        //IsAMTB6x5CB.getItems().addAll("6x5", "3x10");
        IsAMTB6x5CB.getItems().addAll("SIX_BY_FIVE", "THREE_BY_TEN");
        MisfireCB.getItems().addAll("YOK", "VAR");
        NewMisfireCB.getItems().addAll("OLUSMADI", "OLUSTU");
        IsFiringActiveCB.getItems().addAll("ATIM_YAPILMIYOR", "ATIM_YAPILIYOR");


        for (int b = 0; b < 32; b++) {
            RecordMessageList.add(new RecordMessageStructure(b));
        }

        for (int i = 0; i < 32; i++) {
            BITResultList.add(new BITResult(i));
        }

        for (int j = 0; j < 32; j++){
            FirmwareMessageList.add(new FirmwareMessage(j));
        }

        for (int k = 0; k < 32; k++){
            BlankingMessageList.add(new BlankingMessage(k));
        }

        for (int z = 0; z < 32; z++){
            ZeroizeResultList.add(new ZeroizeResult(z));
        }

    }

    public void setComboBoxValue(String value) {
        //AmtbDurumBilgiCB.getItems().clear();
        //AmtbDurumBilgiCB.getItems().add(value);
        AmtbDurumBilgiCB.setValue(value);
    }
    private AMTBState amtbData;

    public void setData(AMTBState data) {
        this.amtbData = data;
    }


    @FXML
    public void onSaveButtonClicked(ActionEvent event) {
        if (amtbData == null)  throw new IllegalArgumentException("AMTB must not be null");

        // Verileri AMTBState nesnesine aktar
        amtbData.setStateInfo(AMTBState.StateInfo.valueOf(AmtbDurumBilgiCB.getValue()));
        amtbData.setAmtbMode(AMTBState.AmtbMode.valueOf(AmtbModSecimCB.getValue()));
        amtbData.setJettisonState(AMTBState.JettisonState.valueOf(JettisonStateCB.getValue()));
        amtbData.setArmedState(AMTBState.ArmedState.valueOf(ArmedCB.getValue()));
        amtbData.setIsArmed(AMTBState.IsArmed.valueOf(IsarmedCB.getValue()));
        amtbData.setBlankIR(AMTBState.BlankIR.valueOf(BlankIRCB.getValue()));
        amtbData.setBlankRF(AMTBState.BlankRF.valueOf(BlankRFCB.getValue()));
        amtbData.setDiscreteTest(AMTBState.DiscreteTest.valueOf(DiscreteTestCB.getValue()));
        amtbData.setFailState(AMTBState.FAILState.valueOf(FAILCB.getValue()));
        amtbData.setIbit(AMTBState.IBIT.valueOf(IBITCB.getValue()));
        amtbData.setIsAMTB6x5(AMTBState.IsAMTB6x5.valueOf(IsAMTB6x5CB.getValue()));
        amtbData.setIsFiringActive(AMTBState.IsFiringActive.valueOf(IsFiringActiveCB.getValue()));
        amtbData.setMdfState(AMTBState.MDFState.valueOf(MDFCB.getValue()));
        amtbData.setMdfLoadingState(AMTBState.MDFLoadingState.valueOf(MDFLoadingCB.getValue()));
        amtbData.setMagCoverState(AMTBState.MagCoverState.valueOf(MagCoverCB.getValue()));
        amtbData.setMisfire(AMTBState.Misfire.valueOf(MisfireCB.getValue()));
        amtbData.setNewFail(AMTBState.NewFail.valueOf(NewFAILCB.getValue()));
        amtbData.setNewMisfire(AMTBState.NewMisfire.valueOf(NewMisfireCB.getValue()));
        amtbData.setRtc(AMTBState.RTC.valueOf(RTCCB.getValue()));
        amtbData.setSqbPower(AMTBState.SQBPower.valueOf(SQBPowerCB.getValue()));
        amtbData.setStcConnected(AMTBState.STCConnected.valueOf(STCConnectedCB.getValue()));
        amtbData.setWowState(AMTBState.WOWState.valueOf(WOWCB.getValue()));

        // Aktif AMTB listesini güncelle
        String active = amtbData.getStateInfo().toString();
        if ("AKTIF".equals(active)) {
                System.out.println("Kaydedildi");
            if (!AMTB_Sim_Controller.getAktifAmtbIdList().contains(AMTB_Sim_Controller.getAmtbNo())) {
                AMTB_Sim_Controller.getAktifAmtbIdList().add(AMTB_Sim_Controller.getAmtbNo());
            }
        } else if ("PASIF".equals(active)) {
            AMTB_Sim_Controller.getAktifAmtbIdList().remove(Integer.valueOf(AMTB_Sim_Controller.getAmtbNo()));
        }
        Collections.sort(AMTB_Sim_Controller.getAktifAmtbIdList());
        System.out.println("Aktif AMTB listesi: " + AMTB_Sim_Controller.getAktifAmtbIdList());

        // Kaydet sonrası pencereyi kapat
       // ((Stage)((Button)event.getSource()).getScene().getWindow()).close();
    }
    @FXML void AmtbModSecimCBCBonAction(ActionEvent event) {

        String amtbModesStr = AmtbModSecimCB.getValue();
        byte amtbModeByte = AMTBEnums.getAmtbModesInt_e(amtbModesStr);

        System.out.println("AmtbModSecimCBCBonAction");
        System.out.println("amtbModesStr : " + amtbModesStr + " , amtbModeByte : " + amtbModeByte);

    }

    @FXML void AmtbJettisonStateCBAction(ActionEvent event) {

        String JettisonStateStr = JettisonStateCB.getValue();
        byte JettisonStateByte = AMTBEnums.getAmtbJettisonStateInt_e(JettisonStateStr);

        System.out.println("AmtbJettisonStateCBAction");
        System.out.println("JettisonStateStr : " + JettisonStateStr + " , JettisonStateByte : " + JettisonStateByte);

    }

    @FXML void AmtbArmedCBAction(ActionEvent event) {

        String AmtbArmedStr = ArmedCB.getValue();
        byte AmtbArmedByte = (byte) (AmtbArmedStr.equals("DISARM") ? 0 : 1);

        System.out.println("AmtbArmedCBAction");
        System.out.println(" AmtbArmedStr : " + AmtbArmedStr + " ,AmtbArmedByte  : " + AmtbArmedByte);

    }

    @FXML void AmtbIsArmedCBAction(ActionEvent event){
        String AmtbIsArmedStr = IsarmedCB.getValue();
        byte AmtbIsArmedByte = (byte)(AmtbIsArmedStr.equals("DISARM") ? 0 : 1);

        System.out.println("AmtbIsArmedCBAction");
        System.out.println(" AmtbIsArmedStr : " + AmtbIsArmedStr + " , AmtbIsArmedByte : " + AmtbIsArmedByte);

    }

    @FXML void AmtbWOWCBAction(ActionEvent event){
        String AmtbWOWStr = WOWCB.getValue();
        byte AmtbWOWByte = (byte)(AmtbWOWStr.equals("HAVADA") ? 0 : 1);

        System.out.println("AmtbWOWCBAction");
        System.out.println(" AmtbWOWStr : " + AmtbWOWStr +" , AmtbWOWByte : " + AmtbWOWByte);

    }

    @FXML void AmtbDiscreteTestCBAction(ActionEvent event){
        String AmtbDiscreteTestStr = DiscreteTestCB.getValue();
        byte AmtbDiscreteTestByte = (byte)(AmtbDiscreteTestStr.equals("PASIF") ? 0 : 1);

        System.out.println("AmtbDiscreteTestCBAction");
        System.out.println(" AmtbDiscreteTestStr : " + AmtbDiscreteTestStr  + " , AmtbDiscreteTestByte : " + AmtbDiscreteTestByte);

    }

    @FXML void AmtbMDFCBAction(ActionEvent event){
        String AmtbMDFStr = MDFCB.getValue();
        byte AmtbMDFByte = (byte)(AmtbMDFStr.equals("YOK") ? 0 : 1);

        System.out.println("AmtbMDFCBAction");
        System.out.println(" AmtbMDFStr : " + AmtbMDFStr + " , AmtbMDFByte : " + AmtbMDFByte);

    }

    @FXML void AmtbMDFLoadingCBAction(ActionEvent event){
        String AmtbMDFLoadingStr = MDFLoadingCB.getValue();
        byte AmtbMDFLoadingByte = (byte)(AmtbMDFLoadingStr.equals("VARSAYILAN") ? 0 : 1);

        System.out.println("AmtbMDFLoadingCBAction");
        System.out.println(" AmtbMDFLoadingStr : "+ AmtbMDFLoadingStr + " , AmtbMDFLoadingByte : " + AmtbMDFLoadingByte);

    }

    @FXML void AmtbMagCoverCBAction(ActionEvent event){
        String AmtbMagCoverStr = MagCoverCB.getValue();
        byte AmtbMagCoverByte = (byte)(AmtbMagCoverStr.equals("KAPALI") ? 0 : 1);

        System.out.println("AmtbMagCoverCBAction");
        System.out.println(" AmtbMagCoverStr : " + AmtbMagCoverStr + " , AmtbMagCoverByte : " + AmtbMagCoverByte);

    }

    @FXML void AmtbIBITCBAction(ActionEvent event){
        String AmtbIBITStr = IBITCB.getValue();
        byte AmtbIBITByte = (byte)(AmtbIBITStr.equals("TAMAMLANMADI") ? 0 : 1);

        System.out.println("AmtbIBITCBAction");
        System.out.println(" AmtbIBITStr : " + AmtbIBITStr + " , AmtbIBITByte : " + AmtbIBITByte);

    }

    @FXML void AmtbFAILCBAction(ActionEvent event){
        String AmtbFAILStr = FAILCB.getValue();
        byte AmtbFAILByte = (byte)(AmtbFAILStr.equals("ARIZA YOK") ? 0 : 1);

        System.out.println("AmtbFAILCBAction");
        System.out.println(" AmtbFAILStr : " + AmtbFAILStr + " , AmtbFAILByte : " + AmtbFAILByte);

    }

    @FXML void AmtbNewFAILCBAction(ActionEvent event){
        String AmtbNewFAILStr = NewFAILCB.getValue();
        byte AmtbNewFAILByte = (byte)(AmtbNewFAILStr.equals("YENI ARIZA OLUSMADI") ? 0 : 1);

        System.out.println("AmtbNewFAILCBAction");
        System.out.println(" AmtbNewFAILStr : " + AmtbNewFAILStr + " , AmtbNewFAILByte : " + AmtbNewFAILByte);

    }

    @FXML void AmtbSQBPowerCBAction(ActionEvent event){
        String AmtbSQBPowerStr = SQBPowerCB.getValue();
        byte AmtbSQBPowerByte = (byte)(AmtbSQBPowerStr.equals("YOK") ? 0 : 1);

        System.out.println("AmtbSQBPowerCBAction");
        System.out.println(" AmtbSQBPowerStr : " + AmtbSQBPowerStr + " , AmtbSQBPowerByte : " + AmtbSQBPowerByte);

    }

    @FXML void AmtbRTCCBAction(ActionEvent event){
        String AmtbRTCStr = RTCCB.getValue();
        byte AmtbRTCByte = (byte)(AmtbRTCStr.equals("SET EDILMEMIS") ? 0 : 1);

        System.out.println("AmtbRTCCBAction");
        System.out.println(" AmtbRTCStr : " + AmtbRTCStr + " , AmtbRTCByte : " + AmtbRTCByte);

    }

    @FXML void AmtbSTCConnectedCBAction(ActionEvent event){
        String AmtbSTCConnectedStr = STCConnectedCB.getValue();
        byte AmtbSTCConnectedByte = (byte)(AmtbSTCConnectedStr.equals("BAGLI DEGIL") ? 0 : 1);

        System.out.println("AmtbSTCConnectedCBAction");
        System.out.println(" AmtbSTCConnectedStr : " + AmtbSTCConnectedStr + " , AmtbSTCConnectedByte : " + AmtbSTCConnectedByte);

    }

    @FXML void AmtbBlankIRCBAction(ActionEvent event){
        String AmtbBlankIRStr = BlankIRCB.getValue();
        byte AmtbBlankIRByte = (byte)(AmtbBlankIRStr.equals("PASIF") ? 0 : 1);

        System.out.println("AmtbBlankIRCBAction");
        System.out.println(" AmtbBlankIRStr : " + AmtbBlankIRStr + " , AmtbBlankIRByte : " + AmtbBlankIRByte);

    }

    @FXML void AmtbBlankRFCBAction(ActionEvent event){
        String AmtbBlankRFStr = BlankRFCB.getValue();
        byte AmtbBlankRFByte = (byte)(AmtbBlankRFStr.equals("PASIF") ? 0 : 1);

        System.out.println("AmtbBlankRFCBAction");
        System.out.println(" AmtbBlankRFStr : " + AmtbBlankRFStr + " , AmtbBlankRFByte : " + AmtbBlankRFByte);

    }

    @FXML void AmtbisAMTB6x5CBAction(ActionEvent event){
        String AmtbisAMTB6x5CBAction = IsAMTB6x5CB.getValue();
        //byte AmtbisAMTB6x5CBActionByte = (byte)(AmtbisAMTB6x5CBAction.equals("6x5") ? 0 : 1);
        byte AmtbisAMTB6x5CBActionByte = (byte)(AmtbisAMTB6x5CBAction.equals("SIX_BY_FIVE") ? 0 : 1);

        System.out.println("AmtbisAMTB6x5CBAction");
        System.out.println(" AmtbisAMTB6x5CBAction : " + AmtbisAMTB6x5CBAction + " , AmtbisAMTB6x5CBActionByte : " + AmtbisAMTB6x5CBActionByte);

    }

    @FXML void AmtbMisfireCBAction(ActionEvent event){
        String AmtbMisfireStr = MisfireCB.getValue();
        byte AmtbMisfireStrByte = (byte)(AmtbMisfireStr.equals("YOK") ? 0 : 1);

        System.out.println("AmtbMisfireCBAction");
        System.out.println(" AmtbMisfireStr : " + AmtbMisfireStr + " , AmtbMisfireStrByte : " + AmtbMisfireStrByte);

    }

    @FXML void AmtbNewMisfireCBAction(ActionEvent event){
        String AmtbNewMisfireStr = NewMisfireCB.getValue();
        byte AmtbNewMisfireByte = (byte)(AmtbNewMisfireStr.equals("OLUSMADI") ? 0 : 1);

        System.out.println("AmtbNewMisfireCBAction");
        System.out.println(" AmtbNewMisfireStr : " + AmtbNewMisfireStr + " , AmtbNewMisfireByte : " + AmtbNewMisfireByte);

    }

    @FXML void AmtbIsFiringActiveCBAction(ActionEvent event){
        String AmtbIsFiringActiveStr = IsFiringActiveCB.getValue();
        byte AmtbIsFiringActiveByte = (byte)(AmtbIsFiringActiveStr.equals("ATIM YAPILMIYOR") ? 0 : 1);

        System.out.println("AmtbIsFiringActiveCBAction");
        System.out.println(" AmtbIsFiringActiveStr : " + AmtbIsFiringActiveStr + " , AmtbIsFiringActiveByte : " + AmtbIsFiringActiveByte);

    }

    @FXML void AmtbDurumBilgiCBAction(ActionEvent event){
        String AmtbDurumBilgiActiveStr = AmtbDurumBilgiCB.getValue();
        byte AmtbDurumBilgiActiveByte = (byte)(AmtbDurumBilgiActiveStr.equals("PASIF") ? 0 : 1);

        System.out.println("AmtbDurumBilgiCBAction");
        System.out.println(" AmtbDurumBilgiActiveStr : " + AmtbDurumBilgiActiveStr + " , AmtbDurumBilgiActiveByte : " + AmtbDurumBilgiActiveByte);

    }


    @FXML void clickFirmwareTanimlamaBtn (ActionEvent event ){

        System.out.println("Firmware  Message buttonuna basildi  +  amtb no :  " + amtbNo);
        clickFirmwareTanimlamaBtn(AMTB_Sim_Controller.g_amtbHatId,AMTB_Sim_Controller.g_amtbNo);


}

    protected void clickFirmwareTanimlamaBtn(int hatNo, int amtbNo) {
        System.out.println("FIRMWARE Message buttonuna basildi hatNo : " + hatNo + " amtbNo : " + amtbNo);

        try {
            FXMLLoader loader = new FXMLLoader(FirmwareMessageController.class.getResource("FirmwareMessage.fxml"));
            AnchorPane popupContent = loader.load();

            FirmwareMessageController firmwareMessageController = loader.getController();

            FirmwareMessage firmwareMessageData = FirmwareMessageList.get(amtbNo);

            String configuration  = String.valueOf(firmwareMessageData.getConfiguration());

            firmwareMessageController.firmwareVersionCB.setValue(configuration);


            Scene popupScene = new Scene(popupContent);
            Stage popupStage = new Stage();
            popupStage.setScene(popupScene);
            popupStage.setTitle("FIRMWARE MESAJLARI " + "HAT " + hatNo + " AMTB ID " + amtbNo );
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.show();

            popupStage.setOnHiding(windowEvent -> {
                FirmwareMessage.Configuration newconfiguration  = FirmwareMessage.Configuration.valueOf(firmwareMessageController.firmwareVersionCB.getValue());
                System.out.println("hat id : " + hatNo + " amtb id : " + amtbNo + " configuration : " +  newconfiguration );


                firmwareMessageData.setConfiguration(newconfiguration);


            });

        }
    catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML void clickBlankingMessageBtn(ActionEvent event ){

        System.out.println("Blanking  Message buttonuna basildi  +  amtb no :  " + amtbNo);
        clickBlankingMessageBtn(AMTB_Sim_Controller.g_amtbHatId,AMTB_Sim_Controller.g_amtbNo);
    }


    protected void clickBlankingMessageBtn(int hatNo, int amtbNo) {
        System.out.println("Blanking Message buttonuna basildi hatNo : " + hatNo + " amtbNo : " + amtbNo);

        try {
            FXMLLoader loader = new FXMLLoader(BlankingMessageController.class.getResource("BlankingMessage.fxml"));
            AnchorPane popupContent = loader.load();

            BlankingMessageController blankingMessageController = loader.getController();

            BlankingMessage blankingMessageData = BlankingMessageList.get(amtbNo);

            String category  = String.valueOf(blankingMessageData.getcategory());
            String direction  = String.valueOf(blankingMessageData.getdirection());
            String operation  = String.valueOf(blankingMessageData.getoperation());

            blankingMessageController.categoryCB.setValue(category);
            blankingMessageController.directionCB.setValue(direction);
            blankingMessageController.operationCB.setValue(operation);


            Scene popupScene = new Scene(popupContent);
            Stage popupStage = new Stage();
            popupStage.setScene(popupScene);
            popupStage.setTitle("BLANKING MESAJLARI " + "HAT " + hatNo + " AMTB ID " + amtbNo );
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.show();

            popupStage.setOnHiding(windowEvent -> {
                BlankingMessage.category newcategory  = BlankingMessage.category.valueOf(blankingMessageController.categoryCB.getValue());
                System.out.println("hat id : " + hatNo + " amtb id : " + amtbNo + " categoryCB : " +  newcategory );

                BlankingMessage.direction newdirection  = BlankingMessage.direction.valueOf(blankingMessageController.directionCB.getValue());
                System.out.println("hat id : " + hatNo + " amtb id : " + amtbNo + " directionCB : " +  newdirection );

                BlankingMessage.operation newoperation  = BlankingMessage.operation.valueOf(blankingMessageController.operationCB.getValue());
                System.out.println("hat id : " + hatNo + " amtb id : " + amtbNo + " operationCB : " +  newoperation );



                blankingMessageData.setcategory(newcategory);
                blankingMessageData.setdirection(newdirection);
                blankingMessageData.setoperation(newoperation);

            });

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML void clickZeroizeResultBtn(ActionEvent event ){

        System.out.println("Zeroize Result Message buttonuna basildi  +  amtb no :  " + amtbNo);
        clickZeroizeResultBtn(AMTB_Sim_Controller.g_amtbHatId,AMTB_Sim_Controller.g_amtbNo);
    }


    protected void clickZeroizeResultBtn(int hatNo, int amtbNo) {
        System.out.println("Zeroize Result buttonuna basildi hatNo : " + hatNo + " amtbNo : " + amtbNo);

        try {
            FXMLLoader loader = new FXMLLoader(BlankingMessageController.class.getResource("ZeroizeResult.fxml"));
            AnchorPane popupContent = loader.load();

            ZeroizeResultController zeroizeResultController = loader.getController();

            ZeroizeResult zeroizeResultData = ZeroizeResultList.get(amtbNo);

            String results  = String.valueOf(zeroizeResultData.getResults());


            zeroizeResultController.zeroizeResultSelectCB.setValue(results);

            Scene popupScene = new Scene(popupContent);
            Stage popupStage = new Stage();
            popupStage.setScene(popupScene);
            popupStage.setTitle("ZEROIZE MESAJLARI " + "HAT " + hatNo + " AMTB ID " + amtbNo );
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.show();

            popupStage.setOnHiding(windowEvent -> {
                ZeroizeResult.results newresults  = ZeroizeResult.results.valueOf(zeroizeResultController.zeroizeResultSelectCB.getValue());
                System.out.println("hat id : " + hatNo + " amtb id : " + amtbNo + " resultsCB : " +  newresults );



                zeroizeResultData.setResults(newresults);

            });

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML void clickRecordMessageBtn(ActionEvent event ){

        System.out.println(" Record Message buttonuna basildi  +  amtb no :  " + amtbNo);
        clickRecordMessageBtn(AMTB_Sim_Controller.g_amtbHatId,AMTB_Sim_Controller.g_amtbNo);
    }


    protected void clickRecordMessageBtn(int hatNo, int amtbNo) {
        System.out.println("Record Message buttonuna basildi hatNo : " + hatNo + " amtbNo : " + amtbNo);

        try {

            FXMLLoader loader = new FXMLLoader(BlankingMessageController.class.getResource("RecordMessage.fxml"));
            AnchorPane popupContent = loader.load();

            RecordMessageController recordMessageController = loader.getController();

            RecordMessageStructure recordMessageData = RecordMessageList.get(amtbNo);

            String recordType  = String.valueOf(recordMessageData.getRecordType());

            recordMessageController.recordTypeCB.setValue(recordType);

            recordMessageController.eventTextfield.setText(String.valueOf(recordMessageData.getEventCount()));
            recordMessageController.faultTextfield.setText(String.valueOf(recordMessageData.getFaultCount()));
            recordMessageController.misfireTextfield.setText(String.valueOf(recordMessageData.getMisfireCount()));

            Scene popupScene = new Scene(popupContent);
            Stage popupStage = new Stage();
            popupStage.setScene(popupScene);
            popupStage.setTitle("Record MESAJLARI " + "HAT " + hatNo + " AMTB ID " + amtbNo );
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.show();




            popupStage.setOnHiding(windowEvent -> {
                RecordMessageStructure.RecordType newRecordType  = RecordMessageStructure.RecordType.valueOf(recordMessageController.recordTypeCB.getValue());
                System.out.println("hat ıd : " + hatNo + " amtb ıd : " + amtbNo + " recordTypeCB : " +  newRecordType );

                int eventcount = Integer.parseInt(recordMessageController.eventTextfield.getText());
                int faultcount = Integer.parseInt(recordMessageController.faultTextfield.getText());
                int misfirecount = Integer.parseInt(recordMessageController.misfireTextfield.getText());

                System.out.println("eventcount  : " + eventcount);
                System.out.println("faultcount  : " + faultcount);
                System.out.println("misfirecount  : " + misfirecount);

                recordMessageData.setRecordType(newRecordType);
                recordMessageData.setEventCount(eventcount);
                recordMessageData.setFaultCount(faultcount);
                recordMessageData.setMisfireCount(misfirecount);

            });

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML void clickhucreEnvanterTanimiBtn (ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(AMTBStateController.class.getResource("AMTBCellButtons.fxml"));
            AnchorPane popupContent = loader.load();

            //EditDialogBoxController editdialogcontroller = loader.getController();
            AMTBCellButtonsController AMTBCellButtonsController = loader.getController();
            Scene popupScene = new Scene(popupContent);
            Stage popupStage = new Stage();

            popupStage.setScene(popupScene);
            popupStage.setTitle("HUCRE BAZLI ENVANTER TANIMI");
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    @FXML void BITMessageClickButton(ActionEvent event) {
        /*
        try {
            FXMLLoader loader = new FXMLLoader(BITResultController.class.getResource("BITResultMessage.fxml"));
            AnchorPane popupContent = loader.load();

            BITResultController bitResultController = loader.getController();
            Scene popupScene = new Scene(popupContent);
            Stage popupStage = new Stage();

            popupStage.setScene(popupScene);
            popupStage.setTitle("BIT MESAJLARI GORUNTULEME");
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        */

        BITMessageClickButton(AMTB_Sim_Controller.g_amtbHatId,AMTB_Sim_Controller.g_amtbNo);
    }

    protected  void BITMessageClickButton(int hatNo, int amtbNo){
        System.out.println("BIT Message buttonuna basildi hatNo : " + hatNo + " amtbNo : " + amtbNo);

        try{
            FXMLLoader loader = new FXMLLoader(BITResultController.class.getResource("BITResultMessage.fxml"));
            AnchorPane popupContent = loader.load();

            BITResultController bitResultController = loader.getController();

            BITResult bitResultData = BITResultList.get(amtbNo);

            String eeprom = String.valueOf(bitResultData.getEeprom());
            String mybComm = String.valueOf(bitResultData.getMybComm());
            String scanVoltage = String.valueOf(bitResultData.getScanVoltage());
            String smartComm1 = String.valueOf(bitResultData.getSmartComm1());
            String smartComm2 = String.valueOf(bitResultData.getSmartComm2());
            String sqbCurrent1 = String.valueOf(bitResultData.getSqbCurrent1());
            String sqbCurrent2 = String.valueOf(bitResultData.getSqbCurrent2());
            String sqbVoltage1 = String.valueOf(bitResultData.getSqbVoltage1());
            String sqbVoltage2 = String.valueOf(bitResultData.getSqbVoltage2());
            String sqbVoltageIn = String.valueOf(bitResultData.getsqbVoltageIn());
            String sqbVoltageArm = String.valueOf(bitResultData.getSqbVoltageArm());
            String loadCh1 = String.valueOf(bitResultData.getLoadCh1());
            String loadCh2 = String.valueOf(bitResultData.getLoadCh2());
            String voltageLeakCh1 = String.valueOf(bitResultData.getVoltageLeakCh1());
            String voltageLeakCh2 = String.valueOf(bitResultData.getVoltageLeakCh2());
            String currentAboveCh1 = String.valueOf(bitResultData.getCurrentAboveCh1());
            String currentAboveCh2 = String.valueOf(bitResultData.getCurrentAboveCh2());
            String currentBelowCh1 = String.valueOf(bitResultData.getCurrentBelowCh1());
            String currentBelowCh2 = String.valueOf(bitResultData.getCurrentBelowCh2());
            String dischargeCh1 = String.valueOf(bitResultData.getDischargeCh1());
            String dischargeCh2 = String.valueOf(bitResultData.getDischargeCh2());
            String currentLeakCh1 = String.valueOf(bitResultData.getCurrentLeakCh1());
            String currentLeakCh2 = String.valueOf(bitResultData.getCurrentLeakCh2());
            String eepromWrite = String.valueOf(bitResultData.getEepromWrite());
            String magazineCover = String.valueOf(bitResultData.getMagazineCover());
            String logbackup = String.valueOf(bitResultData.getLogbackup());
            String ram = String.valueOf(bitResultData.getRAM());

            bitResultController.eepromCB.setValue(eeprom);
            bitResultController.mybcommCB.setValue(mybComm);
            bitResultController.scanvoltageCB.setValue(scanVoltage);
            bitResultController.smartcomm1CB.setValue(smartComm1);
            bitResultController.smartcomm2CB.setValue(smartComm2);
            bitResultController.sqbcurrent1CB.setValue(sqbCurrent1);
            bitResultController.sqbcurrent2CB.setValue(sqbCurrent2);
            bitResultController.sqbvoltage1CB.setValue(sqbVoltage1);
            bitResultController.sqbvoltage2CB.setValue(sqbVoltage2);
            bitResultController.sqbvoltageinCB.setValue(sqbVoltageIn);
            bitResultController.sqbvoltagearmCB.setValue(sqbVoltageArm);
            bitResultController.loadch1CB.setValue(loadCh1);
            bitResultController.loadch2CB.setValue(loadCh2);
            bitResultController.voltageleakch1CB.setValue(voltageLeakCh1);
            bitResultController.voltageleakch2CB.setValue(voltageLeakCh2);
            bitResultController.currentabovech1CB.setValue(currentAboveCh1);
            bitResultController.currentabovech2CB.setValue(currentAboveCh2);
            bitResultController.currentbelowch1CB.setValue(currentBelowCh1);
            bitResultController.currentbelowch2CB.setValue(currentBelowCh2);
            bitResultController.dischargech1CB.setValue(dischargeCh1);
            bitResultController.dischargech2CB.setValue(dischargeCh2);
            bitResultController.currentleakch1CB.setValue(currentLeakCh1);
            bitResultController.currentleakch2CB.setValue(currentLeakCh2);
            bitResultController.eepromwriteCB.setValue(eepromWrite);
            bitResultController.magazinecoverCB.setValue(magazineCover);
            bitResultController.logBackupCB.setValue(logbackup);
            bitResultController.ramCB.setValue(ram);


            Scene popupScene = new Scene(popupContent);
            Stage popupStage = new Stage();
            popupStage.setScene(popupScene);
            popupStage.setTitle("BIT MESAJLARI " + "HAT " + hatNo + " AMTB ID " + amtbNo );
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.show();

            popupStage.setOnHiding(windowEvent -> {
                BITResult.eeprom neweeprom = BITResult.eeprom.valueOf(bitResultController.eepromCB.getValue());
                BITResult.mybComm newmybComm = BITResult.mybComm.valueOf(bitResultController.mybcommCB.getValue());
                BITResult.scanVoltage newscanVoltage = BITResult.scanVoltage.valueOf(bitResultController.scanvoltageCB.getValue());
                BITResult.smartComm1 newsmartComm1 = BITResult.smartComm1.valueOf(bitResultController.smartcomm1CB.getValue());
                BITResult.smartComm2 newsmartComm2 = BITResult.smartComm2.valueOf(bitResultController.smartcomm2CB.getValue());
                BITResult.sqbCurrent1 newsqbCurrent1 = BITResult.sqbCurrent1.valueOf(bitResultController.sqbcurrent1CB.getValue());
                BITResult.sqbCurrent2 newsqbCurrent2 = BITResult.sqbCurrent2.valueOf(bitResultController.sqbcurrent2CB.getValue());
                BITResult.sqbVoltage1 newsqbVoltage1 = BITResult.sqbVoltage1.valueOf(bitResultController.sqbvoltage1CB.getValue());
                BITResult.sqbVoltage2 newsqbVoltage2 = BITResult.sqbVoltage2.valueOf(bitResultController.sqbvoltage2CB.getValue());
                BITResult.sqbVoltageArm newsqbVoltageArm = BITResult.sqbVoltageArm.valueOf(bitResultController.sqbvoltageinCB.getValue());
                BITResult.sqbVoltageIn newsqbVoltageIn = BITResult.sqbVoltageIn.valueOf(bitResultController.sqbvoltagearmCB.getValue());
                BITResult.loadCh1 newloadCh1 = BITResult.loadCh1.valueOf(bitResultController.loadch1CB.getValue());
                BITResult.loadCh2 newloadCh2 = BITResult.loadCh2.valueOf(bitResultController.loadch2CB.getValue());
                BITResult.voltageLeakCh1 newvoltageLeakCh1 = BITResult.voltageLeakCh1.valueOf(bitResultController.voltageleakch1CB.getValue());
                BITResult.voltageLeakCh2 newvoltageLeakCh2 = BITResult.voltageLeakCh2.valueOf(bitResultController.voltageleakch2CB.getValue());
                BITResult.currentAboveCh1 newcurrentAboveCh1 = BITResult.currentAboveCh1.valueOf(bitResultController.currentabovech1CB.getValue());
                BITResult.currentAboveCh2 newcurrentAboveCh2 = BITResult.currentAboveCh2.valueOf(bitResultController.currentabovech2CB.getValue());
                BITResult.currentBelowCh1 newcurrentBelowCh1 = BITResult.currentBelowCh1.valueOf(bitResultController.currentbelowch1CB.getValue());
                BITResult.currentBelowCh2 newcurrentBelowCh2 = BITResult.currentBelowCh2.valueOf(bitResultController.currentbelowch2CB.getValue());
                BITResult.dischargeCh1 newdischargeCh1 = BITResult.dischargeCh1.valueOf(bitResultController.dischargech1CB.getValue());
                BITResult.dischargeCh2 newdischargeCh2 = BITResult.dischargeCh2.valueOf(bitResultController.dischargech2CB.getValue());
                BITResult.currentLeakCh1 newcurrentLeakCh1 = BITResult.currentLeakCh1.valueOf(bitResultController.currentleakch1CB.getValue());
                BITResult.currentLeakCh2 newcurrentLeakCh2 = BITResult.currentLeakCh2.valueOf(bitResultController.currentleakch2CB.getValue());
                BITResult.eepromWrite neweepromWrite = BITResult.eepromWrite.valueOf(bitResultController.eepromwriteCB.getValue());
                BITResult.magazineCover newmagazineCover = BITResult.magazineCover.valueOf(bitResultController.magazinecoverCB.getValue());
                BITResult.logbackup newlogbackup = BITResult.logbackup.valueOf(bitResultController.logBackupCB.getValue());
                BITResult.ram newram = BITResult.ram.valueOf(bitResultController.ramCB.getValue());

                System.out.println("hat id : " + hatNo + " amtb id : " + amtbNo + " eepromCB : " + neweeprom );
                System.out.println("hat id : " + hatNo + " amtb id : " + amtbNo + " mybcommCB : " + newmybComm);
                System.out.println("hat id : " + hatNo + " amtb id : " + amtbNo + " scanVoltage : " + newscanVoltage );
                System.out.println("hat id : " + hatNo + " amtb id : " + amtbNo + " smartComm1 : " + newsmartComm1);
                System.out.println("hat id : " + hatNo + " amtb id : " + amtbNo + " smartComm2 : " + newsmartComm2 );
                System.out.println("hat id : " + hatNo + " amtb id : " + amtbNo + " sqbCurrent1 : " + newsqbCurrent1);
                System.out.println("hat id : " + hatNo + " amtb id : " + amtbNo + " sqbCurrent2 : " + newsqbCurrent2 );
                System.out.println("hat id : " + hatNo + " amtb id : " + amtbNo + " sqbVoltage1 : " + newsqbVoltage1);
                System.out.println("hat id : " + hatNo + " amtb id : " + amtbNo + " sqbVoltage2 : " + newsqbVoltage2);
                System.out.println("hat id : " + hatNo + " amtb id : " + amtbNo + " sqbVoltageArm : " + newsqbVoltageArm);
                System.out.println("hat id : " + hatNo + " amtb id : " + amtbNo + " sqbVoltageIn : " + newsqbVoltageIn );
                System.out.println("hat id : " + hatNo + " amtb id : " + amtbNo + " loadCh1 : " + newloadCh1);
                System.out.println("hat id : " + hatNo + " amtb id : " + amtbNo + " loadCh2 : " + newloadCh2 );
                System.out.println("hat id : " + hatNo + " amtb id : " + amtbNo + " voltageLeakCh1 : " + newvoltageLeakCh1);
                System.out.println("hat id : " + hatNo + " amtb id : " + amtbNo + " voltageLeakCh2 : " + newvoltageLeakCh2 );
                System.out.println("hat id : " + hatNo + " amtb id : " + amtbNo + " currentAboveCh1 : " + newcurrentAboveCh1);
                System.out.println("hat id : " + hatNo + " amtb id : " + amtbNo + " currentAboveCh2 : " + newcurrentAboveCh2 );
                System.out.println("hat id : " + hatNo + " amtb id : " + amtbNo + " currentBelowCh1 : " + newcurrentBelowCh1);
                System.out.println("hat id : " + hatNo + " amtb id : " + amtbNo + " currentBelowCh2 : " + newcurrentBelowCh2 );
                System.out.println("hat id : " + hatNo + " amtb id : " + amtbNo + " dischargeCh1 : " + newdischargeCh1);
                System.out.println("hat id : " + hatNo + " amtb id : " + amtbNo + " dischargeCh2 : " + newdischargeCh2 );
                System.out.println("hat id : " + hatNo + " amtb id : " + amtbNo + " currentLeakCh1 : " + newcurrentLeakCh1);
                System.out.println("hat id : " + hatNo + " amtb id : " + amtbNo + " currentLeakCh2 : " + newcurrentLeakCh2 );
                System.out.println("hat id : " + hatNo + " amtb id : " + amtbNo + " eepromWrite : " + neweepromWrite);
                System.out.println("hat id : " + hatNo + " amtb id : " + amtbNo + " magazineCover : " + newmagazineCover );
                System.out.println("hat id : " + hatNo + " amtb id : " + amtbNo + " logbackup : " + newlogbackup);
                System.out.println("hat id : " + hatNo + " amtb id : " + amtbNo + " ram : " + newram);




                bitResultData.setEeprom(neweeprom);
                bitResultData.setMybComm(newmybComm);
                bitResultData.setScanVoltage(newscanVoltage);

                bitResultData.setSmartComm1(newsmartComm1);
                bitResultData.setSmartComm2(newsmartComm2);

                bitResultData.setSqbCurrent1(newsqbCurrent1);
                bitResultData.setSqbCurrent2(newsqbCurrent2);

                bitResultData.setSqbVoltage1(newsqbVoltage1);
                bitResultData.setSqbVoltage2(newsqbVoltage2);

                bitResultData.setsqbVoltageIn(newsqbVoltageIn);
                bitResultData.setSqbVoltageArm(newsqbVoltageArm);

                bitResultData.setLoadCh1(newloadCh1);
                bitResultData.setLoadCh2(newloadCh2);

                bitResultData.setVoltageLeakCh1(newvoltageLeakCh1);
                bitResultData.setVoltageLeakCh2(newvoltageLeakCh2);

                bitResultData.setCurrentAboveCh1(newcurrentAboveCh1);
                bitResultData.setCurrentAboveCh2(newcurrentAboveCh2);

                bitResultData.setCurrentBelowCh1(newcurrentBelowCh1);
                bitResultData.setCurrentBelowCh2(newcurrentBelowCh2);

                bitResultData.setDischargeCh1(newdischargeCh1);
                bitResultData.setDischargeCh2(newdischargeCh2);

                bitResultData.setCurrentLeakCh1(newcurrentLeakCh1);
                bitResultData.setCurrentLeakCh2(newcurrentLeakCh2);

                bitResultData.setEepromWrite(neweepromWrite);
                bitResultData.setMagazineCover(newmagazineCover);

                bitResultData.setLogbackup(newlogbackup);
                bitResultData.setRAM(newram);



            });

        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML protected void amtbStateSetDefaultButtonClicked() {

        System.out.println("amtbStateSetDefaultButtonClicked : " + amtbNo);
        AmtbDurumBilgiCB.setValue("AKTIF");
        AmtbModSecimCB.setValue("AMTB_MODE_ALIVE");
        JettisonStateCB.setValue("JETTISON_STATE_NOT_STARTED");
        ArmedCB.setValue("DISARM");
        IsarmedCB.setValue("ARM");
        WOWCB.setValue("HAVADA");
        DiscreteTestCB.setValue("PASIF");
        MDFCB.setValue("VAR");
        MDFLoadingCB.setValue("VARSAYILAN");
        MagCoverCB.setValue("ACIK");
        IBITCB.setValue("TAMAMLANMADI");
        FAILCB.setValue("ARIZA_YOK");
        NewFAILCB.setValue("YENI_ARIZA_OLUSMADI");
        SQBPowerCB.setValue("VAR");
        RTCCB.setValue("SET_EDILMEMIS");
        STCConnectedCB.setValue("BAGLI_DEGIL");
        BlankIRCB.setValue("PASIF");
        BlankRFCB.setValue("PASIF");
        IsAMTB6x5CB.setValue("SIX_BY_FIVE");
        MisfireCB.setValue("YOK");
        NewMisfireCB.setValue("OLUSMADI");
        IsFiringActiveCB.setValue("ATIM_YAPILMIYOR");
    }

    //Asagıdakı kodu ana controllera tasıdım. cunku butonu ana fxmle ekledim

    /*@FXML
    void clickEnvanterBilgisiGoruntuleBtn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(AMTBStateController.class.getResource("AMTBInventoryInfoShow.fxml"));
            AnchorPane popupContent = loader.load();

            AMTBStateController editdialogcontroller = loader.getController();
            Scene popupScene = new Scene(popupContent);
            Stage popupStage = new Stage();

            popupStage.setScene(popupScene);
            popupStage.setTitle("ENVANTER BILGISI GORUNTULEME");
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }*/

}
