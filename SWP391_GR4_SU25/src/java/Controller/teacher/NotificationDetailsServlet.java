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
import java.util.Map;
import model.notification.Notification;
import model.notification.NotificationDAO;

/**
 *
 * @author PC
 */
@WebServlet(name = "/teacher/NotificationDetailsServlet", urlPatterns = {"/teacher/notificationdetails"})
public class NotificationDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<Integer, String> roleMap = new HashMap<>();
        roleMap.put(0, "NHÂN VIÊN IT");
        roleMap.put(1, "HIỆU TRƯỞNG");
        roleMap.put(2, "GIÁO VỤ");
        roleMap.put(3, "KẾ TOÁN");
        roleMap.put(4, "GIÁO VIÊN");
        roleMap.put(5, "PHỤ HUYNH");
        String id = request.getParameter("id");
        Notification notifi = new Notification();
        NotificationDAO notificationDAO = new NotificationDAO();
        notifi = notificationDAO.getNotificationById(id);
        request.setAttribute("roleMap", roleMap);
        request.setAttribute("notifi", notifi);
        request.getRequestDispatcher("notificationDetails.jsp").forward(request, response);
    }

}
