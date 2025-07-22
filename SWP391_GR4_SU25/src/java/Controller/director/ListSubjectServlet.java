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
import java.util.ArrayList;
import java.util.List;
import model.subject.Subject;
import model.subject.SubjectDAO;

/**
 * Servlet ListSubjectServlet xử lý các yêu cầu HTTP liên quan đến việc hiển thị danh sách môn học theo trạng thái.
 * 
 * URL Mapping: /director/listsubject 
 * 
 * Chức năng:
 * - Nhận dữ liệu từ client (JSP/HTML form) về trạng thái môn học cần lọc (đã được duyệt, đang chờ xử lý, không được duyệt)
 * - Gọi SubjectDAO để truy vấn danh sách môn học theo trạng thái
 * - Chuyển tiếp (forward) đến trang listSubject.jsp để hiển thị danh sách môn học tương ứng
 * 
 * Phân quyền: Chỉ vai trò **Giám đốc (Director)** được phép truy cập để xem và lọc danh sách môn học
 * 
 * @author ThanhNT
 * @version 1.0
 */

public class ListSubjectServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ListSubjectServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListSubjectServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SubjectDAO subjectDAO = new SubjectDAO();
        request.setAttribute("listSubject", subjectDAO.getSubjectsByStatus("đã được duyệt"));
        request.getRequestDispatcher("listSubject.jsp").forward(request, response);
    }

 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SubjectDAO subjectDAO = new SubjectDAO();
        String status = request.getParameter("status");
        List<Subject> subjectList = new ArrayList<>();
        switch (status) {
            case "approve":
                subjectList = subjectDAO.getSubjectsByStatus("đã được duyệt");
                break;
            case "pending":
                subjectList = subjectDAO.getSubjectsByStatus("đang chờ xử lý");
                break;
            case "decline":
                subjectList = subjectDAO.getSubjectsByStatus("không được duyệt");
                break;

            default:
                break;
        }
        request.setAttribute("listSubject", subjectList);
        request.getRequestDispatcher("listSubject.jsp").forward(request, response);

    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
