package be.kuleuven;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static StudentTableApi sta = null;
    private static Connection connection = null;

    public static void main(String[] args) {
        // CONNECT TO LOCAL SQLITE
        ConnectionManager cm = new ConnectionManager("jdbc:sqlite:mydb.sql");
        connection = cm.getConnection();
        sta = new StudentTableApi(connection);

        //READ FROM DB
        for (Student s : getAllstudentsFromDb()){
            System.out.println(s);
        }
        System.out.println("------------UPDATE-------------");
        //UPDATE DB
        sta.updateDb("""
        UPDATE student SET voornaam = 'Jaqueline' WHERE studnr = 123;
        INSERT INTO oeitiskapot;
        """);
        //INSERT INTO oeitiskapot;

        for (Student s : getAllstudentsFromDb()){
            System.out.println(s);
        }
        // Closing all connections
        cm.flushConnection();
    }

    public static final ArrayList<Student> getAllstudentsFromDb(){
        ResultSet result = sta.readFromDb("SELECT * FROM student;");
        ArrayList<Student> students = new ArrayList<Student>();
        try{
            while (result.next()){
                Student student = new Student(result.getInt("studnr"), result.getString("naam"),result.getString("voornaam"), result.getBoolean("goedbezig"));
                students.add(student);
            }
        }catch (SQLException e){
            System.out.println(REDBG+"ERROR reading result-> \n\t"+e.toString()+RESET);
        }
        return students;
    }

    public static final String REDBG = "\033[41m";
    public static final String RESET = "\033[0m";
}