package be.kuleuven.student;

import be.kuleuven.Main;
import be.kuleuven.student.Student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class StudentRepository {
    private final Connection connection;

    public StudentRepository(Connection connection) {
        this.connection = connection;
    }

    public ResultSet readFromDb(String query){
        try {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(query);
//            statement.close();
            return result;
        } catch (SQLException ex) {
            System.out.println(Main.REDBG+"Exception running SQL commands: "+ex.toString()+Main.RESET);
            ex.printStackTrace();
            throw new RuntimeException("Exception running SQL commands: "+ex.toString());
        }
    }

    public void updateDb(String updateStr){
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(updateStr);
            statement.close();
        } catch (SQLException ex) {
            System.out.println(Main.REDBG+"Exception running SQL commands: "+ex.toString()+Main.RESET);
            ex.printStackTrace();
        }
    }

    public List<Student> getStudentsByName(String name){
        //TODO OPDRACHT 1: implementeer methode
        throw new UnsupportedOperationException();
    };

    public void saveNewStudent(Student student){
        //TODO OPDRACHT 3: implementeer methode
        throw new UnsupportedOperationException();
    }

    public void updateStudent(Student student){
        //TODO OPDRACHT 4: implementeer methode
        throw new UnsupportedOperationException();
    }
}
