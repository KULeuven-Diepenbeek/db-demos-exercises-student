module be.kuleuven.databaseApi {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.sql;
    requires org.jdbi.v3.core;

    opens be.kuleuven.databaseApi to javafx.fxml;
    exports be.kuleuven.databaseApi;
}