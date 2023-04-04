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
import javafx.util.Duration;

import java.io.IOException;

public class MainMenuController{
    @FXML
    private Button logoutButton;
    @FXML
    private Button pregledPodatakaButton;

    @FXML
    private Label greetingLabel;

    @FXML
    private Hyperlink poveznica;

    public static void showMainMenuScreen() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 520, 400);
        HelloApplication.getMainStage().setTitle("Glavni ekran");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();

        var noviTimeLine = new Timeline(
                new KeyFrame(Duration.seconds(30), event -> Platform.runLater(new PosljednjaIzmjenaNit()))
        );
        noviTimeLine.setCycleCount(Timeline.INDEFINITE);
        noviTimeLine.play();
    }

    public static void showMainMenuScreenUser() throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mainMenuUser.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 520, 400);
        HelloApplication.getMainStage().setTitle("Glavni ekran");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }

    public void logoutButtonOnAction(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 520, 400);
        HelloApplication.getMainStage().setTitle("BirdwatchingApp");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
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
    public void openUnosIstrazivaca(ActionEvent e) throws IOException{
        UnosIstrazivacaController.showUnosIstrazivacaScreen();
    }
    @FXML
    public void openPregledLokacija(ActionEvent e) throws IOException{
        PregledLokacijaController.showPregledLokacijaScreen();
    }
    @FXML
    public void openLink(ActionEvent e){
        System.out.println("Ovo treba doraditi...");
    }
    @FXML
    public void showPromjeneScreen(ActionEvent e) throws IOException {
        PromjeneController.showPromjeneScreen();
    }
}


