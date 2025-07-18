/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.Salaries;

import java.util.Date;

/**
 *
 * @author MSI
 */
public class Salary {
      private int id;
    private String personnelId;
    private int salaryMonth;
    private int salaryYear;
    private float baseSalary;
    private float totalSalary;
    private String paymentStatus;
    private Date paymentDate;

    public Salary() {
    }

    public Salary(int id, String personnelId, int salaryMonth, int salaryYear, float baseSalary, float totalSalary, String paymentStatus, Date paymentDate) {
        this.id = id;
        this.personnelId = personnelId;
        this.salaryMonth = salaryMonth;
        this.salaryYear = salaryYear;
        this.baseSalary = baseSalary;
        this.totalSalary = totalSalary;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(String personnelId) {
        this.personnelId = personnelId;
    }

    public int getSalaryMonth() {
        return salaryMonth;
    }

    public void setSalaryMonth(int salaryMonth) {
        this.salaryMonth = salaryMonth;
    }

    public int getSalaryYear() {
        return salaryYear;
    }

    public void setSalaryYear(int salaryYear) {
        this.salaryYear = salaryYear;
    }

    public float getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(float baseSalary) {
        this.baseSalary = baseSalary;
    }

    public float getTotalSalary() {
        return totalSalary;
    }

    public void setTotalSalary(float totalSalary) {
        this.totalSalary = totalSalary;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
    
    
}
