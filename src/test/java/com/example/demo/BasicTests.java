package com.example.demo;

import com.example.projektbirdwatchingapp.Application;
import com.example.projektbirdwatchingapp.PregledPodatakaController;
import de.jensd.fx.glyphs.testapps.App;
import hr.java.vjezbe.entiteti.Analiza;
import hr.java.vjezbe.entiteti.BirdUnos;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class BasicTests{
    PregledPodatakaController p = new PregledPodatakaController();
    List<BirdUnos> testList = List.of(
            new BirdUnos(1,"kos",2,"F","","Ana Jovanovic","Mljet", LocalDate.of(2022,02,02)),
            new BirdUnos(2,"crvendac",2,"F","","Ana Jovanovic","Mljet", LocalDate.of(2022,02,02)),
            new BirdUnos(3,"kos",1,"U","","Ana Jovanovic","Mljet", LocalDate.of(2022,02,02)),
            new BirdUnos(4,"skanjac",2,"F","","Ana Jovanovic","Mljet", LocalDate.of(2022,02,02)),
            new BirdUnos(5,"skanjac",2,"M","","Ana Jovanovic","Mljet", LocalDate.of(2022,02,02)),
            new BirdUnos(6,"skanjac",2,"M","","Ana Jovanovic","Mljet", LocalDate.of(2022,02,02))
    );

    @Test
    @DisplayName("Lista sadrzi cetiri zapisa")
    public void testCountHowMany(){
        int value = p.countHowMany(testList);

        Assertions.assertEquals(6,value);
    }

    @Test
    @DisplayName("Lista sadrzi tri zenke")
    public void testCountFemale(){
        int value = p.countFemale(testList);

        Assertions.assertEquals(3, value);
    }

    @Test
    @DisplayName("Lista sadrzi dva muzjaka")
    public void testCountMale(){
        int value = p.countMale(testList);

        Assertions.assertEquals(3,value);
    }

    @Test
    @DisplayName("Lista sadrzi jednog nepoznatog")
    public void testCountUnknown(){
        int value = p.countUnkonown(testList);

        Assertions.assertEquals(2,value);

    }
}
