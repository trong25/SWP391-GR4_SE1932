/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.classes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.grade.GradeDAO;
import model.personnel.PersonnelDAO;
import model.schoolYear.SchoolYearDAO;
import utils.DBContext;

/**
 *
 * @author MSI
 */
public class ClassDAO extends DBContext{
    private Class createClass(ResultSet resultSet) throws SQLException {
        Class c = new Class();
        c.setId(resultSet.getString("id"));
        c.setName(resultSet.getString("name"));
        GradeDAO gradeDAO = new GradeDAO();
        c.setGrade(gradeDAO.getGrade(resultSet.getString("grade_id")));
         PersonnelDAO personnelDAO = new PersonnelDAO();
        c.setTeacher(personnelDAO.getPersonnel(resultSet.getString("teacher_id")));
         SchoolYearDAO schoolYearDAO = new SchoolYearDAO();
        c.setSchoolYear(schoolYearDAO.getSchoolYear(resultSet.getString("school_year_id")));
        c.setStatus(resultSet.getString("status"));
        c.setCreatedBy(personnelDAO.getPersonnel(resultSet.getString("created_by")));
        return c;
    }
    public List<Class> getByStatus(String status, String schoolYearId) {
        String sql = " Select * from Class where [status] = N'" + status + "'  and school_year_id = ? order by id desc";
        try {
            List<Class> classes = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, schoolYearId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Class c = createClass(resultSet);
                classes.add(c);
            }
            return classes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public Class getClassById(String id) {
        String sql = "select * from [Class] where id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createClass(resultSet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    
    public Class getClassNameByTeacher(String teacherId) {
        String sql = "SELECT *\n"
                + "                FROM Class\n"
                + "                WHERE teacher_id = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, teacherId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return createClass(rs);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public String getClassNameByTeacherAndTimetable(String teacherId, String date) {
        String sql = "SELECT DISTINCT c.name\n"
                + "FROM Class c\n"
                + "JOIN Timetables t ON c.id = t.class_id\n"
                + "JOIN Days d ON t.date_id = d.id\n"
                + "WHERE t.teacher_id = ?\n"
                + "  AND ? = d.date and t.status = N'đã được duyệt';\n";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, teacherId);
            preparedStatement.setString(2, date);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
        public Class getTeacherClassByYear(String year, String teacherId) {
        String sql = "select * from Class c where teacher_id= ? and school_year_id= ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, teacherId);
            preparedStatement.setString(2, year);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createClass(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
