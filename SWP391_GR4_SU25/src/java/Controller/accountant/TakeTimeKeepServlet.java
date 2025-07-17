/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.accountant;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import model.day.Day;
import model.day.DayDAO;
import model.personnel.Personnel;
import model.personnel.PersonnelAttendance;
import model.personnel.PersonnelAttendanceDAO;
import model.personnel.PersonnelDAO;

/**
 * Lớp TakeTimeKeepServlet chịu trách nhiệm quản lý chức năng chấm công nhân sự trong ngày hiện tại.
 * 
 * Phương thức doGet thực hiện việc lấy danh sách nhân sự cần chấm công cho ngày hiện tại. 
 * Nó kiểm tra xem có lịch chấm công trong ngày hay không, sau đó truyền dữ liệu ngày hiện tại và danh sách nhân sự lên JSP để hiển thị form chấm công. 
 * Đồng thời, phương thức này cũng lấy thông báo thành công hoặc lỗi từ session để hiển thị thông báo toast cho người dùng.
 * 
 * Phương thức doPost nhận dữ liệu chấm công được gửi từ form, bao gồm trạng thái có mặt hoặc vắng mặt và ghi chú của từng nhân sự. 
 * Phương thức lấy ngày hiện tại làm chuẩn để lưu dữ liệu chấm công. 
 * Với mỗi nhân sự, servlet kiểm tra xem nhân sự đó đã có bản ghi chấm công trong ngày hay chưa. 
 * Nếu chưa có, thêm mới bản ghi chấm công; nếu đã có, cập nhật lại trạng thái và ghi chú. 
 * Sau đó, kết quả thao tác được lưu vào session để hiển thị thông báo tương ứng, cuối cùng servlet chuyển hướng (redirect) về lại trang chấm công.
 * 
 * Đây là servlet thuộc tầng Controller trong mô hình MVC, tương tác với các lớp DAO như PersonnelDAO, DayDAO, PersonnelAttendanceDAO để truy xuất và cập nhật dữ liệu.
 * 
 * @author HuyDV
 * @version 1.0
 */


public class TakeTimeKeepServlet extends HttpServlet {

    private String getCurrentDateString() {
        Date currentDate = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(currentDate);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String toastType = "", toastMessage = "";
        if (session.getAttribute("toastType") != null) {
            toastType = session.getAttribute("toastType").toString();
            toastMessage = session.getAttribute("toastMessage").toString();
        }
        session.removeAttribute("toastType");
        session.removeAttribute("toastMessage");
        request.setAttribute("toastType", toastType);
        request.setAttribute("toastMessage", toastMessage);

        PersonnelDAO personnelDAO = new PersonnelDAO();
        DayDAO dayDAO = new DayDAO();

        // Sử dụng hàm tiện ích lấy ngày hiện tại
        String dateString = getCurrentDateString();

        if (dayDAO.getDayByDate(dateString) != null) {
            List<Personnel> personnel = personnelDAO.getPersonnelAttendance();
            request.setAttribute("date", dateString);
            request.setAttribute("personnel", personnel);
        }

        request.getRequestDispatcher("takeTimeKeep.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] personnelIds = request.getParameterValues("personnelId");
        HttpSession session = request.getSession();
        String result = "error";

        if (personnelIds != null && personnelIds.length > 0) {
            PersonnelAttendanceDAO attendanceDAO = new PersonnelAttendanceDAO();
            DayDAO dayDAO = new DayDAO();
            PersonnelDAO personnelDAO = new PersonnelDAO();

            // Sử dụng hàm tiện ích lấy ngày hiện tại
            String dateString = getCurrentDateString();
            Day day = dayDAO.getDayByDate(dateString); // lấy ngày 1 lần

            for (String personnelId : personnelIds) {
                String status = request.getParameter("attendance" + personnelId);
                String note = request.getParameter("note" + personnelId);

                PersonnelAttendance attendance = new PersonnelAttendance();
                attendance.setDay(day);
                attendance.setPersonnel(personnelDAO.getPersonnel(personnelId));
                attendance.setStatus(status);
                attendance.setNote(note);

                result = attendanceDAO.addAttendance(attendance);
            }
        }

        if ("success".equals(result)) {
            session.setAttribute("toastType", "success");
            session.setAttribute("toastMessage", "Thao tác thành công");
        } else {
            session.setAttribute("toastType", "error");
            session.setAttribute("toastMessage", result);
        }

        response.sendRedirect("taketimekeep");
    }
}