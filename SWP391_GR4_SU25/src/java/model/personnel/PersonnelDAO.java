/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.personnel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.LinkedHashMap;

import java.util.Date;

import java.util.List;

import model.Salaries.Salary;
import java.util.Map;
import model.role.Role;
import utils.DBContext;

/**
 * Lớp PersonnelDAO dùng để thao tác với bảng Personnel trong cơ sở dữ liệu
 *
 * Chịu trách nhiệm xử lý công việc tạo nhân viên, lấy trạng thái, lấy tất cả
 * nhân viên, cập nhật trong hệ thống. Được gọi bởi servlet liên quan đến người
 * dùng
 *
 * Ví dụ: createPersonnel, getAllPersonnels, getPersonnelByUserId,
 * getAllStatus,...
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

        person.setSpecialization(resultSet.getString("specialization"));
        person.setQualification(resultSet.getString("qualification"));
        person.setTeaching_years(resultSet.getInt("teaching_years"));
        person.setAchievements(resultSet.getString("achievements"));
        person.setCv_file(resultSet.getString("cv_file"));

        //  Lấy thêm thông tin từ bảng Schools (nếu có trong câu truy vấn)

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


    public List<Personnel> getAllPersonnelsWithSalary() {
    List<Personnel> persons = new ArrayList<>();
    String sql = """
        SELECT 
            p.*,
            s.id AS salary_id,
            s.salary_month,
            s.salary_year,
            s.base_salary,
            s.total_salary,
            s.payment_status,
            s.payment_date
        FROM Personnels p
        LEFT JOIN Salaries s ON p.id = s.personnel_id
        ORDER BY p.id, s.salary_year DESC, s.salary_month DESC
    """;

    try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
        Personnel person = null;
        String lastId = null;

        while (rs.next()) {
            String currentId = rs.getString("id");
            if (!currentId.equals(lastId)) {
                if (person != null) {
                    persons.add(person);
                }
                person = new Personnel();
                person.setId(currentId);
                person.setFirstName(rs.getString("first_name"));
                person.setLastName(rs.getString("last_name"));
                person.setGender(rs.getBoolean("gender"));
                person.setBirthday(rs.getDate("birthday"));
                person.setEmail(rs.getString("email"));
                person.setAddress(rs.getString("address"));
                person.setPhoneNumber(rs.getString("phone_number"));
                person.setRoleId(rs.getInt("role_id"));
                person.setStatus(rs.getString("status"));
                person.setAvatar(rs.getString("avatar"));
                person.setUserId(rs.getString("user_id"));
                person.setSchool_id(rs.getString("school_id"));
                person.setSchool_class_id(rs.getString("school_class_id"));
                person.setQualification(rs.getString("qualification"));
                person.setTeaching_years(rs.getInt("teaching_years"));
                lastId = currentId;
            }

            int salaryId = rs.getInt("salary_id");
            if (!rs.wasNull()) {
                Salary salary = new Salary();
                salary.setId(salaryId);
                salary.setPersonnelId(person.getId());
                salary.setSalaryMonth(rs.getInt("salary_month"));
                salary.setSalaryYear(rs.getInt("salary_year"));
                salary.setBaseSalary(rs.getFloat("base_salary"));
                salary.setTotalSalary(rs.getFloat("total_salary"));
                salary.setPaymentStatus(rs.getString("payment_status"));
                salary.setPaymentDate(rs.getDate("payment_date"));
                person.addSalary(salary);
            }
        }
        if (person != null) {
            persons.add(person);
        }
    } catch (Exception e) {
        System.out.println("Lỗi khi lấy danh sách nhân viên kèm lương: " + e.getMessage());
    }

    return persons;
}

//    public List<Personnel> getAllPersonnels() {
//        String sql = "SELECT * FROM [Personnels] ORDER BY id DESC";
//        List<Personnel> persons = new ArrayList<>();
//        try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet resultSet = statement.executeQuery()) {
//
//            while (resultSet.next()) {
//                Personnel person = createPersonnel(resultSet);
//
//                // Lấy thêm thông tin từ bảng Schools (nếu có trong câu truy vấn)
//                try {
//                    person.setAddressSchool(resultSet.getString("addressSchool"));
//                } catch (SQLException e) {
//                    // Trường hợp không có cột addressSchool trong ResultSet
//                }
//
//                persons.add(person);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return persons;
//    }


//Hàm lấy tất cả nhân viên trong cơ sở dữ liệu
    public List<Personnel> getAllPersonnels() {
        String sql = "SELECT * FROM [Personnels] ORDER BY id DESC";
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

                // Thêm các trường mới:
                person.setQualification(resultSet.getString("qualification"));
                person.setTeaching_years(resultSet.getInt("teaching_years"));

                persons.add(person);

            }

        } catch (Exception e) {

            System.out.println("Error retrieving personnels: " + e.getMessage());

            System.out.println("Lỗi khi lấy danh sách Personnel: " + e.getMessage());

        }
        return persons;
    }
public List<Personnel> getActivePersonnels() {
    List<Personnel> list = new ArrayList<>();
    String sql = "SELECT * FROM Personnels WHERE status = N'đang làm việc'";

    try (PreparedStatement ps = connection.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Personnel p = new Personnel();
            p.setId(rs.getString("id"));
            p.setFirstName(rs.getString("first_name"));
            p.setLastName(rs.getString("last_name"));
            p.setGender(rs.getBoolean("gender"));
            p.setBirthday(rs.getDate("birthday"));
            p.setAddress(rs.getString("address"));
            p.setEmail(rs.getString("email"));
            p.setPhoneNumber(rs.getString("phone_number"));
            p.setRoleId(rs.getInt("role_id"));
            p.setStatus(rs.getString("status"));
            p.setAvatar(rs.getString("avatar"));
            p.setUserId(rs.getString("user_id"));
            p.setSchool_id(rs.getString("school_id"));
            p.setSchool_class_id(rs.getString("school_class_id"));
            p.setSpecialization(rs.getString("specialization"));
            p.setQualification(rs.getString("qualification"));
            p.setTeaching_years(rs.getInt("teaching_years"));
            p.setAchievements(rs.getString("achievements"));

            list.add(p);
        }

    } catch (SQLException e) {
        e.printStackTrace(); // hoặc log lỗi bằng logger
    }

    return list;
}

    public Personnel getPersonnelByUserId(String userId) {
        String sql = "SELECT * FROM [User] u JOIN Personnels p ON u.id = p.user_id WHERE u.id = ?";
        Personnel personnel = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, userId);
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

                personnel.setSpecialization(resultSet.getString("specialization"));
                personnel.setQualification(resultSet.getString("qualification"));
                personnel.setTeaching_years(resultSet.getInt("teaching_years"));
                personnel.setAchievements(resultSet.getString("achievements"));
                personnel.setCv_file(resultSet.getString("cv_file"));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return personnel;
    }


   public Personnel getPersonnel(String id) {
    String sql = """
        SELECT 
            p.*, 
            s.id AS salary_id,
            s.salary_month, 
            s.salary_year,
            s.base_salary, 
            s.total_salary, 
            s.payment_status, 
            s.payment_date
        FROM Personnels p
        LEFT JOIN Salaries s ON p.id = s.personnel_id
        WHERE p.id = ?
    """;

    Personnel person = null;

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setString(1, id);
        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            if (person == null) {
                person = new Personnel();
                person.setId(rs.getString("id"));
                person.setFirstName(rs.getString("first_name"));
                person.setLastName(rs.getString("last_name"));
                person.setGender(rs.getBoolean("gender"));
                person.setBirthday(rs.getDate("birthday"));
                person.setEmail(rs.getString("email"));
                person.setAddress(rs.getString("address"));
                person.setPhoneNumber(rs.getString("phone_number"));
                person.setRoleId(rs.getInt("role_id"));
                person.setStatus(rs.getString("status")); // Sửa lại, không lấy từ payment_status
                person.setAvatar(rs.getString("avatar"));
                person.setUserId(rs.getString("user_id"));
                person.setSchool_id(rs.getString("school_id"));
                person.setSchool_class_id(rs.getString("school_class_id"));
                person.setQualification(rs.getString("qualification"));
                person.setTeaching_years(rs.getInt("teaching_years"));
                person.setSpecialization(rs.getString("specialization"));
                person.setAchievements(rs.getString("achievements"));
                person.setCv_file(rs.getString("cv_file"));
            }

            int salaryId = rs.getInt("salary_id");
            if (!rs.wasNull()) {
                Salary salary = new Salary();
                salary.setId(salaryId);
                salary.setPersonnelId(person.getId());
                salary.setSalaryMonth(rs.getInt("salary_month"));
                salary.setSalaryYear(rs.getInt("salary_year"));
                salary.setBaseSalary(rs.getFloat("base_salary"));
                salary.setTotalSalary(rs.getFloat("total_salary"));
                salary.setPaymentStatus(rs.getString("payment_status"));
                salary.setPaymentDate(rs.getDate("payment_date"));
                person.addSalary(salary); // Thêm vào danh sách salaries
            }
        }
    } catch (Exception e) {
        System.out.println("Lỗi khi lấy Personnel theo id (JOIN Salaries): " + e.getMessage());

    public Personnel getPersonnel(String id) {
        String sql = "SELECT * FROM [Personnels] WHERE id = ?";
        Personnel person = null;

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                person = new Personnel();
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
                person.setSpecialization(resultSet.getString("specialization"));
                person.setQualification(resultSet.getString("qualification"));
                person.setTeaching_years(resultSet.getInt("teaching_years"));
                person.setAchievements(resultSet.getString("achievements"));
                person.setCv_file(resultSet.getString("cv_file"));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return person;


    }

    public Personnel getAllPersonnel(String id) {
        String sql = "SELECT * FROM [Personnels] WHERE id = ?";
        Personnel person = null;


        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id); // KHÔNG dùng LIKE, dùng id chính xác

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                person = new Personnel();

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
                person.setQualification(resultSet.getString("qualification"));
                person.setTeaching_years(resultSet.getInt("teaching_years"));
                person.setSpecialization(resultSet.getString("specialization"));
                person.setAchievements(resultSet.getString("achievements"));
                person.setCv_file(resultSet.getString("cv_file"));

                // 👉 TÍNH lương sau khi load dữ liệu
                person.calculateSalary();
            }

        } catch (Exception e) {
            System.out.println("Lỗi khi lấy Personnel theo id: " + e.getMessage());

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

        String sql = "SELECT DISTINCT payment_status FROM Salaries";
        List<String> statusList = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String status = resultSet.getString("payment_status");
                if (status != null && !status.trim().isEmpty()) {
                    statusList.add(status.trim());
                }
            }
        } catch (Exception e) {
            System.out.println("Lỗi khi lấy danh sách trạng thái lương: " + e.getMessage());
        }
        return statusList;
    }
  public List<String> getAllStatuss() {

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
    StringBuilder sql = new StringBuilder("""
        SELECT 
            p.*,
            s.id AS salary_id,
            s.salary_month,
            s.salary_year,
            s.base_salary,
            s.total_salary,
            s.payment_status,
            s.payment_date
        FROM Personnels p
        LEFT JOIN Salaries s ON p.id = s.personnel_id
    """);
    
    // Thêm điều kiện lọc trạng thái nếu status không phải "all"
    if (!"all".equalsIgnoreCase(status)) {
        sql.append(" WHERE s.payment_status = ?");
    }
    sql.append(" ORDER BY p.id DESC, s.salary_year DESC, s.salary_month DESC");

    List<Personnel> persons = new ArrayList<>();
    try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {
        // Gán tham số nếu có điều kiện status
        if (!"all".equalsIgnoreCase(status)) {
            statement.setString(1, status);

    public List<Personnel> getPersonnelByStatus(String status) {
        String sql = "SELECT * FROM Personnels WHERE [status] = ? ORDER BY id DESC";
        List<Personnel> persons = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, status);
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
                person.setSpecialization(resultSet.getString("specialization"));
                person.setQualification(resultSet.getString("qualification"));
                person.setTeaching_years(resultSet.getInt("teaching_years"));
                person.setAchievements(resultSet.getString("achievements"));
                person.setCv_file(resultSet.getString("cv_file"));

                person.setQualification(resultSet.getString("qualification"));
                person.setTeaching_years(resultSet.getInt("teaching_years"));

                persons.add(person);
            }
        } catch (Exception e) {
            System.out.println("Error in getPersonnelByStatus: " + e.getMessage());

        }
        
        ResultSet rs = statement.executeQuery();
        Personnel person = null;
        String lastId = null;

        while (rs.next()) {
            String currentId = rs.getString("id");
            if (!currentId.equals(lastId)) {
                if (person != null) {
                    persons.add(person);
                }
                person = new Personnel();
                person.setId(currentId);
                person.setFirstName(rs.getString("first_name"));
                person.setLastName(rs.getString("last_name"));
                person.setGender(rs.getBoolean("gender"));
                person.setBirthday(rs.getDate("birthday"));
                person.setAddress(rs.getString("address"));
                person.setEmail(rs.getString("email"));
                person.setPhoneNumber(rs.getString("phone_number"));
                person.setRoleId(rs.getInt("role_id"));
                person.setStatus(rs.getString("status"));
                person.setAvatar(rs.getString("avatar"));
                person.setUserId(rs.getString("user_id"));
                person.setSchool_id(rs.getString("school_id"));
                person.setSchool_class_id(rs.getString("school_class_id"));
                person.setQualification(rs.getString("qualification"));
                person.setTeaching_years(rs.getInt("teaching_years"));
                lastId = currentId;
            }

            // Thêm bản ghi lương nếu có
            int salaryId = rs.getInt("salary_id");
            if (!rs.wasNull()) {
                Salary salary = new Salary();
                salary.setId(salaryId);
                salary.setPersonnelId(person.getId());
                salary.setSalaryMonth(rs.getInt("salary_month"));
                salary.setSalaryYear(rs.getInt("salary_year"));
                salary.setBaseSalary(rs.getFloat("base_salary"));
                salary.setTotalSalary(rs.getFloat("total_salary"));
                salary.setPaymentStatus(rs.getString("payment_status"));
                salary.setPaymentDate(rs.getDate("payment_date"));
                person.addSalary(salary); // Thêm vào danh sách salaries
            }
        }
        if (person != null) {
            persons.add(person);
        }
    } catch (SQLException e) {
        System.out.println("Lỗi khi lấy danh sách nhân viên theo trạng thái thanh toán: " + e.getMessage());
    }
    return persons;
}

   public List<Personnel> getPersonnelByRole(int role) {
    String sql = """
        SELECT 
            p.*,
            s.id AS salary_id,
            s.salary_month,
            s.salary_year,
            s.base_salary,
            s.total_salary,
            s.payment_status,
            s.payment_date
        FROM Personnels p
        LEFT JOIN Salaries s ON p.id = s.personnel_id
        WHERE p.role_id = ?
        ORDER BY p.id DESC, s.salary_year DESC, s.salary_month DESC
    """;

    List<Personnel> persons = new ArrayList<>();

    try (PreparedStatement statement = connection.prepareStatement(sql)) {
        statement.setInt(1, role);
        ResultSet rs = statement.executeQuery();

        Personnel person = null;
        String lastId = null;

        while (rs.next()) {
            String currentId = rs.getString("id");
            if (!currentId.equals(lastId)) {
                if (person != null) {
                    persons.add(person);
                }
                person = new Personnel();
                person.setId(currentId);
                person.setFirstName(rs.getString("first_name"));
                person.setLastName(rs.getString("last_name"));
                person.setGender(rs.getBoolean("gender"));
                person.setBirthday(rs.getDate("birthday"));
                person.setEmail(rs.getString("email"));
                person.setAddress(rs.getString("address"));
                person.setPhoneNumber(rs.getString("phone_number"));
                person.setRoleId(rs.getInt("role_id"));
                person.setStatus(rs.getString("status"));
                person.setAvatar(rs.getString("avatar"));
                person.setUserId(rs.getString("user_id"));
                person.setSchool_id(rs.getString("school_id"));
                person.setSchool_class_id(rs.getString("school_class_id"));
                person.setSpecialization(rs.getString("specialization"));
                person.setQualification(rs.getString("qualification"));
                person.setTeaching_years(rs.getInt("teaching_years"));
                person.setAchievements(rs.getString("achievements"));
                person.setCv_file(rs.getString("cv_file"));
                lastId = currentId;
            }

            // Thêm thông tin lương nếu có
            int salaryId = rs.getInt("salary_id");
            if (!rs.wasNull()) {
                Salary salary = new Salary();
                salary.setId(salaryId);
                salary.setPersonnelId(person.getId());
                salary.setSalaryMonth(rs.getInt("salary_month"));
                salary.setSalaryYear(rs.getInt("salary_year"));
                salary.setBaseSalary(rs.getFloat("base_salary"));
                salary.setTotalSalary(rs.getFloat("total_salary"));
                salary.setPaymentStatus(rs.getString("payment_status"));
                salary.setPaymentDate(rs.getDate("payment_date"));
                person.addSalary(salary); // Thêm vào danh sách salaries
            }
        }
        if (person != null) {
            persons.add(person);
        }
    } catch (SQLException e) {
        System.out.println("Lỗi khi lấy Personnel theo role có lương: " + e.getMessage());
        e.printStackTrace();
    }


    return persons;
}

public List<Personnel> getPersonnelByStatusRoleMonth(String status, String role, String month) {
    StringBuilder sql = new StringBuilder("""
        SELECT 
            p.*,
            s.id AS salary_id,
            s.salary_month,
            s.salary_year,
            s.base_salary,
            s.total_salary,
            s.payment_status,
            s.payment_date
        FROM Personnels p
        LEFT JOIN Salaries s ON p.id = s.personnel_id
        WHERE 1=1
    """);

    List<Object> params = new ArrayList<>();

    // Lọc trạng thái thanh toán
    if (status != null && !status.equalsIgnoreCase("all") && !status.isEmpty()) {
        sql.append(" AND s.payment_status = ?");
        params.add(status);
    }

    // Lọc vai trò
    if (role != null && !role.equalsIgnoreCase("all") && !role.isEmpty()) {
        try {
            int xrole = Integer.parseInt(role);
            sql.append(" AND p.role_id = ?");
            params.add(xrole);
        } catch (NumberFormatException e) {
            System.out.println("Lỗi role không hợp lệ: " + role);
            e.printStackTrace();
        }
    }

    // Lọc tháng
    if (month != null && !month.equalsIgnoreCase("all") && !month.isEmpty()) {
        try {
            int m = Integer.parseInt(month);
            sql.append(" AND s.salary_month = ?");
            params.add(m);
        } catch (NumberFormatException e) {
            System.out.println("Lỗi tháng không hợp lệ: " + month);
            e.printStackTrace();
        }
    }

    sql.append(" ORDER BY p.id DESC, s.salary_year DESC, s.salary_month DESC");

    List<Personnel> persons = new ArrayList<>();

    try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {
        for (int i = 0; i < params.size(); i++) {
            statement.setObject(i + 1, params.get(i));
        }

        ResultSet rs = statement.executeQuery();
        Personnel person = null;
        String lastId = null;

        while (rs.next()) {
            String currentId = rs.getString("id");
            if (!currentId.equals(lastId)) {
                if (person != null) {
                    persons.add(person);
                }
                person = new Personnel();
                person.setId(currentId);
                person.setFirstName(rs.getString("first_name"));
                person.setLastName(rs.getString("last_name"));
                person.setGender(rs.getBoolean("gender"));
                person.setBirthday(rs.getDate("birthday"));
                person.setEmail(rs.getString("email"));
                person.setAddress(rs.getString("address"));
                person.setPhoneNumber(rs.getString("phone_number"));
                person.setRoleId(rs.getInt("role_id"));
                person.setStatus(rs.getString("status"));
                person.setAvatar(rs.getString("avatar"));
                person.setUserId(rs.getString("user_id"));
                person.setSchool_id(rs.getString("school_id"));
                person.setSchool_class_id(rs.getString("school_class_id"));
                person.setSpecialization(rs.getString("specialization"));
                person.setQualification(rs.getString("qualification"));
                person.setTeaching_years(rs.getInt("teaching_years"));
                person.setAchievements(rs.getString("achievements"));
                person.setCv_file(rs.getString("cv_file"));
                lastId = currentId;
            }

            // Thêm thông tin lương nếu có
            int salaryId = rs.getInt("salary_id");
            if (!rs.wasNull()) {
                Salary salary = new Salary();
                salary.setId(salaryId);
                salary.setPersonnelId(person.getId());
                salary.setSalaryMonth(rs.getInt("salary_month"));
                salary.setSalaryYear(rs.getInt("salary_year"));
                salary.setBaseSalary(rs.getFloat("base_salary"));
                salary.setTotalSalary(rs.getFloat("total_salary"));
                salary.setPaymentStatus(rs.getString("payment_status"));
                salary.setPaymentDate(rs.getDate("payment_date"));
                person.addSalary(salary); // Thêm vào danh sách salaries
            }
        }
        if (person != null) {
            persons.add(person);
        }
    } catch (SQLException e) {
        System.out.println("Lỗi khi lấy nhân sự theo status + role + month: " + e.getMessage());
        e.printStackTrace();
    }

    return persons;
}
public List<Personnel> getPersonnelByIdNameRoleStatus(String status, String role, String month) {
    StringBuilder sql = new StringBuilder("""
        SELECT 
            p.*,
            s.id AS salary_id,
            s.salary_month,
            s.salary_year,
            s.base_salary,
            s.total_salary,
            s.payment_status,
            s.payment_date
        FROM Personnels p
        LEFT JOIN Salaries s ON p.id = s.personnel_id
        WHERE 1=1
    """);

    List<Object> params = new ArrayList<>();

    // Lọc theo trạng thái thanh toán
    if (status != null && !status.equalsIgnoreCase("all") && !status.isEmpty()) {
        sql.append(" AND s.payment_status = ?");
        params.add(status);
    }

    // Lọc theo vai trò
    if (role != null && !role.equalsIgnoreCase("all") && !role.isEmpty()) {
        try {
            int xrole = Integer.parseInt(role);
            sql.append(" AND p.role_id = ?");
            params.add(xrole);
        } catch (NumberFormatException e) {
            System.out.println("Lỗi role không hợp lệ: " + role);
            e.printStackTrace();
        }
    }

    // Lọc theo tháng (nếu có)
    if (month != null && !month.equalsIgnoreCase("all") && !month.isEmpty()) {
        try {
            int m = Integer.parseInt(month);
            sql.append(" AND s.salary_month = ?");
            params.add(m);
        } catch (NumberFormatException e) {
            System.out.println("Lỗi tháng không hợp lệ: " + month);
            e.printStackTrace();
        }
    }

    sql.append(" ORDER BY p.id DESC, s.salary_year DESC, s.salary_month DESC");

    List<Personnel> persons = new ArrayList<>();

    try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {
        for (int i = 0; i < params.size(); i++) {
            statement.setObject(i + 1, params.get(i));
        }

        ResultSet rs = statement.executeQuery();
        Personnel person = null;
        String lastId = null;

        while (rs.next()) {
            String currentId = rs.getString("id");
            if (!currentId.equals(lastId)) {
                if (person != null) {
                    persons.add(person);
                }
                person = new Personnel();
                person.setId(currentId);
                person.setFirstName(rs.getString("first_name"));
                person.setLastName(rs.getString("last_name"));
                person.setGender(rs.getBoolean("gender"));
                person.setBirthday(rs.getDate("birthday"));
                person.setEmail(rs.getString("email"));
                person.setAddress(rs.getString("address"));
                person.setPhoneNumber(rs.getString("phone_number"));
                person.setRoleId(rs.getInt("role_id"));
                person.setStatus(rs.getString("status"));
                person.setAvatar(rs.getString("avatar"));
                person.setUserId(rs.getString("user_id"));
                person.setSchool_id(rs.getString("school_id"));
                person.setSchool_class_id(rs.getString("school_class_id"));
                person.setSpecialization(rs.getString("specialization"));
                person.setQualification(rs.getString("qualification"));
                person.setTeaching_years(rs.getInt("teaching_years"));
                person.setAchievements(rs.getString("achievements"));
                person.setCv_file(rs.getString("cv_file"));
                lastId = currentId;
            }

            // Thêm thông tin lương nếu có
            int salaryId = rs.getInt("salary_id");
            if (!rs.wasNull()) {
                Salary salary = new Salary();
                salary.setId(salaryId);
                salary.setPersonnelId(person.getId());
                salary.setSalaryMonth(rs.getInt("salary_month"));
                salary.setSalaryYear(rs.getInt("salary_year"));
                salary.setBaseSalary(rs.getFloat("base_salary"));
                salary.setTotalSalary(rs.getFloat("total_salary"));
                salary.setPaymentStatus(rs.getString("payment_status"));
                salary.setPaymentDate(rs.getDate("payment_date"));
                person.addSalary(salary); // Thêm vào danh sách salaries
            }
        }
        if (person != null) {
            persons.add(person);
        }
    } catch (SQLException e) {
        System.out.println("Lỗi khi lấy nhân sự theo role và status có join Salaries: " + e.getMessage());
        e.printStackTrace();
    }

    return persons;
}
 public List<Personnel> getPersonnelByIdNameRoleStatus1(String status, String role) {
        String sql = " Select * from Personnels where 1=1";

    public List<Personnel> getPersonnelByRole(int role) {
        String sql = "SELECT * FROM Personnels WHERE role_id = ? ORDER BY id DESC";
        List<Personnel> persons = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
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

                person.setSchool_id(resultSet.getString("school_id"));
                person.setSchool_class_id(resultSet.getString("school_class_id"));
                person.setSpecialization(resultSet.getString("specialization"));
                person.setQualification(resultSet.getString("qualification"));
                person.setTeaching_years(resultSet.getInt("teaching_years"));
                person.setAchievements(resultSet.getString("achievements"));
                person.setCv_file(resultSet.getString("cv_file"));

                person.setQualification(resultSet.getString("qualification"));
                person.setTeaching_years(resultSet.getInt("teaching_years"));

                persons.add(person);
            }
        } catch (Exception e) {
            System.out.println("Error in getPersonnelByRole: " + e.getMessage());
        }
        return persons;
    }

    public List<Personnel> getPersonnelByIdNameRoleStatus(String status, String role) {
        String sql = "SELECT * FROM Personnels WHERE 1=1";


        if (status != null && !status.isEmpty()) {
            sql += " AND status = N'" + status + "'";
        }
        if (role != null && !role.isEmpty()) {
            int xrole = Integer.parseInt(role);
            sql += " AND role_id = " + xrole;
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

                person.setSchool_id(resultSet.getString("school_id"));
                person.setSchool_class_id(resultSet.getString("school_class_id"));
                //  Bổ sung các trường còn thiếu
                person.setSpecialization(resultSet.getString("specialization"));
                person.setQualification(resultSet.getString("qualification"));
                person.setTeaching_years(resultSet.getInt("teaching_years"));
                person.setAchievements(resultSet.getString("achievements"));
                person.setCv_file(resultSet.getString("cv_file"));

                person.setQualification(resultSet.getString("qualification"));
                person.setTeaching_years(resultSet.getInt("teaching_years"));

                persons.add(person);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return persons;
    }

    public List<Personnel> getPersonnelByNameOrId(String search) {
        String sql = "SELECT * FROM [Personnels] WHERE (last_name + ' ' + first_name LIKE N'%" + search + "%' OR id LIKE '%" + search + "%')";
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
                //  Bổ sung các trường còn thiếu
                person.setSpecialization(resultSet.getString("specialization"));
                person.setQualification(resultSet.getString("qualification"));
                person.setTeaching_years(resultSet.getInt("teaching_years"));
                person.setAchievements(resultSet.getString("achievements"));
                person.setCv_file(resultSet.getString("cv_file"));

                persons.add(person);
            }
        } catch (Exception e) {
            System.out.println("Error in getPersonnelByNameOrId: " + e.getMessage());
        }
        return persons;
    }

   public boolean updatePerson(Personnel person) {

    String sql = "UPDATE Personnels SET first_name = ?, last_name = ?, gender = ?, birthday = ?, "
            + "address = ?, email = ?, phone_number = ?, status = ?, avatar = ?, school_id = ?, school_class_id = ?, "
            + "specialization = ?, qualification = ?, teaching_years = ?, achievements = ?, cv_file = ? WHERE user_id = ?";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setString(1, person.getFirstName());
        stmt.setString(2, person.getLastName());
        stmt.setBoolean(3, person.isGender());
        stmt.setDate(4, new java.sql.Date(person.getBirthday().getTime()));
        stmt.setString(5, person.getAddress());
        stmt.setString(6, person.getEmail());
        stmt.setString(7, person.getPhoneNumber());
        stmt.setString(8, person.getStatus());
        stmt.setString(9, person.getAvatar());
        stmt.setString(10, person.getSchool_id());
        stmt.setString(11, person.getSchool_class_id());
        stmt.setString(12, person.getSpecialization());
        stmt.setString(13, person.getQualification());
        stmt.setInt(14, person.getTeaching_years());
        stmt.setString(15, person.getAchievements());
        stmt.setString(16, person.getCv_file());
        stmt.setString(17, person.getUserId());

        return stmt.executeUpdate() > 0;

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

                personnel.setSchoolName(resultSet.getString("schoolName")); // Cột từ bảng Schools
                personnel.setClassName(resultSet.getString("class_name"));  // Cột từ bảng SchoolClasses

                //  Bổ sung các trường còn thiếu từ bảng Personnels
                personnel.setSchoolName(resultSet.getString("schoolName"));
                personnel.setClassName(resultSet.getString("class_name"));

                // Các trường mở rộng dành cho giáo viên
                personnel.setSpecialization(resultSet.getString("specialization"));
                personnel.setQualification(resultSet.getString("qualification"));
                personnel.setTeaching_years(resultSet.getInt("teaching_years"));
                personnel.setAchievements(resultSet.getString("achievements"));
                personnel.setCv_file(resultSet.getString("cv_file"));
            }

            resultSet.close();
            preparedStatement.close();
        } catch (SQLException e) {
            System.err.println("SQL Error for userId: " + userId + " - " + e.getMessage());
            e.printStackTrace();
        }
        return personnel;
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

public Personnel getTeacherByClass(String classId) {
        String sql = "Select teacher_id from class where id= ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, classId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getPersonnel(resultSet.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
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
                     p.specialization,
                     p.qualification,
                     p.teaching_years,
                     p.achievements,
                     p.cv_file,
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
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setObject(1, studentId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Personnel person = new Personnel();
                person.setId(rs.getString("id"));
                person.setFirstName(rs.getString("first_name"));
                person.setLastName(rs.getString("last_name"));
                person.setGender(rs.getBoolean("gender"));
                person.setBirthday(rs.getDate("birthday"));
                person.setEmail(rs.getString("email"));
                person.setAddress(rs.getString("address"));
                person.setPhoneNumber(rs.getString("phone_number"));
                person.setRoleId(rs.getInt("role_id"));
                person.setStatus(rs.getString("status"));
                person.setAvatar(rs.getString("avatar"));
                person.setUserId(rs.getString("user_id"));
                person.setSpecialization(rs.getString("specialization"));
                person.setQualification(rs.getString("qualification"));
                person.setTeaching_years(rs.getInt("teaching_years"));
                person.setAchievements(rs.getString("achievements"));
                person.setCv_file(rs.getString("cv_file"));
                person.setClassName(rs.getString("class_name")); // lấy từ c.name
                persons.add(person);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return persons;
    }

    public List<Personnel> getPersonnelNonUserId() {
        List<Personnel> list = new ArrayList<>();
        String sql = "SELECT * FROM Personnels WHERE user_id IS NULL AND status = N'đang làm việc'";
        try (PreparedStatement ps = connection.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Personnel p = new Personnel();
                p.setId(rs.getString("id"));
                p.setFirstName(rs.getString("first_name"));
                p.setLastName(rs.getString("last_name"));
                p.setGender(rs.getBoolean("gender"));
                p.setBirthday(rs.getDate("birthday"));
                p.setAddress(rs.getString("address"));
                p.setEmail(rs.getString("email"));
                p.setPhoneNumber(rs.getString("phone_number"));
                p.setRoleId(rs.getInt("role_id"));
                p.setStatus(rs.getString("status"));
                p.setAvatar(rs.getString("avatar"));
                p.setUserId(rs.getString("user_id"));
                p.setSpecialization(rs.getString("specialization"));
                p.setQualification(rs.getString("qualification"));
                p.setTeaching_years(rs.getInt("teaching_years"));
                p.setAchievements(rs.getString("achievements"));
                p.setCv_file(rs.getString("cv_file"));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Personnel> getPersonnelByRoleAndNonUserId(int id) {
        List<Personnel> list = new ArrayList<>();
        String sql = "SELECT * FROM Personnels WHERE role_id = ? AND user_id IS NULL AND status = N'đang làm việc'";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Personnel p = new Personnel();
                p.setId(rs.getString("id"));
                p.setFirstName(rs.getString("first_name"));
                p.setLastName(rs.getString("last_name"));
                p.setGender(rs.getBoolean("gender"));
                p.setBirthday(rs.getDate("birthday"));
                p.setAddress(rs.getString("address"));
                p.setEmail(rs.getString("email"));
                p.setPhoneNumber(rs.getString("phone_number"));
                p.setRoleId(rs.getInt("role_id"));
                p.setStatus(rs.getString("status"));
                p.setAvatar(rs.getString("avatar"));
                p.setUserId(rs.getString("user_id"));
                p.setSpecialization(rs.getString("specialization"));
                p.setQualification(rs.getString("qualification"));
                p.setTeaching_years(rs.getInt("teaching_years"));
                p.setAchievements(rs.getString("achievements"));
                p.setCv_file(rs.getString("cv_file"));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Personnel> getAvailableTeachers(String schoolYearId) {

        String sql = "SELECT t.*, s.schoolName, s.addressSchool "
                + "FROM Personnels t "
                + "LEFT JOIN Class c ON t.id = c.teacher_id AND c.school_year_id = ? "
                + "LEFT JOIN Schools s ON t.school_id = s.id "
                + "WHERE c.teacher_id IS NULL "
                + "AND t.id LIKE 'GV%' "
                + "AND t.status LIKE N'đang làm việc%';";

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

    public List<Personnel> getFreeTeacherByDate(String dayId) {
        String sql = "SELECT p.* FROM Personnels p WHERE p.id NOT IN (\n"
                + "    SELECT t.teacher_id\n"
                + "    FROM Timetables t\n"
                + "    WHERE t.date_id = ?\n"
                + ") and p.id like 'TEA%';";
        List<Personnel> teacherList = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, dayId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                teacherList.add(createPersonnel(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return teacherList;
    }

  public boolean updateSalaryStatus(String personnelId, String status, int month, int year, Date paymentDate) {
        String sql = """
            UPDATE Salaries
            SET payment_status = ?, payment_date = ?
            WHERE personnel_id = ? AND salary_month = ? AND salary_year = ?
        """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status);
            stmt.setDate(2, paymentDate != null ? new java.sql.Date(paymentDate.getTime()) : null);
            stmt.setString(3, personnelId);
            stmt.setInt(4, month);
            stmt.setInt(5, year);
            System.out.println("Updating payment status: personnelId=" + personnelId + ", status=" + status + ", month=" + month + ", year=" + year + ", paymentDate=" + paymentDate);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Rows affected: " + rowsAffected);
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật trạng thái thanh toán: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
  
public List<Personnel> getPersonnelByMonthWithSalary(int month) {
    StringBuilder sql = new StringBuilder("""
        SELECT 
            p.*,
            s.id AS salary_id,
            s.salary_month,
            s.salary_year,
            s.base_salary,
            s.total_salary,
            s.payment_status,
            s.payment_date
        FROM Personnels p
        LEFT JOIN Salaries s ON p.id = s.personnel_id
        WHERE s.salary_month = ?
        ORDER BY p.id DESC
    """);

    List<Personnel> persons = new ArrayList<>();

    try (PreparedStatement statement = connection.prepareStatement(sql.toString())) {
        statement.setInt(1, month);
        ResultSet rs = statement.executeQuery();
        Personnel person = null;
        String lastId = null;

        while (rs.next()) {
            String currentId = rs.getString("id");
            if (!currentId.equals(lastId)) {
                if (person != null) {
                    persons.add(person);
                }
                person = new Personnel();
                person.setId(currentId);
                person.setFirstName(rs.getString("first_name"));
                person.setLastName(rs.getString("last_name"));
                person.setGender(rs.getBoolean("gender"));
                person.setBirthday(rs.getDate("birthday"));
                person.setEmail(rs.getString("email"));
                person.setAddress(rs.getString("address"));
                person.setPhoneNumber(rs.getString("phone_number"));
                person.setRoleId(rs.getInt("role_id"));
                person.setStatus(rs.getString("status"));
                person.setAvatar(rs.getString("avatar"));
                person.setUserId(rs.getString("user_id"));
                person.setQualification(rs.getString("qualification"));
                person.setTeaching_years(rs.getInt("teaching_years"));
                lastId = currentId;
            }

            int salaryId = rs.getInt("salary_id");
            if (!rs.wasNull()) {
                Salary salary = new Salary();
                salary.setId(salaryId);
                salary.setPersonnelId(person.getId());
                salary.setSalaryMonth(rs.getInt("salary_month"));
                salary.setSalaryYear(rs.getInt("salary_year"));
                salary.setBaseSalary(rs.getFloat("base_salary"));
                salary.setTotalSalary(rs.getFloat("total_salary"));
                salary.setPaymentStatus(rs.getString("payment_status"));
                salary.setPaymentDate(rs.getDate("payment_date"));
                person.addSalary(salary);
            }
        }
        if (person != null) {
            persons.add(person);
        }
    } catch (Exception e) {
        System.out.println("Lỗi khi lấy nhân viên theo tháng có lương: " + e.getMessage());
    }

    return persons;
}


    public Personnel getTeacherByClassAndSchoolYear(String classId, String schoolYearId) {
        String sql = "SELECT t.* FROM Class c "
                + "JOIN Personnels t ON c.teacher_id = t.id "
                + "WHERE c.id = ? AND c.school_year_id = ?";
        Personnel teacher = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, classId);
            preparedStatement.setString(2, schoolYearId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                teacher = createPersonnel(resultSet);  // Dùng lại hàm tạo đối tượng Personnel
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return teacher;
    }

   

    public Personnel getHomeroomTeacherByClassId(String classId) {
        String sql = "SELECT p.*, s.schoolName, s.addressSchool, sc.id AS school_class_id "
                + "FROM Class c "
                + "JOIN Personnels p ON c.teacher_id = p.id "
                + "LEFT JOIN Schools s ON p.school_id = s.id "
                + "LEFT JOIN SchoolClasses sc ON p.school_class_id = sc.id "
                + "WHERE c.id = ?";
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

    public Personnel getTeacherByClass(String classId) {
        String sql = "Select teacher_id from class where id= ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, classId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return getPersonnel(resultSet.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }



    public List<Personnel> getPersonnelAttendance() {
        List<Personnel> list = new ArrayList<>();
        String sql = "SELECT * \n"
                + "FROM Personnels \n"
                + "WHERE status = N'đang làm việc' \n"
                + "AND (role_id = 0 OR role_id = 2 OR role_id = 3 OR role_id = 5);";
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
                p.setSchool_id(rs.getString(13));
                p.setSchool_class_id(rs.getString(14));
                p.setSpecialization(rs.getString(15));
                p.setQualification(rs.getString(16));
                p.setTeaching_years(rs.getInt(17));
                p.setAchievements(rs.getString(18));
                p.setCv_file(rs.getString(19));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
  
   public int getWorkingDaysByMonth(String personnelId, int month, int year) {
    int workingDays = 0;
    String sql = """
        SELECT COUNT(*)
        FROM PersonnelsAttendance pa
        JOIN Days d ON pa.day_id = d.id
        WHERE pa.personnel_id = ? AND MONTH(d.date) = ? AND YEAR(d.date) = ? AND pa.status = 'present'
    """;


    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setString(1, personnelId);
        stmt.setInt(2, month);
        stmt.setInt(3, year);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            workingDays = rs.getInt(1);
        }
    } catch (SQLException e) {
        System.out.println("Lỗi khi lấy số ngày công: " + e.getMessage());
    }
    return workingDays;

}

public void updateTotalSalary(String personnelId, int month, int year, float totalSalary) {
    String sql = """
        UPDATE Salaries
        SET total_salary = ?
        WHERE personnel_id = ? AND salary_month = ? AND salary_year = ?
    """;

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
        stmt.setFloat(1, totalSalary);
        stmt.setString(2, personnelId);
        stmt.setInt(3, month);
        stmt.setInt(4, year);
        stmt.executeUpdate();
    } catch (SQLException e) {
        System.out.println("Lỗi khi cập nhật tổng lương: " + e.getMessage());
    }
}
 private List<Salary> getSalariesByPersonnelId(String personnelId) {
        List<Salary> salaries = new ArrayList<>();
        String sql = """
            SELECT id, personnel_id, salary_month, salary_year, base_salary, total_salary, payment_status, payment_date
            FROM Salaries
            WHERE personnel_id = ?
        """;
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, personnelId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Salary salary = new Salary();
                salary.setId(rs.getInt("id"));
                salary.setPersonnelId(rs.getString("personnel_id"));
                salary.setSalaryMonth(rs.getInt("salary_month"));
                salary.setSalaryYear(rs.getInt("salary_year"));
                salary.setBaseSalary(rs.getFloat("base_salary"));
                salary.setTotalSalary(rs.getFloat("total_salary"));
                salary.setPaymentStatus(rs.getString("payment_status"));
                salary.setPaymentDate(rs.getDate("payment_date"));
                int workingDays = getWorkingDaysByMonth(personnelId, rs.getInt("salary_month"), rs.getInt("salary_year"));
                salary.setWorkingDays(workingDays);
                salaries.add(salary);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy danh sách lương: " + e.getMessage());
        }
        return salaries;
    }




       
       
         
   // Kiểm tra xem số điện thoại đã tồn tại trong bảng Personnels hay chưa
   public boolean checkPersonnelPhone(String phoneNumber) {
    String sql = "SELECT phone_number FROM Personnels WHERE phone_number = ?";
    try {
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, phoneNumber.trim());  // Đặt giá trị cho dấu hỏi ?
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next();  // Nếu có bản ghi trùng thì trả về true
    } catch (Exception e) {
        System.out.println("Error checking phone number: " + e.getMessage());
    }
    return false;  // Không tìm thấy => trả về false
}


    // Kiểm tra xem email đã tồn tại trong bảng Personnels hay chưa
   public boolean checkPersonnelEmail(String email) {
    String sql = "SELECT email FROM Personnels WHERE LOWER(email) = ?";
    try {
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, email.trim().toLowerCase());
        ResultSet resultSet = statement.executeQuery();
        return resultSet.next(); // Nếu tìm thấy => trùng
    } catch (Exception e) {
        System.out.println("Error checking email: " + e.getMessage());
    }
    return false;
}


    // Thêm một nhân sự mới vào bảng Personnels với các thông tin đầu vào
    public void insertPersonnel(String id, String firstName, String lastName, int gender, String birthday, String address,
            String email, String phone, int role, String avatar,
            String qualification, String specialization, String achievements,
            int teaching_years, String cv_file) {

        String sql = "INSERT INTO Personnels (id, first_name, last_name, gender, birthday, address, email, phone_number, role_id, status, avatar, user_id, school_id, school_class_id, specialization, qualification, teaching_years, achievements, cv_file) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, id);
            st.setString(2, firstName);
            st.setString(3, lastName);
            st.setInt(4, gender);
            st.setString(5, birthday);
            st.setString(6, address);
            st.setString(7, email);
            st.setString(8, phone);
            st.setInt(9, role);
            st.setString(10, "đang chờ xử lý"); // status mặc định
            st.setString(11, avatar);
            st.setString(12, null); // user_id
            st.setString(13, null); // school_id
            st.setString(14, null); // school_class_id
            st.setString(15, specialization);
            st.setString(16, qualification);
            st.setInt(17, teaching_years);
            st.setString(18, achievements);
            st.setString(19, cv_file);

            st.executeUpdate();
            System.out.println("✅ Insert thành công!");
        } catch (Exception e) {
            System.out.println("❌ Lỗi khi insert: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Trả về số lượng nhân sự theo một vai trò cụ thể (role_id)
    public int getNumberOfPersonByRole(int id) {
        String sql = "select count(id) as numberofpersonbyrole\n"
                + "from Personnels where role_id = ? ";
        int number = 0;
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                number = resultSet.getInt("numberofpersonbyrole");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return number;
    }

    // Thống kê số lượng nhân sự theo vai trò
 public Map<String, Integer> getPersonnelCountByRole() {
        Map<String, Integer> result = new LinkedHashMap<>();
        String sql = """
            SELECT r.description, COUNT(p.id) AS count
            FROM Personnels p
            JOIN Roles r ON p.role_id = r.id
            GROUP BY r.description
            ORDER BY r.description
        """;
        
        try (PreparedStatement stmt = connection.prepareStatement(sql); 
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                result.put(rs.getString("description"), rs.getInt("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Consider proper logging instead of printStackTrace
        }
        return result;
    }

// Thống kê số lượng nhân sự theo trạng thái
     public Map<String, Integer> getPersonnelCountByStatus() {
        Map<String, Integer> result = new LinkedHashMap<>();
        String sql = "SELECT status, COUNT(id) AS count FROM Personnels GROUP BY status ORDER BY status";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql); 
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                result.put(rs.getString("status"), rs.getInt("count"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Consider proper logging instead of printStackTrace
        }
        return result;
    }
     
     
       public Personnel getPersonnels(String id) {
        String sql = "SELECT * FROM [Personnels] WHERE id LIKE ?";
        Personnel person = null;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, "%" + id + "%");
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                person = new Personnel();
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
                person.setSpecialization(resultSet.getString("specialization"));
                person.setQualification(resultSet.getString("qualification"));
                person.setTeaching_years(resultSet.getInt("teaching_years"));
                person.setAchievements(resultSet.getString("achievements"));
                person.setCv_file(resultSet.getString("cv_file"));
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return person;
    }
       
       
        public List<Personnel> getPersonnelByIdNameRoleStatuss(String status, String role) {
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
        
        
          public List<Personnel> getPersonnelByStatuss(String status) {
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
          
              public List<Personnel> getPersonnelByRoles(int role) {
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



}

