package com.example.projektbirdwatchingapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.example.projektbirdwatchingapp.LoginController.odabraniUser;

public class BirdsMediaController  {
    MediaPlayer mediaPlayer;

    FileInputStream input;

    {
        try {
            input = new FileInputStream("src/main/resources/images/Tmerula.jpg");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    Image kos = new Image(input);
    @FXML
    ImageView kos2 = new ImageView(kos);


    public static void showBirdsMedia() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("birdsMedia.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        scene.getStylesheets().add("main.css");
        Application.getMainStage().setTitle("Birds media");
        Application.getMainStage().setScene(scene);
        Application.getMainStage().show();
    }
    @FXML
    void playKos(ActionEvent event){
        File mediaFile = new File("src/main/resources/music/Tmerula.mp3");
        playHitSound(mediaFile);
    }

    @FXML
    void stopSoundAll(ActionEvent event){
        mediaPlayer.stop();
    }

    private void playHitSound(File mediaFile){
        Media media = new Media(mediaFile.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
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
