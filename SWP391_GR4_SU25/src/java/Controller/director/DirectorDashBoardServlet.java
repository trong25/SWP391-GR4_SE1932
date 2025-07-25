package Controller.director;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import model.classes.Class;
import model.classes.ClassDAO;
import model.payment.PaymentDAO;
import model.personnel.Personnel;
import model.personnel.PersonnelDAO;
import model.schoolYear.SchoolYear;
import model.schoolYear.SchoolYearDAO;
import model.student.StudentDAO;
import model.subject.SubjectDAO;

/**
 * Servlet DirectorDashBoardServlet xử lý hiển thị trang dashboard dành cho Giám
 * đốc.
 *
 * 📌 URL Mapping: /director/dashboard
 *
 * Chức năng: - Phương thức GET: + Lấy số lượng học sinh đang theo học (status =
 * "đang theo học"). + Lấy danh sách tất cả lớp học và các lớp đang chờ phê
 * duyệt theo năm học đầu tiên. + Lấy danh sách nhân sự đang chờ phê duyệt
 * (status = "đang chờ xử lý"). + Lấy số lượng học sinh đang chờ duyệt (status =
 * "đang chờ xử lý"). + Gửi các dữ liệu trên sang `dashboard.jsp` để hiển thị.
 *
 * - Phương thức POST: + Chuyển tiếp sang GET như một fallback.
 *
 * Phân quyền: - Chỉ tài khoản có vai trò "Giám đốc" mới được truy cập servlet
 * này.
 *
 * Dữ liệu gửi sang view: - `numberOfStudent`: Số lượng học sinh đang theo học.
 * - `listClass`: Danh sách tất cả lớp học trong hệ thống. - `pendingClasses`:
 * Danh sách lớp học có trạng thái "đang chờ xử lý". - `waitlistpersonnel`: Danh
 * sách nhân sự đang chờ phê duyệt. - `listStudent`: Số học sinh có trạng thái
 * "đang chờ xử lý".
 *
 *
 * @author ThanhNT
 * @version 5.0
 */
public class DirectorDashBoardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy dữ liệu từ DAO
        StudentDAO studentDAO = new StudentDAO();
        ClassDAO classDAO = new ClassDAO();
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        PersonnelDAO personnelDAO = new PersonnelDAO();
        SubjectDAO subjectDAO = new SubjectDAO();
        PaymentDAO paymentDAO = new PaymentDAO();

        String schoolYearId = null;
        String yearParam = request.getParameter("year");
        int year = (yearParam != null) ? Integer.parseInt(yearParam) : 2024;
        Map<Integer, Double> monthlyRevenue = paymentDAO.getMonthlyRevenue(year);

        // Lấy thông tin năm và tháng hiện tại
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
         int currentYear = calendar.get(Calendar.YEAR);// Calendar.MONTH bắt đầu từ 0
        Date currentDate = new Date();

        // Lấy doanh thu tháng hiện tại
        double currentMonthRevenue = paymentDAO.getCurrentMonthRevenue();
       double currentYearRevenue = paymentDAO.getRevenueByYear(currentYear);
        List<SchoolYear> schoolYears = schoolYearDAO.getAll();
        if (!schoolYears.isEmpty()) {
            schoolYearId = schoolYears.get(0).getId(); // lấy năm học đầu tiên
        }

        List<Class> listClass = classDAO.getAll();
        List<Class> pendingClasses = new ArrayList<>();
        if (schoolYearId != null) {
            pendingClasses = classDAO.getByStatus("đang chờ xử lý", schoolYearId);
        }

        // NEW: Lấy danh sách nhân sự đang chờ xử lý
        List<Personnel> waitlistPersonnel = personnelDAO.getPersonnelByStatus("đang chờ xử lý");
        //gửi dữ liệu doanh thu 
        request.setAttribute("currentDate", currentDate);
        request.setAttribute("currentMonthRevenue", currentMonthRevenue);
        request.setAttribute("currentYearRevenue", currentYearRevenue);
        request.setAttribute("monthlyRevenue", monthlyRevenue);
        request.setAttribute("selectedYear", year);
        request.setAttribute("currentYear", currentYear);
        request.setAttribute("currentMonth", currentMonth);
//thông tin nhân như và học sinh
        request.setAttribute("listSubjectPending", subjectDAO.getSubjectsByStatus("đang chờ xử lý"));
        request.setAttribute("pendingClasses", pendingClasses);
        request.setAttribute("listClass", listClass);
        request.setAttribute("numberOfStudent", studentDAO.getStudentByStatus("đang theo học").size());
        request.setAttribute("waitlistpersonnel", waitlistPersonnel);
        request.setAttribute("listStudent", studentDAO.getStudentByStatus("đang chờ xử lý").size());
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Không sử dụng phương thức này trong thực tế, chỉ để POST fallback
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Director Dashboard - Quản lý thống kê học sinh, lớp và nhân sự chờ duyệt";
    }
}
