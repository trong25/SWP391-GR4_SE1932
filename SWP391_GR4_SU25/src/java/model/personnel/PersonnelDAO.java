package model.personnel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.role.Role;
import utils.DBContext;

/**
 *
 * @author MSI
 */
public class PersonnelDAO extends DBContext {

    private Personnel createPersonnel(ResultSet resultSet) throws SQLException {
        Personnel person = new Personnel();
        person.setId(resultSet.getString("id"));
        person.setFirstName(resultSet.getString("first_name"));
        person.setLastName(resultSet.getString("last_name"));
        person.setGender(resultSet.getBoolean("gender"));
        person.setBirthday(resultSet.getDate("birthday"));
        person.setEmail(resultSet.getString("email"));
        person.setAddress(resultSet.getString("address"));
        person.setPhoneNumber(resultSet.getString("phone_number"));
        person.setRoleId(resultSet.getInt("role_id"));
        person.setStatus(resultSet.getString("status"));
        person.setAvatar(resultSet.getString("avatar"));
        person.setUserId(resultSet.getString("user_id"));
        person.setSchool_id(resultSet.getString("school_id"));
        person.setSchool_class_id(resultSet.getString("school_class_id"));

        // Lấy thêm thông tin từ bảng Schools (nếu có trong câu truy vấn)
        try {
            person.setSchoolName(resultSet.getString("schoolName"));
        } catch (SQLException e) {
            // Trường hợp không có cột schoolName trong ResultSet
        }

        try {
            person.setAddressSchool(resultSet.getString("addressSchool"));
        } catch (SQLException e) {
            // Trường hợp không có cột addressSchool trong ResultSet
        }

        return person;
    }

    public List<Personnel> getAllPersonnels() {
        String sql = "select * from [Personnels] order by id desc";
        List<Personnel> persons = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                persons.add(createPersonnel(resultSet));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return persons;
    }

    public Personnel getPersonnelByUserId(String userId) {
        String sql = "select * from [User] u join Personnels p on u.id=p.user_id \n"
                + "where u.id = ?";
        Personnel personnel = new Personnel();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                personnel = createPersonnel(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return personnel;
    }

    public Personnel getPersonnel(String id) {
        String sql = "select * from [Personnels] where id like ? ";
        Personnel person = new Personnel();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, "%" + id + "%");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                person = createPersonnel(resultSet);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return person;
    }

    public int getPendingTeacherCount() {
        String sql = "SELECT COUNT(*) AS total FROM Personnels WHERE status = ? AND role_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, "đang chờ xử lý");
            preparedStatement.setInt(2, 3); // role_id của giáo viên
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("total");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;

    }

    public List<Role> getAllPersonnelRole() {
        String sql = "select DISTINCT r.id, r.description from Roles r where id != 4";
        List<Role> roles = new ArrayList<>();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Role role = new Role();
                role.setId(resultSet.getInt("id")); // ✅ Đúng kiểu int
                role.setDescription(resultSet.getString("description"));
                roles.add(role);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return roles;
    }

    public List<String> getAllStatus() {
        String sql = "select distinct status from Personnels";
        List<String> status = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                status.add(resultSet.getString("status"));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return status;
    }

    public List<Personnel> getPersonnelByStatus(String status) {
        String sql = " Select * from Personnels where [status] = N'" + status + "' order by id desc";
        List<Personnel> persons = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Personnel person = new Personnel();
                person.setId(resultSet.getString("id"));
                person.setFirstName(resultSet.getString("first_name"));
                person.setLastName(resultSet.getString("last_name"));
                person.setGender(resultSet.getBoolean("gender"));
                person.setBirthday(resultSet.getDate("birthday"));
                person.setEmail(resultSet.getString("email"));
                person.setAddress(resultSet.getString("address"));
                person.setPhoneNumber(resultSet.getString("phone_number"));
                person.setRoleId(resultSet.getInt("role_id"));
                person.setStatus(resultSet.getString("status"));
                person.setAvatar(resultSet.getString("avatar"));
                person.setUserId(resultSet.getString("user_id"));
                persons.add(person);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return persons;
    }

