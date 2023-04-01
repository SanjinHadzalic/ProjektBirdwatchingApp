package com.example.projektbirdwatchingapp;

import hr.java.vjezbe.entiteti.IstrazivacUnos;
import hr.java.vjezbe.util.Serijalizacija;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class PromjeneController {
    @FXML
    private TableColumn<Serijalizacija, String> beforeTableColumn;
    @FXML
    private TableColumn<Serijalizacija,String> afterTableColumn;
    @FXML
    private TableColumn<Serijalizacija, String> userTableColumn;
    @FXML
    private TableColumn<Serijalizacija,String> dateTableColumn;
    @FXML
    private TableView<Serijalizacija> changesTableView;
    private ArrayList<Serijalizacija> listaSTO;
    @FXML
    public void initialize() throws FileNotFoundException {
        beforeTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getBeforeChange()));

        afterTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getAfterChange()));


        userTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getUser()));

        dateTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getDateOfChange()));

        changesTableView.setItems(FXCollections.observableList(Serijalizacija.deserializeSTOList(listaSTO)));
    }

    public static void showPromjeneScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("promjene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 850, 800);
        HelloApplication.getMainStage().setTitle("Promjene");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }

    public void natragButtonClicked() throws IOException {
        MainMenuController.showMainMenuScreen();
    }
}
