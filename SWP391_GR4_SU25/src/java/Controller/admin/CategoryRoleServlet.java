/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.admin;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.personnel.Personnel;
import model.personnel.PersonnelDAO;
import model.student.Student;
import model.student.StudentDAO;

/**
 *
 * @author ASUS VIVOBOOK
 */
@WebServlet(name="CategoryRoleServlet", urlPatterns={"/admin/categoryRole"})
public class CategoryRoleServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       String role_id = request.getParameter("role");
        List<Personnel> listPersonnel = new ArrayList<>();
        List<Student> listStudents = new ArrayList<>();
        Map<Integer, String> roleMap = new HashMap<>();
        roleMap.put(0, "NHÂN VIÊN IT");
        roleMap.put(1, "GIÁM ĐỐC");
        roleMap.put(2, "GIÁO VỤ");
        roleMap.put(3, "GIÁO VIÊN");
        roleMap.put(4, "HỌC SINH");
        roleMap.put(5, "KẾ TOÁN");
        switch (role_id) {
            case "0":
                listPersonnel = new PersonnelDAO().getPersonnelByRoleAndNonUserId(0);
                request.setAttribute("roleMap", roleMap);
                request.setAttribute("listPersonnel", listPersonnel);
                request.getRequestDispatcher("adminCreateUser.jsp").forward(request, response);
                break;
            case "1":
                listPersonnel = new PersonnelDAO().getPersonnelByRoleAndNonUserId(1);
                request.setAttribute("roleMap", roleMap);
                request.setAttribute("listPersonnel", listPersonnel);
                request.getRequestDispatcher("adminCreateUser.jsp").forward(request, response);
                break;
            case "2":
                listPersonnel = new PersonnelDAO().getPersonnelByRoleAndNonUserId(2);
                request.setAttribute("roleMap", roleMap);
                request.setAttribute("listPersonnel", listPersonnel);
                request.getRequestDispatcher("adminCreateUser.jsp").forward(request, response);
                break;
            case "3":
                listPersonnel = new PersonnelDAO().getPersonnelByRoleAndNonUserId(3);
                request.setAttribute("roleMap", roleMap);
                request.setAttribute("listPersonnel", listPersonnel);
                request.getRequestDispatcher("adminCreateUser.jsp").forward(request, response);
                break;
            case "4":
                listStudents = new StudentDAO().getStudentNonUserId();
                request.setAttribute("roleMap", roleMap);
                request.setAttribute("listStudents", listStudents);
                request.getRequestDispatcher("adminCreateUser.jsp").forward(request, response);
                break;
            case "5":
                listPersonnel = new PersonnelDAO().getPersonnelByRoleAndNonUserId(5);
                request.setAttribute("roleMap", roleMap);
                request.setAttribute("listPersonnel", listPersonnel);
                request.getRequestDispatcher("adminCreateUser.jsp").forward(request, response);
                break;
            case "6":
                response.sendRedirect("createuser");
                break;
            default:
                throw new AssertionError();
        }
    } 

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
     
    }


}