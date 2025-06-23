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
import model.user.UserDAO;

/**
 * Lớp ResetPassword dùng để xử lý yêu cầu đặt lại mật khẩu cho người dùng theo email.
 * 
 * Chịu trách nhiệm nhận email từ form, gọi phương thức reset mật khẩu trong UserDAO,
 * và chuyển hướng kết quả (thành công hoặc thất bại) về trang quản lý người dùng.
 * 
 * 
 * @author ASUS VIVOBOOK
 * @version 1.0
 */

public class ResetPassword extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      
    } 

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       String email = request.getParameter("email");
        UserDAO dao = new UserDAO();
        boolean a = dao.resetPassword(email);
        HttpSession session = request.getSession();
        if (a == true) {
            session.setAttribute("success", "success");
        } else {
            session.setAttribute("error", "error");
        }
        request.getRequestDispatcher("manageruser").forward(request, response);
    }


}