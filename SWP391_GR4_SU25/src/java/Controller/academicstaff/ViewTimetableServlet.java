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
import java.util.List;
import model.classes.ClassDAO;
import model.day.Day;
import model.day.DayDAO;
import model.timeslot.TimeSlot;
import model.timeslot.TimeSlotDAO;
import model.timetable.Timetable;
import model.timetable.TimetableDAO;
import model.week.Week;
import model.week.WeekDAO;

/**
 * Servlet ViewTimetableServlet xử lý các yêu cầu HTTP để hiển thị chi tiết thời khóa biểu của một lớp học theo tuần.
 *
 * URL Mapping: /academicstaff/view-timetable
 *
 * Chức năng:
 * - Nhận classId, weekId, status từ request parameter
 * - Lấy thông tin thời khóa biểu, tiết học, ngày học, lớp, tuần từ CSDL
 * - Chuyển tiếp dữ liệu sang trang viewTimetable.jsp để hiển thị chi tiết
 *
 * Phân quyền: Chỉ nhân viên học vụ đã đăng nhập mới được phép xem chi tiết thời khóa biểu
 *
 * @author KienPN
 */
@WebServlet(name = "ViewTimetableServlet", urlPatterns = {"/academicstaff/view-timetable"})
public class ViewTimetableServlet extends HttpServlet {

    /**
     * Xử lý yêu cầu HTTP GET để hiển thị chi tiết thời khóa biểu của một lớp học theo tuần.
     *
     * Quy trình:
     * - Lấy classId, weekId, status từ request parameter
     * - Lấy thông tin thời khóa biểu, tiết học, ngày học, lớp, tuần từ CSDL
     * - Đặt các thông tin vào attribute và forward sang viewTimetable.jsp
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException nếu có lỗi servlet
     * @throws IOException nếu có lỗi IO
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String classId = request.getParameter("classId");
        String weekId = request.getParameter("weekId");
        String status = request.getParameter("status");
        TimetableDAO timetableDAO = new TimetableDAO();
        DayDAO dayDAO = new DayDAO();
        TimeSlotDAO timeslotDAO = new TimeSlotDAO();
        ClassDAO classDAO = new ClassDAO();
        WeekDAO weekDAO = new WeekDAO();

        Week week = weekDAO.getWeek(weekId);
        List<Timetable> timetable = timetableDAO.getTimetableByClassAndWeek(classId, weekId, status);
        List<TimeSlot> timeslotList = timeslotDAO.getTimeslotsForTimetable();
        List<Day> dayList = dayDAO.getDayByWeek(weekId);
        model.classes.Class aClass = classDAO.getClassById(classId);

        request.setAttribute("week", week);
        request.setAttribute("aClass", aClass);
        request.setAttribute("timetable", timetable);
        request.setAttribute("timeslotList", timeslotList);
        request.setAttribute("dayList", dayList);

        request.getRequestDispatcher("viewTimetable.jsp").forward(request, response);
    }

    /**
     * Xử lý yêu cầu HTTP POST (không sử dụng trong chức năng này).
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException nếu có lỗi servlet
     * @throws IOException nếu có lỗi IO
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    /**
     * Trả về mô tả ngắn gọn về servlet.
     *
     * @return Chuỗi mô tả servlet
     */
    @Override
    public String getServletInfo() {
        return "Servlet hiển thị chi tiết thời khóa biểu của một lớp học theo tuần.";
    }

}
