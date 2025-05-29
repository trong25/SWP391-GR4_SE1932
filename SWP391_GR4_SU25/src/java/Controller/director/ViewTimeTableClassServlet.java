/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.director;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.schoolYear.SchoolYear;
import model.schoolYear.SchoolYearDAO;
import model.week.WeekDAO;
import model.classes.Class;
import model.classes.ClassDAO;
import model.day.Day;
import model.day.DayDAO;
import model.timeslot.TimeSlot;
import model.timeslot.TimeSlotDAO;
import model.timetable.Timetable;
import model.timetable.TimetableDAO;
import model.week.Week;

/**
 *
 * @author ThanhNT
 */
public class ViewTimeTableClassServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ViewTimeTableClassServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewTimeTableClassServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        //processRequest(request, response);
        HttpSession session = request.getSession();
        String toastMessage = (String) session.getAttribute("toastMessage");
        String toastType = (String) session.getAttribute("toastType");
        if (toastMessage != null && toastType != null) {
            request.setAttribute("toastMessage", toastMessage);
            request.setAttribute("toastType", toastType);
            session.removeAttribute("toastType");
            session.removeAttribute("toastMessage");

        }
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        List<SchoolYear> schoolYearList = schoolYearDAO.getAll();
        request.setAttribute("schoolYearList", schoolYearList);
        request.getRequestDispatcher("viewTimetableClass.jsp").forward(request, response);

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
        //processRequest(request, response);
        String classId = request.getParameter("class");
        WeekDAO weekDAO = new WeekDAO();
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        model.classes.Class aclass = new ClassDAO().getClassById(classId);
        String week = request.getParameter("week");
        String schoolyear = request.getParameter("schoolyear");
        List<model.classes.Class> listClass = new ClassDAO().getBySchoolYearandStatus(schoolyear);
        if (listClass.isEmpty()) {
            HttpSession session = request.getSession();
            session.setAttribute("toastType", "error");
            session.setAttribute("toastMessage", "Năm này không có lớp học");
            response.sendRedirect("viewtimetableclass");
            return;
        }
        List<SchoolYear> schoolYearList = schoolYearDAO.getAll();
            List<Week> weekList = weekDAO.getWeeks(schoolyear);


        List<Timetable> timetable = new ArrayList<>();
        TimeSlotDAO timeSlotDAO = new TimeSlotDAO();
        List<TimeSlot> timeslotList = timeSlotDAO.getTimeslotsForTimetable();
        DayDAO dayDAO = new DayDAO();
        List<Day> dayList = dayDAO.getDayByWeek(week);
        if (dayList.size() > 0) {
            request.setAttribute("timeslotList", timeslotList);
        }
        if (classId != null && week != null && schoolyear != null) {
            timetable = new TimetableDAO().getTimetableByClassAndWeek(classId, week, "đã được duyệt");
        }
        request.setAttribute("listClass", listClass);
        request.setAttribute("timetable", timetable);
        request.setAttribute("classselect", classId);
        request.setAttribute("sltedsy", schoolyear);
        request.setAttribute("sltedw", week);
        request.setAttribute("schoolYearList", schoolYearList);
        request.setAttribute("weekList", weekList);
        request.setAttribute("dayList", dayList);
        request.setAttribute("aClass", aclass);
        request.getRequestDispatcher("viewTimetableClass.jsp").forward(request, response);
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
