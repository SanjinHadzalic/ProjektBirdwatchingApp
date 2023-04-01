package hr.java.vjezbe.entiteti;

import java.time.LocalDate;

public class IstrazivacUnos{
    private Integer id;
    private String ime;
    private String prezime;
    private LocalDate datum;
    private String institucija;
    private String adresa;
    private Integer broj;
    private String email;

    public IstrazivacUnos(Integer id, String ime, String prezime, LocalDate datum, String institucija, String adresa, Integer broj, String email) {
        this.id=id;
        this.ime = ime;
        this.prezime = prezime;
        this.datum = datum;
        this.institucija = institucija;
        this.adresa = adresa;
        this.broj = broj;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getInstitucija() {
        return institucija;
    }

    public void setInstitucija(String institucija) {
        this.institucija = institucija;
    }

    public Integer getBroj() {
        return broj;
    }

    public void setBroj(Integer broj) {
        this.broj = broj;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
