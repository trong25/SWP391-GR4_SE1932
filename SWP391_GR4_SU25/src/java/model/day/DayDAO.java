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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.week.Week;
import utils.DBContext;

import utils.Helper;
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
    private Day getLatest() {
        String sql = "SELECT TOP 1 * FROM Days ORDER BY ID DESC";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Day day = new Day();
                day.setId(rs.getString("id"));
                WeekDAO weekDAO = new WeekDAO();
                day.setWeek(weekDAO.getWeek(rs.getString("week_id")));
                day.setDate(rs.getDate("date"));
                return day;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String generateId(String latestId) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(latestId);
        int number = 0;
        if (matcher.find()) {
            number = Integer.parseInt(matcher.group()) + 1;
        }
        DecimalFormat decimalFormat = new DecimalFormat("000000");
        String result = decimalFormat.format(number);
        return "D" + result;
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
        String sql = "SELECT * FROM Days WHERE week_id = ? ORDER BY day_of_week";
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
     
         public List<Day> getDaysWithTimetableForClass(String weekId, String classId) {
        List<Day> days = new ArrayList<>();
        String sql = "SELECT DISTINCT d.*\n"
                + "FROM Days d\n"
                + "JOIN Timetables t ON d.id = t.date_id\n"
                + "WHERE d.week_id = ? AND t.class_id = ? AND t.status = N'đã được duyệt'\n"
                + "ORDER BY d.date ASC";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, weekId);
            preparedStatement.setString(2, classId);
            
            // Debug logs
            System.out.println("Debug - Getting days for week: " + weekId);
            System.out.println("Debug - Class ID: " + classId);
            System.out.println("Debug - SQL: " + sql);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Day day = createDay(resultSet);
                days.add(day);
                System.out.println("Debug - Found day: " + day.getId() + " - " + day.getDate());
            }
            
            // Debug log for results
            System.out.println("Debug - Total days found: " + days.size());
            
        } catch (Exception e) {
            System.out.println("Debug - Error in getDaysWithTimetableForClass: " + e.getMessage());
            e.printStackTrace();
        }
        return days;
    }


  

   
    public void generateDays(List<Week> weeks) {
        try {
            StringBuilder sql = new StringBuilder("insert into Days values ");
            String newDayId = "";
            if (getLatest() != null) {
                newDayId = generateId(Objects.requireNonNull(getLatest()).getId());
            } else {
                newDayId = "D000001";
            }
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (Week week : weeks) {
                LocalDate currentDate = Helper.convertDateToLocalDate(week.getStartDate());
                LocalDate endDate = Helper.convertDateToLocalDate(week.getEndDate());
                while (!currentDate.isAfter(endDate)) {
                    sql.append("('").append(newDayId).append("','").append(week.getId()).append("','")
                            .append(dateFormat.format(Helper.convertLocalDateToDate(currentDate))).append("'),");
                    newDayId = generateId(newDayId);
                    currentDate = currentDate.plusDays(1);
                }
            }
            sql.deleteCharAt(sql.length() - 1);

            PreparedStatement statement = connection.prepareStatement(sql.toString());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   public List<Day> getDaysInFutureWithTimetableForClass(String classId) {
        List<Day> days = new ArrayList<>();
        String sql = "SELECT DISTINCT d.*\n" +
                "                FROM Days d\n" +
                "                JOIN Timetables t ON d.id = t.date_id\n" +
                "                WHERE d.date > GETDATE() and class_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, classId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                days.add(createDay(resultSet));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return days;
    }
 public static void main(String[] args) {
        // Tạo DAO
        DayDAO dao = new DayDAO();

        // Nhập classId bạn muốn test (có dữ liệu)
        String classId = "C000001"; // thay bằng mã lớp thật

        // Gọi hàm
        List<Day> days = dao.getDaysInFutureWithTimetableForClass(classId);

        // In ra kết quả
        if (days.isEmpty()) {
            System.out.println("Không có ngày nào phù hợp.");
        } else {
            for (Day d : days) {
                System.out.println("ID: " + d.getId());
                System.out.println("Date: " + d.getDate());
                System.out.println("Week ID: " + (d.getWeek() != null ? d.getWeek().getId() : "null"));
                System.out.println("-----------");
            }
        }
    }

    }     


