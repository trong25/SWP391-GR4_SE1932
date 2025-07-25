/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.academicstaff;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import model.classes.ClassDAO;
import model.day.Day;
import model.day.DayDAO;
import model.grade.Grade;
import model.grade.GradeDAO;
import model.personnel.Personnel;
import model.personnel.PersonnelDAO;
import model.schoolYear.SchoolYear;
import model.schoolYear.SchoolYearDAO;
import model.subject.Subject;
import model.subject.SubjectDAO;
import model.timeslot.TimeSlot;
import model.timeslot.TimeSlotDAO;
import model.timetable.Timetable;
import model.timetable.TimetableDAO;
import model.user.User;
import model.week.Week;
import model.week.WeekDAO;

/**
 * Servlet CreateTimetableServlet xử lý các yêu cầu HTTP để tạo mới thời khóa biểu cho lớp học.
 *
 * URL Mapping: /academicstaff/timetable
 *
 * Chức năng:
 * - Hiển thị form tạo thời khóa biểu (GET)
 * - Nhận dữ liệu từ form và tạo mới thời khóa biểu (POST)
 * - Kiểm tra phân quyền, kiểm tra trùng lặp, xác thực dữ liệu đầu vào
 * - Thông báo kết quả thành công/thất bại qua session
 *
 * Phân quyền: Chỉ nhân viên học vụ đã đăng nhập mới được phép tạo thời khóa biểu
 *
 * @author KienPN
 */
@WebServlet(name = "CreateTimetableServlet", urlPatterns = {"/academicstaff/timetable"})
public class CreateTimetableServlet extends HttpServlet {

