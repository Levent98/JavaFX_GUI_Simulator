package com.example.deneme;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class ZeroizeResultController implements Initializable {


    @FXML
    public ComboBox<String>  zeroizeResultSelectCB = new ComboBox<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        zeroizeResultSelectCB.getItems().addAll("AMTB_ZEROIZE_RESULT_NOT_STARTED", "AMTB_ZEROIZE_RESULT_CONTINUE", "AMTB_ZEROIZE_RESULT_FINISHED", "AMTB_ZEROIZE_RESULT_FAILED");


    }
}
