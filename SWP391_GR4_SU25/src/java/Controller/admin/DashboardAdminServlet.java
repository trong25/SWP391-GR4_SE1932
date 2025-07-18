/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.admin;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.day.DayDAO;
import model.personnel.PersonnelAttendanceDAO;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import model.personnel.PersonnelDAO;
import model.user.User;
import model.user.UserDAO;

/**
 * Lớp DashboardAdminServlet dùng để xử lý yêu cầu hiển thị trang tổng quan (dashboard) cho quản trị viên.
 * 
 * Chịu trách nhiệm lấy dữ liệu thống kê như: tổng số tài khoản, số tài khoản bị khóa,
 * số lượng nhân sự theo vai trò, trạng thái và thông tin chấm công trong ngày.
 * 
 * Được sử dụng bởi tầng giao diện JSP (`dashboard.jsp`) dành cho quản trị viên.
 * 
 * Ví dụ: cập nhật, hoặc truy xuất dữ liệu liên quan đến người dùng và nhân sự cho mục đích thống kê.
 * 
 * @author HuyDV
 * @version 1.0
 */

public class DashboardAdminServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      UserDAO userDAO = new UserDAO();
      PersonnelDAO personnelDAO = new PersonnelDAO(); 
       PersonnelAttendanceDAO personnelAttendanceDAO = new PersonnelAttendanceDAO();
        DayDAO dayDAO = new DayDAO();
         HttpSession session = request.getSession();
    
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        String formattedDate = formatter.format(new Date());
         if (dayDAO.getDayByDate(formattedDate) != null) {
            User user = (User)session.getAttribute("user");
            request.setAttribute("attendance", personnelAttendanceDAO.getAttendanceByPersonnelAndDay(
                    user.getUsername(), dayDAO.getDayByDate(formattedDate).getId()));
        }
            
            int totalAccounts = userDAO.countTotalUsers();
            int blockedAccounts = userDAO.countBlockedUsers();

             Map<String, Integer> countByRole = personnelDAO.getPersonnelCountByRole();
            Map<String, Integer> countByStatus = personnelDAO.getPersonnelCountByStatus();
            
            request.setAttribute("totalAccounts", totalAccounts);
            request.setAttribute("blockedAccounts", blockedAccounts);
            request.setAttribute("countByRole", countByRole);
            request.setAttribute("countByStatus", countByStatus);
        } catch (Exception e) {
            e.printStackTrace();
            
        } 
        request.getRequestDispatcher("../admin/dashboard.jsp").forward(request, response);
    }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      
    }


}