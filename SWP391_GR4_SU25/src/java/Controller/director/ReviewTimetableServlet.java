/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.director;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import model.timetable.Timetable;
import model.timetable.TimetableDAO;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet ReviewTimetableServlet xử lý các yêu cầu HTTP để duyệt hoặc từ chối thời khóa biểu của các lớp học.
 *
 * URL Mapping: /director/reviewTimetable
 *
 * Chức năng:
 * - Hiển thị danh sách thời khóa biểu đang chờ duyệt (GET)
 * - Nhận yêu cầu duyệt hoặc từ chối thời khóa biểu (POST)
 * - Cập nhật trạng thái thời khóa biểu trong CSDL
 * - Thông báo kết quả duyệt/từ chối qua session
 *
 * Phân quyền: Chỉ giám đốc đã đăng nhập mới được phép duyệt thời khóa biểu
 *
 * @author KienPN
 */
@WebServlet(name = "ReviewTimetableServlet", urlPatterns = {"/director/reviewTimetable"})
public class ReviewTimetableServlet extends HttpServlet {

    /**
     * Xử lý yêu cầu HTTP GET để hiển thị danh sách thời khóa biểu đang chờ duyệt.
     *
     * Quy trình:
     * - Lấy danh sách thời khóa biểu có trạng thái "đang chờ xử lý" từ CSDL
     * - Đặt danh sách vào attribute và forward sang trang timetableWaitApprove.jsp
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException nếu có lỗi servlet
     * @throws IOException nếu có lỗi IO
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        // Lấy danh sách thời khóa biểu đang chờ duyệt
        TimetableDAO timetableDAO = new TimetableDAO();
        List<Timetable> pendingTimetables = timetableDAO.getTimetablesByStatus("đang chờ xử lý");
        request.setAttribute("pendingTimetables", pendingTimetables);
        request.getRequestDispatcher("timetableWaitApprove.jsp").forward(request, response);
    }

    /**
     * Xử lý yêu cầu HTTP POST để duyệt hoặc từ chối thời khóa biểu.
     *
     * Quy trình:
     * - Nhận timetableId và action (approve/reject) từ request
     * - Nếu approve: gọi DAO để duyệt thời khóa biểu, cập nhật trạng thái và thông báo kết quả
     * - Nếu reject: cập nhật trạng thái "đã bị từ chối" và thông báo kết quả
     * - Gọi lại doGet để cập nhật danh sách
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException nếu có lỗi servlet
     * @throws IOException nếu có lỗi IO
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        String timetableId = request.getParameter("timetableId");
        String action = request.getParameter("action");
        TimetableDAO timetableDAO = new TimetableDAO();
        HttpSession session = request.getSession();
        boolean result = false;
        if ("approve".equals(action)) {
            result = timetableDAO.approveTimetable(timetableId);
            if (result) {
                session.setAttribute("toastType", "success");
                session.setAttribute("toastMessage", "Duyệt thời khóa biểu thành công!");
            } else {
                session.setAttribute("toastType", "error");
                session.setAttribute("toastMessage", "Duyệt thất bại!");
            }
        } else if ("reject".equals(action)) {
            timetableDAO.updateTimetableStatus(timetableId, "đã bị từ chối");
            session.setAttribute("toastType", "success");
            session.setAttribute("toastMessage", "Từ chối thời khóa biểu thành công!");
        }
        doGet(request, response);
    }

    /**
     * Trả về mô tả ngắn gọn về servlet.
     *
     * @return Chuỗi mô tả servlet
     */
    @Override
    public String getServletInfo() {
        return "Servlet duyệt hoặc từ chối thời khóa biểu của các lớp học.";
    }// </editor-fold>

}
