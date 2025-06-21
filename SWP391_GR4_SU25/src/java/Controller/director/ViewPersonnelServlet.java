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
 *
 * @author admin
 */
public class ViewPersonnelServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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
        HttpSession session = request.getSession(true);
        try {
            String message = (String) session.getAttribute("message");
            String type = (String) session.getAttribute("type");
            String xid = request.getParameter("id");
            String xpage = request.getParameter("page");
            Personnel person;
            PersonnelDAO personnelDAO = new PersonnelDAO();
            person = personnelDAO.getPersonnel(xid);
            if (person == null) {
                session.setAttribute("message", "Không tìm thấy nhân viên.");
                session.setAttribute("type", "error");
                response.sendRedirect("waitlistpersonnel?page=" + xpage);
                return;
            }

            request.setAttribute("person", person);
            request.setAttribute("message", message);
            request.setAttribute("type", type);
            request.setAttribute("page", xpage);
            request.getRequestDispatcher("viewPersonnelInfomation.jsp").forward(request, response);
            session.removeAttribute("message");
            session.removeAttribute("type");
        } catch (Exception e) {
            e.printStackTrace(); // In lỗi ra log server
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Lỗi xử lý dữ liệu");
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
