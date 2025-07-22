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
import java.util.List;
import model.student.Student;
import model.student.StudentDAO;


/**
 * Servlet ListStudentServlet xử lý các yêu cầu HTTP liên quan đến hiển thị danh
 * sách học sinh theo lớp và năm học.
 *
 * URL Mapping: /admin/liststudent (được cấu hình trong web.xml hoặc
 * Annotation nếu có)
 *
 * Chức năng: - Nhận dữ liệu từ client (tham số lớp và năm học) - Gọi DAO để
 * truy xuất danh sách học sinh, lớp học, giáo viên và năm học - Chuyển tiếp dữ
 * liệu đến trang JSP để hiển thị thông tin học sinh
 *
 * Phân quyền: Chỉ vai trò Admin được phép truy cập chức
 * năng này
 *
 *
 * @author ThanhNT
 *
 * @author Hachithanh
 *
 * @version 1.0
 */
public class ListStudentServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ListStudentServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListStudentServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        StudentDAO studentDAO = new StudentDAO();
        String status = request.getParameter("status");
        List<Student> listStudents;

        if (status == null || status.isEmpty() || status.equals("all")) {
            listStudents = studentDAO.getStudentByStatus("đang theo học");
        } else {
            listStudents = studentDAO.getStudentByStatus(status);
        }

// Truyền danh sách trạng thái sang JSP
        List<String> listStatus =studentDAO.getAllStudentStatus();
        
        request.setAttribute("statuss", listStatus); 
        request.setAttribute("selectedStatus", status);
        request.setAttribute("listPupil", listStudents);
        request.getRequestDispatcher("listStudent.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       // processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
