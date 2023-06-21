package com.example.projektbirdwatchingapp;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.example.projektbirdwatchingapp.LoginController.odabraniUser;

public class BirdsMediaController {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    FileInputStream inputKosStream;
    FileInputStream inputSkanjacStream;
    FileInputStream inputZebaStream;
    FileInputStream inputVjetrusaStream;
    FileInputStream inputRobinStream;
    FileInputStream inputDivljaPatkaStream;

    {
        try {
            inputKosStream = new FileInputStream("src/main/resources/images/Tmerula.jpg");
            inputSkanjacStream = new FileInputStream("src/main/resources/images/Tmerula.jpg");
            inputZebaStream = new FileInputStream("src/main/resources/images/Finch.jpg");
            inputVjetrusaStream = new FileInputStream("src/main/resources/images/kestrel.png");
            inputRobinStream = new FileInputStream("src/main/resources/images/Robin.jpg");
            inputDivljaPatkaStream = new FileInputStream("src/main/resources/images/mallard.jpg");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    ImageView kosImageView = new ImageView(new Image(inputKosStream));
    @FXML
    ImageView skanjacImageView = new ImageView(new Image(inputSkanjacStream));
    @FXML
    ImageView zebaImageView = new ImageView(new Image(inputZebaStream));


    public static void showBirdsMedia() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("birdsMedia.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        scene.getStylesheets().add("main.css");
        Application.getMainStage().setTitle("Birds media");
        Application.getMainStage().setScene(scene);
        Application.getMainStage().show();
    }

    @FXML
    void playKos(ActionEvent event) {
        if (!isPlaying) {
            isPlaying = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    File mediaFile = new File("src/main/resources/music/Tmerula.mp3");
                    playHitSound(mediaFile);

                    Platform.runLater(() -> {
                        mediaPlayer.setOnPaused(() -> {
                            synchronized (mediaPlayer) {
                                mediaPlayer.notify();
                            }
                        });
                    });
                }
            }).start();
        }
    }

    @FXML
    void playSkanjac(ActionEvent event) {
        if (!isPlaying) {
            isPlaying = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    File mediaFile = new File("src/main/resources/music/Skanjac.mp3");
                    playHitSound(mediaFile);

                    Platform.runLater(() -> {
                        mediaPlayer.setOnPaused(() -> {
                            synchronized (mediaPlayer) {
                                mediaPlayer.notify();
                            }
                        });
                    });
                }
            }).start();
        }

    }

    @FXML
    void playZeba(ActionEvent event) {
        if (!isPlaying) {
            isPlaying = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    File mediaFile = new File("src/main/resources/music/Zeba.mp3");
                    playHitSound(mediaFile);

                    Platform.runLater(() -> {
                        mediaPlayer.setOnPaused(() -> {
                            synchronized (mediaPlayer) {
                                mediaPlayer.notify();
                            }
                        });
                    });
                }
            }).start();
        }
    }

    @FXML
    void stopSoundAll(ActionEvent event) {
        if (isPlaying) {
            isPlaying = false;
            mediaPlayer.pause();
        }
    }

    private void playHitSound(File mediaFile) {
        Media media = new Media(mediaFile.toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    public void natragButtonClicked() throws IOException {
        if (odabraniUser.equals("admin".toUpperCase())) {
            try{
                MainMenuController.showMainMenuScreen();
                mediaPlayer.stop();
            } catch (RuntimeException e){
                logger.error("Uslo se u prozor te se nije nista pokrenulo od ponudjenog!");
            }
        } else {
            try{
                MainMenuUserController.showMainMenuScreenUser();
                mediaPlayer.stop();
            }catch (RuntimeException e){
                logger.error("Uslo se u prozor te se nije nista pokrenulo od ponudjenog!");
            }
        }
    }
}
