package model.classes;

import java.sql.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.grade.GradeDAO;
import model.personnel.PersonnelDAO;
import model.schoolYear.SchoolYear;
import model.schoolYear.SchoolYearDAO;
import utils.DBContext;
import utils.Helper;

public class ClassDAO extends DBContext {

   private Class createClass(ResultSet resultSet) throws SQLException {
        Class c = new Class();
        c.setId(resultSet.getString("id"));
        c.setName(resultSet.getString("name"));
        GradeDAO gradeDAO = new GradeDAO();
        c.setGrade(gradeDAO.getGrade(resultSet.getString("grade_id")));
        PersonnelDAO personnelDAO = new PersonnelDAO();
        c.setTeacher(personnelDAO.getPersonnel(resultSet.getString("teacher_id")));
        SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        c.setSchoolYear(schoolYearDAO.getSchoolYear(resultSet.getString("school_year_id")));
        c.setStatus(resultSet.getString("status"));
        c.setCreatedBy(personnelDAO.getPersonnel(resultSet.getString("created_by")));
        return c;
    }


    public String createNewClass(Class c) {
        String sql = "INSERT INTO [Class] VALUES (?,?,?,?,?,?,?)";
        try {
            if (!isSchoolYearValid(c.getSchoolYear())) {
                return "Lớp phải được tạo trước khi năm học bắt đầu 7 ngày";
            }

            String newClassId = (getLatest() != null)
                    ? generateId(getLatest().getId())
                    : "C000001";

            if (c.getName().isBlank()) return "Tên lớp không được để trống";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, newClassId);
            ps.setString(2, c.getName());
            ps.setString(3, c.getGrade().getId());
            if (c.getTeacher() != null) {
                ps.setString(4, c.getTeacher().getId());
            } else {
                ps.setNull(4, Types.VARCHAR);
            }
            ps.setString(5, c.getSchoolYear().getId());
            ps.setString(6, "đang chờ xử lý");
            ps.setString(7, c.getCreatedBy().getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return "Thao tác thất bại. Lớp đã tồn tại";
        } catch (Exception e) {
            e.printStackTrace();
            return "Vui lòng tạo năm học trước khi tạo lớp";
        }
        return "success";
    }

    private boolean isSchoolYearValid(SchoolYear schoolYear) {
        LocalDate current = LocalDate.now();
        LocalDate start = Helper.convertDateToLocalDate(schoolYear.getStartDate());
        LocalDate end = Helper.convertDateToLocalDate(schoolYear.getEndDate());
        return !(end.isBefore(current) || !start.isAfter(current.plusDays(7)));
    }

    private Class getLatest() {
        String sql = "SELECT TOP 1 * FROM Class ORDER BY id DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) return createClass(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String generateId(String latestId) {
        Matcher matcher = Pattern.compile("\\d+").matcher(latestId);
        int number = matcher.find() ? Integer.parseInt(matcher.group()) + 1 : 1;
        return "C" + new DecimalFormat("000000").format(number);
    }

    public List<Class> getAll() {
        List<Class> list = new ArrayList<>();
        String sql = "SELECT * FROM Class ORDER BY id DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(createClass(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Class getClassById(String id) {
        String sql = "SELECT * FROM Class WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return createClass(rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<Class> getByStatus(String status, String schoolYearId) {
        List<Class> list = new ArrayList<>();
        String sql = "SELECT * FROM Class WHERE [status] = ? AND school_year_id = ? ORDER BY id DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setString(2, schoolYearId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(createClass(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Class> getBySchoolYear(String schoolYearId) {
        List<Class> list = new ArrayList<>();
        String sql = "SELECT * FROM Class WHERE school_year_id = ? ORDER BY id DESC";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, schoolYearId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(createClass(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Class> getBySchoolYearandStatus(String schoolYearId) {
        List<Class> list = new ArrayList<>();
        String sql = "SELECT * FROM Class WHERE school_year_id = ? AND status = N'đã được duyệt'";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, schoolYearId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(createClass(rs));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public Class getClassNameByTeacher(String teacherId) {
        String sql = "SELECT * FROM Class WHERE teacher_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, teacherId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return createClass(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Class getTeacherClassByYear(String year, String teacherId) {
        String sql = "SELECT * FROM Class WHERE teacher_id = ? AND school_year_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, teacherId);
            ps.setString(2, year);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return createClass(rs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getClassNameByTeacherAndTimetable(String teacherId, String date) {
        String sql = "SELECT DISTINCT c.name FROM Class c "
                   + "JOIN Timetables t ON c.id = t.class_id "
                   + "JOIN Days d ON t.date_id = d.id "
                   + "WHERE t.teacher_id = ? AND d.date = ? AND t.status = N'đã được duyệt'";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, teacherId);
            ps.setString(2, date);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Class> getClassesByGradeAndSchoolYear(String classId, String gradeId, String schoolYearId) {
        List<Class> list = new ArrayList<>();
        String sql = "SELECT * FROM Class WHERE school_year_id = ? AND grade_id = ? AND status = N'đã được duyệt'";
        if (classId != null) {
            sql += " AND id != ?";
        }
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, schoolYearId);
            ps.setString(2, gradeId);
            if (classId != null) {
                ps.setString(3, classId);
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(createClass(rs));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }


 public boolean moveOutClassForStudent(String oldClassId, String newClassId, String studentId) {
        String sql = "UPDATE classDetails SET class_id = ? WHERE student_id = ? AND class_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, newClassId);
            ps.setString(2, studentId);
            ps.setString(3, oldClassId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    public String assignTeacherToClass(String teacherId, String classId) {
        String sql = "UPDATE [Class] SET teacher_id = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, teacherId);
            ps.setString(2, classId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return "Phân công giáo viên vào lớp thất bại! Vui lòng thử lại sau!";
        }
        return "success";
    }


    public String reviewClass(String newStatus, String id) {
        String sql;
        if ("accept".equals(newStatus)) {
            sql = "UPDATE [Class] SET status = N'đã được duyệt' WHERE id = ?";
        } else {
            sql = "UPDATE [Class] SET status = N'không được duyệt', teacher_id = NULL WHERE id = ?";
        }
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            return "Có lỗi xảy ra khi duyệt, vui lòng thử lại";
        }
        return "success";
    }

}
