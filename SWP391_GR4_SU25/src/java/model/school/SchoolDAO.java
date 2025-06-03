package model.school;

import java.sql.Connection;
import java.sql.PreparedStatement;

import java.util.ArrayList;
import java.util.List;
import model.school.Schools;
import utils.DBContext;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SchoolDAO extends DBContext{
   
    public List<Schools> getAllSchools() {
        String sql = "SELECT * FROM Schools";

        List<Schools> listSchools = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Schools school = new Schools();
                school.setId(rs.getString("id"));
                school.setSchoolName(rs.getString("schoolName"));
                school.setAddressSchool(rs.getString("addressSchool"));
                school.setEmail(rs.getString("email"));
                listSchools.add(school);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi khi lấy danh sách trường học", e);
        }
        return listSchools;
    }
}


