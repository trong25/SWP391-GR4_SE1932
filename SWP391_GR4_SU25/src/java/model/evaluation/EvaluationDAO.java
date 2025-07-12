package model.evaluation;

import model.day.Day;
import model.day.DayDAO;
import model.student.Student;
import model.student.StudentDAO;
import model.personnel.PersonnelDAO;
import utils.DBContext;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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

    
    public Evaluation createEvaluation(Evaluation evaluation) {
        String sql = "INSERT INTO Evaluations (student_id, date_id, evaluation, notes) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, evaluation.getStudent().getId());
            preparedStatement.setString(2, evaluation.getDate().getId());
            preparedStatement.setString(3, evaluation.getEvaluation());
            preparedStatement.setString(4, evaluation.getNotes());
            
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    evaluation.setId(generatedKeys.getString(1));
                }
                return evaluation;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
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

}


