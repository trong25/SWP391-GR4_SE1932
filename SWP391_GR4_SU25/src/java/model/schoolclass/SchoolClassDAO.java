/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.schoolclass;


import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import utils.DBContext;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author MSI
 */
public class SchoolClassDAO extends DBContext {

    public List<SchoolClass> getSchoolClassesBySchoolId(String schoolId) {
        String sql = "SELECT * FROM SchoolClasses WHERE school_id = ? ORDER BY class_name";

        List<SchoolClass> classList = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, schoolId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                SchoolClass sc = new SchoolClass();
                sc.setId(rs.getString("id"));
                sc.setSchool_Id(rs.getString("school_id"));
                sc.setClassName(rs.getString("class_name"));
                sc.setGrade_level(rs.getString("grade_level"));
                classList.add(sc);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy danh sách lớp học theo trường", e);
        }

        return classList;
    }
    
    public SchoolClass getSchoolClassesById(String schoolId) {
    String sql = "SELECT * FROM SchoolClasses WHERE id = ?";

    List<SchoolClass> classList = new ArrayList<>();
    try {
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, schoolId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            SchoolClass sc = new SchoolClass();
            sc.setId(rs.getString("id"));
            sc.setSchool_Id(rs.getString("school_id"));
            sc.setClassName(rs.getString("class_name"));
            sc.setGrade_level(rs.getString("grade_level"));
            classList.add(sc);
        }
    } catch (SQLException e) {
        throw new RuntimeException("Lỗi khi lấy danh sách lớp học theo trường", e);
    }

    if(classList.isEmpty()) return null;
    return classList.get(0);
}

}
