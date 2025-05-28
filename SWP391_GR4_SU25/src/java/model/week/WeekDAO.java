/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.week;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Date;

import java.util.ArrayList;
import java.util.List;

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

     public Week getCurrentWeek(Date date) {
    String sql = "SELECT * FROM Weeks WHERE ? BETWEEN start_date AND end_date";
    try {
        PreparedStatement statement = connection.prepareStatement(sql);
        java.sql.Date today = new java.sql.Date(System.currentTimeMillis());
        statement.setDate(1, today);
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            return createWeek(rs);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}

      public List<Week> getWeeks(String schoolYearId) {
        List<Week> weeks = new ArrayList<>();
        String sql = "SELECT * FROM weeks WHERE school_year_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, schoolYearId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Week week = createWeek(rs);
                weeks.add(week);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weeks;
    }


}
