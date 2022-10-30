package com.mia.apa;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SupplierController implements Initializable {
    public VBox vbox;
    
    public Button save, update, clear, delete, exit, refresh, supplierList;
    ImageView saveImage, clearImage, exitImage, updateImage, deleteImage, refreshImage, supplierListImage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createImageButtons();
    }

    public void createImageButtons(){
        saveImage = new ImageView("icons8-save-96.png");
        saveImage.setFitHeight(30.0);
        saveImage.setPreserveRatio(true);

        clearImage = new ImageView("icons8-clear-symbol-96.png");
        clearImage.setFitHeight(30.0);
        clearImage.setPreserveRatio(true);

        exitImage = new ImageView("icons8-logout-96.png");
        exitImage.setFitHeight(30.0);
        exitImage.setPreserveRatio(true);

        updateImage = new ImageView("icons8-update-96.png");
        updateImage.setFitHeight(30.0);
        updateImage.setPreserveRatio(true);

        deleteImage = new ImageView("icons8-delete-96.png");
        deleteImage.setFitHeight(30.0);
        deleteImage.setPreserveRatio(true);

        refreshImage = new ImageView("icons8-refresh-96.png");
        refreshImage.setFitHeight(30.0);
        refreshImage.setPreserveRatio(true);

        supplierListImage = new ImageView("icons8-supplier-96.png");
        supplierListImage.setFitHeight(30.0);
        supplierListImage.setPreserveRatio(true);

        save.setGraphic(saveImage);
        clear.setGraphic(clearImage);
        exit.setGraphic(exitImage);
        update.setGraphic(updateImage);
        delete.setGraphic(deleteImage);
        refresh.setGraphic(refreshImage);
        supplierList.setGraphic(supplierListImage);
    }

    public void exitSupplierSection() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        loader.load();
        LogInController controller = loader.getController();
        controller.logInButton();
        vbox.getScene().getWindow().hide();
    }
}
