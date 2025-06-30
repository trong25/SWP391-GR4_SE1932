/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package controllerr;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.personnel.Personnel;
import model.personnel.PersonnelDAO;
import model.student.Student;
import model.student.StudentDAO;
import model.user.User;
import model.user.UserDAO;

/**
 *
 * @author HuyDV
 */
public class LoginServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      HttpSession session = request.getSession();
       if (session.getAttribute("error") != null) {
            String error = (String) session.getAttribute("error");
            request.setAttribute("error", error);
            session.removeAttribute("error");
        }
        request.getRequestDispatcher("login.jsp").forward(request, response);
    } 

  
   @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get data from login form
        String username = request.getParameter("username").trim();
        String password = request.getParameter("password").trim();

        // Check data login
        UserDAO userDAO = new UserDAO();

        User user = userDAO.getUserByUsernamePassword(username, password);

        if (user != null && user.getIsDisabled() != 1) {

            // Lấy thông tin personnel tương ứng với user id
           PersonnelDAO personnelDAO = new PersonnelDAO();

            Personnel personnel = personnelDAO.getPersonnelByUserId(user.getId());

            if (personnel != null) {
                // Lưu thông tin personnel vào session
                HttpSession session = request.getSession();
                session.setAttribute("personnel", personnel);
            }

            // Lấy thông tin student
            StudentDAO studentDAO = new StudentDAO();
            Student student = studentDAO.getStudentByUserId(user.getId());

            System.out.println(student);
            if (student != null) {
                // Lưu thông tin student vào session
                HttpSession session = request.getSession();
                session.setAttribute("student", student);
            }

            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            // Login success
            switch (user.getRoleId()) {
                case 0:
                    response.sendRedirect("admin/dashboard");
                    break;
                case 1:
                    response.sendRedirect("director/dashboard");
                    break;
                case 2:
                    response.sendRedirect("academicstaff/dashboard");
                    break;
                case 3:
                    response.sendRedirect("teacher/dashboard");
                    break;
                case 4:
                    response.sendRedirect("student/dashboard");

                    break;
                case 5:
                    response.sendRedirect("accountant/dashboard");

                    break;
                default:
                    request.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu");
                    request.removeAttribute("error");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    break;
            }
        } else {
            HttpSession session = request.getSession();
            session.setAttribute("error", "Sai tên đăng nhập hoặc mật khẩu !");
            response.sendRedirect("login");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
