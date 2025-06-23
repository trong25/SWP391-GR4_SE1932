/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.subject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.grade.Grade;
import model.grade.GradeDAO;
import utils.DBContext;

/**
Lớp SubjectDAO chịu trách nhiệm thao tác dữ liệu với bảng SubjectDAO trong Database
 * Lấy dữ liệu từ database liên quan đến bảng SubjectDAO
 * Thức hiên các chức năng như tạo môn học, lấy môn học qua id, cập nhật và chỉnh sửa môn học,
 * Ví dụ: createSubject(Subject subject),getSubjectBySubjectId(String subjectId),
 * getSubjectsByStatus(String status), editSubject(Subject subject)
 * 
 * Sử dụng JDBC để kết nới với cơ sở dữ liệu SQL Server
 * @author TrongNV
 */
public class SubjectDAO extends DBContext{
     private Subject createSubject(ResultSet resultSet) throws SQLException{
        GradeDAO gradeDAO = new GradeDAO();
        Subject subject = new Subject();
        subject.setId(resultSet.getString("id"));
        subject.setName(resultSet.getString("name"));
        Grade grade = gradeDAO.getGrade(resultSet.getString("grade_id"));
        subject.setGrade(grade);
        subject.setStatus(resultSet.getString("status"));
        subject.setDescription(resultSet.getString("description"));
        return subject;
    }
      public Subject getSubjectBySubjectId(String subjectId) {
        String sql = "SELECT s.id AS subject_id, s.name AS subject_name, g.id AS grade_id, g.name AS grade_name, s.description "
                + "FROM Subjects s "
                + "JOIN Grades g ON s.grade_id = g.id "
                + "WHERE s.id = ?";
        Subject subject = null;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, subjectId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                subject = new Subject();
                subject.setId(rs.getString("subject_id"));
                subject.setName(rs.getString("subject_name"));

                Grade grade = new Grade();
                grade.setId(rs.getString("grade_id"));
                grade.setName(rs.getString("grade_name"));

                subject.setGrade(grade);
                subject.setDescription(rs.getString("description"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subject;
    }
}
