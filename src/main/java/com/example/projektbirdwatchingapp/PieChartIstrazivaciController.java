package com.example.projektbirdwatchingapp;

import hr.java.vjezbe.entiteti.BirdUnos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.GridPane;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.example.projektbirdwatchingapp.LoginController.odabraniUser;

public class PieChartIstrazivaciController {
    @FXML
    private GridPane istrazivaciPieChartGridPane;
    @FXML
    private PieChart istrazivacPieChart;
    @FXML
    public void initialize() throws FileNotFoundException {
        ObservableList<BirdUnos> data = FXCollections.observableArrayList(Application.getPodatakList());

        List<BirdUnos> non=data.stream().filter(distinctByKey(a->a.getIstrazivac())).collect(Collectors.toList());

        ObservableList<PieChart.Data> dataDva = FXCollections.observableArrayList();

        for (BirdUnos b: non
             ) {
                    dataDva.add(new PieChart.Data(b.getIstrazivac(), data.stream().filter(a->a.getIstrazivac().equals(b.getIstrazivac())).count()));
        }

        istrazivacPieChart.setData(dataDva); // setting data
        istrazivacPieChart.setLabelsVisible(true);//set label visible
        istrazivacPieChart.setLegendSide(Side.RIGHT);
    }

        public static void showPieChartIstrazivac() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("pieChartIstrazivaci.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);
        scene.getStylesheets().add("main.css");
        Application.getMainStage().setTitle("PieChart Istrazivac");
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
