package Controller.teacher;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.classes.Class;
import model.classes.ClassDAO;
import model.day.Day;
import model.day.DayDAO;
import model.schoolYear.SchoolYear;
import model.schoolYear.SchoolYearDAO;
import model.student.Student;
import model.student.StudentDAO;
import model.user.User;
import model.week.Week;
import model.week.WeekDAO;

@WebServlet(name = "teacher/StudentsEvaluationServlet", value = "/teacher/studentsevaluation")
public class StudentsEvaluationServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Khởi tạo các DAO
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        WeekDAO weekDAO = new WeekDAO();
        StudentDAO studentDAO = new StudentDAO();
        DayDAO dayDAO = new DayDAO();
        ClassDAO classDAO = new ClassDAO();

        // Lấy session và user
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        // Lấy danh sách tất cả năm học
        List<SchoolYear> schoolYears = schoolYearDAO.getAll();
        request.setAttribute("schoolYears", schoolYears);

         // Lấy tham số từ request
        String schoolYearId;
        schoolYearId = request.getParameter("schoolYearId");
        String weekId = request.getParameter("weekId");
                
                

        // Nếu không có schoolYearId, lấy năm học hiện tại
        if (schoolYearId == null || schoolYearId.isEmpty()) {
            SchoolYear currentSchoolYear = schoolYearDAO.getLatest();
            if (currentSchoolYear != null) {
                schoolYearId = currentSchoolYear.getId();
            }
        }

        // Lấy danh sách tuần của năm học được chọn
        List<Week> weeks = null;
        if (schoolYearId != null && !schoolYearId.isEmpty()) {
            weeks = weekDAO.getWeeks(schoolYearId);
            request.setAttribute("schoolYearId", schoolYearId);
        }

        // Lấy lớp học của giáo viên trong năm học và danh sách học sinh theo lớp
        List<Class> classList = null;
        String selectedClassId = null;
        List<Student> students = null;
        if (schoolYearId != null && !schoolYearId.isEmpty() && user != null) {
            // Lấy danh sách lớp chủ nhiệm của giáo viên trong năm học này
            classList = classDAO.getClassesByTeacherAndSchoolYear(user.getUsername(), schoolYearId);
            request.setAttribute("classList", classList);
            selectedClassId = request.getParameter("classId");
            if ((selectedClassId == null || selectedClassId.isEmpty()) && classList != null && !classList.isEmpty()) {
                selectedClassId = classList.get(0).getId();
            }
            request.setAttribute("selectedClassId", selectedClassId);
            if (selectedClassId != null && !selectedClassId.isEmpty()) {
                students = studentDAO.getStudentByClass(selectedClassId);
            }
        }

        // Lấy danh sách ngày trong tuần được chọn
        List<Day> days = null;
        if (weekId != null && !weekId.isEmpty()) {
            days = dayDAO.getDayByWeek(weekId);
            request.setAttribute("weekId", weekId);
        }

        // Set các attribute cho JSP
        request.setAttribute("weeks", weeks);
        request.setAttribute("students", students);
        request.setAttribute("days", days);

        // Forward đến JSP
        request.getRequestDispatcher("viewEvaluationReport.jsp").forward(request, response);
    }
} 