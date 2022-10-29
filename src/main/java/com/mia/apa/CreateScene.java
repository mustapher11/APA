package com.mia.apa;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CreateScene implements Initializable {
    static Scene scene;
    static Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public static void changeSceneModal(String resource, String title, boolean resize){
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(CreateScene.class.getResource(resource)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = new Stage();
        scene = new Scene(root);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.getIcons().add(new Image("icons8-gear-100.png"));
        stage.setTitle(title);
        stage.setScene(scene);
        stage.setResizable(resize);
        stage.show();
    }

//    public static void changeScene(String resource, String title, boolean resize){
//        Parent root;
//        try {
//            root = FXMLLoader.load(Objects.requireNonNull(CreateScene.class.getResource(resource)));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        stage = new Stage();
//        scene = new Scene(root);
//        stage.setTitle(title);
//        stage.setScene(scene);
//        stage.setResizable(resize);
//        stage.setMaximized(true);
//        stage.show();
//    }

//    public static Scene getStage(){
//        return scene;
//    }
}
