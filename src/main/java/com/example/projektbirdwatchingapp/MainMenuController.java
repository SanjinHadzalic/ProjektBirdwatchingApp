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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainMenuController{
    @FXML
    private Label pozdrav;
    private  final AtomicBoolean flag = new AtomicBoolean(true);
    private static Timeline noviTimeLine = new Timeline(
            new KeyFrame(Duration.seconds(5), event -> Platform.runLater(new PosljednjaIzmjenaNit()))
    );


    public static void showMainMenuScreen() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 520, 400);
        HelloApplication.getMainStage().setTitle("Glavni ekran");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();

//        var noviTimeLine = new Timeline(
//                new KeyFrame(Duration.seconds(5), event -> Platform.runLater(new PosljednjaIzmjenaNit()))
//        );
        noviTimeLine.setCycleCount(Timeline.INDEFINITE);
        noviTimeLine.play();
    }

    public void logoutButtonOnAction(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 520, 400);
        HelloApplication.getMainStage().setTitle("BirdwatchingApp");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
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
    public void openLink(ActionEvent e){
        System.out.println("Ovo treba doraditi...");
    }//OVO TREBA DORADITI
    @FXML
    public void showPromjeneScreen(ActionEvent e) throws IOException {
        PromjeneController.showPromjeneScreen();
    }
    public void displayUsername(String username){
        pozdrav.setText("Hello " + username);
    }
}


