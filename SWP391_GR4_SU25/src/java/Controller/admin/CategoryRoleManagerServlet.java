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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.user.User;
import model.user.UserDAO;

/**
 * Lớp CategoryRoleManagerServlet dùng để xử lý các yêu cầu quản lý danh sách người dùng theo vai trò (role).
 * 
 * Chịu trách nhiệm lấy danh sách người dùng theo role được truyền vào qua tham số request,
 * thiết lập các dữ liệu cần thiết (map vai trò và trạng thái hoạt động),
 * và chuyển hướng tới trang quản lý người dùng với dữ liệu đã lấy được.
 * 
 * Nếu role truyền vào là "6" (có thể là tất cả hoặc role đặc biệt), servlet sẽ chuyển hướng về trang tổng quản lý người dùng.
 * 
 * Được sử dụng trong tầng Controller theo mô hình MVC,
 * tương tác với lớp UserDAO để truy xuất dữ liệu người dùng từ cơ sở dữ liệu.
 * 
 * Các role được quản lý:
 * - 0: NHÂN VIÊN IT
 * - 1: HIỆU TRƯỞNG
 * - 2: GIÁO VỤ
 * - 3: GIÁO VIÊN
 * - 4: HỌC SINH
 * - 5: KẾ TOÁN
 * 
 * Trạng thái hoạt động:
 * - 0: HOẠT ĐỘNG
 * - 1: KHÔNG HOẠT ĐỘNG
 * 
 * @author ASUS VIVOBOOK
 * @version 1.0
 */

@WebServlet(name="CategoryRoleManagerServlet", urlPatterns={"/admin/categoryRoleManager"})
public class CategoryRoleManagerServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
         Map<Integer, String> roleMap = new HashMap<>();
        Map<Byte, String> roleDis = new HashMap<>();
        roleMap.put(0, "NHÂN VIÊN IT");
        roleMap.put(1, "HIỆU TRƯỞNG");
        roleMap.put(2, "GIÁO VỤ");
        roleMap.put(3, "GIÁO VIÊN");
        roleMap.put(4, "HỌC SINH");
        roleMap.put(5, "KẾ TOÁN");
        
         roleDis.put((byte) 0, "HOẠT ĐỘNG");
        roleDis.put((byte) 1, "KHÔNG HOẠT ĐỘNG");
        String id = request.getParameter("role");
        if (id.equals("6")) {
            response.sendRedirect("manageruser");
        } else {
            UserDAO userDAO = new UserDAO();

            List<User> list = userDAO.getUserByRole(Integer.parseInt(id));
            request.setAttribute("list", list);
            request.setAttribute("roleMap", roleMap);
            request.setAttribute("roleDis", roleDis);
            request.getRequestDispatcher("../admin/managerUser.jsp").forward(request, response);
        }
    } 

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
     
    }


}