    public List<Personnel> getPersonnelByRole(int role) {
        String sql = "select * from [Personnels] where role_id = ? order by id desc";
        List<Personnel> persons = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, role);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Personnel person = new Personnel();
                person.setId(resultSet.getString("id"));
                person.setFirstName(resultSet.getString("first_name"));
                person.setLastName(resultSet.getString("last_name"));
                person.setGender(resultSet.getBoolean("gender"));
                person.setBirthday(resultSet.getDate("birthday"));
                person.setEmail(resultSet.getString("email"));
                person.setAddress(resultSet.getString("address"));
                person.setPhoneNumber(resultSet.getString("phone_number"));
                person.setRoleId(resultSet.getInt("role_id"));
                person.setStatus(resultSet.getString("status"));
                person.setAvatar(resultSet.getString("avatar"));
                person.setUserId(resultSet.getString("user_id"));
                persons.add(person);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return persons;
    }

    public List<Personnel> getPersonnelByIdNameRoleStatus(String status, String role) {
        String sql = " Select * from Personnels where 1=1";

        if (status != null && !status.isEmpty()) {
            sql += " AND status = N'" + status + "'";
        }
        if (role != null && !role.isEmpty()) {
            int xrole = Integer.parseInt(role);
            sql += " AND role_id = " + xrole + "";
        }
        List<Personnel> persons = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Personnel person = new Personnel();
                person.setId(resultSet.getString("id"));
                person.setFirstName(resultSet.getString("first_name"));
                person.setLastName(resultSet.getString("last_name"));
                person.setGender(resultSet.getBoolean("gender"));
                person.setBirthday(resultSet.getDate("birthday"));
                person.setEmail(resultSet.getString("email"));
                person.setAddress(resultSet.getString("address"));
                person.setPhoneNumber(resultSet.getString("phone_number"));
                person.setRoleId(resultSet.getInt("role_id"));
                person.setStatus(resultSet.getString("status"));
                person.setAvatar(resultSet.getString("avatar"));
                person.setUserId(resultSet.getString("user_id"));
                persons.add(person);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return persons;
    }

    public List<Personnel> getPersonnelByNameOrId(String search) {
        String sql = "select * from [Personnels] where (last_name+' '+ first_name like N'%" + search + "%' or id like '%" + search + "%' ) ";
        List<Personnel> persons = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Personnel person = new Personnel();
                person.setId(resultSet.getString("id"));
                person.setFirstName(resultSet.getString("first_name"));
                person.setLastName(resultSet.getString("last_name"));
                person.setGender(resultSet.getBoolean("gender"));
                person.setBirthday(resultSet.getDate("birthday"));
                person.setEmail(resultSet.getString("email"));
                person.setAddress(resultSet.getString("address"));
                person.setPhoneNumber(resultSet.getString("phone_number"));
                person.setRoleId(resultSet.getInt("role_id"));
                person.setStatus(resultSet.getString("status"));
                person.setAvatar(resultSet.getString("avatar"));
                person.setUserId(resultSet.getString("user_id"));
                persons.add(person);
            }
        } catch (Exception e) {
            System.out.println("nction");
        }
        return persons;
    }



