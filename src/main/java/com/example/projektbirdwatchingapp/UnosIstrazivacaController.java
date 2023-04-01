package com.example.projektbirdwatchingapp;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entiteti.IstrazivacUnos;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.OptionalInt;

public class UnosIstrazivacaController {

    @FXML
    private TextField imeIstrazivacaTextField;
    @FXML
    private TextField prezimeIStrazivacaTextField;
    @FXML
    private DatePicker datumRodjenjaIstrazivacaTextField;
    @FXML
    private TextField institucijaIstrazivacaTextField;
    @FXML
    private TextField adresaIstrazivacaTextField;
    @FXML
    private TextField mobitelIstrazivacaTextField;
    @FXML
    private TextField emailIstrazivacaTextField;

    @FXML
    public void saveIstrazivac() throws Exception{
        List<IstrazivacUnos> listaIstrazivaca = BazaPodataka.dohvatiSveIstrazivace();

        OptionalInt idIstrazivacaRaw = listaIstrazivaca.stream()
                .mapToInt(s -> s.getId())
                .max();

        Integer idIstrazivaca = idIstrazivacaRaw.getAsInt() + 1;
        String imeIstrazivac = imeIstrazivacaTextField.getText();
        String prezimeIStrazivac = prezimeIStrazivacaTextField.getText();
        LocalDate datumIstrazivac = datumRodjenjaIstrazivacaTextField.getValue();
        String institucijaIstrazivaca = institucijaIstrazivacaTextField.getText();
        String adresaIstrazivaca = adresaIstrazivacaTextField.getText();
        String mobitelIstrazivaca = mobitelIstrazivacaTextField.getText();
        String emailIstrazivaca = emailIstrazivacaTextField.getText();

        StringBuilder errors = new StringBuilder();

        if (imeIstrazivac.isBlank()){
            errors.append("Nedostaje unos za ime istrazivaca!\n");
        }
        if (prezimeIStrazivac.isBlank()){
            errors.append("Nedostaje unos za prezime istrazivaca!\n");
        }
        if (datumIstrazivac == null){
            errors.append("Nedostaje unos za datum rodjenja istrazivaca!\n");
        }
        if (institucijaIstrazivaca.isBlank()){
            errors.append("Nedostaje unos za instituciju istrazivaca!\n");
        }
        if (adresaIstrazivaca.isBlank()){
            errors.append("Nedostaje unos za adresu isttrazivaca!\n");
        }
        if (mobitelIstrazivaca.isBlank()){
            errors.append("Nedostaje unos za mobilni telefon istrazivaca!\n");
        }
        if(emailIstrazivaca.isBlank()){
            errors.append("Nedostae unos za email adresu istrazivaca!\n");
        }
        if (errors.length()>0){
            Alert obavijestUpozerenja = new Alert(Alert.AlertType.WARNING);
            obavijestUpozerenja.setTitle("Nedostaju unosi");
            obavijestUpozerenja.setHeaderText("Nedostaju sljedeci unosi za istrazivaca:");
            obavijestUpozerenja.setContentText(errors.toString());
            
            obavijestUpozerenja.showAndWait();
        } else if (imeIstrazivac.isBlank() != true && prezimeIStrazivac.isBlank() != true && datumIstrazivac != null && institucijaIstrazivaca.isBlank() != true && adresaIstrazivaca.isBlank() != true && mobitelIstrazivaca.isBlank() != true && emailIstrazivaca.isBlank()!=true ) {
            IstrazivacUnos noviIStrazivac = new IstrazivacUnos(idIstrazivaca, imeIstrazivac, prezimeIStrazivac, datumIstrazivac, institucijaIstrazivaca, adresaIstrazivaca, Integer.parseInt(mobitelIstrazivaca), emailIstrazivaca);

            HelloApplication.getIstrazivacUnosList().add(noviIStrazivac);

            writeNewIstrazivac();

            Alert obavijestUnosa = new Alert(Alert.AlertType.INFORMATION);
            obavijestUnosa.setTitle("Spremanje istrazivaca");
            obavijestUnosa.setHeaderText("Uspjesno je spremljen istrazivac:");
            obavijestUnosa.setContentText("Istrazivac: " + imeIstrazivac + " " + prezimeIStrazivac + " je uspjesno dodan u aplikaciju!\n");

            obavijestUnosa.showAndWait();

            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pregledIstrazivaca.fxml"));
            Scene scene = null;
            try{
                scene = new Scene(fxmlLoader.load(), 1000, 800);
            } catch (IOException ex){
                throw new RuntimeException(ex);
            }
            HelloApplication.getMainStage().setTitle("Pregled istrazivaca");
            HelloApplication.getMainStage().setScene(scene);
            HelloApplication.getMainStage().show();
        }
    }

    public void writeNewIstrazivac() throws Exception{
        List<IstrazivacUnos> listaIstrazivaca = BazaPodataka.dohvatiSveIstrazivace();
        OptionalInt idIstrazivacaRaw = listaIstrazivaca.stream()
                .mapToInt(s->s.getId())
                .max();
        Integer idIstrazivaca = idIstrazivacaRaw.getAsInt() + 1;
        String imeIstrazivac = imeIstrazivacaTextField.getText();
        String prezimeIStrazivac = prezimeIStrazivacaTextField.getText();
        LocalDate datumIstrazivac = datumRodjenjaIstrazivacaTextField.getValue();
        String institucijaIstrazivaca = institucijaIstrazivacaTextField.getText();
        String adresaIstrazivaca = adresaIstrazivacaTextField.getText();
        String mobitelIstrazivaca = mobitelIstrazivacaTextField.getText();
        String emailIstrazivaca = emailIstrazivacaTextField.getText();

        IstrazivacUnos noviIstrazivac = new IstrazivacUnos(idIstrazivaca, imeIstrazivac, prezimeIStrazivac, datumIstrazivac, institucijaIstrazivaca, adresaIstrazivaca, Integer.parseInt(mobitelIstrazivaca), emailIstrazivaca);

        BazaPodataka.spremiNovogIstrazivaca(noviIstrazivac);
    }

    public static void showUnosIstrazivacaScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("unosIstrazivaca.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        HelloApplication.getMainStage().setTitle("Unos istrazivaca");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }


    public void natragButtonClicked() throws IOException {
        PregledIstrazivacaController.showPregledIstrazivacaScreen();
    }

}
