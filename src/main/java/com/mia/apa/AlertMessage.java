package com.mia.apa;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertMessage {
    static Alert errorAlert, successAlert, exitAlert;
    static Optional<ButtonType> buttonType;

    public static void showErrorAlert(String message) {
        errorAlert = new Alert(Alert.AlertType.ERROR);
        errorAlert.setHeaderText("ERROR!");
        errorAlert.setContentText(message);
        errorAlert.show();
    }

    public static void showSuccessAlert(String message) {
        successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setHeaderText("SUCCESS");
        successAlert.setContentText(message);
        successAlert.show();
    }

    public static boolean exitSection(String message) {
        exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.setHeaderText("Exit");
        exitAlert.setContentText(message);
        buttonType = exitAlert.showAndWait();
        return buttonType.filter(type -> type == ButtonType.OK).isPresent();

    }

    public static boolean deleteConfirmation(String message) {
        exitAlert = new Alert(Alert.AlertType.CONFIRMATION);
        exitAlert.setHeaderText("Delete Confirmation");
        exitAlert.setContentText(message);
        buttonType = exitAlert.showAndWait();
        return buttonType.filter(type -> type == ButtonType.OK).isPresent();

    }
}