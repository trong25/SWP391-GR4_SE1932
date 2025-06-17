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

}
