package model.evaluation;

import model.day.Day;
import model.day.DayDAO;
import model.student.Student;
import model.student.StudentDAO;
import model.personnel.PersonnelDAO;
import utils.DBContext;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    
        public SchoolYearSummarize getSchoolYearSummarize(String studentId, String schoolYearId) {
        String sql = "select * from [SchoolYearSummarizeReport] where student_id = ? and schoolyear_id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, studentId);
            statement.setString(2, schoolYearId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                SchoolYearSummarize schoolYearSummarize = new SchoolYearSummarize();
                StudentDAO studentDAO = new StudentDAO();
                PersonnelDAO personnelDAO = new PersonnelDAO();
                schoolYearSummarize.setStudent(studentDAO.getStudentsById(resultSet.getString("student_id")));
                schoolYearSummarize.setSchoolYearId(resultSet.getString("schoolyear_id"));
                schoolYearSummarize.setTitle(resultSet.getString("title"));
                schoolYearSummarize.setGoodTicket(resultSet.getString("good_ticket"));
                schoolYearSummarize.setTeacherNote(resultSet.getString("teacher_note"));
                schoolYearSummarize.setTeacher(personnelDAO.getPersonnel(resultSet.getString("teacher_id")));
                return schoolYearSummarize;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    
        public String updateSchoolYearSummarize(SchoolYearSummarize schoolYearSummarize) {
        String res;
        if (getSchoolYearSummarize(schoolYearSummarize.getStudent().getId(), schoolYearSummarize.getSchoolYearId())!=null){
            res = editSchoolYearSummarize(schoolYearSummarize);
        } else {
            res = addSchoolYearSummarize(schoolYearSummarize);
        }
        return res;
    }

    private String addSchoolYearSummarize(SchoolYearSummarize schoolYearSummarize){
        String sql = "insert into [SchoolYearSummarizeReport] values (?, ?, ?, ?, ? ,?)";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, schoolYearSummarize.getStudent().getId());
            statement.setString(2, schoolYearSummarize.getSchoolYearId());
            statement.setString(3, schoolYearSummarize.getTeacher().getId());
            statement.setString(4, schoolYearSummarize.getGoodTicket());
            statement.setString(5, schoolYearSummarize.getTitle());
            statement.setString(6, schoolYearSummarize.getTeacherNote());
            statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
            return "Thao tác thất bại!";
        }
        return "success";
    }

    private String editSchoolYearSummarize(SchoolYearSummarize schoolYearSummarize){
        String sql = "update [SchoolYearSummarizeReport] set title = ?, teacher_note = ? where student_id = ? and schoolyear_id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, schoolYearSummarize.getTitle());
            statement.setString(2, schoolYearSummarize.getTeacherNote());
            statement.setString(3, schoolYearSummarize.getStudent().getId());
            statement.setString(4, schoolYearSummarize.getSchoolYearId());
            statement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
            return "Thao tác thất bại!";
        }
        return "success";
    }

    // Method to calculate average score by week for a student
    public double getAverageScoreByWeek(String studentId, String weekId) {
        String sql = "SELECT AVG(CAST(e.evaluation AS FLOAT)) as avgScore " +
                     "FROM Evaluations e " +
                     "JOIN Days d ON e.date_id = d.id " +
                     "JOIN Weeks w ON d.week_id = w.id " +
                     "WHERE e.student_id = ? AND w.id = ? " +
                     "AND e.evaluation NOT IN ('Nghỉ học', '0') " +
                     "AND ISNUMERIC(e.evaluation) = 1";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, studentId);
            statement.setString(2, weekId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                double avg = resultSet.getDouble("avgScore");
                return Math.round(avg * 10.0) / 10.0; // Round to 1 decimal place
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // Updated method to count status (including numeric scores)
    public int getNumberOfStatus(String status, String studentId, String weekId) {
        String sql;
        if (status.equals("Nghỉ học")) {
            sql = "SELECT COUNT(*) as count " +
                  "FROM Evaluations e " +
                  "JOIN Days d ON e.date_id = d.id " +
                  "JOIN Weeks w ON d.week_id = w.id " +
                  "WHERE e.student_id = ? AND w.id = ? " +
                  "AND (e.evaluation = 'Nghỉ học' OR e.evaluation = '0')";
        } else {
            // For other statuses, count by score ranges
            sql = "SELECT COUNT(*) as count " +
                  "FROM Evaluations e " +
                  "JOIN Days d ON e.date_id = d.id " +
                  "JOIN Weeks w ON d.week_id = w.id " +
                  "WHERE e.student_id = ? AND w.id = ? " +
                  "AND e.evaluation = ?";
        }
        
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, studentId);
            statement.setString(2, weekId);
            if (!status.equals("Nghỉ học")) {
                statement.setString(3, status);
            }
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    // Method to get school year average score for a student
    public double getSchoolYearAverageScore(String studentId, String schoolYearId) {
        String sql = "SELECT AVG(CAST(e.evaluation AS FLOAT)) as avgScore " +
                     "FROM Evaluations e " +
                     "JOIN Days d ON e.date_id = d.id " +
                     "JOIN Weeks w ON d.week_id = w.id " +
                     "JOIN SchoolYears sy ON w.schoolYear_id = sy.id " +
                     "WHERE e.student_id = ? AND sy.id = ? " +
                     "AND e.evaluation NOT IN ('Nghỉ học', '0') " +
                     "AND ISNUMERIC(e.evaluation) = 1";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, studentId);
            statement.setString(2, schoolYearId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                double avg = resultSet.getDouble("avgScore");
                return Math.round(avg * 10.0) / 10.0; // Round to 1 decimal place
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    // Get latest evaluation ID for ID generation
    private String getLatestEvaluationId() {
        String sql = "SELECT TOP 1 id FROM Evaluations ORDER BY id DESC";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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


