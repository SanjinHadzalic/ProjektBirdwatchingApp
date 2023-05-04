package com.example.projektbirdwatchingapp;

import hr.java.vjezbe.entiteti.BirdUnos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import org.w3c.dom.events.MouseEvent;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.projektbirdwatchingapp.LoginController.odabraniUser;

public class BirdsMediaController {
    MediaPlayer mediaPlayer;

    @FXML
    void play(ActionEvent event){
        File mediaFile = new File("src/main/resources/music/Tmerula.mp3");
        playHitSound(mediaFile);
    }

    @FXML
    void stopSound(ActionEvent event){
        mediaPlayer.stop();
    }

    private void playHitSound(File mediaFile){
        Media media = new Media(mediaFile.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }
    public static void showBirdsMedia() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("birdsMedia.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        scene.getStylesheets().add("main.css");
        Application.getMainStage().setTitle("Birds media");
        Application.getMainStage().setScene(scene);
        Application.getMainStage().show();
    }

    public void natragButtonClicked() throws IOException {
        if (odabraniUser.equals("admin".toUpperCase())){
            MainMenuController.showMainMenuScreen();
            mediaPlayer.stop();
        } else {
            MainMenuUserController.showMainMenuScreenUser();
            mediaPlayer.stop();
        }
    }

}