    /**
     * Xử lý yêu cầu HTTP GET để hiển thị form tạo thời khóa biểu.
     *
     * Quy trình:
     * - Lấy các tham số lọc (năm học, khối, lớp, tuần)
     * - Lấy danh sách năm học, tuần, khối, lớp, ngày, tiết, môn học từ CSDL
     * - Đặt các thông tin vào attribute và forward sang createTimetable.jsp
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException nếu có lỗi servlet
     * @throws IOException nếu có lỗi IO
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

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

            String selectedGradeId = request.getParameter("gradeId");
            String weekId = request.getParameter("weekId");
            String selectedSchoolYearId = request.getParameter("schoolYearId");
            String classId = request.getParameter("classId");
            request.setAttribute("classSelected", classId);
            request.setAttribute("dateWeek", weekId);

            SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
            List<SchoolYear> listSchoolYears = schoolYearDAO.getAll();
            request.setAttribute("listSchoolYears", listSchoolYears);
            WeekDAO weekDAO = new WeekDAO();
            List<Week> listWeek = weekDAO.getAll();
            request.setAttribute("listWeek", listWeek);
            GradeDAO gradeDAO = new GradeDAO();
            List<Grade> listGrade = gradeDAO.getAll();
            request.setAttribute("listGrade", listGrade);

            SchoolYear schoolYear = null;
            if (selectedSchoolYearId != null) {
                schoolYear = schoolYearDAO.getSchoolYear(selectedSchoolYearId);
            }

            ClassDAO classDAO = new ClassDAO();
            // get list class by grade id
            List<model.classes.Class> classList = null;
            if (selectedGradeId != null && schoolYear != null) {
                classList = classDAO.getClassByGradeIdAndSchoolYearAndStatus(selectedGradeId, schoolYear.getId(), "đã được duyệt");
            }
            request.setAttribute("classList", classList);
            DayDAO dayDAO = new DayDAO();
            // get list day by week 
            List<Day> dayList = null;
            if (weekId != null) {
                dayList = dayDAO.getDayByWeek(weekId);
            }
            request.setAttribute("dayList", dayList);
            TimeSlotDAO timeSlotDAO = new TimeSlotDAO();
            List<TimeSlot> listTimeslot = timeSlotDAO.getTimeslotsForTimetable();
            request.setAttribute("listTimeslot", listTimeslot);
            SubjectDAO subjectDAO = new SubjectDAO();

            // get list subject by grade id
            List<Subject> subList = null;
            if (selectedGradeId != null) {
                subList = subjectDAO.getSubjectsByGradeId(selectedGradeId);
            }
            request.setAttribute("subList", subList);

            List<String> days = Arrays.asList("mon", "tue", "wed", "thu", "fri", "sat", "sun");
            request.setAttribute("days", days);
            request.getRequestDispatcher("createTimetable.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Xử lý yêu cầu HTTP POST để nhận dữ liệu form và tạo mới thời khóa biểu.
     *
     * Quy trình:
     * - Lấy dữ liệu từ form (lớp, ngày, tiết, môn, giáo viên, người tạo)
     * - Kiểm tra trùng lặp thời khóa biểu
     * - Sinh mã thời khóa biểu mới, tạo bản ghi trong CSDL
     * - Thông báo kết quả qua session (thành công/thất bại)
     * - Redirect về trang tạo thời khóa biểu
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException nếu có lỗi servlet
     * @throws IOException nếu có lỗi IO
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String action = request.getParameter("action");
            if (action == null) {
                response.sendRedirect("timetable");
            } else if (action.equals("create-timetable")) {
                System.out.println("Heheh");
                HttpSession session = request.getSession();
                TimetableDAO timetableDAO = new TimetableDAO();
                PersonnelDAO personnelDAO = new PersonnelDAO();
                ClassDAO classDAO = new ClassDAO();
                Timetable timetable = new Timetable();

                User user = (User) session.getAttribute("user");
                // Lấy các tham số chính từ form
                String classId = request.getParameter("classId");
                request.setAttribute("classSelected", classId);

                timetable.setaClass(classDAO.getClassById(classId));
                // Định nghĩa các tham số khác
                // Lấy giáo viên cho lớp, nếu không có thì lấy tạm user hiện tại làm giáo viên
                Personnel teacher = personnelDAO.getTeacherByClass(classId);
                if (teacher == null) {
                    // Nếu không có giáo viên phân công, lấy user hiện tại làm giáo viên (nếu là personnel)
                    teacher = personnelDAO.getPersonnelByUserId(user.getId());
                    System.out.println("Không tìm thấy giáo viên cho lớp, dùng user hiện tại làm giáo viên: " + (teacher != null ? teacher.getId() : "null"));
                }
                timetable.setTeacher(teacher);
                Personnel createdBy = personnelDAO.getPersonnelByUserIds(user.getId());
                timetable.setCreatedBy(createdBy);
                if (createdBy == null) {
                    session.setAttribute("toastType", "error");
                    session.setAttribute("toastMessage", "Thiếu thông tin người tạo! Vui lòng kiểm tra tài khoản nhân sự.");
                    response.sendRedirect("timetable");
                    return;
                }
                String status = "đang chờ xử lý";
                timetable.setStatus(status);

                // Kiểm tra dữ liệu giáo viên và người tạo
                if (timetable.getTeacher() == null || timetable.getCreatedBy() == null) {
                    session.setAttribute("toastType", "error");
                    session.setAttribute("toastMessage", "Thiếu thông tin giáo viên hoặc người tạo!");
                    response.sendRedirect("timetable");
                    return;
                }
                // Thu thập tất cả các `dayId` từ tham số đầu vào
                Enumeration<String> parameterNames = request.getParameterNames();
                Set<String> dayIds = new HashSet<>();
                while (parameterNames.hasMoreElements()) {
                    String paramName = parameterNames.nextElement();
                    if (paramName.startsWith("timeslotId_")) {
                        String[] parts = paramName.split("_");
                        String dayId = parts[1];
                        dayIds.add(dayId);
                    }
                }

                // Kiểm tra thời khóa biểu tồn tại cho tất cả dayId và timeslotId
                for (String dayId : dayIds) {
                    for (TimeSlot timeslot : new TimeSlotDAO().getTimeslotsForTimetable()) {
                        String paramName = "timeslotId_" + dayId + "_" + timeslot.getId();
                        String timeslotIdValue = request.getParameter(paramName);
                        if (timeslotIdValue != null && !timeslotIdValue.isEmpty()) {
                            if (timetableDAO.existsTimetableForClassDayAndTimeslot(classId, dayId, timeslot.getId())) {
                                session.setAttribute("toastType", "error");
                                session.setAttribute("toastMessage", "Thời khóa biểu của lớp này đã được tạo cho ca học này!");
                                response.sendRedirect("timetable");
                                return; // Dừng lại nếu thời khóa biểu đã tồn tại
                            }
                        }
                    }
                }

                // Lấy số lớn nhất hiện tại trong DB một lần
                int maxNumber = timetableDAO.getMaxTimetableNumber();
                StringBuilder sql = new StringBuilder("insert into Timetables([id], [class_id], [timeslot_id], [date_id], [subject_id], [created_by], [status], [note], [teacher_id]) values ");
                boolean hasInsert = false;
                parameterNames = request.getParameterNames();
                while (parameterNames.hasMoreElements()) {
                    String paramName = parameterNames.nextElement();
                    if (paramName.startsWith("timeslotId_")) {
                        String timeslotIdValue = request.getParameter(paramName);
                        if (!timeslotIdValue.isEmpty()) {
                            String[] parts = paramName.split("_");
                            String dayId = parts[1];
                            String timeslotId = parts[2];
                            String subjectId = timeslotIdValue; // ID môn học được chọn
                            // Kiểm tra dữ liệu đầu vào
                            if (classId == null || dayId == null || timeslotId == null || subjectId == null ||
                                timetable.getCreatedBy().getId() == null) {
                                System.out.println("Bỏ qua bản ghi lỗi: " + classId + ", " + dayId + ", " + timeslotId + ", " + subjectId);
                                continue; // Bỏ qua bản ghi lỗi
                            }
                            // Tăng số và sinh mã mới cho từng bản ghi
                            maxNumber++;
                            String timetableId = String.format("TT%06d", maxNumber);
                            if (hasInsert) sql.append(",");
                            sql.append("('").append(timetableId).append("','").append(classId).append("','")
                                .append(timeslotId).append("','").append(dayId).append("','")
                                .append(subjectId).append("','").append(timetable.getCreatedBy().getId())
                                .append("',N'").append(status).append("',NULL,");
                            if (timetable.getTeacher() != null) {
                                sql.append("'").append(timetable.getTeacher().getId()).append("')");
                            } else {
                                sql.append("NULL)");
                            }
                            hasInsert = true;
                            // In log chi tiết để debug
                            System.out.println("Insert: id=" + timetableId + ", classId=" + classId + ", dayId=" + dayId + ", timeslotId=" + timeslotId + ", subjectId=" + subjectId + ", createdBy=" + timetable.getCreatedBy().getId() + ", teacherId=" + (timetable.getTeacher() != null ? timetable.getTeacher().getId() : "NULL"));
                        }
                    }
                }
                if (!hasInsert) {
                    session.setAttribute("toastType", "error");
                    session.setAttribute("toastMessage", "Không có tiết học nào hợp lệ để tạo thời khóa biểu!");
                    response.sendRedirect("timetable");
                    return;
                }
                System.out.println("SQL to insert: " + sql.toString());
                String entryCreated = timetableDAO.createTimetable(sql.toString());
                if (entryCreated.equals("success")) {
                    session.setAttribute("toastType", entryCreated);
                    session.setAttribute("toastMessage", "Thời khóa biểu đã được tạo thành công.");
                } else {
                    session.setAttribute("toastType", "error");
                    session.setAttribute("toastMessage", entryCreated);
                }
                response.sendRedirect("timetable");

            }

        } catch (Exception e) {
            e.printStackTrace();
            HttpSession session = request.getSession();
            session.setAttribute("toastType", "error");
            session.setAttribute("toastMessage", "Có lỗi xảy ra khi tạo thời khóa biểu!");
            response.sendRedirect("timetable");
        }
    }

    /**
     * Trả về mô tả ngắn gọn về servlet.
     *
     * @return Chuỗi mô tả servlet
     */
    @Override
    public String getServletInfo() {
        return "Servlet tạo mới thời khóa biểu cho lớp học.";
    }// </editor-fold>

}

