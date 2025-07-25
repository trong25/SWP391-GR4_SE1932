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
 * Servlet PaymentServlet xử lý các yêu cầu HTTP để hiển thị chi tiết một khoản thanh toán của sinh viên.
 *
 * URL Mapping: /student/payment
 *
 * Chức năng:
 * - Nhận ID của khoản thanh toán từ request parameter (?id=...)
 * - Lấy thông tin chi tiết khoản thanh toán từ CSDL qua PaymentDAO
 * - Chuyển tiếp dữ liệu sang trang payment.jsp để hiển thị chi tiết
 *
 * Phân quyền: Chỉ sinh viên đã đăng nhập mới được phép xem chi tiết khoản thanh toán
 *
 * @author KienPN
 */
@WebServlet(name = "PaymentServlet", urlPatterns = {"/student/payment"})
public class PaymentServlet extends HttpServlet {

    /**
     * Xử lý yêu cầu HTTP GET để hiển thị chi tiết một khoản thanh toán.
     *
     * Quy trình:
     * - Lấy ID khoản thanh toán từ request parameter (?id=...)
     * - Lấy thông tin khoản thanh toán từ PaymentDAO
     * - Đặt thông tin vào attribute và forward sang payment.jsp
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException nếu có lỗi servlet
     * @throws IOException nếu có lỗi IO
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        try {
            PaymentDAO paymentDAO = new PaymentDAO();
            Payment payment = paymentDAO.getById(Integer.parseInt(id));
            if (payment == null) {
                Exception ex = new Exception("No payment found with id " + id);
            }
            request.setAttribute("payment", payment);
            request.getRequestDispatcher("payment.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
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
        return "Servlet hiển thị chi tiết khoản thanh toán của sinh viên.";
    }// </editor-fold>

}
