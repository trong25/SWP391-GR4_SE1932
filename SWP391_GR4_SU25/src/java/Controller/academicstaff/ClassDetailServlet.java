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
import java.util.Date;
import java.util.List;
import model.classes.ClassDAO;
import model.personnel.PersonnelDAO;
import model.student.Student;
import model.student.StudentDAO;
import model.classes.Class;
import model.personnel.Personnel;
import model.school.Schools;
import model.schoolYear.SchoolYear;

/**
 *
 * @author MSI
 */
public class ClassDetailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StudentDAO studentDAO = new StudentDAO();
        ClassDAO classDAO = new ClassDAO();
        PersonnelDAO personnelDAO = new PersonnelDAO();
        String classId = request.getParameter("classId");
        HttpSession session = request.getSession();
        session.setAttribute("classId", classId);
        List<Student> listStudent = studentDAO.getListStudentsByClass(null, classId);
        Class classes = classDAO.getClassById(classId);
        List<Student> listAllStudent = studentDAO.getAllStudents();
        request.setAttribute("checkedDate", isSchoolYearInThePast(classes.getSchoolYear()));
        request.setAttribute("listAllStudent", listAllStudent);
        request.setAttribute("teacher", classes.getTeacher());
        request.setAttribute("teacherName", classes.getTeacher().getLastName() + " " + classes.getTeacher().getFirstName());
        request.setAttribute("classes", classes);

        request.setAttribute("moveOutClass", classDAO.getClassesByGradeAndSchoolYear(classId, classes.getGrade().getId(), classes.getSchoolYear().getId()));

        request.setAttribute("listStudent", listStudent);

        request.setAttribute("freeTeachers", session.getAttribute("freeTeachers"));
        request.setAttribute("popUpModal", session.getAttribute("popUpModal"));
        request.setAttribute("dayId", session.getAttribute("dayId"));
        session.removeAttribute("freeTeachers");
        session.removeAttribute("popUpModal");
        session.removeAttribute("dayId");

        //This request for assign teacher to class, sending a list of available teacher
        request.setAttribute("teachers", personnelDAO.getAvailableTeachers(classDAO.getClassById(classId).getSchoolYear().getId()));
        request.getRequestDispatcher("classDetail.jsp").forward(request, response);
    }

    @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String action = request.getParameter("action");
    HttpSession session = request.getSession();

    if ("addStudent".equals(action)) {
        StudentDAO studentDAO = new StudentDAO();
        PersonnelDAO personnelDAO = new PersonnelDAO();
        String toastMessage = "";
        String toastType = "";
        boolean addAtLeastOne = false;

        String[] studentSelected = request.getParameterValues("studentSelected");
        String classId = request.getParameter("classId");

        if (studentSelected != null && classId != null) {
            Personnel teacher = personnelDAO.getHomeroomTeacherByClassId(classId);

            if (teacher != null) {
                String teacherSchoolName = teacher.getSchoolName();
                String teacherSchoolAddress = teacher.getAddressSchool();

                for (String studentId : studentSelected) {
                    Student student = studentDAO.getStudentById2(studentId);
                    if (student != null) {
                        Schools studentSchool = student.getSchool_id();

                        String studentSchoolName = studentSchool.getSchoolName();
                        String studentSchoolAddress = studentSchool.getAddressSchool();

                        boolean sameSchoolName = studentSchoolName != null
                                && studentSchoolName.equalsIgnoreCase(teacherSchoolName);
                        boolean sameSchoolAddress = studentSchoolAddress != null
                                && studentSchoolAddress.equalsIgnoreCase(teacherSchoolAddress);

                        // ✅ Chỉ thêm nếu KHÔNG trùng cả tên và địa chỉ trường
                        if (!(sameSchoolName && sameSchoolAddress)) {
                            boolean result = studentDAO.addStudentToClass(studentId, classId);
                            if (result) {
                                addAtLeastOne = true;
                            }
                        } else {
                            System.out.println("❌ Không thêm: Trùng tên và địa chỉ trường với giáo viên chủ nhiệm");
                        }
                    }
                }
            }
        }

        // Gửi thông báo (toast)
        if (addAtLeastOne) {
            toastMessage = "Thêm học sinh vào lớp thành công";
            toastType = "success";
        } else {
            toastMessage = "Không thể thêm học sinh do trùng tên và địa chỉ trường với giáo viên chủ nhiệm";
            toastType = "error";
        }

        session.setAttribute("toastMessage", toastMessage);
        session.setAttribute("toastType", toastType);
        response.sendRedirect("classdetail?classId=" + classId);
    }
}


    private boolean isSchoolYearInThePast(SchoolYear schoolYear) {
        return schoolYear.getEndDate().before(new Date());
    }

}
