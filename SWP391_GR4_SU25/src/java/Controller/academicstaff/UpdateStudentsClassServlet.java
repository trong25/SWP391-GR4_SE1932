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
 *Servlet UpdateStudentsClassServlet xử lý các yêu cầu HTTP để cập nhật thông tin học sinh trong lớp học
 * và chỉnh sửa thông tin học sinh 
 * 
 * URL Mapping: /academicstaff/updatestudentclass
 * Chức năng:
 * -Nhận dữ liệu từ form
 * - gọi StudentDAO để lấy học sinh thông qua id để cập nhật và chỉnh sửa thông tin học snh và lưu vào cơ sở dữ liệu
 * Phân quyền: chỉ Giáo Vụ và giáo viên được phép chỉnh sửa thông tin học sinh trong lớp học.
 * @author TrongNV
 * @version 1.0
 */
public class UpdateStudentsClassServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
     
    } 

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      Student student = new Student();
      student.setId(request.getParameter("id").trim());
      student.setLastName(request.getParameter("lastName").trim());
      student.setFirstName(request.getParameter("firstName").trim());
      student.setFirstGuardianName(request.getParameter("first_guardian_name").trim());
        student.setFirstGuardianPhoneNumber(request.getParameter("firstGuardianPhoneNumber").trim());
        student.setSecondGuardianName(request.getParameter("second_guardian_name").trim());
        student.setSecondGuardianPhoneNumber(request.getParameter("secondGuardianPhoneNumber").trim());
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
         String schoolIdParam = request.getParameter("school_id");
         String schoolName = request.getParameter("schoolName");
    String addressSchool = request.getParameter("addressSchool");
    if (schoolName != null && !schoolName.trim().isEmpty() &&
        addressSchool != null && !addressSchool.trim().isEmpty()) {
        Schools school = new Schools();
        school.setId(schoolIdParam.trim());
        school.setSchoolName(schoolName.trim());
        school.setAddressSchool(addressSchool.trim());
        student.setSchool_id(school);  // gán đối tượng vào student
    }

    // Lớp học
    String className = request.getParameter("className");
    if (className != null && !className.trim().isEmpty()) {
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setClassName(className.trim());
        student.setSchool_class_id(schoolClass);  // gán đối tượng vào student
    }
        StudentDAO studentDAO = new StudentDAO();
        boolean p = studentDAO.updateStudent(student);
        HttpSession session = request.getSession();
        if (p == true) {
           
            session.setAttribute("success", "success");
        } else {
            session.setAttribute("error", "error");
        }
        request.getRequestDispatcher("studentprofileclass").forward(request, response);
    }


}