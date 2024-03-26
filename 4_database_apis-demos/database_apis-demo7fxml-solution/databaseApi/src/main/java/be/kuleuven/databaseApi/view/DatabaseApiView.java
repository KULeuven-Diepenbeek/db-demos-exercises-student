package be.kuleuven.databaseApi.view;

import be.kuleuven.databaseApi.model.Model;
import be.kuleuven.databaseApi.model.student.Student;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Iterator;

public class DatabaseApiView extends Region {
    private Model model;

    public DatabaseApiView(Model model) {
        this.model = model;
        update();
    }

    public void update(){
        getChildren().clear();
        TableView<Student> tblStudent = new TableView<>();

        tblStudent.getColumns().clear();
        TableColumn<Student, String> col = new TableColumn<>("Naam");
        col.setCellValueFactory(f -> new ReadOnlyObjectWrapper<>(f.getValue().getNaam()));
        tblStudent.getColumns().add(col);

        tblStudent.getItems().add(new Student(124, "Josmans", "Joske", true));
        getChildren().add(tblStudent);
    }


}
