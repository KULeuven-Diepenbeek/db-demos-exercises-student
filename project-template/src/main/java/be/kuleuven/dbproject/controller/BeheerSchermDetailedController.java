package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.ProjectMain;
import be.kuleuven.dbproject.model.Player;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class BeheerSchermDetailedController {

    @FXML
    private Button homeBtn;

    @FXML
    private Label playerIdLbl,playerNameLbl,clubIdLbl;

    private Player player;

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void initialize() {
        playerIdLbl.setText(String.valueOf(player.getId()));
        playerNameLbl.setText(player.getName());
        clubIdLbl.setText(String.valueOf(player.getClubId()));

        homeBtn.setOnMouseClicked(e -> {
            try {
                var stage = new Stage();
                var root = (AnchorPane) FXMLLoader.load(ProjectMain.class.getResource("main.xml"));

                var scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Administratie hoofdscherm TODO pas mij aan");
                stage.initOwner(ProjectMain.getRootStage());
                stage.initModality(Modality.WINDOW_MODAL);
                stage.show();

            } catch (Exception ex) {
                throw new RuntimeException("Kan main scherm niet vinden", ex);
            }
        });
    }
}
