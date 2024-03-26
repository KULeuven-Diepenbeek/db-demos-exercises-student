package be.kuleuven;

import be.kuleuven.student.StudentRepository;
import be.kuleuven.student.StudentRepositoryJDBI;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.Connection;
import java.sql.SQLException;

public class StudentRepositoryJDBITests extends StudentRepositoryTests{
    private Connection connection;

    @BeforeEach
    public void setUp() {
        ConnectionManager cm = new ConnectionManager("jdbc:sqlite:test.sql");
        this.connection = cm.getConnection();
        cm.flushConnection();
        this.studentRepository = new StudentRepositoryJDBI(cm.getConnectionString());
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
