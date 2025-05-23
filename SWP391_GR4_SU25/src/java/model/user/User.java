/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.user;

/**
 *
 * @author MSI
 */
public class User {
      private String id;
    private String username;
    private String email;
    private int roleId;
    private byte isDisabled;

    public User() {
    }

    public User(String id, String username, String email, int roleId, byte isDisabled) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.roleId = roleId;
        this.isDisabled = isDisabled;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public byte getIsDisabled() {
        return isDisabled;
    }

    public void setIsDisabled(byte isDisabled) {
        this.isDisabled = isDisabled;
    }
    
    
}
