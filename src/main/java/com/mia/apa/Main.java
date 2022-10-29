package com.mia.apa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("splash-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image(String.valueOf(getClass().getClassLoader().getResource("icons8-gear-100.png"))));
        stage.setScene(scene);
        stage.show();
    }
}