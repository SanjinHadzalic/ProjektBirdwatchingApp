package hr.java.vjezbe.entiteti;

import java.util.List;

public class IstrazivacUnosBuilder {
    private String ime;
    private String prezime;
    private String institucija;
    private Double broj;
    private List<Lokalitet> lokaliteti;

    public IstrazivacUnosBuilder setIme(String ime) {
        this.ime = ime;
        return this;
    }

    public IstrazivacUnosBuilder setPrezime(String prezime) {
        this.prezime = prezime;
        return this;
    }

    public IstrazivacUnosBuilder setInstitucija(String institucija) {
        this.institucija = institucija;
        return this;
    }

    public IstrazivacUnosBuilder setBroj(Double broj) {
        this.broj = broj;
        return this;
    }

    public IstrazivacUnosBuilder setLokaliteti(List<Lokalitet> lokaliteti) {
        this.lokaliteti = lokaliteti;
        return this;
    }

//    public IstrazivacUnos createIstrazivacUnos() {
//        return new IstrazivacUnos(ime, prezime, institucija, broj, lokaliteti);
//    }
}