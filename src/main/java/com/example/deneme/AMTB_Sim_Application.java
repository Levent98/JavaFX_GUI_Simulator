package com.example.deneme;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AMTB_Sim_Application extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(AMTB_Sim_Application.class.getResource("AMTB-Sim.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        AMTB_Sim_Controller controller = fxmlLoader.getController();
        //SocketConnectionforKKY socketCon = new SocketConnectionforKKY(controller);

        stage.setTitle("AMTB Simulator 1.0");
        stage.setScene(scene);
        //stage.getIcons().add(new Image(String.valueOf(AMTB_Sim_Application.class.getResource("karel.png"))));

        stage.show();

        System.out.println("java version: "+System.getProperty("java.version"));
        System.out.println("javafx.version: " + System.getProperty("javafx.version"));
    }

    public static void main(String[] args) {
        launch();
    }
}