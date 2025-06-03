/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.schoolclass;

/**
 *
 * @author MSI
 */
public class SchoolClass {
     private String id;
    private String school_Id;
    private String className;
    private String grade_level;

    public SchoolClass() {
    }

    public SchoolClass(String id, String school_Id, String className, String grade_level) {
        this.id = id;
        this.school_Id = school_Id;
        this.className = className;
        this.grade_level = grade_level;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchool_Id() {
        return school_Id;
    }

    public void setSchool_Id(String school_Id) {
        this.school_Id = school_Id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getGrade_level() {
        return grade_level;
    }

    public void setGrade_level(String grade_level) {
        this.grade_level = grade_level;
    }

    @Override
    public String toString() {
        return "SchoolClass{" + "id=" + id + ", school_Id=" + school_Id + ", className=" + className + ", grade_level=" + grade_level + '}';
    }
    
}
