package com.mia.apa;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class IDValidationController {

    public TextField id;
    public Button validate;
    public HBox hbox;
    public static String idText;

    public void validateID() throws Exception {
        String userId = id.getText().trim();
        if (userId.isEmpty()){
            AlertMessage.showErrorAlert("Please enter your ID number!");

        }else if (InputValidation.validateID(userId)) {
            AlertMessage.showErrorAlert("Please input a correct id number!");

        }else {
            if (Database.validateLogInDetails(userId)){
                idText = userId;
                CreateScene.changeSceneModal("reset-password.fxml", "Reset Password", false);
                hbox.getScene().getWindow().hide();

            }else {
                id.clear();
                AlertMessage.showErrorAlert("Invalid id number!");
            }
        }
    }

    public static String passID(){
        return idText;
    }
}
