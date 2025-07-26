/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.teacher;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import model.application.ApplicationDAO;
import model.classes.ClassDAO;
import model.classes.Class;
import model.day.DayDAO;
import model.notification.NotificationDAO;
import model.personnel.Personnel;
import model.personnel.PersonnelAttendanceDAO;
import model.student.Student;
import model.student.StudentDAO;
import model.role.RoleDAO;
import model.schoolYear.SchoolYearDAO;
import model.user.User;
import model.timetable.TimetableDAO;
import model.day.Day;
import model.notification.Notification;
import model.application.Application;

/**
 *
 * @author PC
 */
public class DashboardTeacherServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StudentDAO studentDAO = new StudentDAO();
        ClassDAO classDAO = new ClassDAO();

        HttpSession session = request.getSession();
        Personnel personnel = (Personnel) session.getAttribute("personnel");
        
        ClassDAO dao = new ClassDAO();
        Class individualClass = dao.getClassNameByTeacher(personnel.getId());
        if (individualClass != null) {
            
            int listStudentInClass = studentDAO.getSumStudentInClass(individualClass.getId());
            RoleDAO roleDAO = new RoleDAO();
            String roleName = roleDAO.getRoleName(personnel.getRoleId());
            NotificationDAO notificationDAO = new NotificationDAO();
            List<Notification> listNotifications = notificationDAO.getListNotifiByUserId(personnel.getUserId());
            int sumNotification = listNotifications.size();
            request.setAttribute("numberOfStudent", studentDAO.getStudentByStatus("đang theo học").size());
            request.setAttribute("listStudentInClass", listStudentInClass);

            request.setAttribute("sumNotification", sumNotification);
            request.setAttribute("listNotifications", listNotifications);


            TimetableDAO timetableDAO = new TimetableDAO();
            DayDAO dayDAO = new DayDAO();
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String today = sdf.format(new Date());
                Day todayDay = dayDAO.getDayByDate(today);
                if (todayDay != null) {
                    int todayClasses = timetableDAO.getTodayClassesCount(personnel.getId(), todayDay.getId());
                    request.setAttribute("todayClasses", todayClasses);
                } else {
                    request.setAttribute("todayClasses", 0);
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("todayClasses", 0);
            }
        }
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }


}
