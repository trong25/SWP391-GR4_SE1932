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
import java.util.List;
import model.classes.ClassDAO;
import model.schoolYear.SchoolYear;
import model.schoolYear.SchoolYearDAO;

/**
 *
 * @author ThanhNT

 */
public class ReviewClassServlet extends HttpServlet {

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
            out.println("<title>Servlet ReviewClassServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReviewClassServlet at " + request.getContextPath() + "</h1>");
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
        //  processRequest(request, response);
        //Thanhnthe181132 
        ClassDAO classDAO = new ClassDAO();
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        
        String schoolYearId = request.getParameter("schoolYearId");
        
        List<SchoolYear> schoolYears = schoolYearDAO.getAll();
        request.setAttribute("schoolYears", schoolYears);

      

        try {//nếu null thì lấy schoolYearId mới nhất 
            if (schoolYearId == null) {

                schoolYearId = schoolYearDAO.getLatest().getId();
            }
            HttpSession session = request.getSession();
            String result = (String) session.getAttribute("result");
            session.removeAttribute("result");
            if (result != null) {
                if (result.equals("success")) {
                    request.setAttribute("toastType", result);// toastType = "success"
                    request.setAttribute("toastMessage", "Duyệt thành công");
                } else {
                    request.setAttribute("toastType", "error");
                    request.setAttribute("toastMessage", result);// Hiển thị lỗi cụ thể
                }
            }
            request.setAttribute("classes", classDAO.getByStatus("đang chờ xử lý", schoolYearId));//Gửi ID của năm học được chọn sang JSP để có thể giữ nguyên lựa chọn dropdown.
            request.setAttribute("selectedSchoolYearId", schoolYearId);
            request.getRequestDispatcher("reviewClass.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
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
        //  processRequest(request, response);
        String action = request.getParameter("action");
        String schoolYearId = request.getParameter("schoolYearId");
        ClassDAO classDAO = new ClassDAO();
        if (action.equals("accept") || action.equals("decline")) {
            String classId = request.getParameter("id");
            String result = classDAO.reviewClass(action, classId);
            HttpSession session = request.getSession();
            session.setAttribute(result, "result");
            response.sendRedirect("reviewclass?schoolYearId=" + schoolYearId);

        }

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
