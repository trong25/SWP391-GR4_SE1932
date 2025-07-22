/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.timetable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.classes.ClassDAO;


import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.classes.ClassDAO;


import model.classes.Class;

import model.day.Day;
import model.day.DayDAO;
import model.personnel.Personnel;
import model.personnel.PersonnelDAO;
import model.subject.Subject;
import model.subject.SubjectDAO;
import model.timeslot.TimeSlot;
import model.timeslot.TimeSlotDAO;
import utils.DBContext;

/**
 *
 * @author MSI
 */
public class TimetableDAO extends DBContext {

    private Timetable createTimetable(ResultSet resultSet) throws SQLException {
        String id = resultSet.getString("id");
        String classId = resultSet.getString("class_id");
        String timeslotId = resultSet.getString("timeslot_id");
        String dateId = resultSet.getString("date_id");
        String subjectId = resultSet.getString("subject_id");
        String createdById = resultSet.getString("created_by");
        String status = resultSet.getString("status");
        String note = resultSet.getString("note");
        String teacherId = resultSet.getString("teacher_id");

        ClassDAO classDAO = new ClassDAO();
        TimeSlotDAO slotDAO = new TimeSlotDAO();
        DayDAO dayDAO = new DayDAO();
        SubjectDAO subDAO = new SubjectDAO();
        PersonnelDAO personnelDAO = new PersonnelDAO();

        model.classes.Class classs = classDAO.getClassById(classId);
        TimeSlot timeslot = slotDAO.getTimeslotById(timeslotId);
        Day day = dayDAO.getDayByID(dateId);
        Subject subject = subDAO.getSubjectBySubjectId(subjectId);
        Personnel createdBy = personnelDAO.getPersonnel(createdById);
        Personnel teacher = personnelDAO.getPersonnel(teacherId);

        return new Timetable(id, classs, timeslot, day, subject, createdBy, status, note, teacher);
    }

