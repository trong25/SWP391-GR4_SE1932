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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import model.personnel.Personnel;
import model.personnel.PersonnelDAO;
import model.student.Student;
import model.student.StudentDAO;
import model.user.User;
import utils.Helper;

/**
 *
 * @author MSI
 */
public class StudentServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      
     StudentDAO studentDAO = new StudentDAO();

        String status = request.getParameter("status");
        List<Student> listStudents = studentDAO.getAllStudents();
        String newStudentId;
        if (studentDAO.getLatest() != null) {
            newStudentId = studentDAO.generateId(studentDAO.getLatest().getId());
        } else {
            newStudentId = "HS000001";
        }
        if (status != null) {
            switch (status) {
                case "all":
                    listStudents = studentDAO.getAllStudents();
                    break;
                case "pending":
                    listStudents = studentDAO.getStudentByStatus("đang chờ xử lý");
                    break;
                case "approve":
                    listStudents = studentDAO.getStudentByStatus("đang theo học");
                    break;
                case "decline":
                    listStudents = studentDAO.getStudentByStatus("không được duyệt");
                    break;
                case "stop":
                    listStudents = studentDAO.getStudentByStatus("đã thôi học");
                    break;
                default:
                    break;
            }
        }
        request.setAttribute("newStudentId", newStudentId);
        request.setAttribute("listStudent", listStudents);
        request.getRequestDispatcher("student.jsp").forward(request, response);
    }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      String action = request.getParameter("action");
        if (action.equals("create")) {
            HttpSession session = request.getSession();
            PersonnelDAO personnelDAO = new PersonnelDAO();
            StudentDAO studentDAO = new StudentDAO();
            User user = null;
            String toastMessage = "";
            String toastType = "error";
            if (session.getAttribute("user") != null) {
                String avatar = request.getParameter("avatar").trim();
                String firstName = request.getParameter("firstName").trim();
                String schoolName = request.getParameter("schoolName").trim();
                String lastName = request.getParameter("lastName").trim();
                String secondGuardianName = request.getParameter("secondGuardianName").trim();
                String firstGuardianName = request.getParameter("firstGuardianName").trim();
                String birth = request.getParameter("birth");
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date birthday = null;
                try {
                    birthday = formatter.parse(birth);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                String genderRaw = request.getParameter("gender");
                String email = request.getParameter("email").trim();
                String note = request.getParameter("note").trim();
                String address = request.getParameter("address").trim();
                String secondGuardianPhoneNumber = request.getParameter("secondGuardianPhoneNumber").trim();
                String firstGuardianPhoneNumber = request.getParameter("firstGuardianPhoneNumber").trim();

                String status = "đang chờ xử lý";
                user = (User) session.getAttribute("user");
                Personnel createdBy = personnelDAO.getPersonnelByUserId(user.getId());

                Student student = new Student(null, null, Helper.formatName(firstName), Helper.formatName(lastName), address, email, status, birthday, Integer.parseInt(genderRaw) == 1,
                        Helper.formatName(firstGuardianName), firstGuardianPhoneNumber, avatar, secondGuardianName.isBlank() ? null : Helper.formatName(secondGuardianName), secondGuardianPhoneNumber.isBlank() ? null : secondGuardianPhoneNumber, createdBy,
                        note, Helper.formatName(schoolName));
                ////   Stage for create pupil
                ////   Stage for create pupil

                if (address.isBlank() || email.isBlank() || firstGuardianPhoneNumber.isBlank() || avatar.isBlank() || genderRaw.equals("-1")
                        || Helper.formatName(firstName).isBlank() || Helper.formatName(lastName).isBlank()
                        || Helper.formatName(firstGuardianName).isBlank()) {
                    if (address.isBlank()) {
                        toastMessage = "Tạo thật bại ! Vui lòng không bỏ trống trường địa chỉ !";
                    } else if (Helper.formatName(firstName).isBlank()) {
                        toastMessage = "Tạo thật bại ! Vui lòng không bỏ trống trường tên !";
                    } else if (Helper.formatName(schoolName).isBlank()) {
                        toastMessage = "Tạo thật bại ! Vui lòng không bỏ trống trường họ !";
                    } else if (Helper.formatName(lastName).isBlank()) {
                        toastMessage = "Tạo thật bại ! Vui lòng không bỏ trống trường họ !";
                    } else if (Helper.formatName(firstGuardianName).isBlank()) {
                        toastMessage = "Tạo thật bại ! Vui lòng không bỏ trống trường họ tên người giám hộ 1 !";
                    } else if (firstGuardianPhoneNumber.isBlank()) {
                        toastMessage = "Tạo thật bại ! Vui lòng không bỏ trống trường số điện thoại người giám hộ 1 !";
                    } else if (avatar.isBlank()) {
                        toastMessage = "Tạo thật bại ! Vui lòng không bỏ trống trường hình ảnh !";
                    } else if (genderRaw.equals("-1")) {
                        toastMessage = "Tạo thật bại ! Vui lòng không bỏ trống trường giới tính !";
                    } else if (email.isBlank()) {
                        toastMessage = "Tạo thật bại ! Vui lòng không bỏ trống trường email !";
                    }
                    toastType = "error";
                    session.setAttribute("toastMessage", toastMessage);
                    session.setAttribute("toastType", toastType);
                    List<Student> listStudent = studentDAO.getAllStudents();
                    request.setAttribute("listPupil", listStudent);
                    request.setAttribute("newPupilId", request.getParameter("id"));
                    request.getRequestDispatcher("student.jsp").forward(request, response);
                } else if (!(avatar.endsWith("png") || avatar.endsWith("jpg"))) {
                    toastMessage = "Tạo thật bại ! Vui lòng chọn đúng tập hình ảnh !";
                    toastType = "error";
                    session.setAttribute("toastMessage", toastMessage);
                    session.setAttribute("toastType", toastType);
                    List<Student> listStudent = studentDAO.getAllStudents();
                    request.setAttribute("listStudent", listStudent);
                    request.setAttribute("newStudentId", request.getParameter("id"));
                    request.getRequestDispatcher("student.jsp").forward(request, response);
                } else if (secondGuardianName.isBlank() && !secondGuardianPhoneNumber.isBlank()) {
                    toastMessage = "Tạo thật bại ! Vui lòng nhập họ và tên người giám hộ thứ 2!";
                    toastType = "error";
                    session.setAttribute("toastMessage", toastMessage);
                    session.setAttribute("toastType", toastType);
                    List<Student> listPupil = studentDAO.getAllStudents();
                    request.setAttribute("listStudent", listPupil);
                    request.setAttribute("newStudentId", request.getParameter("id"));
                    request.getRequestDispatcher("student.jsp").forward(request, response);
                } else if (studentDAO.checkFirstGuardianPhoneNumberExists(firstGuardianPhoneNumber) || studentDAO.checkSecondGuardianPhoneNumberExists(secondGuardianPhoneNumber)) {
                    if (studentDAO.checkFirstGuardianPhoneNumberExists(firstGuardianPhoneNumber)) {
                        toastMessage = "Số điện thoại người giám hộ đầu tiên đã tồn tại !";
                    } else if (studentDAO.checkSecondGuardianPhoneNumberExists(secondGuardianPhoneNumber)) {
                        toastMessage = "Số điện thoại người giám hộ thứ hai đã tồn tại !";
                    }
                    session.setAttribute("toastMessage", toastMessage);
                    session.setAttribute("toastType", toastType);
                    List<Student> listStudent = studentDAO.getAllStudents();
                    request.setAttribute("listStudent", listStudent);
                    request.setAttribute("newStudentId", request.getParameter("id"));
                    request.getRequestDispatcher("student.jsp").forward(request, response);
                } else {
                    if (studentDAO.createStudent(student)) {
//                        boolean a;
//                        a = studentDAO.createStudent(student);
//                        System.out.println("errorA: " + a);
                        toastMessage = "Xác nhận thành công";
                        toastType = "success";
                        session.setAttribute("toastMessage", toastMessage);
                        session.setAttribute("toastType", toastType);
                        response.sendRedirect("student");

                    } else {
//                        boolean a;
//                        a = studentDAO.createStudent(student);
//                        System.out.println("errorB: " + a);
                        toastMessage = "Tạo thật bại ! Email hoặc số điện thoại đã tồn tại !";
                        toastType = "error";
                        session.setAttribute("toastMessage", toastMessage);
                        session.setAttribute("toastType", toastType);
                        List<Student> listPupil = studentDAO.getAllStudents();
                        request.setAttribute("listStudent", listPupil);
                        request.setAttribute("newStudentId", request.getParameter("id"));
                        request.getRequestDispatcher("student.jsp").forward(request, response);
                    }
                }
    }

        }
    
    }
}
