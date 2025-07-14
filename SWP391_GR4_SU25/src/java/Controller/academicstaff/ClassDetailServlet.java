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
import model.day.Day;
import model.day.DayDAO;
import model.personnel.Personnel;
import model.school.Schools;
import model.schoolYear.SchoolYear;
import model.timetable.TimetableDAO;

/**
 *Servlet ClassDetailServlet xử lý các yêu cầu HTTP để hiển thị danh sách học sinh trong một lớp học
 * và chỉnh sửa thông tin học sinh trong lớp học đó, thêm học sinh mới vào lớp học,
 * phân công giáo viên mới, đổi lớp cho học sinh
 * URL Mapping: /academicstaff/classdetail
 * Chức năng:
 * -Nhận dữ liệu từ form
 * - gọi ClassDAO để thêm hoc sinh, phân công giáo viên, đổi lớp cho học và lưu vào cơ sở dữ liệu
 * Phân quyền: chỉ Giáo Vụ mới được phép làm thêm học sinh vào lớp, phân công giáo viên, đổi lớp cho hoc sinh.
 * @author TrongNV
 * @version 1.0
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
        String gradeLevel = classes.getGrade().getName();
        List<Student> listAllStudent = studentDAO.getStudentsByGrade(gradeLevel);
        request.setAttribute("checkedDate", isSchoolYearInThePast(classes.getSchoolYear()));
        request.setAttribute("listAllStudent", listAllStudent);
        request.setAttribute("teacher", classes.getTeacher());
        request.setAttribute("teacherName", classes.getTeacher().getLastName() + " " + classes.getTeacher().getFirstName() + "-" + classes.getTeacher().getAddressSchool());
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
                                System.out.println("❌ Không thêm: Trùng tên và địa chỉ trường với giáo viên chủ nhiệm hoặc học sinh đã tồn tại trong lớp");
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
                toastMessage = "Không thể thêm học sinh do trùng tên và địa chỉ trường với giáo viên chủ nhiệm hoặc học sinh đã tồn tại trong lớp";
                toastType = "error";
            }

            session.setAttribute("toastMessage", toastMessage);
            session.setAttribute("toastType", toastType);
            response.sendRedirect("classdetail?classId=" + classId);
        } else if (action.equals("moveOutClassForStudent")) {
            ClassDAO classDAO = new ClassDAO();
            String oldClassId = request.getParameter("classId");
            String studentId = request.getParameter("student");
            String newClassId = request.getParameter("classes");
            if (!studentId.isBlank() && !newClassId.isBlank()) {
                if (classDAO.moveOutClassForStudent(oldClassId, newClassId, studentId)) {
                    session.setAttribute("toastType", "success");
                    session.setAttribute("toastMessage", "Thao tác thành công");
                } else {
                    session.setAttribute("toastType", "error");
                    session.setAttribute("toastMessage", "Thao tác thất bại");
                }
            } else {
                session.setAttribute("toastType", "error");
                session.setAttribute("toastMessage", "Thao tác thất bại");
            }
            response.sendRedirect("classdetail?classId=" + oldClassId);
        } else if (action.equals("assignTeacher")) {
            String teacherId = request.getParameter("teacher");
            String classId = request.getParameter("classId");

            ClassDAO classDAO = new ClassDAO();
            StudentDAO studentDAO = new StudentDAO();
            PersonnelDAO personnelDAO = new PersonnelDAO();

            String toastMessage = "";
            String toastType = "";

            Personnel teacher = personnelDAO.getPersonnelById(teacherId); // Lấy thông tin giáo viên
            if (teacher != null) {
                String teacherSchoolName = teacher.getSchoolName();
                String teacherSchoolAddress = teacher.getAddressSchool();

                List<Student> studentsInClass = studentDAO.getStudentsByClassId(classId);

                boolean conflictFound = false;
                for (Student student : studentsInClass) {
                    Schools studentSchool = student.getSchool_id();

                    if (studentSchool != null) {
                        String studentSchoolName = studentSchool.getSchoolName();
                        String studentSchoolAddress = studentSchool.getAddressSchool();

                        boolean sameSchoolName = studentSchoolName != null
                                && studentSchoolName.equalsIgnoreCase(teacherSchoolName);
                        boolean sameSchoolAddress = studentSchoolAddress != null
                                && studentSchoolAddress.equalsIgnoreCase(teacherSchoolAddress);

                        if (sameSchoolName && sameSchoolAddress) {
                            conflictFound = true;
                            break;
                        }
                    }
                }

                if (!conflictFound) {
                    String result = classDAO.assignTeacherToClass(teacherId, classId);
                    TimetableDAO timetableDAO = new TimetableDAO();
                    timetableDAO.updateTeacherOfTimetable(classId, teacherId);

                    if ("success".equals(result)) {
                        toastMessage = "Phân công giáo viên vào lớp thành công";
                        toastType = "success";
                    } else {
                        toastMessage = result;
                        toastType = "error";
                    }
                } else {
                    toastMessage = "Không thể phân công: Giáo viên trùng tên và địa chỉ trường với học sinh trong lớp";
                    toastType = "error";
                }
            } else {
                toastMessage = "Không tìm thấy thông tin giáo viên";
                toastType = "error";
            }

            session.setAttribute("toastType", toastType);
            session.setAttribute("toastMessage", toastMessage);
            response.sendRedirect("classdetail?classId=" + classId);
//        } else if (action.equals("assignSubTeacher")) {
//            String substituteTeacher = request.getParameter("substituteTeacher");
//            String classId = request.getParameter("classId");
//            String dayId= request.getParameter("day");
//            if (substituteTeacher.equals("null")){
//                PersonnelDAO personnelDAO = new PersonnelDAO();
//                session.setAttribute("freeTeachers",personnelDAO.getFreeTeacherByDate(dayId));
//                session.setAttribute("popUpModal", "true");
//                session.setAttribute("dayId", dayId);
//                response.sendRedirect("classdetail?classId=" + classId);
//            } else {
//                TimetableDAO timetableDAO = new TimetableDAO();
//                String result = timetableDAO.updateTimetableOfClass(substituteTeacher, classId, dayId);
//                if (result.equals("success")) {
//                    session.setAttribute("toastType", "success");
//                    session.setAttribute("toastMessage", "Thao tác thành công");
//                } else {
//                    session.setAttribute("toastType", "error");
//                    session.setAttribute("toastMessage", result);
//                }
//                response.sendRedirect("classdetail?classId=" + classId);
//            }
//        }
    
        }
    }

    private boolean isSchoolYearInThePast(SchoolYear schoolYear) {
        return schoolYear.getEndDate().before(new Date());
    }

}