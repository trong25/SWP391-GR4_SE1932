/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package StudentWait;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import utils.DBContext;

/**
 *
 * @author MSI
 */
public class StudentWaitDAO extends DBContext {

    private StudentWait createStudentWait(ResultSet resultSet) throws SQLException {
        try {
            StudentWait studentWait = new StudentWait();
            studentWait.setId(resultSet.getString("id"));
            studentWait.setFirstName(resultSet.getString("first_name"));
            studentWait.setLastName(resultSet.getString("last_name"));
            studentWait.setAddress(resultSet.getString("address"));
            studentWait.setEmail(resultSet.getString("email"));
            studentWait.setBirthday(resultSet.getDate("birthday"));
            studentWait.setGender(resultSet.getBoolean("gender"));
            studentWait.setPhoneNumber(resultSet.getString("phone_number"));
            return studentWait;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<StudentWait> getAllStudentWait() {
        List<StudentWait> studentList = new ArrayList<>();
        String sql = "SELECT * FROM StudentsCanCare ORDER BY id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql); 
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                StudentWait student = createStudentWait(resultSet);
                if (student != null) {
                    studentList.add(student);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return studentList;
    }

    public boolean createStudentWait(StudentWait student) {
        StudentWaitDAO studentWaitDAO = new StudentWaitDAO();
        String sql = "INSERT INTO [dbo].[StudentsCanCare] " +
                "([id], [first_name], [last_name], [address], [email], [birthday], [gender], [phone_number]) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try  {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            // Sinh ID tự động
            String id = "STU" + UUID.randomUUID().toString().substring(0, 7);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, student.getFirstName());
            preparedStatement.setString(3, student.getLastName());
            preparedStatement.setString(4, student.getAddress());
            preparedStatement.setString(5, student.getEmail());
            java.sql.Date sqlBirthday = new java.sql.Date(student.getBirthday().getTime());
            preparedStatement.setDate(6, sqlBirthday);
            preparedStatement.setInt(7, student.getGender() ? 1 : 0);
            preparedStatement.setString(8, student.getPhoneNumber());
            preparedStatement.executeUpdate();
            student.setId(id); // Cập nhật ID cho đối tượng
            return true;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    
     public boolean checkPhoneNumberExists(String phoneNumber) {
        String sql = "SELECT COUNT(*) FROM [StudentsCanCare] WHERE phone_number = ?";
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
}