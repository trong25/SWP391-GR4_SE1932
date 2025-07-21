/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.academicstaff;

import StudentWait.StudentWait;
import StudentWait.StudentWaitDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.awt.Desktop;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.personnel.Personnel;
import model.personnel.PersonnelDAO;
import model.school.Schools;
import model.schoolclass.SchoolClass;
import model.student.Student;
import model.student.StudentDAO;
import model.user.User;
import utils.Helper;

/**
 *
 * @author MSI
 */
public class StudentWaitServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      StudentWaitDAO studentWaitDAO = new StudentWaitDAO();
        List<StudentWait> listStudentWait = studentWaitDAO.getAllStudentWait();
        request.setAttribute("listStudentWait", listStudentWait);
        request.getRequestDispatcher("studentWait.jsp").forward(request, response);
    } 

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      String action = request.getParameter("action");
       if (action != null && action.equals("create")) {
            HttpSession session = request.getSession();
           
            StudentWaitDAO studentWaitDAO = new StudentWaitDAO();

            User user = (User) session.getAttribute("user");
            String toastMessage = "";
            String toastType = "error";

            if (user != null) {
                try {
                    
                    String firstName = request.getParameter("firstName").trim();
                    String lastName = request.getParameter("lastName").trim();
                   
                    String birth = request.getParameter("birth");
                    String genderRaw = request.getParameter("gender");
                    String email = request.getParameter("email").trim();
//                    String note = request.getParameter("note").trim();
                    String address = request.getParameter("address").trim();
                    String phoneNumber = request.getParameter("phoneNumber").trim();
                    

                    // Parse ngày sinh
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date birthday = formatter.parse(birth);

                    

//                    // Tạo đối tượng Student
//                    Student student = new Student(null, user.getId(), Helper.formatName(firstName), Helper.formatName(lastName), address,
//                            email, status, birthday, Integer.parseInt(genderRaw) == 1, Helper.formatName(firstGuardianName),
//                            firstGuardianPhoneNumber, avatar,
//                            secondGuardianName.isBlank() ? null : Helper.formatName(secondGuardianName),
//                            secondGuardianPhoneNumber.isBlank() ? null : secondGuardianPhoneNumber,
//                            createdBy, note, school, schoolClass);

                        StudentWait studentWait = new StudentWait(null, Helper.formatName(firstName), Helper.formatName(lastName), address, email, birthday, Integer.parseInt(genderRaw) == 1, phoneNumber.isBlank()?null : phoneNumber);

                    if (address.isBlank() || email.isBlank() || phoneNumber.isBlank()  || genderRaw.equals("-1")
                            || Helper.formatName(firstName).isBlank() || Helper.formatName(lastName).isBlank()) {
                        if (address.isBlank()) {
                            toastMessage = "Tạo thật bại ! Vui lòng không bỏ trống trường địa chỉ !";
                        }  else if (Helper.formatName(firstName).isBlank()) {
                            toastMessage = "Tạo thật bại ! Vui lòng không bỏ trống trường tên !";
                        } else if (Helper.formatName(lastName).isBlank()) {
                            toastMessage = "Tạo thật bại ! Vui lòng không bỏ trống trường họ !";
                        }  else if (phoneNumber.isBlank()) {
                            toastMessage = "Tạo thật bại ! Vui lòng không bỏ trống trường số điện thoại";
                        }  else if (genderRaw.equals("-1")) {
                            toastMessage = "Tạo thật bại ! Vui lòng không bỏ trống trường giới tính !";
                        } else if (email.isBlank()) {
                            toastMessage = "Tạo thật bại ! Vui lòng không bỏ trống trường email !";
                        }
                        toastType = "error";
                        session.setAttribute("toastMessage", toastMessage);
                        session.setAttribute("toastType", toastType);
                        List<StudentWait> listStudent = studentWaitDAO.getAllStudentWait();
                        request.setAttribute("listStudent", listStudent);
                        request.setAttribute("newStudentId", request.getParameter("id"));
                        request.getRequestDispatcher("studentWait.jsp").forward(request, response);
                    }  else if (studentWaitDAO.checkPhoneNumberExists(phoneNumber) ) {
                        if (studentWaitDAO.checkPhoneNumberExists(phoneNumber)) {
                            toastMessage = "Số điện thoại Bố đã tồn tại";
                        }
                        session.setAttribute("toastMessage", toastMessage);
                        session.setAttribute("toastType", toastType);
                        List<StudentWait> listStudent = studentWaitDAO.getAllStudentWait();
                        request.setAttribute("listStudent", listStudent);
                        request.setAttribute("newStudentId", request.getParameter("id"));
                        request.getRequestDispatcher("studentWait.jsp").forward(request, response);
                    } else {
                        if (studentWaitDAO.createStudentWait(studentWait)) {
                            toastMessage = "Xác nhận thành công";
                            toastType = "success";
                            session.setAttribute("toastMessage", toastMessage);
                            session.setAttribute("toastType", toastType);
                            response.sendRedirect("studentwait");

                        } else {
                            toastMessage = "Tạo học sinh thất bại! Vui lòng kiểm tra lại dữ liệu nhập vào hoặc hệ thống.";
                            toastType = "error";
                            session.setAttribute("toastMessage", toastMessage);
                            session.setAttribute("toastType", toastType);
                            List<StudentWait> listStudent = studentWaitDAO.getAllStudentWait();
                            request.setAttribute("listStudent", listStudent);
                            request.setAttribute("newStudentId", request.getParameter("id"));
                            request.getRequestDispatcher("studentWait.jsp").forward(request, response);
                        }
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(StudentServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }


}
