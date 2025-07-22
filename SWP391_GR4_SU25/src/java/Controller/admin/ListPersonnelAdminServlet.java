/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.admin;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.personnel.Personnel;
import model.personnel.PersonnelDAO;
import model.role.Role;
import utils.Helper;

/**
 * Lớp ListPersonnelAdminServlet dùng để xử lý các yêu cầu hiển thị danh sách nhân sự cho admin.
 * 
 * Chịu trách nhiệm xử lý các yêu cầu HTTP GET và POST liên quan đến việc truy xuất,
 * lọc theo vai trò, trạng thái và tìm kiếm nhân sự trong hệ thống.
 * Được sử dụng bởi tầng controller trong mô hình MVC của hệ thống quản lý trung tâm.
 * 
 * Ví dụ: tạo danh sách nhân sự chờ xử lý, lọc nhân sự theo vai trò hoặc trạng thái,
 * tìm kiếm theo tên hoặc mã nhân sự, và chuyển dữ liệu đến trang JSP để hiển thị.
 * 
 * @author HuyDV
 * @version 1.0
 */



public class ListPersonnelAdminServlet extends HttpServlet {
   
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
        HttpSession session = request.getSession(true);
        String message = (String) session.getAttribute("message");
        String type = (String) session.getAttribute("type");

        PersonnelDAO personnelDAO = new PersonnelDAO();
        List<Personnel> persons = personnelDAO.getAllPersonnels();
        List<Role> roles = personnelDAO.getAllPersonnelRole();
        List<String> statuss = personnelDAO.getAllStatuss();

        request.setAttribute("selectedstatus", "all");
        request.setAttribute("selectedrole", "all");
        request.setAttribute("message", message);
        request.setAttribute("type", type);
        request.setAttribute("persons", persons);
        request.setAttribute("roles", roles);
        request.setAttribute("statuss", statuss);

       request.getRequestDispatcher("/admin/listPersonnel.jsp").forward(request, response);

        session.removeAttribute("message");
        session.removeAttribute("type");
    } catch (Exception e) {
        e.printStackTrace(); // log lỗi ra console
    }
 

    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String message = (String) session.getAttribute("message");
        String type = (String) session.getAttribute("type");
        String role = request.getParameter("role");
        String status = request.getParameter("status");
        String search = request.getParameter("search");

        List<Personnel> persons = new ArrayList<Personnel>();
        List<Role> roles = new ArrayList<>();
        PersonnelDAO personnelDAO = new PersonnelDAO();
        roles = personnelDAO.getAllPersonnelRole();
        if ((role != null || status != null)&& search == null) {
            if(status.equalsIgnoreCase("all")&& role.equalsIgnoreCase("all")){
                 persons = personnelDAO.getAllPersonnels();
             }
            else if(!status.equalsIgnoreCase("all") && role.equalsIgnoreCase("all")){
                  persons =personnelDAO.getPersonnelByStatuss(status);
                 
             }else if(!role.equalsIgnoreCase("all") && status.equalsIgnoreCase("all")){
                 try{
                 int xrole = Integer.parseInt(role);
                 persons = personnelDAO.getPersonnelByRoles(xrole);
                 }catch (NumberFormatException e){
                     persons = personnelDAO.getPersonnelByRoles(-1);
                 }
                
             } else{
            persons = personnelDAO.getPersonnelByIdNameRoleStatuss(status, role);
             }
        }else if(search != null){
            persons = personnelDAO.getPersonnelByNameOrId(Helper.formatString(search));
        } 
        List<String> statuss = new ArrayList<>();
        statuss = personnelDAO.getAllStatuss();
        request.setAttribute("statuss", statuss);
        request.setAttribute("selectedstatus", status);
        request.setAttribute("selectedrole", role);
        request.setAttribute("message", message);
        request.setAttribute("type", type);
        request.setAttribute("roles", roles);
        request.setAttribute("persons", persons);
        request.getRequestDispatcher("/admin/listPersonnel.jsp").forward(request, response);

        session.removeAttribute("message");
        session.removeAttribute("type");
    }




}