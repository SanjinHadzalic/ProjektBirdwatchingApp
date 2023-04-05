package com.example.projektbirdwatchingapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.text.Text;

import java.io.IOException;


public class MainMenuUserController {
    @FXML
    private Text greetingText;

    public static Text statGreeting;
    @FXML
    private Hyperlink poveznica;

    @FXML
    public void initialize(){
        statGreeting=greetingText;
    }
    public static void showMainMenuScreenUser() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mainMenuUser.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 520, 400);
        HelloApplication.getMainStage().setTitle("Glavni ekran");
        statGreeting.setText("Bok " + LoginController.odabraniUser);
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

}
