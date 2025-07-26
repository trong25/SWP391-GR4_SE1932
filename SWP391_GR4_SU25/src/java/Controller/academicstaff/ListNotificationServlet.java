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
import java.util.List;
import model.notification.Notification;
import model.notification.NotificationDAO;

/**
 *
 * @author PC
 */
@WebServlet(name = "/academicstaff/ListNotificationServlet", urlPatterns = {"/academicstaff/listnotification"})
public class ListNotificationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userId = request.getParameter("user_id");
        NotificationDAO notifiDAO = new NotificationDAO();
        List<Notification> notifi = notifiDAO.getListNotifiByUserId(userId);
        request.setAttribute("notifi", notifi);
        request.getRequestDispatcher("listNotification.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userId = request.getParameter("user_id");
        NotificationDAO notifiDAO = new NotificationDAO();
        List<Notification> notifi = notifiDAO.getListNotifiByUserId(userId);
        request.setAttribute("notifi", notifi);
        request.getRequestDispatcher("listNotification.jsp").forward(request, response);
    }


}
