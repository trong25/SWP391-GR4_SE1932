package Controller.teacher;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.classes.ClassDAO;
import model.day.DayDAO;
import model.student.*;
import model.user.User;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

/**
 *
 * @author PC
 */

@WebServlet(name = "teacher/TakeAttendanceServlet", value = "/teacher/takeattendance")
public class TakeAttendanceServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //getting toast message sent from doPost after take attendance
        HttpSession session = request.getSession();
        String toastType = "", toastMessage = "";
        if (session.getAttribute("toastType") != null) {
            toastType = session.getAttribute("toastType").toString();
            toastMessage = session.getAttribute("toastMessage").toString();
        }
        session.removeAttribute("toastType");
        session.removeAttribute("toastMessage");
        request.setAttribute("toastType", toastType);
        request.setAttribute("toastMessage", toastMessage);

        StudentDAO StudentDAO = new StudentDAO();
        User user = (User) session.getAttribute("user");
        Date date = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = dateFormat.format(date);
        List<Student> Students = StudentDAO.getStudentsByTeacherAndTimetable(user.getUsername().toUpperCase(), dateString);
        ClassDAO classDAO = new ClassDAO();
        String className = classDAO.getClassNameByTeacherAndTimetable(user.getUsername(), dateString);
        request.setAttribute("currentDate", dateString);
        request.setAttribute("className", className);
        request.setAttribute("student", Students);
        request.getRequestDispatcher("takeAttendance.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Enumeration<String> params = request.getParameterNames();
        StudentAttendance studentAttendance = new StudentAttendance();
        DayDAO dayDAO = new DayDAO();
        StudentDAO StudentDAO = new StudentDAO();
        StudentAttendanceDAO studentAttendanceDAO = new StudentAttendanceDAO();
        Date currentDate = new Date();
        // Define the date format
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // Convert the Date to a String
        String dateString = formatter.format(currentDate);
        String result = "";
        while (params.hasMoreElements()) {
            String name = params.nextElement();
            if (name.startsWith("attendance")) {
                String studentId = name.substring(10);
                String status = request.getParameter(name);
                String note = request.getParameter("note" + studentId);
                studentAttendance.setDay(dayDAO.getDayByDate(dateString));
                studentAttendance.setStudent(StudentDAO.getStudentsById(studentId));
                studentAttendance.setStatus(status);
                studentAttendance.setNote(note);
                result = studentAttendanceDAO.addAttendance(studentAttendance);
            }
        }
        //sending result
        HttpSession session = request.getSession();
        if (result.equals("success")) {
            session.setAttribute("toastType", "success");
            session.setAttribute("toastMessage", "Thao tác thành công");
        } else {
            session.setAttribute("toastType", "error");
            session.setAttribute("toastMessage", result);
        }
        response.sendRedirect("takeattendance");
    }
}