package model.student;

import model.day.DayDAO;
import utils.DBContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentAttendanceDAO extends DBContext {

    private StudentAttendance createStudentAttendance(ResultSet resultSet) throws SQLException {
        StudentAttendance studentAttendance = new StudentAttendance();
        studentAttendance.setId(resultSet.getString("id"));
        StudentDAO studentDAO = new StudentDAO();
        studentAttendance.setStudent(studentDAO.getStudentById(resultSet.getString("student_id")));
        DayDAO dayDAO = new DayDAO();
        studentAttendance.setDay(dayDAO.getDayByID(resultSet.getString("day_id")));
        studentAttendance.setStatus(resultSet.getString("status"));
        studentAttendance.setNote(resultSet.getString("note"));
        return studentAttendance;
    }


    public StudentAttendance getAttendanceByStudentAndDay(String studentId, String dayId) {
        String sql = "select * from StudentsAttendance where student_id = ? and day_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, studentId);
            statement.setString(2, dayId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createStudentAttendance(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
