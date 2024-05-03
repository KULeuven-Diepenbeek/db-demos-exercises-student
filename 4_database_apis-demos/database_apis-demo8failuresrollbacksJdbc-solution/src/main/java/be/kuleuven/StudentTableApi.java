package be.kuleuven;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class StudentTableApi {
    private final Connection connection;

    public StudentTableApi(Connection connection) {
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
            connection.commit();
            statement.close();
        } catch (SQLException ex) {
            try{
                connection.rollback();
            }catch (SQLException ex2){
                System.out.println("ERROR ROLLBACK FAILED");
            }
            System.out.println(Main.REDBG+"Exception running SQL commands: "+ex.toString()+Main.RESET);
            ex.printStackTrace();
        }
    }
}
