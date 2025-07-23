/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controllerr;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.user.UserDAO;

/**
 *
 * @author HuyDv
 */
@WebServlet(name="NewPassword", urlPatterns={"/newPassword"})
public class NewPassword extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      
    } 

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      UserDAO userDAO = new UserDAO();
        // Get the current session
        HttpSession session = request.getSession();

        // Retrieve the new password and confirm password from the request
        String newPassword = request.getParameter("password").trim();
        String confPassword = request.getParameter("confPassword").trim();

        // Check if new password and confirm password are not null and match
        if (newPassword != null && newPassword.equals(confPassword)) {

            // Get the user's email from the session
            String username = (String) session.getAttribute("username");

            // Attempt to update the user's password
            boolean success = userDAO.updateNewPassword(newPassword, userDAO.getByUsernameOrEmail(username).getId());

            if (success) {
                request.setAttribute("status", "Đã đổi mật khẩu thành công !");
            } else {
                request.setAttribute("status", "Đổi mật khẩu thất bại!");
            }
        } else {
            // Passwords do not match
            request.setAttribute("err", "Mật khẩu không khớp!");
            request.getRequestDispatcher("newPassword.jsp").forward(request, response);
        }
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
    }


