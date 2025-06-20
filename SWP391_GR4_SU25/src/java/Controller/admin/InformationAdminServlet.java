/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */




package Controller.admin;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet InformationAdminServlet dùng để hiển thị trang thông tin cá nhân của quản trị viên.
 *
 * Chịu trách nhiệm:
 * - Lấy thông tin thông báo (toast) từ session nếu có và chuyển sang attribute để hiển thị trên giao diện.
 * - Điều hướng đến trang `information.jsp`, nơi hiển thị thông tin tài khoản quản trị viên.
 *
 * Được sử dụng bởi: giao diện `information.jsp` trong module quản trị hệ thống.
 *
 * Ví dụ: Sau khi quản trị viên cập nhật mật khẩu hoặc thông tin cá nhân, hệ thống sẽ hiển thị lại trang thông tin
 * kèm theo thông báo thành công/thất bại.
 *
 * @author HuyDV
 * @version 1.0
 */
public class InformationAdminServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       HttpSession session = request.getSession();
        String toastType = "", toastMessage = "";
        if (session.getAttribute("toastType") != null) {
            toastType = session.getAttribute("toastType").toString();
            toastMessage = session.getAttribute("toastMessage").toString();
        }
        session.removeAttribute("toastType");
        session.removeAttribute("toastMessage");
        request.setAttribute("toastType", toastType);
        request.setAttribute("toastMessage", toastMessage);
        request.getRequestDispatcher("information.jsp").forward(request, response);
    } 

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      request.getRequestDispatcher("information.jsp").forward(request, response);
    }


}