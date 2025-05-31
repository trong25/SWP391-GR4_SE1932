package model.timeslot;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import utils.DBContext;

public class TimeSlotDAO extends DBContext {

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
                timeslot.setDayType(resultSet.getString("day_type")); // thêm dòng này
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
                timeslot.setDayType(resultSet.getString("day_type")); // thêm dòng này
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return timeslot;
    }

    public List<TimeSlot> getTimeslotsForTimetable() {
        List<String> allowedIds = Arrays.asList("TS001", "TS002", "TS003", "TS004", "TS008", "TS009", "TS010");
        String placeholders = String.join(",", Collections.nCopies(allowedIds.size(), "?"));
        String sql = "SELECT * FROM Timeslots WHERE id IN (" + placeholders + ")";

        List<TimeSlot> listTimeslot = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            for (int i = 0; i < allowedIds.size(); i++) {
                preparedStatement.setString(i + 1, allowedIds.get(i));
            }
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                TimeSlot timeslot = new TimeSlot();
                timeslot.setId(resultSet.getString("id"));
                timeslot.setName(resultSet.getString("name"));
                timeslot.setStartTime(resultSet.getString("start_time"));
                timeslot.setEndTime(resultSet.getString("end_time"));
                timeslot.setSlotNumber(resultSet.getString("slot_number"));
                timeslot.setDayType(resultSet.getString("day_type")); // thêm dòng này
                listTimeslot.add(timeslot);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listTimeslot;
    }
}
