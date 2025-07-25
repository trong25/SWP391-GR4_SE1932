/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.director;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import model.payment.PaymentDAO;

/**
 * Servlet ViewRevenueServlet xử lý các yêu cầu HTTP liên quan đến việc hiển thị doanh thu theo từng tháng trong năm.
 * 
 * URL Mapping: /director/revenue
 * 
 * Chức năng:
 * - Nhận tham số năm từ client (qua request parameter)
 * - Gọi PaymentDAO để truy xuất doanh thu từng tháng theo năm được chọn
 * - Đưa dữ liệu vào request attribute và chuyển tiếp đến trang JSP `viewRevenue.jsp` để hiển thị
 * 
 * Phân quyền: Chỉ vai trò Director (Giám đốc) được phép truy cập chức năng này
 * 
 * @author ThanhNT
 * @version 1.0
 */

@WebServlet(name="ViewRevenueServlet", urlPatterns={"/director/revenue"})
public class ViewRevenueServlet extends HttpServlet {
   

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RevenueServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RevenueServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 

  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
       String yearParam = request.getParameter("year");
        int year = (yearParam != null) ? Integer.parseInt(yearParam) : 2024;

        // Lấy dữ liệu từ DAO
        PaymentDAO paymentDAO = new PaymentDAO();
        Map<Integer, Double> monthlyRevenue = paymentDAO.getMonthlyRevenue(year);

        // Đưa vào request
        request.setAttribute("monthlyRevenue", monthlyRevenue);
        request.setAttribute("selectedYear", year);
                request.getRequestDispatcher("viewRevenue.jsp").forward(request, response);

    } 

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }

 
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
