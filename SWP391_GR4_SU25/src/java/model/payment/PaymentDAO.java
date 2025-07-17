/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.payment;

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
