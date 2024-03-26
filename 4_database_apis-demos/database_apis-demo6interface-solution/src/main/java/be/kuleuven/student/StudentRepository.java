package be.kuleuven.student;

import org.jdbi.v3.core.Jdbi;

import java.util.List;

public interface StudentRepository {
    public List<Student> getStudentsByName(String student);
    public void saveNewStudent(Student student);
}
