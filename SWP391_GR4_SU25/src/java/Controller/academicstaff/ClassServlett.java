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
import java.util.List;
import model.classes.ClassDAO;
import model.classes.Class;
import model.grade.GradeDAO;
import model.personnel.Personnel;
import model.personnel.PersonnelDAO;
import model.schoolYear.SchoolYear;
import model.schoolYear.SchoolYearDAO;
import model.user.User;
import utils.Helper;

/**
  *Servlet ClassServlett xử lý các yêu cầu HTTP để hiển thị danh sách  lớp học
 * và chỉnh sửa thông tinlớp học sinh,
 * 
 * URL Mapping: /academicstaff/class
 * Chức năng:
 * -Nhận dữ liệu từ form
 * - gọi ClassDAO để lấy các hàm lấy danh sách các lớp theo trạng thái,và gọi hàm tạo lớp học đẻe tạo lớp mới và lưu vào cơ sở dữ liệu
 * Phân quyền: chỉ Giáo Vụ mới được phép tạo lớp học mới.
 * @author TrongNV
 * @version 1.0
 */
public class ClassServlett extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      ClassDAO classDAO = new ClassDAO();
      SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        try {
            HttpSession session = request.getSession();
            String toastType ="", toastMessage = "";
            if(session.getAttribute("toastType") != null){
                toastType = session.getAttribute("toastType").toString();
                toastMessage = session.getAttribute("toastType").toString();
                
            }
            session.removeAttribute("toastType");
            session.removeAttribute("toastMessage");
            request.setAttribute("toastType", toastType);
            request.setAttribute("toastMessage", toastMessage);
            String schoolYearId = (String) session.getAttribute("schoolYearId");
            session.removeAttribute("schoolYearId");
            if(schoolYearId == null){
                schoolYearId = request.getParameter("schoolYearId");
                
            }
            if(schoolYearId == null){
                if(schoolYearDAO.getLatest() != null){
                    SchoolYear latestSchoolYear = schoolYearDAO.getLatest();
                    schoolYearId = latestSchoolYear.getId();
                }
            }
            String status = request.getParameter("status");
           List<Class> classes;
            if(status!=null && !status.equals("all")){
                classes = classDAO.getByStatus(status, schoolYearId);
            }else{
                status ="all";
                classes = classDAO.getBySchoolYear(schoolYearId);
            }
            request.setAttribute("status", status);
            request.setAttribute("classes", classes);
            List<SchoolYear> schoolYears = schoolYearDAO.getAll();
            request.setAttribute("schoolYears", schoolYears);
            request.setAttribute("selectedSchoolYear", schoolYearDAO.getSchoolYear(schoolYearId));
            GradeDAO gradeDAO = new GradeDAO();
            request.setAttribute("grades", gradeDAO.getAll());
            PersonnelDAO personnelDAO = new PersonnelDAO();
            request.setAttribute("teachers", personnelDAO.getAvailableTeachers(schoolYearId));
            request.getRequestDispatcher("class.jsp").forward(request, response);
        } catch (Exception e) {
          e.printStackTrace();
        }
          
    } 

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
     String action = request.getParameter("action");
        if (action != null){
            //create new class
            if (action.equals("create")) {
                String name = request.getParameter("name");
                name = Helper.formatName(name);
                String gradeId = request.getParameter("grade");
                String classType = request.getParameter("classType");
                String schoolYearId = request.getParameter("schoolYear");
                String teacherId = request.getParameter("teacher");
                Class c = new Class();
                c.setName(name);
                GradeDAO gradeDAO = new GradeDAO();
                c.setGrade(gradeDAO.getGrade(gradeId));
                c.setClassType(classType);
                SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
                c.setSchoolYear(schoolYearDAO.getSchoolYear(schoolYearId));
                PersonnelDAO personnelDAO = new PersonnelDAO();
                if (!teacherId.isEmpty()) {
                    Personnel teacher = personnelDAO.getPersonnel(teacherId);
                    c.setTeacher(teacher);
                }
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                c.setCreatedBy(personnelDAO.getPersonnelByUserId(user.getId()));
                ClassDAO classDAO = new ClassDAO();
                String result = classDAO.createNewClass(c);
                //return result of creation to user
                if (result.equals("success")) {
                    session.setAttribute("toastType", "success");
                    session.setAttribute("toastMessage", "Thao tác thành công");
                } else {
                    session.setAttribute("toastType", "error");
                    session.setAttribute("toastMessage", result);
                }
                session.setAttribute("schoolYearId", schoolYearId);
                response.sendRedirect("class");
            }
        }
    }


}