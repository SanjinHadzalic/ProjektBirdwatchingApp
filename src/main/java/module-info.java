module com.example.projektbirdwatchingapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.projektbirdwatchingapp to javafx.fxml;
    exports com.example.projektbirdwatchingapp;
}