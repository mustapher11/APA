package com.mia.apa;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {
    public Button save, delete, refresh;

    public static VBox vbox;
    ImageView saveImage, deleteImage, refreshImage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createImageButtons();
    }

    public void createImageButtons(){
        saveImage = new ImageView("icons8-save-96.png");
        saveImage.setFitHeight(30.0);
        saveImage.setPreserveRatio(true);

        deleteImage = new ImageView("icons8-delete-96.png");
        deleteImage.setFitHeight(30.0);
        deleteImage.setPreserveRatio(true);

        refreshImage = new ImageView("icons8-refresh-96.png");
        refreshImage.setFitHeight(30.0);
        refreshImage.setPreserveRatio(true);

        save.setGraphic(saveImage);
        delete.setGraphic(deleteImage);
        refresh.setGraphic(refreshImage);
    }
}
