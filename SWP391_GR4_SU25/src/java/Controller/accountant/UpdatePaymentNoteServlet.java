/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.accountant;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.payment.PaymentDAO;

/**
 *
 * @author admin
 */
@WebServlet(name = "UpdatePaymentNoteServlet", urlPatterns = {"/accountant/updatePaymentNote"})
public class UpdatePaymentNoteServlet extends HttpServlet {

    private PaymentDAO paymentDAO;

    @Override
    public void init() throws ServletException {
        paymentDAO = new PaymentDAO();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdatePaymentNoteServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdatePaymentNoteServlet at " + request.getContextPath() + "</h1>");
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
        processRequest(request, response);
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
        // Thiết lập response type là JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter out = response.getWriter();

        try {
            // Lấy parameters từ request
            String paymentIdStr = request.getParameter("paymentId");
            String note = request.getParameter("note");

            // Validate payment ID
            if (paymentIdStr == null || paymentIdStr.trim().isEmpty()) {
                out.print("{\"success\": false, \"message\": \"Payment ID không được để trống\"}");
                return;
            }

            String paymentId = request.getParameter("paymentId");


            // Note có thể null hoặc empty (cho phép xóa ghi chú)
            if (note == null) {
                note = "";
            }

            // Trim whitespace nhưng vẫn cho phép empty string
            note = note.trim();

            // Kiểm tra độ dài ghi chú (tùy chọn - có thể điều chỉnh theo yêu cầu)
            if (note.length() > 1000) {
                out.print("{\"success\": false, \"message\": \"Ghi chú không được vượt quá 1000 ký tự\"}");
                return;
            }

            // Cập nhật ghi chú thanh toán
            boolean updateSuccess = paymentDAO.updatePaymentNote(paymentId, note);

            if (updateSuccess) {
                out.print("{\"success\": true, \"message\": \"Cập nhật ghi chú thành công\"}");

                // Log activity (optional)
                System.out.println("Payment note updated - ID: " + paymentId + ", Note length: " + note.length());
            } else {
                out.print("{\"success\": false, \"message\": \"Không thể cập nhật ghi chú. Vui lòng thử lại!\"}");
            }

        } catch (Exception e) {
            System.err.println("Error in UpdatePaymentNoteServlet: " + e.getMessage());
            e.printStackTrace();

            out.print("{\"success\": false, \"message\": \"Có lỗi xảy ra trong quá trình xử lý. Vui lòng thử lại!\"}");
        }

        out.flush();
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
