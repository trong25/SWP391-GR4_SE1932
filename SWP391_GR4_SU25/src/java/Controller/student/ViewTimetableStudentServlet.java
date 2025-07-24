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
import model.classes.ClassDAO;
import model.day.Day;
import model.day.DayDAO;
import model.school.SchoolDAO;
import model.schoolYear.SchoolYear;
import model.schoolYear.SchoolYearDAO;
import model.schoolclass.SchoolClass;
import model.schoolclass.SchoolClassDAO;
import model.student.Student;
import model.timeslot.TimeSlot;
import model.timeslot.TimeSlotDAO;
import model.timetable.Timetable;
import model.timetable.TimetableDAO;
import model.user.User;
import model.week.Week;
import model.week.WeekDAO;

/**
 * Servlet ViewTimetableStudentServlet xử lý các yêu cầu HTTP để hiển thị thời
 * khóa biểu của học sinh.
 *
 * URL Mapping: /student/view-timetable
 *
 * Chức năng: - [GET] Lấy thông tin năm học, tuần, ngày học, khung giờ và lớp
 * học của học sinh - Nếu không có tuần được chọn, tự động xác định tuần hiện
 * tại dựa trên ngày hiện tại - Truy xuất dữ liệu thời khóa biểu từ cơ sở dữ
 * liệu theo học sinh và tuần đã chọn - Chuyển tiếp dữ liệu sang
 * viewTimetable.jsp để hiển thị thời khóa biểu
 *
 * Phân quyền: Chỉ học sinh đã đăng nhập mới được phép truy cập chức năng này
 *
 * @author KienPN
 * @version 1.0
 */
@WebServlet(name = "ViewTimetable", urlPatterns = {"/student/view-timetable"})
public class ViewTimetableStudentServlet extends HttpServlet {

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

        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        List<SchoolYear> schoolYearList = schoolYearDAO.getAll();
        request.setAttribute("schoolYearList", schoolYearList);
        String schoolyear = request.getParameter("schoolyear");

        if (schoolyear == null) {
            schoolyear = schoolYearList.get(schoolYearList.size() - 1).getId();
        }
        request.setAttribute("sltedsy", schoolyear);

        WeekDAO weekDAO = new WeekDAO();
        List<Week> weekList = weekDAO.getWeeks(schoolyear);
        request.setAttribute("weekList", weekList);

        String week = request.getParameter("week");
        if (week == null) {
            Date crDate = new Date();
            for (Week week1 : weekList) {
                if (crDate.after(week1.getStartDate()) && crDate.before(week1.getEndDate())) {
                    week = week1.getId();
                    break;
                }
            }
        }

        request.setAttribute("sltedw", week);

        DayDAO dayDAO = new DayDAO();
        List<Day> dayList = dayDAO.getDayByWeek(week);
        request.setAttribute("dayList", dayList);

        TimeSlotDAO timeSlotDAO = new TimeSlotDAO();
        List<TimeSlot> timeslotList = timeSlotDAO.getTimeslotsForTimetable();
        request.setAttribute("timeslotList", timeslotList);

        SchoolClassDAO schoolClassDAO = new SchoolClassDAO();
        SchoolClass aClass = schoolClassDAO.getSchoolClassesById(student.getSchool_class_id().getId());
        request.setAttribute("aClass", aClass);

        TimetableDAO timetableDAO = new TimetableDAO();
        List<Timetable> timetables = timetableDAO.getTimetableByStudentIdAndWeekId(student.getId(), week);
        request.setAttribute("timetables", timetables);
        System.out.println(timetables.size());
        request.getRequestDispatcher("viewTimetable.jsp").forward(request, response);
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
