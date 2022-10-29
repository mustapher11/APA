package com.mia.apa;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class SplashController implements Initializable {
    public StackPane stackPane;
    Stage stage;
    Scene scene;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startSplashScreen();
    }

    public void startSplashScreen() {
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            Platform.runLater(() -> {
                changeSceneModal("login.fxml", "Log In", false);
                stackPane.getScene().getWindow().hide();
            });
        });

        thread.start();
    }

    public void changeSceneModal(String resource, String title, boolean resize) {
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(resource)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = new Stage();
        scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.getIcons().add(new Image(String.valueOf(getClass().getClassLoader().getResource("icons8-gear-100.png"))));
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(resize);
        stage.show();
    }
}