package Controller.director;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.personnel.Personnel;
import model.personnel.PersonnelDAO;

/**
 * Servlet WaitlistPersonnelServlet xử lý các yêu cầu HTTP liên quan đến việc
 * duyệt hoặc từ chối nhân sự trong danh sách chờ.
 *
 * URL Mapping: /director/waitlistpersonnel
 *
 * Chức năng: - Nhận dữ liệu từ client (JSP/HTML form hoặc URL parameters) - Gọi
 * PersonnelDAO để cập nhật trạng thái nhân sự - Lấy danh sách nhân sự đang chờ
 * phê duyệt từ cơ sở dữ liệu - Chuyển tiếp đến trang waitlistPersonnel.jsp hoặc
 * chuyển hướng sang trang chi tiết nhân sự
 *
 * Phân quyền: Chỉ người dùng có vai trò Giám đốc (Director) mới được phép truy
 * cập
 *
 * @author ThanhNT
 * @version 1.0
 */
public class WaitlistPersonnelServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            PersonnelDAO personnelDAO = new PersonnelDAO();

            // Lấy danh sách nhân viên đang chờ phê duyệt
            List<Personnel> waitlistPersonnel = personnelDAO.getPersonnelByStatus("đang chờ xử lý");

            // Set attribute để JSP có thể sử dụng
            request.setAttribute("waitlistpersonnel", waitlistPersonnel);

            // Forward đến JSP page
            request.getRequestDispatcher("/director/waitlistPersonnel.jsp").forward(request, response);

        } catch (Exception e) {
            // Xử lý lỗi
            request.setAttribute("error", "Có lỗi xảy ra khi tải danh sách: " + e.getMessage());
            request.getRequestDispatcher("/director/waitlistPersonnel.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        session.removeAttribute("message");
        session.removeAttribute("type");

        PersonnelDAO personnelDAO = new PersonnelDAO();
        String action = request.getParameter("action");
        String personId = request.getParameter("id");
        String message = "";
        String type = "";

        try {
            if (action != null) {
                if (action.equals("accept")) {
                    personnelDAO.updatePersonnelStatus(personId, "đang làm việc");
                    message = "Đã duyệt thành công";
                    type = "success";
                } else if (action.equals("decline")) {
                    personnelDAO.updatePersonnelStatus(personId, "không được duyệt");
                    message = "Đã từ chối";
                    type = "fail";
                } else if (action.equals("resign")) {
                    personnelDAO.updatePersonnelStatus(personId, "đã nghỉ việc");
                    message = "Đã cập nhật trạng thái nghỉ việc";
                    type = "success";
                }

            }
            session.setAttribute("message", message);
            session.setAttribute("type", type);
            response.sendRedirect("viewpersonnel?id=" + personId);
        } catch (Exception e) {
            session.setAttribute("error", "An error occurred: " + e.getMessage());

        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet xử lý phê duyệt hoặc từ chối nhân sự trong danh sách chờ.";
    }
}
