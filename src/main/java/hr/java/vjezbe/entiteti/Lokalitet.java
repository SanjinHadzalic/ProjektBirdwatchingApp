package hr.java.vjezbe.entiteti;

import java.util.List;

/**
 * countUnos() metoda prebrojava ukupan unos podataka po lokaciji
 */
public non-sealed class Lokalitet implements Istrazivac {
    private Integer id;
    private String nazivLokacije;
    private String typeLocation;
    private String xCoord;
    private String yCoord;
    private List<BirdUnos> unosi;

    public Lokalitet(Integer id, String nazivLokacije, String typeLocation, String xCoord, String yCoord) {
        this.id=id;
        this.nazivLokacije = nazivLokacije;
        this.typeLocation = typeLocation;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNazivLokacije() {
        return nazivLokacije;
    }

    public void setNazivLokacije(String nazivLokacije) {
        this.nazivLokacije = nazivLokacije;
    }

    public String getTypeLocation() {
        return typeLocation;
    }

    public void setTypeLocation(String typeLocation) {
        this.typeLocation = typeLocation;
    }

    public String getxCoord() {
        return xCoord;
    }

    public void setxCoord(String xCoord) {
        this.xCoord = xCoord;
    }

    public String getyCoord() {
        return yCoord;
    }

    public void setyCoord(String yCoord) {
        this.yCoord = yCoord;
    }

    public List<BirdUnos> getUnosi() {
        return unosi;
    }

    public void setUnosi(List<BirdUnos> unosi) {
        this.unosi = unosi;
    }

    @Override
    public int countUnos(Lokalitet lok) {
        int counter = 0;
        for(int i = 0; i < lok.unosi.size(); i++){
            if(lok.unosi.get(i).getBrojnost()>0){
                counter++;
            }
        }
        return counter;
    }

}
