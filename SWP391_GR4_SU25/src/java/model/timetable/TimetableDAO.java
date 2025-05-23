/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.timetable;

import java.sql.ResultSet;
import java.sql.SQLException;
import model.classes.ClassDAO;
import model.day.Day;
import model.day.DayDAO;
import model.personnel.Personnel;
import model.personnel.PersonnelDAO;
import model.subject.Subject;
import model.subject.SubjectDAO;
import model.timeslot.TimeSlot;
import model.timeslot.TimeSlotDAO;
import utils.DBContext;

/**
 *
 * @author MSI
 */
public class TimetableDAO extends DBContext{
    private Timetable createTimetable(ResultSet resultSet) throws SQLException {
        String id = resultSet.getString("id");
        String classId = resultSet.getString("class_id");
        String timeslotId = resultSet.getString("timeslot_id");
        String dateId = resultSet.getString("date_id");
        String subjectId = resultSet.getString("subject_id");
        String createdById = resultSet.getString("created_by");
        String status = resultSet.getString("status");
        String note = resultSet.getString("note");
        String teacherId = resultSet.getString("teacher_id");

        ClassDAO classDAO = new ClassDAO();
        TimeSlotDAO slotDAO = new TimeSlotDAO();
        DayDAO dayDAO = new DayDAO();
        SubjectDAO subDAO = new SubjectDAO();
        PersonnelDAO personnelDAO = new PersonnelDAO();

        model.classes.Class classs = classDAO.getClassById(classId);
    TimeSlot timeslot = slotDAO.getTimeslotById(timeslotId);
        Day day = dayDAO.getDayByID(dateId);
        Subject subject = subDAO.getSubjectBySubjectId(subjectId);
        Personnel createdBy = personnelDAO.getPersonnel(createdById);
        Personnel teacher = personnelDAO.getPersonnel(teacherId);

    return new Timetable(id, classs, timeslot, day, subject, createdBy, status, note, teacher);
   }
}
