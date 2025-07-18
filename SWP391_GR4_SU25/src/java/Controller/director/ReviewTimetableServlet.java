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
import java.util.List;
import model.timetable.Timetable;
import model.timetable.TimetableDAO;
import jakarta.servlet.annotation.WebServlet;

/**
 *
 * @author ThanhNT

 */
@WebServlet(name = "ReviewTimetableServlet", urlPatterns = {"/director/reviewTimetable"})
public class ReviewTimetableServlet extends HttpServlet {
   
    /** 
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Lấy danh sách thời khóa biểu đang chờ duyệt
        TimetableDAO timetableDAO = new TimetableDAO();
        List<Timetable> pendingTimetables = timetableDAO.getTimetablesByStatus("đang chờ xử lý");
        request.setAttribute("pendingTimetables", pendingTimetables);
        request.getRequestDispatcher("timetableWaitApprove.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String action = request.getParameter("action");
        String timetableId = request.getParameter("timetableId");
        TimetableDAO timetableDAO = new TimetableDAO();

        if ("approve".equals(action)) {
            timetableDAO.updateTimetableStatus(timetableId, "đã được duyệt");
            request.setAttribute("message", "Duyệt thời khóa biểu thành công!");
        } else if ("reject".equals(action)) {
            timetableDAO.updateTimetableStatus(timetableId, "bị từ chối");
            request.setAttribute("message", "Từ chối thời khóa biểu thành công!");
        }

        // Sau khi duyệt, load lại danh sách chờ duyệt
        List<Timetable> pendingTimetables = timetableDAO.getTimetablesByStatus("đang chờ xử lý");
        request.setAttribute("pendingTimetables", pendingTimetables);
        request.getRequestDispatcher("timetableWaitApprove.jsp").forward(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
