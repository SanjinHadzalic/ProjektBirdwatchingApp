package hr.java.vjezbe.baza;

import hr.java.vjezbe.entiteti.BirdUnos;
import hr.java.vjezbe.entiteti.IstrazivacUnos;
import hr.java.vjezbe.entiteti.Lokalitet;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.sql.*;
import org.slf4j.Logger;

public class BazaPodataka {
    private static final Logger logger = LoggerFactory.getLogger(BazaPodataka.class);
    private static final String DATABASE_FINAL = "dat/bazaPodataka.properties";

    private static Connection spajanjeNaBazu() throws Exception{
        Properties konfiguracijaBaze = new Properties();
        konfiguracijaBaze.load(new FileInputStream(DATABASE_FINAL));

        Connection con = DriverManager.getConnection(
                konfiguracijaBaze.getProperty("bazaPodatakaUrl"),
                konfiguracijaBaze.getProperty("korisnickoIme"),
                konfiguracijaBaze.getProperty("lozinka"));

                return con;
    }

    public static List<IstrazivacUnos> dohvatiSveIstrazivace() {
        List<IstrazivacUnos> listaIstrazivacUnos = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rst = null;
        try{
            con = spajanjeNaBazu();

            if (con != null){
                System.out.println("Uspjesno smo se spojili na bazu podataka!\n");
            }

            stmt = con.createStatement();

            rst = stmt.executeQuery("SELECT * FROM ISTRAZIVAC;");

            while (rst.next()){
                Integer id = rst.getInt("id");
                String ime = rst.getString("ime");
                String prezime = rst.getString("prezime");
                LocalDate datumRodjenja = rst.getDate("datum_rodjenja").toLocalDate();
                String institucija = rst.getString("institucija");
                String adresa = rst.getString("adresa");
                Integer telefon = rst.getInt("telefon");
                String email = rst.getString("email");

                IstrazivacUnos newIStrazivac = new IstrazivacUnos(id, ime, prezime, datumRodjenja, institucija, adresa, telefon, email);

                listaIstrazivacUnos.add(newIStrazivac);
            }
        } catch (Exception ex){
            String m = "Doslo je do greske tijekom spajanja na bazu podataka!";
//            System.out.println("Doslo je do greske tijekom spajanja na bazu podataka!\n");
            logger.error(m);
//            ex.printStackTrace();
        }finally {
            try{
                rst.close();
                stmt.close();
                con.close();
            } catch (SQLException sqlex){
                sqlex.printStackTrace();
            }
        }
        return listaIstrazivacUnos;
    }

    public static void spremiNovogIstrazivaca(IstrazivacUnos istrazivac) throws Exception{
        try(Connection con = spajanjeNaBazu()){
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO ISTRAZIVAC(ime, prezime, datum_rodjenja, institucija, adresa, telefon, email) VALUES(?, ?, ?, ?, ?, ?, ?);");

            pstmt.setString(1, istrazivac.getIme());
            pstmt.setString(2, istrazivac.getPrezime());
            pstmt.setDate(3, Date.valueOf(istrazivac.getDatum()));
            pstmt.setString(4, istrazivac.getInstitucija());
            pstmt.setString(5, istrazivac.getAdresa());
            pstmt.setDouble(6, istrazivac.getBroj());
            pstmt.setString(7, istrazivac.getEmail());
            pstmt.executeUpdate();

            System.out.println("Spremljeno je!");
        }catch (SQLException| IOException ex){
            String msg = "Doslo je do greske u radu s bazom podataka tijekom spremanja novog istraizvaca!\n";
            System.out.println(msg);
        }
    }

    public static void ukloniIstrazivaca(Integer id) throws Exception{
        Connection cnt = spajanjeNaBazu();

        PreparedStatement stmt = cnt.prepareStatement("DELETE FROM ISTRAZIVAC WHERE ID = ?");
        stmt.setInt(1, id);

        stmt.executeUpdate();

        System.out.println("Uspjesno je uklonjen istrazivac pod rednim brojem " + id);
    }

