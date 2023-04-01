package hr.java.vjezbe.util;

import hr.java.vjezbe.entiteti.BirdUnos;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Serijalizacija implements Serializable {
    private static final long serialVersionUID = 6460948376697488364L;

    public String beforeChange;
    public String afterChange;
    public String user;
    public String dateOfChange;

    public Serijalizacija(String beforeChange, String afterChange, String user, String dateOfChange) {
        this.beforeChange = beforeChange;
        this.afterChange = afterChange;
        this.user = user;
        this.dateOfChange = dateOfChange;
    }

    public String getBeforeChange() {
        return beforeChange;
    }

    public void setBeforeChange(String beforeChange) {
        this.beforeChange = beforeChange;
    }

    public String getAfterChange() {
        return afterChange;
    }

    public void setAfterChange(String afterChange) {
        this.afterChange = afterChange;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDateOfChange() {
        return dateOfChange;
    }

    public void setDateOfChange(String dateOfChange) {
        this.dateOfChange = dateOfChange;
    }

    public static void serializeSTO(ArrayList<Serijalizacija> sto){

        try{
            FileOutputStream fileOut = new FileOutputStream("src/main/java/hr/java/vjezbe/util/serSTOList.txt");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(sto);
            out.close();
            fileOut.close();
            System.out.println("Objekt uspjesno serijaliziran\n");
        } catch (IOException ex){
            ex.printStackTrace();
        }
    }

    public static ArrayList<Serijalizacija> deserializeSTOList(ArrayList<Serijalizacija> deserLista){
        try{
            FileInputStream fileIn = new FileInputStream("src/main/java/hr/java/vjezbe/util/serSTOList.txt");
            ObjectInputStream in = new ObjectInputStream(fileIn);


             deserLista = (ArrayList<Serijalizacija>) in.readObject();

            in.close();
            fileIn.close();
        } catch (IOException i){
            i.printStackTrace();
        }catch (ClassNotFoundException c){
            System.out.println("Serijalizacija class not found");
            c.printStackTrace();
        }
        //validacija
        System.out.println("Ucitane vrijednosti unutar funkcije deserijalizacije su: ");
        for (Serijalizacija q : deserLista){
            System.out.println(q.getBeforeChange() + " " + q.getAfterChange() + " " + q.getUser() + " " + q.getDateOfChange());
        }
        return deserLista;
    }
}
