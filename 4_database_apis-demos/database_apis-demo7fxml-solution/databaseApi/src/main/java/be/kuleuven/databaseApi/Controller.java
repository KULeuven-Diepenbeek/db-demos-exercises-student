package be.kuleuven.databaseApi;

import java.net.URL;
import java.util.ResourceBundle;

import be.kuleuven.databaseApi.model.Model;
import be.kuleuven.databaseApi.view.DatabaseApiView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane speelbord;

    private Model model;
    private DatabaseApiView view;
    @FXML
    void initialize() {
        model = new Model();
        view = new DatabaseApiView(model);
        speelbord.getChildren().add(view);
        view.setOnMouseClicked(this::onViewClicked);
        view.setOnDragDetected(MouseEvent -> clicked());
    }

    private void clicked() {

    }

    public void update(){
        view.update();
    }

    public void onViewClicked(MouseEvent me){

    }

}
