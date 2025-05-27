package model.student;

import model.day.DayDAO;
import utils.DBContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public String addAttendance(StudentAttendance studentAttendance) {
        String res;
        if (getAttendanceByStudentAndDay(studentAttendance.getStudent().getId(), studentAttendance.getDay().getId()) == null) {
            res = insertAttendance(studentAttendance);
        } else {
            res = updateAttendance(studentAttendance);
        }
        return res;
    }

    private String insertAttendance(StudentAttendance studentAttendance) {
        String sql = "insert into StudentsAttendance values (?,?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            String newId = (getLatest() == null) ? "STA000001" : generateId(getLatest().getId());
            statement.setString(1, newId);
            statement.setString(2, studentAttendance.getDay().getId());
            statement.setString(3, studentAttendance.getStudent().getId());
            statement.setString(4, studentAttendance.getStatus());
            statement.setString(5, studentAttendance.getNote());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return "Thao tác thất bại! Vui lòng thử lại sau";
        }
        return "success";
    }

    private String updateAttendance(StudentAttendance studentAttendance) {
        String sql = "update StudentsAttendance set status = ?, note = ? where student_id = ? and day_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, studentAttendance.getStatus());
            statement.setString(2, studentAttendance.getNote());
            statement.setString(3, studentAttendance.getStudent().getId());
            statement.setString(4, studentAttendance.getDay().getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return "Thao tác thất bại! Vui lòng thử lại sau";
        }
        return "success";
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

    public boolean checkAttendanceByDay(List<Student> studentList, String dayId) {
        StringBuilder sql = new StringBuilder("select id from StudentsAttendance where day_id = ? ");
        if (!studentList.isEmpty()) {
            sql.append(" and student_id in (");
        }
        for (int i = 0; i < studentList.size(); i++) {
            sql.append("'").append(studentList.get(i).getId()).append(i == studentList.size() - 1 ? "')" : "',");
        }

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
            preparedStatement.setString(1, dayId);
            ResultSet resultSet = preparedStatement.executeQuery();
            int rows = 0;
            while (resultSet.next()) {
                rows++;
            }
            if (rows == studentList.size()) {
                return true;
            } else if (rows == 0) {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public List<StudentAttendance> getAttendanceOfClassByWeek(String classId, String weekId) {
        List<StudentAttendance> listStudentAttendance = new ArrayList<>();
        String sql = "select * from StudentsAttendance";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            // TODO: add filtering condition for classId and weekId
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listStudentAttendance;
    }

    private StudentAttendance getLatest() {
        String sql = "SELECT TOP 1 * FROM StudentsAttendance ORDER BY ID DESC";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return createStudentAttendance(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String generateId(String latestId) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(latestId);
        int number = 0;
        if (matcher.find()) {
            number = Integer.parseInt(matcher.group()) + 1;
        }
        DecimalFormat decimalFormat = new DecimalFormat("000000");
        return "STA" + decimalFormat.format(number);
    }
}
