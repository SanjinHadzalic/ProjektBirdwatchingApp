package com.example.projektbirdwatchingapp;

import hr.java.vjezbe.niti.PosljednjaIzmjenaNit;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainMenuController{
    @FXML
    private GridPane menuGridPane;
    @FXML
    private Button test2;

    private  final AtomicBoolean flag = new AtomicBoolean(true);
    private static Timeline noviTimeLine = new Timeline(
            new KeyFrame(Duration.seconds(30), event -> Platform.runLater(new PosljednjaIzmjenaNit()))
    );

    public static void showMainMenuScreen() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("mainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 520, 400);
        scene.getStylesheets().add("main.css");
        Application.getMainStage().setTitle("Glavni ekran");
        Application.getMainStage().setScene(scene);
        Application.getMainStage().show();

//        var noviTimeLine = new Timeline(
//                new KeyFrame(Duration.seconds(5), event -> Platform.runLater(new PosljednjaIzmjenaNit()))
//        );
        noviTimeLine.setCycleCount(Timeline.INDEFINITE);
        noviTimeLine.play();
    }

    public void logoutButtonOnAction(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 520, 400);
        scene.getStylesheets().add("main.css");
        Application.getMainStage().setTitle("BirdwatchingApp");
        Application.getMainStage().setScene(scene);
        Application.getMainStage().show();
        noviTimeLine.stop();
    }

    @FXML
    public void openPregledPodataka(ActionEvent e) throws IOException{
        PregledPodatakaController.showPregledPodatakaScreen();
    }

    @FXML
    public void openPregledIstrazivaca(ActionEvent e) throws IOException{
        PregledIstrazivacaController.showPregledIstrazivacaScreen();
    }

    @FXML
    public void openPregledLokacija(ActionEvent e) throws IOException{
        PregledLokacijaController.showPregledLokacijaScreen();
    }
    @FXML
    public void openPieChartIstrazivac(ActionEvent e) throws IOException{
        PieChartIstrazivaciController.showPieChartIstrazivac();
    }
    @FXML
    public void openLink(ActionEvent e){
        System.out.println("Ovo treba doraditi...");
    }//OVO TREBA DORADITI
    @FXML
    public void showPromjeneScreen(ActionEvent e) throws IOException {
        PromjeneController.showPromjeneScreen();
    }
}