public boolean updatePerson(Personnel person) {
        String sql = "UPDATE Personnels SET first_name = ?, last_name = ?, gender = ?, address = ?, email = ?, phone_number = ? WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, person.getFirstName());
            stmt.setString(2, person.getLastName());
            stmt.setBoolean(3, person.isGender());
            stmt.setString(4, person.getAddress());
            stmt.setString(5, person.getEmail());
            stmt.setString(6, person.getPhoneNumber());
            stmt.setString(7, person.getUserId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkPhoneNumberExists(String phoneNumber) {
        String sql = "SELECT COUNT(*) FROM [Personnels] WHERE phone_number = ?";
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

    public Personnel getTeacherInfoByUserId(String userId) {
        String sql = "SELECT p.*, "
                + "sc.class_name, sc.grade_level, "
                + "sch.schoolName "
                + "FROM Personnels p "
                + "LEFT JOIN SchoolClasses sc ON p.school_class_id = sc.id "
                + "LEFT JOIN Schools sch ON p.school_id = sch.id "
                + "WHERE p.user_id = ?";
        Personnel personnel = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                personnel = createPersonnel(resultSet);
                personnel.setClassName(resultSet.getString("class_name"));
            }
        } catch (SQLException e) {
            System.err.println("SQL Error for userId: " + userId + " - " + e.getMessage());
            e.printStackTrace();
        }
        return personnel;
    }

    public List<Personnel> getByStudentId(String studentId) {
        String sql = """
                     SELECT DISTINCT 
                         p.id,
                         p.first_name,
                         p.last_name,
                         p.gender,
                         p.birthday,
                         p.address,
                         p.email,
                         p.phone_number,
                         p.role_id,
                         p.status,
                         p.avatar,
                         p.user_id,
                         CASE 
                             WHEN c.teacher_id = p.id THEN 'Giáo viên chủ nhiệm'
                             ELSE 'Giáo viên bộ môn'
                         END AS teacher_role,
                         c.name AS class_name
                     FROM Students s
                         INNER JOIN classDetails cd ON s.id = cd.student_id
                         INNER JOIN Class c ON cd.class_id = c.id
                         LEFT JOIN Timetables t ON c.id = t.class_id
                         INNER JOIN Personnels p ON (c.teacher_id = p.id OR t.teacher_id = p.id)
                     WHERE s.id = ?
                     ORDER BY p.first_name, p.last_name;
                     """;
        List<Personnel> persons = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, studentId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Personnel person = createPersonnel(resultSet);
                person.setClassName(resultSet.getString("class_name"));
                persons.add(person);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return persons;
    }
    
    public List<Personnel> getPersonnelNonUserId() {
        List<Personnel> list = new ArrayList<>();
        String sql = "SELECT * \n"
                + "FROM Personnels \n"
                + "WHERE user_id IS NULL \n"
                + "  AND status = N'đang làm việc';";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(createPersonnel(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
        
    public List<Personnel> getPersonnelByRoleAndNonUserId(int id) {
        List<Personnel> list = new ArrayList<>();
        String sql = "SELECT * FROM Personnels where role_id=? and user_id is null and status = N'đang làm việc'";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(createPersonnel(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    public List<Personnel> getAvailableTeachers(String schoolYearId) {
        String sql = "SELECT t.*, s.schoolName, s.addressSchool " +
                 "FROM Personnels t " +
                 "LEFT JOIN Class c ON t.id = c.teacher_id AND c.school_year_id = ? " +
                 "LEFT JOIN Schools s ON t.school_id = s.id " +
                 "WHERE c.teacher_id IS NULL " +
                 "AND t.id LIKE 'TEA%' " +
                 "AND t.status LIKE N'đang làm việc%'";
        List<Personnel> teachers = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, schoolYearId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                teachers.add(createPersonnel(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teachers;
    }
        
    public Personnel getHomeroomTeacherByClassId(String classId) {
        String sql = "SELECT p.*, s.schoolName, s.addressSchool, sc.id AS school_class_id " +
                 "FROM Class c " +
                 "JOIN Personnels p ON c.teacher_id = p.id " +
                 "LEFT JOIN Schools s ON p.school_id = s.id " +
                 "LEFT JOIN SchoolClasses sc ON p.school_class_id = sc.id " +
                 "WHERE c.id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, classId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return createPersonnel(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error in getHomeroomTeacherByClassId: " + e.getMessage());
        }
        return null;
    }
}
}

