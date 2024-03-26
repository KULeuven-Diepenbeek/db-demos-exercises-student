package be.kuleuven.student;

import be.kuleuven.Main;

import java.sql.*;
import java.util.ArrayList;
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
        //SOLVED OPDRACHT 1: implementeer methode
        ArrayList<Student> resultList = new ArrayList<Student>();
        try {
            Statement s = connection.createStatement();
            String stmt = "SELECT * FROM student WHERE naam = '" + name + "'";
            //System.out.println(stmt);
            ResultSet result = s.executeQuery(stmt);

            while(result.next()) {
                int studnr = result.getInt("studnr");
                String naam  = result.getString("naam");
                String voornaam = result.getString("voornaam");
                boolean goedbezig = result.getBoolean("goedbezig");

                resultList.add(new Student(studnr, naam, voornaam, goedbezig));
            }
            s.close();

        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }

        return resultList;
    };

    public void saveNewStudent(Student student){
//        SOLVED OPDRACHT 3: implementeer methode
//        // WITHOUT PREPARED STATEMENT
//        try {
//            Statement s = connection.createStatement();
//            String stmt = "INSERT INTO student(naam, voornaam, studnr, goedbezig) VALUES ('" + student.getNaam() + "', '" + student.getVoornaam() + "', " + student.getStudnr() + ", " +
//                    student.getGoedBezig() + ")";
//            //System.out.println(stmt);
//            s.executeUpdate(stmt);
//            s.close();
//        } catch(SQLException ex) {
//            throw new RuntimeException(ex);
//        }
        // WITH PREPARED STATEMENT
        try {
            String stmt = "INSERT INTO student(naam, voornaam, studnr, goedbezig) VALUES (?, ?, ?, ?)";
            //System.out.println(stmt);
            PreparedStatement prepared = connection.prepareStatement(stmt);
            prepared.setString(1, student.getNaam());
            prepared.setString(2, student.getVoornaam());
            prepared.setInt(3, student.getStudnr());
            prepared.setBoolean(4, student.getGoedBezig());
            prepared.execute();

            prepared.close();
        } catch(SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void updateStudent(Student student){
        //SOLVED OPDRACHT 4: implementeer methode
        String updateString = String.format("UPDATE student SET voornaam = '%s', naam = '%s', goedBezig = %d WHERE studnr = %d;",student.getVoornaam(), student.getNaam(),student.getGoedBezig() ? 1 : 0,student.getStudnr());
        try {
            int aantalStudenten = getStudentsByStudnr(student.getStudnr()).size();
            if (aantalStudenten == 1){
                Statement statement = connection.createStatement();
                statement.executeUpdate(updateString);
                statement.close();
            }else{
                throw new InvalidStudentException(student);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Exception running SQL commands: "+updateString);
        }
    }

    public List<Student> getStudentsByStudnr(int nr){
        ArrayList<Student> resultList = new ArrayList<Student>();
        try {
            Statement s = connection.createStatement();
            String stmt = "SELECT * FROM student WHERE studnr = '" + nr + "'";
            //System.out.println(stmt);
            ResultSet result = s.executeQuery(stmt);

            while(result.next()) {
                int studnr = result.getInt("studnr");
                String naam  = result.getString("naam");
                String voornaam = result.getString("voornaam");
                boolean goedbezig = result.getBoolean("goedbezig");

                resultList.add(new Student(studnr, naam, voornaam, goedbezig));
            }
            s.close();

        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }

        return resultList;
    };
}
