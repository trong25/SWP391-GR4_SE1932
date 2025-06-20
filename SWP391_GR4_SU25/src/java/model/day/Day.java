/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.day;

import java.util.Calendar;
import java.util.Date;
import model.week.Week;

/**
 *
 * @author MSI
 */
public class Day {
    private String id;
    private Week week;
    private Date date;

    public Day() {
    }

    public Day(String id, Week week, Date date) {
        this.id = id;
        this.week = week;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Week getWeek() {
        return week;
    }

    public void setWeek(Week week) {
        this.week = week;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    
}
