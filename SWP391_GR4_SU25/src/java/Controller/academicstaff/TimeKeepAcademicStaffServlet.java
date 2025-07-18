/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.academicstaff;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.day.Day;
import model.day.DayDAO;
import model.personnel.Personnel;
import model.personnel.PersonnelDAO;
import model.schoolYear.SchoolYearDAO;
import model.user.User;
import model.week.WeekDAO;
/**
 * Lớp TimeKeepAcademicStaffServlet dùng để xử lý các yêu cầu HTTP GET liên quan đến việc hiển thị bảng chấm công của nhân sự theo tuần và năm học.
 * 
 * Servlet này chịu trách nhiệm:
 * - Lấy danh sách tất cả các năm học từ cơ sở dữ liệu để hiển thị trong danh sách chọn.
 * - Lấy danh sách các tuần tương ứng với năm học được chọn.
 * - Xác định tuần được chọn hiện tại (nếu không chọn thì để rỗng).
 * - Lấy thông tin người dùng hiện tại từ session và truy xuất dữ liệu nhân sự tương ứng.
 * - Truy xuất danh sách các ngày trong tuần được chọn từ bảng `Day`.
 * - Gửi toàn bộ dữ liệu cần thiết (năm học, tuần học, ngày làm việc, thông tin nhân sự) sang trang JSP để hiển thị.
 * 
 * Servlet này được sử dụng trong tầng điều khiển (Controller) của mô hình kiến trúc MVC,
 * đóng vai trò trung gian giữa tầng dữ liệu (DAO) và giao diện người dùng (JSP).
 * 
 * Ví dụ: Khi một nhân sự truy cập trang "Tình hình chấm công", servlet này sẽ tải thông tin năm học, tuần hiện tại, 
 * tên nhân sự và trạng thái chấm công từng ngày trong tuần đó, sau đó chuyển đến trang `viewTimeKeep.jsp` để hiển thị.
 * 
 * Giao diện phía người dùng có thể chọn lại năm học hoặc tuần để xem bảng chấm công tương ứng.
 * 
 * @author HuyDV
 * @version 1.0
 */
@WebServlet(name="TimeKeepAcademicStaffServlet", urlPatterns={"/academicstaff/mytimekeeping"})
public class TimeKeepAcademicStaffServlet extends HttpServlet {
   
   @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        //list of school years
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        request.setAttribute("schoolYears", schoolYearDAO.getAll());

        //get school year id from select box
        String schoolYearId = request.getParameter("schoolYearId");
        if (schoolYearId == null){
            if (schoolYearDAO.getLatest()!=null){
                schoolYearId = schoolYearDAO.getLatest().getId();
            }
        }
        if (schoolYearId != null) {
            //get list of weeks for select box
            WeekDAO weekDAO = new WeekDAO();
            request.setAttribute("weeks", weekDAO.getWeeks(schoolYearId));
            request.setAttribute("schoolYearId", schoolYearId);

            String weekId = request.getParameter("weekId");
            request.setAttribute("weekId", weekId);
            DayDAO dayDAO = new DayDAO();
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            request.setAttribute("personnelId", user.getUsername());
            PersonnelDAO personnelDAO = new PersonnelDAO();
            Personnel personnel = personnelDAO.getPersonnels(user.getUsername());
            request.setAttribute("personnelFullName", personnel.getLastName() + " " + personnel.getFirstName());
            System.out.println(weekId);
            List<Day> days = dayDAO.getDayByWeek(weekId);
            request.setAttribute("days", days);
        }

        //direct to jsp
        request.getRequestDispatcher("viewTimeKeep.jsp").forward(request, response);

    }
    

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
     
    }


}