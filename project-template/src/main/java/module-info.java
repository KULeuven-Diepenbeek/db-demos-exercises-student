module be.kuleuven.dbproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens be.kuleuven.dbproject.controller to javafx.fxml;
    exports be.kuleuven.dbproject.controller;
    exports be.kuleuven.dbproject;
    opens be.kuleuven.dbproject to javafx.fxml;
}