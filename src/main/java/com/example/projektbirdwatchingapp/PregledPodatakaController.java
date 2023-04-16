package com.example.projektbirdwatchingapp;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entiteti.*;
import hr.java.vjezbe.util.Serijalizacija;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static com.example.projektbirdwatchingapp.LoginController.odabraniUser;

public non-sealed class PregledPodatakaController implements Initializable, Analiza {
    @FXML
    private ComboBox<Nomenklatura> vrstaComboBox;
    @FXML
    private TextField brojnostVrsteTextField;
    @FXML
    private ComboBox<String> spolVrsteComboBox;
    @FXML
    private TextField countFemaleTextField;
    @FXML
    private ComboBox<String> istrazivacComboBox;
    @FXML
    private ComboBox<String> lokacijaComboBox;
    @FXML
    private DatePicker datumDatePicker;
    @FXML
    private TableView<BirdUnos> podaciTableView;
    @FXML
    private TableColumn<BirdUnos,String> vrstaTableColumn;
    @FXML
    private TableColumn<BirdUnos,String> brojnostTableColumn;
    @FXML
    private TableColumn<BirdUnos,String> spolTableColumn;
    @FXML
    private TableColumn<BirdUnos,String> komentariTableColumn;
    @FXML
    private TableColumn<BirdUnos,String> istrazivacTableColumn;
    @FXML
    private TableColumn<BirdUnos, String> lokacijaTableColumn;
    @FXML
    private TableColumn<BirdUnos,String> datumTableColumn;

    private final List<IstrazivacUnos> istrazivaciBaza;

    {
        try {
            istrazivaciBaza = BazaPodataka.dohvatiSveIstrazivacee().stream().sorted(Comparator.comparing(IstrazivacUnos::getIme)).toList();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private final List<Lokalitet> lokacijaBazaPodataka = BazaPodataka.dohvatiSveLokalitete().stream().sorted(Comparator.comparing(Lokalitet::getNazivLokacije)).toList();
    private ArrayList<Serijalizacija> listaSTO = new ArrayList<>();
    private final AtomicBoolean running = new AtomicBoolean(true);
    private Integer countFemale = countFemale(Application.getPodatakList());
    private Integer countMale = countMale(Application.getPodatakList());
    private Integer countUnknown = countUnkonown(Application.getPodatakList());


    public static void showPregledPodatakaScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("pregledPodataka.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 800);
        Application.getMainStage().setTitle("Pregled Podataka");
        Application.getMainStage().setScene(scene);
        Application.getMainStage().show();
    }
    @FXML
    public void natragButtonClicked() throws IOException {
        if (odabraniUser.equals("admin".toUpperCase())){
            MainMenuController.showMainMenuScreen();
            running.set(false);
        } else {
            MainMenuUserController.showMainMenuScreenUser();
            running.set(false);
        }
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        vrstaTableColumn.setCellValueFactory(cellData-> new SimpleStringProperty(cellData.getValue().getNaziv()));
        brojnostTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBrojnost().toString()));
        spolTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSpol()));
        komentariTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKomentari()));
        istrazivacTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIstrazivac()));
        lokacijaTableColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLokacija()));
        datumTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<BirdUnos,String>,
                        ObservableValue<String>>(){
                    @Override
                    public ObservableValue<String> call(
                            TableColumn.CellDataFeatures<BirdUnos, String> student) {
                        SimpleStringProperty property = new
                                SimpleStringProperty();
                        DateTimeFormatter formatter =
                                DateTimeFormatter.ofPattern("dd.MM.yyyy.");
                        property.setValue(
                                student.getValue().getDatum().format(formatter));
                        return property;
                    }
                });
        vrstaComboBox.setEditable(true);
        vrstaComboBox.getItems().setAll(Arrays.stream(Nomenklatura.values()).sorted(Comparator.comparing(Nomenklatura::getVrsta)).toList());
        spolVrsteComboBox.setItems(FXCollections.observableArrayList("M", "F","U" ));
        if (odabraniUser.equals("admin".toUpperCase())){
            for (IstrazivacUnos e : istrazivaciBaza){
                istrazivacComboBox.getItems().add(e.getIme() + " " + e.getPrezime());
            }
        } else {
            String user = odabraniUser;
            System.out.println("Tijekom pregleda je " + user);
            List<IstrazivacUnos> istr = istrazivaciBaza.stream()
                            .filter(a->a.getIme().toLowerCase().startsWith(user.toLowerCase()))
                    .collect(Collectors.toList());
            for(IstrazivacUnos f : istr){
                istrazivacComboBox.getItems().add(f.getIme() + " " + f.getPrezime());
            }
        }
        for (Lokalitet l : lokacijaBazaPodataka){
            lokacijaComboBox.getItems().add(l.getNazivLokacije());
        }
        refreshPodatak(odabraniUser, running);
    }

    public Thread refreshPodatak(String odabraniUser, AtomicBoolean running){
        Thread t = new Thread(() -> {
            while(running.get()){
                Platform.runLater(() ->{
                    if (odabraniUser.equals("admin".toUpperCase())){
                        podaciTableView.setItems(FXCollections.observableList(Application.getPodatakList()));
                        countFemaleTextField.setText(String.valueOf(countFemale));
                    } else {
                        podaciTableView.setItems(FXCollections.observableList(Application.getPodatakList().stream().filter(a->a.getIstrazivac().toLowerCase().startsWith(odabraniUser.toLowerCase())).collect(Collectors.toList())));
                    }
                });
                try {
                    Thread.sleep(3000); //sleep 3 secs
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t.setDaemon(true);
        t.start();

        return t;
    }
    @FXML
    public void filterPodatak(){
        String naziv = String.valueOf(vrstaComboBox.getSelectionModel().getSelectedItem());
        String brojnost = brojnostVrsteTextField.getText();
        String spol = String.valueOf(spolVrsteComboBox.getSelectionModel().getSelectedItem());
        String istrazivac = String.valueOf(istrazivacComboBox.getSelectionModel().getSelectedItem());
        String lokacija = String.valueOf(lokacijaComboBox.getSelectionModel().getSelectedItem());
        LocalDate datum = datumDatePicker.getValue();

        List<BirdUnos> filterPodatakList = new ArrayList<>(Application.getPodatakList());

        if(Optional.of(naziv).isPresent() == true){
            filterPodatakList = filterPodatakList.stream()
                    .filter(s->s.getNaziv().toLowerCase().contains(naziv.toLowerCase()))
                    .collect(Collectors.toList());
        }
        if (Optional.of(brojnost).isPresent()==true){
            filterPodatakList=filterPodatakList.stream()
                    .filter(s->s.getBrojnost().toString().contains(brojnost))
                    .collect(Collectors.toList());
        }
        if (Optional.ofNullable(spol).isPresent()==true){
            filterPodatakList=filterPodatakList.stream()
                    .filter(s->s.getSpol().contains(spol))
                    .collect(Collectors.toList());
        }
        if(Optional.ofNullable(istrazivac).isPresent() == true){
            filterPodatakList = filterPodatakList.stream()
                    .filter(s->s.getIstrazivac().contains(istrazivac))
                    .collect(Collectors.toList());
        }
        if(Optional.ofNullable(lokacija).isPresent() == true){
            filterPodatakList = filterPodatakList.stream()
                    .filter(s->s.getLokacija().contains(lokacija))
                    .collect(Collectors.toList());
        }
        if(Optional.ofNullable(datum).isEmpty() == false){
            filterPodatakList = filterPodatakList.stream()
                    .filter(s->s.getDatum().equals(datum))
                    .collect(Collectors.toList());
        }
        podaciTableView.setItems(FXCollections.observableList(filterPodatakList));
//        System.out.println(vrstaComboBox.getSelectionModel().getSelectedItem());
        running.set(false);
    }

    @FXML
    public void onClickedRowShow(){
        BirdUnos changed = podaciTableView.getSelectionModel().getSelectedItem();

        vrstaComboBox.setValue(Nomenklatura.valueOf(changed.getNaziv()));
        brojnostVrsteTextField.setText(changed.getBrojnost().toString());
        spolVrsteComboBox.setValue(changed.getSpol());
        istrazivacComboBox.setValue(changed.getIstrazivac());
        lokacijaComboBox.setValue(changed.getLokacija());
        datumDatePicker.setValue(changed.getDatum());
    }

    @FXML
    public void clearSelection(){
        vrstaComboBox.getSelectionModel().clearSelection();
        brojnostVrsteTextField.clear();
        spolVrsteComboBox.getSelectionModel().clearSelection();
        istrazivacComboBox.getSelectionModel().clearSelection();
        lokacijaComboBox.getSelectionModel().clearSelection();
        datumDatePicker.setValue(null);

        podaciTableView.setItems(FXCollections.observableList(Application.getPodatakList()));
        running.set(true);
        refreshPodatak(odabraniUser,running);
    }

    @FXML
    public void updateSelectedPodatak(ActionEvent event) throws Exception {
        ObservableList<BirdUnos> crntPodatakList = podaciTableView.getItems();
        BirdUnos toBeChanged = podaciTableView.getSelectionModel().getSelectedItem();

        String beforeChange = toBeChanged.getId() + "," + toBeChanged.getNaziv() + "," + toBeChanged.getBrojnost() + ","+ toBeChanged.getSpol()+","+toBeChanged.getKomentari()+","+toBeChanged.getIstrazivac()+","+toBeChanged.getLokacija()+","+toBeChanged.getDatum();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Azuriranje odabranog podatka");
        alert.setHeaderText("Zelite li podatku rednog broja: >>" + toBeChanged.getId() +  "<< promijeniti vrijednosti:");
        alert.setContentText(toBeChanged.getNaziv() + " u " + vrstaComboBox.getSelectionModel().getSelectedItem() + "\n " +
                toBeChanged.getBrojnost() + " u " + brojnostVrsteTextField.getText() + "\n " +
                toBeChanged.getSpol() + " u " + spolVrsteComboBox.getSelectionModel().getSelectedItem() + "\n " +
                toBeChanged.getIstrazivac() + " u " + istrazivacComboBox.getSelectionModel().getSelectedItem() + "\n " +
                toBeChanged.getLokacija() + " u " + lokacijaComboBox.getSelectionModel().getSelectedItem() + "\n " +
                toBeChanged.getDatum() + " u " + datumDatePicker.getValue());

        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK){
            Integer currentPodatakId = Integer.parseInt(String.valueOf(toBeChanged.getId()));

            for(BirdUnos target : crntPodatakList){
                if(target.getId() == currentPodatakId){
                    target.setNaziv(String.valueOf(vrstaComboBox.getSelectionModel().getSelectedItem()));
                    target.setBrojnost(Integer.valueOf(brojnostVrsteTextField.getText()));
                    target.setSpol(spolVrsteComboBox.getSelectionModel().getSelectedItem());
                    target.setIstrazivac(istrazivacComboBox.getSelectionModel().getSelectedItem());
                    target.setLokacija(lokacijaComboBox.getSelectionModel().getSelectedItem());
                    target.setDatum(datumDatePicker.getValue());

                    String afterChange = target.getId() + "," + target.getNaziv() + "," + target.getBrojnost() + ","+ target.getSpol()+","+target.getKomentari()+","+target.getIstrazivac()+"'"+target.getLokacija()+","+target.getDatum();
                    String user = odabraniUser.toUpperCase();
                    LocalDateTime ldt = LocalDateTime.now();
                    DateTimeFormatter dateTFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                    String dateOfChange = ldt.format(dateTFormat);

                    listaSTO=Serijalizacija.deserializeSTOList(listaSTO);
//                    System.out.println("Nakon izlaza iz deser funkcija>>>>");
//                    for(Serijalizacija q : listaSTO){
//                        System.out.println(q.getBeforeChange() + " " + q.getAfterChange() + " " + q.getUser() + " " + q.getDateOfChange());
//                    }

                    Serijalizacija changeNew = new Serijalizacija(beforeChange, afterChange, user, dateOfChange);
                    listaSTO.add(changeNew);

                    Serijalizacija.serializeSTO(listaSTO);
//                    System.out.println("Velicina listeSTO je: " + listaSTO.size());
//                    System.out.println("Vrijednosti nakon funkcie serijalizacije>>>> ");
//                    for (Serijalizacija q : listaSTO){
//                        System.out.println(q.getBeforeChange() + " " + q.getAfterChange() + " " + q.getUser() + " " + q.getDateOfChange());
//
//                    }
                    podaciTableView.setItems(crntPodatakList);
                    BazaPodataka.azurirajPodatak(toBeChanged);
                    podaciTableView.refresh();
                    break;
                }
            }
        }
    }

    @FXML
    public void deleteSelectedPodatak() throws Exception{
        BirdUnos abolished = podaciTableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Brisanje odabranog podatka");
        alert.setHeaderText(null);
        alert.setContentText("Zelite li ukloniti odabrani podatak?");

        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK){
            podaciTableView.getItems().remove(abolished);
            BazaPodataka.ukloniPodatak(abolished.getId());
        }
    }

    @FXML
    public void showNoviPodatakScreen() throws IOException {
        UnosPodatkaController.showUnosPodatkaScreen();
    }

    @Override
    public Integer countHowMany(List<BirdUnos> podaci) {
        Integer countAll = Math.toIntExact(podaci.stream()
                .filter(a -> a.getBrojnost() > 0)
                .count());
        return countAll;
    }

    @Override
    public Integer countFemale(List<BirdUnos> podaci) {
        Integer countFemale = Math.toIntExact(podaci.stream()
                .filter(f -> f.getSpol().toUpperCase().equals("F".toUpperCase()))
                .count());
        return countFemale;
    }

    @Override
    public Integer countMale(List<BirdUnos> podaci) {
        Integer countMale = Math.toIntExact(podaci.stream()
                .filter(m -> m.getSpol().toUpperCase().equals("M".toUpperCase()))
                .count());
        return countMale;
    }

    @Override
    public Integer countUnkonown(List<BirdUnos> podaci) {
        Integer countUnknown = Math.toIntExact(podaci.stream()
                .filter(u -> u.getSpol().toUpperCase().equals("U".toUpperCase()))
                .count());
        return countUnknown;
    }
}
