package hr.java.vjezbe.entiteti;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public final class BirdUnos implements Serializable {
    private Integer id;
    private String naziv;
    private Integer brojnost;
    private String spol;
    private String komentari;
    private String istrazivac;
    private String lokacija;
    private LocalDate datum;

    public BirdUnos(Integer id, String naziv, Integer brojnost, String spol, String komentari, String istrazivac, String lokacija, LocalDate datum) {
        this.id = id;
        this.naziv = naziv;
        this.brojnost = brojnost;
        this.spol = spol;
        this.komentari = komentari;
        this.istrazivac = istrazivac;
        this.lokacija = lokacija;
        this.datum = datum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Integer getBrojnost() {
        return brojnost;
    }

    public void setBrojnost(Integer brojnost) {
        this.brojnost = brojnost;
    }

    public String getSpol() {
        return spol;
    }

    public void setSpol(String spol) {
        this.spol = spol;
    }

    public String getKomentari() {
        return komentari;
    }

    public void setKomentari(String komentari) {
        this.komentari = komentari;
    }

    public String getIstrazivac() {
        return istrazivac;
    }

    public void setIstrazivac(String istrazivac) {
        this.istrazivac = istrazivac;
    }

    public String getLokacija() {
        return lokacija;
    }

    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }
}
