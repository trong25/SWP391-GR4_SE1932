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

/**
 * Lớp UpdatePersonnelServlet dùng để xử lý việc cập nhật thông tin cá nhân của nhân sự trong hệ thống.
 * 
 * Chịu trách nhiệm nhận dữ liệu cập nhật từ form, kiểm tra tính duy nhất của email và số điện thoại,
 * cập nhật thông tin nhân sự và người dùng tương ứng trong cơ sở dữ liệu.
 * 
 * Dựa vào vai trò (roleId) của nhân sự, servlet sẽ chuyển hướng (redirect) đến trang thông tin phù hợp
 * sau khi cập nhật thành công hoặc thất bại.
 * 
 * Được sử dụng trong tầng Controller của mô hình MVC,
 * tương tác với các DAO: PersonnelDAO, UserDAO và StudentDAO để thực hiện nghiệp vụ.
 * 
 * Ví dụ cập nhật: tên, giới tính, địa chỉ, email, số điện thoại nhân sự.
 * 
 * Các roleId:
 * - 0: Admin
 * - 1: Director
 * - 2: Academic Staff
 * - 3: Teacher
 * - 5: Accountant
 * 
 * @author HuyDV
 * @version 1.0
 */



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
