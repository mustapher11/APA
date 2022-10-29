package com.mia.apa;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class RegisterController {
    public BorderPane borderPane;
    public TextField first_name, last_name, id, phone;
    public PasswordField password, confirm_password;
    static int affectedRows = 0;

    public void register() throws Exception {
        String firstName = first_name.getText().trim();
        String lastName = last_name.getText().trim();
        String idNumber = id.getText().trim();
        String phoneNumber = phone.getText().trim();
        String passwordText = password.getText().trim();
        String confirmPassword = confirm_password.getText().trim();

        if (firstName.isEmpty() && lastName.isEmpty() && idNumber.isEmpty() && phoneNumber.isEmpty()
        && passwordText.isEmpty() && confirmPassword.isEmpty()){
            AlertMessage.showErrorAlert("All fields must be field!");

        }else if (firstName.isEmpty()) {
            AlertMessage.showErrorAlert("Please insert a valid first name!");

        }else if (lastName.isEmpty()) {
            AlertMessage.showErrorAlert("Please insert a valid last name!");

        }else if (InputValidation.validateTextInput(firstName)) {
            AlertMessage.showErrorAlert("Invalid input! First Name should be text only of length 3-8 letters.");

        }else if (InputValidation.validateTextInput(lastName)) {
            AlertMessage.showErrorAlert("Invalid input! Last Name should be text only of length 3-8 letters.");

        }else if (InputValidation.validateID(idNumber)) {
            id.clear();
            AlertMessage.showErrorAlert("Invalid input! Please insert numbers only of length 6-8 digits.");

        }else if (InputValidation.validatePhone(phoneNumber)) {
            phone.clear();
            AlertMessage.showErrorAlert("Invalid input! Please insert numbers only of length 10-13 digits.");

        }else if (Database.validateLogInDetails(idNumber)) {
            id.clear();
            AlertMessage.showErrorAlert("Please use a different id number!");

        }else if (passwordText.isEmpty()) {
            AlertMessage.showErrorAlert("Please insert your password!");

        }else if (confirmPassword.isEmpty()){
            AlertMessage.showErrorAlert("Please confirm your password!");

        }else if (!confirmPassword.equals(passwordText)){
            password.clear();
            confirm_password.clear();
            AlertMessage.showErrorAlert("Passwords mismatch!");

        }else {
            affectedRows = Database.registerCashier(firstName, lastName, idNumber, phoneNumber, passwordText);
            if (affectedRows > 0) {
                System.out.println(affectedRows);
                moveToLogIn();
            }else {
                id.clear();
                phone.clear();
                password.clear();
                confirm_password.clear();
                AlertMessage.showErrorAlert("An error has occurred! Please try again!");
            }
        }
    }

    public void moveToLogIn(){
        CreateScene.changeSceneModal("login.fxml", "Log In",false);
        borderPane.getScene().getWindow().hide();
    }
}
