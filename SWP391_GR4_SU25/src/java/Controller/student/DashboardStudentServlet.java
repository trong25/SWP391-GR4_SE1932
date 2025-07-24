/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.student;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.time.LocalDate;
import java.sql.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Calendar;
import model.day.DayDAO;
import model.evaluation.EvaluationDAO;
import model.notification.Notification;
import model.notification.NotificationDAO;
import model.student.Student;
import model.student.StudentAttendanceDAO;
import model.student.StudentDAO;
import model.timetable.Timetable;
import model.timetable.TimetableDAO;
import model.timeslot.TimeSlot;
import model.timeslot.TimeSlotDAO;
import model.day.Day;
import model.week.WeekDAO;
import model.user.User;

/**
 * Servlet DashboardStudentServlet xử lý các yêu cầu HTTP để hiển thị bảng điều
 * khiển của học sinh.
 *
 * URL Mapping: /student/dashboard
 *
 * Chức năng: - Lấy thông tin học sinh từ session - Truy xuất các dữ liệu liên
 * quan như: thời khóa biểu, đánh giá, điểm danh, thông báo - Tính toán tuần
 * hiện tại, lọc thông tin tương ứng trong CSDL - Chuyển tiếp dữ liệu sang trang
 * dashboard.jsp để hiển thị
 *
 * Phân quyền: Chỉ học sinh đã đăng nhập mới được truy cập trang bảng điều khiển
 * này
 *
 * @author KienPN
 * @version 1.0
 */
public class DashboardStudentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Lấy thông tin từ session
        User user = (User) request.getSession().getAttribute("user");
        Student student = (Student) request.getSession().getAttribute("student");
        String studentId = student.getId();

        //Khởi tạo các DAO
        NotificationDAO notificationDAO = new NotificationDAO();
        EvaluationDAO evaluationDAO = new EvaluationDAO();
        DayDAO dayDAO = new DayDAO();
        WeekDAO weekDAO = new WeekDAO();
        StudentDAO studentDAO = new StudentDAO();
        StudentAttendanceDAO studentAttendanceDAO = new StudentAttendanceDAO();
        TimeSlotDAO timeSlotDAO = new TimeSlotDAO();
        // Lấy ngày hiện tại (chỉ lấy phần ngày, không có giờ/phút/giây)
        LocalDate localDate = LocalDate.now();
        Date currentDate = Date.valueOf(localDate);
        try {
            // Lấy tuần hiện tại
            String weekId = weekDAO.getCurrentWeek(currentDate);
            List<Day> dayList = null;
            List<TimeSlot> timeslotList = null;
            List<Timetable> timetables = null;
            if (weekId != null) {
                dayList = dayDAO.getDayByWeek(weekId);
                timeslotList = timeSlotDAO.getTimeslotsForTimetable();
                TimetableDAO timetableDAO = new TimetableDAO();
                timetables = timetableDAO.getTimetableByStudentIdAndWeekId(studentId, weekId);
            }
            request.setAttribute("dayList", dayList);
            request.setAttribute("timeslotList", timeslotList);
            request.setAttribute("timetables", timetables);
            request.setAttribute("studentId", studentId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //Xử lý đánh giá và điểm danh
        String evaluation = "";
        String takeAttendance = "";
        if (weekDAO.getCurrentWeek(currentDate) == null) {
            evaluation = "Không có đánh giá";
            takeAttendance = "Đang không trong năm học";
        } else {
            if (evaluationDAO.getEvaluationByStudentIdAndDay(studentDAO.getStudentByUserId(user.getId()).getId(), dayDAO.getDateIDbyDay(currentDate)) != null) {
                evaluation = evaluationDAO.getEvaluationByStudentIdAndDay(studentDAO.getStudentByUserId(user.getId()).getId(), dayDAO.getDateIDbyDay(currentDate)).getEvaluation();
            }
            String todayDateId = dayDAO.getDateIDbyDay(currentDate);
            System.out.println("studentId: " + student.getId());
            System.out.println("currentDate: " + currentDate);
            System.out.println("todayDateId: " + todayDateId);
            String todayStatus = studentAttendanceDAO.getTodayAttendanceStatus(student.getId(), todayDateId);
            System.out.println("todayStatus: " + todayStatus);
            if (todayStatus == null || todayStatus.isEmpty()) {
                takeAttendance = "Chưa cập nhật";
            } else if ("present".equals(todayStatus)) {
                takeAttendance = "Có mặt";
            } else if ("absent".equals(todayStatus)) {
                takeAttendance = "Vắng";
            } else {
                takeAttendance = todayStatus;
            }
        }
        // Đếm số lượng thông báo
        List<Notification> listNotifications = notificationDAO.getListNotifiByUserId(user.getId());
        int notifications = listNotifications.size();
        if (notificationDAO.getListNotifiByUserId(user.getId()).isEmpty()) {
            notifications = 0;
        }
        request.setAttribute("listNotifications", listNotifications);
        request.setAttribute("evaluation", evaluation);
        request.setAttribute("takeAttendance", takeAttendance);
        request.setAttribute("notifications", notifications);
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
