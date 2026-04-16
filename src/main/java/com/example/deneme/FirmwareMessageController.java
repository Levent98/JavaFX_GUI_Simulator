package com.example.deneme;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class FirmwareMessageController implements Initializable{


    @FXML public  ComboBox<String> firmwareVersionCB = new ComboBox<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        firmwareVersionCB.getItems().addAll("KTAS_AMTB_v1_52", "KTAS_AMTB_v1_53", "KTAS_AMTB_v1_54", "KTAS_AMTB_v1_55", "KTAS_AMTB_v1_56");


    }
}
