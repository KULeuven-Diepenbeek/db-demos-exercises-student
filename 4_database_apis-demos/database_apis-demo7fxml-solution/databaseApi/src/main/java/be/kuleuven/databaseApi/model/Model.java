package be.kuleuven.databaseApi.model;

import be.kuleuven.databaseApi.model.cursus.Cursus;
import be.kuleuven.databaseApi.model.student.Student;
import be.kuleuven.databaseApi.model.student.StudentRepository;
import be.kuleuven.databaseApi.model.student.StudentRepositoryJDBI;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Model {
    private ArrayList<Student> studenten;
    private ArrayList<Cursus> cursussen;

    private ConnectionManager connectionManager;
    public Model() {
//        connectionManager = new ConnectionManager("jdbc:sqlite:mydb.sql");
//        connectionManager.flushConnection();
//        Connection connection = connectionManager.getConnection();
//        StudentRepository studentRepository = new StudentRepositoryJDBI(connectionManager.getConnectionString());
    }

    public static void main(String[] args) {

    }
}
