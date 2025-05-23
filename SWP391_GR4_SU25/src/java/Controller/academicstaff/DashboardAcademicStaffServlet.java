/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.academicstaff;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import model.classes.ClassDAO;
import model.day.DayDAO;
import model.event.eventDAO;
import model.personnel.PersonnelAttendanceDAO;
import model.schoolYear.SchoolYearDAO;
import model.student.StudentDAO;
import model.user.User;

/**
 *
 * @author MSI
 */
public class DashboardAcademicStaffServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      eventDAO eventDAO = new eventDAO();
        StudentDAO studentDAO = new StudentDAO();
        ClassDAO classDAO = new ClassDAO();
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        DayDAO dayDAO = new DayDAO();
        PersonnelAttendanceDAO personnelAttendanceDAO = new PersonnelAttendanceDAO();
        HttpSession session = request.getSession();
        
        
        // Ngày hiện tại
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = formatter.format(new Date());
        
         // 👉 Danh sách lớp học trong năm học hiện tại
        if (schoolYearDAO.getClosestSchoolYears() != null) {
            request.setAttribute("listClass", classDAO.getByStatus("đã được duyệt",
                    schoolYearDAO.getClosestSchoolYears().getId()));
        } else if (schoolYearDAO.getSchoolYearByDate(new Date()) != null) {
            request.setAttribute("listClass", classDAO.getByStatus("đã được duyệt",
                    schoolYearDAO.getSchoolYearByDate(new Date()).getId()));
        } else {
            request.setAttribute("listClass", null);
        }

        // 👉 Chấm công ngày hiện tại
        if (dayDAO.getDayByDate(formattedDate) != null) {
            User user = (User)session.getAttribute("user");
            request.setAttribute("attendance", personnelAttendanceDAO.getAttendanceByPersonnelAndDay(
                    user.getUsername(), dayDAO.getDayByDate(formattedDate).getId()));
        }

        // 👉 Sự kiện tương lai và số học sinh đang theo học
        request.setAttribute("listEvents", eventDAO.getFutureEvent(2));
        request.setAttribute("numberOfStudent", studentDAO.getStudentByStatus("đang theo học").size());

        // 👉 Forward về trang dashboard
      // 👉 Forward về trang dashboard (có đường dẫn đúng)
request.getRequestDispatcher("/academicstaff/dashboard.jsp").forward(request, response);


        
    } 

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
     
    }

 @Override
    public String getServletInfo() {
        return "Dashboard Academic Staff Servlet";
    }
}
