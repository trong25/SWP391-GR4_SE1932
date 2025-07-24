package model.evaluation;

import model.day.Day;
import model.day.DayDAO;
import model.student.Student;
import model.student.StudentDAO;
import utils.DBContext;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
        // Sử dụng date_id thay cho timetable_id
//        Timetable timetable = timetableDAO.getTimetableByDateId(resultSet.getString("date_id"));
//        evaluation.setTimetable(timetable);
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
            e.printStackTrace();
        }
        return null;
    }

    public List<Evaluation> getEvaluationByWeekandStudentId(String weekId, String student_id) {
        List<Evaluation> list = new ArrayList<>();
        String sql = """
                   -- Lấy danh sách evaluation của một học sinh trong một tuần (theo tiết học)
                   SELECT 
                       e.id, 
                       e.student_id, 
                       e.date_id, 
                       e.evaluation, 
                       e.notes  
                   FROM [Evaluations] e 
                       JOIN [Days] d ON e.date_id = d.id
                       JOIN [Weeks] w ON d.week_id = w.id
                   WHERE 
                       d.week_id = ?           -- Tham số 1: week_id
                       AND e.student_id = ?;   -- Tham số 2: student_id
                   """;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, weekId);
            preparedStatement.setString(2, student_id);
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
                         JOIN [Days] d ON e.date_id = d.id
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
                JOIN Days d ON e.date_id = d.id
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
    
    
            public List<Evaluation> getEvaluationByWeek(String weekId) {
        List<Evaluation> list = new ArrayList<>();
        String sql = "select e.id, e.student_id,e.date_id,e.evaluation,e.notes  from Evaluations e join Days d on e.date_id = d.id\n"
                + "join Weeks w on d.week_id = w.id\n"
                + "where week_id= ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, weekId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                list.add(createEvaluation(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
            
                    public boolean checkEvaluationExist(String studentId, String dateId) {
        String sql = "select * from Evaluations where student_id=? and date_id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, studentId);
            preparedStatement.setString(2, dateId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
                    
                                public boolean updateEvaluationByStudentAndDay(Evaluation evaluation) {
        String sql = "update Evaluations set evaluation = ? where  student_id= ? and date_id= ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, evaluation.getEvaluation());
            preparedStatement.setString(2, evaluation.getStudent().getId());
            preparedStatement.setString(3, evaluation.getDate().getId());
            int row = preparedStatement.executeUpdate();
            if (row == 1) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
                                
                                                public boolean updateNoteByStudentAndDay(Evaluation evaluation) {
        String sql = "update Evaluations set notes = ? where  student_id= ? and date_id= ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, evaluation.getNotes());
            preparedStatement.setString(2, evaluation.getStudent().getId());
            preparedStatement.setString(3, evaluation.getDate().getId());
            int row = preparedStatement.executeUpdate();
            if (row == 1) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    
        public boolean createEvaluation(Evaluation evaluation) {
        EvaluationDAO evaluationDAO = new EvaluationDAO();
        String sql = "insert into Evaluations values (?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            if (getLatest() == null) {
                preparedStatement.setString(1, "EVA000001");
            } else {
                preparedStatement.setString(1, generateId(evaluationDAO.getLatest().getId()));
            }
            preparedStatement.setString(2, evaluation.getStudent().getId());
            preparedStatement.setString(3, evaluation.getDate().getId());
            preparedStatement.setString(4, evaluation.getEvaluation());
            preparedStatement.setString(5, evaluation.getNotes());
            int row = preparedStatement.executeUpdate();
            if (row == 1) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
        
    // Generate new evaluation ID
        private String generateEvaluationId(String latestId) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\\d+");
        java.util.regex.Matcher matcher = pattern.matcher(latestId);
        int number = 0;
        if (matcher.find()) {
            number = Integer.parseInt(matcher.group()) + 1;
        }
        java.text.DecimalFormat decimalFormat = new java.text.DecimalFormat("000000");
        String result = decimalFormat.format(number);
        return "E" + result;
    }
    
       private Evaluation getLatest() {
        String sql = "SELECT TOP 1 * FROM Evaluations ORDER BY ID DESC";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return createEvaluation(rs);
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
        String result = decimalFormat.format(number);
        return "EVA" + result;
    }



}
