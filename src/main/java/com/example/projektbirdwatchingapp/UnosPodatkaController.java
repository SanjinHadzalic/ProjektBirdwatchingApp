package com.example.projektbirdwatchingapp;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entiteti.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.OptionalInt;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static com.example.projektbirdwatchingapp.LoginController.odabraniUser;

public class UnosPodatkaController implements Initializable {
    @FXML
    private GridPane unosPodatakaGridPane;
    @FXML
    private Text vrstaText;
    @FXML
    private Button addPodatakButton;
    @FXML
    private Button backToPrevScreenButton;
    @FXML
    private ComboBox<BinarnaNomenklatura> vrstaComboBox;
    @FXML
    private TextField brojnostTextField;
    @FXML
    private ComboBox<String> spolComboBox;
    @FXML
    private ComboBox<String> istrazivacComboBox;
    @FXML
    private ComboBox<String> lokacijaComboBox;
    @FXML
    private DatePicker datumDatePicker;
    @FXML
    private TextArea komentariTextArea;
    private final List<IstrazivacUnos> istrazivaciBaza;

    {
        try {
            istrazivaciBaza = BazaPodataka.dohvatiSveIstrazivacee().stream().toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private final List<Lokalitet> lokacijaBazaPodataka = BazaPodataka.dohvatiSveLokalitete().stream().toList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vrstaComboBox.setEditable(true);
        vrstaComboBox.getItems().setAll(BinarnaNomenklatura.values());
        GenderSpecific female = new GenderSpecific("Female");
        GenderSpecific male = new GenderSpecific("Male");
        GenderSpecific unknown = new GenderSpecific("Unknown");
        spolComboBox.setItems(FXCollections.observableArrayList(female.gender(),male.gender(),unknown.gender()));
        spolComboBox.setValue(female.gender());

        if (odabraniUser.equals("admin".toUpperCase())){
            for (IstrazivacUnos e : istrazivaciBaza){
                istrazivacComboBox.getItems().add(e.getIme() + " " + e.getPrezime());
            }
            istrazivacComboBox.setValue(istrazivaciBaza.get(0).getIme() + " " + istrazivaciBaza.get(0).getPrezime());
        } else {
            String user = odabraniUser;
            List<IstrazivacUnos> istr = istrazivaciBaza.stream()
                            .filter(a->a.getIme().toLowerCase().startsWith(user.toLowerCase()))
                    .collect(Collectors.toList());
            for(IstrazivacUnos f : istr){
                istrazivacComboBox.getItems().add(f.getIme() + " " + f.getPrezime());
            }
            istrazivacComboBox.setValue(istr.get(0).getIme() + " " + istr.get(0).getPrezime());
        }
        for (Lokalitet l : lokacijaBazaPodataka){
            lokacijaComboBox.getItems().add(l.getNazivLokacije());
        }
        lokacijaComboBox.setValue(lokacijaBazaPodataka.get(0).getNazivLokacije());
    }
    @FXML
    public void savePodatak() {
        List<BirdUnos> podatakList = BazaPodataka.dohvatiSvePodatkee();

        OptionalInt idPodatakRaw = podatakList.stream()
                .mapToInt(s -> s.getId())
                .max();

        Integer idPodatka = idPodatakRaw.getAsInt() + 1;
        String vrstaPodatka = String.valueOf(vrstaComboBox.getSelectionModel().getSelectedItem());
        String brojnostPodatka = brojnostTextField.getText();
        String spolPodatka = spolComboBox.getSelectionModel().getSelectedItem();
        String istrazivacPodatka = istrazivacComboBox.getSelectionModel().getSelectedItem();
        String lokacijaPodatka = lokacijaComboBox.getSelectionModel().getSelectedItem();
        String komentari = komentariTextArea.getText();
        LocalDate datumPodatka = datumDatePicker.getValue();

        StringBuilder errors = new StringBuilder();

        if (vrstaPodatka.isBlank()){
            errors.append("Nedostaje unos za ime vrsste podatka!\n");
        }
        if (brojnostPodatka.isBlank()){
            errors.append("Nedostaje unos za brojnost vrste!\n");
        }
        if (spolPodatka.isBlank()){
            errors.append("Nedostaje unos za spol vrste!\n");
        }
        if (istrazivacPodatka.isBlank()){
            errors.append("Nedostaje unos za istrazivaca vrste!\n");
        }
        if (lokacijaPodatka.isBlank()){
            errors.append("Nedostaje unos za unos!\n");
        }
        if (datumPodatka == null){
            errors.append("Nedostaje unos za datum unosa podatka!\n");
        }
        if (errors.length()>0){
            Alert obavijestUpozerenja = new Alert(Alert.AlertType.WARNING);
            obavijestUpozerenja.setTitle("Nedostaju unosi");
            obavijestUpozerenja.setHeaderText("Nedostaju sljedeci unosi za podatke:");
            obavijestUpozerenja.setContentText(errors.toString());

            obavijestUpozerenja.showAndWait();
        } else if (vrstaPodatka.isBlank() != true && brojnostPodatka.isBlank() != true && datumPodatka != null && spolPodatka.isBlank() != true && istrazivacPodatka.isBlank() != true && lokacijaPodatka.isBlank() != true) {
            BirdUnos noviPodatak = new BirdUnos(idPodatka, vrstaPodatka, Integer.parseInt(brojnostPodatka), spolPodatka, komentari, istrazivacPodatka, lokacijaPodatka, datumPodatka);

            Application.getPodatakList().add(noviPodatak);

            writeNewPodatak();

            Alert obavijestUnosa = new Alert(Alert.AlertType.INFORMATION);
            obavijestUnosa.setTitle("Spremanje novog podatka");
            obavijestUnosa.setHeaderText("Uspjesno je spremljen podatak:");
            obavijestUnosa.setContentText("Podatak: " + vrstaPodatka + " >>" + idPodatka + " je uspjesno dodan u aplikaciju!\n");

            obavijestUnosa.showAndWait();

            FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("pregledPodataka.fxml"));
            Scene scene = null;
            try{
                scene = new Scene(fxmlLoader.load(), 1000, 800);
            } catch (IOException ex){
                throw new RuntimeException(ex);
            }
            Application.getMainStage().setTitle("Pregled podataka");
            Application.getMainStage().setScene(scene);
            Application.getMainStage().show();
        }
    }

