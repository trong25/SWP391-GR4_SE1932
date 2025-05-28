package model.evaluation;

import model.day.Day;
import model.day.DayDAO;
import model.student.Student;
import model.student.StudentDAO;
import utils.DBContext;

import java.sql.*;
public class EvaluationDAO extends DBContext {
    private Evaluation createEvaluation(ResultSet resultSet) throws SQLException {
        StudentDAO studentDAO = new StudentDAO();
        DayDAO dayDAO = new DayDAO();
        Evaluation evaluation = new Evaluation();
        evaluation.setId(resultSet.getString("id"));
        Student student = studentDAO.getStudentById(resultSet.getString("student_id"));
        evaluation.setStudent(student);
        Day day = dayDAO.getDayByID(resultSet.getString("date_id"));
        evaluation.setDate(day);
        evaluation.setEvaluation(resultSet.getString("evaluation"));
        evaluation.setNotes(resultSet.getString("notes"));
        return evaluation;
    }
    public Evaluation getEvaluationByStudentIdAndDay(String studentId, String dateId) {
        String sql = "select * from Evaluations where student_id=? and date_id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, studentId);
            preparedStatement.setString(2, dateId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createEvaluation(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}


