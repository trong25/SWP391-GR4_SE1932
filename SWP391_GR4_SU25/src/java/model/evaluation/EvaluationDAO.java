package model.evaluation;

import model.day.Day;
import model.day.DayDAO;
import model.student.Student;
import model.student.StudentDAO;
import utils.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.timetable.Timetable;
import model.timetable.TimetableDAO;

public class EvaluationDAO extends DBContext {

    private Evaluation createEvaluation(ResultSet resultSet) throws SQLException {
        StudentDAO studentDAO = new StudentDAO();
        DayDAO dayDAO = new DayDAO();
        TimetableDAO timetableDAO = new TimetableDAO();
        Evaluation evaluation = new Evaluation();
        evaluation.setId(resultSet.getString("id"));
        Student student = studentDAO.getStudentsById(resultSet.getString("student_id"));
        evaluation.setStudent(student);
        Timetable timetable = timetableDAO.getTimetableById(resultSet.getString("timetable_id"));
        evaluation.setTimetable(timetable);
        evaluation.setEvaluation(resultSet.getString("evaluation"));
        evaluation.setNotes(resultSet.getString("notes"));
        return evaluation;
    }

    public Evaluation getEvaluationByStudentIdAndDay(String studentId, String timetableId) {
        String sql = "select * from Evaluations where student_id=? and timetable_id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, studentId);
            preparedStatement.setString(2, timetableId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createEvaluation(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Evaluation> getEvaluationByWeekandPupilId(String weekId, String pupil_id) {
        List<Evaluation> list = new ArrayList<>();
        String sql = """
                   -- Lấy danh sách evaluation của một học sinh trong một tuần (theo tiết học)
                   SELECT 
                       e.id, 
                       e.student_id, 
                       e.timetable_id, 
                       e.evaluation, 
                       e.notes  
                   FROM [Evaluations] e 
                       JOIN [Timetables] t ON e.timetable_id = t.id
                       JOIN [Days] d ON t.date_id = d.id
                       JOIN [Weeks] w ON d.week_id = w.id
                   WHERE 
                       d.week_id = ?           -- Tham số 1: week_id
                       AND e.student_id = ?;   -- Tham số 2: student_id
                   """;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, weekId);
            preparedStatement.setString(2, pupil_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(createEvaluation(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public int countEvaluationOfWeek(String week_id, String pupil_id) {
        int result = 0;
        String sql = """
                     SELECT 
                         COUNT(e.evaluation) AS good_day 
                     FROM [Evaluations] e 
                         JOIN [Timetables] t ON e.timetable_id = t.id
                         JOIN [Days] d ON t.date_id = d.id
                         JOIN [Weeks] w ON d.week_id = w.id
                     WHERE 
                         w.id = ?                    -- Tham số 1: week_id
                         AND e.student_id = ?        -- Tham số 2: student_id
                         AND e.evaluation = 'Ngoan'; -- Lọc theo evaluation = 'Ngoan'
                     """;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, week_id);
            statement.setString(2, pupil_id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getInt("good_day");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<String> NumberOfGoodEvaluationsPerYear(String student_id) {
        List<String> list = new ArrayList<>();
        String sql = """
            WITH T AS (
                SELECT school_year_id, week_id, COUNT(evaluation) AS good_day 
                FROM Evaluations e
                JOIN Timetables t ON e.timetable_id = t.id
                JOIN Days d ON t.date_id = d.id
                JOIN Weeks w ON d.week_id = w.id
                WHERE e.student_id = ? AND e.evaluation = 'Ngoan'
                GROUP BY school_year_id, week_id
            ),
            N AS (
                SELECT school_year_id, COUNT(id) AS week 
                FROM Weeks
                GROUP BY school_year_id
            )
            SELECT T.school_year_id, N.week, COUNT(T.good_day) AS good_ticket 
            FROM T 
            JOIN N ON T.school_year_id = N.school_year_id
            WHERE T.good_day >= 3
            GROUP BY T.school_year_id, N.week
            """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, student_id);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String data = rs.getString("school_year_id") + "-"
                            + rs.getString("good_ticket") + "-"
                            + rs.getString("week");
                    list.add(data);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
