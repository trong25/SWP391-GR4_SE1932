package model.student;

import model.day.DayDAO;

import utils.DBContext;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StudentAttendanceDAO extends DBContext {


    private StudentAttendance createStudentAttendance(ResultSet resultSet) throws SQLException {
        StudentAttendance studentAttendance = new StudentAttendance();
        studentAttendance.setId(resultSet.getString("id"));
        StudentDAO studentDAO = new StudentDAO();

        studentAttendance.setStudent(studentDAO.getStudentsById(resultSet.getString("student_id")));

        DayDAO dayDAO = new DayDAO();
        studentAttendance.setDay(dayDAO.getDayByID(resultSet.getString("day_id")));
        studentAttendance.setStatus(resultSet.getString("status"));
        studentAttendance.setNote(resultSet.getString("note"));
        return studentAttendance;
    }




    
    public String addAttendance(StudentAttendance studentAttendance) {
        try {
            // Debug logs
            System.out.println("Debug - Adding attendance for student: " + studentAttendance.getStudent().getId());
            System.out.println("Debug - Day: " + studentAttendance.getDay().getId());
            System.out.println("Debug - Status: " + studentAttendance.getStatus());
            
            // Check if attendance already exists
            StudentAttendance existingAttendance = getAttendanceByStudentAndDay(
                studentAttendance.getStudent().getId(), 
                studentAttendance.getDay().getId()
            );
            
            if (existingAttendance == null) {
                System.out.println("Debug - No existing attendance found, inserting new record");
                return insertAttendance(studentAttendance);
            } else {
                System.out.println("Debug - Existing attendance found, updating record");
                return updateAttendance(studentAttendance);
            }
        } catch (Exception e) {
            System.out.println("Debug - Error in addAttendance: " + e.getMessage());
            e.printStackTrace();
            return "Thao tác thất bại! Vui lòng thử lại sau";
        }
    }

    private String insertAttendance(StudentAttendance studentAttendance) {
        String sql = "INSERT INTO StudentsAttendance (id, day_id, student_id, status, note) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            
            // Generate new ID
            String newId = generateNewId();
            System.out.println("Debug - Generated new ID: " + newId);
            
            statement.setString(1, newId);
            statement.setString(2, studentAttendance.getDay().getId());
            statement.setString(3, studentAttendance.getStudent().getId());
            statement.setString(4, studentAttendance.getStatus());
            statement.setString(5, studentAttendance.getNote());
            
            // Debug log
            System.out.println("Debug - Inserting attendance with values:");
            System.out.println("Debug - ID: " + newId);
            System.out.println("Debug - Day ID: " + studentAttendance.getDay().getId());
            System.out.println("Debug - Student ID: " + studentAttendance.getStudent().getId());
            System.out.println("Debug - Status: " + studentAttendance.getStatus());
            System.out.println("Debug - Note: " + studentAttendance.getNote());
            
            int result = statement.executeUpdate();
            System.out.println("Debug - Insert result: " + result + " rows affected");
            
            if (result > 0) {
                return "success";
            } else {
                return "Không thể thêm điểm danh";
            }
        } catch (Exception e) {
            System.out.println("Debug - Error in insertAttendance: " + e.getMessage());
            e.printStackTrace();
            return "Thao tác thất bại! Vui lòng thử lại sau";
        }
    }

    private String updateAttendance(StudentAttendance studentAttendance) {
        String sql = "UPDATE StudentsAttendance SET status = ?, note = ? WHERE student_id = ? AND day_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, studentAttendance.getStatus());
            statement.setString(2, studentAttendance.getNote());
            statement.setString(3, studentAttendance.getStudent().getId());
            statement.setString(4, studentAttendance.getDay().getId());
            
            // Debug log
            System.out.println("Debug - Updating attendance with values:");
            System.out.println("Debug - Student ID: " + studentAttendance.getStudent().getId());
            System.out.println("Debug - Day ID: " + studentAttendance.getDay().getId());
            System.out.println("Debug - Status: " + studentAttendance.getStatus());
            System.out.println("Debug - Note: " + studentAttendance.getNote());
            
            int result = statement.executeUpdate();
            System.out.println("Debug - Update result: " + result + " rows affected");
            
            if (result > 0) {
                return "success";
            } else {
                return "Không thể cập nhật điểm danh";
            }
        } catch (Exception e) {
            System.out.println("Debug - Error in updateAttendance: " + e.getMessage());
            e.printStackTrace();
            return "Thao tác thất bại! Vui lòng thử lại sau";
        }
    }

    private String generateNewId() {
        String sql = "SELECT TOP 1 id FROM StudentsAttendance ORDER BY id DESC";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            String latestId = "HS000000";
            
            if (rs.next()) {
                latestId = rs.getString("id");
            }
            
            // Extract number and increment
            String numberStr = latestId.substring(3);
            int number = Integer.parseInt(numberStr) + 1;
            
            // Format with leading zeros
            String newNumber = String.format("%06d", number);
            return "HS" + newNumber;
            
        } catch (Exception e) {
            System.out.println("Debug - Error generating new ID: " + e.getMessage());
            e.printStackTrace();
            return "HS000001"; // Fallback ID
        }
    }

   
    public StudentAttendance getAttendanceByStudentAndDay(String studentId, String dayId) {
        String sql = """
                     SELECT sa.*, 
                            s.first_name, 
                            s.last_name, 
                            d.date,
                            sub.name AS subject_name,
                            ts.name AS timeslot_name,
                            ts.start_time,
                            ts.end_time,
                            ts.slot_number
                     FROM StudentsAttendance sa
                     JOIN Students s ON sa.student_id = s.id
                     JOIN Days d ON sa.day_id = d.id
                     LEFT JOIN Timetables tt ON sa.day_id = tt.date_id AND sa.teacher_id = tt.teacher_id
                     LEFT JOIN Subjects sub ON tt.subject_id = sub.id
                     LEFT JOIN Timeslots ts ON tt.timeslot_id = ts.id
                     WHERE sa.student_id = ? AND sa.day_id = ?
                     """;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, studentId);
            statement.setString(2, dayId);
            
            // Debug log
            System.out.println("Debug - Getting attendance for student: " + studentId);
            System.out.println("Debug - Day: " + dayId);
            System.out.println("Debug - SQL: " + sql);
            
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                StudentAttendance attendance = createStudentAttendance(resultSet);
                attendance.setSubjectName(resultSet.getString("subject_name"));
                attendance.setTimeslotName(resultSet.getString("timeslot_name"));
                System.out.println("Debug - Found attendance: " + attendance.getStatus());
                System.out.println("Debug - Attendance ID: " + attendance.getId());
                System.out.println("Debug - Attendance Note: " + attendance.getNote());
                System.out.println("Debug - Student: " + attendance.getStudent().getLastName() + " " + attendance.getStudent().getFirstName());
                System.out.println("Debug - Day: " + attendance.getDay().getDate());
                return attendance;
            } else {
                System.out.println("Debug - No attendance record found for student: " + studentId + " and day: " + dayId);
            }
        } catch (Exception e) {
            System.out.println("Debug - Error getting attendance: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    
    public StudentAttendance getAttendanceByStudentAndDayAndTimeslotId(String studentId, String dayId, String timeslotId) {
        String sql = """
                     SELECT sa.*, 
                            s.first_name, 
                            s.last_name, 
                            d.date,
                            sub.name AS subject_name,
                            ts.name AS timeslot_name,
                            ts.start_time,
                            ts.end_time,
                            ts.slot_number
                     FROM StudentsAttendance sa
                     JOIN Students s ON sa.student_id = s.id
                     JOIN Days d ON sa.day_id = d.id
                     LEFT JOIN Timetables tt ON sa.day_id = tt.date_id AND sa.teacher_id = tt.teacher_id AND tt.timeslot_id = ?
                     LEFT JOIN Subjects sub ON tt.subject_id = sub.id
                     LEFT JOIN Timeslots ts ON tt.timeslot_id = ts.id
                     WHERE sa.student_id = ? AND sa.day_id = ?
                     """;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, timeslotId);
            statement.setString(2, studentId);
            statement.setString(3, dayId);
            
            // Debug log
            System.out.println("Debug - Getting attendance for student: " + studentId);
            System.out.println("Debug - Day: " + dayId);
            System.out.println("Debug - SQL: " + sql);
            
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                StudentAttendance attendance = createStudentAttendance(resultSet);
                attendance.setSubjectName(resultSet.getString("subject_name"));
                attendance.setTimeslotName(resultSet.getString("timeslot_name"));
                System.out.println("Debug - Found attendance: " + attendance.getStatus());
                System.out.println("Debug - Attendance ID: " + attendance.getId());
                System.out.println("Debug - Attendance Note: " + attendance.getNote());
                System.out.println("Debug - Student: " + attendance.getStudent().getLastName() + " " + attendance.getStudent().getFirstName());
                System.out.println("Debug - Day: " + attendance.getDay().getDate());
                return attendance;
            } else {
                System.out.println("Debug - No attendance record found for student: " + studentId + " and day: " + dayId);
            }
        } catch (Exception e) {
            System.out.println("Debug - Error getting attendance: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

 
    public boolean checkAttendanceByDay(List<Student> listStudent, String dayId) {
        StringBuilder sql= new StringBuilder("select id from StudentsAttendance where day_id= ? ");
       if(!listStudent.isEmpty()){
           sql.append(" and student_id in (");
       }
        for(int i=0;i<listStudent.size();i++){
            if(i== listStudent.size()-1){
                sql.append("'").append(listStudent.get(i).getId()).append("')");
            }else{
                sql.append("'").append(listStudent.get(i).getId()).append("',");
            }
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
            preparedStatement.setString(1,dayId);
            ResultSet resultSet = preparedStatement.executeQuery();
            int rows=0;
            while(resultSet.next()){
                rows++;
            }
            if(rows== listStudent.size()){
                return true;
            }else if(rows==0){
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    
    public List<StudentAttendance> getAttendanceOfClassByWeek(String classId, String weekId) {
        List<StudentAttendance> listStudentAttendance = new ArrayList<>();
        String sql = "select * from StudentsAttendance";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);

        }catch (Exception e){
            e.printStackTrace();
        }
        return listStudentAttendance;
    }


    private StudentAttendance getLatest() {
        String sql = "SELECT TOP 1 * FROM studentsAttendance ORDER BY ID DESC";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return createStudentAttendance(rs);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    private String generateId(String latestId) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(latestId);
        int number = 0;
        if (matcher.find()) {
            number = Integer.parseInt(matcher.group()) + 1;
        }
        DecimalFormat decimalFormat = new DecimalFormat("000000");
        String result = decimalFormat.format(number);
        return "HS" + result;
    }

    public String getTodayAttendanceStatus(String studentId, String dayId) {
        String sql = "SELECT status FROM StudentsAttendance WHERE student_id = ? AND day_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, studentId);
            statement.setString(2, dayId);
            ResultSet rs = statement.executeQuery();
            boolean hasPresent = false;
            while (rs.next()) {
                String status = rs.getString("status");
                if ("absent".equalsIgnoreCase(status)) return "absent";
                if ("present".equalsIgnoreCase(status)) hasPresent = true;
            }
            if (hasPresent) return "present";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ""; // Chưa cập nhật
    }

}
