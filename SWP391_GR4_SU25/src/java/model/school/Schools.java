/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.school;

/**
 *
 * @author MSI
 */
public class Schools {
    private String id;
    private String schoolName;
    private String addressSchool;
    private String email;

    public Schools() {
    }

    public Schools(String id, String schoolName, String addressSchool, String email) {
        this.id = id;
        this.schoolName = schoolName;
        this.addressSchool = addressSchool;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getAddressSchool() {
        return addressSchool;
    }

    public void setAddressSchool(String addressSchool) {
        this.addressSchool = addressSchool;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
}
