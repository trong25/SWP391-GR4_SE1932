/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.personnel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
}
