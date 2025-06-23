/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.subject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
public class SubjectDAO extends DBContext {

    private Subject createSubject(ResultSet resultSet) throws SQLException {
    GradeDAO gradeDAO = new GradeDAO();
    Subject subject = new Subject();
    
    subject.setId(resultSet.getString("id"));
    subject.setName(resultSet.getString("name"));

    Grade grade = gradeDAO.getGrade(resultSet.getString("grade_id"));
    subject.setGrade(grade);

    subject.setStatus(resultSet.getString("status"));
    subject.setDescription(resultSet.getString("description"));

    // Thêm dòng này để lấy subject_type từ DB
    subject.setSubjectType(resultSet.getString("subject_type"));

    return subject;
}


    public String createSubject(Subject subject) {
        String sql = "INSERT INTO [dbo].[Subjects] ([id], [name], [grade_id], [description], [status], [subject_type]) VALUES (?, ?, ?, ?, ?, ?)";

        if (checkSubjectExist(subject.getName(), subject.getGrade().getId())) {
            return "Tạo thất bại! Môn học đã tồn tại.";
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            String newId = (getLastest() == null) ? "S000001" : generateId(getLastest().getId());

            preparedStatement.setString(1, newId);
            preparedStatement.setString(2, subject.getName());
            preparedStatement.setString(3, subject.getGrade().getId());
            preparedStatement.setString(4, subject.getDescription());
            preparedStatement.setString(5, subject.getStatus());
            preparedStatement.setString(6, subject.getSubjectType());

            int rowsInserted = preparedStatement.executeUpdate();
            return (rowsInserted > 0) ? "success" : "Tạo thất bại! Không thể thêm môn học.";

        } catch (SQLException e) {
            e.printStackTrace();
            return "Tạo thất bại! Lỗi cơ sở dữ liệu: " + e.getMessage();
        }
    }

    public boolean checkSubjectExist(String name, String gradeId) {
        String sql = "select * from Subjects where [name] = ? and grade_id= ? and (status =N'đang chờ xử lý' or status=N'đã được duyệt')";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, gradeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public Subject getLastest() {
        String sql = "Select top 1 * from Subjects order by id desc";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createSubject(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
        return "S" + result;
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

    public List<Subject> getAll() {
        List<Subject> subjects = new ArrayList<>();
        String sql = "Select * from Subjects order by id desc";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Subject subject = createSubject(rs);
                subjects.add(subject);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subjects;
    }

    public List<Subject> getSubjectsByStatus(String status) {
        List<Subject> subjectList = new ArrayList<>();
        String sql = "select * from Subjects where status = ? order by id desc";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, status);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Subject subject = createSubject(resultSet);
                subjectList.add(subject);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return subjectList;
    }

    public String editSubject(Subject subject) {
        // Kiểm tra xem môn học đã tồn tại chưa (trừ chính nó)
        if (checkSubjectExist(subject.getName(), subject.getGrade().getId(), subject.getId())) {
            return "Chỉnh sửa thất bại! Môn học đã tồn tại.";
        }

        String sql = "UPDATE [Subjects] "
                + "SET name = ?, grade_id = ?, description = ?, status = ?, subject_type = ? "
                + "WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, subject.getName());
            preparedStatement.setString(2, subject.getGrade().getId());
            preparedStatement.setString(3, subject.getDescription());
            preparedStatement.setString(4, subject.getStatus());
            preparedStatement.setString(5, subject.getSubjectType());
            preparedStatement.setString(6, subject.getId());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                return "Không tìm thấy môn học để cập nhật.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Thao tác thất bại! Vui lòng thử lại sau.";
        }

        return "success";
    }

    private boolean checkSubjectExist(String name, String gradeId, String id) {
        String sql = "select * from Subjects where [name] = ? and grade_id= ? and (status =N'đang chờ xử lý' or status=N'đã được duyệt') and id != ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, gradeId);
            preparedStatement.setString(3, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