    public List<Timetable> getTimetableByClassAndWeek(String classId, String weekId, String status) {
        List<Timetable> timetables = new ArrayList<>();
        String sql = "SELECT t.id AS timetable_id,\n"
                + "       c.id AS class_id,\n"
                + "       ts.id AS timeslot_id,\n"
                + "       d.id AS date_id,\n"
                + "       s.id AS subject_id,\n"
                + "       t.created_by,\n"
                + "       t.status,\n"
                + "       t.note,\n"
                + "       p.id AS teacher_id\n"
                + "FROM Timetables t\n"
                + "JOIN Class c ON t.class_id = c.id\n"
                + "JOIN Timeslots ts ON t.timeslot_id = ts.id\n"
                + "JOIN Days d ON t.date_id = d.id\n"
                + "JOIN Subjects s ON t.subject_id = s.id\n"
                + "JOIN Personnels p ON t.teacher_id = p.id\n"
                + "JOIN Weeks w ON d.week_id = w.id\n"
                + "WHERE c.id = ? \n"
                + "  AND w.id = ?\n"
                + "  AND t.status = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, classId);
            statement.setString(2, weekId);
            statement.setString(3, status);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Fetching data from the result set and creating Timetable object for each row
                String timetableId = resultSet.getString("timetable_id");
                String classIdResult = resultSet.getString("class_id");
                String timeslotId = resultSet.getString("timeslot_id");
                String dateId = resultSet.getString("date_id");
                String subjectId = resultSet.getString("subject_id");
                String createdBy = resultSet.getString("created_by");
                String statusResult = resultSet.getString("status");
                String note = resultSet.getString("note");
                String teacherId = resultSet.getString("teacher_id");

                // Fetch related entities using DAOs
                ClassDAO classDAO = new ClassDAO();
                TimeSlotDAO timeslotDAO = new TimeSlotDAO();
                DayDAO dayDAO = new DayDAO();
                SubjectDAO subjectDAO = new SubjectDAO();
                PersonnelDAO personnelDAO = new PersonnelDAO();

                Class classs = classDAO.getClassById(classIdResult);
                TimeSlot timeslot = timeslotDAO.getTimeslotById(timeslotId);
                Day day = dayDAO.getDayByID(dateId);
                Subject subject = subjectDAO.getSubjectBySubjectId(subjectId);
                Personnel createdByObj = personnelDAO.getPersonnel(createdBy);
                Personnel teacher = personnelDAO.getPersonnel(teacherId);

                // Create Timetable object
                Timetable timetable = new Timetable(timetableId, classs, timeslot, day, subject, createdByObj, statusResult, note, teacher);
                timetables.add(timetable);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving timetables by classId and weekId", e);
        }
        return timetables;
    }

    public List<Timetable> getTeacherTimetable(String teacherId, String weekId) {
        List<Timetable> timetables = new ArrayList<>();

        String sql = """
                     SELECT 
                         t.id AS id,
                         c.id AS class_id,
                         ts.id AS timeslot_id,
                         d.id AS date_id,
                         s.id AS subject_id,
                         t.created_by,
                         t.status,
                         t.note,
                         p.id AS teacher_id,
                     
                         -- Thông tin bổ sung
                         c.name AS class_name,
                         ts.name AS timeslot_name,
                         ts.start_time,
                         ts.end_time,
                         d.date AS class_date,
                         d.day_of_week,
                         s.name AS subject_name,
                         CONCAT(p.first_name, ' ', p.last_name) AS teacher_name,
                     
                         -- Thêm trạng thái điểm danh
                         ISNULL(sa.status, N'Not yet') AS attendance_status
                     
                     FROM Timetables t
                     INNER JOIN Class c ON t.class_id = c.id
                     INNER JOIN classDetails cd ON c.id = cd.class_id
                     INNER JOIN Students st ON cd.student_id = st.id
                     INNER JOIN Timeslots ts ON t.timeslot_id = ts.id
                     INNER JOIN Days d ON t.date_id = d.id
                     INNER JOIN Weeks w ON d.week_id = w.id
                     INNER JOIN Subjects s ON t.subject_id = s.id
                     LEFT JOIN Personnels p ON t.teacher_id = p.id
                     LEFT JOIN StudentsAttendance sa 
                            ON sa.student_id = st.id 
                           AND sa.day_id = d.id 
                           AND sa.teacher_id = p.id
                     
                     WHERE st.id = ?
                       AND w.id = ? 
                     ORDER BY d.date, ts.slot_number;
                     """;

        String sql = "SELECT t.id AS timetable_id, "
                + "c.id AS class_id, "
                + "ts.id AS timeslot_id, "
                + "d.id AS date_id, "
                + "s.id AS subject_id, "
                + "t.created_by, "
                + "t.status, "
                + "t.note, "
                + "p.id AS teacher_id "
                + "FROM Timetables t "
                + "JOIN Class c ON t.class_id = c.id "
                + "JOIN Timeslots ts ON t.timeslot_id = ts.id "
                + "JOIN Days d ON t.date_id = d.id "
                + "JOIN Subjects s ON t.subject_id = s.id "
                + "JOIN Personnels p ON t.teacher_id = p.id "
                + "JOIN Weeks w ON d.week_id = w.id "
                + "WHERE p.id = ? "
                + "AND w.id = ? "
                + "AND t.status = ?";
 

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, studentID);
            statement.setString(2, weekId);
 
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Timetable timetable = createTimetable(resultSet);
                timetable.setAttendanceStatus(resultSet.getString("attendance_status"));

            statement.setString(3, "đã được duyệt");

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String timetableId = resultSet.getString("timetable_id");
                String classIdResult = resultSet.getString("class_id");
                String timeslotId = resultSet.getString("timeslot_id");
                String dateId = resultSet.getString("date_id");
                String subjectId = resultSet.getString("subject_id");
                String createdBy = resultSet.getString("created_by");
                String statusResult = resultSet.getString("status");
                String note = resultSet.getString("note");

                // Fetch related entities using DAOs
                ClassDAO classDAO = new ClassDAO();
                TimeSlotDAO timeslotDAO = new TimeSlotDAO();
                DayDAO dayDAO = new DayDAO();
                SubjectDAO subjectDAO = new SubjectDAO();
                PersonnelDAO personnelDAO = new PersonnelDAO();

                Class classs = classDAO.getClassById(classIdResult);
                TimeSlot timeslot = timeslotDAO.getTimeslotById(timeslotId);
                Day day = dayDAO.getDayByID(dateId);
                Subject subject = subjectDAO.getSubjectBySubjectId(subjectId);
                Personnel createdByObj = personnelDAO.getPersonnel(createdBy);
                Personnel teacher = personnelDAO.getPersonnel(teacherId);

                Timetable timetable = new Timetable(timetableId, classs, timeslot, day, subject, createdByObj, statusResult, note, teacher);
 
                timetables.add(timetable);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving timetables by teacherId and weekId", e);
        }
        return timetables;
    }

 
    public List<TeacherSlot> getTeacherByDayId(String dayId, String studentId) {
        List<TeacherSlot> list = new ArrayList<>();
        String sql = """
                     SELECT DISTINCT t.teacher_id,
                                     sub.name AS subject_name,
                                     ts.name AS timeslot_name,
                                     ts.id AS timeslot_id,
                                     ts.start_time,
                                     ts.end_time,
                                     ts.slot_number
                     FROM Timetables t
                     INNER JOIN Class c ON t.class_id = c.id
                     INNER JOIN classDetails cd ON c.id = cd.class_id
                     INNER JOIN Students s ON cd.student_id = s.id
                     INNER JOIN Subjects sub ON t.subject_id = sub.id
                     INNER JOIN Timeslots ts ON t.timeslot_id = ts.id
                     WHERE t.date_id = ?
                       AND s.id = ?;
                     """;

//    public List<TeacherSlot> getTeacherByDayId(String dayId, String studentId) {
//        List<TeacherSlot> list = new ArrayList<>();
//        String sql = """
//                     SELECT DISTINCT t.teacher_id,
//                                     sub.name AS subject_name,
//                                     ts.name AS timeslot_name,
//                                     ts.id AS timeslot_id,
//                                     ts.start_time,
//                                     ts.end_time,
//                                     ts.slot_number
//                     FROM Timetables t
//                     INNER JOIN Class c ON t.class_id = c.id
//                     INNER JOIN classDetails cd ON c.id = cd.class_id
//                     INNER JOIN Students s ON cd.student_id = s.id
//                     INNER JOIN Subjects sub ON t.subject_id = sub.id
//                     INNER JOIN Timeslots ts ON t.timeslot_id = ts.id
//                     WHERE t.date_id = ?
//                       AND s.id = ?;
//                     """;
    public List<Timetable> getTimetableByStudentIdAndWeekId(String studentID, String weekId) {
        List<Timetable> timetables = new ArrayList<>();
        String sql = """
                 SELECT t.id AS id,
                        c.id AS class_id,
                        ts.id AS timeslot_id,
                        d.id AS date_id,
                        s.id AS subject_id,
                        t.created_by,
                        t.status,
                        t.note,
                        p.id AS teacher_id,
                        -- Thêm các thông tin bổ sung hữu ích
                        c.name AS class_name,
                        ts.name AS timeslot_name,
                        ts.start_time,
                        ts.end_time,
                        d.date AS class_date,
                        d.day_of_week,
                        s.name AS subject_name,
                        CONCAT(p.first_name, ' ', p.last_name) AS teacher_name
                 FROM Timetables t
                 INNER JOIN Class c ON t.class_id = c.id
                 INNER JOIN classDetails cd ON c.id = cd.class_id
                 INNER JOIN Students st ON cd.student_id = st.id
                 INNER JOIN Timeslots ts ON t.timeslot_id = ts.id
                 INNER JOIN Days d ON t.date_id = d.id
                 INNER JOIN Weeks w ON d.week_id = w.id
                 INNER JOIN Subjects s ON t.subject_id = s.id
                 LEFT JOIN Personnels p ON t.teacher_id = p.id
                 WHERE st.id = ?
                   AND w.id = ?
                 ORDER BY d.date, ts.slot_number;
                 """;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, studentID);
            statement.setString(2, weekId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                timetables.add(createTimetable(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving timetables by classId and weekId", e);
        }
        return timetables;
    }

    public List<TeacherSlot> getTeacherByDayId(String dayId, String studentId) {
        List<TeacherSlot> list = new ArrayList<>();
        String sql = """
                 SELECT DISTINCT t.teacher_id,
                                 sub.name AS subject_name,
                                 ts.name AS timeslot_name,
                                 ts.id AS timeslot_id,
                                 ts.start_time,
                                 ts.end_time,
                                 ts.slot_number
                 FROM Timetables t
                 INNER JOIN Class c ON t.class_id = c.id
                 INNER JOIN classDetails cd ON c.id = cd.class_id
                 INNER JOIN Students s ON cd.student_id = s.id
                 INNER JOIN Subjects sub ON t.subject_id = sub.id
                 INNER JOIN Timeslots ts ON t.timeslot_id = ts.id
                 WHERE t.date_id = ?
                   AND s.id = ?;
                 """;

 
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, dayId);
            statement.setString(2, studentId);
            System.out.println(dayId);
            System.out.println(studentId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                list.add(new TeacherSlot(
                        resultSet.getString("teacher_id"),
                        resultSet.getString("subject_name"),
                        resultSet.getString("timeslot_name"),
                        resultSet.getString("timeslot_id")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

 
    public int getTodayClassesCount(String teacherId, String dayId) {
        String sql = "SELECT COUNT(*) as count "
                + "FROM Timetables t "
                + "WHERE t.teacher_id = ? AND t.date_id = ? AND t.status = N'đã được duyệt'";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, teacherId);
            statement.setString(2, dayId);

            // Debug logs
            System.out.println("Debug - Teacher ID: " + teacherId);
            System.out.println("Debug - Day ID: " + dayId);
            System.out.println("Debug - SQL: " + sql);


    public int getTodayClassesCount(String teacherId, String dateId) {
        String sql = "SELECT COUNT(*) as count FROM Timetables t "
                + "WHERE t.teacher_id = ? AND t.date_id = ? AND t.status = N'đã được duyệt'";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, teacherId);
            statement.setString(2, dateId);

   
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                System.out.println("Debug - Classes found: " + count);
                return count;
            }
        } catch (SQLException e) {
            System.out.println("Debug - Error in getTodayClassesCount: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    // Lấy số lớn nhất hiện tại trong DB
    public int getMaxTimetableNumber() {
        String sql = "SELECT MAX(CAST(SUBSTRING(id, 3, 6) AS INT)) AS max_id FROM Timetables";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt("max_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Nếu chưa có bản ghi nào
    }

    // Sinh mã mới dựa trên số lớn nhất
    public String generateTimetableId() {
        int maxNumber = getMaxTimetableNumber();
        int newNumber = maxNumber + 1;
        DecimalFormat decimalFormat = new DecimalFormat("000000");
        String result = decimalFormat.format(newNumber);
        return "TT" + result;
    }

    public boolean existsTimetableForClassInCurrentWeek(String classId, String dayId) {
        String sql = "SELECT status FROM Timetables WHERE class_id = ? AND date_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, classId);
            stmt.setString(2, dayId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String status = rs.getString("status");
                if ("đã được duyệt".equalsIgnoreCase(status) || "đang chờ xử lý".equalsIgnoreCase(status)) {
                    return true; // Timetable exists
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error checking timetable existence", e);
        }
        return false;
    }

    public String createTimetable(String sql) {
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return "Tạo thất bại!";
        }
        return "success";
    }

    public List<TimetableDTO> getTimetableByClassAndWeekAndCreateBy(String create_by) {
        List<TimetableDTO> timetables = new ArrayList<>();
        String sql = "SELECT DISTINCT\n"
                + "    temp.class_id,\n"
                + "    w.id AS week_id,\n"
                + "    w.start_date,\n"
                + "    w.end_date,\n"
                + "    temp.created_by,\n"
                + "    temp.status,\n"
                + "    temp.note,\n" // Thêm trường note vào câu truy vấn SQL
                + "    temp.teacher_id\n"
                + "FROM (\n"
                + "    SELECT\n"
                + "        t.id,\n"
                + "        t.class_id,\n"
                + "        d.week_id,\n"
                + "        t.created_by,\n"
                + "        t.status,\n"
                + "        t.note,\n" // Lấy trường note từ bảng Timetables
                + "        t.teacher_id,\n"
                + "        ROW_NUMBER() OVER (PARTITION BY t.class_id, d.week_id, t.status ORDER BY t.id) AS row_num\n"
                + "    FROM\n"
                + "        [Timetables] t\n"
                + "    JOIN [Days] d ON t.date_id = d.id\n"
                + "where t.created_by = ?"
                + ") AS temp\n"
                + "JOIN [Weeks] w ON temp.week_id = w.id\n"
                + "WHERE temp.row_num = 1\n"
                + "AND (NOT EXISTS (\n"
                + "        SELECT 1\n"
                + "        FROM [Timetables] t2\n"
                + "        JOIN [Days] d2 ON t2.date_id = d2.id\n"
                + "        WHERE temp.class_id = t2.class_id\n"
                + "        AND temp.week_id = d2.week_id\n"
                + "        AND temp.status <> t2.status\n"
                + "        AND t2.id < temp.id\n"
                + " )\n"
                + "    OR temp.row_num = 1);";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, create_by);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String classId = resultSet.getString("class_id");
                String weekId = resultSet.getString("week_id");
                Date startDate = resultSet.getDate("start_date");
                Date endDate = resultSet.getDate("end_date");
                String createdBy = resultSet.getString("created_by");
                String status = resultSet.getString("status");
                String note = resultSet.getString("note");  // Lấy giá trị note từ ResultSet
                String teacherId = resultSet.getString("teacher_id");

                ClassDAO classDAO = new ClassDAO();
                PersonnelDAO personnelDAO = new PersonnelDAO();

                Class classObj = classDAO.getClassById(classId);
                Personnel createdByObj = personnelDAO.getPersonnel(createdBy);
                Personnel teacherObj = personnelDAO.getPersonnel(teacherId);

                TimetableDTO timetable = new TimetableDTO(classObj, weekId, startDate, endDate, createdByObj, status, note, teacherObj);
                timetables.add(timetable);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving unique class timetables", e);
        }
        return timetables;
    }

    public Timetable getTimetableById(String id) {
        String sql = "SELECT * FROM Timetables where id=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setObject(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return createTimetable(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Giá trị mặc định nếu không có bản ghi nào trong bảng
    }

    // Lấy danh sách thời khóa biểu theo trạng thái
    public List<Timetable> getTimetablesByStatus(String status) {
        List<Timetable> list = new ArrayList<>();
        String sql = "SELECT * FROM Timetables WHERE status = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                list.add(createTimetable(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Cập nhật trạng thái thời khóa biểu
    public void updateTimetableStatus(String timetableId, String status) {
        String sql = "UPDATE Timetables SET status = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setString(2, timetableId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateTeacherOfTimetable(String classId, String teacherId) {
        String sql = "update Timetables set teacher_id = ? where class_id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, teacherId);
            statement.setString(2, classId);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public List<Timetable> getTeacherTimetable(String teacherId, String weekId) {
        List<Timetable> timetables = new ArrayList<>();
        String sql = "SELECT t.id AS timetable_id,\n" +
                "c.id AS class_id,\n" +
                "                ts.id AS timeslot_id,\n" +
                "                  d.id AS date_id,\n" +
                "                 s.id AS subject_id,\n" +
                "                    t.created_by,\n" +
                "                   t.status,\n" +
                "                 t.note,\n" +
                "                    p.id AS teacher_id\n" +
                "             FROM Timetables t\n" +
                "              JOIN Class c ON t.class_id = c.id\n" +
                "             JOIN Timeslots ts ON t.timeslot_id = ts.id\n" +
                "             JOIN Days d ON t.date_id = d.id\n" +
                "            JOIN Subjects s ON t.subject_id = s.id\n" +
                "           JOIN Personnels p ON t.teacher_id = p.id\n" +
                "           JOIN Weeks w ON d.week_id = w.id\n" +
                "                WHERE p.id = ?\n" +
                "           AND w.id = ?\n" +
                "          AND t.status = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, teacherId);
            statement.setString(2, weekId);
            statement.setString(3, "đã được duyệt");
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                // Fetching data from the result set and creating Timetable object for each row
                String timetableId = resultSet.getString("timetable_id");
                String classIdResult = resultSet.getString("class_id");
                String timeslotId = resultSet.getString("timeslot_id");
                String dateId = resultSet.getString("date_id");
                String subjectId = resultSet.getString("subject_id");
                String createdBy = resultSet.getString("created_by");
                String statusResult = resultSet.getString("status");
                String note = resultSet.getString("note");

                // Fetch related entities using DAOs
                ClassDAO classDAO = new ClassDAO();
                TimeSlotDAO timeslotDAO = new TimeSlotDAO();
                DayDAO dayDAO = new DayDAO();
                SubjectDAO subjectDAO = new SubjectDAO();
                PersonnelDAO personnelDAO = new PersonnelDAO();

                Class classs = classDAO.getClassById(classIdResult);
                TimeSlot timeslot = timeslotDAO.getTimeslotById(timeslotId);
                Day day = dayDAO.getDayByID(dateId);
                Subject subject = subjectDAO.getSubjectBySubjectId(subjectId);
                Personnel createdByObj = personnelDAO.getPersonnel(createdBy);
                Personnel teacher = personnelDAO.getPersonnel(teacherId);
// Create Timetable object
                Timetable timetable = new Timetable(timetableId, classs, timeslot, day, subject, createdByObj, statusResult, note, teacher);
                timetables.add(timetable);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving timetables by classId and weekId", e);
        }
        return timetables;
    }
}
