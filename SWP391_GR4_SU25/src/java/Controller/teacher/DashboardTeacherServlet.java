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

import model.event.eventDAO;

import model.notification.NotificationDAO;

import model.personnel.Personnel;
import model.personnel.PersonnelAttendanceDAO;

import model.student.Student;
import model.student.StudentDAO;

import model.role.RoleDAO;

import model.schoolYear.SchoolYearDAO;
import model.user.User;

/**
 *
 * @author Admin
 */
public class DashboardTeacherServlet extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        eventDAO eventDAO = new eventDAO();
        StudentDAO pupilDAO = new StudentDAO();
        ClassDAO classDAO = new ClassDAO();

        HttpSession session = request.getSession();
        Personnel personnel = (Personnel) session.getAttribute("personnel");
        
        ClassDAO dao = new ClassDAO();
        Class individualClass = dao.getClassNameByTeacher(personnel.getId());
        if (individualClass != null) {
            StudentDAO studentDAO = new StudentDAO();
            int listStudentInClass = studentDAO.getSumStudentInClass(individualClass.getId());
            ApplicationDAO applicationDAO = new ApplicationDAO();
            RoleDAO roleDAO = new RoleDAO();
            String roleName = roleDAO.getRoleName(personnel.getRoleId());
            int sumApplication = applicationDAO.getForPersonnel(roleName).size();

            NotificationDAO notificationDAO = new NotificationDAO();
            int sumNotification = notificationDAO.getListNotifiByUserId(personnel.getUserId()).size();
            request.setAttribute("listEvents", eventDAO.getFutureEvent(2));
            request.setAttribute("numberOfStudent", pupilDAO.getStudentByStatus("đang theo học").size());
            request.setAttribute("listPupilInClass", listStudentInClass);
            request.setAttribute("sumApplication", sumApplication);
            request.setAttribute("sumNotification", sumNotification);
        }
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
