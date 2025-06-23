/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.director;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.classes.ClassDAO;
import model.personnel.Personnel;
import model.personnel.PersonnelDAO;
import model.schoolYear.SchoolYearDAO;
import model.student.Student;
import model.student.StudentDAO;
import model.classes.Class;
/**
 * Servlet ListStudentServlet xử lý các yêu cầu HTTP liên quan đến hiển thị danh sách học sinh theo lớp và năm học.
 * 
 * URL Mapping: /director/liststudent (được cấu hình trong web.xml hoặc Annotation nếu có)
 * 
 * Chức năng:
 * - Nhận dữ liệu từ client (tham số lớp và năm học)
 * - Gọi DAO để truy xuất danh sách học sinh, lớp học, giáo viên và năm học
 * - Chuyển tiếp dữ liệu đến trang JSP để hiển thị thông tin học sinh
 * 
 * Phân quyền: Chỉ vai trò Director (Giám đốc trung tâm) được phép truy cập chức năng này
 * 
 * @author ThanhNT
 * @version 1.0
 */

public class ListStudentServlet extends HttpServlet {

 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ListStudentServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListStudentServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ClassDAO classDAO = new ClassDAO();
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        StudentDAO studentDAO = new StudentDAO();
        PersonnelDAO personnelDAO = new PersonnelDAO();
         String classesSelect = request.getParameter("classes");
        String schoolYearSelect = request.getParameter("schoolYear");
        if (schoolYearSelect==null ){
            schoolYearSelect = schoolYearDAO.getLatest().getId();
        }
            Class gradeClass =  classDAO.getClassById(classesSelect);
            Personnel teacher = personnelDAO.getTeacherByClassAndSchoolYear(classesSelect,schoolYearSelect);
            if(teacher!=null && gradeClass!=null){
                request.setAttribute("teacherName",teacher.getLastName()+" "+teacher.getFirstName());
                request.setAttribute("grade",gradeClass.getName());
            }

        List<Student> listStudents = studentDAO.getStudentByClass(classesSelect);
        List<Class> listClass = classDAO.getBySchoolYear(schoolYearSelect);

        request.setAttribute("classSelect",classesSelect);
        request.setAttribute("listPupil",listStudents);
        request.setAttribute("schoolYearSelect",schoolYearSelect);
        request.setAttribute("listClass",listClass);
        request.setAttribute("listSchoolYear",schoolYearDAO.getAll());
        request.getRequestDispatcher("listStudent.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
