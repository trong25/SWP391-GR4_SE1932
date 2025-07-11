package Controller.teacher;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.classes.Class;
import model.classes.ClassDAO;
import model.evaluation.EvaluationDAO;
import model.evaluation.SchoolYearSummarize;
import model.personnel.PersonnelDAO;
import model.student.Student;
import model.student.StudentDAO;
import model.schoolYear.SchoolYear;
import model.schoolYear.SchoolYearDAO;
import model.user.User;
import model.week.WeekDAO;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

@WebServlet(name = "teacher/StudentSchoolYearSummarizeServlet", value = "/teacher/schoolyearsummarize")
public class StudentSchoolYearSummarizeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String schoolYearId = request.getParameter("schoolYearId");
        HttpSession session = request.getSession();
        if (session.getAttribute("schoolYearId") != null) {
            schoolYearId = (String) session.getAttribute("schoolYearId");
        }
        String toastType = "", toastMessage = "";
        if (session.getAttribute("toastType") != null) {
            toastType = session.getAttribute("toastType").toString();
            toastMessage = session.getAttribute("toastMessage").toString();
        }
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        if (schoolYearId == null && schoolYearDAO.getLatest()!=null) {
            schoolYearId = schoolYearDAO.getLatest().getId();
        }

        session.removeAttribute("toastType");
        session.removeAttribute("toastMessage");
        request.setAttribute("toastType", toastType);
        request.setAttribute("toastMessage", toastMessage);
        session.removeAttribute("schoolYearId");
        request.setAttribute("schoolYearId", schoolYearId);

        //get list school year
        List<SchoolYear> schoolYears = schoolYearDAO.getAll();
        request.setAttribute("schoolYears", schoolYears);

        boolean display = false;
        SchoolYear schoolYear = schoolYearDAO.getSchoolYear(schoolYearId);
        WeekDAO weekDAO = new WeekDAO();
        if (schoolYear != null) {
            StudentDAO studentDAO = new StudentDAO();
            ClassDAO classDAO = new ClassDAO();
            User user = (User) session.getAttribute("user");
            PersonnelDAO personnelDAO = new PersonnelDAO();
            Class teacherClass = classDAO.getTeacherClassByYear(schoolYearId, personnelDAO.getPersonnelByUserId(user.getId()).getId());
            Date currentDate = Date.from(Instant.now());
            if (currentDate.after(weekDAO.getLastWeek(schoolYearId).getStartDate())) {
                List<Student> students = new ArrayList<>();
                display = true;
                if (teacherClass!=null){
                    students = studentDAO.getStudentByClass(teacherClass.getId());
                    request.setAttribute("students", students);
                }
                if (students.isEmpty()){
                    request.setAttribute("error", "Bạn không có lớp trong thời gian này");
                }
            }
            if (currentDate.after(schoolYear.getEndDate())) {
                request.setAttribute("expired", "true");
                System.out.println("true");
            }
        }
        request.setAttribute("display", display);



        request.getRequestDispatcher("schoolYearSummarize.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String schoolYearId = request.getParameter("schoolYearId");

        // Get all parameter names
        Enumeration<String> parameterNames = request.getParameterNames();
        SchoolYearSummarize schoolYearSummarize = new SchoolYearSummarize();
        schoolYearSummarize.setSchoolYearId(schoolYearId);
        PersonnelDAO personnelDAO = new PersonnelDAO();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        schoolYearSummarize.setTeacher(personnelDAO.getPersonnelByUserId(user.getId()));
        StudentDAO studentDAO = new StudentDAO();
        String result = "";

        // Loop through parameter names to process form fields
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();

            // Check if the parameter name matches the pattern for evaluate and note fields
            if (paramName.startsWith("evaluate")) {
                // Extract the Student ID from the parameter name
                String studentId = paramName.substring("evaluate".length());

                // Get the evaluation value
                String evaluationValue = request.getParameter(paramName);

                // Get the corresponding note value
                String noteParamName = "note" + studentId;
                String noteValue = request.getParameter(noteParamName);

                // Optionally get the goodTicket value
                String goodTicketParamName = "goodTicket" + studentId;
                String goodTicketValue = request.getParameter(goodTicketParamName);
                schoolYearSummarize.setStudent(studentDAO.getStudentsById(studentId));
                schoolYearSummarize.setGoodTicket(goodTicketValue);
                schoolYearSummarize.setTitle(evaluationValue);
                schoolYearSummarize.setTeacherNote(noteValue);

                EvaluationDAO evaluationDAO = new EvaluationDAO();
                result = evaluationDAO.updateSchoolYearSummarize(schoolYearSummarize);
                if (result.equals("success")) {
                    session.setAttribute("toastType", "success");
                    session.setAttribute("toastMessage", "Thao tác thành công");
                } else {
                    session.setAttribute("toastType", "error");
                    session.setAttribute("toastMessage", result);
                }
            }
        }

        session.setAttribute("schoolYearId", schoolYearId);

        response.sendRedirect("schoolyearsummarize");
    }
}