    public void writeNewPodatak(){
        List<BirdUnos> podatakList = BazaPodataka.dohvatiSvePodatkee();
        OptionalInt idPodatakRaw = podatakList.stream()
                .mapToInt(s->s.getId())
                .max();
        Integer idPodatak = idPodatakRaw.getAsInt() + 1;
        String vrstaPodatak = String.valueOf(vrstaComboBox.getSelectionModel().getSelectedItem());
        String brojnostPodatak = brojnostTextField.getText();
        String spolPodatak = spolComboBox.getSelectionModel().getSelectedItem();
        String komentari = komentariTextArea.getText();
        String istrazivacPodatak = istrazivacComboBox.getSelectionModel().getSelectedItem();
        String lokacijaPodatak = lokacijaComboBox.getSelectionModel().getSelectedItem();
        LocalDate datumIstrazivac = datumDatePicker.getValue();

        BirdUnos noviPodatak = new BirdUnos(idPodatak, vrstaPodatak, Integer.parseInt(brojnostPodatak), spolPodatak, komentari, istrazivacPodatak,  lokacijaPodatak, datumIstrazivac);

        BazaPodataka.spremiNoviPodatak(noviPodatak);
    }


    public static void showUnosPodatkaScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("unosPodatka.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        scene.getStylesheets().add("main.css");
        Application.getMainStage().setTitle("Unos novog podatka");
        Application.getMainStage().setScene(scene);
        Application.getMainStage().show();
    }


    public void natragButtonClicked() throws IOException {
        PregledPodatakaController.showPregledPodatakaScreen();
    }

}
