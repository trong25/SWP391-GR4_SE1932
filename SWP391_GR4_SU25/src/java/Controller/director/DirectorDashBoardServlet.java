package Controller.director;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.classes.Class;
import model.classes.ClassDAO;
import model.personnel.Personnel;
import model.personnel.PersonnelDAO;
import model.schoolYear.SchoolYear;
import model.schoolYear.SchoolYearDAO;
import model.student.StudentDAO;

/**
 * Servlet DirectorDashBoardServlet xử lý hiển thị dashboard dành cho Giám đốc.
 * 
 * URL Mapping: /director/dashboard
 * 
 * Chức năng:
 * - Lấy số lượng học sinh đang theo học
 * - Lấy danh sách lớp đang chờ phê duyệt
 * - Lấy danh sách nhân sự đang chờ phê duyệt
 */
public class DirectorDashBoardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        StudentDAO studentDAO = new StudentDAO();
        ClassDAO classDAO = new ClassDAO();
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        PersonnelDAO personnelDAO = new PersonnelDAO();

        String schoolYearId = null;
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

        // Gửi dữ liệu sang dashboard.jsp
        request.setAttribute("pendingClasses", pendingClasses);
        request.setAttribute("listClass", listClass);
        request.setAttribute("numberOfStudent", studentDAO.getStudentByStatus("đang theo học").size());
        request.setAttribute("waitlistpersonnel", waitlistPersonnel);

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
