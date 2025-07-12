package Controller.teacher;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.classes.Class;
import model.classes.ClassDAO;
import model.day.DayDAO;
import model.student.Student;
import model.student.StudentDAO;
import model.schoolYear.SchoolYear;
import model.schoolYear.SchoolYearDAO;
import model.student.Student;
import model.user.User;
import model.week.Week;
import model.week.WeekDAO;


@WebServlet(name = "teacher/EvaluationReportServlet", value = "/teacher/evaluationreport")
public class EvaluationReportServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StudentDAO studentDAO = new StudentDAO();
        WeekDAO weekDAO = new WeekDAO();
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        ClassDAO classDAO = new ClassDAO();

        List<Student> listStudent =null;
        List<Week> listWeeks = weekDAO.getWeeks(schoolYearDAO.getLatest().getId());
        Class classes = new Class();
        HttpSession session = request.getSession();
        // Get the current date
        Date currentDate = new Date();
        User user = (User) session.getAttribute("user");
        SchoolYear schoolYear = schoolYearDAO.getSchoolYearByDate(currentDate);
        if(schoolYear!=null){
             listStudent = studentDAO.getListStudentOfTeacherBySchoolYear(schoolYear.getId(),user.getUsername());
             listWeeks= weekDAO.getWeeks(schoolYear.getId());
            classes = classDAO.getTeacherClassByYear(schoolYear.getId(), user.getUsername());
        }
        request.setAttribute("classes",classes);
        request.setAttribute("checkYear",schoolYear!=null?schoolYear.getId():schoolYearDAO.getLatest().getId());
        request.setAttribute("checkWeek",weekDAO.getCurrentWeek(currentDate));
        request.setAttribute("listWeeks",listWeeks);
        request.setAttribute("listStudent",listStudent);
        request.setAttribute("listSchoolYear", schoolYearDAO.getAll());
        request.getRequestDispatcher("evaluationReport.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StudentDAO studentDAO = new StudentDAO();
        WeekDAO weekDAO = new WeekDAO();
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        ClassDAO classDAO = new ClassDAO();

        List<Student> listStudent =null;
        Class classes = new Class();
        List<Week> listWeeks = null;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String schoolYear = request.getParameter("schoolYear");
        String week = request.getParameter("week");
        if(schoolYear!=null){
            listStudent = studentDAO.getListStudentOfTeacherBySchoolYear(schoolYear,user.getUsername());
            listWeeks= weekDAO.getWeeks(schoolYear);
            classes = classDAO.getTeacherClassByYear(schoolYear, user.getUsername());

        }
        request.setAttribute("classes",classes);
        request.setAttribute("checkWeek",week);
        request.setAttribute("checkYear",schoolYear);
        request.setAttribute("listWeeks",listWeeks);
        request.setAttribute("listStudent",listStudent);
        request.setAttribute("listSchoolYear", schoolYearDAO.getAll());
        request.getRequestDispatcher("evaluationReport.jsp").forward(request,response);
    }
}