/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.student;

/**
 *
 * @author ThanhNT
 */
public class StudentAttendanceSummary {

    private String studentId;
    private String avatar;
    private String firstName;
    private String lastName;
    private String classId;
    private String className;
    private int fee;
    private int presentCount;
    private int absentCount;

    public StudentAttendanceSummary() {
    }

    // Constructor đầy đủ
    public StudentAttendanceSummary(String studentId, String avatar, String firstName, String lastName, String classId, String className, int fee, int presentCount, int absentCount) {
        this.studentId = studentId;
        this.avatar = avatar;
        this.firstName = firstName;
        this.lastName = lastName;
        this.classId = classId;
        this.className = className;
        this.fee = fee;
        this.presentCount = presentCount;
        this.absentCount = absentCount;
    }

    // Getters
    public String getStudentId() {
        return studentId;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getClassId() {
        return classId;
    }

    public String getClassName() {
        return className;
    }

    public int getFee() {
        return fee;
    }

    public int getPresentCount() {
        return presentCount;
    }

    public int getAbsentCount() {
        return absentCount;
    }

    // Setters
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public void setPresentCount(int presentCount) {
        this.presentCount = presentCount;
    }

    public void setAbsentCount(int absentCount) {
        this.absentCount = absentCount;
    }

    // Phương thức tiện ích
    public String getFullName() {
        return firstName + " " + lastName;
    }

    public int getTotalAttendance() {
        return presentCount + absentCount;
    }

    public double getAttendanceRate() {
        int total = getTotalAttendance();
        return total > 0 ? (double) presentCount / total * 100 : 0.0;
    }

}
