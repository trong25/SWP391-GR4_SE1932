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
import java.util.logging.Level;
import java.util.logging.Logger;
import model.personnel.Personnel;
import model.personnel.PersonnelDAO;
import model.school.SchoolDAO;
import model.school.Schools;
import model.schoolclass.SchoolClass;
import model.schoolclass.SchoolClassDAO;
import model.student.Student;
import model.student.StudentDAO;
import model.user.User;
import utils.Helper;

/**
 *
 * @author TrongNV
 */
public class StudentServlet extends HttpServlet {

    @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String schoolIdParam = request.getParameter("schoolId");

    // Xử lý khi được gọi từ JS để lấy danh sách lớp theo mã trường (AJAX)
    if (schoolIdParam != null && request.getHeader("X-Requested-With") != null
            && request.getHeader("X-Requested-With").equals("XMLHttpRequest")) {
        response.setContentType("application/json;charset=UTF-8");

        SchoolClassDAO schoolClassDAO = new SchoolClassDAO();
        List<SchoolClass> classList = schoolClassDAO.getSchoolClassesBySchoolId(schoolIdParam);

        PrintWriter out = response.getWriter();
        StringBuilder jsonBuilder = new StringBuilder();
        jsonBuilder.append("[");

        for (int i = 0; i < classList.size(); i++) {
            SchoolClass sc = classList.get(i);
            jsonBuilder.append("{");
            jsonBuilder.append("\"id\":\"").append(sc.getId()).append("\",");
            jsonBuilder.append("\"className\":\"").append(sc.getClassName()).append("\"");
            jsonBuilder.append("}");

            if (i < classList.size() - 1) {
                jsonBuilder.append(",");
            }
        }

        jsonBuilder.append("]");
        out.print(jsonBuilder.toString());
        out.flush();
        return;
    }

    // Nếu không phải yêu cầu từ AJAX => xử lý hiển thị danh sách học sinh như bình thường
    StudentDAO studentDAO = new StudentDAO();
    SchoolDAO schoolDAO = new SchoolDAO();

    List<Schools> schoolList = schoolDAO.getAllSchools();
    List<Student> listStudents = studentDAO.getAllStudents();

    String newStudentId;
    if (studentDAO.getLatest() != null) {
        newStudentId = studentDAO.generateId(studentDAO.getLatest().getId());
    } else {
        newStudentId = "HS000001";
    }

