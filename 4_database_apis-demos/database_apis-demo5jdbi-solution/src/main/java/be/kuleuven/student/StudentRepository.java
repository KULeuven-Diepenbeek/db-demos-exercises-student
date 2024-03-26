package be.kuleuven.student;

import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class StudentRepository {
    private Jdbi jdbi;

    public StudentRepository(String connectionString) {
        jdbi = Jdbi.create(connectionString);
    }

    public List<Student> getStudentsByName(String student) {
        return jdbi.withHandle(handle -> {
            return handle.createQuery("SELECT * FROM student WHERE naam = :naam")
                    .bind("naam", student)
                    .mapToBean(Student.class)
                    .list();
        });

    }

    public void saveNewStudent(Student student) {
        jdbi.withHandle(handle -> {
            return handle.execute("INSERT INTO student (studnr, naam, voornaam, goedBezig) VALUES (?, ?, ?, ?)",
                    student.getStudnr(), student.getNaam(), student.getVoornaam(), student.isGoedBezig());

//            return handle.createUpdate("INSERT INTO student (studnr, naam, voornaam, goedBezig) VALUES (:studnr, :naam, :voornaam, :goedBezig)")
//                    .bindBean(student)
//                    .execute();
        });
    }
}
