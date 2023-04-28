module com.example.demo24 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.demo24 to javafx.fxml;
    exports com.example.demo24;
}