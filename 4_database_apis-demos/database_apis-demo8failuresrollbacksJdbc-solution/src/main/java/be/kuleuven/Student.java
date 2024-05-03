package be.kuleuven;

import java.util.Objects;

public class Student {
    private int studnr;
    private String naam, voornaam;
    private boolean goedbezig;

    public Student(int studnr, String naam, String voornaam, boolean goedbezig) {
        this.studnr = studnr;
        this.naam = naam;
        this.voornaam = voornaam;
        this.goedbezig = goedbezig;
    }

    public int getStudnr() {
        return studnr;
    }

    public void setStudnr(int studnr) {
        this.studnr = studnr;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public boolean isGoedbezig() {
        return goedbezig;
    }

    public void setGoedbezig(boolean goedbezig) {
        this.goedbezig = goedbezig;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return studnr == student.studnr && goedbezig == student.goedbezig && Objects.equals(naam, student.naam) && Objects.equals(voornaam, student.voornaam);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studnr, naam, voornaam, goedbezig);
    }

    @Override
    public String toString() {
        return "Student{" +
                "studnr=" + studnr +
                ", naam='" + naam + '\'' +
                ", voornaam='" + voornaam + '\'' +
                ", goedbezig=" + goedbezig +
                '}';
    }
}
