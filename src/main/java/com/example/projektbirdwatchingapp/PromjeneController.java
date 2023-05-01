package com.example.projektbirdwatchingapp;

import hr.java.vjezbe.util.Serijalizacija;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

public class PromjeneController {
    @FXML
    private GridPane serijalizacijaGridPane;
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
    private AtomicBoolean running = new AtomicBoolean(true);
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

        refreshPromjene();
    }

    public Thread refreshPromjene(){
        Thread t = new Thread(() -> {
            while(running.get()){
                System.out.println("Thread za refresh promjena radi\n");
                try {
                    Thread.sleep(3000); //sleep 3 secs
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                Platform.runLater(() ->{
                    changesTableView.setItems(FXCollections.observableList(Serijalizacija.deserializeSTOList(listaSTO)));
                });
            }
        });

        t.setDaemon(true);
        t.start();

        return t;
    }
    public static void showPromjeneScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("promjene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 850, 800);
        scene.getStylesheets().add("main.css");
        Application.getMainStage().setTitle("Promjene");
        Application.getMainStage().setScene(scene);
        Application.getMainStage().show();
    }

    public void natragButtonClicked() throws IOException {
        MainMenuController.showMainMenuScreen();
        running.set(false);
    }
}
