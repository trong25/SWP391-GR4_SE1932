/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.payment;

import java.sql.Types;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.student.Student;
import utils.DBContext;

/**
 *
 * @author admin
 */
public class PaymentDAO extends DBContext {

     public Payment createPayment(ResultSet resultSet) throws SQLException {
        Payment payment = new Payment();
        payment.setId(resultSet.getInt("id"));
        payment.setCode(resultSet.getString("code"));
        payment.setStudentId(resultSet.getString("student_id"));
        payment.setClassId(resultSet.getString("class_id"));
        payment.setMonth(resultSet.getInt("month"));
        payment.setYear(resultSet.getInt("year"));
        payment.setAmount(resultSet.getFloat("amount"));
        payment.setStatus(resultSet.getString("status"));
        payment.setDueTo(resultSet.getDate("due_to"));
        payment.setPaymentDate(resultSet.getDate("payment_date"));
        payment.setNote(resultSet.getString("note"));
        return payment;
    }

    public Payment getById(Integer id) {
        String sql = """
                     select * from Payments where id = ?
                     """;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return createPayment(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Payment getByCode(String code) {
        String sql = """
                     select * from Payments where code = ?
                     """;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, code);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return createPayment(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Payment> getListPaymentByStudentId(String studentId) {
        String sql = """
                     select * from Payments where student_id = ?
                     """;
        List<Payment> list = new ArrayList<>();
        try {

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, studentId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                list.add(createPayment(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertPayment(Payment payment) {
        String sql = "INSERT INTO Payments (code, student_id, class_id, month, year, amount, note, due_to) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, payment.getCode());
            ps.setString(2, payment.getStudentId());
            ps.setString(3, payment.getClassId());
            ps.setInt(4, payment.getMonth());
            ps.setInt(5, payment.getYear());
            ps.setFloat(6, payment.getAmount());
            ps.setString(7, payment.getNote());
            ps.setDate(8, new java.sql.Date(payment.getDueTo().getTime())); //  dùng giá trị từ form

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // kiểm tra log lỗi cụ thể
            return false;
        }
    }

    public boolean isPaymentExists(String studentId, String classId, int month, int year) {
        String sql = "SELECT COUNT(*) FROM Payments WHERE student_id = ? AND class_id = ? AND month = ? AND year = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, studentId);
            ps.setString(2, classId);
            ps.setInt(3, month);
            ps.setInt(4, year);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateStatus(Integer id, String status) {
        String sql = """
                     update Payments set status = ?, payment_date = CURRENT_TIMESTAMP 
                     where id = ?
                     """;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, status);
            statement.setObject(2, id);
            int rowEf = statement.executeUpdate();
            return rowEf > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Payment> getALL() {
        String sql = " SELECT * FROM Payments ";
        List<Payment> listPayments = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Payment payment = createPayment(resultSet);
                listPayments.add(payment);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listPayments;
    }

    public List<StudentPaymentInfo> getStudentPaymentInfoList() {
        List<StudentPaymentInfo> list = new ArrayList<>();
        String sql = """
        SELECT 
            st.id,
            st.avatar,
            st.first_name,
            st.last_name,
            st.email,
            st.first_guardian_name,
            st.first_guardian_phone_number,
            s.[month],
            s.amount,
            s.status
        FROM Students st
        JOIN Payments s ON s.student_id = st.id
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                StudentPaymentInfo info = new StudentPaymentInfo();
                info.setStudentId(rs.getString("id"));
                info.setAvatar(rs.getString("avatar"));
                info.setFirstName(rs.getString("first_name"));
                info.setLastName(rs.getString("last_name"));
                info.setEmail(rs.getString("email"));
                info.setFirstGuardianName(rs.getString("first_guardian_name"));
                info.setFirstGuardianPhoneNumber(rs.getString("first_guardian_phone_number"));
                info.setMonth(rs.getInt("month"));
                info.setAmount(rs.getFloat("amount"));
                info.setStatus(rs.getString("status"));
                list.add(info);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching student payment info", e);
        }

        return list;
    }

    public boolean updatePaymentStatus(int paymentId, String newStatus) {
        String sql = "UPDATE Payment SET status = ? WHERE payment_id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            ps.setString(1, newStatus);
            ps.setInt(2, paymentId);

            int rowsAffected = ps.executeUpdate();
            System.out.println("Update payment status - Rows affected: " + rowsAffected);

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating payment status: " + e.getMessage());
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            System.err.println("Unexpected error updating payment status: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePaymentNote(int paymentId, String note) {
        String sql = "UPDATE Payment SET note = ? WHERE payment_id = ?";
        try ( PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, note);
            ps.setInt(2, paymentId);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating payment note: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

}
