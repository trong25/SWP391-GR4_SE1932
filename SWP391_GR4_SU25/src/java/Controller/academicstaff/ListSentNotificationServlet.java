/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.academicstaff;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.notification.Notification;
import model.notification.NotificationDAO;
import model.personnel.Personnel;

/**
 *
 * @author PC
 */
@WebServlet(name = "/academicstaff/ListSentNotificationServlet", urlPatterns = {"/academicstaff/listsentnotifi"})
public class ListSentNotificationServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ListSentNotificationServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListSentNotificationServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Personnel personnel = (Personnel) session.getAttribute("personnel");
        String id = personnel.getId();
        NotificationDAO notifiDAO = new NotificationDAO();
        List<Notification> notifi = notifiDAO.getListSentNotifiById(id);
        request.setAttribute("notifi", notifi);
        request.getRequestDispatcher("listNotifisent.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
