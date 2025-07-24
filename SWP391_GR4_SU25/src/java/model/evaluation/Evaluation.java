package model.evaluation;

import model.day.Day;
import model.student.Student;

public class Evaluation {
    private String id;
    private Student student;
    private Day date;
    private String evaluation;
    private String notes;

    public Evaluation(String id, Student student, Day date, String evaluation, String notes) {
        this.id = id;
        this.student = student;
        this.date = date;
        this.evaluation = evaluation;
        this.notes = notes;
    }

    public Evaluation() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Day getDate() {
        return date;
    }

    public void setDate(Day date) {
        this.date = date;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
    
}
