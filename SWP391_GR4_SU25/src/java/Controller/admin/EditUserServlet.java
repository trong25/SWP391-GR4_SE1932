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
import java.util.HashMap;
import java.util.Map;
import model.user.User;
import model.user.UserDAO;

/**
 * Servlet EditUserServlet dùng để xử lý hiển thị form chỉnh sửa thông tin tài khoản người dùng.
 *
 * Chịu trách nhiệm:
 * - Nhận ID người dùng từ request.
 * - Lấy thông tin chi tiết của người dùng từ cơ sở dữ liệu.
 * - Chuẩn bị dữ liệu vai trò và trạng thái hoạt động để hiển thị trong form chỉnh sửa.
 * - Chuyển dữ liệu đến trang JSP `editUser.jsp` để hiển thị cho admin chỉnh sửa.
 *
 * Được sử dụng bởi: giao diện quản trị `editUser.jsp` trong chức năng cập nhật tài khoản người dùng.
 *
 * Ví dụ: Khi admin bấm "Chỉnh sửa" một tài khoản, servlet này sẽ cung cấp thông tin chi tiết để cập nhật.
 *
 * @author HuyDV
 * @version 1.0
 */
public class EditUserServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      
    } 

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
        String id = request.getParameter("id");
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserById(id);
        request.setAttribute("roleMap", roleMap);
        request.setAttribute("roleDis", roleDis);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/admin/editUser.jsp").forward(request, response);
    }

    }

