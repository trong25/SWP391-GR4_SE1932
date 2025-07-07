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
import model.personnel.PersonnelDAO;
import model.student.Student;
import model.student.StudentDAO;
import model.user.UserDAO;

/**
 * Servlet RegisterAccountServlet dùng để xử lý việc tạo tài khoản người dùng mới cho các đối tượng chưa có tài khoản.
 *
 * Chịu trách nhiệm:
 * - Nhận danh sách mã người dùng được chọn từ form (checkbox).
 * - Phân tích mã định danh để xác định vai trò tương ứng (học sinh, giáo viên, kế toán, hiệu trưởng, v.v).
 * - Gọi `UserDAO` để tạo tài khoản mới dựa trên email từ bảng học sinh hoặc nhân sự.
 * - Thiết lập thông báo thành công hoặc lỗi vào session để hiển thị ở trang `createuser`.
 *
 * Được sử dụng bởi: trang quản trị `adminCreateUser.jsp` để tạo tài khoản cho các đối tượng đủ điều kiện.
 *
 * Ví dụ:
 * - Người quản trị chọn nhiều học sinh chưa có tài khoản từ danh sách và nhấn "Tạo tài khoản".
 * - Servlet này sẽ tạo tài khoản cho từng học sinh, đồng thời chuyển hướng trở lại trang tạo tài khoản với thông báo phù hợp.
 *
 * @author HuyDV
 * @version 1.0
 */

@WebServlet(name="RegisterAccountServlet", urlPatterns={"/admin/registeraccount"})
public class RegisterAccountServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      
    } 

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       // Lấy giá trị của các checkbox được chọn từ request
        String[] selectedUserIds = request.getParameterValues("user_checkbox");
        HttpSession session = request.getSession();
        boolean u;
        // Kiểm tra nếu không có checkbox nào được chọn
        if (selectedUserIds == null || selectedUserIds.length == 0) {
            session.setAttribute("error", "error");
            response.sendRedirect("createuser");
        } else {
            UserDAO userDAO = new UserDAO();
            PersonnelDAO personnelDAO = new PersonnelDAO();
            StudentDAO studentDAO = new StudentDAO();
            for (String username : selectedUserIds) {
                switch (username.substring(0, 2)) {
                    case "HS":
                        u = userDAO.createNewUser(username, studentDAO.getStudentById(username).getEmail(), 4, (byte) 0);
                        if (u == true) {
                            session.setAttribute("success", "success");
                        } else {
                            session.setAttribute("errors", "errors");
                        }
                        break;
                    case "KT":
                        u = userDAO.createNewUser(username, personnelDAO.getPersonnel(username).getEmail(), 5, (byte) 0);
                        if (u == true) {
                            session.setAttribute("success", "success");
                        } else {
                            session.setAttribute("errors", "errors");
                        }
                        break;
                    case "GD":
                        u = userDAO.createNewUser(username, personnelDAO.getPersonnel(username).getEmail(), 1, (byte) 0);
                        if (u == true) {
                            session.setAttribute("success", "success");
                        } else {
                            session.setAttribute("errors", "errors");
                        }
                        break;
                    case "GV":
                        u = userDAO.createNewUser(username, personnelDAO.getPersonnel(username).getEmail(), 3, (byte) 0);
                        if (u == true) {
                            session.setAttribute("success", "success");
                        } else {
                            session.setAttribute("errors", "errors");
                        }
                        break;
                    case "NV":
                        u = userDAO.createNewUser(username, personnelDAO.getPersonnel(username).getEmail(), 2, (byte) 0);
                        if (u == true) {
                            session.setAttribute("success", "success");
                        } else {
                            session.setAttribute("errors", "errors");
                        }
                        break;
                    case "AD":
                        u = userDAO.createNewUser(username, personnelDAO.getPersonnel(username).getEmail(), 0, (byte) 0);
                        if (u == true) {
                            session.setAttribute("success", "success");
                        } else {
                            session.setAttribute("errors", "errors");
                        }
                        break;
                    default:
                        break;
                }
            }
            response.sendRedirect("createuser");
        }
    }


}