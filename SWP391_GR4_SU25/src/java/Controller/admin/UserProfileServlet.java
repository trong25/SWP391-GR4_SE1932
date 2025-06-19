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
import jakarta.servlet.http.HttpSession;
import model.user.User;
import model.user.UserDAO;

/**
 * Servlet UserProfileServlet dùng để xử lý cập nhật thông tin hồ sơ người dùng trong hệ thống.
 *
 * Chịu trách nhiệm:
 * - Nhận dữ liệu người dùng (email, role, trạng thái hoạt động) từ form chỉnh sửa.
 * - Cập nhật thông tin người dùng vào cơ sở dữ liệu thông qua `UserDAO`.
 * - Lưu kết quả cập nhật (thành công/thất bại) vào session để hiển thị thông báo tương ứng.
 *
 * Được sử dụng bởi: giao diện quản trị viên (`managerUser.jsp`) để cập nhật hồ sơ người dùng.
 *
 * Ví dụ:
 * - Quản trị viên thay đổi email hoặc vai trò của một người dùng và nhấn nút "Cập nhật".
 * - Servlet sẽ cập nhật thông tin và chuyển hướng trở lại danh sách người dùng kèm thông báo.
 *
 * @author HuyDV
 * @version 1.0
 */

@WebServlet(name="UserProfileServlet", urlPatterns={"/admin/userprofile"})
public class UserProfileServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       
    } 

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
         // Lấy dữ liệu từ form gửi lên
      String user_id = request.getParameter("userId");
        String email = request.getParameter("email");
        int role = Integer.parseInt(request.getParameter("role"));
        byte active = Byte.parseByte(request.getParameter("active"));
        // Tạo đối tượng User và set các thông tin lấy từ form
        User user = new User();
        user.setId(user_id);
        user.setEmail(email.trim());
        user.setIsDisabled(active);
        user.setRoleId(role);
        UserDAO userDAO = new UserDAO();
        boolean u = userDAO.updateUser(user);
          // Lưu trạng thái cập nhật vào session để hiển thị thông báo
        HttpSession session = request.getSession();
        if (u == true) {
            session.setAttribute("successedit", "success");
        } else {
            session.setAttribute("erroredit", "error");
        }
        request.getRequestDispatcher("manageruser").forward(request, response);
    }


}