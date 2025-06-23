package model.day;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.week.Week;
import model.week.WeekDAO;
import utils.DBContext;
import utils.Helper;

public class DayDAO extends DBContext {

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
                day = createDay(rs);
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
        String sql = "SELECT * FROM Days WHERE date = ?";

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
            statement.setDate(1, new java.sql.Date(day.getTime()));
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getString("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Day> getDayByWeek(String weekId) {
        List<Day> days = new ArrayList<>();
        String sql = "SELECT * FROM Days WHERE week_id = ? ORDER BY day_of_week";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, weekId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                days.add(createDay(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return days;
    }

    public List<Day> getDaysWithTimetableForClass(String weekId, String classId) {
        List<Day> days = new ArrayList<>();
        String sql = "SELECT DISTINCT d.* FROM Days d " +
                     "JOIN Timetables t ON d.id = t.date_id " +
                     "WHERE d.week_id = ? AND t.class_id = ? AND t.status = N'đã được duyệt' " +
                     "ORDER BY d.date ASC";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, weekId);
            preparedStatement.setString(2, classId);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                days.add(createDay(resultSet));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return days;
    }


    public void generateDays(List<Week> weeks) {
        try {
            StringBuilder sql = new StringBuilder("INSERT INTO Days VALUES ");
            String newDayId = (getLatest() != null)
                    ? generateId(Objects.requireNonNull(getLatest()).getId())
                    : "D000001";

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (Week week : weeks) {
                LocalDate currentDate = Helper.convertDateToLocalDate(week.getStartDate());
                LocalDate endDate = Helper.convertDateToLocalDate(week.getEndDate());
                while (!currentDate.isAfter(endDate)) {
                    sql.append("('").append(newDayId).append("','")
                            .append(week.getId()).append("','")
                            .append(dateFormat.format(Helper.convertLocalDateToDate(currentDate)))
                            .append("'),");
                    newDayId = generateId(newDayId);
                    currentDate = currentDate.plusDays(1);
                }
            }
            sql.deleteCharAt(sql.length() - 1); // Xoá dấu phẩy cuối cùng

            PreparedStatement statement = connection.prepareStatement(sql.toString());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Day> getDaysInFutureWithTimetableForClass(String classId) {
        List<Day> days = new ArrayList<>();
        String sql = "SELECT DISTINCT d.* FROM Days d " +
                     "JOIN Timetables t ON d.id = t.date_id " +
                     "WHERE d.date > GETDATE() AND t.class_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, classId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                days.add(createDay(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return days;
    }

    public static void main(String[] args) {
        DayDAO dao = new DayDAO();
        String classId = "C000001"; // Mã lớp test
        List<Day> days = dao.getDaysInFutureWithTimetableForClass(classId);
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
