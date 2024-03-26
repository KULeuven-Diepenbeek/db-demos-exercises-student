package be.kuleuven;

import javax.swing.plaf.nimbus.State;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;

public class ConnectionManager {
    private Connection connection;
    private String connectionString;

    public ConnectionManager(String connectionString){
        this.connectionString = connectionString;
        try {
            // auto-creates if not exists
            connection = DriverManager.getConnection(connectionString);
            connection.setAutoCommit(false);

            initTables();
            verifyTableContents();
        } catch (Exception e) {
            System.out.println("Db connection handle failure");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
    public String getConnectionString() {
        return connectionString;
    }

    public void flushConnection() {
        try {
            connection.commit();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void initTables() throws Exception {
        String sql = new String(Files.readAllBytes(Paths.get("src/main/resources/dbcreate.sql")));
        System.out.println(sql);
        Statement s = connection.createStatement();
        s.executeUpdate(sql);
        s.close();
    }

    public void verifyTableContents() throws SQLException {
        Statement s = connection.createStatement();
        ResultSet result = s.executeQuery("SELECT COUNT(*) as cnt FROM student");
        assert result.getInt("cnt") == 3;
    }
}
