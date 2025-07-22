/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.grade;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import utils.DBContext;

/**
 *
 * @author MSI
 */
public class GradeDAO extends DBContext{
    private Grade createGrade(ResultSet resultSet) throws SQLException {
        Grade grade = new Grade();
        grade.setId(resultSet.getString("id"));
        grade.setName(resultSet.getString("name"));
        grade.setDescription(resultSet.getString("description"));
        return grade;
    }
     public Grade getGrade(String gradeId) {
        String sql = "select * from Grades where id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, gradeId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()) {
                Grade grade = new Grade();
                grade.setId(resultSet.getString("id"));
                grade.setName(resultSet.getString("name"));
                grade.setDescription(resultSet.getString("description"));
                return grade;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
      public List<Grade> getAll() {
        String sql = "select * from Grades";
        List<Grade> grades = new ArrayList<>();
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()) {
                Grade grade = createGrade(resultSet);
                grades.add(grade);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return grades;
    }
}