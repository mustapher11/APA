package com.mia.apa;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LogInController implements Initializable {

    public TextField id;
    public PasswordField password;

    public VBox vbox;
    public MenuItem resetPassword;
    static String idText;
    Parent root;
    Stage stage;
    Scene scene;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void logIn() throws Exception {
        String idNumber = id.getText().trim();
        String passwordText = password.getText().trim();

        if (idNumber.isEmpty() && passwordText.isEmpty()){
            AlertMessage.showErrorAlert("Please insert your valid id and password!");
        }else if (idNumber.isEmpty()){
           AlertMessage.showErrorAlert("Please insert your valid id number!");

        }else if (InputValidation.validateID(idNumber)) {
            id.clear();
            AlertMessage.showErrorAlert("Wrong input! Please enter a number in the id field.");

        }else if (passwordText.isEmpty()) {
           AlertMessage.showErrorAlert("Please insert your password!");

        }else {
            if (Database.validateLogInDetails(idNumber, passwordText)){
                idText = idNumber;
                logInButton();
                vbox.getScene().getWindow().hide();
            }else {
                id.clear();
                password.clear();
                AlertMessage.showErrorAlert("Invalid log in credentials!");
            }
        }
    }

    public static String getIdText(){
        return idText;
    }
    public void moveToRegister(){
        CreateScene.changeSceneModal("register.fxml", "Register",false);
        vbox.getScene().getWindow().hide();
    }

    public void passwordReset(){
        CreateScene.changeSceneModal("id-validation.fxml", "Password Reset", false);
    }

    public void logInButton(){
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("dashboard.fxml")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = new Stage();
        scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.getIcons().add(new Image(String.valueOf(getClass().getClassLoader().getResource("icons8-gear-100.png"))));
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.setOnCloseRequest(e -> {
            e.consume();
            if (AlertMessage.exitSection("Do you want to exit the system?")){
                CreateScene.changeSceneModal("login.fxml", "Log In", false);
                stage.close();
            }
        });
        stage.setResizable(false);
        stage.show();
    }
}