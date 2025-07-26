package Controller.teacher;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.classes.ClassDAO;
import model.day.Day;
import model.day.DayDAO;
import model.personnel.Personnel;
import model.schoolYear.SchoolYear;
import model.schoolYear.SchoolYearDAO;
import model.timeslot.TimeSlot;
import model.timeslot.TimeSlotDAO;
import model.timetable.Timetable;
import model.timetable.TimetableDAO;
import model.user.User;
import model.week.Week;
import model.week.WeekDAO;

/**
 *
 * @author PC
 */
@WebServlet(name = "/teacher/ViewTimetableServlet", urlPatterns = {"/teacher/view-timetable"})
public class ViewTimetableServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<SchoolYear> schoolYearList = new SchoolYearDAO().getAll();
        request.setAttribute("schoolYearList", schoolYearList);
        request.getRequestDispatcher("viewTimetable.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Personnel personnel = (Personnel) session.getAttribute("personnel");
        String id = personnel.getId();
        String week = request.getParameter("week");
        String schoolyear = request.getParameter("schoolyear");
        model.classes.Class aclass = new ClassDAO().getTeacherClassByYear(schoolyear, id);
        if (aclass == null) {
            List<SchoolYear> schoolYearList = new SchoolYearDAO().getAll();
            request.setAttribute("toastType", "error");
            request.setAttribute("toastMessage", "Bạn chưa được phân công năm này hãy chọn lại");
            request.setAttribute("schoolYearList", schoolYearList);
            request.getRequestDispatcher("viewTimetable.jsp").forward(request, response);
        } else {
            WeekDAO weekDAO = new WeekDAO();
            SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
            List<SchoolYear> schoolYearList = schoolYearDAO.getAll();
            List<Week> weekList = weekDAO.getWeeks(schoolyear);
            List<Timetable> timetable;
            TimeSlotDAO timeslotDAO = new TimeSlotDAO();
            List<TimeSlot> timeslotList = timeslotDAO.getTimeslotsForTimetable();
            DayDAO dayDAO = new DayDAO();
            List<Day> dayList = dayDAO.getDayByWeek(week);
            if (dayList.size() > 0) {
                request.setAttribute("timeslotList", timeslotList);
            }
            User user = (User) session.getAttribute("user");
            timetable = new TimetableDAO().getTeacherTimetable(user.getUsername(), week);
            request.setAttribute("timetable", timetable);
            request.setAttribute("sltedsy", schoolyear);
            request.setAttribute("sltedw", week);
            request.setAttribute("schoolYearList", schoolYearList);
            request.setAttribute("weekList", weekList);
            request.setAttribute("dayList", dayList);
            request.setAttribute("aClass", aclass);
            request.getRequestDispatcher("viewTimetable.jsp").forward(request, response);
        }
    }
}
