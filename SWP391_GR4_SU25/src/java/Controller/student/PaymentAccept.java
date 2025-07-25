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
 * Servlet PaymentAccept xử lý xác nhận thanh toán cho sinh viên.
 *
 * URL Mapping: /student/PaymentAccept
 *
 * Chức năng:
 * - Nhận tham số 'code' từ request (GET)
 * - Kiểm tra giao dịch thanh toán tương ứng trong CSDL
 * - Nếu tồn tại, cập nhật trạng thái thanh toán sang "Paid"
 * - Trả về mã trạng thái HTTP phù hợp (200 nếu thành công, 400 nếu lỗi)
 *
 * Phân quyền: Chỉ sinh viên đã đăng nhập và có mã giao dịch hợp lệ mới xác nhận được thanh toán
 *
 * @author KienPN
 */
@WebServlet(name = "PaymentAccept", urlPatterns = {"/student/PaymentAccept"})
public class PaymentAccept extends HttpServlet {

    /**
     * Xử lý yêu cầu HTTP GET để xác nhận thanh toán.
     *
     * Quy trình:
     * - Lấy mã giao dịch từ request parameter (?code=...)
     * - Tìm giao dịch trong CSDL qua PaymentDAO
     * - Nếu không tìm thấy, trả về lỗi 400
     * - Nếu tìm thấy, cập nhật trạng thái sang "Paid" và trả về 200
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException nếu có lỗi servlet
     * @throws IOException nếu có lỗi IO
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
     * Xử lý yêu cầu HTTP POST (không sử dụng trong chức năng này).
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException nếu có lỗi servlet
     * @throws IOException nếu có lỗi IO
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Trả về mô tả ngắn gọn về servlet.
     *
     * @return Chuỗi mô tả servlet
     */
    @Override
    public String getServletInfo() {
        return "Servlet xác nhận thanh toán cho sinh viên.";
    }// </editor-fold>

}
