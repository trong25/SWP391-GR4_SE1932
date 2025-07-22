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
import model.subject.SubjectDAO;

/**
 * Servlet ReviewSubjectServlet xử lý các yêu cầu HTTP liên quan đến việc duyệt
 * môn học cho hệ thống quản lý trung tâm.
 *
 * URL Mapping: /director/reviewsubject 
 *
 * Chức năng: - Nhận dữ liệu từ client (JSP/HTML form) về hành động duyệt hoặc
 * từ chối môn học - Gọi SubjectDAO để truy vấn hoặc cập nhật trạng thái môn học
 * - Chuyển tiếp (forward) đến trang reviewSubject.jsp để hiển thị danh sách môn
 * học đang chờ xử lý cùng với thông báo
 *
 * Phân quyền: Chỉ vai trò **Giám đốc (Director)** được phép truy cập và xử lý
 * yêu cầu này
 *
 * @author ThanhNT
 * @version 1.0
 */
public class ReviewSubjectServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ReviewSubjectServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReviewSubjectServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SubjectDAO subjectDAO = new SubjectDAO();
        request.setAttribute("listSubjectPending", subjectDAO.getSubjectsByStatus("đang chờ xử lý"));
        request.getRequestDispatcher("reviewSubject.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SubjectDAO subjectDAO = new SubjectDAO();
        String action = request.getParameter("action");
        String subjectId = request.getParameter("id");
        String toastMessage = "";
        String toastType = "";
        boolean result = false;
        if (action.equals("accept")) {
            result = subjectDAO.updateStatusById(subjectId, "đã được duyệt");
        } else if (action.equals("decline")) {
            subjectDAO.updateStatusById(subjectId, "không được duyệt");
        }
        if (result) {
            toastType = "success";
            toastMessage = "Duyệt thành công";
        } else {
            toastMessage = "Từ chối thành công";
            toastType = "success";
        }
        request.setAttribute("listSubjectPending", subjectDAO.getSubjectsByStatus("đang chờ xử lý"));
        request.setAttribute("toastMessage", toastMessage);
        request.setAttribute("toastType", toastType);
        request.getRequestDispatcher("reviewSubject.jsp").forward(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
