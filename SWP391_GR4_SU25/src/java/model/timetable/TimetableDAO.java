/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.timetable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.classes.ClassDAO;
import model.classes.Class;
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
public class TimetableDAO extends DBContext {

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

    public List<Timetable> getTimetableByClassAndWeek(String classId, String weekId, String status) {
        List<Timetable> timetables = new ArrayList<>();
        String sql = "SELECT t.id AS timetable_id,\n"
                + "       c.id AS class_id,\n"
                + "       ts.id AS timeslot_id,\n"
                + "       d.id AS date_id,\n"
                + "       s.id AS subject_id,\n"
                + "       t.created_by,\n"
                + "       t.status,\n"
                + "       t.note,\n"
                + "       p.id AS teacher_id\n"
                + "FROM Timetables t\n"
                + "JOIN Class c ON t.class_id = c.id\n"
                + "JOIN Timeslots ts ON t.timeslot_id = ts.id\n"
                + "JOIN Days d ON t.date_id = d.id\n"
                + "JOIN Subjects s ON t.subject_id = s.id\n"
                + "JOIN Personnels p ON t.teacher_id = p.id\n"
                + "JOIN Weeks w ON d.week_id = w.id\n"
                + "WHERE c.id = ? \n"
                + "  AND w.id = ?\n"
                + "  AND t.status = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, classId);
            statement.setString(2, weekId);
            statement.setString(3, status);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Fetching data from the result set and creating Timetable object for each row
                String timetableId = resultSet.getString("timetable_id");
                String classIdResult = resultSet.getString("class_id");
                String timeslotId = resultSet.getString("timeslot_id");
                String dateId = resultSet.getString("date_id");
                String subjectId = resultSet.getString("subject_id");
                String createdBy = resultSet.getString("created_by");
                String statusResult = resultSet.getString("status");
                String note = resultSet.getString("note");
                String teacherId = resultSet.getString("teacher_id");

                // Fetch related entities using DAOs
                ClassDAO classDAO = new ClassDAO();
                TimeSlotDAO timeslotDAO = new TimeSlotDAO();
                DayDAO dayDAO = new DayDAO();
                SubjectDAO subjectDAO = new SubjectDAO();
                PersonnelDAO personnelDAO = new PersonnelDAO();

                Class classs = classDAO.getClassById(classIdResult);
                TimeSlot timeslot = timeslotDAO.getTimeslotById(timeslotId);
                Day day = dayDAO.getDayByID(dateId);
                Subject subject = subjectDAO.getSubjectBySubjectId(subjectId);
                Personnel createdByObj = personnelDAO.getPersonnel(createdBy);
                Personnel teacher = personnelDAO.getPersonnel(teacherId);

                // Create Timetable object
                Timetable timetable = new Timetable(timetableId, classs, timeslot, day, subject, createdByObj, statusResult, note, teacher);
                timetables.add(timetable);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving timetables by classId and weekId", e);
        }
        return timetables;
    }

    public int getTodayClassesCount(String teacherId, String dayId) {
        String sql = "SELECT COUNT(*) as count " +
                "FROM Timetables t " +
                "WHERE t.teacher_id = ? AND t.date_id = ? AND t.status = N'đã được duyệt'";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, teacherId);
            statement.setString(2, dayId);
            
            // Debug logs
            System.out.println("Debug - Teacher ID: " + teacherId);
            System.out.println("Debug - Day ID: " + dayId);
            System.out.println("Debug - SQL: " + sql);
            
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                System.out.println("Debug - Classes found: " + count);
                return count;
            }
        } catch (SQLException e) {
            System.out.println("Debug - Error in getTodayClassesCount: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
    public List<Timetable> getTeacherTimetable(String teacherId, String weekId) {
        List<Timetable> timetables = new ArrayList<>();
        String sql = "SELECT t.id AS timetable_id,\n" +
                "c.id AS class_id,\n" +
                "                ts.id AS timeslot_id,\n" +
                "                  d.id AS date_id,\n" +
                "                 s.id AS subject_id,\n" +
                "                    t.created_by,\n" +
                "                   t.status,\n" +
                "                 t.note,\n" +
                "                    p.id AS teacher_id\n" +
                "             FROM Timetables t\n" +
                "              JOIN Class c ON t.class_id = c.id\n" +
                "             JOIN Timeslots ts ON t.timeslot_id = ts.id\n" +
                "             JOIN Days d ON t.date_id = d.id\n" +
                "            JOIN Subjects s ON t.subject_id = s.id\n" +
                "           JOIN Personnels p ON t.teacher_id = p.id\n" +
                "           JOIN Weeks w ON d.week_id = w.id\n" +
                "                WHERE p.id = ?\n" +
                "           AND w.id = ?\n" +
                "          AND t.status = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, teacherId);
            statement.setString(2, weekId);
            statement.setString(3, "đã được duyệt");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Fetching data from the result set and creating Timetable object for each row
                String timetableId = resultSet.getString("timetable_id");
                String classIdResult = resultSet.getString("class_id");
                String timeslotId = resultSet.getString("timeslot_id");
                String dateId = resultSet.getString("date_id");
                String subjectId = resultSet.getString("subject_id");
                String createdBy = resultSet.getString("created_by");
                String statusResult = resultSet.getString("status");
                String note = resultSet.getString("note");

                // Fetch related entities using DAOs
                ClassDAO classDAO = new ClassDAO();
                TimeSlotDAO timeslotDAO = new TimeSlotDAO();
                DayDAO dayDAO = new DayDAO();
                SubjectDAO subjectDAO = new SubjectDAO();
                PersonnelDAO personnelDAO = new PersonnelDAO();

                Class classs = classDAO.getClassById(classIdResult);
                TimeSlot timeslot = timeslotDAO.getTimeslotById(timeslotId);
                Day day = dayDAO.getDayByID(dateId);
                Subject subject = subjectDAO.getSubjectBySubjectId(subjectId);
                Personnel createdByObj = personnelDAO.getPersonnel(createdBy);
                Personnel teacher = personnelDAO.getPersonnel(teacherId);

                // Create Timetable object
                Timetable timetable = new Timetable(timetableId, classs, timeslot, day, subject, createdByObj, statusResult, note, teacher);
                timetables.add(timetable);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving timetables by classId and weekId", e);
        }
        return timetables;
    }

}
