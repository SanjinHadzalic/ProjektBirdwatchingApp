package hr.java.vjezbe.entiteti;

import com.example.projektbirdwatchingapp.PregledPodatakaController;

import java.util.List;

sealed public interface Analiza permits PregledPodatakaController {

     Integer countHowMany(List<BirdUnos> podaci);
     Integer countFemale(List<BirdUnos> podaci);
     Integer countMale(List<BirdUnos> podaci);
     Integer countUnkonown(List<BirdUnos> podaci);
 }
