package be.kuleuven;

import org.junit.jupiter.api.*;
import org.sqlite.SQLiteException;

import javax.xml.transform.Result;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;


public class MainTests {
//    public static final String TEST_DB = "mydb-test.sql";
    @BeforeEach
    public void setUp() {
//        Main.connectToDbLocal("jdbc:sqlite:"+TEST_DB);
        Main.connectToDb("jdbc:sqlite::memory:");
        Main.createDb();
    }
    @Test
    public void readFromDb_givenSelectAll_then3rowsInResultSet(){
        ResultSet r = Main.readFromDb("SELECT * FROM student;");

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
        ResultSet r = Main.readFromDb("SELECT * FROM student WHERE studnr = 123;");
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
        Main.updateDb("UPDATE student SET voornaam = 'Jaqueline' WHERE studnr = 123;");

        ResultSet r = Main.readFromDb("SELECT * FROM student WHERE studnr = 123;");
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
                    ResultSet r = Main.readFromDb("SELECT * FROM studen;");
                }).withMessageContaining("Exception running SQL commands");
    }

    @AfterEach
    public void tearDown() {
        Main.closeAllConnections();
//        //Delete mydb-test file
//        File testdb = new File(TEST_DB);
//        testdb.delete();
    }
}
