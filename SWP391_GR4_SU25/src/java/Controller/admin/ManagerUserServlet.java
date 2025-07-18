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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.user.User;
import model.user.UserDAO;

/**
 * Servlet ManagerUserServlet dùng để quản lý danh sách tài khoản người dùng trong hệ thống.
 *
 * Chịu trách nhiệm:
 * - Lấy danh sách toàn bộ người dùng từ cơ sở dữ liệu.
 * - Xử lý các thông báo từ session như đặt lại mật khẩu hoặc cập nhật tài khoản (thành công/thất bại).
 * - Chuẩn bị dữ liệu hiển thị: danh sách vai trò, trạng thái hoạt động và thông tin người dùng.
 * - Chuyển dữ liệu đến trang `managerUser.jsp` để hiển thị danh sách và thao tác quản trị.
 *
 * Được sử dụng bởi: giao diện `managerUser.jsp` trong phần quản trị hệ thống người dùng.
 *
 * Ví dụ: Sau khi admin thay đổi mật khẩu hoặc chỉnh sửa tài khoản người dùng, servlet này sẽ tải lại dữ liệu
 * và hiển thị thông báo tương ứng trên giao diện.
 *
 * @author HuyDV
 * @version 1.0
 */

public class ManagerUserServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      doPost(request, response);
    } 

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      Map<Integer, String> roleMap = new HashMap<>();
        Map<Byte, String> roleDis = new HashMap<>();
        roleMap.put(0, "NHÂN VIÊN IT");
        roleMap.put(1, "GIÁM ĐỐC");
        roleMap.put(2, "GIÁO VỤ");
        roleMap.put(3, "GIÁO VIÊN");
        roleMap.put(4, "HỌC SINH");
        roleMap.put(5, "KẾ TOÁN");

        roleDis.put((byte) 0, "HOẠT ĐỘNG");
        roleDis.put((byte) 1, "KHÔNG HOẠT ĐỘNG");
        UserDAO userDAO = new UserDAO();
        List<User> list;
        list = userDAO.getListUser();
        HttpSession session = request.getSession();
        String error = (String) session.getAttribute("error");
        String success = (String) session.getAttribute("success");
        String successedit = (String) session.getAttribute("successedit");
        String erroredit = (String) session.getAttribute("erroredit");
        if (error != null) {
            request.setAttribute("toastType", "error");
            request.setAttribute("toastMessage", "Đặt Lại Mật Khẩu Không Thành Công");
            session.removeAttribute("error");
        } else if (success != null) {
            request.setAttribute("toastType", "success");
            request.setAttribute("toastMessage", "Đặt Lại Mật Khẩu Thành Công");
            session.removeAttribute("success");
        }
        if (successedit != null) {
            request.setAttribute("toastType", "success");
            request.setAttribute("toastMessage", "Cập Nhật Thông Tin Thành Công");
            session.removeAttribute("successedit");
        } else if (erroredit != null) {
            request.setAttribute("toastType", "error");
            request.setAttribute("toastMessage", "Cập Nhật Thông Tin Không Thành Công, Email Đã Được Đăng Ký");
            session.removeAttribute("erroredit");
        }
        request.setAttribute("list", list);
        request.setAttribute("roleMap", roleMap);
        request.setAttribute("roleDis", roleDis);
        request.getRequestDispatcher("/admin/managerUser.jsp").forward(request, response);
    }


}