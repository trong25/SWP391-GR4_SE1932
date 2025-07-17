package model.role;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author ASUS VIVOBOOK
 */
public class Role {

    private String id;
    private String description;

    public Role(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public Role() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

            case "0":
                return "Nhân viên IT";
            case "1":
                return "Giám Đốc";
            case "2":
                return "Giáo vụ";
            case "3":
                return "Giáo viên";
            case "4":

            case "5":

                return "Kế toán";
        }
        return "";

    }

}
