package controllerr;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import model.personnel.Personnel;
import model.personnel.PersonnelDAO;
import model.student.StudentDAO;
import model.user.User;
import model.user.UserDAO;

public class UpdatePersonnelServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet UpdatePersonnelServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdatePersonnelServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PersonnelDAO personnelDAO = new PersonnelDAO();
        UserDAO userDAO = new UserDAO();
        StudentDAO studentDAO = new StudentDAO();

        HttpSession session = request.getSession();
        Personnel person = (Personnel) session.getAttribute("personnel");
        User user = (User) session.getAttribute("user");

        String firstName = request.getParameter("first_name").trim();
        String lastName = request.getParameter("last_name").trim();
        String genderStr = request.getParameter("gender").trim();
        String address = request.getParameter("address").trim();
        String email = request.getParameter("email").trim();
        String phoneNumber = request.getParameter("phone_number").trim();
   // Các trường riêng cho giáo viên (roleId == 3)
        String specialization = request.getParameter("specialization");
        specialization = (specialization != null) ? specialization.trim() : null;

        String qualification = request.getParameter("qualification");
        qualification = (qualification != null) ? qualification.trim() : null;

        String achievements = request.getParameter("achievements");
        achievements = (achievements != null) ? achievements.trim() : null;

        String teachingYearsStr = request.getParameter("teaching_years");
        int teachingYears = 0;
        if (teachingYearsStr != null && !teachingYearsStr.trim().isEmpty()) {
            try {
                teachingYears = Integer.parseInt(teachingYearsStr.trim());
            } catch (NumberFormatException e) {
                teachingYears = 0;
            }
        }
        boolean emailExists = userDAO.checkEmailExists(email) && !email.equals(user.getEmail());
        boolean phoneNumberExists = (personnelDAO.checkPhoneNumberExists(phoneNumber)
                || studentDAO.checkFirstGuardianPhoneNumberExists(phoneNumber)
                || studentDAO.checkSecondGuardianPhoneNumberExists(phoneNumber))
                && !phoneNumber.equals(person.getPhoneNumber());

        if (emailExists && phoneNumberExists) {
            session.setAttribute("toastType", "error");
            session.setAttribute("toastMessage", "Email và số điện thoại đã tồn tại.");
        } else if (emailExists) {
            session.setAttribute("toastType", "error");
            session.setAttribute("toastMessage", "Email đã tồn tại.");
        } else if (phoneNumberExists) {
            session.setAttribute("toastType", "error");
            session.setAttribute("toastMessage", "Số điện thoại đã tồn tại.");
        } else {
            person.setFirstName(firstName);
            person.setLastName(lastName);
            boolean gender = Boolean.parseBoolean(genderStr);
            person.setGender(gender);
            person.setAddress(address);
            person.setEmail(email);
            person.setPhoneNumber(phoneNumber);
           
                person.setSpecialization(specialization);
                person.setQualification(qualification);
                person.setTeaching_years(teachingYears);
                person.setAchievements(achievements);
            
            user.setEmail(email);
            boolean successUser = userDAO.updateUserById(user);
            boolean successPerson = personnelDAO.updatePerson(person);
            if (successPerson && successUser) {
                session.setAttribute("toastType", "success");
                session.setAttribute("toastMessage", "Đã cập nhật thành công !");
            } else {
                session.setAttribute("toastType", "error");
                session.setAttribute("toastMessage", "Đã cập nhật thất bại !");
            }
        }

        switch (person.getRoleId()) {
            case 0:
                response.sendRedirect("admin/information");
                break;
            case 1:
                response.sendRedirect("director/information");
                break;
            case 2:
                response.sendRedirect("academicstaff/information");
                break;
            case 3:
                response.sendRedirect("teacher/information");
                break;
            case 5:
                response.sendRedirect("accountant/information");
                break;

            default:
                throw new AssertionError();
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
