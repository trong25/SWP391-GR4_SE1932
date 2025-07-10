/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.academicstaff;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.classes.ClassDAO;
import model.day.Day;
import model.day.DayDAO;
import model.timeslot.TimeSlot;
import model.timeslot.TimeSlotDAO;
import model.timetable.Timetable;
import model.timetable.TimetableDAO;
import model.week.Week;
import model.week.WeekDAO;

/**
 *
 * @author Admin
 */
@WebServlet(name = "/academicstaff/ViewTimetableServlet", urlPatterns = {"/academicstaff/view-timetable"})
public class ViewTimetableServlet extends HttpServlet {

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
        String classId = request.getParameter("classId");
        String weekId = request.getParameter("weekId");
        String status = request.getParameter("status");
        TimetableDAO timetableDAO = new TimetableDAO();
        DayDAO dayDAO = new DayDAO();
        TimeSlotDAO timeslotDAO = new TimeSlotDAO();
        ClassDAO classDAO = new ClassDAO();
        WeekDAO weekDAO = new WeekDAO();

        Week week = weekDAO.getWeek(weekId);
        List<Timetable> timetable = timetableDAO.getTimetableByClassAndWeek(classId, weekId, status);
        List<TimeSlot> timeslotList = timeslotDAO.getTimeslotsForTimetable();
        List<Day> dayList = dayDAO.getDayByWeek(weekId);
        model.classes.Class aClass = classDAO.getClassById(classId);

        request.setAttribute("week", week);
        request.setAttribute("aClass", aClass);
        request.setAttribute("timetable", timetable);
        request.setAttribute("timeslotList", timeslotList);
        request.setAttribute("dayList", dayList);

        request.getRequestDispatcher("viewTimetable.jsp").forward(request, response);
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
