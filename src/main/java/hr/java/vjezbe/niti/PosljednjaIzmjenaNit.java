package hr.java.vjezbe.niti;

import hr.java.vjezbe.util.Serijalizacija;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PosljednjaIzmjenaNit implements Runnable{
    @Override
    public void run(){
        ArrayList<Serijalizacija> listaSTO = new ArrayList<>();
        listaSTO=Serijalizacija.deserializeSTOList(listaSTO);

        System.out.println("Velicina listeSTO je " + listaSTO.size());
        Serijalizacija novi = listaSTO.get(listaSTO.size()-1);
        String a = novi.getBeforeChange();
        String b = novi.getAfterChange();
        String datum = novi.getDateOfChange();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Testiranje niti za posljednju izmjenu");
        alert.setHeaderText("Posljednja primjena je napravljena: " + datum + "\n");
        alert.setContentText(a + "\n " + b);

        alert.show();

    }
}
