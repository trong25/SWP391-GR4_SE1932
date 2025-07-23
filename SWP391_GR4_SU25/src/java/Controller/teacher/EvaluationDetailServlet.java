package Controller.teacher;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.day.DayDAO;
import model.evaluation.EvaluationDAO;
import model.student.Student;
import model.student.StudentDAO;
import model.schoolYear.SchoolYear;
import model.schoolYear.SchoolYearDAO;
import model.user.User;
import model.week.WeekDAO;


@WebServlet(name = "teacher/EvaluationDetailServlet", value = "/teacher/evaluationdetail")
public class EvaluationDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        WeekDAO weekDAO = new WeekDAO();
        EvaluationDAO evaluationDAO = new EvaluationDAO();
        StudentDAO studentDAO = new StudentDAO();
        DayDAO dayDAO = new DayDAO();
        String weekId = request.getParameter("weekId");
        List<Student> listStudent = null;
        HttpSession session = request.getSession();
        SchoolYear schoolYear = null;
        // Get the current date
        User user = (User) session.getAttribute("user");
        if(!weekId.isBlank()){
            schoolYear = weekDAO.getWeek(weekId).getSchoolYear();
        }
        if(schoolYear!=null){
            listStudent = studentDAO.getListStudentOfTeacherBySchoolYear(schoolYear.getId(),user.getUsername());
        }
        request.setAttribute("evaluationList",evaluationDAO.getEvaluationByWeek(weekId));
        request.setAttribute("dayList",dayDAO.getDayByWeek(weekId));
        request.setAttribute("listStudent",listStudent);
        request.setAttribute("week",weekDAO.getWeek(weekId));
        request.getRequestDispatcher("evaluationDetail.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}