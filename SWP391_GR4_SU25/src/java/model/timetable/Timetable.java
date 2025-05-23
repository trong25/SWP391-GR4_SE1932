/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.timetable;

import model.classes.Class;
import model.day.Day;
import model.personnel.Personnel;
import model.subject.Subject;
import model.timeslot.TimeSlot;

/**
 *
 * @author MSI
 */
public class Timetable {
    private String id;
    private model.classes.Class aClass;
    private TimeSlot timeslot;
    private Day day;
    private Subject subject;
    private Personnel createdBy;
    private String status;
    private String note;
    private Personnel teacher;

    public Timetable() {
    }

    public Timetable(String id, Class aClass, TimeSlot timeslot, Day day, Subject subject, Personnel createdBy, String status, String note, Personnel teacher) {
        this.id = id;
        this.aClass = aClass;
        this.timeslot = timeslot;
        this.day = day;
        this.subject = subject;
        this.createdBy = createdBy;
        this.status = status;
        this.note = note;
        this.teacher = teacher;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }

    public TimeSlot getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(TimeSlot timeslot) {
        this.timeslot = timeslot;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Personnel getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Personnel createdBy) {
        this.createdBy = createdBy;
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

    public Personnel getTeacher() {
        return teacher;
    }

    public void setTeacher(Personnel teacher) {
        this.teacher = teacher;
    }
    
    
}
