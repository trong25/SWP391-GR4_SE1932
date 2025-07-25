package Controller.accountant;

import model.student.StudentAttendanceSummaryDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import model.payment.Payment;
import model.payment.PaymentDAO;
import model.student.StudentAttendanceSummary;

/**
 * Servlet SendPaymentNoticeServlet xử lý các yêu cầu HTTP liên quan đến việc tạo và gửi thông báo thanh toán học phí cho học sinh.
 * 
 * URL Mapping: /notify-payment (gián tiếp thông qua cấu hình điều hướng hoặc đường dẫn servlet map nếu có)
 * 
 * Chức năng:
 * - Trong phương thức `doGet`: 
 *   + Lấy danh sách học sinh đang học và số buổi đã học trong tháng
 *   + Hiển thị danh sách này trên trang JSP để kế toán tạo hóa đơn
 * - Trong phương thức `doPost`: 
 *   + Nhận dữ liệu từ form hóa đơn học phí (mã hóa đơn, học sinh, lớp, số tiền, hạn nộp...)
 *   + Kiểm tra và xử lý dữ liệu, tạo đối tượng `Payment`
 *   + Gọi `PaymentDAO` để lưu thông tin thanh toán vào cơ sở dữ liệu
 *   + Thông báo kết quả (thành công/thất bại) qua session attribute
 * 
 * Phân quyền: Chỉ vai trò Accountant (Kế toán) được phép truy cập chức năng này
 * 
 * @author ThanhNT
 * @version 1.0
 */

public class SendPaymentNoticeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Sử dụng DAO mới
            StudentAttendanceSummaryDAO attendanceDAO = new StudentAttendanceSummaryDAO();
            List<StudentAttendanceSummary> studentList = attendanceDAO.getActiveStudentsAttendance();

            // Đặt attribute với tên đúng theo JSP
            request.setAttribute("listStudent", studentList);

            // Forward tới JSP
            request.getRequestDispatcher("sendPaymentNotice.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();

            // Set thông báo lỗi
            HttpSession session = request.getSession();
            session.setAttribute("toastMessage", "Có lỗi xảy ra khi tải danh sách học sinh!");
            session.setAttribute("toastType", "error");

            // Forward với danh sách trống
            request.setAttribute("listStudent", List.of());
            request.getRequestDispatcher("sendPaymentNotice.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();

        try {
            // Lấy parameters từ form
            String code = request.getParameter("code");
            String studentId = request.getParameter("student_id");
            String classId = request.getParameter("class_id");
            
            // Lấy cả amount và amount_raw, ưu tiên amount_raw nếu có
            String amountStr = request.getParameter("amount_raw");
            if (amountStr == null || amountStr.trim().isEmpty()) {
                amountStr = request.getParameter("amount");
                // Nếu amount có format với dấu chấm phẩy, loại bỏ chúng
                if (amountStr != null) {
                    amountStr = amountStr.replace(".", "").replace(",", "");
                }
            }
            
            String status = request.getParameter("status");
            String paymentDateStr = request.getParameter("paymentDate");
            String dueDateStr = request.getParameter("dueDate");
            String note = request.getParameter("note");

          
            // Validate required fields
            if (code == null || code.trim().isEmpty()) {
                session.setAttribute("toastMessage", "Mã hóa đơn không được để trống!");
                session.setAttribute("toastType", "error");
                response.sendRedirect("notify-payment");
                return;
            }
            
            if (studentId == null || studentId.trim().isEmpty()) {
                session.setAttribute("toastMessage", "Mã học sinh không được để trống!");
                session.setAttribute("toastType", "error");
                response.sendRedirect("notify-payment");
                return;
            }
            
            if (classId == null || classId.trim().isEmpty()) {
                session.setAttribute("toastMessage", "Mã lớp không được để trống!");
                session.setAttribute("toastType", "error");
                response.sendRedirect("notify-payment");
                return;
            }
            
            if (amountStr == null || amountStr.trim().isEmpty()) {
                session.setAttribute("toastMessage", "Số tiền không được để trống!");
                session.setAttribute("toastType", "error");
                response.sendRedirect("notify-payment");
                return;
            }
            
            if (dueDateStr == null || dueDateStr.trim().isEmpty()) {
                session.setAttribute("toastMessage", "Hạn đóng tiền không được để trống!");
                session.setAttribute("toastType", "error");
                response.sendRedirect("notify-payment");
                return;
            }

            // Parse amount
            float amount;
            try {
                amount = Float.parseFloat(amountStr.trim());
                if (amount <= 0) {
                    throw new NumberFormatException("Amount must be positive");
                }
                System.out.println("Parsed amount: " + amount);
            } catch (NumberFormatException e) {
                System.out.println("Amount parsing error: " + e.getMessage());
                session.setAttribute("toastMessage", "Số tiền không hợp lệ: " + amountStr);
                session.setAttribute("toastType", "error");
                response.sendRedirect("notify-payment");
                return;
            }

            // Parse dates
            Date paymentDate = null;
            Date dueDate;
            try {
                if (paymentDateStr != null && !paymentDateStr.trim().isEmpty()) {
                    paymentDate = java.sql.Date.valueOf(paymentDateStr.trim());
                }
                dueDate = java.sql.Date.valueOf(dueDateStr.trim());

                // Validate due date is not in the past
                Date today = new Date();
                if (dueDate.before(today)) {
                    session.setAttribute("toastMessage", "Hạn đóng tiền không thể là ngày trong quá khứ!");
                    session.setAttribute("toastType", "error");
                    response.sendRedirect("notify-payment");
                    return;
                }

                System.out.println("Parsed due date: " + dueDate);

            } catch (IllegalArgumentException e) {
                System.out.println("Date parsing error: " + e.getMessage());
                session.setAttribute("toastMessage", "Định dạng ngày không hợp lệ!");
                session.setAttribute("toastType", "error");
                response.sendRedirect("notify-payment");
                return;
            }

            // Tạo Payment object
            Payment payment = new Payment();
            payment.setCode(code.trim());
            payment.setStudentId(studentId.trim());
            payment.setClassId(classId.trim());
            
            LocalDate now = LocalDate.now();
            payment.setMonth(now.getMonthValue());
            payment.setYear(now.getYear());
            payment.setAmount(amount);
            payment.setStatus(status != null ? status.trim() : "Chưa đóng");
            payment.setPaymentDate(paymentDate);
            payment.setDueTo(dueDate);
            payment.setNote(note != null ? note.trim() : "");

            System.out.println("Payment object created: " + payment.toString());

            // Insert payment
            PaymentDAO paymentDAO = new PaymentDAO();
            boolean success = paymentDAO.insertPayment(payment);

            System.out.println("Insert result: " + success);

            if (success) {
                session.setAttribute("toastMessage", "Tạo hóa đơn thành công cho học sinh: " + studentId);
                session.setAttribute("toastType", "success");
            } else {
                session.setAttribute("toastMessage", "Có lỗi xảy ra khi tạo hóa đơn! Vui lòng kiểm tra lại thông tin.");
                session.setAttribute("toastType", "error");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception in payment creation: " + e.getMessage());
            session.setAttribute("toastMessage", "Có lỗi hệ thống xảy ra: " + e.getMessage());
            session.setAttribute("toastType", "error");
        }

        // Redirect để tránh duplicate submission
        response.sendRedirect("notify-payment");
    }

    @Override
    public String getServletInfo() {
        return "Servlet for creating payment notices for students";
    }
}