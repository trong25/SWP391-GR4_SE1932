package model.evaluation;

import model.day.Day;
import model.day.DayDAO;
import model.student.Student;
import model.student.StudentDAO;
import utils.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EvaluationDAO {
    public static List<Evaluation> getEvaluationsByStudentId(String studentId) {
    List<Evaluation> list = new ArrayList<>();
    String sql = "SELECT * FROM Evaluations WHERE student_id = ?";

    DBContext db = new DBContext();

    try (Connection conn = db.getConnection()) {
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, studentId);
        ResultSet rs = ps.executeQuery();

        StudentDAO studentDAO = new StudentDAO();
        DayDAO dayDAO = new DayDAO();

        while (rs.next()) {
            Evaluation e = new Evaluation();
            e.setId(rs.getString("id"));

            Student student = studentDAO.getStudentById(studentId);
            Day day = dayDAO.getDayByID(rs.getString("date_id"));

            e.setStudent(student);
            e.setDate(day);
            e.setEvaluation(rs.getString("evaluation"));
            e.setNotes(rs.getString("notes"));

            list.add(e);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}
}


