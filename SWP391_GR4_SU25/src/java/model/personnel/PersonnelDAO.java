/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

        return person;
    }

    public List<Personnel> getAllPersonnels() {
        String sql = "select * from [Personnels] order by id desc";
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
                person.setSchool_id(resultSet.getString("school_id"));
                person.setSchool_class_id(resultSet.getString("school_class_id"));
                persons.add(person);
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
            while (resultSet.next()) {
                personnel.setId(resultSet.getString(8)); /// 8 is position of personnel Id on join of two table
                personnel.setFirstName(resultSet.getString("first_name"));
                personnel.setLastName(resultSet.getString("last_name"));
                personnel.setGender(resultSet.getBoolean("gender"));
                personnel.setBirthday(resultSet.getDate("birthday"));
                personnel.setEmail(resultSet.getString("email"));
                personnel.setAddress(resultSet.getString("address"));
                personnel.setPhoneNumber(resultSet.getString("phone_number"));
                personnel.setRoleId(resultSet.getInt("role_id"));
                personnel.setStatus(resultSet.getString("status"));
                personnel.setAvatar(resultSet.getString("avatar"));
                personnel.setUserId(resultSet.getString("user_id"));
                personnel.setSchool_id(resultSet.getString("school_id"));
                personnel.setSchool_class_id(resultSet.getString("school_class_id"));
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
            while (resultSet.next()) {

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
            System.out.println("Executing query for userId: " + userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                personnel = new Personnel();
                personnel.setId(resultSet.getString("id"));
                personnel.setFirstName(resultSet.getString("first_name"));
                personnel.setLastName(resultSet.getString("last_name"));
                personnel.setGender(resultSet.getBoolean("gender"));
                personnel.setBirthday(resultSet.getDate("birthday"));
                personnel.setEmail(resultSet.getString("email"));
                personnel.setAddress(resultSet.getString("address"));
                personnel.setPhoneNumber(resultSet.getString("phone_number"));
                personnel.setRoleId(resultSet.getInt("role_id"));
                personnel.setStatus(resultSet.getString("status"));
                personnel.setAvatar(resultSet.getString("avatar"));
                personnel.setUserId(resultSet.getString("user_id"));
                personnel.setSchool_id(resultSet.getString("school_id"));
                personnel.setSchool_class_id(resultSet.getString("school_class_id"));
                personnel.setSchoolName(resultSet.getString("schoolName")); // Khớp với cột schoolName
                personnel.setClassName(resultSet.getString("class_name"));  // Khớp với cột class_name
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.err.println("SQL Error for userId: " + userId + " - " + e.getMessage());
            e.printStackTrace();
        }
        return personnel;
    }

    public List<Personnel> getByStudentId(String studentId) {
        String sql = """
                     -- Cách 3: Kết hợp cả hai - lấy tất cả giáo viên với đầy đủ thông tin
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
            statement.setObject(1, studentId);
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
                Personnel p = new Personnel();
                p.setId(rs.getString(1));
                p.setFirstName(rs.getString(2));
                p.setLastName(rs.getString(3));
                p.setGender(rs.getBoolean(4));
                p.setBirthday(rs.getDate(5));
                p.setAddress(rs.getString(6));
                p.setEmail(rs.getString(7));
                p.setPhoneNumber(rs.getString(8));
                p.setRoleId(rs.getInt(9));
                p.setStatus(rs.getString(10));
                p.setAvatar(rs.getString(11));
                p.setUserId(rs.getString(12));
                list.add(p);
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
                Personnel p = new Personnel();
                p.setId(rs.getString(1));
                p.setFirstName(rs.getString(2));
                p.setLastName(rs.getString(3));
                p.setGender(rs.getBoolean(4));
                p.setBirthday(rs.getDate(5));
                p.setAddress(rs.getString(6));
                p.setEmail(rs.getString(7));
                p.setPhoneNumber(rs.getString(8));
                p.setRoleId(rs.getInt(9));
                p.setStatus(rs.getString(10));
                p.setAvatar(rs.getString(11));
                p.setUserId(rs.getString(12));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean updatePersonnelStatus(String pId, String status) {
        String sql = "UPDATE [dbo].[Personnels]\n"
                + "   SET [status] = ? \n"
                + " WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, status);
            preparedStatement.setString(2, pId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e);
        }
        return false;
    }
}
