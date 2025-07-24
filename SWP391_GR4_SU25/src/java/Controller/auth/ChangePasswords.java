/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.auth;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.user.User;
import model.user.UserDAO;

/**
 *
 * @author Huy
 */
public class ChangePasswords extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ChangePasswords</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ChangePasswords at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

 
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
        User user = (User) session.getAttribute("user");
        // Retrieve the new password and confirm password from the request
        String oldPass = request.getParameter("oldPassword").trim();
        String newPassword = request.getParameter("newPassword").trim();
        String confPassword = request.getParameter("confirmPassword").trim();

        try {
            if (userDAO.checkPassword(oldPass, user.getUsername()) && newPassword.equals(confPassword)) {
                boolean result = userDAO.updateNewPassword(newPassword, user.getId());
                if (result) {
                    session.setAttribute("toastType", "success");
                    session.setAttribute("toastMessage", "Đã cập nhật thành công !");
                } else {
                    session.setAttribute("toastType", "error");
                    session.setAttribute("toastMessage", "Đã cập nhật thất bại !");
                }
            } else {
                session.setAttribute("toastType", "error");
                session.setAttribute("toastMessage", "Mật khẩu không trùng khớp !");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        switch (user.getRoleId()) {
            // role id = 0 : admin.
            case 0:
                response.sendRedirect("admin/information");
                break;
            case 1:
                // role id = 1 : Director.
                response.sendRedirect("director/information");
                break;
            case 2:
                // role id = 2 : academic staff.
                response.sendRedirect("academicstaff/information");
                break;
            case 3:
                // role id = 3: Teacher .
                response.sendRedirect("teacher/information");
                break;
            case 4:
                // role id = 4 : student
                response.sendRedirect("student/information");
                break;
            case 5:
                // role id = 5 : Accountant.
                response.sendRedirect("accountant/information");
                break;    
                
            default:
                throw new AssertionError();
        }
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
