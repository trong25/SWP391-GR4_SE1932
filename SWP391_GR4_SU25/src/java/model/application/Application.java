package model.application;

import model.personnel.Personnel;

import java.util.Date;

public class Application {
    private String id;
    private Date processedAt;
    private ApplicationType type;
    private String details;
    private String processNote;
    private String status;
    private String createdBy;
    private Date createdAt;
    private Date startDate;
    private Date endDate;
    private Personnel processedBy;

    public Application() {
    }

    public Application(String id, Date processedAt, ApplicationType type, String details,
                       String processNote, String status, String createdBy, Date createdAt,
                       Date startDate, Date endDate, Personnel processedBy) {
        this.id = id;
        this.processedAt = processedAt;
        this.type = type;
        this.details = details;
        this.processNote = processNote;
        this.status = status;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
        this.startDate = startDate;
        this.endDate = endDate;
        this.processedBy = processedBy;
    }

    public String getProcessNote() {
        return processNote;
    }

    public void setProcessNote(String processNote) {
        this.processNote = processNote;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getProcessedAt() {
        return processedAt;
    }

    public void setProcessedAt(Date processedAt) {
        this.processedAt = processedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Personnel getProcessedBy() {
        return processedBy;
    }

    public void setProcessedBy(Personnel processedBy) {
        this.processedBy = processedBy;
    }

    public ApplicationType getType() {
        return type;
    }

    public void setType(ApplicationType type) {
        this.type = type;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
