package be.kuleuven.databaseApi.model.student;

import java.util.List;

public interface StudentRepository {
    public List<Student> getStudentsByName(String student);
    public void saveNewStudent(Student student);
}
