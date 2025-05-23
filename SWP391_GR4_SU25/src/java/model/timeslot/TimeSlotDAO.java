/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.timeslot;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DBContext;

/**
 *
 * @author MSI
 */
public class TimeSlotDAO extends DBContext{
     public List<TimeSlot> getAllTimeslots() {
        String sql = "SELECT * FROM Timeslots";
        List<TimeSlot> listTimeslot = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                TimeSlot timeslot = new TimeSlot();
                timeslot.setId(resultSet.getString("id"));
                timeslot.setName(resultSet.getString("name"));
                timeslot.setStartTime(resultSet.getString("start_time"));
                timeslot.setEndTime(resultSet.getString("end_time"));
                timeslot.setSlotNumber(resultSet.getString("slot_number"));
                listTimeslot.add(timeslot);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listTimeslot;
    }
     public TimeSlot getTimeslotById(String timeslotId) {
        String sql = "SELECT * FROM Timeslots WHERE id = ?";
        TimeSlot timeslot = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, timeslotId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                timeslot = new TimeSlot();
                timeslot.setId(resultSet.getString("id"));
                timeslot.setName(resultSet.getString("name"));
                timeslot.setStartTime(resultSet.getString("start_time"));
                timeslot.setEndTime(resultSet.getString("end_time"));
                timeslot.setSlotNumber(resultSet.getString("slot_number"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return timeslot;
    }
}
