package com.example.projektbirdwatchingapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.BreakIterator;
import java.util.ResourceBundle;

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


