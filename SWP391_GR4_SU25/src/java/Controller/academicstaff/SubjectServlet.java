package Controller.academicstaff;

import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.grade.Grade;
import model.grade.GradeDAO;
import model.subject.Subject;
import model.subject.SubjectDAO;
import utils.Helper;


/**
*Servlet SubjectServlet xử lý các yêu cầu HTTP để hiển thị danh sách môn học
 * và chỉnh sửa thông tin môn học , tạo môn học  mới,
 * 
 * URL Mapping: /academicstaff/subject
 * Chức năng:
 * -Nhận dữ liệu từ form
 * - gọi SubjectDAO để tạo năm học mới và lưu vào cơ sở dữ liệu
 * Phân quyền: chỉ Giáo Vụ mới được phép tạo môn học mới.
 * @author TrongNV
 * @version 1.0
 */

public class SubjectServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SubjectDAO subjectDAO = new SubjectDAO();
        GradeDAO gradeDAO = new GradeDAO();
        String status = request.getParameter("status");
        List<Subject> subjectList;

        if (status == null || status.equals("all")) {
            subjectList = subjectDAO.getAll();
        } else {
            subjectList = subjectDAO.getSubjectsByStatus(status);
        }

        request.setAttribute("listAllSubject", subjectList);
        request.setAttribute("listGrade", gradeDAO.getAll());
        request.getRequestDispatcher("subject.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        GradeDAO gradeDAO = new GradeDAO();
        SubjectDAO subjectDAO = new SubjectDAO();
        HttpSession session = request.getSession();

        String name = request.getParameter("name");
        String grade = request.getParameter("grade");
        String description = request.getParameter("description");
        String subjectType = request.getParameter("subjectType");
        String toastMessage = "";
        String toastType = "error";

        // Format input
        name = Helper.formatName(name);
        description = Helper.formatString(description);
        subjectType = Helper.formatString(subjectType);

        // Regex kiểm tra input
        String regexSubjectName = "^[" + Helper.VIETNAMESE_CHARACTERS + "A-Za-z0-9\\s,;:.!?-]{1,125}$";
        String regexDescription = "^[" + Helper.VIETNAMESE_CHARACTERS + "A-Za-z0-9\\s,;:.!?-]{1,1000}$";
        String regexSubjectType = "^[" + Helper.VIETNAMESE_CHARACTERS + "A-Za-z0-9\\s,;:.!?-]{1,1000}$";

        if (action.equals("create")) {
            // Validate dữ liệu
            if (!name.matches(regexSubjectName) || !description.matches(regexDescription) ||
                grade == null || grade.isBlank() || !subjectType.matches(regexSubjectType)) {

                if (!name.matches(regexSubjectName)) {
                    toastMessage = "Tạo thất bại! Tên môn học trống hoặc quá 125 kí tự.";
                } else if (!description.matches(regexDescription)) {
                    toastMessage = "Tạo thất bại! Mô tả trống hoặc quá 1000 kí tự.";
                } else if (grade == null || grade.isBlank()) {
                    toastMessage = "Tạo thất bại! Vui lòng chọn khối.";
                } else if (!subjectType.matches(regexSubjectType)) {
                    toastMessage = "Tạo thất bại! Kiểu môn học trống hoặc quá 1000 kí tự.";
                }

                request.setAttribute("toastMessage", toastMessage);
                request.setAttribute("toastType", toastType);
                request.setAttribute("listAllSubject", subjectDAO.getAll());
                request.setAttribute("listGrade", gradeDAO.getAll());
                request.getRequestDispatcher("subject.jsp").forward(request, response);
                return;
            }

            // Tạo subject mới
            Subject subject = new Subject(null, name, gradeDAO.getGrade(grade), description, "đang chờ xử lý", subjectType);
            String result = subjectDAO.createSubject(subject);

            if (result.equals("success")) {
                session.setAttribute("toastMessage", "Tạo thành công");
                session.setAttribute("toastType", "success");
            } else {
                session.setAttribute("toastMessage", result);
                session.setAttribute("toastType", "error");
            }

            response.sendRedirect("subject");

        } else if (action.equals("edit")) {
            String subjectId = request.getParameter("subjectId");

            // Validate dữ liệu chỉnh sửa
            if (name.isEmpty() || name.length() > 125) {
                toastMessage = "Chỉnh sửa thất bại! Tên môn học trống hoặc quá 125 kí tự.";
            } else if (description.isEmpty() || description.length() > 1000) {
                toastMessage = "Chỉnh sửa thất bại! Mô tả trống hoặc quá 1000 kí tự.";
            } else if (subjectType.isEmpty() || subjectType.length() > 1000) {
                toastMessage = "Chỉnh sửa thất bại! Kiểu môn học trống hoặc quá 1000 kí tự.";
            }

            Subject subject = new Subject(subjectId, name, gradeDAO.getGrade(grade), description, "đang chờ xử lý", subjectType);

            if (!toastMessage.isEmpty()) {
                session.setAttribute("toastMessage", toastMessage);
                session.setAttribute("toastType", toastType);
                response.sendRedirect("subject");
                return;
            }

            String result = subjectDAO.editSubject(subject);

            if (result.equals("success")) {
                session.setAttribute("toastMessage", "Chỉnh sửa thành công");
                session.setAttribute("toastType", "success");
            } else {
                session.setAttribute("toastMessage", result);
                session.setAttribute("toastType", "error");
            }

            response.sendRedirect("subject");
        }
    }
}