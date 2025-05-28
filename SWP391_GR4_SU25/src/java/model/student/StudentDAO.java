/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.personnel.Personnel;
import model.personnel.PersonnelDAO;
import utils.DBContext;

/**
 *
 * @author MSI
 */
public class StudentDAO extends DBContext {

    private Student createStudent(ResultSet resultSet) throws SQLException {
        try {
            PersonnelDAO personnelDAO = new PersonnelDAO();
            Student student = new Student();
            student.setId(resultSet.getString("id"));
            student.setUserId(resultSet.getString("user_id"));
            student.setFirstName(resultSet.getString("first_name"));
            student.setLastName(resultSet.getString("last_name"));
            student.setAddress(resultSet.getString("address"));
            student.setEmail(resultSet.getString("email"));
            student.setStatus(resultSet.getString("status"));
            student.setBirthday(resultSet.getDate("birthday"));
            student.setGender(resultSet.getBoolean("gender"));
            student.setFirstGuardianName(resultSet.getString("first_guardian_name"));
            student.setFirstGuardianPhoneNumber(resultSet.getString("first_guardian_phone_number"));
            student.setAvatar(resultSet.getString("avatar"));
            student.setSecondGuardianName(resultSet.getString("second_guardian_name"));
            student.setSecondGuardianPhoneNumber(resultSet.getString("second_guardian_phone_number"));
            Personnel personnel = personnelDAO.getPersonnel(resultSet.getString("created_by"));
            student.setCreatedBy(personnel);
            student.setParentSpecialNote(resultSet.getString("parent_special_note"));
            student.setSchoolName(resultSet.getString("School_name"));
            return student;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String generateId(String latestId) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(latestId);
        int number = 0;
        if (matcher.find()) {
            number = Integer.parseInt(matcher.group()) + 1;
        }
        DecimalFormat decimalFormat = new DecimalFormat("000000");
        String result = decimalFormat.format(number);
        return "HS" + result;
    }

    public Student getLatest() {
        String sql = "select TOP 1 * from Students order by id desc";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createStudent(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean createStudent(Student student) {
        String insertSql = """
        INSERT INTO [dbo].[Students]
        ([id], [user_id], [first_name], [last_name], [address], [email], [status],
         [birthday], [gender], [first_guardian_name], [first_guardian_phone_number], [avatar],
         [second_guardian_name], [second_guardian_phone_number], [created_by], [parent_special_note], [school_name])
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """;

        try (PreparedStatement stmt = connection.prepareStatement(insertSql)) {
            // Tạo ID mới
            String newId = "HS000001";
            Student latest = getLatest();
            if (latest != null) {
                newId = generateId(latest.getId());
            }

            // Thiết lập tham số
            stmt.setString(1, newId);
            stmt.setString(2, student.getUserId());
            stmt.setString(3, student.getFirstName());
            stmt.setString(4, student.getLastName());
            stmt.setString(5, student.getAddress());
            stmt.setString(6, student.getEmail());
            stmt.setString(7, student.getStatus());
            stmt.setDate(8, new java.sql.Date(student.getBirthday().getTime())); // dùng java.sql.Date thay vì format chuỗi
            stmt.setBoolean(9, student.getGender());
            stmt.setString(10, student.getFirstGuardianName());
            stmt.setString(11, student.getFirstGuardianPhoneNumber());
            stmt.setString(12, student.getAvatar());
            stmt.setString(13, student.getSecondGuardianName());
            stmt.setString(14, student.getSecondGuardianPhoneNumber());
            stmt.setString(15, student.getCreatedBy().getId());
            stmt.setString(16, student.getParentSpecialNote());
            stmt.setString(17, student.getSchoolName());

            // Thực thi
            int result = stmt.executeUpdate();
            return result == 1;

        } catch (SQLException e) {
            return false;
        }

    }
    public List<Student> getStudentByStatus(String status) {
        String sql = " Select * from Students where [status] = N'" + status + "' order by id desc";
        List<Student> listStudents = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student = createStudent(resultSet);
                listStudents.add(student);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listStudents;
    }
    public List<Student> getAllStudents() {
        String sql = "select * from Students order by id desc";
        List<Student> listStudent = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student = createStudent(resultSet);
                listStudent.add(student);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listStudent;
    }
    public Student getStudentByUserId(String userId) {
        String sql = "SELECT * FROM Students WHERE user_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Student student = new Student();
                student = createStudent(resultSet);
                return student;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public boolean checkFirstGuardianPhoneNumberExists(String phoneNumber) {
        String sql = "SELECT COUNT(*) FROM [Students] WHERE first_guardian_phone_number = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, phoneNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

   
    public boolean checkSecondGuardianPhoneNumberExists(String phoneNumber) {
        String sql = "SELECT COUNT(*) FROM [Students] WHERE second_guardian_phone_number = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, phoneNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

     public Student getStudentsById(String id) {
        String sql = "select * from Students where id='" + id + "'";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Student student = new Student();
                student = createStudent(resultSet);
                return student;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
       public boolean updateStudent(Student student) {
        String sql = "update dbo.[Students] set first_guardian_name=?, "
                + "first_guardian_phone_number=?, "
                + "second_guardian_name=?, "
                + "second_guardian_phone_number=?, "
                + "address=?,"
                +"school_name=?,"
                + " parent_special_note=?, "
                + "first_name=?, "
                + "last_name=?, "
                + "birthday=?, "
                + "email=?, "
                + "avatar=? "
                + "where id=?";
        
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, student.getFirstGuardianName());
            ps.setString(2, student.getFirstGuardianPhoneNumber());
            ps.setString(3, student.getSecondGuardianName());
            ps.setString(4, student.getSecondGuardianPhoneNumber());
            ps.setString(5, student.getAddress());
            ps.setString(6, student.getSchoolName());
            ps.setString(7, student.getParentSpecialNote());
           
            ps.setString(8,student.getFirstName());
            ps.setString(9, student.getLastName());
            ps.setDate(10, new java.sql.Date(student.getBirthday().getTime()));
            ps.setString(11, student.getEmail());
            ps.setString(12, student.getAvatar());
            ps.setString(13, student.getId());
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    
    public Student getStudentById(String id) {
    String sql = "SELECT * FROM Students WHERE id = ?";
    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return createStudent(rs);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}


}
