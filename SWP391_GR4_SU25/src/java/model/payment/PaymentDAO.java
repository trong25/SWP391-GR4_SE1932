/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.payment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
// Lấy tổng doanh thu theo tháng hiện tại

    public double getCurrentMonthRevenue() {
        // SQL Server syntax với Calendar để lấy tháng/năm hiện tại
        String sql = "SELECT SUM(amount) as total_revenue FROM Payments "
                + "WHERE month = ? AND year = ? AND status = 'paid'";

        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH) + 1; // Calendar.MONTH bắt đầu từ 0
        int currentYear = calendar.get(Calendar.YEAR);

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, currentMonth);
            stmt.setInt(2, currentYear);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    double result = rs.getDouble("total_revenue");
                    return rs.wasNull() ? 0.0 : result;
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi getCurrentMonthRevenue: " + e.getMessage());
            e.printStackTrace();
        }
        return 0.0;
    }

// Lấy danh sách doanh thu theo từng tháng trong năm
    public Map<Integer, Double> getMonthlyRevenue(int year) {
        Map<Integer, Double> monthlyRevenue = new HashMap<>();
        String sql = "SELECT month, SUM(amount) as total_revenue "
                + "FROM Payments "
                + "WHERE year = ? AND status = 'paid' "
                + "GROUP BY month "
                + "ORDER BY month";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, year);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int month = rs.getInt("month");
                    double revenue = rs.getDouble("total_revenue");
                    monthlyRevenue.put(month, revenue);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi getMonthlyRevenue: " + e.getMessage());
            e.printStackTrace();
        }
        return monthlyRevenue;
    }

    // lấy tổng doanh thu 
    public double getAllRevenue() {
        String sql = "SELECT SUM(amount) AS total FROM Payments WHERE status = 'paid'";

        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                double result = rs.getDouble("total");
                return rs.wasNull() ? 0.0 : result;
            }

        } catch (SQLException e) {
            System.out.println("Lỗi getTotalRevenue: " + e.getMessage());
            e.printStackTrace();
        }

        return 0.0;
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
