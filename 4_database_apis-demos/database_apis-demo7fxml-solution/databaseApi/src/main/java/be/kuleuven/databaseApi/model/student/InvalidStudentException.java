package be.kuleuven.databaseApi.model.student;

public class InvalidStudentException extends RuntimeException {
    public InvalidStudentException(Student student) {
        super("Student " + student.getStudnr() + " not found in DB");
    }
}