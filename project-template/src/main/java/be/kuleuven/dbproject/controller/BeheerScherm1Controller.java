package be.kuleuven.dbproject.controller;

import be.kuleuven.dbproject.ProjectMain;
import be.kuleuven.dbproject.model.Player;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class BeheerScherm1Controller {
    @FXML
    private TableView tblTips;

    private ArrayList<Player> players;

    public void initialize() {
        // TODO: get the correct data from database

        Player testPlayer = new Player("Arne Duyver", 101, 100001);
        this.players = new ArrayList<>();
        this.players.add(testPlayer);

        initTable();
        tblTips.setOnMouseClicked(e -> playerDoubleClicked(e));
    }

    private void initTable() {
        tblTips.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        tblTips.getColumns().clear();

        // TODO zijn dit de juiste kolommen?
        int colIndex = 0;
        for(var colName : new String[]{"Player ID", "Player name"}) {
            TableColumn<ObservableList<String>, String> col = new TableColumn<>(colName);
            final int finalColIndex = colIndex;
            col.setCellValueFactory(f -> new ReadOnlyObjectWrapper<>(f.getValue().get(finalColIndex)));
            tblTips.getColumns().add(col);
            colIndex++;
        }

        tblTips.getItems().add(FXCollections.observableArrayList(players.get(0).getId(), players.get(0).getName()));
    }

    private void playerDoubleClicked(MouseEvent e){
        if(e.getClickCount() == 2 && tblTips.getSelectionModel().getSelectedItem() != null) {
            var selectedRow = tblTips.getSelectionModel().getSelectedIndex();
            Player wantedPlayer = players.get(selectedRow);
            try {
                var stage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(BeheerScherm1Controller.class.getResoure("beheerscherm1.fxml"));
                var root = (AnchorPane) loader.load();
                // Pass object to other controller
                BeheerSchermDetailedController bsdc = loader.getController(); // This did the "trick"
                bsdc.setPlayer(wantedPlayer);

                var scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Administratie hoofdscherm TODO pas mij aan");
                stage.initOwner(ProjectMain.getRootStage());
                stage.initModality(Modality.WINDOW_MODAL);
                stage.show();

            } catch (Exception ex) {
                throw new RuntimeException("Kan main scherm niet vinden", ex);
            }


        }
    }
}
