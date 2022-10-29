package com.mia.apa;

import com.mia.apa.AlertMessage;
import com.mia.apa.Database;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;

import java.net.URL;
import java.util.ResourceBundle;

public class ResetPasswordController implements Initializable{
    public PasswordField password, confirmPassword;
    public Button reset;
    String idText;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idText = IDValidationController.passID();
        System.out.println(idText);
    }

    public void resetPassword() throws Exception {
        String passWord = password.getText().trim();
        String confirm_password = confirmPassword.getText().trim();

        if (passWord.isEmpty() && confirm_password.isEmpty()){
            AlertMessage.showErrorAlert("All fields must be filled!");

        }else if (passWord.isEmpty()) {
            AlertMessage.showErrorAlert("Please provide your new password!");

        }else if (confirm_password.isEmpty()) {
            AlertMessage.showErrorAlert("Please confirm your new password!");

        }else if (!confirm_password.equals(passWord)) {
            confirmPassword.clear();
            AlertMessage.showErrorAlert("Passwords mismatch!");

        }else {
            int affectedRows = Database.updatePassword(passWord, idText);
            password.clear();
            confirmPassword.clear();
            if (affectedRows > 0){
                AlertMessage.showSuccessAlert("Password reset successfully!");
            }else {
                AlertMessage.showSuccessAlert("An error has occurred! Please try again later!");
            }
        }
    }
}
