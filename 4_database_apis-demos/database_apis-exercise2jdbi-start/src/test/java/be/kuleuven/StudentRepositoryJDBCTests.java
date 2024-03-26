package be.kuleuven;

import be.kuleuven.student.StudentRepositoryJDBC;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.SQLException;

public class StudentRepositoryJDBCTests extends StudentRepositoryTests{
    private Connection connection;

    @BeforeEach
    public void setUp() {
        ConnectionManager cm = new ConnectionManager("jdbc:sqlite:test.sql");
        this.connection = cm.getConnection();
        this.studentRepository = new StudentRepositoryJDBC(cm.getConnection());
    }
    @AfterEach
    public void tearDown() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
