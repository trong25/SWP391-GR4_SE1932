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
import model.timetable.Timetable;
import model.timetable.TimetableDAO;
import model.timetable.TimetableDTO;

/**
 * Servlet ListTimetable xử lý các yêu cầu HTTP để hiển thị danh sách thời khóa biểu của một lớp học.
 *
 * URL Mapping: /academicstaff/listtimetable
 *
 * Chức năng:
 * - Nhận classId từ request parameter (?id=...)
 * - Lấy danh sách thời khóa biểu của lớp từ CSDL qua TimetableDAO
 * - Chuyển tiếp dữ liệu sang trang listTimetable.jsp để hiển thị
 *
 * Phân quyền: Chỉ nhân viên học vụ đã đăng nhập mới được phép xem danh sách thời khóa biểu
 *
 * @author KienPN
 */
@WebServlet(name = "ListTimetable", urlPatterns = {"/academicstaff/listtimetable"})
public class ListTimetable extends HttpServlet {

    /**
     * Xử lý yêu cầu HTTP GET để hiển thị danh sách thời khóa biểu của một lớp học.
     *
     * Quy trình:
     * - Lấy classId từ request parameter (?id=...)
     * - Lấy danh sách thời khóa biểu từ TimetableDAO
     * - Đặt danh sách vào attribute và forward sang listTimetable.jsp
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException nếu có lỗi servlet
     * @throws IOException nếu có lỗi IO
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //classid , date_id(startdate-enddate) , createby , status , teacherid
        String id = request.getParameter("id");
        TimetableDAO timetableDAO = new TimetableDAO();
        List<TimetableDTO> listTimetable = timetableDAO.getTimetableByClassAndWeekAndCreateBy(id);
        request.setAttribute("listTimetable", listTimetable);
        request.getRequestDispatcher("listTimetable.jsp").forward(request, response);
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

}
