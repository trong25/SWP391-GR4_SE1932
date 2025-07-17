/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.personnel.Personnel;
import model.personnel.PersonnelDAO;
import model.school.SchoolDAO;
import model.school.Schools;
import model.schoolclass.SchoolClass;
import utils.DBContext;

/**
 * Lớp StudentDAO chịu trách nhiệm thao tác dữ liệu với bảng StudentDAO trong
 * Database Lấy dữ liệu từ database liên quan đến bảng SubjectDAO Thức hiên các
 * chức năng như tạo học sinh, lấy học sinh qua id, cập nhật và chỉnh sửa thông
 * tin học sinh, Ví dụ: createStudent(Student student),getStudentByStatus(String
 * status), getListStudentsByClass(String studentId, String classId),
 * updateStudent(Student student),...
 *
 * Sử dụng JDBC để kết nới với cơ sở dữ liệu SQL Server
 *
 * @author TrongNV
 */
public class StudentDAO extends DBContext {

    private Student createStudent(ResultSet resultSet) throws SQLException {
        try {
            PersonnelDAO personnelDAO = new PersonnelDAO();
            SchoolDAO schoolDAO = new SchoolDAO();

            Student student = new Student();
            student.setId(resultSet.getString("id"));
            student.setUserId(resultSet.getString("user_id"));
            student.setFirstName(resultSet.getString("first_name"));
            student.setLastName(resultSet.getString("last_name"));
            student.setAddress(resultSet.getString("address"));
            student.setEmail(resultSet.getString("email"));
            student.setStatus(resultSet.getString("status"));
            student.setBirthday(resultSet.getDate("birthday"));
            student.setGender(resultSet.getBoolean("gender"));
            student.setFirstGuardianName(resultSet.getString("first_guardian_name"));
            student.setFirstGuardianPhoneNumber(resultSet.getString("first_guardian_phone_number"));
            student.setAvatar(resultSet.getString("avatar"));
            student.setSecondGuardianName(resultSet.getString("second_guardian_name"));
            student.setSecondGuardianPhoneNumber(resultSet.getString("second_guardian_phone_number"));
            student.setCreatedBy(personnelDAO.getPersonnel(resultSet.getString("created_by")));
            student.setParentSpecialNote(resultSet.getString("parent_special_note"));

            // Tạo và gán School object từ ResultSet
            Schools school = new Schools();
            school.setId(resultSet.getString("school_id"));
            school.setSchoolName(resultSet.getString("schoolName"));
            school.setAddressSchool(resultSet.getString("addressSchool"));
            student.setSchool_id(school);

            // Tạo và gán SchoolClass object từ ResultSet
            SchoolClass schoolClass = new SchoolClass();
            schoolClass.setId(resultSet.getString("school_class_id"));
            schoolClass.setClassName(resultSet.getString("class_name"));
            schoolClass.setGrade_level(resultSet.getString("grade_name"));
            student.setSchool_class_id(schoolClass);

            return student;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Student getLatest() {
        String sql = """
        SELECT TOP 1 s.*, 
                     sc.schoolName, 
                     sc.addressSchool, 
                     c.class_name, 
                     c.grade_level AS grade_name
        FROM Students s 
        LEFT JOIN Schools sc ON s.school_id = sc.id 
        LEFT JOIN SchoolClasses c ON s.school_class_id = c.id 
        ORDER BY s.id DESC
    """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createStudent(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql); ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                return createStudent(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String generateId(String latestId) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(latestId);
        int number = 0;
        if (matcher.find()) {
            number = Integer.parseInt(matcher.group()) + 1;
        }
        DecimalFormat decimalFormat = new DecimalFormat("000000");
        String result = decimalFormat.format(number);
        return "HS" + result;
    }

    public boolean createStudent(Student student) {
        String insertSql = """
        INSERT INTO [dbo].[Students]
        ([id], [user_id], [first_name], [last_name], [address], [email], [status],
         [birthday], [gender], [first_guardian_name], [first_guardian_phone_number], [avatar],
         [second_guardian_name], [second_guardian_phone_number], [created_by], 
         [parent_special_note], [school_id], [school_class_id])
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
    """;

        try (PreparedStatement stmt = connection.prepareStatement(insertSql)) {
            // Sinh ID mới
            String newId = "HS000001";
            Student latest = getLatest();
            if (latest != null) {
                newId = generateId(latest.getId());
            }
            student.setId(newId);

            // Gán giá trị
            stmt.setString(1, student.getId());
            stmt.setString(2, student.getUserId());
            stmt.setString(3, student.getFirstName());
            stmt.setString(4, student.getLastName());
            stmt.setString(5, student.getAddress());
            stmt.setString(6, student.getEmail());
            stmt.setString(7, student.getStatus());

            if (student.getBirthday() != null) {
                stmt.setDate(8, new java.sql.Date(student.getBirthday().getTime()));
            } else {
                stmt.setNull(8, java.sql.Types.DATE);
            }

            stmt.setBoolean(9, student.getGender());
            stmt.setString(10, student.getFirstGuardianName());
            stmt.setString(11, student.getFirstGuardianPhoneNumber());
            stmt.setString(12, student.getAvatar());
            stmt.setString(13, student.getSecondGuardianName());
            stmt.setString(14, student.getSecondGuardianPhoneNumber());

            if (student.getCreatedBy() != null) {
                stmt.setString(15, student.getCreatedBy().getId());
            } else {
                stmt.setNull(15, java.sql.Types.VARCHAR);
            }

            stmt.setString(16, student.getParentSpecialNote());
            stmt.setString(17, student.getSchool_id() != null ? student.getSchool_id().getId() : null);
            stmt.setString(18, student.getSchool_class_id() != null ? student.getSchool_class_id().getId() : null);

            // Thực thi
            int result = stmt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Student> getStudentByStatus(String status) {
        List<Student> students = new ArrayList<>();
        String sql = """
        SELECT s.*, 
               sch.schoolName AS schoolName, 
               sch.addressSchool AS addressSchool, 
               cls.class_name AS class_name,
               cls.grade_level AS grade_name
        FROM Students s
        LEFT JOIN Schools sch ON s.school_id = sch.id
        LEFT JOIN SchoolClasses cls ON s.school_class_id = cls.id
        WHERE s.[status] = ?
        ORDER BY s.id DESC
    """;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, status);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Student student = createStudent(resultSet);
                    if (student != null) {
                        students.add(student);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving students by status", e);
        }

        return students;
    }

    public int getPendingStudentCount() {
        String sql = "SELECT COUNT(*) AS total FROM Students WHERE status = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "đang chờ xử lý");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public List<Student> getAllStudents() {
        String sql = """

    SELECT s.*, 
           sch.schoolName AS schoolName,
           sch.addressSchool AS addressSchool,
           cls.class_name AS class_name,
           cls.grade_level AS grade_name
    FROM Students s
    LEFT JOIN Schools sch ON s.school_id = sch.id
    LEFT JOIN SchoolClasses cls ON s.school_class_id = cls.id
    ORDER BY s.id DESC
""";

        List<Student> listStudent = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student student = createStudent(resultSet);
                listStudent.add(student);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listStudent;
    }

    public Student getStudentByUserId(String userId) {
        String sql = "SELECT s.*, sc.schoolName, c.class_name "
                + "FROM Students s "
                + "LEFT JOIN Schools sc ON s.school_id = sc.id "
                + "LEFT JOIN SchoolClasses c ON s.school_class_id = c.id "
                + "WHERE s.user_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createStudent(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public boolean checkFirstGuardianPhoneNumberExists(String phoneNumber) {
        String sql = "SELECT COUNT(*) FROM [Students] WHERE first_guardian_phone_number = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, phoneNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkSecondGuardianPhoneNumberExists(String phoneNumber) {
        String sql = "SELECT COUNT(*) FROM [Students] WHERE second_guardian_phone_number = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, phoneNumber);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Student getStudentsById(String id) {
        String sql = """
        SELECT s.*, 
               sch.schoolName AS schoolName,
               sch.addressSchool AS addressSchool,
               cls.class_name AS class_name,
               cls.grade_level AS grade_name
        FROM Students s
        LEFT JOIN Schools sch ON s.school_id = sch.id
        LEFT JOIN SchoolClasses cls ON s.school_class_id = cls.id
        WHERE s.id = ?
    """;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createStudent(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Student getStudentByIdWithNames(String id) {
        String sql = """
        SELECT s.*, 
               sc.class_name, sc.grade_level, 
               sch.schoolName, sch.addressSchool, sch.email AS school_email
        FROM Students s
        LEFT JOIN SchoolClasses sc ON s.school_class_id = sc.id
        LEFT JOIN Schools sch ON s.school_id = sch.id
        WHERE s.id = ?
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Student student = new Student();
                student.setId(rs.getString("id"));
                student.setUserId(rs.getString("user_id"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setAddress(rs.getString("address"));
                student.setEmail(rs.getString("email"));
                student.setStatus(rs.getString("status"));
                student.setBirthday(rs.getDate("birthday"));
                student.setGender(rs.getBoolean("gender"));
                student.setFirstGuardianName(rs.getString("first_guardian_name"));
                student.setFirstGuardianPhoneNumber(rs.getString("first_guardian_phone_number"));
                student.setAvatar(rs.getString("avatar"));
                student.setSecondGuardianName(rs.getString("second_guardian_name"));
                student.setSecondGuardianPhoneNumber(rs.getString("second_guardian_phone_number"));
                student.setParentSpecialNote(rs.getString("parent_special_note"));

                // Truy vấn created_by (nếu cần)
                PersonnelDAO personnelDAO = new PersonnelDAO();
                student.setCreatedBy(personnelDAO.getPersonnel(rs.getString("created_by")));

                // Gán School
                Schools school = new Schools();
                school.setId(rs.getString("school_id"));
                school.setSchoolName(rs.getString("schoolName"));
                school.setAddressSchool(rs.getString("addressSchool"));
                school.setEmail(rs.getString("school_email"));
                student.setSchool_id(school);

                // Gán SchoolClass
                return student;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateStudent(Student student) {
        String updateStudentSQL = """
        UPDATE dbo.Students 
        SET first_guardian_name = ?, 
            first_guardian_phone_number = ?, 
            second_guardian_name = ?, 
            second_guardian_phone_number = ?, 
            address = ?, 
            school_id = ?, 
            school_class_id = ?, 
            parent_special_note = ?, 
            first_name = ?, 
            last_name = ?, 
            birthday = ?, 
            email = ?, 
            avatar = ? 
        WHERE id = ?
    """;

        String updateSchoolSQL = """
        UPDATE dbo.Schools
        SET addressSchool = ?
        WHERE id = ?
    """;

        String updateGradeLevelSQL = """
        UPDATE dbo.SchoolClasses
        SET grade_level = ?
        WHERE id = ?
    """;

        try {
            connection.setAutoCommit(false); // Bắt đầu transaction

            // 1. Cập nhật học sinh
            try (PreparedStatement ps = connection.prepareStatement(updateStudentSQL)) {
                ps.setString(1, student.getFirstGuardianName());
                ps.setString(2, student.getFirstGuardianPhoneNumber());
                ps.setString(3, student.getSecondGuardianName());
                ps.setString(4, student.getSecondGuardianPhoneNumber());
                ps.setString(5, student.getAddress());
                ps.setString(6, student.getSchool_id() != null ? student.getSchool_id().getId() : null);
                ps.setString(7, student.getSchool_class_id() != null ? student.getSchool_class_id().getId() : null);
                ps.setString(8, student.getParentSpecialNote());
                ps.setString(9, student.getFirstName());
                ps.setString(10, student.getLastName());
                if (student.getBirthday() != null) {
                    ps.setDate(11, new java.sql.Date(student.getBirthday().getTime()));
                } else {
                    ps.setNull(11, java.sql.Types.DATE);
                }
                ps.setString(12, student.getEmail());
                ps.setString(13, student.getAvatar());
                ps.setString(14, student.getId());
                ps.executeUpdate();
            }

            // 2. Cập nhật địa chỉ trường nếu có thông tin
            // 2. Cập nhật địa chỉ trường nếu có
            if (student.getSchool_id() != null && student.getSchool_id().getAddressSchool() != null) {
                try (PreparedStatement psSchool = connection.prepareStatement(updateSchoolSQL)) {
                    psSchool.setString(1, student.getSchool_id().getAddressSchool());
                    psSchool.setString(2, student.getSchool_id().getId());
                    psSchool.executeUpdate();
                }
            }

            // 3. Cập nhật grade_level nếu có
            if (student.getSchool_class_id() != null && student.getSchool_class_id().getGrade_level() != null) {
                try (PreparedStatement psGrade = connection.prepareStatement(updateGradeLevelSQL)) {
                    psGrade.setString(1, student.getSchool_class_id().getGrade_level());
                    psGrade.setString(2, student.getSchool_class_id().getId());
                    psGrade.executeUpdate();
                }
            }

            connection.commit(); // Commit nếu không có lỗi
            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                connection.rollback(); // Rollback nếu lỗi
            } catch (Exception rollbackEx) {
                rollbackEx.printStackTrace();
            }
            return false;
        } finally {
            try {
                connection.setAutoCommit(true); // Trả lại trạng thái mặc định
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean updateStudentClass(Student student) {
        String updateStudentSQL = """
        UPDATE dbo.Students 
        SET first_guardian_name = ?, 
            first_guardian_phone_number = ?, 
            second_guardian_name = ?, 
            second_guardian_phone_number = ?, 
            address = ?, 
            school_id = ?, 
            school_class_id = ?, 
            parent_special_note = ?, 
            first_name = ?, 
            last_name = ?, 
            birthday = ?, 
            email = ?, 
            avatar = ? 
        WHERE id = ?
    """;

        String updateSchoolSQL = """
        UPDATE dbo.Schools
        SET schoolName = ?, addressSchool = ?
        WHERE id = ?
    """;

        String updateClassSQL = """
        UPDATE dbo.SchoolClass
        SET class_name = ?
        WHERE id = ?
    """;

        try {
            connection.setAutoCommit(false); // Transaction

            // 1. Cập nhật thông tin học sinh
            try (PreparedStatement ps = connection.prepareStatement(updateStudentSQL)) {
                ps.setString(1, student.getFirstGuardianName());
                ps.setString(2, student.getFirstGuardianPhoneNumber());
                ps.setString(3, student.getSecondGuardianName());
                ps.setString(4, student.getSecondGuardianPhoneNumber());
                ps.setString(5, student.getAddress());
                ps.setString(6, student.getSchool_id() != null ? student.getSchool_id().getId() : null);
                ps.setString(7, student.getSchool_class_id() != null ? student.getSchool_class_id().getId() : null);
                ps.setString(8, student.getParentSpecialNote());
                ps.setString(9, student.getFirstName());
                ps.setString(10, student.getLastName());
                if (student.getBirthday() != null) {
                    ps.setDate(11, new java.sql.Date(student.getBirthday().getTime()));
                } else {
                    ps.setNull(11, java.sql.Types.DATE);
                }
                ps.setString(12, student.getEmail());
                ps.setString(13, student.getAvatar());
                ps.setString(14, student.getId());
                ps.executeUpdate();
            }

            // 2. Cập nhật tên và địa chỉ trường học nếu có
            if (student.getSchool_id() != null
                    && student.getSchool_id().getId() != null
                    && (student.getSchool_id().getSchoolName() != null || student.getSchool_id().getAddressSchool() != null)) {

                try (PreparedStatement psSchool = connection.prepareStatement(updateSchoolSQL)) {
                    psSchool.setString(1, student.getSchool_id().getSchoolName());
                    psSchool.setString(2, student.getSchool_id().getAddressSchool());
                    psSchool.setString(3, student.getSchool_id().getId());
                    psSchool.executeUpdate();
                }
            }

            // 3. Cập nhật tên lớp học nếu có
            if (student.getSchool_class_id() != null
                    && student.getSchool_class_id().getId() != null
                    && student.getSchool_class_id().getClassName() != null) {

                try (PreparedStatement psClass = connection.prepareStatement(updateClassSQL)) {
                    psClass.setString(1, student.getSchool_class_id().getClassName());
                    psClass.setString(2, student.getSchool_class_id().getId());
                    psClass.executeUpdate();
                }
            }

            connection.commit();
            return true;

        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                connection.rollback();
            } catch (Exception rollbackEx) {
                rollbackEx.printStackTrace();
            }
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean updateStudentStatus(String studentID, String status) {
        String sql = "UPDATE Students SET [status] = ? WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, status);
            preparedStatement.setString(2, studentID);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public Student getStudentById(String id) {
        String sql = """
        SELECT 
            s.*, 
            sc.schoolName, 
            sc.addressSchool, 
            c.class_name
        FROM Students s
        LEFT JOIN Schools sc ON s.school_id = sc.id
        LEFT JOIN SchoolClasses c ON s.school_class_id = c.id
        WHERE s.id = ?
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return createStudent(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy học sinh theo ID: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public int getSumStudentInClass(String classId) {
        String sql = "SELECT COUNT(*) AS total_students\n"
                + "FROM Class INNER JOIN\n"
                + "     classDetails ON Class.id = classDetails.class_id INNER JOIN\n"
                + "     Students ON classDetails.student_id = Students.id\n"
                + "WHERE Class.id = ?";

        int totalStudents = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, classId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    totalStudents = resultSet.getInt("total_students");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return totalStudents;
    }

    public List<Student> getStudentsByTeacherAndTimetable(String teacherId, String date) {
        String sql = """
        SELECT DISTINCT s.id, s.first_name, s.last_name, s.avatar
        FROM Students s
        INNER JOIN classDetails cd ON s.id = cd.student_id
        INNER JOIN Timetables t ON cd.class_id = t.class_id
        INNER JOIN dbo.Days d ON t.date_id = d.id
        WHERE t.teacher_id = ?
        AND CONVERT(date, d.date) = CONVERT(date, ?)
        """;
        List<Student> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, teacherId);
            preparedStatement.setString(2, date);

            // Debug logs
            System.out.println("Debug - Executing query with:");
            System.out.println("Debug - Teacher ID: " + teacherId);
            System.out.println("Debug - Date: " + date);

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getString("id"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setAvatar(rs.getString("avatar"));
                list.add(student);
            }

            // Debug log for results
            System.out.println("Debug - Total students found: " + list.size());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Student> getListStudentsByClass(String studentId, String classId) {

        List<Student> listStudents = new ArrayList<>();
        String sql = """
        SELECT s.*, 
               sch.schoolName, 
               sch.addressSchool AS addressSchool, 
               cls.class_name,
                    cls.grade_level AS grade_name
        FROM Students s
        JOIN classDetails c ON s.id = c.student_id
        LEFT JOIN Schools sch ON s.school_id = sch.id
        LEFT JOIN SchoolClasses cls ON s.school_class_id = cls.id
        WHERE c.class_id = ?
        """;

        if (studentId != null) {
            sql += "AND s.id != ?\n";
        }

        sql += "ORDER BY s.id";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, classId);
            if (studentId != null) {
                preparedStatement.setString(2, studentId);
            }
            // Debug logs
            System.out.println("Debug - Getting students for class: " + classId);
            System.out.println("Debug - SQL: " + sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student student = createStudent(resultSet); // đã có addressSchool trong createStudent
                listStudents.add(student);
            }
            // Debug log for results
            System.out.println("Debug - Total students found: " + listStudents.size());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listStudents;
    }

    public boolean addStudentToClass(String pupilId, String classId) {
        String sql = "INSERT INTO [dbo].[classDetails]\n"
                + "           ([student_id]\n"
                + "           ,[class_id])\n"
                + "     VALUES\n"
                + "           (?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, pupilId);
            preparedStatement.setString(2, classId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }

    public Student getStudentById2(String studentId) {
        String sql = "SELECT s.id AS student_id, "
                + "       s.school_id, sch.schoolName AS school_name, "
                + "       s.school_class_id, sc.class_name AS class_name "
                + "FROM Students s "
                + "LEFT JOIN Schools sch ON s.school_id = sch.id "
                + "LEFT JOIN SchoolClasses sc ON s.school_class_id = sc.id "
                + "WHERE s.id = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, studentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Student student = new Student();
                    student.setId(rs.getString("student_id"));

                    // Gán trường học
                    Schools school = new Schools();
                    school.setId(rs.getString("school_id"));
                    school.setSchoolName(rs.getString("school_name"));
                    student.setSchool_id(school);

                    // Gán lớp học
                    SchoolClass schoolClass = new SchoolClass();
                    schoolClass.setId(rs.getString("school_class_id"));
                    schoolClass.setClassName(rs.getString("class_name"));
                    student.setSchool_class_id(schoolClass);

                    return student;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in getStudentById2: " + e.getMessage());
        }
        return null;
    }

    public List<StudentWithClassDTO> getStudentWithClassById(String studentId) {
        List<StudentWithClassDTO> list = new ArrayList<>();
        String sql = """
        SELECT 
            s.id,
            s.avatar,
            s.first_name,
            s.last_name,
            cd.class_id,
            cl.name
        FROM Students s
        JOIN classDetails cd ON cd.student_id = s.id
        JOIN Class cl ON cl.id = cd.class_id
        WHERE s.id = ? AND s.status = N'đang theo học';
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, studentId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                StudentWithClassDTO s = new StudentWithClassDTO();
                s.setId(rs.getString("id"));
                s.setAvatar(rs.getString("avatar"));
                s.setFirstName(rs.getString("first_name"));
                s.setLastName(rs.getString("last_name"));
                s.setClassId(rs.getString("class_id"));
                s.setClassName(rs.getString("name"));
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

public List<Student> getStudentByClass(String classId) {
        String sql = "SELECT *\n"
                + "FROM     Class INNER JOIN\n"
                + "                  classDetails ON Class.id = classDetails.class_id INNER JOIN\n"
                + "                  Students ON classDetails.student_id = Students.id INNER JOIN\n"
                + "                  SchoolYears ON Class.school_year_id = SchoolYears.id\n"
                + "\t\t\t\t  where class_id= ?";
        List<Student> listStudents = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, classId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                PersonnelDAO personnelDAO = new PersonnelDAO();
                student.setId(resultSet.getString("student_id"));
                student.setUserId(resultSet.getString("user_id"));
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));
                student.setAddress(resultSet.getString("address"));
                student.setEmail(resultSet.getString("email"));
                student.setStatus(resultSet.getString("status"));
                student.setBirthday(resultSet.getDate("birthday"));
                student.setGender(resultSet.getBoolean("gender"));
                student.setFirstGuardianName(resultSet.getString("first_guardian_name"));
                student.setFirstGuardianPhoneNumber(resultSet.getString("first_guardian_phone_number"));
                student.setAvatar(resultSet.getString("avatar"));
                student.setSecondGuardianName(resultSet.getString("second_guardian_name"));
                student.setSecondGuardianPhoneNumber(resultSet.getString("second_guardian_phone_number"));
                Personnel personnel = personnelDAO.getPersonnel(resultSet.getString("created_by"));
                student.setCreatedBy(personnel);
                student.setParentSpecialNote(resultSet.getString("parent_special_note"));
                listStudents.add(student);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return listStudents;
    }

    // Add this method to check data
    public void checkDataForAttendance(String teacherId, String date) {
        try {
            // Check Timetables
            String timetableSql = "SELECT COUNT(*) as count FROM Timetables WHERE teacher_id = ?";
            PreparedStatement ps1 = connection.prepareStatement(timetableSql);
            ps1.setString(1, teacherId);
            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()) {
                System.out.println("Debug - Number of timetables for teacher: " + rs1.getInt("count"));
            }

            // Check Days
            String daysSql = "SELECT COUNT(*) as count FROM Days WHERE date = ?";
            PreparedStatement ps2 = connection.prepareStatement(daysSql);
            ps2.setString(1, date);
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) {
                System.out.println("Debug - Number of days matching date: " + rs2.getInt("count"));
            }

            // Check classDetails
            String classDetailsSql = """
                SELECT COUNT(*) as count 
                FROM classDetails cd
                JOIN Timetables t ON cd.class_id = t.class_id
                WHERE t.teacher_id = ?
            """;
            PreparedStatement ps3 = connection.prepareStatement(classDetailsSql);
            ps3.setString(1, teacherId);
            ResultSet rs3 = ps3.executeQuery();
            if (rs3.next()) {
                System.out.println("Debug - Number of class details for teacher: " + rs3.getInt("count"));
            }

        } catch (Exception e) {
            System.out.println("Debug - Error checking data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Student> getStudentNonUserId() {
        List<Student> list = new ArrayList<>();
        String sql = """
        SELECT s.*, 
               sc.schoolName, 
               sc.addressSchool, 
               c.class_name,
               g.name AS grade_name
        FROM Students s
        LEFT JOIN Schools sc ON s.school_id = sc.id
        LEFT JOIN SchoolClasses c ON s.school_class_id = c.id
        LEFT JOIN Grades g ON c.grade_level = g.id
        WHERE s.user_id IS NULL AND s.status = N'đang theo học'
        ORDER BY s.id DESC
    """;

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Student student = createStudent(rs);
                list.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

//     public List<Student> getStudentsWithoutClass(String schoolYearId) {
//        List<Student> listStudent = new ArrayList<>();
//        String sql = "Select  Students.id    FROM  Students left  JOIN\n"
//                + "                 classDetails ON Students.id = classDetails.student_id  left  JOIN\n"
//                + "                Class ON Class.id = classDetails.class_id\n"
//                + "               where  Students.status= N'đang theo học' and class_id is null \n"
//                + "\t\t\t  union  \n"
//                + "\t\t\t  Select  distinct student_id  from \n"
//                + "\t\t\t  classDetails join Class on classDetails.class_id = Class.id\n"
//                + "               where   pupil_id not in (Select pupil_id\n"
//                + "\t\t\t   from classDetails  join Class on classDetails.class_id = Class.id\n"
//                + "\t\t\t   where school_year_id = ? )";
//
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, schoolYearId);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                listStudent.add(getStudentsById(resultSet.getString(1)));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return listStudent;
//    }
    public List<Student> getStudentsByClassId(String classId) {
        List<Student> students = new ArrayList<>();

        String sql = """
        SELECT s.*, 
               sch.schoolName, 
               sch.addressSchool, 
               c.class_name,
               g.name AS grade_name
        FROM classDetails cd
        JOIN Students s ON cd.student_id = s.id
        LEFT JOIN Schools sch ON s.school_id = sch.id
        LEFT JOIN SchoolClasses c ON s.school_class_id = c.id
        LEFT JOIN Grades g ON c.grade_level = g.id
        WHERE cd.class_id = ?
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, classId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Student student = createStudent(rs);
                students.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    public List<StudentWithClassDTO> getStudentsWithClassInfo() {
        List<StudentWithClassDTO> list = new ArrayList<>();

        String sql = """
      SELECT 
          s.id,
          s.avatar,
          s.first_name,
          s.last_name,
          STRING_AGG(cd.class_id,', ') AS class_id,
          STRING_AGG(cl.name, ',  ') AS class_name
        FROM Students s
        JOIN classDetails cd ON cd.student_id = s.id
        JOIN Class cl ON cl.id = cd.class_id
        WHERE s.status =  N'đang theo học'
        GROUP BY s.id, s.avatar, s.first_name, s.last_name;
    """;

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                StudentWithClassDTO dto = new StudentWithClassDTO();
                dto.setId(rs.getString("id"));
                dto.setAvatar(rs.getString("avatar"));
                dto.setFirstName(rs.getString("first_name"));
                dto.setLastName(rs.getString("last_name"));
                dto.setClassId(rs.getString("class_id"));
                dto.setClassName(rs.getString("class_name"));
                list.add(dto);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching students with class info: " + e.getMessage());
            e.printStackTrace();
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            closeConnection(); // Nếu muốn đóng sau khi gọi
        }

        return list;
    }

}
//     public List<Student> getStudentsWithoutClass(String schoolYearId) {
//        List<Student> listStudent = new ArrayList<>();
//        String sql = "Select  Students.id    FROM  Students left  JOIN\n"
//                + "                 classDetails ON Students.id = classDetails.student_id  left  JOIN\n"
//                + "                Class ON Class.id = classDetails.class_id\n"
//                + "               where  Students.status= N'đang theo học' and class_id is null \n"
//                + "\t\t\t  union  \n"
//                + "\t\t\t  Select  distinct student_id  from \n"
//                + "\t\t\t  classDetails join Class on classDetails.class_id = Class.id\n"
//                + "               where   pupil_id not in (Select pupil_id\n"
//                + "\t\t\t   from classDetails  join Class on classDetails.class_id = Class.id\n"
//                + "\t\t\t   where school_year_id = ? )";
//
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setString(1, schoolYearId);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                listStudent.add(getStudentsById(resultSet.getString(1)));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return listStudent;
//    }

