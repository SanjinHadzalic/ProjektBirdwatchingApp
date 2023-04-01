package com.example.projektbirdwatchingapp;

import hr.java.vjezbe.entiteti.AppUser;
import hr.java.vjezbe.entiteti.HashingPassword;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class LoginController {
    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button cancelButton;

    @FXML
    private Button loginButton;

    private static Set<AppUser> appUserSet;
    public static String odabraniUser;
    public static Set<AppUser> getAppUserSet(){return appUserSet;}

    @FXML
    public void initialize() throws FileNotFoundException{
        appUserSet = new HashSet<>();

        File appUsersFile = new File("src/main/java/hr/java/vjezbe/util/appUsers");
        Scanner appUserScanner = new Scanner(appUsersFile);

        while(appUserScanner.hasNextLine()){
            String username = appUserScanner.nextLine();
            String password = appUserScanner.nextLine();
            String role = appUserScanner.nextLine();

            AppUser newAppUser = new AppUser(username, password, role);

            getAppUserSet().add(newAppUser);
        }
    }

    public void loginButtonOnAction(ActionEvent e) throws IOException{
        if (usernameTextField.getText().isBlank() == false && passwordTextField.getText().isBlank() == false){
            String userTest = usernameTextField.getText();
            String passTest = HashingPassword.doHashing(passwordTextField.getText());

            for (AppUser a:appUserSet
            ) {
                String itUser = a.getUsername();
                String itPass = a.getPassword();
                String itRole = a.getRole();

                if (userTest.equals(itUser) &&  Objects.equals(itRole, "A")){
                    if(passTest.equals(itPass)){
                        MainMenuController.showMainMenuScreen();
                        odabraniUser=itUser.toUpperCase();
                        System.out.println(odabraniUser);
                    }
                    else if (!passTest.equals(itPass)){
                        Alert losaLozinka = new Alert(Alert.AlertType.ERROR);
                        losaLozinka.setTitle("Greska tijekom prijave admina");
                        losaLozinka.setContentText("Unesena je pogresna lozinka >>" + passwordTextField.getText() + "<<");
                        losaLozinka.showAndWait();

                        passwordTextField.clear();

                        System.out.println("Sifra admina nije dobra!!!");
                    }
                } else if (userTest.equals(itUser)  && Objects.equals(itRole, "U")) {
                    if (passTest.equals(itPass)){
                        MainMenuController.showMainMenuScreenUser();
                        odabraniUser=itUser.toUpperCase();
                        System.out.println(odabraniUser);
                    } else if (!passTest.equals(itPass)) {
                        Alert losaLozinka = new Alert(Alert.AlertType.ERROR);
                        losaLozinka.setTitle("Greska tijekom prijave korisnika");
                        losaLozinka.setContentText("Unesena je pogresna lozinka >>" + passwordTextField.getText() + "<<");
                        losaLozinka.showAndWait();

                        passwordTextField.clear();
                        System.out.println("Sifra korisnika: " + usernameTextField.getText() + " je pogresna!");
                    }
                    System.out.println("ucitan je korisnik...");
                }
            }
        } else {
            StringBuilder errors = new StringBuilder();
            if (usernameTextField.getText().isBlank()){
                errors.append("Nedostaje unos za username!\n");
            }
            if (passwordTextField.getText().isBlank()){
                errors.append("Nedostaje unos za lozinku korisnika!\n");
            }
            if(errors.length()>0){
                Alert faliPoruka = new Alert(Alert.AlertType.ERROR);
                faliPoruka.setTitle("Greska tijekom prijave");
                faliPoruka.setHeaderText("Greska tijekom prijave korisnika u aplikaciju:");
                faliPoruka.setContentText(errors.toString());

                faliPoruka.showAndWait();
            }
        }
    }
    public void cancelButtonOnAction(ActionEvent e){
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onEnter(ActionEvent e) throws IOException {
        if (usernameTextField.getText().isBlank() == false && passwordTextField.getText().isBlank() == false){
            String userTest = usernameTextField.getText();
            String passTest = HashingPassword.doHashing(passwordTextField.getText());

            for (AppUser a:appUserSet
            ) {
                String itUser = a.getUsername();
                String itPass = a.getPassword();
                String itRole = a.getRole();

                if (userTest.equals(itUser) && Objects.equals(itRole, "A")){
                    if (passTest.equals(itPass)){
                        MainMenuController.showMainMenuScreen();
                        odabraniUser=itUser.toUpperCase();
                        System.out.println(odabraniUser);
                    } else if (!passTest.equals(itPass)) {
                        Alert losaLozinka = new Alert(Alert.AlertType.ERROR);
                        losaLozinka.setTitle("Greska tijekom prijave administratora");
                        losaLozinka.setContentText("Unesena je pogresna lozinka >>" + passwordTextField.getText() + "<<");
                        losaLozinka.showAndWait();

                        passwordTextField.clear();
                        System.out.println("Sifra administratora: " + usernameTextField.getText() + " je pogresna!");
                    }
                } else if (userTest.equals(itUser) && Objects.equals(itRole, "U")) {
                    if (passTest.equals(itPass)){
                        MainMenuController.showMainMenuScreenUser();
                        odabraniUser=itUser;
                        System.out.println(odabraniUser);
                    } else if (!passTest.equals(itPass)) {
                        Alert losaLozinka = new Alert(Alert.AlertType.ERROR);
                        losaLozinka.setTitle("Greska tijekom prijave korisnika " + usernameTextField.getText());
                        losaLozinka.setContentText("Unesena je pogresna lozinka >>" + passwordTextField.getText() + "<<");
                        losaLozinka.showAndWait();

                        passwordTextField.clear();
                        System.out.println("Sifra korisnika: " + usernameTextField.getText() + " je pogresna!");

                    }
                    System.out.println("ucitan je korisnik...");
                }
            }
        } else {
            StringBuilder errors = new StringBuilder();
            if (usernameTextField.getText().isBlank()){
                errors.append("Nedostaje unos za username!\n");
            }
            if (passwordTextField.getText().isBlank()){
                errors.append("Nedostaje unos za lozinku korisnika!\n");
            }
            if(errors.length()>0){
                Alert faliPoruka = new Alert(Alert.AlertType.ERROR);
                faliPoruka.setTitle("Greska tijekom prijave");
                faliPoruka.setHeaderText("Greska tijekom prijave korisnika u aplikaciju:");
                faliPoruka.setContentText(errors.toString());

                faliPoruka.showAndWait();
            }
        }
    }


}