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
    List<BirdUnos> testList = List.of(
            new BirdUnos(1,"kos",2,"F","","Ana Jovanovic","Mljet", LocalDate.of(2022,02,02)),
            new BirdUnos(2,"crvendac",2,"F","","Ana Jovanovic","Mljet", LocalDate.of(2022,02,02)),
            new BirdUnos(3,"kos",1,"U","","Ana Jovanovic","Mljet", LocalDate.of(2022,02,02)),
            new BirdUnos(4,"skanjac",2,"M","","Ana Jovanovic","Mljet", LocalDate.of(2022,02,02))
            );

    @Test
    @DisplayName("Lista sadrzi cetiri zapisa")
    public void test(){
        PregledPodatakaController p = new PregledPodatakaController();

        int value = p.countHowMany(testList);

        Assertions.assertEquals(4,value);
    }
}
