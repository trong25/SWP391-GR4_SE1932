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
            Class classes = classDAO.getTeacherClassByYear(schoolYearId, teacherId);
            if (classes != null){
                //get day list
                DayDAO dayDAO = new DayDAO();
                List<Day> days = dayDAO.getDaysWithTimetableForClass(weekId, classes.getId());
                request.setAttribute("days", days);
                request.setAttribute("classes", classes);

                //get student list
                StudentDAO studentDAO = new StudentDAO();
                List<Student> students = studentDAO.getListStudentsByClass(null, classes.getId());
                request.setAttribute("students", students);

                // Debug logs
//                System.out.println("Debug - Number of days: " + (days != null ? days.size() : 0));
//                System.out.println("Debug - Number of students: " + (students != null ? students.size() : 0));
//                if (days != null && !days.isEmpty()) {
//                    System.out.println("Debug - First day ID: " + days.get(0).getId());
//                }
//                if (students != null && !students.isEmpty()) {
//                    System.out.println("Debug - First student ID: " + students.get(0).getId());
//                }
            }
        }
        request.getRequestDispatcher("viewStudentsAttendance.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}