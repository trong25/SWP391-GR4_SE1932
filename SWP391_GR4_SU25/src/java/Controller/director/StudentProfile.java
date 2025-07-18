/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.director;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.student.Student;
import model.student.StudentDAO;

/**
 * Servlet StudentProfile xử lý các yêu cầu HTTP để hiển thị thông tin chi tiết
 * của học sinh.
 *
 * URL Mapping: /director/studentsprofile
 *
 * Chức năng: - Nhận ID học sinh từ request (POST) - Gọi StudentDAO để truy vấn
 * thông tin học sinh từ cơ sở dữ liệu - Gửi đối tượng Student sang trang JSP để
 * hiển thị thông tin chi tiết
 *
 * JSP đích: informationStudent.jsp
 *
 * Phân quyền: Chỉ vai trò Giám đốc (Director) hoặc người có quyền truy cập mới
 * được phép xem thông tin học sinh chi tiết.
 *
 * @author ThanhNT
 * @version 2.0
 */
public class StudentProfile extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StudentProfile</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StudentProfile at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Lấy tham số từ form
        String id = request.getParameter("id");

        // Lấy thông tin học sinh từ database
        StudentDAO studentDAO = new StudentDAO();
        Student student = studentDAO.getStudentById(id);
        if (student == null) {
            request.setAttribute("toastMessage", "Không tìm thấy học sinh với ID: " + id);
            request.setAttribute("toastType", "error");
            request.getRequestDispatcher("reviewStudent.jsp").forward(request, response);
            return;
        }

        // Gửi student và status về JSP
        request.setAttribute("student", student);
        request.setAttribute("status", studentDAO.getStudentByStatus("đang chờ xử lý")); // dùng để hiển thị điều kiện ở JSP nếu cần

        // Điều hướng sang trang thông tin chi tiết
        request.getRequestDispatcher("/director/informationStudent.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
