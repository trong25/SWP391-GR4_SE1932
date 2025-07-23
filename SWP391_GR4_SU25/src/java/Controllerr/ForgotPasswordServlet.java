/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controllerr;

import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.user.User;
import model.user.UserDAO;

/**
 *
 * @author HuyDV
 */
public class ForgotPasswordServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
      
    } 

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
     String key = request.getParameter("email");
      RequestDispatcher dispatcher = null;
        HttpSession session = request.getSession();
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getByUsernameOrEmail(key);
        if(user == null){
            request.setAttribute("error", "Email hoặc tài khoản bạn nhập không tồn tại vui lòng nhập lại!");
            request.getRequestDispatcher("forgotPassword.jsp").forward(request, response);
        }
        else if (key != null || !key.equals("")) {
            // update password generate random
            userDAO.resetPassword(key);
            session.setAttribute("username", user.getUsername());
            dispatcher = request.getRequestDispatcher("enterNewPassword.jsp");
            request.setAttribute("message", "Mật khẩu đã được gửi đến bạn, vui lòng kiểm tra email !");
            dispatcher.forward(request, response);
        }

    }
    
    

}