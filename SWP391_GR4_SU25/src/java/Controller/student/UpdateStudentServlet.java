/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.student;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.personnel.PersonnelDAO;
import model.student.Student;
import model.student.StudentDAO;
import model.user.User;
import model.user.UserDAO;

/**
 *
 * @author ASUS VIVOBOOK
 */
public class UpdateStudentServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      
    } 

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
     
     PersonnelDAO personnelDAO = new PersonnelDAO();
        UserDAO userDAO = new UserDAO();
        StudentDAO studentDAO = new StudentDAO();
        // Lấy thông tin student từ session
        HttpSession session = request.getSession();
        Student student =  (Student) session.getAttribute("student");
        User user = (User) session.getAttribute("user");

        // Lấy thông tin cần update từ request
        String firstGuardianName = request.getParameter("first_guardian_name").trim();
        String firstGuardianPhoneNumber = request.getParameter("firstGuardianPhoneNumber").trim();
        String secondGuardianName = request.getParameter("second_guardian_name").trim();
        String secondGuardianPhoneNumber = request.getParameter("secondGuardianPhoneNumber").trim();
        String email = request.getParameter("email").trim();
        String address = request.getParameter("address").trim();
        String note = request.getParameter("note").trim();

        // Kiểm tra tính duy nhất của email và số điện thoại
        boolean emailExists = userDAO.checkEmailExists(email) && !email.equals(user.getEmail());
        boolean phoneNumberMotherExists = (personnelDAO.checkPhoneNumberExists(firstGuardianPhoneNumber) || studentDAO.checkFirstGuardianPhoneNumberExists(firstGuardianPhoneNumber) || studentDAO.checkSecondGuardianPhoneNumberExists(firstGuardianPhoneNumber))
                && (!firstGuardianPhoneNumber.equals(student.getFirstGuardianPhoneNumber()));
        boolean phoneNumberSecondGuardianExists = (personnelDAO.checkPhoneNumberExists(secondGuardianPhoneNumber) || studentDAO.checkFirstGuardianPhoneNumberExists(secondGuardianPhoneNumber) || studentDAO.checkSecondGuardianPhoneNumberExists(firstGuardianPhoneNumber))
                && (!secondGuardianPhoneNumber.equals(student.getSecondGuardianPhoneNumber()));
        if (emailExists && phoneNumberMotherExists && phoneNumberSecondGuardianExists) {
            request.setAttribute("toastType", "error");
            request.setAttribute("toastMessage", "Email và số điện thoại của cả bố và mẹ đã tồn tại.");
        } else if (emailExists && phoneNumberMotherExists) {
            request.setAttribute("toastType", "error");
            request.setAttribute("toastMessage", "Email và số điện thoại của bố.");
        } else if (emailExists && phoneNumberSecondGuardianExists) {
            request.setAttribute("toastType", "error");
            request.setAttribute("toastMessage", "Email và số điện thoại của mẹ đã tồn tại.");
        } else if (emailExists) {
            request.setAttribute("toastType", "error");
            request.setAttribute("toastMessage", "Email đã tồn tại.");
        } else if (phoneNumberMotherExists && phoneNumberSecondGuardianExists) {
            request.setAttribute("toastType", "error");
            request.setAttribute("toastMessage", "Số điện thoại của cả bố và mẹ đã tồn tại.");
        } else if (phoneNumberMotherExists) {
            request.setAttribute("toastType", "error");
            request.setAttribute("toastMessage", "Số điện thoại của bố đã tồn tại.");
        } else if (phoneNumberSecondGuardianExists) {
            request.setAttribute("toastType", "error");
            request.setAttribute("toastMessage", "Số điện thoại của mẹ đã tồn tại.");
        } else if(firstGuardianPhoneNumber.equals(secondGuardianPhoneNumber)){
            request.setAttribute("toastType", "error");
            request.setAttribute("toastMessage", "Số điện thoại của bố và mẹ không được trùng nhau");
        }
        else {

            // Cập nhật thông tin của student
            student.setFirstGuardianName(firstGuardianName);
            student.setFirstGuardianPhoneNumber(firstGuardianPhoneNumber);
            student.setSecondGuardianName(secondGuardianName);
            student.setSecondGuardianPhoneNumber(secondGuardianPhoneNumber);
            student.setEmail(email);
            student.setAddress(address);
            student.setParentSpecialNote(note);
            user.setEmail(email);
            // Cập nhật thông tin của student trong cơ sở dữ liệu
            boolean successUser = userDAO.updateUserById(user);
            boolean success = studentDAO.updateStudent(student);

            if (success && successUser) {
                request.setAttribute("toastType", "success");
                request.setAttribute("toastMessage", "Đã cập nhật thành công !");
            } else {
                request.setAttribute("toastType", "error");
                request.setAttribute("toastMessage", "Đã cập nhật thất bại !");
            }
        }
        request.getRequestDispatcher("informationStudent.jsp").forward(request, response);
    }


}