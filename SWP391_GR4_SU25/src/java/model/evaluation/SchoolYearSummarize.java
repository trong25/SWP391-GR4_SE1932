package model.evaluation;

import model.personnel.Personnel;
import model.student.Student;

public class SchoolYearSummarize {
    private Student student;
    private String schoolYearId;
    private Personnel teacher;
    private String title;
    private String goodTicket;
    private String teacherNote;

    public SchoolYearSummarize(Student student, String schoolYearId, Personnel teacher, String title, String goodTicket, String teacherNote) {
        this.student = student;
        this.schoolYearId = schoolYearId;
        this.teacher = teacher;
        this.title = title;
        this.goodTicket = goodTicket;
        this.teacherNote = teacherNote;
    }

    public SchoolYearSummarize() {
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getSchoolYearId() {
        return schoolYearId;
    }

    public void setSchoolYearId(String schoolYearId) {
        this.schoolYearId = schoolYearId;
    }

    public Personnel getTeacher() {
        return teacher;
    }

    public void setTeacher(Personnel teacher) {
        this.teacher = teacher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTeacherNote() {
        return teacherNote;
    }

    public void setTeacherNote(String teacherNote) {
        this.teacherNote = teacherNote;
    }

    public String getGoodTicket() {
        return goodTicket;
    }

    public void setGoodTicket(String goodTicket) {
        this.goodTicket = goodTicket;
    }
}
