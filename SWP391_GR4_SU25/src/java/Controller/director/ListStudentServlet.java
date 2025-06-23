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
 *
 * @author admin
 */
public class ListStudentServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
