/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.academicstaff;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import model.personnel.Personnel;
import model.personnel.PersonnelDAO;
import model.schoolYear.SchoolYear;
import model.schoolYear.SchoolYearDAO;
import model.user.User;
import utils.Helper;

/**
*Servlet SchoolYearServlet xử lý các yêu cầu HTTP để hiển thị danh sách năm học
 * và chỉnh sửa thông tin năm học , tạo năm học  mới,
 * 
 * URL Mapping: /academicstaff/schoolyear
 * Chức năng:
 * -Nhận dữ liệu từ form
 * - gọi SchoolYearDAO để tạo năm học mới và lưu vào cơ sở dữ liệu
 * Phân quyền: chỉ Giáo Vụ mới được phép tạo năm học mới.
 * @author TrongNV
 * @version 1.0
 */
public class SchoolYearServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
      SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        List<SchoolYear> schoolYears = schoolYearDAO.getAll();
        HttpSession session = request.getSession();
        String toastType ="", toastMessage ="";
        if(session.getAttribute("toastType") != null){
            toastType = session.getAttribute("toastType").toString();
            toastMessage = session.getAttribute("toastMessage").toString();
        }
        session.removeAttribute("toastType");
        session.removeAttribute("toastMessage");
        request.setAttribute("toastType", toastType);
        request.setAttribute("toastMessage", toastMessage);
        request.setAttribute("schoolYears", schoolYears);
        request.getRequestDispatcher("schoolYear.jsp").forward(request, response);
    } 

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
     SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        try {
            String action = request.getParameter("action");
            if(action== null){
                response.sendRedirect("schoolyear");
            }else{
                String startDateRaw = request.getParameter("startDate");
                String endDateRaw = request.getParameter("endDate");
                String description = request.getParameter("description");
                description = Helper.formatString(description);
                
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                String username = user.getUsername();
                SchoolYear schoolYear = new SchoolYear();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date startDate = dateFormat.parse(startDateRaw);
                Date endDate = dateFormat.parse(endDateRaw);
                schoolYear.setName(getSchoolYearName(startDate, endDate));
                schoolYear.setStartDate(startDate);
                schoolYear.setEndDate(endDate);
                schoolYear.setDescription(description);
                
                PersonnelDAO personnelDAO = new PersonnelDAO();
                Personnel personnel = personnelDAO.getPersonnel(username);
                schoolYear.setCreatedBy(personnel);
                String result ="";
                if(action.equals("create")){
                    result=schoolYearDAO.createNewSchoolYear(schoolYear);
                }else{
                    schoolYear.setId(request.getParameter("schoolYearId"));
                    result = schoolYearDAO.editSchoolYear(schoolYear);
                    
                }
                if(result.equals("success")){
                    session.setAttribute("toastType", "success");
                    session.setAttribute("toastMessage", "Thao tác thành công");
                }else{
                    session.setAttribute("toastType", "error");
                    session.setAttribute("toastMessage", result);
                }
                response.sendRedirect("schoolyear");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
private String getSchoolYearName(Date startDate, Date endDate) {
        // Convert Date to LocalDate
        LocalDate startLocalDate = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endLocalDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Extract the year from each LocalDate
        int startYear = startLocalDate.getYear();
        int endYear = endLocalDate.getYear();
        if (endYear == startYear) {
           return Helper.formatString(startYear + " ");
        }

        // Generate the school year string
        return startYear + "-" + endYear;
    }

}