    String status = request.getParameter("status");
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
        }
    }

    request.setAttribute("schoolList", schoolList);
    request.setAttribute("newStudentId", newStudentId);
    request.setAttribute("listStudent", listStudents);

    request.getRequestDispatcher("student.jsp").forward(request, response);
}

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null && action.equals("create")) {
            HttpSession session = request.getSession();
            PersonnelDAO personnelDAO = new PersonnelDAO();
            StudentDAO studentDAO = new StudentDAO();

            User user = (User) session.getAttribute("user");
            String toastMessage = "";
            String toastType = "error";

            if (user != null) {
                try {
                    String avatar = request.getParameter("avatar").trim();
                    String firstName = request.getParameter("firstName").trim();
                    String lastName = request.getParameter("lastName").trim();
                    String secondGuardianName = request.getParameter("secondGuardianName").trim();
                    String firstGuardianName = request.getParameter("firstGuardianName").trim();
                    String birth = request.getParameter("birth");
                    String genderRaw = request.getParameter("gender");
                    String email = request.getParameter("email").trim();
                    String note = request.getParameter("note").trim();
                    String address = request.getParameter("address").trim();
                    String secondGuardianPhoneNumber = request.getParameter("secondGuardianPhoneNumber").trim();
                    String firstGuardianPhoneNumber = request.getParameter("firstGuardianPhoneNumber").trim();
                    String schoolID = request.getParameter("schoolID").trim();
                    String schoolClassID = request.getParameter("schoolClassId").trim();
                    String status = "đang chờ xử lý";
                    Schools school = new Schools();
                    school.setId(schoolID);

                    SchoolClass schoolClass = new SchoolClass();
                    schoolClass.setId(schoolClassID);

                    // Parse ngày sinh
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    Date birthday = formatter.parse(birth);

                    // Lấy người tạo
                    Personnel createdBy = personnelDAO.getPersonnelByUserId(user.getId());

                  //   Tạo đối tượng Student
                    Student student = new Student(null, user.getId(), Helper.formatName(firstName), Helper.formatName(lastName), address,
                            email, status, birthday, Integer.parseInt(genderRaw) == 1, Helper.formatName(firstGuardianName),
                            firstGuardianPhoneNumber, avatar,
                            secondGuardianName.isBlank() ? null : Helper.formatName(secondGuardianName),
                            secondGuardianPhoneNumber.isBlank() ? null : secondGuardianPhoneNumber,
                            createdBy, note, school, schoolClass);


                    if (address.isBlank() || email.isBlank() || firstGuardianPhoneNumber.isBlank() || avatar.isBlank() || genderRaw.equals("-1")
                            || Helper.formatName(firstName).isBlank() || Helper.formatName(lastName).isBlank()
                            || Helper.formatName(firstGuardianName).isBlank()|| schoolID==null || schoolID.isBlank() || schoolClassID==null || schoolClassID.isBlank()) {
                        if (address.isBlank()) {
                            toastMessage = "Tạo thật bại ! Vui lòng không bỏ trống trường địa chỉ !";
                        } else if (schoolID.isBlank() || schoolID== null) {
                            toastMessage = "Tạo thật bại ! Vui lòng không bỏ trống trường schoolID !";
                        } else if (Helper.formatName(firstName).isBlank()) {
                            toastMessage = "Tạo thật bại ! Vui lòng không bỏ trống trường tên !";
                        } else if (Helper.formatName(schoolClassID).isBlank()) {
                            toastMessage = "Tạo thật bại ! Vui lòng không bỏ trống trường họ !";
                        } else if (Helper.formatName(lastName).isBlank()) {
                            toastMessage = "Tạo thật bại ! Vui lòng không bỏ trống trường họ !";
                        } else if (Helper.formatName(firstGuardianName).isBlank()) {
                            toastMessage = "Tạo thật bại ! Vui lòng không bỏ trống họ tên bố";
                        } else if (firstGuardianPhoneNumber.isBlank()) {
                            toastMessage = "Tạo thật bại ! Vui lòng không bỏ trống trường số điện thoại Bố";
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
                        request.setAttribute("listStudent", listStudent);
                        request.setAttribute("newStudentId", request.getParameter("id"));
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
                        toastMessage = "Tạo thật bại ! Vui lòng nhập họ và tên Mẹ!";
                        toastType = "error";
                        session.setAttribute("toastMessage", toastMessage);
                        session.setAttribute("toastType", toastType);
                        List<Student> listStudent = studentDAO.getAllStudents();
                        request.setAttribute("listStudent", listStudent);
                        request.setAttribute("newStudentId", request.getParameter("id"));
                        request.getRequestDispatcher("student.jsp").forward(request, response);
                    } else if (studentDAO.checkFirstGuardianPhoneNumberExists(firstGuardianPhoneNumber) || studentDAO.checkSecondGuardianPhoneNumberExists(secondGuardianPhoneNumber)) {
                        if (studentDAO.checkFirstGuardianPhoneNumberExists(firstGuardianPhoneNumber)) {
                            toastMessage = "Số điện thoại Bố đã tồn tại";
                        } else if (studentDAO.checkSecondGuardianPhoneNumberExists(secondGuardianPhoneNumber)) {
                            toastMessage = "Số điện thoại Mẹ đã tồn tại";
                        }
                        session.setAttribute("toastMessage", toastMessage);
                        session.setAttribute("toastType", toastType);
                        List<Student> listStudent = studentDAO.getAllStudents();
                        request.setAttribute("listStudent", listStudent);
                        request.setAttribute("newStudentId", request.getParameter("id"));
                        request.getRequestDispatcher("student.jsp").forward(request, response);
                    } else {
                        if (studentDAO.createStudent(student)) {
                            toastMessage = "Xác nhận thành công";
                            toastType = "success";
                            session.setAttribute("toastMessage", toastMessage);
                            session.setAttribute("toastType", toastType);
                            response.sendRedirect("student");

                        } else {
                            session.setAttribute("toastMessage", toastMessage);
                            session.setAttribute("toastType", toastType);
                            List<Student> listStudent = studentDAO.getAllStudents();
                            request.setAttribute("listStudent", listStudent);
                            request.setAttribute("newStudentId", request.getParameter("id"));
                            request.getRequestDispatcher("student.jsp").forward(request, response);
                        }
                    }
                } catch (ParseException ex) {
                    Logger.getLogger(StudentServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
