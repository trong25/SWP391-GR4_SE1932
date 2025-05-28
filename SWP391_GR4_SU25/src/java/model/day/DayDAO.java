/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.day;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.week.WeekDAO;
import java.sql.Date;
import utils.DBContext;

/**
 *
 * @author MSI
 */
public class DayDAO extends DBContext{
    private Day createDay(ResultSet resultSet) throws SQLException {
        Day day = new Day();
        day.setId(resultSet.getString("id"));
        WeekDAO weekDAO = new WeekDAO();
        day.setWeek(weekDAO.getWeek(resultSet.getString("week_id")));
        day.setDate(resultSet.getDate("date"));
        return day;
    } 
    public Day getDayByID(String dateId) {
        String sql = "SELECT * FROM Days WHERE id = ?";
        Day day = null;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, dateId);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                day = new Day();
                day.setId(rs.getString("id"));
                WeekDAO weekDAO = new WeekDAO();
                day.setWeek(weekDAO.getWeek(rs.getString("week_id")));
                day.setDate(rs.getDate("date"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;
    }
     public Day getDayByDate(String date) {
        String sql = "select * from days where date = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, date);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createDay(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


public String getDateIDbyDay(java.util.Date day) {
    String sql = "SELECT id FROM Days WHERE date = ?";
    try {
        PreparedStatement statement = connection.prepareStatement(sql);
        // Chuyển java.util.Date sang java.sql.Date để set vào PreparedStatement
        statement.setDate(1, new java.sql.Date(day.getTime()));
        ResultSet rs = statement.executeQuery();
        if (rs.next()) {
            return rs.getString("id");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null; // Không tìm thấy id cho ngày đó
}



      public List<Day> getDayByWeek(String weekId) {
        List<Day> days = new ArrayList<>();
        String sql = "SELECT id, week_id, date FROM Days WHERE week_id = ? AND DATEPART(WEEKDAY, date) BETWEEN 2 AND 6";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, weekId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Day day = new Day();
                day.setId(rs.getString("id"));
                WeekDAO weekDAO = new WeekDAO();
                day.setWeek(weekDAO.getWeek(rs.getString("week_id")));
                day.setDate(rs.getDate("date"));
                days.add(day);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return days;
    }

}
