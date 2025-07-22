/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.payment;

import java.util.Date;

/**
 *
 * @author ThanhNT
 */
public class Payment {

   private Integer id;
    private String code;
    private String studentId;
    private String classId;
    private Integer month;
    private Integer year;
    private Float amount;
    private String status;
    private Date dueTo;
    private Date paymentDate;
    private String note;

    public Payment() {
    }

    public Payment(Integer id, String code, String studentId, String classId, Integer month, Integer year, Float amount, String status, Date dueTo, Date paymentDate, String note) {
        this.id = id;
        this.code = code;
        this.studentId = studentId;
        this.classId = classId;
        this.month = month;
        this.year = year;
        this.amount = amount;
        this.status = status;
        this.dueTo = dueTo;
        this.paymentDate = paymentDate;
        this.note = note;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDueTo() {
        return dueTo;
    }

    public void setDueTo(Date dueTo) {
        this.dueTo = dueTo;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

   


}
