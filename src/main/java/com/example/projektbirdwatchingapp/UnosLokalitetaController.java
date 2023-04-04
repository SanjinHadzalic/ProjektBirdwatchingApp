package com.example.projektbirdwatchingapp;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entiteti.IstrazivacUnos;
import hr.java.vjezbe.entiteti.Lokalitet;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.OptionalInt;

public class UnosLokalitetaController {
    @FXML
    private TextField nazivLokacijeTextField;
    @FXML
    private RadioButton nacionalniParkRadioButton;
    @FXML
    private RadioButton parkPrirodeRadioButton;
    @FXML
    private RadioButton ostaloRadioButton;
    @FXML
    private ToggleGroup parkToggleGroup;
    @FXML
    private TextField xKoordinataTextField;
    @FXML
    private TextField yKoordinataTextField;
    @FXML
    public void saveLokalitet() throws Exception{
        List<Lokalitet> listaLokaliteta = BazaPodataka.dohvatiSveLokacije();

        OptionalInt idLokalitetaRaw = listaLokaliteta.stream()
                .mapToInt(s -> s.getId())
                .max();

        Integer idLokalitet = idLokalitetaRaw.getAsInt() + 1;
        String nazivLokacije = nazivLokacijeTextField.getText();
        String tipLokacije = "test";

        if (nacionalniParkRadioButton.isSelected()){
            tipLokacije = "NP";
        } else if (parkPrirodeRadioButton.isSelected()) {
            tipLokacije = "PP";
        } else if (ostaloRadioButton.isSelected()) {
            tipLokacije = "Ostalo";
        }

        String xKoordLokacije = xKoordinataTextField.getText();
        String yKoordLokacije = yKoordinataTextField.getText();

        StringBuilder errors = new StringBuilder();

        if (nazivLokacije.isBlank()){
            errors.append("Nedostaje unos za naziv nove lokacije!\n");
        }
        if (tipLokacije.isBlank()){
            errors.append("Nedostaje odabir tipa lokacije!\n");
        }
        if (xKoordLokacije.isBlank()){
            errors.append("Nedostaje unos za x koordinatu nove lokacije!\n");
        }
        if (yKoordLokacije.isBlank()){
            errors.append("Nedostaje unos za y koordinatu nove lokacije!\n");
        }
        if (errors.length()>0){
            Alert obavijestUpozerenja = new Alert(Alert.AlertType.WARNING);
            obavijestUpozerenja.setTitle("Nedostaju unosi");
            obavijestUpozerenja.setHeaderText("Nedostaju sljedeci unosi za lokaciju:");
            obavijestUpozerenja.setContentText(errors.toString());

            obavijestUpozerenja.showAndWait();
        } else if (nazivLokacije.isBlank() != true && tipLokacije.isBlank() != true && xKoordLokacije.isBlank() != true && yKoordLokacije.isBlank() != true) {
            Lokalitet novaLokacija = new Lokalitet(idLokalitet, nazivLokacije, tipLokacije, xKoordLokacije, yKoordLokacije);

            HelloApplication.getLokacijaList().add(novaLokacija);

            writeNewLokalitet();

            Alert obavijestUnosa = new Alert(Alert.AlertType.INFORMATION);
            obavijestUnosa.setTitle("Spremanje lokaliteta");
            obavijestUnosa.setHeaderText("Uspjesno je spremlena nova lokacija:");
            obavijestUnosa.setContentText("Lokalitet: " + nazivLokacije + " " + tipLokacije + " je uspjesno dodana u aplikaciju!\n");

            obavijestUnosa.showAndWait();

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pregledLokacija.fxml"));
            Scene scene = null;
            try{
                scene = new Scene(fxmlLoader.load(), 650, 500);
            } catch (IOException ex){
                throw new RuntimeException(ex);
            }
            HelloApplication.getMainStage().setTitle("Pregled lokacija");
            HelloApplication.getMainStage().setScene(scene);
            HelloApplication.getMainStage().show();
        }
    }

    public void writeNewLokalitet() throws Exception{
        List<Lokalitet> listaLokaliteta = BazaPodataka.dohvatiSveLokacije();
        OptionalInt idLokalitetaRaw = listaLokaliteta.stream()
                .mapToInt(s->s.getId())
                .max();

        Integer idLokalitet = idLokalitetaRaw.getAsInt() + 1;
        String nazivLokacije = nazivLokacijeTextField.getText();
        String tipLokacije = "test";
        if (nacionalniParkRadioButton.isSelected()){
            tipLokacije = "NP";
        } else if (parkPrirodeRadioButton.isSelected()) {
            tipLokacije = "PP";
        } else if (ostaloRadioButton.isSelected()) {
            tipLokacije = "Ostalo";
        }
        String xKoordLokacije = xKoordinataTextField.getText();
        String yKoordLokacije = yKoordinataTextField.getText();

        Lokalitet noviLokalitet = new Lokalitet(idLokalitet, nazivLokacije, tipLokacije, xKoordLokacije, yKoordLokacije);

        BazaPodataka.spremiNovuLokaciju(noviLokalitet);
    }

    public static void showUnosLokalitetaScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("unosLokaliteta.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        HelloApplication.getMainStage().setTitle("Unos lokaliteta");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }
    @FXML
    public void backToPregledLokaliteta() throws IOException {
        PregledLokacijaController.showPregledLokacijaScreen();
    }
}
