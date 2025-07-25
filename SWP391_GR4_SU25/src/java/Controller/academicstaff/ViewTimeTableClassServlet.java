/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.academicstaff;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.classes.ClassDAO;
import model.day.Day;
import model.day.DayDAO;
import model.schoolYear.SchoolYear;
import model.schoolYear.SchoolYearDAO;
import model.timeslot.TimeSlot;
import model.timeslot.TimeSlotDAO;
import model.timetable.Timetable;
import model.timetable.TimetableDAO;
import model.week.Week;
import model.week.WeekDAO;

/**
 * Servlet ViewTimeTableClassServlet xử lý các yêu cầu HTTP để hiển thị thời khóa biểu của các lớp học theo năm học và tuần.
 *
 * URL Mapping: /academicstaff/viewtimetableclass
 *
 * Chức năng:
 * - Hiển thị form chọn năm học, tuần, lớp (GET)
 * - Nhận dữ liệu lọc và hiển thị thời khóa biểu của lớp theo tuần (POST)
 * - Thông báo lỗi nếu không có lớp học hoặc dữ liệu không hợp lệ
 *
 * Phân quyền: Chỉ nhân viên học vụ đã đăng nhập mới được phép xem thời khóa biểu lớp
 *
 * @author KienPN
 */
@WebServlet(name = "ViewTimeTableClassStaffServlet", urlPatterns = {"/academicstaff/viewtimetableclass"})
public class ViewTimeTableClassServlet extends HttpServlet {

    /**
     * Xử lý yêu cầu HTTP GET để hiển thị form chọn năm học, tuần, lớp.
     *
     * Quy trình:
     * - Lấy danh sách năm học từ CSDL
     * - Đặt vào attribute và forward sang viewTimetableClass.jsp
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException nếu có lỗi servlet
     * @throws IOException nếu có lỗi IO
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String toastMessage = (String) session.getAttribute("toastMessage");
        String toastType = (String) session.getAttribute("toastType");
        if (toastMessage != null && toastType != null) {
            request.setAttribute("toastMessage", toastMessage);
            request.setAttribute("toastType", toastType);
            session.removeAttribute("toastMessage");
            session.removeAttribute("toastType");
        }
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        List<SchoolYear> schoolYearList = schoolYearDAO.getAll();
        request.setAttribute("schoolYearList", schoolYearList);
        request.getRequestDispatcher("viewTimetableClass.jsp").forward(request, response);
    }

    /**
     * Xử lý yêu cầu HTTP POST để lọc và hiển thị thời khóa biểu lớp theo tuần.
     *
     * Quy trình:
     * - Lấy classId, week, schoolyear từ request
     * - Lấy danh sách lớp, tuần, năm học, tiết, ngày từ CSDL
     * - Lấy thời khóa biểu của lớp theo tuần
     * - Đặt các thông tin vào attribute và forward sang viewTimetableClass.jsp
     * - Nếu không có lớp học, thông báo lỗi qua session
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException nếu có lỗi servlet
     * @throws IOException nếu có lỗi IO
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String classId = request.getParameter("class");
        WeekDAO weekDAO = new WeekDAO();
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        model.classes.Class aclass = new ClassDAO().getClassById(classId);
        String week = request.getParameter("week");
        String schoolyear = request.getParameter("schoolyear");
        List<model.classes.Class> listClass = new ClassDAO().getBySchoolYearandStatus(schoolyear);
        if (listClass.isEmpty()) {
            HttpSession session = request.getSession();
            session.setAttribute("toastType", "error");
            session.setAttribute("toastMessage", "Năm này không có lớp học");
            response.sendRedirect("viewtimetableclass");
            return;
        }
        List<SchoolYear> schoolYearList = schoolYearDAO.getAll();
        List<Week> weekList = weekDAO.getWeeks(schoolyear);
        List<Timetable> timetable = new ArrayList<>();
        TimeSlotDAO timeslotDAO = new TimeSlotDAO();
        List<TimeSlot> timeslotList = timeslotDAO.getTimeslotsForTimetable();
        DayDAO dayDAO = new DayDAO();
        List<Day> dayList = dayDAO.getDayByWeek(week);
        if (dayList.size() > 0) {
            request.setAttribute("timeslotList", timeslotList);
        }
        if (classId != null && week != null && schoolyear != null) {
            timetable = new TimetableDAO().getTimetableByClassAndWeek(classId, week, "đã được duyệt");
        }
        request.setAttribute("listClass", listClass);
        request.setAttribute("timetable", timetable);
        request.setAttribute("classselect", classId);
        request.setAttribute("sltedsy", schoolyear);
        request.setAttribute("sltedw", week);
        request.setAttribute("schoolYearList", schoolYearList);
        request.setAttribute("weekList", weekList);
        request.setAttribute("dayList", dayList);
        request.setAttribute("aClass", aclass);
        request.getRequestDispatcher("viewTimetableClass.jsp").forward(request, response);
    }

    /**
     * Trả về mô tả ngắn gọn về servlet.
     *
     * @return Chuỗi mô tả servlet
     */
    @Override
    public String getServletInfo() {
        return "Servlet hiển thị thời khóa biểu của các lớp học theo năm học và tuần.";
    }

}
