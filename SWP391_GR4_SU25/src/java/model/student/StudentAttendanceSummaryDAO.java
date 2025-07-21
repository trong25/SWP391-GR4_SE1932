package dao.students;

import utils.DBContext;
import model.student.StudentAttendanceSummary;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentAttendanceSummaryDAO extends DBContext {
    
    /**
     * Tạo đối tượng StudentAttendanceSummary từ ResultSet
     */
    private StudentAttendanceSummary createStudentAttendanceSummary(ResultSet rs) throws SQLException {
        StudentAttendanceSummary attendance = new StudentAttendanceSummary();
        
        attendance.setStudentId(rs.getString("id"));
        attendance.setAvatar(rs.getString("avatar"));
        attendance.setFirstName(rs.getString("first_name"));
        attendance.setLastName(rs.getString("last_name"));
        attendance.setClassId(rs.getString("class_id"));
        attendance.setClassName(rs.getString("class_name"));
        attendance.setFee(rs.getInt("fee"));
        attendance.setPresentCount(rs.getInt("present_count"));
        attendance.setAbsentCount(rs.getInt("absent_count"));
        
        return attendance;
    }
    
    /**
     * Lấy thông tin học sinh với số liệu điểm danh theo từng lớp
     * Dành cho học sinh đang theo học
     */
    public List<StudentAttendanceSummary> getActiveStudentsAttendance() {
        List<StudentAttendanceSummary> list = new ArrayList<>();
        String sql = "SELECT " +
                    "    s.id, " +
                    "    s.avatar, " +
                    "    s.first_name, " +
                    "    s.last_name, " +
                    "    cd.class_id, " +
                    "    cl.name AS class_name, " +
                    "    cl.fee, " +
                    "    SUM(CASE WHEN sa.status = 'present' THEN 1 ELSE 0 END) AS present_count, " +
                    "    SUM(CASE WHEN sa.status = 'absent' THEN 1 ELSE 0 END) AS absent_count " +
                    "FROM Students s " +
                    "JOIN classDetails cd ON cd.student_id = s.id " +
                    "JOIN Class cl ON cl.id = cd.class_id " +
                    "LEFT JOIN StudentsAttendance sa ON s.id = sa.student_id " +
                    "WHERE s.status = N'đang theo học' " +
                    "GROUP BY " +
                    "    s.id, s.avatar, s.first_name, s.last_name, " +
                    "    cd.class_id, cl.name, cl.fee";
        
        try (Connection conn = getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                list.add(createStudentAttendanceSummary(rs));
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    /**
     * Lấy thông tin học sinh với số liệu điểm danh theo một lớp cụ thể
     */
    public List<StudentAttendanceSummary> getStudentsAttendanceByClass(String classId) {
        List<StudentAttendanceSummary> list = new ArrayList<>();
        String sql = "SELECT " +
                    "    s.id, " +
                    "    s.avatar, " +
                    "    s.first_name, " +
                    "    s.last_name, " +
                    "    cd.class_id, " +
                    "    cl.name AS class_name, " +
                    "    cl.fee, " +
                    "    SUM(CASE WHEN sa.status = 'present' THEN 1 ELSE 0 END) AS present_count, " +
                    "    SUM(CASE WHEN sa.status = 'absent' THEN 1 ELSE 0 END) AS absent_count " +
                    "FROM Students s " +
                    "JOIN classDetails cd ON cd.student_id = s.id " +
                    "JOIN Class cl ON cl.id = cd.class_id " +
                    "LEFT JOIN StudentsAttendance sa ON s.id = sa.student_id " +
                    "WHERE s.status = N'đang theo học' AND cd.class_id = ? " +
                    "GROUP BY " +
                    "    s.id, s.avatar, s.first_name, s.last_name, " +
                    "    cd.class_id, cl.name, cl.fee";
        
        try (Connection conn = getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, classId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(createStudentAttendanceSummary(rs));
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    /**
     * Lấy thông tin một học sinh cụ thể với số liệu điểm danh
     */
    public List<StudentAttendanceSummary> getStudentAttendanceById(String studentId) {
        List<StudentAttendanceSummary> list = new ArrayList<>();
        String sql = "SELECT " +
                    "    s.id, " +
                    "    s.avatar, " +
                    "    s.first_name, " +
                    "    s.last_name, " +
                    "    cd.class_id, " +
                    "    cl.name AS class_name, " +
                    "    cl.fee, " +
                    "    SUM(CASE WHEN sa.status = 'present' THEN 1 ELSE 0 END) AS present_count, " +
                    "    SUM(CASE WHEN sa.status = 'absent' THEN 1 ELSE 0 END) AS absent_count " +
                    "FROM Students s " +
                    "JOIN classDetails cd ON cd.student_id = s.id " +
                    "JOIN Class cl ON cl.id = cd.class_id " +
                    "LEFT JOIN StudentsAttendance sa ON s.id = sa.student_id " +
                    "WHERE s.status = N'đang theo học' AND s.id = ? " +
                    "GROUP BY " +
                    "    s.id, s.avatar, s.first_name, s.last_name, " +
                    "    cd.class_id, cl.name, cl.fee";
        
        try (Connection conn = getConnection(); 
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, studentId);
            
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(createStudentAttendanceSummary(rs));
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    /**
     * Lấy học sinh có tỷ lệ điểm danh thấp (dưới ngưỡng nhất định)
     */
    public List<StudentAttendanceSummary> getStudentsWithLowAttendance(double minAttendanceRate) {
        List<StudentAttendanceSummary> allStudents = getActiveStudentsAttendance();
        List<StudentAttendanceSummary> lowAttendanceStudents = new ArrayList<>();
        
        for (StudentAttendanceSummary student : allStudents) {
            if (student.getAttendanceRate() < minAttendanceRate) {
                lowAttendanceStudents.add(student);
            }
        }
        
        return lowAttendanceStudents;
    }
}