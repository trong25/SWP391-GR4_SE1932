/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.student;

import java.util.Date;
import model.personnel.Personnel;
import model.school.Schools;
import model.schoolclass.SchoolClass;

/**
 *Lớp Student khai báo các thuộc tính của  học sinh trong hệ thống.
 * Được sử dụng để quản lý thông tin về lớp học như: mã, tên,  trạng thái,địa chỉ, ngày sinh
 *,tên bố, tên mẹ, sdt bố và mẹ, trường học và lớp học mô tả , kiểu môn học và người tạo.
 * @author TrongNV
 * @author MSI
 */
public class Student {

    private String id;
    private String userId;
    private String firstName;
    private String lastName;
    private String address;
    private String email;
    private String status;
    private Date birthday;
    private boolean gender;
    private String firstGuardianName;
    private String firstGuardianPhoneNumber;
    private String avatar;
    private String secondGuardianName;
    private String secondGuardianPhoneNumber;
    private Personnel createdBy;
    private String parentSpecialNote;
    private Schools school_id;
    private SchoolClass school_class_id;

    public Student() {
    }

    public Student(String id, String userId, String firstName, String lastName, String address, String email, String status, Date birthday, boolean gender, String firstGuardianName, String firstGuardianPhoneNumber, String avatar, String secondGuardianName, String secondGuardianPhoneNumber, Personnel createdBy, String parentSpecialNote, Schools school_id, SchoolClass school_class_id) {
        this.id = id;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.status = status;
        this.birthday = birthday;
        this.gender = gender;
        this.firstGuardianName = firstGuardianName;
        this.firstGuardianPhoneNumber = firstGuardianPhoneNumber;
        this.avatar = avatar;
        this.secondGuardianName = secondGuardianName;
        this.secondGuardianPhoneNumber = secondGuardianPhoneNumber;
        this.createdBy = createdBy;
        this.parentSpecialNote = parentSpecialNote;
        this.school_id = school_id;
        this.school_class_id = school_class_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getFirstGuardianName() {
        return firstGuardianName;
    }

    public void setFirstGuardianName(String firstGuardianName) {
        this.firstGuardianName = firstGuardianName;
    }

    public String getFirstGuardianPhoneNumber() {
        return firstGuardianPhoneNumber;
    }

    public void setFirstGuardianPhoneNumber(String firstGuardianPhoneNumber) {
        this.firstGuardianPhoneNumber = firstGuardianPhoneNumber;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSecondGuardianName() {
        return secondGuardianName;
    }

    public void setSecondGuardianName(String secondGuardianName) {
        this.secondGuardianName = secondGuardianName;
    }

    public String getSecondGuardianPhoneNumber() {
        return secondGuardianPhoneNumber;
    }

    public void setSecondGuardianPhoneNumber(String secondGuardianPhoneNumber) {
        this.secondGuardianPhoneNumber = secondGuardianPhoneNumber;
    }

    public Personnel getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Personnel createdBy) {
        this.createdBy = createdBy;
    }

    public String getParentSpecialNote() {
        return parentSpecialNote;
    }

    public void setParentSpecialNote(String parentSpecialNote) {
        this.parentSpecialNote = parentSpecialNote;
    }

    public Schools getSchool_id() {
        return school_id;
    }

    public void setSchool_id(Schools school_id) {
        this.school_id = school_id;
    }

    public SchoolClass getSchool_class_id() {
        return school_class_id;
    }

    public void setSchool_class_id(SchoolClass school_class_id) {
        this.school_class_id = school_class_id;
    }

    @Override
    public String toString() {
return "Student{" + "id=" + id + ", userId=" + userId + ", firstName=" + firstName + ", lastName=" + lastName + ", address=" + address + ", email=" + email + ", status=" + status + ", birthday=" + birthday + ", gender=" + gender + ", firstGuardianName=" + firstGuardianName + ", firstGuardianPhoneNumber=" + firstGuardianPhoneNumber + ", avatar=" + avatar + ", secondGuardianName=" + secondGuardianName + ", secondGuardianPhoneNumber=" + secondGuardianPhoneNumber + ", createdBy=" + createdBy + ", parentSpecialNote=" + parentSpecialNote + ", school_id=" + school_id + ", school_class_id=" + school_class_id + '}';
    }

   
}