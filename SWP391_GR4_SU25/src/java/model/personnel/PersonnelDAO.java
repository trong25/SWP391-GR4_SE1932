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
 * Lớp PersonnelDAO dùng để thao tác với bảng Personnel trong cơ sở dữ liệu
 * 
 * Chịu trách nhiệm xử lý công việc tạo nhân viên, lấy trạng thái, lấy tất cả nhân viên, cập nhật trong hệ thống.
 * Được gọi bởi servlet liên quan đến người dùng
 * 
 * Ví dụ: createPersonnel, getAllPersonnels, getPersonnelByUserId, getAllStatus,...
 * 
 * @author TrongNV
 * @version 1.0
 */




public class PersonnelDAO extends DBContext {
    
     //hàm tạo nhân viên
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

//Hàm lấy tất cả nhân viên trong cơ sở dữ liệu

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
                role.setId(resultSet.getString("id")); // ✅ Đúng kiểu int
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


  


           
        public List<Personnel> getAvailableTeachers(String schoolYearId) {

    String sql = "SELECT t.*, s.schoolName, s.addressSchool " +
                 "FROM Personnels t " +
                 "LEFT JOIN Class c ON t.id = c.teacher_id AND c.school_year_id = ? " +
                 "LEFT JOIN Schools s ON t.school_id = s.id " +
                 "WHERE c.teacher_id IS NULL " +
                 "AND t.id LIKE 'GV%' " +
                 "AND t.status LIKE N'đang làm việc%';";

    List<Personnel> teachers = new ArrayList<>();
    try {
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, schoolYearId);
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            Personnel teacher = createPersonnel(resultSet);
            teachers.add(teacher);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return teachers;
}



    public Personnel getPersonnelById(String id) {
    String sql = """
        SELECT p.*, s.schoolName, s.addressSchool
        FROM Personnels p
        LEFT JOIN Schools s ON p.school_id = s.id
        WHERE p.id = ?
    """;

    try (PreparedStatement ps = connection.prepareStatement(sql)) {
        ps.setString(1, id);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            Personnel personnel = new Personnel();
            personnel.setId(rs.getString("id"));
            personnel.setFirstName(rs.getString("first_name"));
            personnel.setLastName(rs.getString("last_name"));
            personnel.setGender(rs.getBoolean("gender"));
            personnel.setBirthday(rs.getDate("birthday"));
            personnel.setAddress(rs.getString("address"));
            personnel.setEmail(rs.getString("email"));
            personnel.setPhoneNumber(rs.getString("phone_number"));
            personnel.setRoleId(rs.getInt("role_id"));
            personnel.setStatus(rs.getString("status"));
            personnel.setAvatar(rs.getString("avatar"));
            personnel.setUserId(rs.getString("user_id"));
            personnel.setSchool_id(rs.getString("school_id"));
            personnel.setSchool_class_id(rs.getString("school_class_id"));
            personnel.setSchoolName(rs.getString("schoolName"));
            personnel.setAddressSchool(rs.getString("addressSchool"));
            return personnel;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;
}
public List<Personnel> getFreeTeacherByDate(String dayId){
        String sql = "SELECT p.* FROM Personnels p WHERE p.id NOT IN (\n" +
                "    SELECT t.teacher_id\n" +
                "    FROM Timetables t\n" +
                "    WHERE t.date_id = ?\n" +
                ") and p.id like 'TEA%';";
        List<Personnel> teacherList = new ArrayList<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, dayId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                teacherList.add(createPersonnel(resultSet));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return teacherList;
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

    public Personnel getTeacherByClassAndSchoolYear(String classId, String schoolYearId) {
        String sql = "Select teacher_id from class where id= ? and school_year_id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, classId);
            preparedStatement.setString(2, schoolYearId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getPersonnel(resultSet.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
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
                Personnel teacher = new Personnel();
                teacher.setId(rs.getString("id"));
                teacher.setFirstName(rs.getString("first_name"));
                teacher.setLastName(rs.getString("last_name"));
                teacher.setGender(rs.getInt("gender") == 1);
                teacher.setBirthday(rs.getDate("birthday"));
                teacher.setAddress(rs.getString("address"));
                teacher.setEmail(rs.getString("email"));
                teacher.setPhoneNumber(rs.getString("phone_number"));
                teacher.setRoleId(rs.getInt("role_id"));
                teacher.setStatus(rs.getString("status"));
                teacher.setAvatar(rs.getString("avatar"));
                teacher.setUserId(rs.getString("user_id"));
                teacher.setSchool_id(rs.getString("school_id"));
                teacher.setSchool_class_id(rs.getString("school_class_id"));
                teacher.setSchoolName(rs.getString("schoolName")); // ✅ Thêm trường tên trường
                teacher.setAddressSchool(rs.getString("addressSchool")); // ✅ Thêm trường địa chỉ trường

                return teacher;
            }
        }
    } catch (SQLException e) {
        System.out.println("Error in getHomeroomTeacherByClassId: " + e.getMessage());
    }
    return null;
}
}

       