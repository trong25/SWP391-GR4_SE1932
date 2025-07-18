/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.admin;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.personnel.Personnel;
import model.personnel.PersonnelDAO;

/**
 * Lớp ViewPersonnelAdminServlet dùng để hiển thị thông tin chi tiết của một nhân viên cụ thể cho quản trị viên.
 * 
 * Chịu trách nhiệm xử lý yêu cầu HTTP GET từ quản trị viên, truy xuất dữ liệu nhân viên theo ID và chuyển tiếp
 * thông tin đến trang JSP để hiển thị chi tiết. Ngoài ra, xử lý hiển thị thông báo trạng thái (message/type) từ session nếu có.
 * 
 * Được sử dụng bởi tầng Controller trong mô hình MVC, phục vụ chức năng xem thông tin nhân sự ở giao diện quản trị.
 * 
 * Ví dụ:truy xuất dữ liệu liên quan đến nhân viên trong hệ thống quản lý trung tâm đào tạo.
 * 
 * @author HuyDV
 * @version 1.0
 */

@WebServlet(name="ViewPersonnelAdminServlet", urlPatterns={"/admin/viewpersonnel"})
public class ViewPersonnelAdminServlet extends HttpServlet {
   
   
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        try {
            String message = (String) session.getAttribute("message");
            String type = (String) session.getAttribute("type");
            String xid = request.getParameter("id");
            String xpage = request.getParameter("page");
            Personnel person;
            PersonnelDAO personnelDAO = new PersonnelDAO();
            person = personnelDAO.getPersonnel(xid);
            request.setAttribute("person", person);
            request.setAttribute("message", message);
            request.setAttribute("type", type);
            request.setAttribute("page", xpage);

            request.getRequestDispatcher("viewPersonnelInfomation.jsp").forward(request, response);
            session.removeAttribute("message");
            session.removeAttribute("type");
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra log server
        }
    }

  
       @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      
    }


}