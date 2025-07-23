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
import model.student.Student;
import java.sql.Timestamp;
import utils.DBContext;

/**
 *
 * @author admin
 */
public class PaymentDAO extends DBContext {

    public Payment createPayment(ResultSet rs) throws SQLException {
        Payment payment = new Payment();
        Student student = new Student();
        payment.setId(rs.getInt("id"));
        student.setAvatar("avatar");
        payment.setCode(rs.getString("code"));
        payment.setStudentId(rs.getString("student_id"));
        payment.setClassId(rs.getString("class_id"));
        payment.setAmount(rs.getInt("amount"));
        payment.setStatus(rs.getString("status"));
        payment.setPaymentDate(rs.getDate("payment_date"));
        payment.setDueDate(rs.getDate("due_to"));
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

    public List<Payment> getPaymentsByStatus(String status) {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT * FROM Payments WHERE status = ? ORDER BY id DESC";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Payment payment = createPayment(rs);
                list.add(payment);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách Payments theo trạng thái: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    public List<StudentPaymentInfo> getStudentPaymentInfoByStatus(String status) {
        List<StudentPaymentInfo> list = new ArrayList<>();
        String sql = """
        SELECT 
            s.code AS payment_id,
            st.id AS student_id,
            st.first_name,
            st.last_name,
            st.email,
            st.first_guardian_phone_number,
            s.[month],
            s.due_to,
            s.amount,
            s.status,
            s.note                     
        FROM Students st
        JOIN Payments s ON s.student_id = st.id
        WHERE s.status = ?
        ORDER BY s.[month] DESC, st.last_name ASC
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                StudentPaymentInfo info = new StudentPaymentInfo();
                info.setId(rs.getString("payment_id"));
                info.setStudentId(rs.getString("student_id"));
                info.setFirstName(rs.getString("first_name"));
                info.setLastName(rs.getString("last_name"));
                info.setEmail(rs.getString("email"));
                info.setFirstGuardianPhoneNumber(rs.getString("first_guardian_phone_number"));
                info.setMonth(rs.getInt("month"));

                Timestamp dueToTimestamp = rs.getTimestamp("due_to");
                info.setDueTo(dueToTimestamp != null ? dueToTimestamp.toLocalDateTime() : null);

                info.setAmount(rs.getFloat("amount"));
                info.setStatus(rs.getString("status"));
                info.setNote(rs.getString("note"));

                list.add(info);
            }
        } catch (SQLException e) {
            System.err.println("Error in getStudentPaymentInfoByStatus: " + e.getMessage());
            throw new RuntimeException("Error fetching filtered student payment info", e);
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
            ps.setDate(8, new java.sql.Date(payment.getDueDate().getTime())); //  dùng giá trị từ form

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
            s.code as payment_id,        -- ID của payment (cần cho servlet)
            st.id as student_id,         -- ID của student  
            st.first_name,
            st.last_name,
            st.email,
            st.first_guardian_phone_number,
            s.[month],
            s.due_to,
            s.amount,
            s.status,
            s.note                     
        FROM Students st
        JOIN Payments s ON s.student_id = st.id
        ORDER BY s.[month] DESC, st.last_name ASC
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                StudentPaymentInfo info = new StudentPaymentInfo();
                info.setId(rs.getString("payment_id"));
                info.setStudentId(rs.getString("student_id"));
                info.setFirstName(rs.getString("first_name"));
                info.setLastName(rs.getString("last_name"));
                info.setEmail(rs.getString("email"));
                info.setFirstGuardianPhoneNumber(rs.getString("first_guardian_phone_number"));
                info.setMonth(rs.getInt("month"));

                // Xử lý due_to timestamp
                Timestamp dueToTimestamp = rs.getTimestamp("due_to");
                info.setDueTo(dueToTimestamp != null ? dueToTimestamp.toLocalDateTime() : null);

                info.setAmount(rs.getFloat("amount"));
                info.setStatus(rs.getString("status"));

                // QUAN TRỌNG: Bỏ comment dòng này
                info.setNote(rs.getString("note"));

                list.add(info);
            }
        } catch (SQLException e) {
            System.err.println("Error in getStudentPaymentInfoList: " + e.getMessage());
            throw new RuntimeException("Error fetching student payment info", e);
        }
        return list;
    }

    public boolean updatePaymentStatus(String paymentId, String status) {
        String sql = "UPDATE Payments SET status = ?, payment_date = ? WHERE code = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            if ("paid".equals(status)) {
                ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            } else {
                ps.setNull(2, java.sql.Types.TIMESTAMP);
            }
            ps.setString(3, paymentId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating payment status: " + e.getMessage());
            return false;
        }
    }

    public boolean updatePaymentNote(String paymentId, String note) {
        String sql = "UPDATE Payments SET note = ? WHERE code = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, note);
            ps.setString(2, paymentId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating payment note: " + e.getMessage());
            return false;
        }
    }

}
