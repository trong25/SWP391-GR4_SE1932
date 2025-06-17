/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.user;

import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.DBContext;
import java.sql.PreparedStatement;
import java.text.DecimalFormat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import utils.Email;

import utils.PasswordUtil;

/**
 *
 * @author MSI
 */
public class UserDAO extends DBContext {

    private User createUser(ResultSet rs) throws SQLException {
        User newUser = new User();
        newUser.setId(rs.getString(1));
        newUser.setUsername(rs.getString(2));
        newUser.setEmail(rs.getString(5));
        newUser.setRoleId(rs.getInt(6));
        newUser.setIsDisabled(rs.getByte(7));
        return newUser;
    }

    private byte[] getUserSalt(String key) {
        byte[] salt = null;
        String sql = "SELECT salt FROM [User] WHERE user_name = ? OR email = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, key);
            statement.setString(2, key);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                salt = rs.getBytes("salt");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return salt;
    }

    public User getUserByUsernamePassword(String key, String password) {
        try {
            byte[] salt = getUserSalt(key);

//        if (salt == null) {
//            salt = PasswordUtil.generateSalt();
//            byte[] hashedPassword = PasswordUtil.hashPassword(password.toCharArray(), salt);
//            String sqlUpdate = "UPDATE [dbo].[User] SET salt = ?, hashed_password = ? WHERE user_name = ?";
//            try (PreparedStatement psUp = connection.prepareStatement(sqlUpdate)) {
//                psUp.setBytes(1, salt);
//                psUp.setBytes(2, hashedPassword);
//                psUp.setString(3, userName);
//                psUp.executeUpdate();
//            }
//            return getUserByUsername(userName);
//        }
            byte[] hashedPassword = PasswordUtil.hashPassword(password.toCharArray(), salt);
            String sql = "SELECT * FROM [dbo].[User] WHERE (user_name = ? OR email = ?) AND hashed_password = ?";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, key);
                ps.setString(2, key);
                ps.setBytes(3, hashedPassword);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return createUser(rs);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM dbo.[User] WHERE [user_name] = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return createUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getUserByRoleIdandTeacherId(int role, String teacherId) {
        List<User> listUser = new ArrayList<>();
        String sql = "select * from [User] u inner join classDetails cd on u.user_name"
                + "= cd.student_id inner join Class c on c.id=cd.class_id where u.role_id=? and c.teacher_id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, role);
            ps.setString(2, teacherId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listUser.add(createUser(rs));
            }
            return listUser;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User getByUsernameOrEmail(String key) {

        String sql = "SELECT * FROM dbo.[User] WHERE user_name = ? OR email = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, key);
            stmt.setString(2, key);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return createUser(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    private String generatePassword() {
        SecureRandom random = new SecureRandom(); // Sử dụng SecureRandom để tạo số ngẫu nhiên
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int digit = random.nextInt(10); // Sinh số ngẫu nhiên từ 0 đến 9
            password.append(digit); // Thêm số ngẫu nhiên vào mật khẩu
        }

        return password.toString(); // Trả về mật khẩu dưới dạng chuỗi
    }

    public boolean resetPassword(String key) {
        try {
            String newPassword = generatePassword();
            Email.sendEmail(Objects.requireNonNull(getByUsernameOrEmail(key)).getEmail(), "Xử lí quên mật khẩu cho Tabi learning", "Mật khẩu mới: " + newPassword);
            byte[] salt = PasswordUtil.generateSalt();
            byte[] hashedNewPassword = PasswordUtil.hashPassword(newPassword.toCharArray(), salt);
            String sql = "UPDATE [dbo].[User] SET [salt] =?, [hashed_password] = ? WHERE [email] = ? OR user_name = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBytes(1, salt);
            statement.setBytes(2, hashedNewPassword);
            statement.setString(3, key);
            statement.setString(4, key);
            int rowCount = statement.executeUpdate();
            return rowCount > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean checkPassword(String password, String username) {
        try {
            byte[] salt = getUserSalt(username);
            byte[] expectedHashPassword = PasswordUtil.hashPassword(password.toCharArray(), salt);

            String sql = "SELECT [hashed_password] FROM [User] WHERE user_name = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                byte[] storedHashedPassword = rs.getBytes("hashed_password");
                return Arrays.equals(expectedHashPassword, storedHashedPassword);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateNewPassword(String newPassword, String userId) {
        String sql = "UPDATE [User]\n"
                + "SET [salt] = ? \n"
                + "  ,[hashed_password] = ? \n"
                + "WHERE [id] = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            byte[] salt = PasswordUtil.generateSalt();
            byte[] hashedNewPassword = PasswordUtil.hashPassword(newPassword.toCharArray(), salt);
            statement.setBytes(1, salt);
            statement.setBytes(2, hashedNewPassword);
            statement.setString(3, userId);
            int rowCount = statement.executeUpdate();
            return rowCount > 0;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public boolean updateUserById(User user) {
        String sql = "UPDATE [dbo].[User]\n"
                + "   SET [email] = ?\n"
                + " WHERE [id] = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean checkEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM [User] WHERE email = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
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

    public List<User> getListUser() {
        List<User> list = new ArrayList<>();

        String sql = "SELECT * FROM dbo.[User]";
        try {

            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User u = createUser(rs);
                list.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public User getUserById(String id) {
        String sql = "select * from [User] where id=?";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getString(1));
                user.setUsername(rs.getString(2));
                user.setEmail(rs.getString(5));
                user.setRoleId(rs.getInt(6));
                user.setIsDisabled(rs.getByte(7));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateUser(User user) {
        String currentEmail = getCurrentEmailById(user.getId());
        if (!user.getEmail().equals(currentEmail) && isEmailExist(user.getEmail())) {
            return false; // Email đã tồn tại
        }
        String sql = "Update dbo.[User] set email=? , role_id=?, isDisabled=? where id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getEmail());
            ps.setInt(2, user.getRoleId());
            ps.setByte(3, user.getIsDisabled());
            ps.setString(4, user.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private String getCurrentEmailById(String userId) {
        String sql = "SELECT email FROM dbo.[User] WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("email");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean isEmailExist(String email) {
        String sql = "SELECT COUNT(*) FROM dbo.[User] WHERE email = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createNewUser(String user_name, String email, int role_id, byte isDisable) {
        String sql = "insert into [User] values(?,?,?,?,?,?,?) ";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            String userId;
            if (getLatest() != null) {
                userId = generateId(getLatest().getId());
            } else {
                userId = "U000001";
            }
            statement.setString(1, userId);
            statement.setString(2, user_name.toLowerCase());
            String password = generatePassword();
            byte[] salt = PasswordUtil.generateSalt();
            statement.setBytes(3, salt);
            byte[] hashedPassword = PasswordUtil.hashPassword(password.toCharArray(), salt);
            statement.setBytes(4, hashedPassword);
            statement.setString(5, email);
            statement.setInt(6, role_id);
            statement.setByte(7, isDisable);
            statement.executeUpdate();
            Email.sendEmail(email, "Tạo tài khoản thành công", "Tên tài khoản: " + user_name.toLowerCase() + "||Mật khẩu: " + password);
            updatePersonnelUserId(user_name, userId);
            updateStudentsUserId(user_name, userId);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    private void updatePersonnelUserId(String personnelId, String userId) {
        String sql = "update [Personnels] set user_id = ? where id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userId);
            statement.setString(2, personnelId);
            statement.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void updateStudentsUserId(String PupilsId, String userId) {
        String sql = "update Pupils set user_id = ? where id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, userId);
            statement.setString(2, PupilsId);
            statement.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private User getLatest() {
        String sql = "select TOP 1 * from [User] order by id desc";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createUser(resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String generateId(String latestId) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(latestId);
        int number = 0;
        if (matcher.find()) {
            number = Integer.parseInt(matcher.group()) + 1;
        }
        DecimalFormat decimalFormat = new DecimalFormat("000000");
        String result = decimalFormat.format(number);
        return "U" + result;
    }
    
    


}
