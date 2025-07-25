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
 * Servlet UpdatePaymentStatusServlet xử lý các yêu cầu HTTP để cập nhật trạng thái thanh toán của hóa đơn học phí.
 * 
 * URL Mapping: /accountant/updatePaymentStatus
 * 
 * Chức năng:
 * - Nhận dữ liệu từ client, bao gồm `paymentId` và `status`
 * - Kiểm tra tính hợp lệ của dữ liệu đầu vào
 * - Gọi `PaymentDAO` để cập nhật trạng thái hóa đơn trong cơ sở dữ liệu
 * - Phản hồi về client dưới dạng JSON thông báo kết quả (thành công/thất bại)
 * 
 * Phân quyền: Chỉ vai trò Accountant (Kế toán) được phép truy cập chức năng này
 * 
 * @author ThanhNT
 * @version 1.0
 */

@WebServlet(name = "UpdatePaymentStatusServlet", urlPatterns = {"/accountant/updatePaymentStatus"})
public class UpdatePaymentStatusServlet extends HttpServlet {

    private PaymentDAO paymentDAO;

    @Override
    public void init() throws ServletException {
        paymentDAO = new PaymentDAO();
    }

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
            String status = request.getParameter("status");

            // Validate input
            if (paymentIdStr == null || paymentIdStr.trim().isEmpty()) {
                out.print("{\"success\": false, \"message\": \"Payment ID không được để trống\"}");
                return;
            }

            if (status == null || status.trim().isEmpty()) {
                out.print("{\"success\": false, \"message\": \"Trạng thái không được để trống\"}");
                return;
            }

            String paymentId = request.getParameter("paymentId");

            // Validate status values
            if (!status.equals("paid") && !status.equals("Not yet") && !status.equals("overdue")) {
                out.print("{\"success\": false, \"message\": \"Trạng thái không hợp lệ\"}");
                return;
            }

            // Cập nhật trạng thái thanh toán
            boolean updateSuccess = paymentDAO.updatePaymentStatus(paymentId, status);

            if (updateSuccess) {
                out.print("{\"success\": true, \"message\": \"Cập nhật trạng thái thanh toán thành công\"}");

                // Log activity (optional)
                System.out.println("Payment status updated - ID: " + paymentId + ", Status: " + status);
            } else {
                out.print("{\"success\": false, \"message\": \"Không thể cập nhật trạng thái thanh toán. Vui lòng thử lại!\"}");
            }

        } catch (Exception e) {
            System.err.println("Error in UpdatePaymentStatusServlet: " + e.getMessage());
            e.printStackTrace();

            out.print("{\"success\": false, \"message\": \"Có lỗi xảy ra trong quá trình xử lý. Vui lòng thử lại!\"}");
        }

        out.flush();
    }

    @Override
    public String getServletInfo() {
        return "Update Payment Status Servlet";
    }
}
