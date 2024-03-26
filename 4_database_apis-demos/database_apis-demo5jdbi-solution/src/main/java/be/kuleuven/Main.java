package be.kuleuven;

import be.kuleuven.student.Student;
import be.kuleuven.student.StudentRepository;

import java.sql.*;
import java.util.List;

public class Main {
        private static Statement statement = null;
    private static ResultSet result = null;

    public static void main(String[] args) throws SQLException {
        ConnectionManager cm = new ConnectionManager("jdbc:sqlite:test.sql");
        cm.flushConnection();
        Connection connection = cm.getConnection();
        StudentRepository studentRepository = new StudentRepository(cm.getConnectionString());
        studentRepository.saveNewStudent(new Student(23356,"Johanna", "Sofie",  true));
        List<Student> result = studentRepository.getStudentsByName("Johanna");
        for (Student s: result) {
            System.out.println(s);
        }
    }

    public static final String REDBG = "\033[41m";;
    public static final String RESET = "\033[0m";
}