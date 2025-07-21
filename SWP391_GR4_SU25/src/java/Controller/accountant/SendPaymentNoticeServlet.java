package Controller.accountant;

import dao.students.StudentAttendanceSummaryDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import model.payment.Payment;
import model.payment.PaymentDAO;
import model.student.StudentAttendanceSummary;

/**
 * Servlet xử lý tạo thông báo thanh toán học phí
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

//        request.setCharacterEncoding("UTF-8");
//        HttpSession session = request.getSession();
//
//        try {
//            // Lấy parameters từ form
//            String code = request.getParameter("code");
//            String studentId = request.getParameter("student_id"); // Sửa tên parameter cho đúng
//            String classId = request.getParameter("class_id");     // Sửa tên parameter cho đúng
//            String dayId = request.getParameter("day_id");
//            String amountStr = request.getParameter("amount");
//            String status = request.getParameter("status");
//            String paymentDateStr = request.getParameter("paymentDate");
//            String dueDateStr = request.getParameter("dueDate");
//            String note = request.getParameter("note");
//
//            // Validate required fields
//            if (code == null || code.trim().isEmpty() || 
//                studentId == null || studentId.trim().isEmpty() ||
//                classId == null || classId.trim().isEmpty() ||
//                amountStr == null || amountStr.trim().isEmpty() ||
//                dueDateStr == null || dueDateStr.trim().isEmpty()) {
//                
//                session.setAttribute("toastMessage", "Vui lòng điền đầy đủ thông tin bắt buộc!");
//                session.setAttribute("toastType", "error");
//                response.sendRedirect("notify-payment");
//                return;
//            }
//
//            // Parse amount
//            float amount;
//            try {
//                amount = Float.parseFloat(amountStr);
//                if (amount <= 0) {
//                    throw new NumberFormatException("Amount must be positive");
//                }
//            } catch (NumberFormatException e) {
//                session.setAttribute("toastMessage", "Số tiền không hợp lệ!");
//                session.setAttribute("toastType", "error");
//                response.sendRedirect("notify-payment");
//                return;
//            }
//
//            // Parse dates
//            Date paymentDate = null;
//            Date dueDate;
//            try {
//                if (paymentDateStr != null && !paymentDateStr.trim().isEmpty()) {
//                    paymentDate = java.sql.Date.valueOf(paymentDateStr);
//                }
//                dueDate = java.sql.Date.valueOf(dueDateStr);
//                
//                // Validate due date is not in the past
//                Date today = new Date();
//                if (dueDate.before(today)) {
//                    session.setAttribute("toastMessage", "Hạn đóng tiền không thể là ngày trong quá khứ!");
//                    session.setAttribute("toastType", "error");
//                    response.sendRedirect("notify-payment");
//                    return;
//                }
//                
//            } catch (IllegalArgumentException e) {
//                session.setAttribute("toastMessage", "Định dạng ngày không hợp lệ!");
//                session.setAttribute("toastType", "error");
//                response.sendRedirect("notify-payment");
//                return;
//            }
//
//            // Tạo Payment object
//            Payment payment = new Payment();
//            payment.setCode(code);
//            payment.setStudentId(studentId);
//            payment.setClassId(classId);
//            payment.setDayId(dayId);
//            payment.setAmount((int) amount);
//            payment.setStatus(status != null ? status : "Chưa đóng");
//            payment.setPaymentDate(paymentDate);
//            payment.setDueDate(dueDate);
//            payment.setNote(note);
//
//            // Insert payment
//            PaymentDAO paymentDAO = new PaymentDAO();
//            boolean success = paymentDAO.insertPayment(payment);
//
//            if (success) {
//                session.setAttribute("toastMessage", "Tạo hóa đơn thành công!");
//                session.setAttribute("toastType", "success");
//            } else {
//                session.setAttribute("toastMessage", "Có lỗi xảy ra khi tạo hóa đơn!");
//                session.setAttribute("toastType", "error");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            session.setAttribute("toastMessage", "Có lỗi hệ thống xảy ra!");
//            session.setAttribute("toastType", "error");
//        }
//
//        // Redirect để tránh duplicate submission
//        response.sendRedirect("notify-payment");
    }

    @Override
    public String getServletInfo() {
        return "Servlet for creating payment notices for students";
    }
}