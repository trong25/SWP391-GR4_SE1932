/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.timetablepivot;

// DAO class
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import utils.DBContext;

public class TimetableDAO extends DBContext {

    /**
     * Lấy thời khóa biểu dạng pivot cho học sinh theo tuần hiện tại
     *
     * @param studentId ID của học sinh
     * @return List<TimetablePivot> danh sách thời khóa biểu
     */
    public List<TimetablePivot> getStudentTimetablePivot(String studentId) {
        List<TimetablePivot> timetableList = new ArrayList<>();

        String sql = """
                        SELECT 
                            ts.slot_number, 
                            ts.start_time + ' - ' + ts.end_time AS time_slot, 
                            MAX(CASE WHEN DATENAME(WEEKDAY, d.date) = 'Monday' THEN sub.name + ' (' + p.first_name + ' ' + p.last_name + ')' END) AS Monday, 
                            MAX(CASE WHEN DATENAME(WEEKDAY, d.date) = 'Tuesday' THEN sub.name + ' (' + p.first_name + ' ' + p.last_name + ')' END) AS Tuesday, 
                            MAX(CASE WHEN DATENAME(WEEKDAY, d.date) = 'Wednesday' THEN sub.name + ' (' + p.first_name + ' ' + p.last_name + ')' END) AS Wednesday, 
                            MAX(CASE WHEN DATENAME(WEEKDAY, d.date) = 'Thursday' THEN sub.name + ' (' + p.first_name + ' ' + p.last_name + ')' END) AS Thursday, 
                            MAX(CASE WHEN DATENAME(WEEKDAY, d.date) = 'Friday' THEN sub.name + ' (' + p.first_name + ' ' + p.last_name + ')' END) AS Friday, 
                            MAX(CASE WHEN DATENAME(WEEKDAY, d.date) = 'Saturday' THEN sub.name + ' (' + p.first_name + ' ' + p.last_name + ')' END) AS Saturday, 
                            MAX(CASE WHEN DATENAME(WEEKDAY, d.date) = 'Sunday' THEN sub.name + ' (' + p.first_name + ' ' + p.last_name + ')' END) AS Sunday 
                        FROM Students s 
                        INNER JOIN classDetails cd ON s.id = cd.student_id 
                        INNER JOIN Class c ON cd.class_id = c.id 
                        INNER JOIN Timetables t ON c.id = t.class_id 
                        INNER JOIN Days d ON t.date_id = d.id 
                        INNER JOIN Weeks w ON d.week_id = w.id 
                        INNER JOIN Timeslots ts ON t.timeslot_id = ts.id 
                        INNER JOIN Subjects sub ON t.subject_id = sub.id 
                        INNER JOIN Personnels p ON t.teacher_id = p.id 
                        WHERE s.id = ? 
                        AND GETDATE() BETWEEN w.start_date AND w.end_date 
                        GROUP BY ts.slot_number, ts.start_time, ts.end_time 
                        ORDER BY ts.slot_number
                        """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, studentId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TimetablePivot timetable = new TimetablePivot();
                    timetable.setSlotNumber(rs.getString("slot_number"));
                    timetable.setTimeSlot(rs.getString("time_slot"));
                    timetable.setMonday(rs.getString("Monday"));
                    timetable.setTuesday(rs.getString("Tuesday"));
                    timetable.setWednesday(rs.getString("Wednesday"));
                    timetable.setThursday(rs.getString("Thursday"));
                    timetable.setFriday(rs.getString("Friday"));
                    timetable.setSaturday(rs.getString("Saturday"));
                    timetable.setSunday(rs.getString("Sunday"));

                    timetableList.add(timetable);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting student timetable pivot: " + e.getMessage());
            e.printStackTrace();
        }

