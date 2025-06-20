/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.student;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;
import model.day.DayDAO;
import model.evaluation.EvaluationDAO;
import model.notification.Notification;
import model.notification.NotificationDAO;
import model.student.Student;
import model.student.StudentAttendanceDAO;
import model.student.StudentDAO;
import model.timetablepivot.TimetableDAO;
import model.timetablepivot.TimetablePivot;
import model.user.User;
import model.week.WeekDAO;
import utils.DBContext;

/**
 *
 * @author Admin
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
        Date currentDate = Date.from(Instant.now());// Lấy ngày hiện tại
        Connection connection = new DBContext().getConnection();
        try {

            // Gọi DAO để lấy dữ liệu
            TimetableDAO timetableDAO = new TimetableDAO(connection);
            List<TimetablePivot> timetableList = timetableDAO.getStudentTimetablePivot(studentId);

            // Đưa dữ liệu vào request scope
            request.setAttribute("timetableList", timetableList);
            request.setAttribute("studentId", studentId);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Đóng kết nối
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Error closing connection: " + e.getMessage());
                }
            }
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
            if (studentAttendanceDAO.getAttendanceByStudentAndDay(studentDAO.getStudentByUserId(user.getId()).getId(), dayDAO.getDateIDbyDay(currentDate)) != null) {
                takeAttendance = studentAttendanceDAO.getAttendanceByStudentAndDay(studentDAO.getStudentByUserId(user.getId()).getId(), dayDAO.getDateIDbyDay(currentDate)).getStatus();
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
