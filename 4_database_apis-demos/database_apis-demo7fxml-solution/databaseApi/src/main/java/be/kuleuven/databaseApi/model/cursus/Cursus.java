package be.kuleuven.databaseApi.model.cursus;

import be.kuleuven.databaseApi.model.student.Student;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cursus {
    private int courseCode, ects;
    private String naam;
    private List<Student> enrolledStudents;

    public Cursus(int courseCode, int ects, String naam) {
        this.courseCode = courseCode;
        this.ects = ects;
        this.naam = naam;
        this.enrolledStudents = new ArrayList<Student>();
    }

    public void addStudentToCourse(Student s){
        enrolledStudents.add(s);
    }

    public void clearStudentsInCourse(Student s){
        enrolledStudents = new ArrayList<Student>();
    }

    public int getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(int courseCode) {
        this.courseCode = courseCode;
    }

    public int getEcts() {
        return ects;
    }

    public void setEcts(int ects) {
        this.ects = ects;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public List<Student> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(List<Student> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    @Override
    public String toString() {
        return "Cursus{" +
                "courseCode=" + courseCode +
                ", ects=" + ects +
                ", naam='" + naam + '\'' +
                ", enrolledStudents=" + enrolledStudents +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cursus cursus = (Cursus) o;
        return courseCode == cursus.courseCode && ects == cursus.ects && Objects.equals(naam, cursus.naam) && Objects.equals(enrolledStudents, cursus.enrolledStudents);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseCode, ects, naam, enrolledStudents);
    }
}
