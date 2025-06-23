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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.personnel.Personnel;
import model.personnel.PersonnelDAO;
import model.student.Student;
import model.student.StudentDAO;

/**
 * Lớp CategoryRoleServlet dùng để xử lý các yêu cầu lấy danh sách nhân sự hoặc học sinh
 * theo vai trò (role) chưa có tài khoản người dùng trong hệ thống.
 * 
 * Chịu trách nhiệm truy vấn danh sách nhân sự hoặc học sinh dựa trên role được truyền qua tham số,
 * đồng thời cung cấp thông tin bản đồ vai trò để hiển thị tên vai trò tương ứng.
 * 
 * Dữ liệu sau đó được chuyển tiếp tới trang tạo tài khoản người dùng (adminCreateUser.jsp)
 * để thực hiện việc tạo tài khoản cho nhân sự hoặc học sinh được chọn.
 * 
 * Các roleId được hỗ trợ:
 * - 0: NHÂN VIÊN IT
 * - 1: HIỆU TRƯỞNG
 * - 2: GIÁO VỤ
 * - 3: GIÁO VIÊN
 * - 4: HỌC SINH
 * - 5: KẾ TOÁN
 * - 6: Chuyển hướng tới trang tạo người dùng chung (createuser)
 * 
 * Được sử dụng trong tầng Controller theo mô hình MVC,
 * tương tác với PersonnelDAO và StudentDAO để truy xuất dữ liệu.
 * 
 * @author ASUS VIVOBOOK
 * @version 1.0
 */

@WebServlet(name="CategoryRoleServlet", urlPatterns={"/admin/categoryRole"})
public class CategoryRoleServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       String role_id = request.getParameter("role");
        List<Personnel> listPersonnel = new ArrayList<>();
        List<Student> listStudents = new ArrayList<>();
        Map<Integer, String> roleMap = new HashMap<>();
        roleMap.put(0, "NHÂN VIÊN IT");
        roleMap.put(1, "HIỆU TRƯỞNG");
        roleMap.put(2, "GIÁO VỤ");
        roleMap.put(3, "GIÁO VIÊN");
        roleMap.put(4, "HỌC SINH");
        roleMap.put(5, "KẾ TOÁN");
        switch (role_id) {
            case "0":
                listPersonnel = new PersonnelDAO().getPersonnelByRoleAndNonUserId(0);
                request.setAttribute("roleMap", roleMap);
                request.setAttribute("listPersonnel", listPersonnel);
                request.getRequestDispatcher("adminCreateUser.jsp").forward(request, response);
                break;
            case "1":
                listPersonnel = new PersonnelDAO().getPersonnelByRoleAndNonUserId(1);
                request.setAttribute("roleMap", roleMap);
                request.setAttribute("listPersonnel", listPersonnel);
                request.getRequestDispatcher("adminCreateUser.jsp").forward(request, response);
                break;
            case "2":
                listPersonnel = new PersonnelDAO().getPersonnelByRoleAndNonUserId(2);
                request.setAttribute("roleMap", roleMap);
                request.setAttribute("listPersonnel", listPersonnel);
                request.getRequestDispatcher("adminCreateUser.jsp").forward(request, response);
                break;
            case "3":
                listPersonnel = new PersonnelDAO().getPersonnelByRoleAndNonUserId(3);
                request.setAttribute("roleMap", roleMap);
                request.setAttribute("listPersonnel", listPersonnel);
                request.getRequestDispatcher("adminCreateUser.jsp").forward(request, response);
                break;
            case "4":
                listStudents = new StudentDAO().getStudentNonUserId();
                request.setAttribute("roleMap", roleMap);
                request.setAttribute("listStudents", listStudents);
                request.getRequestDispatcher("adminCreateUser.jsp").forward(request, response);
                break;
            case "5":
                listPersonnel = new PersonnelDAO().getPersonnelByRoleAndNonUserId(5);
                request.setAttribute("roleMap", roleMap);
                request.setAttribute("listPersonnel", listPersonnel);
                request.getRequestDispatcher("adminCreateUser.jsp").forward(request, response);
                break;
            case "6":
                response.sendRedirect("createuser");
                break;
            default:
                throw new AssertionError();
        }
    } 

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
     
    }


}