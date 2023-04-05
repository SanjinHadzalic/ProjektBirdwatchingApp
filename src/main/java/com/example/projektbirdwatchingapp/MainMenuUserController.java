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
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("mainMenuUser.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 520, 400);
        Application.getMainStage().setTitle("Glavni ekran");
        statGreeting.setText("Bok " + LoginController.odabraniUser);
        Application.getMainStage().setScene(scene);
        Application.getMainStage().show();

    }

    public void logoutButtonOnAction(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 520, 400);
        Application.getMainStage().setTitle("BirdwatchingApp");
        Application.getMainStage().setScene(scene);
        Application.getMainStage().show();
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
