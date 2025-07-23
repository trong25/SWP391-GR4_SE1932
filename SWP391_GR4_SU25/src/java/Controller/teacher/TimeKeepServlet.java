package Controller.teacher;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.day.Day;
import model.day.DayDAO;
import model.schoolYear.SchoolYear;
import model.schoolYear.SchoolYearDAO;
import model.user.User;
import model.week.WeekDAO;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import model.personnel.Personnel;
import model.personnel.PersonnelDAO;

@WebServlet(name = "teacher/TimeKeepServlet", value = "/teacher/mytimekeeping")
public class TimeKeepServlet extends HttpServlet {
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
        if (schoolYearId != null) {
            //get list of weeks for select box
            WeekDAO weekDAO = new WeekDAO();
            request.setAttribute("weeks", weekDAO.getWeeks(schoolYearId));
            request.setAttribute("schoolYearId", schoolYearId);

            String weekId = request.getParameter("weekId");
            if (weekId == null) {
                weekId = weekDAO.getCurrentWeek(new Date());
            }
            if (weekId == null) {
                weekId = weekDAO.getfirstWeekOfClosestSchoolYear(schoolYearId).getId();
            }
            request.setAttribute("weekId", weekId);
            DayDAO dayDAO = new DayDAO();
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            request.setAttribute("personnelId", user.getUsername());
            PersonnelDAO personnelDAO = new PersonnelDAO();
            Personnel personnel = personnelDAO.getPersonnel(user.getUsername());
            request.setAttribute("personnelFullName", personnel.getLastName() + " " + personnel.getFirstName());
            System.out.println(weekId);
            List<Day> days = dayDAO.getDayByWeek(weekId);
            request.setAttribute("days", days);
        }

        //direct to jsp
        request.getRequestDispatcher("viewAttendance.jsp").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}