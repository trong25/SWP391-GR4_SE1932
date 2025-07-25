/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.student;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.notification.Notification;
import model.notification.NotificationDAO;
import model.user.User;

/**
 * Servlet NotificationdetailsServlet xử lý các yêu cầu HTTP để hiển thị chi
 * tiết một thông báo cụ thể cho học sinh.
 *
 * URL Mapping: /student/notificationdetails
 *
 * Chức năng: - Kiểm tra người dùng đã đăng nhập chưa (qua session) - Nhận ID
 * của thông báo từ request parameter (?id=...) - Gọi NotificationDAO để lấy chi
 * tiết thông báo từ CSDL - Chuyển tiếp dữ liệu sang trang
 * notificationDetails.jsp để hiển thị chi tiết
 *
 * Phân quyền: Chỉ học sinh đã đăng nhập mới được phép xem chi tiết thông báo
 *
 * @author KIenPN
 * @version 1.0
 */
@WebServlet(name = "Notificationdetails", urlPatterns = {"/student/notificationdetails"})
public class NotificationdetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect("/login");
            return;
        }
        String nID = request.getParameter("id");
        NotificationDAO notificationDAO = new NotificationDAO();
        Notification notification = notificationDAO.getNotificationById(nID);
        request.setAttribute("notifi", notification);
        request.getRequestDispatcher("notificationDetails.jsp").forward(request, response);
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
