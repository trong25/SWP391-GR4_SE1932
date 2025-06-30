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
import java.util.ArrayList;
import java.util.List;
import model.personnel.Personnel;
import model.personnel.PersonnelDAO;
import model.role.Role;
import utils.Helper;

/**
 * Servlet ListPersonnelServlet xử lý các yêu cầu HTTP để hiển thị danh sách
 * nhân sự theo chức vụ và trạng thái.
 *
 * URL Mapping: /director/listpersonnel
 *
 * Chức năng: - Nhận tham số từ form lọc chức vụ và trạng thái nhân sự (GET hoặc
 * POST) - Gọi RoleDAO và PersonnelDAO để lấy danh sách vai trò và nhân sự từ cơ
 * sở dữ liệu - Gửi dữ liệu danh sách về trang JSP để hiển thị - Hỗ trợ lọc nhân
 * sự theo từng vai trò hoặc xem toàn bộ
 *
 * Phân quyền: Chỉ Giám đốc (Director) được phép truy cập chức năng này.
 *
 * @author ThanhNT
 * @version 1.0
 */
public class ListPersonnelServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ListPersonnelServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListPersonnelServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            HttpSession session = request.getSession(true);
            String message = (String) session.getAttribute("message");
            String type = (String) session.getAttribute("type");

            PersonnelDAO personnelDAO = new PersonnelDAO();
            List<Personnel> persons = personnelDAO.getAllPersonnels();
            List<Role> roles = personnelDAO.getAllPersonnelRole();
            List<String> statuss = personnelDAO.getAllStatus();

            request.setAttribute("selectedstatus", "all");
            request.setAttribute("selectedrole", "all");
            request.setAttribute("message", message);
            request.setAttribute("type", type);
            request.setAttribute("persons", persons);
            request.setAttribute("roles", roles);
            request.setAttribute("statuss", statuss);

            request.getRequestDispatcher("listPersonnel.jsp").forward(request, response);
            session.removeAttribute("message");
            session.removeAttribute("type");
        } catch (Exception e) {
            e.printStackTrace(); // log lỗi ra console
//        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Có lỗi xảy ra: " + e.getMessage());
        }

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
        //processRequest(request, response);
        HttpSession session = request.getSession(true);
        String message = (String) session.getAttribute("message");
        String type = (String) session.getAttribute("type");
        String role = request.getParameter("role");
        String status = request.getParameter("status");
        String search = request.getParameter("search");
//        System.out.println(role);
//        System.out.println(status);

        List<Personnel> persons = new ArrayList<Personnel>();
        List<Role> roles = new ArrayList<>();
        PersonnelDAO personnelDAO = new PersonnelDAO();
        roles = personnelDAO.getAllPersonnelRole();
        if ((role != null || status != null) && search == null) {
            if (status.equalsIgnoreCase("all") && role.equalsIgnoreCase("all")) {
                persons = personnelDAO.getAllPersonnels();
            } else if (!status.equalsIgnoreCase("all") && role.equalsIgnoreCase("all")) {
                persons = personnelDAO.getPersonnelByStatus(status);

            } else if (!role.equalsIgnoreCase("all") && status.equalsIgnoreCase("all")) {
                try {
                    int xrole = Integer.parseInt(role);
                    persons = personnelDAO.getPersonnelByRole(xrole);
                } catch (NumberFormatException e) {
                    persons = personnelDAO.getPersonnelByRole(-1);
                }
            } else {
                persons = personnelDAO.getPersonnelByIdNameRoleStatus(status, role);
            }
        }
        List<String> statuss = new ArrayList<>();
        statuss = personnelDAO.getAllStatus();
        request.setAttribute("statuss", statuss);
        request.setAttribute("selectedstatus", status);
        request.setAttribute("selectedrole", role);
        request.setAttribute("message", message);
        request.setAttribute("type", type);
        request.setAttribute("roles", roles);
        request.setAttribute("persons", persons);
        request.getRequestDispatcher("listPersonnel.jsp").forward(request, response);
        session.removeAttribute("message");
        session.removeAttribute("type");
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
