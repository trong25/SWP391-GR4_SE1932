/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.timetable;

import java.util.Date;
import model.personnel.Personnel;

public class TimetableDTO {

    private model.classes.Class aClass;

    public model.classes.Class getaClass() {
        return aClass;
    }

    public void setaClass(model.classes.Class aClass) {
        this.aClass = aClass;
    }
    private String weekId;
    private Date startDate;
    private Date endDate;
    private Personnel createdBy;
    private String status;
    private String note;
    private Personnel teacher;

    public TimetableDTO(model.classes.Class aClass, String weekId, Date startDate, Date endDate, Personnel createdBy, String status,String note, Personnel teacher) {
        this.aClass = aClass;
        this.weekId = weekId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdBy = createdBy;
        this.status = status;
        this.note = note;
        this.teacher = teacher;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    // Getters and setters...
    public String getWeekId() {
        return weekId;
    }

    public void setWeekId(String weekId) {
        this.weekId = weekId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public Personnel getTeacher() {
        return teacher;
    }

    public void setTeacher(Personnel teacher) {
        this.teacher = teacher;
    }
}
