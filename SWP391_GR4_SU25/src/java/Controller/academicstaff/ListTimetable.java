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
import model.timetable.Timetable;
import model.timetable.TimetableDAO;
import model.timetable.TimetableDTO;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ListTimetable", urlPatterns = {"/academicstaff/listtimetable"})
public class ListTimetable extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //classid , date_id(startdate-enddate) , createby , status , teacherid
        String id = request.getParameter("id");
        TimetableDAO timetableDAO = new TimetableDAO();
        List<TimetableDTO> listTimetable = timetableDAO.getTimetableByClassAndWeekAndCreateBy(id);
        request.setAttribute("listTimetable", listTimetable);
        request.getRequestDispatcher("listTimetable.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
