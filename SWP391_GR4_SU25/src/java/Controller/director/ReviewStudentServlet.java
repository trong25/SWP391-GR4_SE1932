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
import model.student.StudentDAO;

/**
 * Servlet ReviewStudentServlet xử lý các yêu cầu HTTP liên quan đến việc duyệt học sinh.
 * 
 * URL Mapping: /director/reviewstudent
 * 
 * 🔹 Chức năng:
 * - Phương thức GET: Lấy danh sách học sinh đang chờ xử lý từ cơ sở dữ liệu và hiển thị trên trang `reviewStudent.jsp`.
 * - Phương thức POST: 
 *    + Nhận hành động duyệt hoặc từ chối từ form gửi lên.
 *    + Cập nhật trạng thái của học sinh trong cơ sở dữ liệu.
 *    + Gửi thông báo (toast) phản hồi về kết quả xử lý.
 * 
 * ⚠️ Quyền truy cập: Chỉ Giám đốc có thể truy cập servlet này.
 * 
 * 📌 Trạng thái học sinh sử dụng:
 * - "đang chờ xử lý": Chờ giám đốc duyệt
 * - "đang theo học": Đã được duyệt
 * - "không được duyệt": Đã bị từ chối
 * 
 * View: `reviewStudent.jsp`
 * 
 * @author ThanhNT
 * @version 1.0
 */

public class ReviewStudentServlet extends HttpServlet {

  
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ReviewStudentServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReviewStudentServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StudentDAO studentDAO = new StudentDAO();
        request.setAttribute("listStudent", studentDAO.getStudentByStatus("đang chờ xử lý"));
        request.getRequestDispatcher("reviewStudent.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        StudentDAO studentDAO = new StudentDAO();

        String action = request.getParameter("action");
        String studentID = request.getParameter("id");
        String toastMessage = "";
        String toastType = "";
        boolean result = false;
        if (action != null) {
            if (action.equals("accept")) {
                result = studentDAO.updateStudentStatus(studentID, "đang theo học");
            } else if (action.equals("decline")) {
                studentDAO.updateStudentStatus(studentID, "không được duyệt");
            }
        }
        if (result) {
            toastMessage = "Duyệt thành công";
            toastType = "success";
        } else {
            toastMessage = "Từ chối thành công";
            toastType = "success";
        }
        request.setAttribute("listStudent", studentDAO.getStudentByStatus("đang chờ xử lý"));
        request.setAttribute("toastMessage", toastMessage);
        request.setAttribute("toastType", toastType);
        request.getRequestDispatcher("reviewStudent.jsp").forward(request, response);
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