    public static void azurirajIstrazivaca(IstrazivacUnos istrazivac) throws Exception {
        Connection cnt = spajanjeNaBazu();

        PreparedStatement pstmt = cnt.prepareStatement("UPDATE ISTRAZIVAC SET " +
                "IME = ?, " +
                "PREZIME = ?," +
                "DATUM_RODJENJA = ?," +
                "INSTITUCIJA = ?," +
                "ADRESA = ?," +
                "TELEFON = ?," +
                "EMAIL = ? "
                + "WHERE ID = ?");

        pstmt.setString(1, istrazivac.getIme());
        pstmt.setString(2, istrazivac.getPrezime());
        pstmt.setDate(3, Date.valueOf(istrazivac.getDatum()));
        pstmt.setString(4, istrazivac.getInstitucija());
        pstmt.setString(5, istrazivac.getAdresa());
        pstmt.setInt(6, istrazivac.getBroj());
        pstmt.setString(7, istrazivac.getEmail());
        pstmt.setInt(8, istrazivac.getId());

        pstmt.executeUpdate();
    }

    public static List<Lokalitet> dohvatiSveLokacije(){
        List<Lokalitet> lokacijeLista = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rst = null;
        try{
            con = spajanjeNaBazu();

            if (con != null){
                System.out.println("Uspjesno smo se spojili na bazu podataka!\n");
            }

            stmt = con.createStatement();

            rst = stmt.executeQuery("SELECT * FROM LOKACIJA;");

            while (rst.next()){
                Integer id = rst.getInt("id");
                String naziv = rst.getString("naziv");
                String tip = rst.getString("tip");
                String x_coord = rst.getString("x_coord");
                String y_coord = rst.getString("y_coord");

                Lokalitet newLokalitet = new Lokalitet(id, naziv, tip, x_coord, y_coord);

                lokacijeLista.add(newLokalitet);
            }
        } catch (Exception ex){
            System.out.println("Doslo je do greske tijekom spajanja na bazu podataka!\n");
            ex.printStackTrace();
        } finally {
            try{
                rst.close();
                stmt.close();
                con.close();
            } catch (SQLException sqlex){
                sqlex.printStackTrace();
            }
        }
        return lokacijeLista;
    }

    public static void spremiNovuLokaciju(Lokalitet novaLokacija) throws Exception{
        try(Connection con = spajanjeNaBazu()){
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO LOKACIJA(naziv, tip, x_coord, y_coord) VALUES(?, ?, ?, ?);");

            pstmt.setString(1, novaLokacija.getNazivLokacije());
            pstmt.setString(2, novaLokacija.getTypeLocation());
            pstmt.setString(3, novaLokacija.getxCoord());
            pstmt.setString(4, novaLokacija.getyCoord());
            pstmt.executeUpdate();

            System.out.println("Spremljena je nova lokacija u bazi podataka!");
        }catch (SQLException| IOException ex){
            String msg = "Doslo je do greske u radu s bazom podataka tijekom spremanja nove lokacije!\n";
            System.out.println(msg);
        }
    }

    public static void azurirajLLokalitet(Lokalitet updateLokalitet) throws Exception {
        Connection cnt = spajanjeNaBazu();

        PreparedStatement pstmt = cnt.prepareStatement("UPDATE LOKACIJA SET " +
                "NAZIV = ?, " +
                "TIP = ?, " +
                "X_COORD = ?, " +
                "Y_COORD = ? " +
                 "WHERE ID = ?");

        pstmt.setString(1, updateLokalitet.getNazivLokacije());
        pstmt.setString(2, updateLokalitet.getTypeLocation());
        pstmt.setDouble(3, Double.parseDouble(updateLokalitet.getxCoord()));
        pstmt.setDouble(4, Double.parseDouble(updateLokalitet.getyCoord()));
        pstmt.setInt(5,updateLokalitet.getId());

        pstmt.executeUpdate();
    }

    public static void ukloniLokaciju(Integer id) throws Exception{
        Connection cnt = spajanjeNaBazu();

        PreparedStatement stmt = cnt.prepareStatement("DELETE FROM LOKACIJA WHERE ID = ?");
        stmt.setInt(1, id);

        stmt.executeUpdate();

        System.out.println("Uspjesno je uklonjena lokacija pod rednim brojem: " + id);
    }

