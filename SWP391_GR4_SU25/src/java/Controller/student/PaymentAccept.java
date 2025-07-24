/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.student;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.payment.Payment;
import model.payment.PaymentDAO;

/**
 * Servlet xử lý việc xác nhận thanh toán cho sinh viên.
 * URL: /student/PaymentAccept
 * Nhận tham số `code` từ request và cập nhật trạng thái thanh toán tương ứng sang "Paid".
 * 
 * @author KienPN
 */
@WebServlet(name = "PaymentAccept", urlPatterns = {"/student/PaymentAccept"})
public class PaymentAccept extends HttpServlet {

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
        String code = request.getParameter("code");
        response.setContentType("application/json");
        try {
            PaymentDAO paymentDAO = new PaymentDAO();
            Payment payment = paymentDAO.getByCode(code);
            if (payment == null) {
                Exception exception = new Exception("No transaction found with code:" + code);
                throw exception;
            }
            boolean isPaid = paymentDAO.updateStatus(payment.getId(), "Paid");
            response.setStatus(200);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(400);
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
