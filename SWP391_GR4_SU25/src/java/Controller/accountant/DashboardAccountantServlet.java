/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.accountant;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.personnel.PersonnelDAO;
import model.student.StudentDAO;
import model.user.User;

/**
 * Lớp {@code DashboardAccountantServlet} chịu trách nhiệm xử lý yêu cầu hiển thị bảng điều khiển (dashboard) dành cho kế toán.
 * 
 * Trong phương thức {@code doGet}, servlet thực hiện các bước sau:
 * - Lấy danh sách học sinh đang theo học từ {@code StudentDAO}.
 * - Lấy danh sách nhân sự đang làm việc từ {@code PersonnelDAO}.
 * - Đếm số lượng học sinh và nhân sự theo trạng thái tương ứng.
 * - Thiết lập các thuộc tính {@code students} và {@code personnels} vào {@code request} để truyền dữ liệu cho trang JSP hiển thị.
 * - Chuyển tiếp (forward) yêu cầu đến trang {@code dashboard.jsp} để hiển thị giao diện.
 * 
 * 
 * Đây là servlet thuộc tầng Controller trong mô hình MVC, kết nối giữa tầng View (JSP) và tầng Model (DAO).
 * 
 * @author HuyDV
 * @version 1.0
 */

public class DashboardAccountantServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        StudentDAO studentDAO = new StudentDAO();
        PersonnelDAO personnelDAO = new PersonnelDAO();
   
        User user = (User) request.getSession().getAttribute("user");

        int students = studentDAO.getStudentByStatus("đang theo học").size();
        int personnels = personnelDAO.getPersonnelByStatus("đang làm việc").size();
        request.setAttribute("students", students);
        request.setAttribute("personnels", personnels);
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    } 

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
     
    }


}