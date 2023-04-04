package com.example.projektbirdwatchingapp;

import hr.java.vjezbe.baza.BazaPodataka;
import hr.java.vjezbe.entiteti.IstrazivacUnos;
import hr.java.vjezbe.entiteti.Lokalitet;
import hr.java.vjezbe.util.Serijalizacija;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class PregledLokacijaController {
    String probaGlob = "NP";
    @FXML
    private TextField nazivLokacijeTextField;
    @FXML
    private TextField x_coordTextField;
    @FXML
    private TextField y_coordTextField;
    @FXML
    private RadioButton nacionalniParkRadioButton = new RadioButton("NP");
    @FXML
    private RadioButton parkPrirodeRadioButton = new RadioButton("PP");
    @FXML
    private RadioButton ostaloRadioButton = new RadioButton("Ostalo");
    @FXML
    private ToggleGroup parkToggleGroup;
    @FXML
    private TableView<Lokalitet> lokacijeTableView;
    @FXML
    private TableColumn<Lokalitet, String> nazivLokacijeTableColumn;
    @FXML
    private TableColumn<Lokalitet, String> tipLokacijeTableColumn;
    @FXML
    private TableColumn<Lokalitet, String> xCoordLokacijeTableColumn;
    @FXML
    private TableColumn<Lokalitet, String> yCoordLokacijeTableColumn;
    private ArrayList<Serijalizacija> listaSTO = new ArrayList<>();
    private final AtomicBoolean running = new AtomicBoolean(true);

    @FXML
    public void initialize(){
        nazivLokacijeTableColumn.setCellValueFactory(
                cellData->new SimpleStringProperty(cellData.getValue().getNazivLokacije())
        );
        tipLokacijeTableColumn.setCellValueFactory(
                cellData->new SimpleStringProperty(cellData.getValue().getTypeLocation())
        );
        xCoordLokacijeTableColumn.setCellValueFactory(
                cellData->new SimpleStringProperty(cellData.getValue().getxCoord().toString())
        );
        yCoordLokacijeTableColumn.setCellValueFactory(
                cellData->new SimpleStringProperty(cellData.getValue().getyCoord().toString())
        );

        parkToggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                RadioButton chk = (RadioButton)newValue.getToggleGroup().getSelectedToggle();
                if (chk.getText().equals("Nacionalni park")){
                    probaGlob = "NP";
                    System.out.println("Selected Radio Button: " + chk.getText());
                } else if (chk.getText().equals("Park prirode")) {
                    probaGlob = "PP";
                    System.out.println("Selected Radio Button: " + chk.getText());
                } else if (chk.getText().equals("Ostalo")) {
                    probaGlob = "Ostalo";
                    System.out.println("Selected Radio Button: " + chk.getText());
                }
            }
        });

        refreshLokacija(running);
    }
    public Thread refreshLokacija(AtomicBoolean running){
        Thread t = new Thread(() -> {
            while(running.get()){
                System.out.println("Thread za refresh lokacija radi\n");
                Platform.runLater(() ->{
                    lokacijeTableView.setItems(FXCollections.observableList(HelloApplication.getLokacijaList()));
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
    public void filterLokacije() {
        String nazivLokacije = nazivLokacijeTextField.getText();
        String tipLokacije = probaGlob;
        String x_coord = x_coordTextField.getText();
        String y_coord = y_coordTextField.getText();

        List<Lokalitet> filterLokacija = new ArrayList<>(HelloApplication.getLokacijaList());

        if (Optional.of(nazivLokacije).isPresent() == true) {
            filterLokacija = filterLokacija.stream()
                    .filter(s -> s.getNazivLokacije().toLowerCase().contains(nazivLokacije.toLowerCase()))
                    .collect(Collectors.toList());
        }
        if (Optional.ofNullable(tipLokacije).isPresent() == true){
            filterLokacija = filterLokacija.stream()
                    .filter(s -> s.getTypeLocation().toLowerCase().contains(tipLokacije.toLowerCase()))
                    .collect(Collectors.toList());
        }
        if (Optional.of(x_coord).isPresent() == true) {
            filterLokacija = filterLokacija.stream()
                    .filter(s -> s.getxCoord().contains(x_coord))
                    .collect(Collectors.toList());
        }
        if (Optional.of(y_coord).isPresent() == true){
            filterLokacija = filterLokacija.stream()
                    .filter(s -> s.getyCoord().contains(y_coord))
                    .collect(Collectors.toList());
        }

        lokacijeTableView.setItems(FXCollections.observableList(filterLokacija));
        System.out.println(nazivLokacije + " " +  tipLokacije + " " + x_coord + " " +  y_coord);
        running.set(false);
    }

    @FXML
    public void onRowClickedShowLokalitet() throws Exception{
        Lokalitet clickedRowLokalitet = lokacijeTableView.getSelectionModel().getSelectedItem();

        nazivLokacijeTextField.setText(String.valueOf(clickedRowLokalitet.getNazivLokacije()));
        switch (clickedRowLokalitet.getTypeLocation()) {
            case "NP" -> {
                nacionalniParkRadioButton.setSelected(true);
                System.out.println("Odabirom retka je odabran i NP radio button");
            }
            case "Ostalo" -> {
                ostaloRadioButton.setSelected(true);
                System.out.println("Odabirom retka je odabran i OSTALO radio button");
            }
            case "PP" -> {
                parkPrirodeRadioButton.setSelected(true);
                System.out.println("Odabirom retka je odabran i PP radio button");
            }
        }
        x_coordTextField.setText(String.valueOf(clickedRowLokalitet.getxCoord()));
        y_coordTextField.setText(String.valueOf(clickedRowLokalitet.getyCoord()));
    }



    @FXML
    public void updateSelectedLokacijja(ActionEvent event) throws Exception {
        ObservableList<Lokalitet> crntLokalitetList = lokacijeTableView.getItems();
        Lokalitet toBeChanged = lokacijeTableView.getSelectionModel().getSelectedItem();
        String beforeChange = toBeChanged.getId()+","+toBeChanged.getNazivLokacije()+","+toBeChanged.getTypeLocation()+","+toBeChanged.getxCoord()+","+toBeChanged.getyCoord();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Azuriranje odabrane lokacije");
        alert.setHeaderText("Zelite li lokaciji >>" + toBeChanged.getNazivLokacije() +  "<< promijeniti vrijednosti:");
        alert.setContentText(toBeChanged.getNazivLokacije() + " u " + nazivLokacijeTextField.getText() + "\n " +
                toBeChanged.getTypeLocation() + " u " + probaGlob + "\n " +
                toBeChanged.getxCoord() + " u " + x_coordTextField.getText() + "\n " +
                toBeChanged.getyCoord() + " u " + y_coordTextField.getText() + "\n ");

        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK){
            Integer currentLokacijaId = Integer.parseInt(String.valueOf(toBeChanged.getId()));

            for(Lokalitet target : crntLokalitetList){
                if(target.getId() == currentLokacijaId){
                    target.setNazivLokacije(nazivLokacijeTextField.getText());
                    target.setTypeLocation(probaGlob);
                    target.setxCoord(x_coordTextField.getText());
                    target.setyCoord(y_coordTextField.getText());

                    String afterChange = target.getId()+","+target.getNazivLokacije()+","+target.getTypeLocation()+","+target.getxCoord()+","+target.getyCoord();
                    String user = LoginController.odabraniUser;
                    LocalDateTime ldt = LocalDateTime.now();
                    DateTimeFormatter dateTFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                    String dateOfChange = ldt.format(dateTFormat);

                    listaSTO=Serijalizacija.deserializeSTOList(listaSTO);
                    Serijalizacija serSTO = new Serijalizacija(beforeChange,afterChange,user,dateOfChange);
                    listaSTO.add(serSTO);
                    Serijalizacija.serializeSTO(listaSTO);

                    lokacijeTableView.setItems(crntLokalitetList);
                    BazaPodataka.azurirajLLokalitet(toBeChanged);
                    lokacijeTableView.refresh();
                    break;
                }
            }
        }
    }

    @FXML
    public void clearSelection() throws Exception{
        nazivLokacijeTextField.clear();
        nacionalniParkRadioButton.setSelected(true);
        x_coordTextField.clear();
        y_coordTextField.clear();
        running.set(true);
        refreshLokacija(running);
    }

    @FXML
    public void deleteSelectedLokacija() throws Exception{
        Lokalitet abolished = lokacijeTableView.getSelectionModel().getSelectedItem();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Brisanje odabrane lokacije");
        alert.setHeaderText(null);
        alert.setContentText("Zelite li ukloniti odabranu lokaciju?");

        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK){
            lokacijeTableView.getItems().remove(abolished);
            BazaPodataka.ukloniLokaciju(abolished.getId());
        }
    }
    public static void showPregledLokacijaScreen() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("pregledLokacija.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 650, 500);
        HelloApplication.getMainStage().setTitle("Pregled lokacija");
        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }

    @FXML
    public void showUnosLokalitetaScreen() throws IOException {
        UnosLokalitetaController.showUnosLokalitetaScreen();
    }
    public void natragButtonClicked() throws IOException {
        MainMenuController.showMainMenuScreen();
    }

}
