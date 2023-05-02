module com.example.projektbirdwatchingapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.slf4j;

    opens com.example.projektbirdwatchingapp to javafx.fxml;
    exports com.example.projektbirdwatchingapp;
}