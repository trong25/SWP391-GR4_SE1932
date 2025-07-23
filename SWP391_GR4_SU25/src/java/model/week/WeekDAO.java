/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.week;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.day.DayDAO;
import model.schoolYear.SchoolYear;
import model.schoolYear.SchoolYearDAO;
import utils.DBContext;
import utils.Helper;

/**
 *
 * @author MSI
 */
public class WeekDAO extends DBContext {

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

    public void generateWeeks(SchoolYear schoolYear) {
        try {
            Date startDate = schoolYear.getStartDate();
            Date endDate = schoolYear.getEndDate();
            LocalDate schoolYearStartDate = Helper.convertDateToLocalDate(startDate);
            LocalDate schoolYearEndDate = Helper.convertDateToLocalDate(endDate);
            LocalDate currentStartDate = schoolYearStartDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            StringBuilder sql = new StringBuilder("insert into Weeks values ");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String newWeekId = "";
            if (getLatest() != null) {
                newWeekId = generateId(Objects.requireNonNull(getLatest()).getId());
            } else {
                newWeekId = "W000001";
            }
            ArrayList<Week> weekList = new ArrayList<>();
            while (!currentStartDate.isAfter(schoolYearEndDate)) {
                LocalDate currentEndDate = currentStartDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
                if (currentEndDate.isAfter(schoolYearEndDate)) {
currentEndDate = schoolYearEndDate;
                }
                weekList.add(new Week(Helper.convertLocalDateToDate(currentStartDate),
                        newWeekId, Helper.convertLocalDateToDate(currentEndDate), schoolYear));
                sql.append("('").append(newWeekId).append("','").
                        append(dateFormat.format(Helper.convertLocalDateToDate(currentStartDate))).
                        append("','").append(dateFormat.format(Helper.convertLocalDateToDate(currentEndDate))).
                        append("','").append(schoolYear.getId()).append("')").append(",");
                newWeekId = generateId(newWeekId);
                currentStartDate = currentStartDate.plusWeeks(1);
            }
            sql.deleteCharAt(sql.length() - 1);

            PreparedStatement statement = connection.prepareStatement(sql.toString());
            statement.executeUpdate();
            DayDAO dayDAO = new DayDAO();
            dayDAO.generateDays(weekList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Week getLatest() {
        String sql = "SELECT TOP 1 * FROM Weeks ORDER BY ID DESC";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return createWeek(rs);
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
        return "W" + result;
    }

    public String getCurrentWeek(Date date) {
        String sql = "SELECT id FROM Weeks WHERE ? BETWEEN start_date AND end_date";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDate(1, new java.sql.Date(date.getTime()));
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

    public Week getfirstWeekOfClosestSchoolYear(String id) {
        Week week = new Week();
        String sql = " select top 1 w.* from Weeks w join SchoolYears sy on w.school_year_id = sy.id where school_year_id = ? order by id ASC";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                week = createWeek(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return week;
    }

    public List<Week> getAll() {
        String sql = """
                     select * from Weeks
                     """;
        List<Week> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                list.add(createWeek(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public String getYearByWeek(String id) {
        String sql = "SELECT [school_year_id] FROM [dbo].[Weeks] WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getString("school_year_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



         
             public Week getLastWeek(String schoolYearId){
        String sql = "select top 1 * from [Weeks] where school_year_id = ? order by id desc";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, schoolYearId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return createWeek(resultSet);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    
    public boolean checkWeekInSchoolYear(String week_id,String year_id){
        String sql = "select * from Weeks where id = ? and school_year_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, week_id);
            preparedStatement.setString(2, year_id);


            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

public Week getLastWeekOfClosestSchoolYearOfStudent(String id){
        Week week = new Week();
        String sql = " select top 1 w.* from classDetails cd join dbo.Class C on cd.class_id = C.id\n" +
                "join dbo.SchoolYears SY on C.school_year_id = SY.id\n" +
                "join dbo.Weeks W on SY.id = W.school_year_id\n" +
                " where SY.end_date <= CAST(GETDATE() AS DATE) and cd.student_id =? order by w.end_date desc";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                week = createWeek(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return week;
    }
}
