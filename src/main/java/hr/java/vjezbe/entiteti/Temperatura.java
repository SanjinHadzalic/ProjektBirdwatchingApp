package hr.java.vjezbe.entiteti;

public class Temperatura <T>{
    private T temp;

    public Temperatura(T temp) {
        this.temp = temp;
    }

    public T getTemp() {
        return temp;
    }

    public void setTemp(T temp) {
        this.temp = temp;
    }

    public void printTemp(){
        System.out.println("Unesena temperatura za lokaciju je " + temp);;
    }


}
