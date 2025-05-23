/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.week;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.schoolYear.SchoolYearDAO;
import utils.DBContext;

/**
 *
 * @author MSI
 */
public class WeekDAO extends DBContext{
     private Week createWeek(ResultSet rs) throws SQLException {
        Week week = new Week();
        week.setId(rs.getString("id"));
        week.setStartDate(rs.getDate("start_date"));
        week.setEndDate(rs.getDate("end_date"));
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        week.setSchoolYear(schoolYearDAO.getSchoolYear(rs.getString("school_year_id")));
        return week;
    }
     public Week getWeek(String id) {
        String sql = "select * from Weeks where id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return createWeek(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
