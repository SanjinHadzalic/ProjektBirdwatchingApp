package com.example.projektbirdwatchingapp;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entiteti.IstrazivacUnos;
import hr.java.vjezbe.iznimke.KorisnikPostojiException;
import hr.java.vjezbe.iznimke.NeispravanUnosException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.OptionalInt;

public class UnosIstrazivacaController {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);
    @FXML
    private TextField imeIstrazivacaTextField;
    @FXML
    private TextField prezimeIStrazivacaTextField;
    @FXML
    private DatePicker datumRodjenjaIstrazivacaDatePicker;
    @FXML
    private TextField institucijaIstrazivacaTextField;
    @FXML
    private TextField adresaIstrazivacaTextField;
    @FXML
    private TextField mobitelIstrazivacaTextField;
    @FXML
    private TextField emailIstrazivacaTextField;

    @FXML
    public void saveIstrazivac() throws Exception {
        boolean flag = true;
        List<IstrazivacUnos> listaIstrazivaca = BazaPodataka.dohvatiSveIstrazivace();

        OptionalInt idIstrazivacaRaw = listaIstrazivaca.stream()
                .mapToInt(s -> s.getId())
                .max();

        Integer idIstrazivaca = idIstrazivacaRaw.getAsInt() + 1;
        String imeIstrazivac = imeIstrazivacaTextField.getText();
        imeIstrazivac=capitalizeString(imeIstrazivac);
        String prezimeIStrazivac = prezimeIStrazivacaTextField.getText();
        prezimeIStrazivac=capitalizeString(prezimeIStrazivac);
        LocalDate datumIstrazivac = datumRodjenjaIstrazivacaDatePicker.getValue();
        String institucijaIstrazivaca = institucijaIstrazivacaTextField.getText();
        String adresaIstrazivaca = adresaIstrazivacaTextField.getText();
        String mobitelIstrazivaca = mobitelIstrazivacaTextField.getText();
        String emailIstrazivaca = emailIstrazivacaTextField.getText();

        try{
            checkDate(datumIstrazivac,flag);
        } catch (NeispravanUnosException e){
            logger.error(e.getMessage());
            Alert ob = new Alert(Alert.AlertType.ERROR, "Neispranvo je unesen datum!");
            ob.showAndWait();
            datumRodjenjaIstrazivacaDatePicker.setValue(LocalDate.now());
            flag=false;

        }

        try{
            checkEmail(emailIstrazivaca,flag);
        } catch (NeispravanUnosException e){
                logger.error(e.getMessage());
                Alert ob = new Alert(Alert.AlertType.ERROR, "Neispranvo je unesen mail!");
                ob.showAndWait();
                emailIstrazivacaTextField.clear();
                flag=false;
        }

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
        } else if (imeIstrazivac.isBlank() != true && prezimeIStrazivac.isBlank() != true && datumIstrazivac != null && institucijaIstrazivaca.isBlank() != true && adresaIstrazivaca.isBlank() != true && mobitelIstrazivaca.isBlank() != true && emailIstrazivaca.isBlank()!=true && flag) {
            IstrazivacUnos noviIStrazivac = new IstrazivacUnos(idIstrazivaca, imeIstrazivac, prezimeIStrazivac, datumIstrazivac, institucijaIstrazivaca, adresaIstrazivaca, Integer.parseInt(mobitelIstrazivaca), emailIstrazivaca);

            boolean flag2 = true;
            //metoda koja provjerava ime, prezime, DOB
            try{
                checkUser(noviIStrazivac);
            } catch (KorisnikPostojiException e){
                logger.error(e.getMessage());
                Alert ob = new Alert(Alert.AlertType.ERROR, "Korisnik vec postoji u aplikaciji!");
                ob.showAndWait();
                imeIstrazivacaTextField.setText("PROMIJENI");
                prezimeIStrazivacaTextField.setText("PROMIJENI");
                datumRodjenjaIstrazivacaDatePicker.setValue(LocalDate.now());
                flag2=false;
            }

            if (flag2){
                Application.getIstrazivacUnosList().add(noviIStrazivac);

                writeNewIstrazivac();

                Alert obavijestUnosa = new Alert(Alert.AlertType.INFORMATION);
                obavijestUnosa.setTitle("Spremanje istrazivaca");
                obavijestUnosa.setHeaderText("Uspjesno je spremljen istrazivac:");
                obavijestUnosa.setContentText("Istrazivac: " + imeIstrazivac + " " + prezimeIStrazivac + " je uspjesno dodan u aplikaciju!\n");

                obavijestUnosa.showAndWait();

                FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("pregledIstrazivaca.fxml"));
                Scene scene = null;
                try{
                    scene = new Scene(fxmlLoader.load(), 800, 600);
                } catch (IOException ex){
                    throw new RuntimeException(ex);
                }
                Application.getMainStage().setTitle("Pregled istrazivaca");
                Application.getMainStage().setScene(scene);
                Application.getMainStage().show();
            }
        }
    }

    private void checkEmail(String emailIstrazivaca,boolean flag) throws NeispravanUnosException {
        if (!emailIstrazivaca.contains("@")){
            throw new NeispravanUnosException("Uneseni mail: "+emailIstrazivaca + "ne sadrzi znak @");
        } else {
            flag=true;
        }
    }

    private void checkDate(LocalDate datumIstrazivaca,boolean flag) throws NeispravanUnosException {
        LocalDate olderCheckDate = LocalDate.parse("01.01.1958.", DateTimeFormatter.ofPattern("dd.MM.yyyy."));
        LocalDate youngerCheckDate = LocalDate.parse("01.01.2002.",DateTimeFormatter.ofPattern("dd.MM.yyyy."));
        if(datumIstrazivaca.isBefore(olderCheckDate)){
            throw new NeispravanUnosException("Datum rodjenja ne moze biti prije " + olderCheckDate);
        } else if (datumIstrazivaca.isAfter(youngerCheckDate)) {
            throw new NeispravanUnosException("Datum rodjenja ne moze biti poslije " + youngerCheckDate);
        } else {
            flag=true;
        }
    }

    private void checkUser(IstrazivacUnos checkNewUser) throws KorisnikPostojiException{
        List<IstrazivacUnos> listaIstrazivacProvjera = Application.getIstrazivacUnosList();

        boolean flag = listaIstrazivacProvjera.stream()
                .filter(a->a.getIme().toUpperCase().contains(checkNewUser.getIme().toUpperCase()) && a.getPrezime().toUpperCase().contains(checkNewUser.getPrezime().toUpperCase()) && a.getDatum().equals(checkNewUser.getDatum()))
                .findFirst()
                .isPresent();
        if (flag){
            logger.error("Uneseni podaci za novog korisnika: " + checkNewUser.getIme() + " " + checkNewUser.getPrezime()+ " " + checkNewUser.getDatum() + " vec postoje u aplikaciji!");
            throw new KorisnikPostojiException("Uneseni podaci za novog korisnika: " + checkNewUser.getIme() + " " + checkNewUser.getPrezime()+ " " + checkNewUser.getDatum() + " vec postoje u aplikaciji!");
        }
    }

    private String capitalizeString(String toBeCapitalized){
        String ret = toBeCapitalized.substring(0,1).toUpperCase() + toBeCapitalized.substring(1).toLowerCase();
        return ret;
    }
    public void writeNewIstrazivac() throws Exception{
        List<IstrazivacUnos> listaIstrazivaca = BazaPodataka.dohvatiSveIstrazivace();
        OptionalInt idIstrazivacaRaw = listaIstrazivaca.stream()
                .mapToInt(s->s.getId())
                .max();
        Integer idIstrazivaca = idIstrazivacaRaw.getAsInt() + 1;
        String imeIstrazivac = imeIstrazivacaTextField.getText();
        String prezimeIStrazivac = prezimeIStrazivacaTextField.getText();
        LocalDate datumIstrazivac = datumRodjenjaIstrazivacaDatePicker.getValue();
        String institucijaIstrazivaca = institucijaIstrazivacaTextField.getText();
        String adresaIstrazivaca = adresaIstrazivacaTextField.getText();
        String mobitelIstrazivaca = mobitelIstrazivacaTextField.getText();
        String emailIstrazivaca = emailIstrazivacaTextField.getText();

        IstrazivacUnos noviIstrazivac = new IstrazivacUnos(idIstrazivaca, imeIstrazivac, prezimeIStrazivac, datumIstrazivac, institucijaIstrazivaca, adresaIstrazivaca, Integer.parseInt(mobitelIstrazivaca), emailIstrazivaca);

        BazaPodataka.spremiNovogIstrazivaca(noviIstrazivac);
    }

    public static void showUnosIstrazivacaScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("unosIstrazivaca.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        Application.getMainStage().setTitle("Unos istrazivaca");
        Application.getMainStage().setScene(scene);
        Application.getMainStage().show();
    }


    public void natragButtonClicked() throws IOException {
        PregledIstrazivacaController.showPregledIstrazivacaScreen();
    }



}
