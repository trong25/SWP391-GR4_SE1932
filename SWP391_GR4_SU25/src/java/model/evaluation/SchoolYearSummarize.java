package model.evaluation;

import models.personnel.Personnel;
import models.pupil.Pupil;

public class SchoolYearSummarize {
    private Pupil pupil;
    private String schoolYearId;
    private Personnel teacher;
    private String title;
    private String goodTicket;
    private String teacherNote;

    public SchoolYearSummarize(Pupil pupil, String schoolYearId, Personnel teacher, String title, String goodTicket, String teacherNote) {
        this.pupil = pupil;
        this.schoolYearId = schoolYearId;
        this.teacher = teacher;
        this.title = title;
        this.goodTicket = goodTicket;
        this.teacherNote = teacherNote;
    }

    public SchoolYearSummarize() {
    }

    public Pupil getPupil() {
        return pupil;
    }

    public void setPupil(Pupil pupil) {
        this.pupil = pupil;
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
