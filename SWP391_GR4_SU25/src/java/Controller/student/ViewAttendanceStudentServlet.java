/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.student;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import model.day.Day;
import model.day.DayDAO;
import model.schoolYear.SchoolYear;
import model.schoolYear.SchoolYearDAO;
import model.student.Student;
import model.user.User;
import model.week.Week;
import model.week.WeekDAO;

/**
 * Servlet ViewAttendanceStudentServlet xử lý các yêu cầu HTTP để hiển thị bảng
 * điểm danh của học sinh.
 *
 * URL Mapping: /student/attendance
 *
 * Chức năng: - [GET] Lấy thông tin học sinh hiện tại từ session - Truy xuất
 * danh sách năm học, tuần và ngày học tương ứng từ cơ sở dữ liệu - Xác định
 * tuần hiện tại nếu không có tham số tuần được chọn - Gửi dữ liệu điểm danh đến
 * trang viewAttendance.jsp để hiển thị
 *
 * Phân quyền: Chỉ học sinh đã đăng nhập mới được phép truy cập
 *
 * @author KienPN
 * @version 1.0
 */
@WebServlet(name = "ViewAttendanceStudent", urlPatterns = {"/student/attendance"})
public class ViewAttendanceStudentServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        Student student = (Student) request.getSession().getAttribute("student");
        request.setAttribute("studentFullname", student.getFirstName() + " " + student.getLastName());

        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        List<SchoolYear> schoolYearList = schoolYearDAO.getAll();
        request.setAttribute("schoolYears", schoolYearList);
        String schoolyear = request.getParameter("schoolYearId");

        if (schoolyear == null) {
            schoolyear = schoolYearList.get(schoolYearList.size() - 1).getId();
        }
        request.setAttribute("schoolYearId", schoolyear);

        WeekDAO weekDAO = new WeekDAO();
        List<Week> weekList = weekDAO.getWeeks(schoolyear);
        request.setAttribute("weeks", weekList);

        String week = request.getParameter("weekId");
        if (week == null) {
            Date crDate = new Date();
            for (Week week1 : weekList) {
                if (crDate.after(week1.getStartDate()) && crDate.before(week1.getEndDate())) {
                    week = week1.getId();
                    break;
                }
            }
        }

        request.setAttribute("weekId", week);

        DayDAO dayDAO = new DayDAO();
        List<Day> dayList = dayDAO.getDayByWeek(week);
        request.setAttribute("days", dayList);

        request.getRequestDispatcher("viewAttendance.jsp").forward(request, response);

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
