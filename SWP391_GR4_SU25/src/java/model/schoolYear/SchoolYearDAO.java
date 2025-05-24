/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.schoolYear;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.personnel.Personnel;
import model.personnel.PersonnelDAO;
import utils.DBContext;

/**
 *
 * @author MSI
 */
public class SchoolYearDAO extends DBContext {

    private SchoolYear createNewSchoolYear(ResultSet rs) throws SQLException {
        SchoolYear schoolYear = new SchoolYear();
        schoolYear.setId(rs.getString("id"));
        schoolYear.setName(rs.getString("name"));
        schoolYear.setStartDate(rs.getDate("start_date"));
        schoolYear.setEndDate(rs.getDate("end_date"));
        schoolYear.setDescription(rs.getString("description"));
        PersonnelDAO personnelDAO = new PersonnelDAO();
        Personnel personnel = personnelDAO.getPersonnel(rs.getString("created_by"));
        schoolYear.setCreatedBy(personnel);
        return schoolYear;
    }

    public List<SchoolYear> getAll() {
        List<SchoolYear> schoolYears = new ArrayList<SchoolYear>();
        String sql = "select * from schoolYears order by id desc";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                SchoolYear schoolYear = createNewSchoolYear(rs);
                schoolYears.add(schoolYear);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return schoolYears;
    }

    public SchoolYear getSchoolYear(String id) {
        String sql = "select * from schoolYears where id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                SchoolYear schoolYear = createNewSchoolYear(rs);
                return schoolYear;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public SchoolYear getClosestSchoolYears() {
        String sql = "select top 1  * from schoolYears where end_date >= CAST(GETDATE() AS DATE) order by start_date";

        SchoolYear schoolYear = new SchoolYear();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                schoolYear = createNewSchoolYear(resultSet);

            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return schoolYear;
    }

    public SchoolYear getSchoolYearByDate(Date date) {
        String sql = "SELECT * FROM SchoolYears WHERE ? BETWEEN start_date AND end_date";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setDate(1, new java.sql.Date(date.getTime()));
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                SchoolYear schoolYear = new SchoolYear();
                schoolYear.setId(resultSet.getString("id"));
                schoolYear.setName(resultSet.getString("name"));
                schoolYear.setStartDate(resultSet.getDate("start_date"));
                schoolYear.setEndDate(resultSet.getDate("end_date"));
                schoolYear.setDescription(resultSet.getString("description"));

                return schoolYear;
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public SchoolYear getLatest() {
        String sql = "SELECT TOP 1 * FROM SchoolYears ORDER BY ID DESC";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return createNewSchoolYear(resultSet);

            }
        } catch (Exception e) {

            e.printStackTrace();

        }
        return null;
    }
}