    public static List<BirdUnos> dohvatiSvePodatke(){
        List<BirdUnos> podatakLista = new ArrayList<>();
        Connection con = null;
        Statement stmt = null;
        ResultSet rst = null;
        try{
            con = spajanjeNaBazu();

            if (con != null){
                System.out.println("Uspjesno smo se spojili na bazu podataka!\n");
            }

            stmt = con.createStatement();

            rst = stmt.executeQuery("SELECT * FROM PODATAK;");

            while (rst.next()){
                Integer id = rst.getInt("id");
                String vrsta = rst.getString("vrsta");
                Integer brojnost = rst.getInt("brojnost");
                String spol = rst.getString("spol");
                String komentari = rst.getString("komentari");
                String istrazivac = rst.getString("istrazivac");
                String lokacija = rst.getString("lokacija");
                LocalDate datum = rst.getDate("datum").toLocalDate();

                BirdUnos newUnos = new BirdUnos(id, vrsta, brojnost, spol, komentari, istrazivac, lokacija, datum);

                podatakLista.add(newUnos);
            }
        } catch (Exception ex){
            System.out.println("Doslo je do greske tijekom spajanja na bazu podataka!\n");
            ex.printStackTrace();
        } finally {
            try{
                rst.close();
                stmt.close();
                con.close();
            } catch (SQLException sqlex){
                sqlex.printStackTrace();
            }
        }
        return podatakLista;
    }

    public static void azurirajPodatak(BirdUnos updatePodatak) throws Exception {
        Connection cnt = spajanjeNaBazu();

        PreparedStatement pstmt = cnt.prepareStatement("UPDATE PODATAK SET " +
                "VRSTA = ?, " +
                "BROJNOST = ?, " +
                "SPOL = ?, " +
                "ISTRAZIVAC = ?, " +
                "LOKACIJA = ?, " +
                "DATUM = ? " +
                "WHERE ID = ?");

        pstmt.setString(1, updatePodatak.getNaziv());
        pstmt.setInt(2, updatePodatak.getBrojnost());
        pstmt.setString(3, updatePodatak.getSpol());
        pstmt.setString(4, updatePodatak.getIstrazivac());
        pstmt.setString(5, updatePodatak.getLokacija());
        pstmt.setDate(6, Date.valueOf(updatePodatak.getDatum()));
        pstmt.setInt(7,updatePodatak.getId());

        pstmt.executeUpdate();
    }

    public static void spremiNoviPodatak(BirdUnos noviPodatak) throws Exception{
        try(Connection con = spajanjeNaBazu()){
            PreparedStatement pstmt = con.prepareStatement("INSERT INTO PODATAK(vrsta, brojnost, spol, komentari, istrazivac, lokacija, datum) VALUES(?, ?, ?, ?, ?, ?, ?);");

            pstmt.setString(1, noviPodatak.getNaziv());
            pstmt.setInt(2, noviPodatak.getBrojnost());
            pstmt.setString(3, noviPodatak.getSpol());
            pstmt.setString(4, noviPodatak.getKomentari());
            pstmt.setString(5, noviPodatak.getIstrazivac());
            pstmt.setString(6, noviPodatak.getLokacija());
            pstmt.setDate(7, Date.valueOf(noviPodatak.getDatum()));
            pstmt.executeUpdate();

            System.out.println("Uspjesno je spremljen novi podatak!");
        }catch (SQLException| IOException ex){
            String msg = "Doslo je do greske u radu s bazom podataka tijekom spremanja novog istraizvaca!\n";
            System.out.println(msg);
        }
    }

    public static void ukloniPodatak(Integer id) throws Exception{
        Connection cnt = spajanjeNaBazu();

        PreparedStatement stmt = cnt.prepareStatement("DELETE FROM PODATAK WHERE ID = ?");
        stmt.setInt(1, id);

        stmt.executeUpdate();

        System.out.println("Uspjesno je uklonjen podatak pod rednim brojem: " + id);
    }

}
