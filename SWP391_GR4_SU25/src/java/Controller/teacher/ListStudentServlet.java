package Controller.teacher;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.classes.Class;
import model.classes.ClassDAO;
import model.personnel.Personnel;
import model.personnel.PersonnelDAO;
import model.student.Student;
import model.student.StudentDAO;
import model.schoolYear.SchoolYear;
import model.schoolYear.SchoolYearDAO;
import model.user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "teacher/ListStudentServlet", value = "/teacher/listStudent")
public class ListStudentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StudentDAO studentDAO = new StudentDAO();
        PersonnelDAO personnelDAO = new PersonnelDAO();
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        ClassDAO classDAO = new ClassDAO();
        
        HttpSession session = request.getSession();

        User user = null;
        //// variable to display the year that being checked
        String yearSelected = "";
        ///// Field to define the variable

        Class classes = new Class();
        List<Student> listStudent = new ArrayList<>();
        String gradeTeacher = null;
        String classTeacher = null;
        if (session.getAttribute("user") != null) {
            user = (User) session.getAttribute("user");

            if (schoolYearDAO.getLatest()!=null){
                yearSelected = schoolYearDAO.getLatest().getId();
            }
            String schoolYear = request.getParameter("schoolYear");
            Personnel teacher = personnelDAO.getPersonnelByUserId(user.getId());
            if (schoolYear != null) {
                yearSelected = schoolYear;
            }
            if (!yearSelected.isEmpty()) {
                listStudent = studentDAO.getListStudentOfTeacherBySchoolYear(yearSelected, teacher.getId());

                ///  Get Class and grade of class of this teacher in one school year
                classes = classDAO.getTeacherClassByYear(yearSelected, teacher.getId());
            }

            if (classes != null) {
                gradeTeacher = classes.getGrade().getName();
                classTeacher = classes.getName();
            }
            
        }
        List<SchoolYear> listSchoolYear = schoolYearDAO.getAll();
        request.setAttribute("teacherGrade", gradeTeacher);
        request.setAttribute("teacherClass", classTeacher);
        request.setAttribute("checkYear", yearSelected);
        request.setAttribute("listStudent", listStudent);
        request.setAttribute("listSchoolYear", listSchoolYear);
        request.getRequestDispatcher("listStudent.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    }
}
