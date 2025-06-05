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
import model.personnel.Personnel;
import model.personnel.PersonnelDAO;
import model.student.Student;

@WebServlet(name = "student/SendApplicationServlet", value = "/student/sendapplication")
public class SendApplicationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Hiển thị form gửi đơn và hiển thị thông báo nếu có
        HttpSession session = request.getSession();
        String toastType = "", toastMessage = "";
        if (session.getAttribute("toastType") != null) { //Lấy thông báo từ session
            toastType = session.getAttribute("toastType").toString();
            toastMessage = session.getAttribute("toastMessage").toString();
        }
        session.removeAttribute("toastType");
        session.removeAttribute("toastMessage");
        request.setAttribute("toastType", toastType);
        request.setAttribute("toastMessage", toastMessage);
        
        //Lấy danh sách loại đơn của học sinh
        ApplicationDAO applicationDAO = new ApplicationDAO();
        PersonnelDAO personnelDAO = new PersonnelDAO();
        List<ApplicationType> applicationTypes = applicationDAO.getAllApplicationTypes("pupil");
        Student student = (Student)request.getSession().getAttribute("student");
        List<Personnel> personnels = personnelDAO.getByStudentId(student.getId());
        request.setAttribute("applicationTypes", applicationTypes);
        request.setAttribute("personnels", personnels);
        
        //Chuyển tiếp sang JSP
        request.getRequestDispatcher("sendApplication.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Xử lý việc gửi đơn (lưu vào CSDL), sau đó chuyển hướng lại về GET để hiển thị lại trang với thông báo tương ứng.
        String type = request.getParameter("type");
        String details = Helper.formatString(request.getParameter("details"));
        String startDateRaw = request.getParameter("startDate");
        String endDateRaw = request.getParameter("endDate");
        String processedBy = request.getParameter("teacher_id");
        try{
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = dateFormat.parse(startDateRaw);
            Date endDate = dateFormat.parse(endDateRaw);
            
            //Tạo đối tượng Application
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
            application.setId("APP"+new Date().getTime());
            Personnel personnel = new Personnel();
            personnel.setId(processedBy);
            application.setProcessedBy(personnel);
            //DAO để lưu dữ liệu
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