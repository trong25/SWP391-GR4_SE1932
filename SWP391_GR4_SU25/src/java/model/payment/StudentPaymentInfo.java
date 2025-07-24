package model.payment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StudentPaymentInfo {

    private String id;
    private String studentId;
    private String firstName;
    private String lastName;
    private String email;
    private String firstGuardianPhoneNumber;
    private int month;
    private float amount;
    private LocalDateTime dueTo;
    private String status;
    private String note;

    // Constructors
    public StudentPaymentInfo() {
    }

    public StudentPaymentInfo(String id, String studentId, String firstName, String lastName, String email, String firstGuardianPhoneNumber, int month, float amount, LocalDateTime dueTo, String status, String note) {
        this.id = id;
        this.studentId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.firstGuardianPhoneNumber = firstGuardianPhoneNumber;
        this.month = month;
        this.amount = amount;
        this.dueTo = dueTo;
        this.status = status;
        this.note = note;
    }


    // THÊM METHOD NÀY - Quan trọng!
    public String getDueDate() {
        if (dueTo != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return dueTo.format(formatter);
        }
        return "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstGuardianPhoneNumber() {
        return firstGuardianPhoneNumber;
    }

    public void setFirstGuardianPhoneNumber(String firstGuardianPhoneNumber) {
        this.firstGuardianPhoneNumber = firstGuardianPhoneNumber;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public LocalDateTime getDueTo() {
        return dueTo;
    }

    public void setDueTo(LocalDateTime dueTo) {
        this.dueTo = dueTo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    
}
