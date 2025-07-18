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
import utils.DBContext;

/**
 *
 * @author admin
 */
public class PaymentDAO extends DBContext {

    public Payment createPayment(ResultSet rs) throws SQLException {
        Payment payment = new Payment();
        payment.setId(rs.getInt("id"));
        payment.setCode(rs.getString("code"));
        payment.setStudentId(rs.getString("student_id"));
        payment.setClassId(rs.getString("class_id"));
        payment.setDayId(rs.getString("day_id"));
        payment.setAmount(rs.getInt("amount"));
        payment.setStatus(rs.getString("status"));
        payment.setPaymentDate(rs.getDate("payment_date"));
        payment.setDueDate(rs.getDate("due_date"));
        payment.setNote(rs.getString("note"));
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

    public void insertPayment(Payment payment) {
        String sql = "INSERT INTO Payments (code, student_id, class_id, day_id, amount, status, payment_date, due_date, note) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, payment.getCode());
            ps.setString(2, payment.getStudentId());
            ps.setString(3, payment.getClassId());
            ps.setString(4, payment.getDayId());
            ps.setFloat(5, payment.getAmount());
            ps.setString(6, payment.getStatus());
            if (payment.getPaymentDate() != null) {
                ps.setDate(7, new java.sql.Date(payment.getPaymentDate().getTime()));
            } else {
                ps.setNull(7, Types.DATE);
            }
            if (payment.getDueDate() != null) {
                ps.setDate(8, new java.sql.Date(payment.getDueDate().getTime()));
            } else {
                ps.setNull(8, Types.DATE);
            }
            ps.setString(9, payment.getNote());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
}
