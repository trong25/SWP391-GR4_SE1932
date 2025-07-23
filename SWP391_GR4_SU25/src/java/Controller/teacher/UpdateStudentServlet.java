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
import java.text.SimpleDateFormat;
import java.util.Date;

import model.student.Student;
import model.student.StudentDAO;

/**
 *
 * @author PC
 */
@WebServlet(name = "UpdateStudentServlet", urlPatterns = {"/teacher/updatestudent"})
public class UpdateStudentServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateStudentServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateStudentServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        String schoolYear = request.getParameter("schoolYear");
        request.setAttribute("schoolYear", schoolYear);
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
        Student student = new Student();
        student.setId(request.getParameter("id").trim());
        student.setLastName(request.getParameter("lastName").trim());
        student.setFirstName(request.getParameter("firstName").trim());
//        student.setfirstGuardianName(request.getParameter("first_guardian_name").trim());
//        student.setfirstGuardianPhoneNumber(request.getParameter("firstGuardianPhoneNumber").trim());
//        student.setsecondGuardianName(request.getParameter("second_guardian_name").trim());
//        student.setsecondGuardianPhoneNumber(request.getParameter("secondGuardianPhoneNumber").trim());
        student.setAddress(request.getParameter("address").trim());
        student.setParentSpecialNote(request.getParameter("note").trim());
        student.setEmail(request.getParameter("email").trim());
        String birthdayStr = request.getParameter("birthday");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = null;
        String avatar = request.getParameter("image");
        String currentAvatar = request.getParameter("currentAvatar");
        if (avatar.isEmpty() || avatar.isBlank()) {
            student.setAvatar(currentAvatar);
        } else {
            student.setAvatar(avatar);
        }
        try {
            birthday = formatter.parse(birthdayStr);
            student.setBirthday(birthday);
        } catch (Exception e) {
            e.printStackTrace();
        }
        StudentDAO studentDAO = new StudentDAO();
        boolean p = studentDAO.updateStudent(student);
        HttpSession session = request.getSession();
        if (p == true) {
            session.setAttribute("success", "success");
        } else {
            session.setAttribute("error", "error");
        }
        request.getRequestDispatcher("studentprofile").forward(request, response);
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
