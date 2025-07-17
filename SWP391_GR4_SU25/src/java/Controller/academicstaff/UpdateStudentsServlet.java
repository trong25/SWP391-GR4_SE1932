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
import model.school.Schools;
import model.schoolclass.SchoolClass;
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
    student.setFirstName(request.getParameter("firstName").trim());
    student.setLastName(request.getParameter("lastName").trim());
    student.setFirstGuardianName(request.getParameter("first_guardian_name").trim());
    student.setFirstGuardianPhoneNumber(request.getParameter("firstGuardianPhoneNumber").trim());

    String secondGuardianName = request.getParameter("second_guardian_name");
    if (secondGuardianName != null && !secondGuardianName.trim().isEmpty()) {
        student.setSecondGuardianName(secondGuardianName.trim());
    }

    String secondGuardianPhoneNumber = request.getParameter("secondGuardianPhoneNumber");
    if (secondGuardianPhoneNumber != null && !secondGuardianPhoneNumber.trim().isEmpty()) {
        student.setSecondGuardianPhoneNumber(secondGuardianPhoneNumber.trim());
    }

    student.setAddress(request.getParameter("address").trim());
    student.setParentSpecialNote(request.getParameter("note").trim());
    student.setEmail(request.getParameter("email").trim());

    // Xử lý avatar
    String avatar = request.getParameter("image");
    String currentAvatar = request.getParameter("currentAvatar");
    if (avatar == null || avatar.isBlank()) {
        student.setAvatar(currentAvatar);
    } else {
        student.setAvatar(avatar);
    }

    // Xử lý ngày sinh
    String birthdayStr = request.getParameter("birthday");
    try {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = formatter.parse(birthdayStr);
        student.setBirthday(birthday);
    } catch (Exception e) {
        e.printStackTrace(); // log lỗi
    }

    // Chuyển school_id thành Schools object
    String schoolIdParam = request.getParameter("school_id");
    if (schoolIdParam != null && !schoolIdParam.trim().isEmpty()) {
        Schools school = new Schools();
        school.setId(schoolIdParam.trim());
        student.setSchool_id(school);
    }

    // Chuyển school_class_id thành SchoolClass object
    String schoolClassIdParam = request.getParameter("school_class_id");
    String gradeLevel = request.getParameter("grade_level");
   if (schoolClassIdParam != null && !schoolClassIdParam.trim().isEmpty()) {
    SchoolClass schoolClass = new SchoolClass();
    schoolClass.setId(schoolClassIdParam.trim());

    if (gradeLevel != null && !gradeLevel.trim().isEmpty()) {
        schoolClass.setGrade_level(gradeLevel.trim());
    }


    student.setSchool_class_id(schoolClass);
}

    
    

    // Cập nhật sinh viên
    StudentDAO studentDAO = new StudentDAO();
    boolean updated = studentDAO.updateStudent(student);
    HttpSession session = request.getSession();

    if (updated) {
        Student updatedStudent = studentDAO.getStudentByIdWithNames(student.getId());
        request.setAttribute("student", updatedStudent);
        session.setAttribute("success", "Cập nhật thành công!");
    } else {
        session.setAttribute("error", "Cập nhật thất bại!");
    }

    request.getRequestDispatcher("studentprofile").forward(request, response);
}

    
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
}
