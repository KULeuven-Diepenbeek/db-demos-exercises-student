package be.kuleuven;

import java.sql.*;
import java.util.Scanner;

public class Main {

    private static Connection connection = null;
    private static Statement statement = null;
    private static ResultSet result = null;

    public static void main(String[] args) {
        connectToDb("jdbc:sqlite:mydb.sql");
        createDb();

        // Database is created, verifying next ...
        verifyDbContents();

        //READ FROM DB
        result = readFromDb("SELECT * FROM student;");
        try{
            while (result.next()){
                System.out.println("Studnr: "+result.getInt("studnr"));
            }
        }catch (SQLException e){
            System.out.println(REDBG+"ERROR reading result-> \n\t"+e.toString()+RESET);
        }

        //UPDATE DB
        updateDb("UPDATE student SET voornaam = 'Jaqueline' WHERE studnr = 123;");
        // check update
        result = readFromDb("SELECT * FROM student WHERE studnr = 123;");
        try{
            while (result.next()){
                System.out.println("voornaam: "+result.getString("voornaam"));
            }
        }catch (SQLException e){
            System.out.println(REDBG+"ERROR reading result-> \n\t"+e.toString()+RESET);
        }

        // Closing all connections
        closeAllConnections();
    }



    public static void connectToDb(String connectionString) {
        if (connection == null){
            try {
                connection = DriverManager.getConnection(connectionString);
            } catch (SQLException e){
                System.out.println(REDBG+"ERROR connecting to database -> "+connectionString+"\n\t"+e.toString()+RESET);
            }
        }
    }

    public static void closeAllConnections(){
        if (result != null) {
            try {
                result.close();
            } catch (SQLException e) {
                System.out.println(REDBG+"Error closing resultset: "+e.toString()+RESET);
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println(REDBG+"Error closing statement: "+e.toString()+RESET);
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println(REDBG+"Error closing connection: "+e.toString()+RESET);
            }
        }
    }



    public static void createDb() {
        try {
            statement = connection.createStatement();
            statement.executeUpdate(CREATE_TABLE_SQLITE);
        } catch (SQLException e){
            System.out.println(REDBG+"ERROR creating database table ->\n\t"+e.toString()+RESET);
        }
    }

    public static void verifyDbContents() {
        try {
            statement = connection.createStatement();
            result = statement.executeQuery("SELECT COUNT(*) as cnt FROM student;");
            while (result.next()){
                System.out.println("Assert that number of rows is 3: "+  (result.getInt("cnt") == 3));
                assert result.getInt("cnt") == 3;
            }
        } catch (SQLException e){
            System.out.println(REDBG+"ERROR verifying database table ->\n\t"+e.toString()+RESET);
            e.printStackTrace();
        }
    }

    public static ResultSet readFromDb(String query){
        try {
            statement = connection.createStatement();
            result = statement.executeQuery(query);
            return result;
        } catch (SQLException ex) {
            System.out.println(REDBG+"Exception running SQL commands: "+ex.toString()+RESET);
            ex.printStackTrace();
            throw new RuntimeException("Exception running SQL commands: "+ex.toString());
        }
    }

    public static void updateDb(String updateStr){
        try {
            statement = connection.createStatement();
            statement.executeUpdate(updateStr);
        } catch (SQLException ex) {
            System.out.println(REDBG+"Exception running SQL commands: "+ex.toString()+RESET);
            ex.printStackTrace();
        }
    }
    public static final String CREATE_TABLE_SQLITE = """
                                                    DROP TABLE IF EXISTS student;
                                                    CREATE TABLE student(
                                                        studnr INT NOT NULL PRIMARY KEY,
                                                        naam TEXT NOT NULL,
                                                        voornaam TEXT,
                                                        goedbezig BOOL
                                                    );
                                                    DROP TABLE IF EXISTS log;
                                                    CREATE TABLE log(
                                                        id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
                                                        date DATETIME DEFAULT CURRENT_TIMESTAMP,
                                                        foreign_id INT NOT NULL,
                                                        msg TEXT
                                                    );
                                                    INSERT INTO student(studnr, naam, voornaam, goedbezig) VALUES (123, 'Trekhaak', 'Jaak', 0);
                                                    INSERT INTO student(studnr, naam, voornaam, goedbezig) VALUES (456, 'Peeters', 'Jos', 0);
                                                    INSERT INTO student(studnr, naam, voornaam, goedbezig) VALUES (890, 'Dongmans', 'Ding', 1);
                                                    """;

    public static final String REDBG = "\033[41m";;
    public static final String RESET = "\033[0m";
}