        return timetableList;
    }

    /**
     * Lấy thời khóa biểu dạng pivot cho học sinh theo khoảng thời gian cụ thể
     *
     * @param studentId ID của học sinh
     * @param startDate Ngày bắt đầu
     * @param endDate Ngày kết thúc
     * @return List<TimetablePivot> danh sách thời khóa biểu
     */
    public List<TimetablePivot> getStudentTimetablePivotByDateRange(String studentId, Date startDate, Date endDate) {
        List<TimetablePivot> timetableList = new ArrayList<>();
        System.out.println(" '"+(startDate.getYear()+1900) + "-" + (startDate.getMonth()+1) + "-" + startDate.getDate()+"' ");
        String sd = " '"+(startDate.getYear()+1900) + "-" + (startDate.getMonth()+1) + "-" + startDate.getDate()+"' ";
        String ed = " '"+(endDate.getYear()+1900) + "-" + (endDate.getMonth()+1) + "-" + endDate.getDate()+"' ";
        String sql = """
                        SELECT 
                            ts.slot_number, 
                            ts.start_time + ' - ' + ts.end_time AS time_slot, 
                            MAX(CASE WHEN DATENAME(WEEKDAY, d.date) = 'Monday' THEN sub.name + ' (' + p.first_name + ' ' + p.last_name + ')' END) AS Monday, 
                            MAX(CASE WHEN DATENAME(WEEKDAY, d.date) = 'Tuesday' THEN sub.name + ' (' + p.first_name + ' ' + p.last_name + ')' END) AS Tuesday, 
                            MAX(CASE WHEN DATENAME(WEEKDAY, d.date) = 'Wednesday' THEN sub.name + ' (' + p.first_name + ' ' + p.last_name + ')' END) AS Wednesday, 
                            MAX(CASE WHEN DATENAME(WEEKDAY, d.date) = 'Thursday' THEN sub.name + ' (' + p.first_name + ' ' + p.last_name + ')' END) AS Thursday, 
                            MAX(CASE WHEN DATENAME(WEEKDAY, d.date) = 'Friday' THEN sub.name + ' (' + p.first_name + ' ' + p.last_name + ')' END) AS Friday, 
                            MAX(CASE WHEN DATENAME(WEEKDAY, d.date) = 'Saturday' THEN sub.name + ' (' + p.first_name + ' ' + p.last_name + ')' END) AS Saturday, 
                            MAX(CASE WHEN DATENAME(WEEKDAY, d.date) = 'Sunday' THEN sub.name + ' (' + p.first_name + ' ' + p.last_name + ')' END) AS Sunday 
                        FROM Students s 
                        INNER JOIN classDetails cd ON s.id = cd.student_id 
                        INNER JOIN Class c ON cd.class_id = c.id 
                        INNER JOIN Timetables t ON c.id = t.class_id 
                        INNER JOIN Days d ON t.date_id = d.id 
                        INNER JOIN Timeslots ts ON t.timeslot_id = ts.id 
                        INNER JOIN Subjects sub ON t.subject_id = sub.id 
                        INNER JOIN Personnels p ON t.teacher_id = p.id 
                        WHERE s.id = ? 
                        AND d.date BETWEEN """+sd+""" 
                                                AND """+ed+"""
                        GROUP BY ts.slot_number, ts.start_time, ts.end_time 
                        ORDER BY ts.slot_number
                        """;
        
//        sql = """
//                        SELECT 
//                            ts.slot_number, 
//                            ts.start_time + ' - ' + ts.end_time AS time_slot, 
//                            MAX(CASE WHEN DATENAME(WEEKDAY, d.date) = 'Monday' THEN sub.name + ' (' + p.first_name + ' ' + p.last_name + ')' END) AS Monday, 
//                            MAX(CASE WHEN DATENAME(WEEKDAY, d.date) = 'Tuesday' THEN sub.name + ' (' + p.first_name + ' ' + p.last_name + ')' END) AS Tuesday, 
//                            MAX(CASE WHEN DATENAME(WEEKDAY, d.date) = 'Wednesday' THEN sub.name + ' (' + p.first_name + ' ' + p.last_name + ')' END) AS Wednesday, 
//                            MAX(CASE WHEN DATENAME(WEEKDAY, d.date) = 'Thursday' THEN sub.name + ' (' + p.first_name + ' ' + p.last_name + ')' END) AS Thursday, 
//                            MAX(CASE WHEN DATENAME(WEEKDAY, d.date) = 'Friday' THEN sub.name + ' (' + p.first_name + ' ' + p.last_name + ')' END) AS Friday, 
//                            MAX(CASE WHEN DATENAME(WEEKDAY, d.date) = 'Saturday' THEN sub.name + ' (' + p.first_name + ' ' + p.last_name + ')' END) AS Saturday, 
//                            MAX(CASE WHEN DATENAME(WEEKDAY, d.date) = 'Sunday' THEN sub.name + ' (' + p.first_name + ' ' + p.last_name + ')' END) AS Sunday 
//                        FROM Students s 
//                        INNER JOIN classDetails cd ON s.id = cd.student_id 
//                        INNER JOIN Class c ON cd.class_id = c.id 
//                        INNER JOIN Timetables t ON c.id = t.class_id 
//                        INNER JOIN Days d ON t.date_id = d.id 
//                        INNER JOIN Timeslots ts ON t.timeslot_id = ts.id 
//                        INNER JOIN Subjects sub ON t.subject_id = sub.id 
//                        INNER JOIN Personnels p ON t.teacher_id = p.id 
//                        WHERE s.id = ? 
//                        AND d.date BETWEEN ? AND ?
//                        GROUP BY ts.slot_number, ts.start_time, ts.end_time 
//                        ORDER BY ts.slot_number
//                        """;
        System.out.println(sql);
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, studentId);
//            stmt.setObject(2, startDate);
//            stmt.setObject(3, endDate);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    TimetablePivot timetable = new TimetablePivot();
                    timetable.setSlotNumber(rs.getString("slot_number"));
                    timetable.setTimeSlot(rs.getString("time_slot"));
                    timetable.setMonday(rs.getString("Monday"));
                    timetable.setTuesday(rs.getString("Tuesday"));
                    timetable.setWednesday(rs.getString("Wednesday"));
                    timetable.setThursday(rs.getString("Thursday"));
                    timetable.setFriday(rs.getString("Friday"));
                    timetable.setSaturday(rs.getString("Saturday"));
                    timetable.setSunday(rs.getString("Sunday"));

                    timetableList.add(timetable);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting student timetable pivot by date range: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println(timetableList.size());
        return timetableList;
    }
}
