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
import java.util.Objects;
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
        
    private byte[] getUserSalt(String username){
        byte[] salt = null;
        String sql = "select salt from [User] where user_name = ?";
        try{
          PreparedStatement statement = connection.prepareStatement(sql);
         statement.setString(1, username);
         ResultSet rs = statement.executeQuery();
         
         if(rs.next()){
             salt = rs.getBytes("salt");
         }
        }catch (Exception e){
            e.printStackTrace();
        }
        return salt;
    }



public User getUserByUsernamePassword(String userName, String password){
    try {
        byte[] salt = getUserSalt(userName);

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
        String sql = "SELECT * FROM [dbo].[User] WHERE user_name = ? AND hashed_password = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, userName);
            ps.setBytes(2, hashedPassword);
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
          
          
     public User getByUsernameOrEmail (String key){
              
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
         
         
         
                   public boolean resetPassword(String key){
             try{
                 String newPassword = generatePassword();
                 Email.sendEmail(Objects.requireNonNull(getByUsernameOrEmail(key)).getEmail(), "Yêu cầu quên mật khẩu", "Mật khẩu mới: " + newPassword);
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
                
             }catch (Exception e){
                 e.printStackTrace();
             } 
              
          return false;    
          }
                   
                   
                   
                   
            public boolean checkPassword(String password, String username){  
                try{
                    byte[] salt = getUserSalt(username);
                    byte[] expectedHashPassword = PasswordUtil.hashPassword(password.toCharArray(), salt);
                    String sql = "select [hashed_password] from [User] where user_name = ?";
                    PreparedStatement statement = connection.prepareStatement(sql);
                    statement.setString(1, username);
                    ResultSet rs = statement.executeQuery();
                    if (rs.next()) {
                    byte[] hashPassword = rs.getBytes("hashed_password");
                    String expectedHex = bytesToHex(expectedHashPassword);
                    String actualHex = bytesToHex(hashPassword);
                    // Compare the hexadecimal representations
                    return expectedHex.equals(actualHex);
            }
                    
                }catch(Exception e){
                    e.printStackTrace();
                }
                return false;
             
            }
            
            
                         
     private String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
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
            
                
     
     
     
     
 


}
