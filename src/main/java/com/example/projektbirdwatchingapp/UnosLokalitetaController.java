package com.example.projektbirdwatchingapp;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entiteti.Lokalitet;
import hr.java.vjezbe.iznimke.KorisnikPostojiException;
import hr.java.vjezbe.iznimke.LokalitetPostojiException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.OptionalInt;

public class UnosLokalitetaController {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    @FXML
    private AnchorPane unosLokalitetaAnchorPane;
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
    public void saveLokalitet(){
        List<Lokalitet> listaLokaliteta = BazaPodataka.dohvatiSveLokalitete();

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

            boolean flag = true;

            try{
                checkNewLokalitet(novaLokacija);
            } catch (LokalitetPostojiException e){
                logger.error(e.getMessage());
                Alert ob = new Alert(Alert.AlertType.ERROR, "Lokalitet vec postoji u aplikaciji!");
                ob.showAndWait();
                nazivLokacijeTextField.setText("PROMIJENI");
                nacionalniParkRadioButton.setSelected(true);
                xKoordinataTextField.setText("PROMIJENI");
                yKoordinataTextField.setText("PROMIJENI");
                flag=false;
            }

            if (flag){
                Application.getLokacijaList().add(novaLokacija);

                writeNewLokalitet();

                Alert obavijestUnosa = new Alert(Alert.AlertType.INFORMATION);
                obavijestUnosa.setTitle("Spremanje lokaliteta");
                obavijestUnosa.setHeaderText("Uspjesno je spremljena nova lokacija:");
                obavijestUnosa.setContentText("Lokalitet: " + nazivLokacije + " " + tipLokacije + " je uspjesno dodana u aplikaciju!\n");

                obavijestUnosa.showAndWait();

                FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("pregledLokacija.fxml"));
                Scene scene = null;
                try{
                    scene = new Scene(fxmlLoader.load(), 650, 500);
                } catch (IOException ex){
                    throw new RuntimeException(ex);
                }
                Application.getMainStage().setTitle("Pregled lokacija");
                Application.getMainStage().setScene(scene);
                Application.getMainStage().show();
            }
        }
    }

    private void checkNewLokalitet(Lokalitet newLokalitet) throws LokalitetPostojiException {
        List<Lokalitet> lokalitetiExisting = Application.getLokacijaList();

        boolean flag = lokalitetiExisting.stream()
                .filter(a-> a.getNazivLokacije().toUpperCase().equals(newLokalitet.getNazivLokacije().toUpperCase()))
                .findFirst()
                .isPresent();

        if (flag){
            logger.error("Uneseni podaci za novu lokaciju: " + newLokalitet.getNazivLokacije() + " " + newLokalitet.getTypeLocation() + " " + newLokalitet.getxCoord() + newLokalitet.getyCoord() + " vec postoje u aplikaciji!");
            throw new LokalitetPostojiException("Uneseni podaci za novog korisnika: " + newLokalitet.getNazivLokacije() + " " + newLokalitet.getTypeLocation() + " " + newLokalitet.getxCoord() + newLokalitet.getyCoord() + " vec postoji u aplikaciji!");
        }
        System.out.println(flag);

    }
    public void writeNewLokalitet(){
        List<Lokalitet> listaLokaliteta = BazaPodataka.dohvatiSveLokalitete();
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

        Lokalitet noviLokalitet = new Lokalitet(idLokalitet, capitalizeString(nazivLokacije), tipLokacije, xKoordLokacije, yKoordLokacije);

        BazaPodataka.spremiNovuLokaciju(noviLokalitet);
    }

    private String capitalizeString(String toBeCapitalized){
        String ret = toBeCapitalized.substring(0,1).toUpperCase() + toBeCapitalized.substring(1).toLowerCase();
        return ret;
    }


    public static void showUnosLokalitetaScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("unosLokaliteta.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        scene.getStylesheets().add("main.css");
        Application.getMainStage().setTitle("Unos lokaliteta");
        Application.getMainStage().setScene(scene);
        Application.getMainStage().show();
    }
    @FXML
    public void backToPregledLokaliteta() throws IOException {
        PregledLokacijaController.showPregledLokacijaScreen();
    }
}
