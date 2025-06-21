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


public class WaitlistPersonnelServlet extends HttpServlet {

  @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    PersonnelDAO personnelDAO = new PersonnelDAO();
    String action = request.getParameter("action");
    String personId = request.getParameter("id");
    String message = "";
    String type = "";

    try {
        if (action != null && personId != null) {
            if (action.equals("accept")) {
                personnelDAO.updatePersonnelStatus(personId, "đang làm việc");
                message = "Đã duyệt thành công";
                type = "success";
            } else if (action.equals("decline")) {
                personnelDAO.updatePersonnelStatus(personId, "không được duyệt");
                message = "Đã từ chối";
                type = "fail";
            }
        }

        List<Personnel> waitlist = personnelDAO.getPersonnelByStatus("đang chờ xử lý");
        if (waitlist == null) {
            waitlist = List.of(); // Tránh null, vẫn hiển thị JSP với thông báo không có dữ liệu
        }

        request.setAttribute("message", message);
        request.setAttribute("type", type);
        request.setAttribute("waitlistpersonnel", waitlist);
        request.getRequestDispatcher("waitlistPersonnel.jsp").forward(request, response);

    } catch (Exception e) {
        request.setAttribute("error", "Đã xảy ra lỗi: " + e.getMessage());
        request.getRequestDispatcher("waitlistPersonnel.jsp").forward(request, response);
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