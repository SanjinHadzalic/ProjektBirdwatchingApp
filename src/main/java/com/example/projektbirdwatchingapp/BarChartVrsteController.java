package com.example.projektbirdwatchingapp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

import static com.example.projektbirdwatchingapp.LoginController.odabraniUser;

public class BarChartVrsteController {
    public static void showBarChartVrste() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("vrsteBarChart.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        scene.getStylesheets().add("main.css");
        Application.getMainStage().setTitle("BarChart vrste");
        Application.getMainStage().setScene(scene);
        Application.getMainStage().show();
    }

    public void natragButtonClicked() throws IOException {
        if (odabraniUser.equals("admin".toUpperCase())){
            MainMenuController.showMainMenuScreen();
        } else {
            MainMenuUserController.showMainMenuScreenUser();
        }
    }

}
