package Controller.student;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.evaluation.Evaluation;
import model.evaluation.EvaluationDAO;
import model.schoolYear.SchoolYear;
import model.schoolYear.SchoolYearDAO;
import model.student.Student;
import model.student.StudentDAO;
import model.user.User;
import model.week.Week;
import model.week.WeekDAO;

/**
 * Servlet ViewDailyEvaluationReportServlet xử lý các yêu cầu HTTP để hiển thị báo cáo đánh giá hàng ngày của học sinh.
 *
 * URL Mapping: /student/viewdailyevaluationreport
 *
 * Chức năng:
 * - Kiểm tra người dùng đã đăng nhập chưa (qua session)
 * - Lấy thông tin học sinh, năm học, tuần, đánh giá từ CSDL
 * - Xác định tuần/năm học hiện tại hoặc gần nhất
 * - Chuyển tiếp dữ liệu sang trang viewDailyEvaluationReport.jsp để hiển thị báo cáo
 *
 * Phân quyền: Chỉ học sinh đã đăng nhập mới được phép xem báo cáo đánh giá
 *
 * @author KienPN
 */
@WebServlet(name = "ViewDailyEvaluationReportServlet", urlPatterns = {"/student/viewdailyevaluationreport"})
public class ViewDailyEvaluationReportServlet extends HttpServlet {

    /**
     * Xử lý yêu cầu HTTP GET để hiển thị báo cáo đánh giá hàng ngày của học sinh.
     *
     * Quy trình:
     * - Lấy user từ session
     * - Lấy thông tin học sinh, năm học, tuần, đánh giá từ CSDL
     * - Xác định tuần/năm học hiện tại hoặc gần nhất
     * - Đặt các thông tin vào attribute và forward sang viewDailyEvaluationReport.jsp
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException nếu có lỗi servlet
     * @throws IOException nếu có lỗi IO
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
            StudentDAO studentDAO = new StudentDAO();
            EvaluationDAO evaluationDAO = new EvaluationDAO();
            WeekDAO weekDAO = new WeekDAO();
            Date currentDate = Date.from(Instant.now());

            // Lấy student 1 lần duy nhất
            Student student = studentDAO.getStudentByUserId(user.getId());

            String sltedw = "";
            String sltedy = "";
            String display = "week";

            // Xác định tuần và năm học hiện tại
            if (weekDAO.getCurrentWeek(currentDate) != null) {
                sltedw = weekDAO.getCurrentWeek(currentDate);
                sltedy = weekDAO.getYearByWeek(sltedw);
            } else if (schoolYearDAO.getClosestSchoolYears() != null
                    && schoolYearDAO.checkStudentInClassOfSchoolYear(student.getId(), schoolYearDAO.getClosestSchoolYears().getId())) {
                sltedy = schoolYearDAO.getClosestSchoolYears().getId();
                sltedw = weekDAO.getfirstWeekOfClosestSchoolYear(sltedy).getId();
            } else {
                sltedw = weekDAO.getLastWeekOfClosestSchoolYearOfStudent(student.getId()).getId();
                sltedy = weekDAO.getYearByWeek(sltedw);
            }

            List<SchoolYear> schoolYears = schoolYearDAO.getListSchoolYearsByStudentID(student.getId());
            List<Week> weekList = weekDAO.getWeeks(sltedy);
            List<Evaluation> evaluationList = evaluationDAO.getEvaluationByWeekandStudentId(sltedw, student.getId());
            Week choosenweek = weekDAO.getWeek(sltedw);

            // Set attribute cho JSP
            request.setAttribute("student", student);
            request.setAttribute("schoolYearList", schoolYears);
            request.setAttribute("weekList", weekList);
            request.setAttribute("evaluationList", evaluationList);
            request.setAttribute("display", display);
            request.setAttribute("sltedy", sltedy);
            request.setAttribute("sltedw", sltedw);
            request.setAttribute("cweek", choosenweek);

            request.getRequestDispatcher("viewDailyEvaluationReport.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Xử lý yêu cầu HTTP POST để lọc hoặc chuyển đổi chế độ hiển thị báo cáo đánh giá.
     *
     * Quy trình:
     * - Lấy các tham số lọc từ request (schoolyear, week, display)
     * - Lấy lại thông tin học sinh từ session
     * - Nếu chế độ hiển thị là "week", kiểm tra tuần hợp lệ và lấy dữ liệu đánh giá tuần đó
     * - Nếu không, lấy dữ liệu tổng hợp theo năm học
     * - Đặt các thông tin vào attribute và forward sang viewDailyEvaluationReport.jsp
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException nếu có lỗi servlet
     * @throws IOException nếu có lỗi IO
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
            StudentDAO studentDAO = new StudentDAO();
            EvaluationDAO evaluationDAO = new EvaluationDAO();
            WeekDAO weekDAO = new WeekDAO();
            String sltedy = request.getParameter("schoolyear");
            String sltedw = request.getParameter("week");
            String display = request.getParameter("display");
            Student student = studentDAO.getStudentByUserId(user.getId());
            if ("week".equalsIgnoreCase(display) && sltedw == null && sltedy == null) {
                response.sendRedirect("viewdailyevaluationreport");
            } else if ("week".equalsIgnoreCase(display)) {
                if (weekDAO.checkWeekInSchoolYear(sltedw, sltedy)) {
                    Week choosenweek = weekDAO.getWeek(sltedw);
                    request.setAttribute("cweek", choosenweek);
                    List<Evaluation> evaluationList = evaluationDAO.getEvaluationByWeekandStudentId(sltedw, student.getId());
                    request.setAttribute("evaluationList", evaluationList);
                }
                List<SchoolYear> schoolYears = schoolYearDAO.getListSchoolYearsByStudentID(student.getId());
                List<Week> weekList = weekDAO.getWeeks(sltedy);

                request.setAttribute("student", student);
                request.setAttribute("schoolYearList", schoolYears);
                request.setAttribute("weekList", weekList);
                request.setAttribute("sltedy", sltedy);
                request.setAttribute("sltedw", sltedw);
                request.setAttribute("display", display);
                request.getRequestDispatcher("viewDailyEvaluationReport.jsp").forward(request, response);
            } else {
                List<String> dataList = evaluationDAO.NumberOfGoodEvaluationsPerYear(student.getId());
                List<SchoolYear> schoolYears = new ArrayList<>();
                List<Integer> good_day = new ArrayList<>();
                List<Integer> week = new ArrayList<>();
                for (int i = 0; i < dataList.size(); i++) {
                    String[] parts = dataList.get(i).split("-");
                    schoolYears.add(schoolYearDAO.getSchoolYear(parts[0]));
                    good_day.add(Integer.parseInt(parts[1]));
                    week.add(Integer.parseInt(parts[2]));
                }
                request.setAttribute("student", student);
                request.setAttribute("schoolYears", schoolYears);
                request.setAttribute("good_day", good_day);
                request.setAttribute("week", week);
                request.setAttribute("display", display);
                request.getRequestDispatcher("viewDailyEvaluationReport.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
