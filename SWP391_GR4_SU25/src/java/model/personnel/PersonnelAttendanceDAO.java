/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.personnel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.day.DayDAO;
import utils.DBContext;

/**
 *
 * @author MSI
 */
public class PersonnelAttendanceDAO extends DBContext{
    private PersonnelAttendance createPersonnelAttendance(ResultSet resultSet) throws SQLException {
        PersonnelAttendance personnelAttendance = new PersonnelAttendance();
        personnelAttendance.setId(resultSet.getString("id"));
        PersonnelDAO personnelDAO = new PersonnelDAO();
        personnelAttendance.setPersonnel(personnelDAO.getPersonnel(resultSet.getString("personnel_id")));
        DayDAO dayDAO = new DayDAO();
        personnelAttendance.setDay(dayDAO.getDayByID(resultSet.getString("day_id")));
        personnelAttendance.setStatus(resultSet.getString("status"));
        personnelAttendance.setNote(resultSet.getString("note"));
        return personnelAttendance;
    }
      public PersonnelAttendance getAttendanceByPersonnelAndDay(String personnelId, String dayId) {
        String sql = "select * from [PersonnelsAttendance] where personnel_id = ? and day_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, personnelId);
            statement.setString(2, dayId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createPersonnelAttendance(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
      
          public String addAttendance(PersonnelAttendance personnelAttendance) {
        String res = "";
        if (getAttendanceByPersonnelAndDay(personnelAttendance.getPersonnel().getId(), personnelAttendance.getDay().getId()) == null) {
            res = insertAttendance(personnelAttendance);
        } else {
            res = updateAttendance(personnelAttendance);
        }
        return res;
    }
          
            private String insertAttendance(PersonnelAttendance personnelAttendance) {
        String sql = "insert into PersonnelsAttendance values (?,?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            String newId = "";
            if (getLatest() == null) {
                newId = "PEA000001";
            } else {
                newId = generateId(getLatest().getId());
            }
            statement.setString(1, newId);
            statement.setString(2, personnelAttendance.getDay().getId());
            statement.setString(3, personnelAttendance.getPersonnel().getId());
            statement.setString(4, personnelAttendance.getStatus());
            statement.setString(5, personnelAttendance.getNote());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return "Thao tác thất bại! Vui lòng thử lại sau";
        }
        return "success";
    }

    private String updateAttendance(PersonnelAttendance personnelAttendance) {
        String sql = "update PersonnelsAttendance set status = ?, note = ? where personnel_id = ? and day_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, personnelAttendance.getStatus());
            statement.setString(2, personnelAttendance.getNote());
            statement.setString(3, personnelAttendance.getPersonnel().getId());
            statement.setString(4, personnelAttendance.getDay().getId());
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return "Thao tác thất bại! Vui lòng thử lại sau";
        }
        return "success";
    }
    
        private PersonnelAttendance getLatest() {
        String sql = "SELECT TOP 1 * FROM PersonnelsAttendance ORDER BY ID DESC";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return createPersonnelAttendance(rs);
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
        return "PEA" + result;
    }
      
}
