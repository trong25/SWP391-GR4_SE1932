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

    public String convertToWeekDay()
        {
            // Create a Calendar object and set the date
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(this.date);

            // Get the day of the week (1 = Sunday, 7 = Saturday)
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

            // Print the day of the week
            String dayString = "";
            switch (dayOfWeek) {
                case Calendar.SUNDAY:    dayString = "Chủ Nhật"; break;
                case Calendar.MONDAY:    dayString = "Thứ Hai"; break;
                case Calendar.TUESDAY:   dayString = "Thứ Ba"; break;
                case Calendar.WEDNESDAY: dayString = "Thứ Tư"; break;
                case Calendar.THURSDAY:  dayString = "Thứ Năm"; break;
                case Calendar.FRIDAY:    dayString = "Thứ Sáu"; break;
                case Calendar.SATURDAY:  dayString = "Thứ Bảy"; break;
            }

           return dayString;
        }
}
