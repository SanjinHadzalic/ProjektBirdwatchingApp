package com.example.projektbirdwatchingapp;

import hr.java.vjezbe.entiteti.BinarnaNomenklatura;
import hr.java.vjezbe.entiteti.BirdUnos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.GridPane;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.example.projektbirdwatchingapp.LoginController.odabraniUser;

public class BarChartVrsteController {
        public static String changer = "zeba";
        ObservableList<BirdUnos> data = FXCollections.observableArrayList(Application.getPodatakList());
        XYChart.Series series1 = new XYChart.Series();
        @FXML
        private GridPane barChartGridPane;
        @FXML
        private BarChart barChartVrste;
        @FXML
        private ComboBox vrsteComboBox;
        @FXML
        public void initialize() throws FileNotFoundException {
            List<BirdUnos> non=data.stream().filter(distinctByKey(a->a.getIstrazivac())).collect(Collectors.toList());
            System.out.println("INIT changer je " + changer);
            System.out.println(non.size());

            for (BirdUnos a: data
                 ) {
                series1.getData().add(new XYChart.Data(a.getIstrazivac(), data.stream().filter(b->b.getNaziv().equals(changer) && b.getIstrazivac().equals(a.getIstrazivac())).count()));
            }
            series1.setName("Istrazivaci");

            barChartVrste.getData().addAll(series1);
            barChartVrste.setAnimated(false);
            barChartVrste.setBarGap(0);
            barChartVrste.setMaxSize(650,450);

            vrsteComboBox.setEditable(true);
            vrsteComboBox.setValue(changer);
//            vrsteComboBox.setValue("zeba");
            vrsteComboBox.getItems().setAll(Arrays.stream(BinarnaNomenklatura.values()).sorted(Comparator.comparing(BinarnaNomenklatura::getVrsta)).toList());
        }
        public void changeVrsta() throws IOException {
            changer = vrsteComboBox.getSelectionModel().getSelectedItem().toString();
//            System.out.println(vrsteComboBox.getSelectionModel().getSelectedItem().toString());
//            System.out.println("Changer: "+changer);

            showBarChartVrste(changer);
//            barChartVrste.getData().remove(series1);
//            barChartVrste.getData().clear();
//            barChartVrste.layout();
//
//            for (BirdUnos a: data
//            ) {
//                series1.getData().add(new XYChart.Data(a.getIstrazivac(), data.stream().filter(b->b.getNaziv().equals(changer) && b.getIstrazivac().equals(a.getIstrazivac())).count()));
//            }
//
//            barChartVrste.getData().add(series1);
        }
        public static void showBarChartVrste(String changerDva) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("vrsteBarChart.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        scene.getStylesheets().add("main.css");

        changer=changerDva;

        Application.getMainStage().setTitle("BarChart vrste");
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
    public static <T> Predicate<T> distinctByKey(
            Function<? super T, ?> keyExtractor) {

        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
