package model.notification;

import model.personnel.Personnel;

import java.util.Date;

public class Notification {
    private String id;
    private String heading;
    private String details;
    private Personnel createdBy;
    private Date createdAt;

    public Notification(String id, String heading, String details, Personnel createdBy, Date createdAt) {
        this.id = id;
        this.heading = heading;
        this.details = details;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public Notification() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Personnel getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Personnel createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Notification{" + "id=" + id + ", heading=" + heading + ", details=" + details + ", createdBy=" + createdBy + ", createdAt=" + createdAt + '}';
    }
    
}
