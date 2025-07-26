package Controller.teacher;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.classes.Class;
import model.classes.ClassDAO;
import model.day.Day;
import model.day.DayDAO;
import model.personnel.PersonnelDAO;
import model.student.Student;
import model.student.StudentDAO;
import model.schoolYear.SchoolYearDAO;
import model.user.User;
import model.week.WeekDAO;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author PC
 */

@WebServlet(name = "teacher/ViewStudentsAttendanceServlet", value = "/teacher/studentsattendance")
public class ViewStudentsAttendanceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //list of school years
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        request.setAttribute("schoolYears", schoolYearDAO.getAll());

        //get school year id from select box
        String schoolYearId = request.getParameter("schoolYearId");
        if (schoolYearId == null){
            if (schoolYearDAO.getLatest()!=null){
                schoolYearId = schoolYearDAO.getLatest().getId();
            }
        }
        if (schoolYearId != null){
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

            //send list of students
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            PersonnelDAO personnelDAO = new PersonnelDAO();
            String teacherId = personnelDAO.getPersonnelByUserIds(user.getId()).getId();
            ClassDAO classDAO = new ClassDAO();
            // Lấy danh sách lớp chủ nhiệm của giáo viên trong năm học này
            List<Class> classList = classDAO.getClassesByTeacherAndSchoolYear(teacherId, schoolYearId);
            request.setAttribute("classList", classList);
            String selectedClassId = request.getParameter("classId");
            if ((selectedClassId == null || selectedClassId.isEmpty()) && classList != null && !classList.isEmpty()) {
                selectedClassId = classList.get(0).getId();
            }
            request.setAttribute("selectedClassId", selectedClassId);

            Class classes = null;
            if (selectedClassId != null && !selectedClassId.isEmpty()) {
                classes = classDAO.getClassById(selectedClassId);
            }
            if (classes != null){
                //get day list
                DayDAO dayDAO = new DayDAO();
                // Lấy tất cả ngày trong tuần (không chỉ ngày có timetable)
                List<Day> days = dayDAO.getDayByWeek(weekId);
                request.setAttribute("days", days);
                request.setAttribute("classes", classes);

                //get student list
                StudentDAO studentDAO = new StudentDAO();
                List<Student> students = studentDAO.getListStudentsByClass(null, classes.getId());
                request.setAttribute("students", students);

            }
        }
        request.getRequestDispatcher("viewStudentsAttendance.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}