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
        String studentId = request.getParameter("student_id");
        String classId = request.getParameter("class_id");
        int month = Integer.parseInt(request.getParameter("month"));
        int year = Integer.parseInt(request.getParameter("year"));
        float amount = Float.parseFloat(request.getParameter("amount"));
        String status = request.getParameter("status");
        String note = request.getParameter("note");

        Date paymentDate = null;

        Payment payment = new Payment();
        payment.setCode(code);
        payment.setStudentId(studentId);
        payment.setClassId(classId);
        payment.setMonth(month);
        payment.setYear(year);
        payment.setAmount(amount);
        payment.setStatus(status);
        payment.setNote(note);
        payment.setPaymentDate(paymentDate);

        PaymentDAO dao = new PaymentDAO();
        boolean insertSuccess = false;

        try {
            // Giả sử bạn có hàm insert:
            insertSuccess = dao.insert(payment); // Thực hiện insert thực sự
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!insertSuccess) {
            request.setAttribute("openModal", "create-pupil");
            request.setAttribute("error", "Thêm không thành công");
            request.getRequestDispatcher("sendPaymentNotice.jsp").forward(request, response);
        } else {
            request.setAttribute("success", "Thêm hóa đơn thành công!");
            request.getRequestDispatcher("sendPaymentNotice.jsp").forward(request, response);

        }
    }

  
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
