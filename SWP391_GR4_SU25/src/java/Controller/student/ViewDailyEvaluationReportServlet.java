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

@WebServlet(name = "ViewDailyEvaluationReportServlet", urlPatterns = {"/student/viewdailyevaluationreport"})
public class ViewDailyEvaluationReportServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
            StudentDAO pupilDAO = new StudentDAO();
            EvaluationDAO evaluationDAO = new EvaluationDAO();
            WeekDAO weekDAO = new WeekDAO();
            Date currentDate = Date.from(Instant.now());
            String sltedw = "";
            String sltedy = "";
            String display = "week";
            Student pupil = pupilDAO.getStudentByUserId(user.getId());
            if (weekDAO.getCurrentWeek(currentDate) != null) {
                sltedw = weekDAO.getCurrentWeek(currentDate);
                sltedy = weekDAO.getYearByWeek(sltedw);
            } else if (schoolYearDAO.getClosestSchoolYears() != null && schoolYearDAO.checkPupilInClassOfSchoolYear(pupilDAO.getStudentByUserId(user.getId()).getId(), schoolYearDAO.getClosestSchoolYears().getId())) {
                sltedy = schoolYearDAO.getClosestSchoolYears().getId();
                sltedw = weekDAO.getfirstWeekOfClosestSchoolYear(sltedy).getId();
            } else {
                sltedw = weekDAO.getLastWeekOfClosestSchoolYearOfPupil(pupilDAO.getStudentByUserId(user.getId()).getId()).getId();
                sltedy = weekDAO.getYearByWeek(sltedw);
            }
            List<SchoolYear> schoolYears = schoolYearDAO.getListSchoolYearsByPupilID(pupilDAO.getStudentByUserId(user.getId()).getId());
            List<Week> weekList = weekDAO.getWeeks(sltedy);
            List<Evaluation> evaluationList = evaluationDAO.getEvaluationByWeekandPupilId(sltedw, pupilDAO.getStudentByUserId(user.getId()).getId());
            int good_day = evaluationDAO.countEvaluationOfWeek(sltedw, pupilDAO.getStudentByUserId(user.getId()).getId());
            Week choosenweek = weekDAO.getWeek(sltedw);
            request.setAttribute("pupil", pupil);
            request.setAttribute("good_day", good_day);
            request.setAttribute("schoolYearList", schoolYears);
            request.setAttribute("weekList", weekList);
            request.setAttribute("evaluationList", evaluationList);
            request.setAttribute("display", display);
            request.setAttribute("sltedy", sltedy);
            request.setAttribute("sltedw", sltedw);
            request.getRequestDispatcher("viewDailyEvaluationReport.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute("user");
            SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
            StudentDAO pupilDAO = new StudentDAO();
            EvaluationDAO evaluationDAO = new EvaluationDAO();
            WeekDAO weekDAO = new WeekDAO();
            String sltedy = request.getParameter("schoolyear");
            String sltedw = request.getParameter("week");
            String display = request.getParameter("display");
            Student pupil = pupilDAO.getStudentByUserId(user.getId());
            if (display.equalsIgnoreCase("week") && sltedw == null && sltedy == null) {
                response.sendRedirect("viewdailyevaluationreport");
            } else if (display.equalsIgnoreCase("week")) {
                if (weekDAO.checkWeekInSchoolYear(sltedw, sltedy)) {
                    Week choosenweek = weekDAO.getWeek(sltedw);
                    request.setAttribute("cweek", choosenweek);
                    List<Evaluation> evaluationList = evaluationDAO.getEvaluationByWeekandPupilId(sltedw, pupilDAO.getStudentByUserId(user.getId()).getId());
                    int good_day = evaluationDAO.countEvaluationOfWeek(sltedw, pupilDAO.getStudentByUserId(user.getId()).getId());
                    request.setAttribute("evaluationList", evaluationList);
                    request.setAttribute("good_day", good_day);
                }
                List<SchoolYear> schoolYears = schoolYearDAO.getListSchoolYearsByPupilID(pupilDAO.getStudentByUserId(user.getId()).getId());
                List<Week> weekList = weekDAO.getWeeks(sltedy);

                request.setAttribute("pupil", pupil);
                request.setAttribute("schoolYearList", schoolYears);
                request.setAttribute("weekList", weekList);

                request.setAttribute("sltedy", sltedy);
                request.setAttribute("sltedw", sltedw);
                request.setAttribute("display", display);
                request.getRequestDispatcher("viewDailyEvaluationReport.jsp").forward(request, response);
            } else {
                List<String> dataList = evaluationDAO.NumberOfGoodEvaluationsPerYear(pupilDAO.getStudentByUserId(user.getId()).getId());
                List<SchoolYear> schoolYears = new ArrayList<>();
                List<Integer> good_day = new ArrayList<>();
                List<Integer> week = new ArrayList<>();
                for (int i = 0; i < dataList.size(); i++) {
                    String[] parts = dataList.get(i).split("-");
                    schoolYears.add(schoolYearDAO.getSchoolYear(parts[0]));
                    good_day.add(Integer.parseInt(parts[1]));
                    week.add(Integer.parseInt(parts[2]));
                }
                request.setAttribute("pupil", pupil);
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
