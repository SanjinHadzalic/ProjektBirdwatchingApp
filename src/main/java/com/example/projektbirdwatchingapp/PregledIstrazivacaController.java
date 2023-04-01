package com.example.projektbirdwatchingapp;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entiteti.IstrazivacUnos;
import hr.java.vjezbe.util.Serijalizacija;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.util.Callback;
import org.w3c.dom.events.MouseEvent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PregledIstrazivacaController {

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
    private TableView<IstrazivacUnos> istrazivacPregledTableView;
    @FXML
    private TableColumn<IstrazivacUnos,String> imeIstrazivacaTableColumn;
    @FXML
    private TableColumn<IstrazivacUnos,String> prezimeIstrazivacaTableColumn;
    @FXML
    private TableColumn<IstrazivacUnos,String> datumRodjenjaIstrazivacaTableColumn;
    @FXML
    private TableColumn<IstrazivacUnos,String> institucijaIstrazivacaTableColumn;
    @FXML
    private TableColumn<IstrazivacUnos,String> adresaIstrazivacaTableColumn;
    @FXML
    private TableColumn<IstrazivacUnos,String> mobitelIstrazivacaTableColumn;
    @FXML
    private TableColumn<IstrazivacUnos,String> emailIstrazivacaTableColumn;
    private ArrayList<Serijalizacija> listaSTO = new ArrayList<>();
    @FXML
    public void initialize() throws FileNotFoundException {
        imeIstrazivacaTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getIme()));

        prezimeIstrazivacaTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getPrezime()));

        datumRodjenjaIstrazivacaTableColumn.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<IstrazivacUnos,String>,
                        ObservableValue<String>>(){
                    @Override
                    public ObservableValue<String> call(
                            TableColumn.CellDataFeatures<IstrazivacUnos, String> student) {
                        SimpleStringProperty property = new
                                SimpleStringProperty();
                        DateTimeFormatter formatter =
                                DateTimeFormatter.ofPattern("dd.MM.yyyy.");
                        property.setValue(
                                student.getValue().getDatum().format(formatter));
                        return property;
                    }
                });

        institucijaIstrazivacaTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getInstitucija()));

        adresaIstrazivacaTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getAdresa()));

        mobitelIstrazivacaTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getBroj().toString()));

        emailIstrazivacaTableColumn
                .setCellValueFactory(cellData ->
                        new SimpleStringProperty(cellData.getValue().getEmail()));

        istrazivacPregledTableView.setItems(FXCollections.observableList(HelloApplication.getIstrazivacUnosList()));
    }
    public static void showPregledIstrazivacaScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pregledIstrazivaca.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 750, 800);
        HelloApplication.getMainStage().setTitle("Pregled istrazivaca");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }

    @FXML
    public void filterIstrazivac(){
        String imeIstrazivac = imeIstrazivacaTextField.getText();
        String prezimeIStrazivac = prezimeIStrazivacaTextField.getText();
        LocalDate datumIstrazivac = datumRodjenjaIstrazivacaTextField.getValue();
        String institucijaIstrazivaca = institucijaIstrazivacaTextField.getText();
        String adresaIstrazivaca = adresaIstrazivacaTextField.getText();
        String mobitelIstrazivaca = mobitelIstrazivacaTextField.getText();
        String emailIstrazivaca = emailIstrazivacaTextField.getText();

        List<IstrazivacUnos> filterIStrazivacUnos = new ArrayList<>(HelloApplication.getIstrazivacUnosList());

        if(Optional.of(imeIstrazivac).isPresent() == true){
            filterIStrazivacUnos = filterIStrazivacUnos.stream()
                    .filter(s -> s.getIme().toLowerCase().contains(imeIstrazivac.toLowerCase()))
                    .collect(Collectors.toList());
        }
        if(Optional.of(prezimeIStrazivac).isPresent() == true){
            filterIStrazivacUnos = filterIStrazivacUnos.stream()
                    .filter(s->s.getPrezime().toLowerCase().contains(prezimeIStrazivac.toLowerCase()))
                    .collect(Collectors.toList());
        }
        if(Optional.ofNullable(datumIstrazivac).isEmpty() == false){
            filterIStrazivacUnos = filterIStrazivacUnos.stream()
                    .filter(s->s.getDatum().equals(datumIstrazivac))
                    .collect(Collectors.toList());
        }
        if(Optional.of(institucijaIstrazivaca).isPresent() == true){
            filterIStrazivacUnos = filterIStrazivacUnos.stream()
                    .filter(s->s.getInstitucija().toLowerCase().contains(institucijaIstrazivaca.toLowerCase()))
                    .collect(Collectors.toList());
        }
        if (Optional.of(adresaIstrazivaca).isPresent() == true){
            filterIStrazivacUnos = filterIStrazivacUnos.stream()
                    .filter(s->s.getAdresa().toLowerCase().contains(adresaIstrazivaca.toLowerCase()))
                    .collect(Collectors.toList());
        }
        if (Optional.of(mobitelIstrazivaca).isPresent() == true){
            filterIStrazivacUnos = filterIStrazivacUnos.stream()
                    .filter(s->s.getBroj().toString().contains(mobitelIstrazivaca))
                    .collect(Collectors.toList());
        }
        if(Optional.of(emailIstrazivaca).isPresent() == true){
            filterIStrazivacUnos = filterIStrazivacUnos.stream()
                    .filter(s->s.getEmail().toLowerCase().contains(emailIstrazivaca.toLowerCase()))
                    .collect(Collectors.toList());
        }

        istrazivacPregledTableView.setItems(FXCollections.observableList(filterIStrazivacUnos));
    }

    @FXML
    public void onRowClickedShow() throws Exception{
        IstrazivacUnos changed = istrazivacPregledTableView.getSelectionModel().getSelectedItem();

        imeIstrazivacaTextField.setText(String.valueOf(changed.getIme()));
        prezimeIStrazivacaTextField.setText(String.valueOf(changed.getPrezime()));
        datumRodjenjaIstrazivacaTextField.setValue(LocalDate.parse(String.valueOf(changed.getDatum())));
        institucijaIstrazivacaTextField.setText(String.valueOf(changed.getInstitucija()));
        adresaIstrazivacaTextField.setText(String.valueOf(changed.getAdresa()));
        mobitelIstrazivacaTextField.setText(String.valueOf(changed.getBroj()));
        emailIstrazivacaTextField.setText(String.valueOf(changed.getEmail()));
    }

    @FXML
    public void clearSelection() throws Exception{
        imeIstrazivacaTextField.clear();
        prezimeIStrazivacaTextField.clear();
        datumRodjenjaIstrazivacaTextField.setValue(null);
        institucijaIstrazivacaTextField.clear();
        adresaIstrazivacaTextField.clear();
        mobitelIstrazivacaTextField.clear();
        emailIstrazivacaTextField.clear();
    }
    @FXML
    public void submit(ActionEvent event) throws Exception {
        ObservableList<IstrazivacUnos> crntIstrazivacList = istrazivacPregledTableView.getItems();
        IstrazivacUnos toBeChanged = istrazivacPregledTableView.getSelectionModel().getSelectedItem();
        String beforeChange = toBeChanged.getId()+","+toBeChanged.getIme()+","+toBeChanged.getPrezime()+","+toBeChanged.getDatum()+","+toBeChanged.getInstitucija()+","+toBeChanged.getBroj()+","+toBeChanged.getAdresa()+","+toBeChanged.getEmail();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Azuriranje odabranog korisnika");
        alert.setHeaderText("Zelite li istrazivacu " + toBeChanged.getIme() + " " + toBeChanged.getPrezime() + " promijeniti vrijednosti:");
        alert.setContentText(toBeChanged.getIme() + " u " + imeIstrazivacaTextField.getText() + "\n " +
                            toBeChanged.getPrezime() + " u " + prezimeIStrazivacaTextField.getText() + "\n " +
                            toBeChanged.getDatum() + " u " + datumRodjenjaIstrazivacaTextField.getValue() + "\n " +
                            toBeChanged.getInstitucija() + " u " + institucijaIstrazivacaTextField.getText() + "\n " +
                            toBeChanged.getBroj() + " u " + mobitelIstrazivacaTextField.getText() + "\n " +
                            toBeChanged.getAdresa() + " u " + adresaIstrazivacaTextField.getText() + "\n " +
                            toBeChanged.getEmail() + " u " + emailIstrazivacaTextField.getText() + "\n");

        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK){
            Integer currentIstrazivacId = Integer.parseInt(String.valueOf(toBeChanged.getId()));

            for(IstrazivacUnos target : crntIstrazivacList){
                if(target.getId() == currentIstrazivacId){
                    target.setIme(imeIstrazivacaTextField.getText());
                    target.setPrezime(prezimeIStrazivacaTextField.getText());
                    target.setDatum(datumRodjenjaIstrazivacaTextField.getValue());
                    target.setInstitucija(institucijaIstrazivacaTextField.getText());
                    target.setAdresa(adresaIstrazivacaTextField.getText());
                    target.setBroj(Integer.valueOf(mobitelIstrazivacaTextField.getText()));
                    target.setEmail(emailIstrazivacaTextField.getText());

                    String afterChange = target.getId()+","+target.getIme()+","+target.getPrezime()+","+target.getDatum()+","+target.getInstitucija()+","+target.getBroj()+","+target.getAdresa()+","+target.getEmail();
                    String user = LoginController.odabraniUser;
                    LocalDateTime ldt = LocalDateTime.now();
                    DateTimeFormatter dateTFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                    String dateOfChange = ldt.format(dateTFormat);

                    listaSTO=Serijalizacija.deserializeSTOList(listaSTO);
                    Serijalizacija serSTO = new Serijalizacija(beforeChange,afterChange,user,dateOfChange);
                    listaSTO.add(serSTO);
                    Serijalizacija.serializeSTO(listaSTO);

                    istrazivacPregledTableView.setItems(crntIstrazivacList);
                    BazaPodataka.azurirajIstrazivaca(toBeChanged);
                    istrazivacPregledTableView.refresh();
                    break;
                }
            }
        }

    }
    @FXML
    public void deleteSelectedIstrazivac() throws Exception{
        IstrazivacUnos abolished = istrazivacPregledTableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Brisanje odabranog korisnika");
        alert.setHeaderText(null);
        alert.setContentText("Zelite li ukloniti odabranog istrazivaca?");

        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK){
            istrazivacPregledTableView.getItems().remove(abolished);
            BazaPodataka.ukloniIstrazivaca(abolished.getId());
        }
    }

    @FXML
    public void addNewUserSpona() throws IOException {
        UnosIstrazivacaController.showUnosIstrazivacaScreen();
    }
    public void natragButtonClicked() throws IOException {
        MainMenuController.showMainMenuScreen();
    }
}
