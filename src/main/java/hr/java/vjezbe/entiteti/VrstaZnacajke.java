package hr.java.vjezbe.entiteti;

/**
 *  sadrzi atribute za vrstu ptice; kategorija(grabljivica, vodarica, pjevica, curlin)
 *  ugrozenost(da, ne)
 *  zadnjaGodina - zadnja godina opazanja vrste
 */

public abstract class VrstaZnacajke {
    public int kategorija;
    public String ugrozenost;
    public Integer zadnjaGodina;

    public VrstaZnacajke(int kategorija) {
    }

    public int getKategorija() {
        return kategorija;
    }

    public void setKategorija(int kategorija) {
        this.kategorija = kategorija;
    }

    public String getUgrozenost() {
        return ugrozenost;
    }

    public void setUgrozenost(String ugrozenost) {
        this.ugrozenost = ugrozenost;
    }

    public Integer getZadnjaGodina() {
        return zadnjaGodina;
    }

    public void setZadnjaGodina(Integer zadnjaGodina) {
        this.zadnjaGodina = zadnjaGodina;
    }

    public abstract void izvjestaj();
}
