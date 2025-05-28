package model.student;

import model.day.Day;

public class StudentAttendance {
    private String id;
    private Day day;
    private Student student;
    private String status;
    private String note;

    public StudentAttendance() {
    }

    public StudentAttendance(String id, Day day, Student student, String status, String note) {
        this.id = id;
        this.day = day;
        this.student = student;
        this.status = status;
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
