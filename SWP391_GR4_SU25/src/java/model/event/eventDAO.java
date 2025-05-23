/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.event;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.personnel.PersonnelDAO;
import utils.DBContext;

/**
 *
 * @author MSI
 */
public class eventDAO extends DBContext{
     private Event createEvent(ResultSet resultSet) throws SQLException {
        Event event = new Event();
        event.setId(resultSet.getString("id"));
        PersonnelDAO personnelDAO = new PersonnelDAO();
        event.setCreatedBy(personnelDAO.getPersonnel(resultSet.getString("created_by")));
        event.setHeading(resultSet.getString("heading"));
        event.setDetails(resultSet.getString("details"));
        event.setDate(resultSet.getDate("date"));
        return event;
    }
      public List<Event> getFutureEvent(int role) {
        List<Event> events = new ArrayList<>();
        String sql = "select * from [Events] join dbo.eventDetails eD on Events.id = eD.event_id\n" +
                "         where date > GETDATE() and eD.participant = ? order by date desc";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, role);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                events.add(createEvent(resultSet));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return events;
    }
}
