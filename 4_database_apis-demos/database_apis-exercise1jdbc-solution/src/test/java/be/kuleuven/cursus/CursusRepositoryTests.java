package be.kuleuven.cursus;

import be.kuleuven.ConnectionManager;
import be.kuleuven.student.Student;
import be.kuleuven.student.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CursusRepositoryTests {
    private Connection connection;
    private CursusRepository cursusRepository;

    @BeforeEach
    public void setUp() {
        ConnectionManager cm = new ConnectionManager("jdbc:sqlite::memory:");
        this.connection = cm.getConnection();
        try {
            cm.initTables();
            cm.verifyTableContents();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        cursusRepository = new CursusRepository(this.connection);
    }

    @Test
    public void getStudentsInCourse_givenDab_then1studentPeeters(){
        List<Student> result = cursusRepository.getStudentsInCourse(1);
        assertThat(result).withFailMessage("result should not be null").isNotNull();
        assertThat(result.size()).withFailMessage("resultset should be one record").isEqualTo(1);
        assertThat(result.get(0).getNaam())
                .as("resultSet element 0")
                .isEqualTo("Peeters");
    }

    @Test
    public void getStudentsInCourseWithJoin_givenDab_then1namePeeters(){
        List<String> result = cursusRepository.getStudentsInCourseWithJoin(1);
        assertThat(result).withFailMessage("result should not be null").isNotNull();
        assertThat(result.size()).withFailMessage("resultset should be one record").isEqualTo(1);
        assertThat(result.get(0))
                .as("resultSet element 0")
                .isEqualTo("Peeters");
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
