package be.kuleuven.student;

import be.kuleuven.ConnectionManager;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class StudentRepositoryTests {
    private Connection connection;
    private StudentRepository studentRepository;

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
        studentRepository = new StudentRepository(this.connection);
    }
    @Test
    public void readFromDb_givenSelectAll_then3rowsInResultSet(){
        ResultSet r = studentRepository.readFromDb("SELECT * FROM student;");

        int rows = 0;
        try{
            while (r.next()){
                rows++;
            }
        }catch (SQLException sqe){
            throw new RuntimeException();
        }
        assertThat(rows).as("rows in database").isEqualTo(3);
    }

    @Test
    public void readFromDb_givenSelectStudnr123_thenVoornaamIsJaak(){
        ResultSet r = studentRepository.readFromDb("SELECT * FROM student WHERE studnr = 123;");
        String resultVoornaam = "";
        try{
            while (r.next()){
                resultVoornaam = r.getString("voornaam");
            }
        }catch (SQLException sqe){
            throw new RuntimeException();
        }
        assertThat(resultVoornaam).as("naam van studnr 123").isEqualTo("Jaak");
    }

    @Test
    public void updateDb_givenStudnr123VoornaamToJaqueline_thenVoornaamIsJaqueline(){
        studentRepository.updateDb("UPDATE student SET voornaam = 'Jaqueline' WHERE studnr = 123;");

        ResultSet r = studentRepository.readFromDb("SELECT * FROM student WHERE studnr = 123;");
        String resultVoornaam = "";
        try{
            while (r.next()){
                resultVoornaam = r.getString("voornaam");
            }
        }catch (SQLException sqe){
            throw new RuntimeException();
        }
        assertThat(resultVoornaam).as("naam van studnr 123").isEqualTo("Jaqueline");
    }

    @Test
    public void readFromDb_givenSelectAllWithWrongSyntax_thenShouldThrowSQLException(){
        assertThatExceptionOfType(RuntimeException.class)
                .isThrownBy(() -> {
                    ResultSet r = studentRepository.readFromDb("SELECT * FROM studen;");
                }).withMessageContaining("Exception running SQL commands");
    }

    @Test
    public void getStudentsByName_givenNaamInDb_thenGetListOf1StudentObjectWithCorrectStudent(){
        //TODO OPDRACHT 2: test getStudentsByName methode
        throw new UnsupportedOperationException();
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
