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
import java.text.SimpleDateFormat;
import java.util.Date;
import model.student.Student;
import model.student.StudentDAO;

/**
 *
 * @author MSI
 */
public class UpdateStudentsServlet extends HttpServlet {
   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdateStudentsServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdateStudentsServlet at " + request.getContextPath() + "</h1>");
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
     Student student = new Student();
     student.setId(request.getParameter("id").trim());
     student.setLastName(request.getParameter("lastName"));
      student.setFirstName(request.getParameter("firstName").trim());
        student.setFirstGuardianName(request.getParameter("first_guardian_name").trim());
        student.setFirstGuardianPhoneNumber(request.getParameter("firstGuardianPhoneNumber").trim());
        student.setSecondGuardianName(request.getParameter("second_guardian_name").trim());
        student.setSecondGuardianPhoneNumber(request.getParameter("secondGuardianPhoneNumber").trim());
        student.setAddress(request.getParameter("address").trim());
         
        student.setParentSpecialNote(request.getParameter("note").trim());
        student.setSchool_id(request.getParameter("school_id").trim());
        student.setSchool_class_id(request.getParameter("school_class_id").trim());
        student.setSchoolName(request.getParameter("schoolName").trim());
         student.setClassName(request.getParameter("classname").trim());
        student.setEmail(request.getParameter("email").trim());
       
         String birthdayStr = request.getParameter("birthday");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = null;
        String avatar = request.getParameter("image");
        String currentAvatar = request.getParameter("currentAvatar");
        
        if(avatar.isEmpty() || avatar.isBlank()){
            student.setAvatar(currentAvatar);
        }else{
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
        if(p==true){
         session.setAttribute("success", "success");
    }else{
            session.setAttribute("error", "error");
        }
request.getRequestDispatcher("studentprofile").forward(request, response);

}
    
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
}
