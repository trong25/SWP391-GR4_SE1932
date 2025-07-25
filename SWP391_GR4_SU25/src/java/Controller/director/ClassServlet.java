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
import java.util.List;
import model.classes.Class;
import model.classes.ClassDAO;
import model.schoolYear.SchoolYear;
import model.schoolYear.SchoolYearDAO;

/**
 * Servlet ClassServlet xử lý các yêu cầu HTTP liên quan đến việc hiển thị danh sách lớp học theo năm học và trạng thái.
 * 
 * URL Mapping: /director/class
 * 
 * Chức năng:
 * - Trong phương thức `doGet`: 
 *   + Lấy danh sách năm học từ `SchoolYearDAO`
 *   + Lấy danh sách lớp học theo năm học và trạng thái (nếu có)
 *   + Chuyển tiếp dữ liệu sang trang `class.jsp` để hiển thị
 * - Trong phương thức `doPost`: chưa triển khai xử lý
 * 
 * Phân quyền: Chỉ vai trò Director (Giám đốc) được phép truy cập chức năng này
 * 
 * @author ThanhNT
 * @version 1.0
 */

public class ClassServlet extends HttpServlet {

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
            out.println("<title>Servlet ClassServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ClassServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       // processRequest(request, response);
       try {
            SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
            ClassDAO classDAO = new ClassDAO();
            request.setAttribute("schoolYears", schoolYearDAO.getAll());
            String schoolYearId = request.getParameter("schoolYearId");
            if (schoolYearId == null) {
                SchoolYear lastestSchoolYear = schoolYearDAO.getLatest();
                schoolYearId = lastestSchoolYear.getId();
            }
            String status = request.getParameter("status");
            List<Class> classes;
            if (status != null && !status.equals("all") && !status.equals("đang chờ xử lý")) {
                classes = classDAO.getByStatus(status, schoolYearId);
            } else {
                status = "all";
                classes = classDAO.getBySchoolYear(schoolYearId);
            }
            request.setAttribute("status", status);
            request.setAttribute("selectedSchoolYearId", schoolYearId);
            request.setAttribute("classes", classes);
            request.getRequestDispatcher("class.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // processRequest(request, response);
        
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