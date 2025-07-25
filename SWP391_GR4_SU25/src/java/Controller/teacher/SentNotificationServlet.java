/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.teacher;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.notification.Notification;
import model.notification.NotificationDAO;

/**
 *
 * @author PC
 */
@WebServlet(name = "/teacher/SentNotificationServlet", urlPatterns = {"/teacher/sentnotifidetails"})
public class SentNotificationServlet extends HttpServlet {

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
            out.println("<title>Servlet SentNotificationServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SentNotificationServlet at " + request.getContextPath() + "</h1>");
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
        String id = request.getParameter("id");
        String createby = request.getParameter("createby");
        Notification notifi = new Notification();
        NotificationDAO notificationDAO = new NotificationDAO();
        notifi = notificationDAO.getNotificationById(id);
        List<Integer> role_id = notificationDAO.getRoleSentNotifiByIdandCreatBy(id, createby);
        Map<Integer, String> roleMap = new HashMap<>();
        roleMap.put(0, "QUẢN TRỊ VIÊN");
        roleMap.put(1, "GIÁM ĐỐC");
        roleMap.put(2, "GIÁO VỤ");
        roleMap.put(3, "KẾ TOÁN");
        roleMap.put(4, "GIÁO VIÊN");
        roleMap.put(5, "PHỤ HUYNH");
        request.setAttribute("roleMap", roleMap);
        request.setAttribute("listrole_id", role_id);
        request.setAttribute("notifi", notifi);
        request.getRequestDispatcher("sentNotificationDetails.jsp").forward(request, response);
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
