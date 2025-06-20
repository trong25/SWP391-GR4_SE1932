package model.role;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author ThanhNT
 *
 */
public class Role {

    private int id;
    private String description;

    public Role() {
    }

    public Role(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVNeseDescription() {
        switch (this.id) {
            case 0:
                return "Nhân viên IT";
            case 1:
                return "Giám đốc";
            case 2:
                return "Giáo vụ";
            case 3:
                return "Giáo viên";
            default:
                return "";
        }
    }
}
