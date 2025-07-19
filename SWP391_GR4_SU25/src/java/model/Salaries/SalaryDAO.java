/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Salaries;

import utils.DBContext;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author MSI
 */
public class SalaryDAO extends DBContext{
   public boolean checkExistingSalary(String personnelId, int month, int year) {
        String sql = "SELECT COUNT(*) FROM Salaries WHERE personnel_id = ? AND salary_month = ? AND salary_year = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, personnelId);
            stmt.setInt(2, month);
            stmt.setInt(3, year);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi kiểm tra bảng lương: " + e.getMessage());
        }
        return false;
    }

    public void insertSalary(Salary salary) {
        String sql = """
            INSERT INTO Salaries (personnel_id, salary_month, salary_year, base_salary, total_salary, payment_status, payment_date)
            VALUES (?, ?, ?, ?, ?, ?, ?)
        """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, salary.getPersonnelId());
            stmt.setInt(2, salary.getSalaryMonth());
            stmt.setInt(3, salary.getSalaryYear());
            stmt.setFloat(4, salary.getBaseSalary());
            stmt.setFloat(5, salary.getTotalSalary());
            stmt.setString(6, salary.getPaymentStatus());
            if (salary.getPaymentDate() != null) {
                stmt.setDate(7, new java.sql.Date(salary.getPaymentDate().getTime()));
            } else {
                stmt.setNull(7, java.sql.Types.DATE);
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi thêm bảng lương: " + e.getMessage());
        }
    }



}
