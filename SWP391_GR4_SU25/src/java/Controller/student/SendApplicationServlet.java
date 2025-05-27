package Controller.student;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.application.Application;
import model.application.ApplicationDAO;
import model.application.ApplicationType;
import model.user.User;
import utils.Helper;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "student/SendApplicationServlet", value = "/student/sendapplication")
public class SendApplicationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //getting toast message sent from do post after send application (if exist)
        HttpSession session = request.getSession();
        String toastType = "", toastMessage = "";
        if (session.getAttribute("toastType") != null) {
            toastType = session.getAttribute("toastType").toString();
            toastMessage = session.getAttribute("toastMessage").toString();
        }
        session.removeAttribute("toastType");
        session.removeAttribute("toastMessage");
        request.setAttribute("toastType", toastType);
        request.setAttribute("toastMessage", toastMessage);
        ApplicationDAO applicationDAO = new ApplicationDAO();
        List<ApplicationType> applicationTypes = applicationDAO.getAllApplicationTypes("student");
        request.setAttribute("applicationTypes", applicationTypes);
        request.getRequestDispatcher("sendApplication.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        String details = Helper.formatString(request.getParameter("details"));
        String startDateRaw = request.getParameter("startDate");
        String endDateRaw = request.getParameter("endDate");
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = dateFormat.parse(startDateRaw);
            Date endDate = dateFormat.parse(endDateRaw);
            ApplicationDAO applicationDAO = new ApplicationDAO();
            Application application = new Application();
            application.setType(applicationDAO.getById(type));
            application.setDetails(details);
            application.setStatus("đang chờ xử lý");
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            application.setCreatedBy(user.getId());
            application.setCreatedAt(new Date());
            application.setStartDate(startDate);
            application.setEndDate(endDate);

            //getting result for toast message
            String result = applicationDAO.addApplication(application);
            if (result.equals("success")) {
                session.setAttribute("toastType", "success");
                session.setAttribute("toastMessage", "Gửi đơn thành công");
            } else {
                session.setAttribute("toastType", "error");
                session.setAttribute("toastMessage", result);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


        response.sendRedirect("sendapplication");
    }
}