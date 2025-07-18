/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.student;

import model.day.Day;

/**
 *
 * @author MSI
 */
public class StudentAttendance {
      private String id;
    private Day day;
    private Student student;
    private String status;
    private String note;
    private String subjectName;
    private String timeslotName;

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

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTimeslotName() {
        return timeslotName;
    }

    public void setTimeslotName(String timeslotName) {
        this.timeslotName = timeslotName;
    }
    
    
}