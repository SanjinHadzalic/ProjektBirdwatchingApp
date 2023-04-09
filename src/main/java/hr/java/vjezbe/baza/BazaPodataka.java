package hr.java.vjezbe.baza;

import hr.java.vjezbe.entiteti.BirdUnos;
import hr.java.vjezbe.entiteti.IstrazivacUnos;
import hr.java.vjezbe.entiteti.Lokalitet;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.sql.*;
import org.slf4j.Logger;

public final class BazaPodataka {
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

    public static <T> List<T> getAllData(ResultConverter<T> converter, String query) throws Exception {
        List<T> listaData = new ArrayList<>();
        Connection cntn = null;
        Statement stmt = null;
        ResultSet rstt = null;
        cntn = spajanjeNaBazu();
        try{
            stmt = cntn.createStatement();
            rstt = stmt.executeQuery(query);
            while(rstt.next()){
                T t = converter.converter(rstt);
                listaData.add(t);
            }
        }  catch (Exception ex){
            logger.error("Doslo je do greske tijekom spajanja na bazu podataka!");
            ex.printStackTrace();
        } finally {
            try{
                rstt.close();
                stmt.close();
                cntn.close();
            } catch (SQLException sqlex){
                sqlex.printStackTrace();
            }
        }
        return listaData;
    }
    public static  <T> void removeData(Integer id, String query) {
        try {
            Connection cnnt = spajanjeNaBazu();

            PreparedStatement pstmt = cnnt.prepareStatement(query);
            pstmt.setInt(1, id);

            pstmt.executeUpdate();
        } catch (Exception e) {
            logger.error("Greska tijekom uklanjanja retka iz baze podataka!");
            throw new RuntimeException(e);
        }
    }

    public static List<IstrazivacUnos> dohvatiSveIstrazivacee() {
        try {
            return getAllData(resultset -> new IstrazivacUnos(resultset.getInt("id"), resultset.getString("ime"),
                                                                    resultset.getString("prezime"), resultset.getDate("datum_rodjenja").toLocalDate(),
                                                                    resultset.getString("institucija"), resultset.getString("adresa"),
                                                                    resultset.getInt("telefon"), resultset.getString("email")), "SELECT * FROM ISTRAZIVAC;");
        } catch (Exception e) {
            logger.error("Problem tijekom dohvacanja svih istrazivaca!");
            throw new RuntimeException(e);
        }
    }
    public static List<Lokalitet> dohvatiSveLokalitete() {
        try {
            return getAllData(resultset -> new Lokalitet(resultset.getInt("id"), resultset.getString("naziv"),
                                                        resultset.getString("tip"), resultset.getString("x_coord"),
                                                        resultset.getString("y_coord")), "SELECT * FROM LOKACIJA;");
        } catch (Exception e) {
            logger.error("Problem tijekom dohvacanja svih lokaliteta!");
            throw new RuntimeException(e);
        }
    }

    public static List<BirdUnos> dohvatiSvePodatkee(){
        try {
            return getAllData(resultset -> new BirdUnos(resultset.getInt("id"), resultset.getString("vrsta"),
                                                        resultset.getInt("brojnost"), resultset.getString("spol"),
                                                        resultset.getString("komentari"), resultset.getString("istrazivac"),
                                                        resultset.getString("lokacija"), resultset.getDate("datum").toLocalDate()), "SELECT * FROM PODATAK;");
        } catch (Exception e) {
            logger.error("Problem tijekom dohvacanja svih podataka istrazivanja!");
            throw new RuntimeException(e);
        }
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
        }catch (SQLException | IOException ex){
            String msg = "Doslo je do greske u radu s bazom podataka tijekom spremanja novog istraizvaca!\n";
            System.out.println(msg);
        }
    }

    public static void ukloniIstrazivaca(Integer id) throws Exception{
        removeData(id, "DELETE FROM ISTRAZIVAC WHERE ID = ?");
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
        removeData(id,"DELETE FROM LOKACIJA WHERE ID = ?");
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
        removeData(id,"DELETE FROM PODATAK WHERE ID = ?");
    }
}
