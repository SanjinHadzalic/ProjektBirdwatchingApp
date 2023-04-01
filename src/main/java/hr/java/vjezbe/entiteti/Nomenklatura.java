package hr.java.vjezbe.entiteti;

import java.util.List;

/**
 * enumeracija koja sadrzi hrvatske nazive vrsta te pripadajuce znanstvene nazive vrsta.
 */
public enum Nomenklatura {
    velika_sjenica("Parus major"),
    zeba("Fringilla coelebs"),
    kos("Turdus merula"),
    skanjac("Buteo buteo"),
    vjetrusa("Falco tinnunculus"),
    divlja_patka("anas platyrhynchos"),
    cesljugar("Carduelis carduelis"),
    zelendur("Carduelic chloris"),
    vrabac("Passer domesticus"),
    poljski_vrabac("Passer montanus"),
    golub_grivnjas("Columba palumbus");
    private String vrsta;

    public String getVrsta(){
        return this.vrsta;
    }
    private Nomenklatura(String vrsta) {
        this.vrsta=vrsta;
    }
}
