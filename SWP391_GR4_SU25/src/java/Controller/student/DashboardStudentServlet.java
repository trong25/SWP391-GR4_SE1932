    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */


package Controller.student;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.day.DayDAO;
import model.evaluation.EvaluationDAO;
import model.notification.NotificationDAO;
import model.student.StudentAttendanceDAO;
import model.student.StudentDAO;
import model.user.User;
import model.week.WeekDAO;

/**
 *
 * @author Admin
 */
public class DashboardStudentServlet extends HttpServlet {
   

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        NotificationDAO notificationDAO = new NotificationDAO();
        EvaluationDAO evaluationDAO = new EvaluationDAO();
        DayDAO dayDAO = new DayDAO();
        WeekDAO weekDAO = new WeekDAO();
        StudentDAO studentDAO = new StudentDAO();
        StudentAttendanceDAO studentAttendanceDAO = new StudentAttendanceDAO();

        Date currentDate = Date.from(Instant.now());
        String evaluation = "";
        String takeAttendance = "";
        if(weekDAO.getCurrentWeek(currentDate)==null){
            evaluation = "Không có đánh giá";
            takeAttendance = "Đang không trong năm học";
        }else{
            if( evaluationDAO.getEvaluationByStudentIdAndDay(studentDAO.getStudentByUserId(user.getId()).getId(),dayDAO.getDateIDbyDay(currentDate))!=null){
                evaluation = evaluationDAO.getEvaluationByStudentIdAndDay(studentDAO.getStudentByUserId(user.getId()).getId(),dayDAO.getDateIDbyDay(currentDate)).getEvaluation();
            }
            if(studentAttendanceDAO.getAttendanceByStudentAndDay(studentDAO.getStudentByUserId(user.getId()).getId(),dayDAO.getDateIDbyDay(currentDate))!=null){
                takeAttendance = studentAttendanceDAO.getAttendanceByStudentAndDay(studentDAO.getStudentByUserId(user.getId()).getId(),dayDAO.getDateIDbyDay(currentDate)).getStatus();
            }
        }


        int notifications = notificationDAO.getListNotifiByUserId(user.getId()).size();
        if(notificationDAO.getListNotifiByUserId(user.getId()).isEmpty()){
            notifications = 0;
        }
        request.setAttribute("evaluation", evaluation);
        request.setAttribute("takeAttendance", takeAttendance);
        request.setAttribute("notifications", notifications);
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    } 

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
