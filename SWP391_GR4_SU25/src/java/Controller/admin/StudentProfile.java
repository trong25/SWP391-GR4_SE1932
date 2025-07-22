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
import model.student.Student;
import model.student.StudentDAO;

/**
 * Servlet `StudentProfile` dùng để xử lý các yêu cầu liên quan đến hồ sơ chi
 * tiết của học sinh.
 *
 * URL Mapping: /admin/studentsprofile
 *
 * Chức năng: - Nhận ID học sinh và hành động (accept/decline/dropout) từ
 * request (phương thức POST). - Sử dụng StudentDAO để truy xuất và cập nhật
 * trạng thái học sinh trong cơ sở dữ liệu. - Sau khi cập nhật, lấy lại thông
 * tin mới nhất và chuyển tiếp đến trang JSP để hiển thị.
 *
 * Giao diện hiển thị: informationStudent.jsp
 *
 * Phân quyền truy cập: - Chỉ những người dùng có vai trò Admin hoặc được cấp
 * quyền đặc biệt mới có thể truy cập chức năng này.
 *
 * Tác giả: ThanhNT Phiên bản: 2.0 Ngày cập nhật: 2025-07-21
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
        String id = request.getParameter("id");
        String action = request.getParameter("action");

        StudentDAO studentDAO = new StudentDAO();
        Student student = studentDAO.getStudentsById(id);

        if (student == null) {
            request.setAttribute("toastMessage", "Không tìm thấy học sinh với ID: " + id);
            request.setAttribute("toastType", "error");
            request.getRequestDispatcher("reviewStudent.jsp").forward(request, response);
            return;
        }

        String message = "";
        String type = "success";

        if (action != null && id != null) {
            switch (action) {
                case "accept":
                    studentDAO.updateStudentStatus(id, "đang theo học");
                    message = "Đã chấp nhận học sinh.";
                    break;
                case "decline":
                    studentDAO.updateStudentStatus(id, "không được duyệt");
                    message = "Đã từ chối học sinh.";
                    break;
                case "dropout":
                    studentDAO.updateStudentStatus(id, "đã nghỉ học");
                    message = "Học sinh đã được chuyển sang trạng thái nghỉ học.";
                    break;
                default:
                    message = "Hành động không hợp lệ.";
                    type = "error";
                    break;
            }
        }

        // Lấy lại thông tin mới sau khi cập nhật
        student = studentDAO.getStudentsById(id);
        request.setAttribute("student", student);
        request.setAttribute("status", studentDAO.getAllStudentStatus());
        request.setAttribute("toastMessage", message);
        request.setAttribute("toastType", type);
        request.getRequestDispatcher("informationStudent.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
