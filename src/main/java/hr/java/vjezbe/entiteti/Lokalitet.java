package hr.java.vjezbe.entiteti;

import java.util.List;

/**
 * countUnos() metoda prebrojava ukupan unos podataka po lokaciji
 */
public non-sealed class Lokalitet implements Istrazivac, Analiza {
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
    @Override
    public int[] countGender(List<Lokalitet> lok) {
        int[] container = new int[3];
        int countM = 0;
        int countF = 0;
        int countU = 0;

        for(int i = 0; i < lok.size(); i ++){
            for(int j = 0; j < lok.get(i).getUnosi().size(); j++) {
                if (lok.get(i).getUnosi().get(j).getSpol().equals("M")) {
                    countM++;
                    container[0] = countM;
                } else if (lok.get(i).getUnosi().get(j).getSpol().equals("F")) {
                    countF++;
                    container[1] = countF;
                } else {
                    countU++;
                    container[2] = countU;
                }
            }
        }
        return container;
    }

}
