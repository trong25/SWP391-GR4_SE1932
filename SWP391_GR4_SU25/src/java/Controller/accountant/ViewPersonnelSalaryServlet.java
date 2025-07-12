/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.accountant;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.personnel.Personnel;
import model.personnel.PersonnelDAO;

/**
 *ViewPersonnelSalaryServlet chịu trách nhiệm lấy thông tin chi tiết của nhân viên để hiển thị dữ liệu ở trang viewPersonnelInformation.jsp
 cập nhật trạng thái thanh toán gọi hàm updatePersonnelStatus,
 sử dụng hàm getPersonnel() đê lấy thông tin của nhân viên
 * 
 * @author TrongNV
 * @version 1.0
 */
public class ViewPersonnelSalaryServlet extends HttpServlet {
   protected void processRequest(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ViewPersonnelSalaryServlet</title>");  
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ViewPersonnelSalaryServlet at " + request.getContextPath () + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    } 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      String xid = request.getParameter("id");
        Personnel person;
        PersonnelDAO personnelDAO = new PersonnelDAO();
        person = personnelDAO.getPersonnel(xid);
        request.setAttribute("person", person);
        request.getRequestDispatcher("viewPersonnelSalarylnformation.jsp").forward(request, response);
    } 

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
     String id = request.getParameter("id");
     PersonnelDAO personnelDAO = new PersonnelDAO();
     personnelDAO.updatePersonnelStatus(id, "đã thanh toán");
     response.sendRedirect("viewpersonnel?id="+id);
    }


}
