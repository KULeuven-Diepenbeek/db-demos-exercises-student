package be.kuleuven;

import be.kuleuven.student.InvalidStudentException;
import be.kuleuven.student.Student;
import be.kuleuven.student.StudentRepository;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public abstract class StudentRepositoryTests {
    protected StudentRepository studentRepository;

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

}
