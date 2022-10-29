package com.mia.apa;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ProductsSectionController1 implements Initializable {
    public Button addQueue, clearPayment, generateReceipt, printReceipt;
    public Button clearQueue, editRecord, deleteRecord, refresh, exit;
    public Button view;
    public BorderPane borderPane;
    ImageView add, cancel, cancel1, generate, print, imageEdit, imageDelete, imageRefresh, imageExit, viewRecord;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controls();
        manageOrders();
    }

    public void controls(){
        add = new ImageView("C:\\Users\\user\\Desktop\\JavaFX\\Hotel\\src\\Images\\icons8-add-new-96.png");
        add.setFitHeight(40.0);
        add.setPreserveRatio(true);

        cancel = new ImageView("C:\\Users\\user\\Desktop\\JavaFX\\Hotel\\src\\Images\\icons8-empty-trash-96.png");
        cancel.setFitHeight(40.0);
        cancel.setPreserveRatio(true);

        cancel1 = new ImageView("C:\\Users\\user\\Desktop\\JavaFX\\Hotel\\src\\Images\\icons8-broom-96.png");
        cancel1.setFitHeight(40.0);
        cancel1.setPreserveRatio(true);

        generate = new ImageView("C:\\Users\\user\\Desktop\\JavaFX\\Hotel\\src\\Images\\icons8-generated-photos-96.png");
        generate.setFitHeight(40.0);
        generate.setPreserveRatio(true);

        print = new ImageView("C:\\Users\\user\\Desktop\\JavaFX\\Hotel\\src\\Images\\icons8-print-96.png");
        print.setFitHeight(40.0);
        print.setPreserveRatio(true);

        viewRecord = new ImageView("C:\\Users\\user\\Desktop\\JavaFX\\Hotel\\src\\Images\\icons8-view-96.png");
        viewRecord.setFitHeight(40.0);
        viewRecord.setPreserveRatio(true);

        addQueue.setGraphic(add);
        clearQueue.setGraphic(cancel1);
        clearPayment.setGraphic(cancel);
        generateReceipt.setGraphic(generate);
        printReceipt.setGraphic(print);
        view.setGraphic(viewRecord);
    }

    public void manageOrders(){
        imageEdit = new ImageView("C:\\Users\\user\\Desktop\\JavaFX\\Hotel\\src\\Images\\icons8-pencil-96.png");
        imageEdit.setFitHeight(40.0);
        imageEdit.setPreserveRatio(true);

        imageDelete = new ImageView("C:\\Users\\user\\Desktop\\JavaFX\\Hotel\\src\\Images\\icons8-trash-96.png");
        imageDelete.setFitHeight(40.0);
        imageDelete.setPreserveRatio(true);

        imageRefresh = new ImageView("C:\\Users\\user\\Desktop\\JavaFX\\Hotel\\src\\Images\\icons8-available-updates-96.png");
        imageRefresh.setFitHeight(40.0);
        imageRefresh.setPreserveRatio(true);

        imageExit = new ImageView("C:\\Users\\user\\Desktop\\JavaFX\\Hotel\\src\\Images\\icons8-logout-96.png");
        imageExit.setFitHeight(40.0);
        imageExit.setPreserveRatio(true);

        editRecord.setGraphic(imageEdit);
        deleteRecord.setGraphic(imageDelete);
        refresh.setGraphic(imageRefresh);
        exit.setGraphic(imageExit);
    }

    public void exitButton(){
        CreateScene.changeSceneModal("dashboard.fxml","Dashboard", false);
        borderPane.getScene().getWindow().hide();
    }
}
