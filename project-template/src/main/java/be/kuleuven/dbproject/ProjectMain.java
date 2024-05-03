package be.kuleuven.dbproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ProjectMain extends Application {
    private static Stage rootStage;

    public static Stage getRootStage() {
        return rootStage;
    }
    @Override
    public void start(Stage stage) throws IOException {
        rootStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("main.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 480, 360);
        stage.setTitle("Administratie hoofdscherm TODO pas mij aan");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}