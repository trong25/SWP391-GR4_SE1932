/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.timetablepivot;

// Model class cho thời khóa biểu dạng pivot
public class TimetablePivot {
    private String slotNumber;
    private String timeSlot;
    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;
    private String saturday;
    private String sunday;
    
    // Constructor
    public TimetablePivot() {}
    
    public TimetablePivot(String slotNumber, String timeSlot, String monday, 
                         String tuesday, String wednesday, String thursday, 
                         String friday, String saturday, String sunday) {
        this.slotNumber = slotNumber;
        this.timeSlot = timeSlot;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
    }
    
    // Getters and Setters
    public String getSlotNumber() {
        return slotNumber;
    }
    
    public void setSlotNumber(String slotNumber) {
        this.slotNumber = slotNumber;
    }
    
    public String getTimeSlot() {
        return timeSlot;
    }
    
    public void setTimeSlot(String timeSlot) {
        this.timeSlot = timeSlot;
    }
    
    public String getMonday() {
        return monday;
    }
    
    public void setMonday(String monday) {
        this.monday = monday;
    }
    
    public String getTuesday() {
        return tuesday;
    }
    
    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }
    
    public String getWednesday() {
        return wednesday;
    }
    
    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }
    
    public String getThursday() {
        return thursday;
    }
    
    public void setThursday(String thursday) {
        this.thursday = thursday;
    }
    
    public String getFriday() {
        return friday;
    }
    
    public void setFriday(String friday) {
        this.friday = friday;
    }
    
    public String getSaturday() {
        return saturday;
    }
    
    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }
    
    public String getSunday() {
        return sunday;
    }
    
    public void setSunday(String sunday) {
        this.sunday = sunday;
    }
    
    @Override
    public String toString() {
        return "TimetablePivot{" +
                "slotNumber='" + slotNumber + '\'' +
                ", timeSlot='" + timeSlot + '\'' +
                ", monday='" + monday + '\'' +
                ", tuesday='" + tuesday + '\'' +
                ", wednesday='" + wednesday + '\'' +
                ", thursday='" + thursday + '\'' +
                ", friday='" + friday + '\'' +
                ", saturday='" + saturday + '\'' +
                ", sunday='" + sunday + '\'' +
                '}';
    }
}

