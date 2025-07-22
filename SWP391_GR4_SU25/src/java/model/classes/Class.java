/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.classes;

import model.grade.Grade;
import model.personnel.Personnel;
import model.schoolYear.SchoolYear;

/**
 * Lớp Class khai báo các thuộc tính của một lớp học trong hệ thống. Được sử
 * dụng để quản lý thông tin về lớp học như: mã, tên, khối, giáo viên,năm học,
 * trạng thái, mô tả và người tạo.
 *
 * @author TrongNV
 * @version1.0
 */
public class Class {

    private String id;
    private String name;
    private Grade grade;
    private Personnel teacher;
    private SchoolYear schoolYear;
    private String status;
    private String classType;
    private Personnel createdBy;
    private int fee;

    public Class() {
    }


    public Class(String id, String name, Grade grade, Personnel teacher, SchoolYear schoolYear, String status, String classType, Personnel createdBy) {

    public Class(String id, String name, Grade grade, Personnel teacher, SchoolYear schoolYear, String status, Personnel createdBy, int fee) {

        this.id = id;
        this.name = name;
        this.grade = grade;
        this.teacher = teacher;
        this.schoolYear = schoolYear;
        this.status = status;
        this.classType = classType;
        this.createdBy = createdBy;
        this.fee = fee;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Personnel getTeacher() {
        return teacher;
    }

    public void setTeacher(Personnel teacher) {
        this.teacher = teacher;
    }

    public SchoolYear getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(SchoolYear schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Personnel getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Personnel createdBy) {
        this.createdBy = createdBy;
    }

    
    
}


}

