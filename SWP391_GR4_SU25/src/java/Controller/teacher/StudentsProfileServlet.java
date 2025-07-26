/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.teacher;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.student.Student;
import model.student.StudentDAO;

/**
 *
 * @author PC
 */
@WebServlet(name = "/teacher/StudentsProfileServlet", value = "/teacher/studentprofile")
public class StudentsProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getParameter("action").equalsIgnoreCase("edit")) {
            String id = request.getParameter("id");
            String schoolYear = request.getParameter("schoolYear");
            StudentDAO studentDAO = new StudentDAO();
            Student student = studentDAO.getStudentsById(id);
            request.setAttribute("student", student);
            request.setAttribute("schoolYear", schoolYear);
            request.getRequestDispatcher("editInformationStudent.jsp").forward(request, response);
        } else {
            String id = request.getParameter("id");
            HttpSession session = request.getSession();
            String schoolYear = "";
            schoolYear = request.getParameter("schoolYear");
            request.setAttribute("schoolYear", schoolYear);
            session.removeAttribute("schoolyear");
            StudentDAO studentDAO = new StudentDAO();
            Student student = studentDAO.getStudentsById(id);
            String success = (String) session.getAttribute("success");
            String error = (String) session.getAttribute("error");
            if (success != null) {
                request.setAttribute("toastType", "success");
                request.setAttribute("toastMessage", "Cập nhật thông tin thành công");
                session.removeAttribute(success);
            }
            if (error != null) {
                request.setAttribute("toastType", "error");
                request.setAttribute("toastMessage", "Cập nhật thông tin thất bại");
                session.removeAttribute(error);
            }
            request.setAttribute("student", student);
            request.getRequestDispatcher("informationStudents.jsp").forward(request, response);
        }
    }



}
