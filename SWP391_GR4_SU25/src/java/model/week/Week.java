/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.week;

import java.text.SimpleDateFormat;
import java.util.Date;
import model.schoolYear.SchoolYear;

/**
 *
 * @author MSI
 */
public class Week {
     private String id;
    private Date startDate;
    private Date endDate;
    private SchoolYear schoolYear;

    public Week() {
    }

     public Week(Date startDate, String id, Date endDate, SchoolYear schoolYear) {
        this.startDate = startDate;
        this.id = id;
        this.endDate = endDate;
        this.schoolYear = schoolYear;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public SchoolYear getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(SchoolYear schoolYear) {
        this.schoolYear = schoolYear;
    }
     public String getStartDatetoEndDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String start =  sdf.format(startDate);
        String end =  sdf.format(endDate);
        return start+"-"+end;
    }
    
}
