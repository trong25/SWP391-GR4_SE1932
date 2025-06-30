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
import jakarta.servlet.http.HttpSession;
import model.personnel.Personnel;
import model.personnel.PersonnelDAO;

/**
 * Servlet ViewPersonnelServlet xử lý các yêu cầu HTTP liên quan đến việc xem
 * chi tiết thông tin của một nhân sự cụ thể.
 *
 * URL Mapping: Không dùng @WebServlet mà cấu hình qua web.xml hoặc mapping
 * Servlet mặc định trong project.
 *
 * Chức năng: - Nhận ID nhân sự từ client (thông qua URL param) - Gọi
 * PersonnelDAO để lấy thông tin chi tiết nhân sự - Hiển thị thông tin nhân sự
 * trên trang viewPersonnelInfomation.jsp - Xử lý lỗi khi nhân sự không tồn tại
 * hoặc lỗi hệ thống
 *
 * Phân quyền: Chỉ người dùng có vai trò Giám đốc (Director) được phép xem thông
 * tin chi tiết nhân sự
 *
 * @author ThanhNT
 * @version 1.0
 */
public class ViewPersonnelServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ViewPersonnelServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewPersonnelServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        try {
            String message = (String) session.getAttribute("message");
            String type = (String) session.getAttribute("type");
            String xid = request.getParameter("id");
            String xpage = request.getParameter("page");
            Personnel person;
            PersonnelDAO personnelDAO = new PersonnelDAO();
            person = personnelDAO.getPersonnel(xid);
            String xstatus = request.getParameter("status");
            String xrole = request.getParameter("role");
            String xsearch = request.getParameter("search");
            if (person == null) {
                session.setAttribute("message", "Không tìm thấy nhân viên.");
                session.setAttribute("type", "error");
                // 👇 Gửi kèm các tham số lọc nếu có
                String redirectUrl = "waitlistpersonnel?page=" + xpage;
                if (xstatus != null) {
                    redirectUrl += "&status=" + xstatus;
                }
                if (xrole != null) {
                    redirectUrl += "&role=" + xrole;
                }
                if (xsearch != null) {
                    redirectUrl += "&search=" + xsearch;
                }

                response.sendRedirect(redirectUrl);
                return;
            }

            request.setAttribute("person", person);
            request.setAttribute("message", message);
            request.setAttribute("type", type);
            request.setAttribute("page", xpage);
            request.setAttribute("status", xstatus);
            request.setAttribute("role", xrole);
            request.setAttribute("search", xsearch);
            request.getRequestDispatcher("viewPersonnelInfomation.jsp").forward(request, response);
            session.removeAttribute("message");
            session.removeAttribute("type");
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra log server
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi xử lý dữ liệu");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
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
