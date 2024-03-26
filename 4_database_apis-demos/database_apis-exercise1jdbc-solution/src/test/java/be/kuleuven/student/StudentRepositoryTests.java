package be.kuleuven.student;

import be.kuleuven.ConnectionManager;
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
        //SOLVED OPDRACHT 2: test getStudentsByName methode
        // Aangezien er voor elke test een nieuwe testdatabase in RAM wordt aangemaakt met al enkele test entries, hebben we de saveStudent methode dus niet nodig
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

    @Test
    public void updateStudent_givenUnknownStudnr_thenThrowsInvalidStudentException() {
        assertThatExceptionOfType(InvalidStudentException.class)
                .isThrownBy(() -> {
                    int invalidStudnr = 456789;
                    studentRepository.updateStudent(new Student(invalidStudnr, "PeetersAndereAchternaam", "PeetersAndereVoornaam", true));
                });
    }

    @Test
    public void updateStudent_givenCorrectValues_thenUpdatesAllProperties() {
        studentRepository.updateStudent(new Student(456, "PeetersAndereAchternaam", "PeetersAndereVoornaam", true));
        List<Student> students = studentRepository.getStudentsByName("PeetersAndereAchternaam");
        assertThat(students.size()).withFailMessage("Expected changed student name 'PeetersAndereAchternaam' to be found in DB!").isEqualTo(1);
        Student updatedFromDb = students.get(0);
        assertThat(updatedFromDb.getNaam())
                .as("Naam updated student")
                .isEqualTo("PeetersAndereAchternaam");
        assertThat(updatedFromDb.getVoornaam())
                .as("Voornaam updated student")
                .isEqualTo("PeetersAndereVoornaam");
        assertThat(updatedFromDb.getStudnr())
                .as("Studnr updated student")
                .isEqualTo(456);
        assertThat(updatedFromDb.isGoedBezig())
                .as("goedBezig updated student")
                .isEqualTo(true);
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
