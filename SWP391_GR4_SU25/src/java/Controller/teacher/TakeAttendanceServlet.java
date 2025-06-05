package Controller.teacher;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.classes.ClassDAO;
import model.day.DayDAO;
import model.student.*;
import model.user.User;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

/**
 *
 * @author PC
 */

@WebServlet(name = "teacher/TakeAttendanceServlet", value = "/teacher/takeattendance")
public class TakeAttendanceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //getting toast message sent from doPost after take attendance
        HttpSession session = request.getSession();
        String toastType = "", toastMessage = "";
        if (session.getAttribute("toastType") != null) {
            toastType = session.getAttribute("toastType").toString();
            toastMessage = session.getAttribute("toastMessage").toString();
        }
        session.removeAttribute("toastType");
        session.removeAttribute("toastMessage");
        request.setAttribute("toastType", toastType);
        request.setAttribute("toastMessage", toastMessage);

        StudentDAO StudentDAO = new StudentDAO();
        User user = (User) session.getAttribute("user");
        Date date = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(date);
        
        // Debug logs
        System.out.println("Debug - User: " + user.getUsername());
        System.out.println("Debug - Date: " + dateString);
        
        // Add data check
        StudentDAO.checkDataForAttendance(user.getUsername().toUpperCase(), dateString);
        
        List<Student> Students = StudentDAO.getStudentsByTeacherAndTimetable(user.getUsername().toUpperCase(), dateString);
        
        // Debug log for students list
        System.out.println("Debug - Number of students found: " + (Students != null ? Students.size() : 0));
        if (Students != null && !Students.isEmpty()) {
            System.out.println("Debug - First student ID: " + Students.get(0).getId());
        }
        
        ClassDAO classDAO = new ClassDAO();
        String className = classDAO.getClassNameByTeacherAndTimetable(user.getUsername(), dateString);
        
        // Debug log for class name
        System.out.println("Debug - Class name: " + className);
        
        request.setAttribute("currentDate", dateString);
        request.setAttribute("className", className);
        request.setAttribute("students", Students);
        
        // Debug log for request attributes
        System.out.println("Debug - Request attributes set:");
        System.out.println("Debug - currentDate: " + request.getAttribute("currentDate"));
        System.out.println("Debug - className: " + request.getAttribute("className"));
        System.out.println("Debug - students size: " + (request.getAttribute("students") != null ? ((List)request.getAttribute("students")).size() : 0));
        
        request.getRequestDispatcher("takeAttendance.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Enumeration<String> params = request.getParameterNames();
            StudentAttendance studentAttendance = new StudentAttendance();
            DayDAO dayDAO = new DayDAO();
            StudentDAO StudentDAO = new StudentDAO();
            StudentAttendanceDAO studentAttendanceDAO = new StudentAttendanceDAO();
            Date currentDate = new Date();
            
            // Define the date format
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            // Convert the Date to a String
            String dateString = formatter.format(currentDate);
            
            // Debug logs
            System.out.println("Debug - Processing attendance for date: " + dateString);
            
            String result = "success";
            boolean hasAttendance = false;
            
            while (params.hasMoreElements()) {
                String name = params.nextElement();
                if (name.startsWith("attendance")) {
                    hasAttendance = true;
                    String studentId = name.substring(10);
                    String status = request.getParameter(name);
                    String note = request.getParameter("note" + studentId);
                    
                    // Debug logs
                    System.out.println("Debug - Processing student: " + studentId);
                    System.out.println("Debug - Status: " + status);
                    System.out.println("Debug - Note: " + note);
                    
                    studentAttendance.setDay(dayDAO.getDayByDate(dateString));
                    studentAttendance.setStudent(StudentDAO.getStudentsById(studentId));
                    studentAttendance.setStatus(status);
                    studentAttendance.setNote(note);
                    
                    String attendanceResult = studentAttendanceDAO.addAttendance(studentAttendance);
                    if (!attendanceResult.equals("success")) {
                        result = attendanceResult;
                        break;
                    }
                }
            }
            
            if (!hasAttendance) {
                result = "Không có dữ liệu điểm danh nào được gửi";
            }
            
            //sending result
            HttpSession session = request.getSession();
            if (result.equals("success")) {
                session.setAttribute("toastType", "success");
                session.setAttribute("toastMessage", "Điểm danh thành công");
            } else {
                session.setAttribute("toastType", "error");
                session.setAttribute("toastMessage", result);
            }
            
            // Debug log
            System.out.println("Debug - Attendance result: " + result);
            
            response.sendRedirect("takeattendance");
            
        } catch (Exception e) {
            System.out.println("Debug - Error in doPost: " + e.getMessage());
            e.printStackTrace();
            
            HttpSession session = request.getSession();
            session.setAttribute("toastType", "error");
            session.setAttribute("toastMessage", "Có lỗi xảy ra: " + e.getMessage());
            response.sendRedirect("takeattendance");
        }
    }
}