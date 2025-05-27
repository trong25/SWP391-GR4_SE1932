/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import utils.DBContext;
import java.sql.PreparedStatement;
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

        if (salt == null) {
            salt = PasswordUtil.generateSalt();
            byte[] hashedPassword = PasswordUtil.hashPassword(password.toCharArray(), salt);
            String sqlUpdate = "UPDATE [dbo].[User] SET salt = ?, hashed_password = ? WHERE user_name = ?";
            try (PreparedStatement psUp = connection.prepareStatement(sqlUpdate)) {
                psUp.setBytes(1, salt);
                psUp.setBytes(2, hashedPassword);
                psUp.setString(3, userName);
                psUp.executeUpdate();
            }
            return getUserByUsername(userName);
        }

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


}
