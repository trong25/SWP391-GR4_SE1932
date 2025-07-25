/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.student;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.payment.Payment;
import model.payment.PaymentDAO;
import model.student.Student;

/**
 * Servlet PaymentList xử lý các yêu cầu HTTP để hiển thị danh sách thanh toán của sinh viên.
 *
 * URL Mapping: /student/payments
 *
 * Chức năng:
 * - Kiểm tra sinh viên đã đăng nhập chưa (qua session)
 * - Lấy danh sách các khoản thanh toán của sinh viên từ CSDL qua PaymentDAO
 * - Chuyển tiếp dữ liệu sang trang payments.jsp để hiển thị danh sách
 *
 * Phân quyền: Chỉ sinh viên đã đăng nhập mới được phép xem danh sách thanh toán
 *
 * @author KienPN
 */
@WebServlet(name = "PaymentList", urlPatterns = {"/student/payments"})
public class PaymentList extends HttpServlet {

    /**
     * Xử lý yêu cầu HTTP GET để hiển thị danh sách thanh toán của sinh viên.
     *
     * Quy trình:
     * - Lấy thông tin sinh viên từ session
     * - Nếu chưa đăng nhập, trả về lỗi
     * - Nếu đã đăng nhập, lấy danh sách thanh toán từ PaymentDAO
     * - Đặt danh sách vào attribute và forward sang payments.jsp
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException nếu có lỗi servlet
     * @throws IOException nếu có lỗi IO
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Student student = (Student) request.getSession().getAttribute("student");

        try {
            if (student == null) {
                Exception ex = new Exception("Student null");
                throw ex;
            }
            PaymentDAO paymentDAO = new PaymentDAO();
            List<Payment> payments = paymentDAO.getListPaymentByStudentId(student.getId());
            request.setAttribute("payments", payments);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("payments.jsp").forward(request, response);
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
        return "Servlet hiển thị danh sách thanh toán của sinh viên.";
    }// </editor-fold>

}
