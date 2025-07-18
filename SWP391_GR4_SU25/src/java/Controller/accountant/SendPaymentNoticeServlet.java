package Controller.accountant;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import model.payment.Payment;
import model.payment.PaymentDAO;
import model.student.StudentDAO;
import model.student.StudentWithClassDTO;

/**
 *
 * @author admin
 */
public class SendPaymentNoticeServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet SendPaymentNoticeServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet SendPaymentNoticeServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        StudentDAO studentDAO = new StudentDAO();
        List<StudentWithClassDTO> listStudent = studentDAO.getStudentsWithClassInfo();
        request.setAttribute("listStudent", listStudent);

        String studentId = request.getParameter("studentId");
        if (studentId != null && !studentId.trim().isEmpty()) {
            StudentWithClassDTO student = studentDAO.getStudentWithClassById(studentId).stream().findFirst().orElse(null);
            if (student != null) {
                request.setAttribute("selectedStudent", student);  // đúng tên theo JSP
                request.setAttribute("openModal", true);
            }
        }

        request.getRequestDispatcher("sendPaymentNotice.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String code = request.getParameter("code");
        String studentId = request.getParameter("studentId");
        String classId = request.getParameter("classId");
        String dayId = request.getParameter("dayId");
        float amount = Float.parseFloat(request.getParameter("amount"));
        String status = request.getParameter("status");
        String paymentDateStr = request.getParameter("paymentDate");
        String dueDateStr = request.getParameter("dueDate");
        String note = request.getParameter("note");

        Date paymentDate = null;
        Date dueDate = null;
        try {
            if (paymentDateStr != null && !paymentDateStr.isEmpty()) {
                paymentDate = java.sql.Date.valueOf(paymentDateStr);
            }
            if (dueDateStr != null && !dueDateStr.isEmpty()) {
                dueDate = java.sql.Date.valueOf(dueDateStr);
            }
        } catch (IllegalArgumentException e) {
            // xử lý lỗi định dạng ngày
        }

        Payment payment = new Payment();
        payment.setCode(code);
        payment.setStudentId(studentId);
        payment.setClassId(classId);
        payment.setDayId(dayId);
        payment.setAmount((int) amount);
        payment.setStatus(status);
        payment.setPaymentDate(paymentDate);
        payment.setDueDate(dueDate);
        payment.setNote(note);

        PaymentDAO dao = new PaymentDAO();
        dao.insertPayment(payment);

        request.getRequestDispatcher("sendPaymentNotice.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
