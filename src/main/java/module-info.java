module com.example.deneme {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires jdk.compiler;
    requires java.naming;
    requires com.fazecast.jSerialComm;
    requires java.desktop;

    opens com.example.deneme to javafx.fxml;
    exports com.example.deneme;
    exports com.example.deneme.MessageIDConstans;
    opens com.example.deneme.MessageIDConstans to javafx.fxml;
    exports com.example.deneme.ControllerClasses;
    opens com.example.deneme.ControllerClasses to javafx.fxml;
}