/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.subject;

import model.grade.Grade;

/**
 * Lớp Subject khai báo các thuộc tính của một môn học trong hệ thống. Được sử
 * dụng để quản lý thông tin về lớp học như: mã, tên, trạng thái, mô tả , kiểu
 * môn học và người tạo.
 *
 * @author TrongNV
 * @author MSI
 */
public class Subject {

    private String id;
    private String name;
    private Grade grade;
    private String description;
    private String status;
    private String subjectType;

    public Subject() {
    }

    public Subject(String id, String name, Grade grade, String description, String status, String subjectType) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.description = description;
        this.status = status;
        this.subjectType = subjectType;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }

}
