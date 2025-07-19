/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.timetable;

/**
 *
 * @author admin
 */
public class TeacherSlot {
    private String teacherId;
    private String subjectName;
    private String timeslotName;
    private String timeslotId;

    public TeacherSlot() {
    }

    public TeacherSlot(String teacherId, String subjectName, String timeslotName, String timeslotId) {
        this.teacherId = teacherId;
        this.subjectName = subjectName;
        this.timeslotName = timeslotName;
        this.timeslotId = timeslotId;
    }

    

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
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

    public String getTimeslotId() {
        return timeslotId;
    }

    public void setTimeslotId(String timeslotId) {
        this.timeslotId = timeslotId;
    }
    
}
