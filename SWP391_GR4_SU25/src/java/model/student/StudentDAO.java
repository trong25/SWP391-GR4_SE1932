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
import model.school.Schools;
import model.schoolclass.SchoolClass;
import utils.DBContext;

/**
 *
 * @author MSI
 */
public class StudentDAO extends DBContext {

    private Student createStudent(ResultSet resultSet) throws SQLException {
        try {
            PersonnelDAO personnelDAO = new PersonnelDAO();
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

            // Tạo và gán School object
            Schools school = new Schools();
            school.setId(resultSet.getString("school_id"));
            school.setSchoolName(resultSet.getString("schoolName"));
            student.setSchool_id(school);

            // Tạo và gán SchoolClass object
            SchoolClass schoolClass = new SchoolClass();
            schoolClass.setId(resultSet.getString("school_class_id"));
            schoolClass.setClassName(resultSet.getString("class_name"));
            student.setSchool_class_id(schoolClass);

            return student;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Student getLatest() {
        String sql = "SELECT TOP 1 s.*, sc.schoolName, c.class_name "
                + "FROM Students s "
                + "LEFT JOIN Schools sc ON s.school_id = sc.id "
                + "LEFT JOIN SchoolClasses c ON s.school_class_id = c.id "
                + "ORDER BY s.id DESC";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createStudent(resultSet);
            }
        } catch (Exception e) {
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
        String sql = """
        SELECT s.*, 
               sch.schoolName AS schoolName, 
               cls.class_name AS class_name
        FROM Students s
        LEFT JOIN Schools sch ON s.school_id = sch.id
        LEFT JOIN SchoolClasses cls ON s.school_class_id = cls.id
        WHERE s.[status] = ?
        ORDER BY s.id DESC
    """;

        List<Student> listStudents = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, status);  // tránh SQL injection
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student student = createStudent(resultSet);
                listStudents.add(student);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listStudents;
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
               cls.class_name AS class_name
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
        String sql = "SELECT * FROM Students WHERE user_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Student student = new Student();
                student = createStudent(resultSet);
                return student;
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
               cls.class_name AS class_name
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
                SchoolClass schoolClass = new SchoolClass();
                schoolClass.setId(rs.getString("school_class_id"));
                schoolClass.setClassName(rs.getString("class_name"));
                schoolClass.setGrade_level(rs.getString("grade_level"));
                student.setSchool_class_id(schoolClass);

                return student;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateStudent(Student student) {
        String sql = """
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

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
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

            int affectedRows = ps.executeUpdate();
            return affectedRows > 0;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public Student getStudentById(String id) {
        String sql = "SELECT * FROM Students WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return createStudent(rs);
            }
        } catch (SQLException e) {
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
        String sql = "SELECT DISTINCT Students.id, Students.first_name, Students.last_name, Students.avatar\n"
                + "FROM Students\n"
                + "JOIN classDetails ON Students.id = classDetails.Students_id\n"
                + "JOIN Timetables ON classDetails.class_id = Timetables.class_id\n"
                + "JOIN dbo.Days ON Timetables.date_id = dbo.Days.id\n"
                + "WHERE Timetables.teacher_id = ?\n"
                + "AND ? = dbo.Days.date;";
        List<Student> list = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, teacherId);
            preparedStatement.setString(2, date);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Student student = new Student();
                student.setId(rs.getString("id"));
                student.setFirstName(rs.getString("first_name"));
                student.setLastName(rs.getString("last_name"));
                student.setAvatar(rs.getString("avatar"));
                list.add(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Student> getListStudentsByClass(String studentId, String classId) {
        List<Student> listStudents = new ArrayList<>();

        String sql = "select * from Students p join classDetails c on p.id = c.student_id \n"
                + "where class_id= '" + classId + "'";
        if (studentId != null) {
            sql += " and student_id != '" + studentId + "'";
        }
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Student student = new Student();
                student = createStudent(resultSet);
                listStudents.add(student);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listStudents;
    }

    public static void main(String[] args) {
    StudentDAO studentDAO = new StudentDAO();

    int a = studentDAO.getPendingStudentCount();  // đúng kiểu trả về
    System.out.println("Số học sinh đang chờ xử lý: " + a);
}

}
