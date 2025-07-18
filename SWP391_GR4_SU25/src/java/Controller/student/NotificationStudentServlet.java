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
import java.util.List;
import model.notification.Notification;
import model.notification.NotificationDAO;
import model.user.User;

/**
 * Servlet NotificationStudentServlet xử lý các yêu cầu HTTP để hiển thị danh sách thông báo của học sinh.
 * 
 * URL Mapping: /student/NotificationStudent
 * 
 * Chức năng:
 * - Kiểm tra người dùng đã đăng nhập hay chưa (lấy từ session)
 * - Nếu chưa đăng nhập, chuyển hướng sang trang đăng nhập
 * - Nếu đã đăng nhập, gọi NotificationDAO để lấy danh sách thông báo dựa trên user ID
 * - Chuyển tiếp dữ liệu thông báo đến trang listNotification.jsp để hiển thị
 * 
 * Phân quyền: Chỉ học sinh đã đăng nhập mới có thể xem danh sách thông báo
 * 
 * @author KienPN
 * @version 1.0
 */
@WebServlet(name = "NotificationStudent", urlPatterns = {"/student/NotificationStudent"})
public class NotificationStudentServlet extends HttpServlet {

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
        User user = (User) request.getSession().getAttribute("user");
        NotificationDAO notificationDAO = new NotificationDAO();
        List<Notification> notifications = notificationDAO.getListNotifiByUserId(user.getId());
        request.setAttribute("notifications", notifications);
        request.getRequestDispatcher("listNotification.jsp").forward(request, response);
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
