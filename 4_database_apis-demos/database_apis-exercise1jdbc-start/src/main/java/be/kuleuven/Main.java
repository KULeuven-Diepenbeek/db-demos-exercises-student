package be.kuleuven;

import be.kuleuven.student.StudentRepository;

import java.sql.*;

public class Main {
        private static Statement statement = null;
    private static ResultSet result = null;

    public static void main(String[] args) throws SQLException {
        // CONNECT TO LOCAL SQLITE
        ConnectionManager cm = new ConnectionManager("jdbc:sqlite:mydb.sql");
        Connection connection = cm.getConnection();
        // Database is created, verifying next ...
        cm.verifyTableContents();

        //READ FROM DB
        StudentRepository sta = new StudentRepository(connection);
        result = sta.readFromDb("SELECT * FROM student;");
        try{
            while (result.next()){
                System.out.println("Studnr: "+result.getInt("studnr"));
            }
        }catch (SQLException e){
            System.out.println(REDBG+"ERROR reading result-> \n\t"+e.toString()+RESET);
        }

        //UPDATE DB
        sta.updateDb("UPDATE student SET voornaam = 'Jaqueline' WHERE studnr = 123;");
        // check update
        result = sta.readFromDb("SELECT * FROM student WHERE studnr = 123;");
        try{
            while (result.next()){
                System.out.println("voornaam: "+result.getString("voornaam"));
            }
        }catch (SQLException e){
            System.out.println(REDBG+"ERROR reading result-> \n\t"+e.toString()+RESET);
        }

        // Closing all connections
        cm.flushConnection();
    }

    public static final String REDBG = "\033[41m";;
    public static final String RESET = "\033[0m";
}