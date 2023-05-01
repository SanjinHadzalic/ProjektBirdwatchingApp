package com.example.projektbirdwatchingapp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

import static com.example.projektbirdwatchingapp.LoginController.odabraniUser;

public class PieChartIstrazivaciController {
    public static void showPieChartIstrazivac() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("pieChartIstrazivaci.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        Application.getMainStage().setTitle("PieChart Istrazivac");
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
