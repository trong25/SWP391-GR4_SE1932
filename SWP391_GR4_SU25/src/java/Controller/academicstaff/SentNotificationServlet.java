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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.notification.Notification;
import model.notification.NotificationDAO;

/**
 *
 * @author PC
 */
@WebServlet(name = "/academicstaff/SentNotificationServlet", urlPatterns = {"/academicstaff/sentnotifidetails"})
public class SentNotificationServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

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
        roleMap.put(1, "HIỆU TRƯỞNG");
        roleMap.put(2, "GIÁO VỤ");
        roleMap.put(3, "GIÁO VIÊN");
        roleMap.put(4, "HỌC SINH");
        roleMap.put(5, "KẾ TOÁN");
        request.setAttribute("roleMap", roleMap);
        request.setAttribute("listrole_id", role_id);
        request.setAttribute("notifi", notifi);
        request.getRequestDispatcher("sentNotificationDetails.jsp").forward(request, response);
    }


}
