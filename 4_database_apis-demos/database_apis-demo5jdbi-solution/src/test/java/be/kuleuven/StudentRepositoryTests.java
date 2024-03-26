package be.kuleuven;

import be.kuleuven.student.Student;
import be.kuleuven.student.StudentRepository;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class StudentRepositoryTests {
    private Connection connection;
    private StudentRepository studentRepository;

    @BeforeEach
    public void setUp() {
        ConnectionManager cm = new ConnectionManager("jdbc:sqlite:test.sql");
        this.connection = cm.getConnection();
        cm.flushConnection();
        studentRepository = new StudentRepository(cm.getConnectionString());
    }

    @Test
    public void getStudentsByName_givenNaamInDb_thenGetListOf1StudentObjectWithCorrectStudent(){
        List<Student> result = studentRepository.getStudentsByName("Dongmans");
        assertThat(result).withFailMessage("result should not be null").isNotNull();
        assertThat(result.size()).withFailMessage("resultset should be one record").isEqualTo(1);
        assertThat(result.get(0))
//                .withFailMessage("Student should be Ding Dongmans 890 true")
                .as("resultSet element 0")
                .isEqualTo(new Student(890, "Dongmans", "Ding", true));

    }

    @Test
    public void getStudentsByName_givenNameUnknown_thenReturnsEmptyList() {
        List<Student> result = studentRepository.getStudentsByName("bloekiebla");
        assertThat(result).withFailMessage("result should not be null").isNotNull();
        assertThat(result.size()).withFailMessage("resultset size should be zero").isEqualTo(0);
    }


    @Test
    public void saveStudent_givenSamePrimaryKey_thenShouldCrash() {
        // We weten dat student "Peeters" reeds in de DB zit met als key 456.
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> {
                    studentRepository.saveNewStudent(new Student(456,"PeetersCopy","Jozefien", true));
                }).withMessageContaining("[SQLITE_CONSTRAINT_PRIMARYKEY] A PRIMARY KEY constraint failed (UNIQUE constraint failed:");
    }

    @Test
    public void saveStudent_givenCorrectValues_thenAddsNewStudentInDb() {
        studentRepository.saveNewStudent(new Student(23356,"Johanna", "Sofie",  true));

        List<Student> result = studentRepository.getStudentsByName("Johanna");
        assertThat(result.get(0))
                .as("Found student by name 'Johanna' in db")
                .isEqualTo(new Student(23356, "Johanna", "Sofie", true));
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
