package Controller.student;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.application.Application;
import model.application.ApplicationDAO;
import model.user.User;

import java.io.IOException;
import java.util.List;

//this shows sent application from the teacher themselves
/**
 * Servlet SentApplicationsServlet xử lý các yêu cầu HTTP để hiển thị danh sách
 * đơn đã gửi của học sinh.
 *
 * URL Mapping: /student/sentapplications
 *
 * Chức năng: - [GET] Lấy danh sách đơn đã gửi bởi học sinh hiện tại (dựa theo
 * session) - Có thể lọc danh sách đơn theo trạng thái (status: all, đang chờ,
 * đã duyệt, bị từ chối…) - Chuyển tiếp danh sách sang trang JSP để hiển thị
 *
 * Phân quyền: Chỉ học sinh đã đăng nhập mới được phép truy cập
 *
 * @author KienPN
 * @version 1.0
 */
@WebServlet(name = "student/SentApplicationsServlet", value = "/student/sentapplications")
public class SentApplicationsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String status = request.getParameter("status");
        ApplicationDAO applicationDAO = new ApplicationDAO();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        List<Application> sentApplications;
        if (status == null || status.equals("all")) {
            sentApplications = applicationDAO.getSentApplications(user.getId());
        } else {
            sentApplications = applicationDAO.getSentApplicationsWithStatus(user.getId(), status);
        }
        request.setAttribute("sentApplications", sentApplications);
        request.getRequestDispatcher("sentApplications.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
