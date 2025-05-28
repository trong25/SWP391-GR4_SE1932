/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.academicstaff;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.student.Student;
import model.student.StudentDAO;

/**
 *
 * @author MSI
 */
public class StudentsProfileServlet extends HttpServlet {
   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StudentsProfileServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StudentsProfileServlet at " + request.getContextPath() + "</h1>");
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
     String action = request.getParameter("action");
     if(action.equalsIgnoreCase("edit")){
         String id = request.getParameter("id");
         StudentDAO studentDAO = new StudentDAO();
         Student student = studentDAO.getStudentsById(id);
         request.setAttribute("student", student);
         request.getRequestDispatcher("editInformationStudent.jsp").forward(request, response);
     }else{
         String id = request.getParameter("id");
          StudentDAO studentDAO = new StudentDAO();
           Student student = studentDAO.getStudentsById(id);
            HttpSession session = request.getSession();
            String success = (String) session.getAttribute("success");
            String error = (String) session.getAttribute("error");
            if (success != null) {
                request.setAttribute("toastType", "success");
                request.setAttribute("toastMessage", "Cập nhật thông tin thành công");
                session.removeAttribute("success");
            }
            if (error != null) {
                request.setAttribute("toastType", "error");
                request.setAttribute("toastMessage", "Cập nhật thông tin thất bại");
                session.removeAttribute("error");
            }
            request.setAttribute("student", student);
            request.getRequestDispatcher("informationStudents.jsp").forward(request, response);
     }
    }


}
