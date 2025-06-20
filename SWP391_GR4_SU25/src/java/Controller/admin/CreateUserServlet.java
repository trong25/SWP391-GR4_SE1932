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
import java.util.HashMap;
import java.util.Map;
import model.personnel.PersonnelDAO;
import model.student.StudentDAO;

/**
 * Servlet CreateUserServlet dùng để xử lý việc hiển thị form tạo tài khoản người dùng mới.
 *
 * Chịu trách nhiệm:
 * - Chuẩn bị dữ liệu danh sách học sinh và nhân sự chưa có tài khoản.
 * - Chuẩn bị thông tin vai trò người dùng (IT, Hiệu trưởng, Giáo vụ, Giáo viên, Học sinh, Kế toán).
 * - Gửi dữ liệu đến trang JSP để hiển thị giao diện quản trị.
 * - Xử lý hiển thị thông báo thành công/thất bại sau khi tạo tài khoản.
 *
 * Được sử dụng bởi: giao diện `adminCreateUser.jsp` trong chức năng quản trị hệ thống.
 * 
 * Ví dụ: Khi admin truy cập `/admin/createuser`, servlet sẽ cung cấp danh sách người dùng chưa có tài khoản
 * để admin có thể chọn và tạo tài khoản mới.
 *
 * @author HuyDV
 * @version 1.0
 */
@WebServlet(name="CreateUserServlet", urlPatterns={"/admin/createuser"})
public class CreateUserServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        Map<Integer, String> roleMap = new HashMap<>();

        roleMap.put(0, "NHÂN VIÊN IT");
        roleMap.put(1, "HIỆU TRƯỞNG");
        roleMap.put(2, "GIÁO VỤ");
        roleMap.put(3, "GIÁO VIÊN");
        roleMap.put(4, "HỌC SINH");
        roleMap.put(5, "KẾ TOÁN");

        HttpSession session = request.getSession();
        String error = (String) session.getAttribute("error");
        String errors = (String) session.getAttribute("errors");
        String success = (String) session.getAttribute("success");
        if (error != null) {
            request.setAttribute("toastType", "error");
            request.setAttribute("toastMessage", "Không có tài khoản nào được chọn.");
            session.removeAttribute("error");
        } else if (success != null) {
            request.setAttribute("toastType", "success");
            request.setAttribute("toastMessage", "Tạo tài khoản mới thành công.");
            session.removeAttribute("success");
        } else if (errors != null) {
            request.setAttribute("toastType", "error");
            request.setAttribute("toastMessage", "Tạo tài khoản mới thất bại. Email đã được đăng ký!");
            session.removeAttribute("errors");
        }
        request.setAttribute("listStudents", new StudentDAO().getStudentNonUserId());
        request.setAttribute("listPersonnel", new PersonnelDAO().getPersonnelNonUserId());
        request.setAttribute("roleMap", roleMap);

        request.getRequestDispatcher("adminCreateUser.jsp").forward(request, response);
    } 

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
     
    }


}