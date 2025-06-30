package model.personnel;

import java.util.Date;

public class Personnel {

    private String id;
    private String firstName;
    private String lastName;
    private boolean gender;
    private Date birthday;
    private String address;
    private String email;
    private String phoneNumber;
    private int roleId;
    private String status;
    private String avatar;
    private String userId;
    private String school_id;
    private String school_class_id;
    private String specialization;
    private String qualification;
    private int teaching_years;
    private String achievements;
    private String cv_file;
    private String schoolName;
    private String className;

    // Constructors
    public Personnel() {
    }

    public Personnel(String id, String firstName, String lastName, boolean gender, Date birthday, String address, String email, String phoneNumber, int roleId, String status, String avatar, String userId, String school_id, String school_class_id, String specialization, String qualification, int teaching_years, String achievements, String cv_file, String schoolName, String className) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthday = birthday;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.roleId = roleId;
        this.status = status;
        this.avatar = avatar;
        this.userId = userId;
        this.school_id = school_id;
        this.school_class_id = school_class_id;
        this.specialization = specialization;
        this.qualification = qualification;
        this.teaching_years = teaching_years;
        this.achievements = achievements;
        this.cv_file = cv_file;
        this.schoolName = schoolName;
        this.className = className;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSchool_id() {
        return school_id;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
    }

    public String getSchool_class_id() {
        return school_class_id;
    }

    public void setSchool_class_id(String school_class_id) {
        this.school_class_id = school_class_id;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public int getTeaching_years() {
        return teaching_years;
    }

    public void setTeaching_years(int teaching_years) {
        this.teaching_years = teaching_years;
    }

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    public String getCv_file() {
        return cv_file;
    }

    public void setCv_file(String cv_file) {
        this.cv_file = cv_file;
    }
}
