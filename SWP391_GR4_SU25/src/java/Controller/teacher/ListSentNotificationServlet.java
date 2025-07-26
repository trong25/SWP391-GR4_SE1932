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
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.notification.Notification;
import model.notification.NotificationDAO;
import model.personnel.Personnel;

/**
 *
 * @author PC
 */
@WebServlet(name = "/teacher/ListSentNotificationServlet", urlPatterns = {"/teacher/listsentnotifi"})
public class ListSentNotificationServlet extends HttpServlet {

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


}
