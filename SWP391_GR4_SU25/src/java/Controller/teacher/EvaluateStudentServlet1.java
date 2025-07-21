package Controller.teacher;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.classes.Class;
import model.classes.ClassDAO;
import model.day.Day;
import model.day.DayDAO;
import model.evaluation.Evaluation;
import model.evaluation.EvaluationDAO;
import model.personnel.Personnel;
import model.personnel.PersonnelDAO;
import model.student.*;
import model.schoolYear.SchoolYear;
import model.schoolYear.SchoolYearDAO;
import model.user.User;
import utils.Helper;


@WebServlet(name = "teacher/EvaluateStudentServlet", value = "/teacher/evaluate")
public class EvaluateStudentServlet1 extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StudentDAO studentDAO = new  StudentDAO();
        ClassDAO classDAO = new ClassDAO();
        DayDAO dayDAO = new DayDAO();
         StudentAttendanceDAO  studentAttendanceDAO = new  StudentAttendanceDAO();

        HttpSession session = request.getSession();
        // Get the current date
        Date currentDate = new Date();
        // Define the date format
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        // Convert the Date to a String
        String dateString = formatter.format(currentDate);
        String dateId =null;
        String checkAttendance ="notAttendance";
        User user = (User) session.getAttribute("user");
        List< Student> listStudent = studentDAO.getStudentsByTeacherAndTimetable(user.getUsername().toUpperCase(), dateString);
        String  className = classDAO.getClassNameByTeacherAndTimetable(user.getUsername(), dateString);
        Day day = dayDAO.getDayByDate(dateString);
        if(day!=null){
            dateId = day.getId();
            if(studentAttendanceDAO.checkAttendanceByDay(listStudent,dateId)){
                checkAttendance ="attendance" ;
            }
        }
        request.setAttribute("checkAttendance",checkAttendance);
        request.setAttribute("dateId",dateId);
        request.setAttribute("teacherClass",className);
        request.setAttribute("listStudent", listStudent);
        request.getRequestDispatcher("evaluateStudent.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StudentDAO studentDAO = new StudentDAO();
        EvaluationDAO evaluationDAO = new EvaluationDAO();
        DayDAO dayDAO = new DayDAO();
        HttpSession session = request.getSession();

        String action = request.getParameter("action");
        String toastType = "", toastMessage = "";
        if(action.equals("evaluateStudent")){
            // Thu thập tất cả các tham số đầu vào
            Enumeration<String> parameterNames = request.getParameterNames();
            HashMap<String,String> evaluateMap = new HashMap<>();
            while(parameterNames.hasMoreElements()){
                String paramName = parameterNames.nextElement();
                if(paramName.startsWith("evaluation-") || paramName.startsWith("notes-")){
                    if(paramName.startsWith("evaluation-") ){
                        String   evaltuationValue = request.getParameter(paramName);
                        String[]   partsEvaluation = paramName.split("-");
                        evaluateMap.put(partsEvaluation[1]+"-evaluation",evaltuationValue);
                        /// add evaluation at the end to studentId different from studentId at note
                    }
                    if(paramName.startsWith("notes-")){
                        String  noteValue = request.getParameter(paramName);
                        String[]    partsNotes = paramName.split("-");
                        evaluateMap.put(partsNotes[1]+"-notes",noteValue);
                        /// add note at the end to studentId different from studentId at evaluation
                    }
                }
            }

            int counter =0;
            Date currentDate = new Date();
            // Define the date format
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            // Convert the Date to a String
            String dateString = formatter.format(currentDate);
            User user = (User) session.getAttribute("user");
            List<Student> listStudent = studentDAO.getStudentsByTeacherAndTimetable(user.getUsername().toUpperCase(), dateString);


            if(listStudent!=null){
                for(Student student : listStudent){
                    Evaluation evaluation = new Evaluation();
                    evaluation.setStudent(studentDAO.getStudentsById(student.getId()));
                    evaluation.setDate(dayDAO.getDayByDate(dateString));
                    if(evaluateMap.containsKey(student.getId()+"-evaluation")){
                        evaluation.setEvaluation(Helper.formatString(evaluateMap.get(student.getId()+"-evaluation")));
                    }
                    if(evaluateMap.containsKey(student.getId()+"-notes")){
                        evaluation.setNotes(Helper.formatString(evaluateMap.get(student.getId()+"-notes")));
                    }
                    if(evaluationDAO.checkEvaluationExist(student.getId(),dayDAO.getDayByDate(dateString).getId())){
                        if(evaluationDAO.updateEvaluationByStudentAndDay(evaluation)
                        && evaluationDAO.updateNoteByStudentAndDay(evaluation)){
                            counter++;
                        }
                    }else {
                        boolean result;
                        result = evaluationDAO.createEvaluation(evaluation);
                        if (result) {
                            counter++;
                        }
                    }

                }
            }
            if(counter==listStudent.size()){
                toastType="success";
                toastMessage="Thao tác thành công";
            }else {
                toastType="error";
                toastMessage="Thao tác thất bại";
            }
            session.setAttribute("toastType", toastType);
            session.setAttribute("toastMessage", toastMessage);
            response.sendRedirect("evaluate");
        }
    }
}