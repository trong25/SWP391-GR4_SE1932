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
import model.user.UserDAO;

/**
 *
 * @author ASUS VIVOBOOK
 */
public class DashboardAdminServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      UserDAO userDAO = new UserDAO();

        try {
            int totalAccounts = userDAO.countTotalUsers();
            int blockedAccounts = userDAO.countBlockedUsers();

            request.setAttribute("totalAccounts", totalAccounts);
            request.setAttribute("blockedAccounts", blockedAccounts);
        } catch (Exception e) {
            e.printStackTrace();
            
        } 
        request.getRequestDispatcher("../admin/dashboard.jsp").forward(request, response);
    }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      request.getRequestDispatcher("../dashboard.jsp").forward(request, response);
    }


}