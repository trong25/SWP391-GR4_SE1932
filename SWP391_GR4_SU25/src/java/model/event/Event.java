/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.event;

import java.util.Date;
import model.personnel.Personnel;

/**
 *
 * @author MSI
 */
public class Event {
    private String id;
    private Personnel createdBy;
    private String heading;
    private String details;
    private Date date;

    public Event() {
    }

    public Event(String id, Personnel createdBy, String heading, String details, Date date) {
        this.id = id;
        this.createdBy = createdBy;
        this.heading = heading;
        this.details = details;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Personnel getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Personnel createdBy) {
        this.createdBy = createdBy;
    }

    public String getHeading() {
        return heading;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    
    
}
