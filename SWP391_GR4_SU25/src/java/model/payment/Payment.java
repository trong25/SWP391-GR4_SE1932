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
    private String dayId;
    private int amount;
    private String status;
    private Date paymentDate;
    private Date dueDate;
    private String note;

    public Payment() {
    }

    public Payment(Integer id, String code, String studentId, String classId, String dayId, int amount, String status, Date paymentDate, Date dueDate, String note) {
        this.id = id;
        this.code = code;
        this.studentId = studentId;
        this.classId = classId;
        this.dayId = dayId;
        this.amount = amount;
        this.status = status;
        this.paymentDate = paymentDate;
        this.dueDate = dueDate;
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

    public String getDayId() {
        return dayId;
    }

    public void setDayId(String dayId) {
        this.dayId = dayId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

   

}
