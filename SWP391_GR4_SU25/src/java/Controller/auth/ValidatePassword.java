/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.auth;

import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.user.UserDAO;

/**
 *
 * @author HuyDV
 */
public class ValidatePassword extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      
    } 

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      String currentPassword = request.getParameter("currentPassword").trim();

        RequestDispatcher dispatcher = null;
        UserDAO userDAO = new UserDAO();
        HttpSession session = request.getSession();
        // Check if user enter new password correct
        try {
            if (userDAO.checkPassword(currentPassword, (String) session.getAttribute("username"))) {
                request.setAttribute("status", "success");
                dispatcher = request.getRequestDispatcher("newPassword.jsp");
                dispatcher.forward(request, response);

            } else {
                request.setAttribute("error", "Sai mật khẩu, vui lòng thử lại !");
                dispatcher = request.getRequestDispatcher("enterNewPassword.jsp");
                dispatcher.forward(request, response);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    }


