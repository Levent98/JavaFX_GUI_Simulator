package com.example.deneme;

import com.example.deneme.ControllerClasses.BITResult;
import com.example.deneme.MessageIDConstans.AMTBEnums;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class BITResultController implements Initializable {

    @FXML public ComboBox<String> currentabovech1CB  = new ComboBox<>();

    @FXML public ComboBox<String> currentabovech2CB  = new ComboBox<>();

    @FXML public ComboBox<String> currentbelowch1CB  = new ComboBox<>();

    @FXML public ComboBox<String> currentbelowch2CB  = new ComboBox<>();

    @FXML public ComboBox<String> currentleakch1CB  = new ComboBox<>();

    @FXML public ComboBox<String> currentleakch2CB  = new ComboBox<>();

    @FXML public ComboBox<String> dischargech1CB = new ComboBox<>();

    @FXML public ComboBox<String> dischargech2CB = new ComboBox<>();

    @FXML public ComboBox<String> eepromCB = new ComboBox<>();

    @FXML public ComboBox<String> eepromwriteCB = new ComboBox<>();

    @FXML public ComboBox<String> loadch1CB = new ComboBox<>();

    @FXML public ComboBox<String> loadch2CB = new ComboBox<>();

    @FXML public ComboBox<String> magazinecoverCB = new ComboBox<>();

    @FXML public ComboBox<String> mybcommCB = new ComboBox<>();

    @FXML public ComboBox<String> scanvoltageCB = new ComboBox<>();

    @FXML public ComboBox<String> smartcomm1CB = new ComboBox<>();

    @FXML public ComboBox<String> smartcomm2CB = new ComboBox<>();

    @FXML public ComboBox<String> sqbcurrent1CB = new ComboBox<>();

    @FXML public ComboBox<String> sqbcurrent2CB = new ComboBox<>();

    @FXML public ComboBox<String> sqbvoltage1CB = new ComboBox<>();

    @FXML public ComboBox<String> sqbvoltage2CB = new ComboBox<>();

    @FXML public ComboBox<String> sqbvoltagearmCB = new ComboBox<>();

    @FXML public ComboBox<String> voltageleakch1CB = new ComboBox<>();

    @FXML public ComboBox<String> voltageleakch2CB = new ComboBox<>();

    @FXML public ComboBox<String> sqbvoltageinCB = new ComboBox<>();

    @FXML public ComboBox<String> logBackupCB = new ComboBox<>();

    @FXML public ComboBox<String> ramCB = new ComboBox<>();

    @Override public void initialize(URL location, ResourceBundle resources){

        eepromCB.getItems().addAll("AMTB_BIT_RES_OK","AMTB_BIT_RES_FAIL","AMTB_BIT_RES_NOT_TESTED");
        mybcommCB.getItems().addAll("AMTB_BIT_RES_OK","AMTB_BIT_RES_FAIL","AMTB_BIT_RES_NOT_TESTED");
        scanvoltageCB.getItems().addAll("AMTB_BIT_RES_OK","AMTB_BIT_RES_FAIL","AMTB_BIT_RES_NOT_TESTED");
        smartcomm1CB.getItems().addAll("AMTB_BIT_RES_OK","AMTB_BIT_RES_FAIL","AMTB_BIT_RES_NOT_TESTED");
        smartcomm2CB.getItems().addAll("AMTB_BIT_RES_OK","AMTB_BIT_RES_FAIL","AMTB_BIT_RES_NOT_TESTED");
        sqbcurrent1CB.getItems().addAll("AMTB_BIT_RES_OK","AMTB_BIT_RES_FAIL","AMTB_BIT_RES_NOT_TESTED");
        sqbcurrent2CB.getItems().addAll("AMTB_BIT_RES_OK","AMTB_BIT_RES_FAIL","AMTB_BIT_RES_NOT_TESTED");
        sqbvoltage1CB.getItems().addAll("AMTB_BIT_RES_OK","AMTB_BIT_RES_FAIL","AMTB_BIT_RES_NOT_TESTED");
        sqbvoltage2CB.getItems().addAll("AMTB_BIT_RES_OK","AMTB_BIT_RES_FAIL","AMTB_BIT_RES_NOT_TESTED");
        sqbvoltageinCB.getItems().addAll("AMTB_BIT_RES_OK","AMTB_BIT_RES_FAIL","AMTB_BIT_RES_NOT_TESTED");
        sqbvoltagearmCB.getItems().addAll("AMTB_BIT_RES_OK","AMTB_BIT_RES_FAIL","AMTB_BIT_RES_NOT_TESTED");
        loadch1CB.getItems().addAll("AMTB_BIT_RES_OK","AMTB_BIT_RES_FAIL","AMTB_BIT_RES_NOT_TESTED");
        loadch2CB.getItems().addAll("AMTB_BIT_RES_OK","AMTB_BIT_RES_FAIL","AMTB_BIT_RES_NOT_TESTED");
        voltageleakch1CB.getItems().addAll("AMTB_BIT_RES_OK","AMTB_BIT_RES_FAIL","AMTB_BIT_RES_NOT_TESTED");
        voltageleakch2CB.getItems().addAll("AMTB_BIT_RES_OK","AMTB_BIT_RES_FAIL","AMTB_BIT_RES_NOT_TESTED");
        currentabovech1CB.getItems().addAll("AMTB_BIT_RES_OK","AMTB_BIT_RES_FAIL","AMTB_BIT_RES_NOT_TESTED");
        currentabovech2CB.getItems().addAll("AMTB_BIT_RES_OK","AMTB_BIT_RES_FAIL","AMTB_BIT_RES_NOT_TESTED");
        currentbelowch1CB.getItems().addAll("AMTB_BIT_RES_OK","AMTB_BIT_RES_FAIL","AMTB_BIT_RES_NOT_TESTED");
        currentbelowch2CB.getItems().addAll("AMTB_BIT_RES_OK","AMTB_BIT_RES_FAIL","AMTB_BIT_RES_NOT_TESTED");
        dischargech1CB.getItems().addAll("AMTB_BIT_RES_OK","AMTB_BIT_RES_FAIL","AMTB_BIT_RES_NOT_TESTED");
        dischargech2CB.getItems().addAll("AMTB_BIT_RES_OK","AMTB_BIT_RES_FAIL","AMTB_BIT_RES_NOT_TESTED");
        currentleakch1CB.getItems().addAll("AMTB_BIT_RES_OK","AMTB_BIT_RES_FAIL","AMTB_BIT_RES_NOT_TESTED");
        currentleakch2CB.getItems().addAll("AMTB_BIT_RES_OK","AMTB_BIT_RES_FAIL","AMTB_BIT_RES_NOT_TESTED");
        eepromwriteCB.getItems().addAll("AMTB_BIT_RES_OK","AMTB_BIT_RES_FAIL","AMTB_BIT_RES_NOT_TESTED");
        magazinecoverCB.getItems().addAll("AMTB_BIT_RES_OK","AMTB_BIT_RES_FAIL","AMTB_BIT_RES_NOT_TESTED");
        logBackupCB.getItems().addAll("AMTB_BIT_RES_OK","AMTB_BIT_RES_FAIL","AMTB_BIT_RES_NOT_TESTED");
        ramCB.getItems().addAll("AMTB_BIT_RES_OK","AMTB_BIT_RES_FAIL","AMTB_BIT_RES_NOT_TESTED");

    }

}


