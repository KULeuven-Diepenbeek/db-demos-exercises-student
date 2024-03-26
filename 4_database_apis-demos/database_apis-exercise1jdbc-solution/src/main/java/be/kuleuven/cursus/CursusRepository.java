package be.kuleuven.cursus;

import be.kuleuven.student.Student;
import be.kuleuven.student.StudentRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

//TODO OPDRACHT 6: implement class
public class CursusRepository {

    private final Connection connection;

    public CursusRepository(Connection connection) {
        this.connection = connection;
    }

    public List<Student> getStudentsInCourse(int cursusId){
        ArrayList<Student> resultList = new ArrayList<Student>();
        try {
            Statement s = connection.createStatement();
            String stmt = "SELECT * FROM inschrijvingen WHERE cursus = '" + cursusId + "'";
            //System.out.println(stmt);
            ResultSet result = s.executeQuery(stmt);

            while(result.next()) {
                int studnr = result.getInt("student");
                StudentRepository sr = new StudentRepository(connection);
                List<Student> sl = sr.getStudentsByStudnr(studnr);
                resultList.addAll(sl);
            }
            s.close();

        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }

        return resultList;

    }
    public List<String> getStudentsInCourseWithJoin(int cursusId){
        ArrayList<String> resultList = new ArrayList<String>();
        try {
            Statement s = connection.createStatement();
            String stmt = """
                    SELECT inschrijvingen.cursus, student.naam AS student_id
                    FROM inschrijvingen
                    JOIN student ON inschrijvingen.student = student.studnr;
                    """;
            //System.out.println(stmt);
            ResultSet result = s.executeQuery(stmt);

            while(result.next()) {
                String student = result.getString("student_id");
                resultList.add(student);
            }
            s.close();

        } catch(Exception ex) {
            throw new RuntimeException(ex);
        }

        return resultList;
    }
}
