package com.example.projektbirdwatchingapp;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entiteti.BirdUnos;
import hr.java.vjezbe.entiteti.IstrazivacUnos;
import hr.java.vjezbe.entiteti.Lokalitet;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application extends javafx.application.Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    private static Stage mainStage;
    private static List<IstrazivacUnos> istrazivacUnosList;
    private static List<Lokalitet> lokacijaList;
    private static List<BirdUnos> podatakList;
    @Override
    public void start(Stage stage) throws IOException {
        logger.info("Aplikacija je pokrenuta");
        istrazivacUnosList= new ArrayList<>();
        lokacijaList = new ArrayList<>();
        podatakList = new ArrayList<>();
        addIstrazivac();
        addLokacija();
        addPodatak();
        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 520, 400);
        stage.setTitle("BirdwatchingApp");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
    public static List<IstrazivacUnos> getIstrazivacUnosList(){return istrazivacUnosList;}
    public static List<Lokalitet> getLokacijaList(){return lokacijaList;}
    public static List<BirdUnos> getPodatakList(){return podatakList;}
    private void addIstrazivac() throws FileNotFoundException{
        istrazivacUnosList = BazaPodataka.dohvatiSveIstrazivace();
    }

    private void addLokacija() throws FileNotFoundException{
        lokacijaList = BazaPodataka.dohvatiSveLokacije();
    }
    private void addPodatak() throws FileNotFoundException{
            podatakList = BazaPodataka.dohvatiSvePodatke();
    }
    public static Stage getMainStage(){return mainStage;}
}