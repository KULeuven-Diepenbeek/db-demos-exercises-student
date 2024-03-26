package be.kuleuven.databaseApi.model.cursus;

import be.kuleuven.databaseApi.model.student.Student;
import be.kuleuven.databaseApi.model.student.StudentRepository;

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
