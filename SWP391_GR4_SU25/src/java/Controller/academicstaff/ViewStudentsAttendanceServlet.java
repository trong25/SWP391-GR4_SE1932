package Controller.academicstaff;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.classes.Class;
import model.classes.ClassDAO;
import model.day.Day;
import model.day.DayDAO;
import model.student.StudentDAO;
import model.schoolYear.SchoolYearDAO;
import model.week.WeekDAO;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@WebServlet(name = "academicstaff/ViewStudentsAttendanceServlet", value = "/academicstaff/studentsattendance")
public class ViewStudentsAttendanceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //list of school years
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        request.setAttribute("schoolYears", schoolYearDAO.getAll());

        //get school year id from select box
        String schoolYearId = request.getParameter("schoolYearId");
        if (schoolYearId == null){
            schoolYearId = schoolYearDAO.getLatest().getId();
        }
        //get list of weeks for select box
        WeekDAO weekDAO = new WeekDAO();
        request.setAttribute("weeks", weekDAO.getWeeks(schoolYearId));
        request.setAttribute("schoolYearId", schoolYearId);

        String weekId = request.getParameter("weekId");
        if (weekId == null){
            weekId = weekDAO.getCurrentWeek(new Date());
        }
        if (weekId == null){
            weekId = weekDAO.getfirstWeekOfClosestSchoolYear(schoolYearId).getId();
        }
        request.setAttribute("weekId", weekId);



        //send list of class for select box
        ClassDAO classDAO = new ClassDAO();
        request.setAttribute("classList", classDAO.getBySchoolYear(schoolYearId));

        //get class id
        String classId = request.getParameter("classId");
        StudentDAO studentDAO = new StudentDAO();
        if (classId != null && !classId.isBlank()) {
            // Nếu đã chọn lớp, chỉ lấy danh sách học sinh của lớp đó
            Class classes = classDAO.getClassById(classId);
            request.setAttribute("classes", classes);
            request.setAttribute("students", studentDAO.getListStudentsByClass(null, classes.getId()));
            request.setAttribute("classId", classId);
            DayDAO dayDAO = new DayDAO();
            request.setAttribute("days", dayDAO.getDaysWithTimetableForClass(weekId, classId));
        } else {
            // Nếu chưa chọn lớp, lấy danh sách học sinh của tất cả các lớp trong năm học đó
            List<model.classes.Class> classList = classDAO.getBySchoolYear(schoolYearId);
            List<model.student.Student> allStudents = new java.util.ArrayList<>();
            for (Class c : classList) {
                List<model.student.Student> studentsInClass = studentDAO.getListStudentsByClass(null, c.getId());
                System.out.println("DEBUG - classId: " + c.getId() + ", số học sinh: " + studentsInClass.size());
                allStudents.addAll(studentsInClass);
            }
            System.out.println("DEBUG - Tổng số học sinh tất cả lớp: " + allStudents.size());
            request.setAttribute("students", allStudents);
            // Không cần lấy days, classes khi chưa chọn lớp
        }


        request.getRequestDispatcher("viewStudentsAttendance.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}