package be.kuleuven;

import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class StudentTableApiTests {
    private Connection connection;
    private StudentTableApi sta;

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
        sta = new StudentTableApi(this.connection);
    }
    @Test
    public void readFromDb_givenSelectAll_then3rowsInResultSet(){
        ResultSet r = sta.readFromDb("SELECT * FROM student;");

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
        ResultSet r = sta.readFromDb("SELECT * FROM student WHERE studnr = 123;");
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
        sta.updateDb("UPDATE student SET voornaam = 'Jaqueline' WHERE studnr = 123;");

        ResultSet r = sta.readFromDb("SELECT * FROM student WHERE studnr = 123;");
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
                    ResultSet r = sta.readFromDb("SELECT * FROM studen;");
                }).withMessageContaining("Exception running SQL commands");
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
