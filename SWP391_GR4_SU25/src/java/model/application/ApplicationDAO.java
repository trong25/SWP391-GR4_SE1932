package model.application;

import model.personnel.PersonnelDAO;
import model.personnel.PersonnelDAO;
import model.schoolYear.SchoolYear;
import utils.DBContext;
import utils.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.sql.Types.NULL;

public class ApplicationDAO extends DBContext {

    private Application createApplication(ResultSet rs) throws SQLException {
        Application app = new Application();
        app.setId(rs.getString("id"));
        app.setProcessedAt(rs.getDate("processed_at"));
        app.setType(getById(rs.getString("application_type")));
        app.setDetails(rs.getString("details"));
        app.setProcessNote(rs.getString("process_note"));
        app.setStatus(rs.getString("status"));
        app.setCreatedBy(rs.getString("created_by"));
        app.setCreatedAt(rs.getDate("created_at"));
        app.setStartDate(rs.getDate("start_date"));
        app.setEndDate(rs.getDate("end_date"));
        PersonnelDAO personnelDAO = new PersonnelDAO();
        app.setProcessedBy(personnelDAO.getPersonnel(rs.getString("processed_by")));
        return app;
    }


    public ApplicationType getById(String id) {
        ApplicationType app = new ApplicationType();
        String sql = "select * from [Application_Types] where id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                app.setId(resultSet.getString("id"));
                app.setName(resultSet.getString("name"));
                app.setDescription(resultSet.getString("description"));
                return app;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<ApplicationType> getAllApplicationTypes(String role) {
        List<ApplicationType> applicationTypes = new ArrayList<ApplicationType>();
        String sql = "select id, name, description from [Application_Types] where sender_role = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, role);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                ApplicationType applicationType = new ApplicationType();
                applicationType.setId(resultSet.getString("id"));
                applicationType.setName(resultSet.getString("name"));
                applicationType.setDescription(resultSet.getString("description"));
                applicationTypes.add(applicationType);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return applicationTypes;
    }


    public List<Application> getForPersonnel(String role){
        List<Application> applications = new ArrayList<>();
        String sql = "SELECT a.*\n" +
                "FROM Applications a\n" +
                "JOIN Application_Types at\n" +
                "ON a.application_type = at.id\n" +
                "WHERE at.receiver_role = ?\n" +
                "ORDER BY id desc";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, role);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Application application = createApplication(resultSet);
                applications.add(application);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return applications;
    }


    public Application getApplicationById(String id){
        Application app = new Application();
        String sql = "select * from [Applications] where id = ? order by id desc";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                app = createApplication(resultSet);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return app;
    }


    public String addApplication(Application application) {
        String sql = "insert into [Applications] values (?,?,?,?,?,?,?,?,?,?,?)";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            if(getLatest()==null){
                preparedStatement.setString(1, "APP000001");
            } else {
                preparedStatement.setString(1, generateId(getLatest().getId()));
            }
            preparedStatement.setNull(2, Types.VARCHAR);
            preparedStatement.setString(3, application.getType().getId());
            preparedStatement.setString(4, application.getDetails());
            preparedStatement.setNull(5, Types.NVARCHAR);
            preparedStatement.setString(6, application.getStatus());
            preparedStatement.setString(7, application.getCreatedBy());
            preparedStatement.setString(8, Helper.convertDateToLocalDate(application.getCreatedAt()).toString());
            if (application.getStartDate()!=null){
                preparedStatement.setString(9, Helper.convertDateToLocalDate(application.getStartDate()).toString());
                preparedStatement.setString(10, Helper.convertDateToLocalDate(application.getEndDate()).toString());
            } else {
                preparedStatement.setNull(9, Types.VARCHAR);
                preparedStatement.setNull(10, Types.VARCHAR);
            }

            preparedStatement.setNull(11, Types.VARCHAR);
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
            return "Gửi đơn thất bại! Vui lòng thử lại sau";
        }
        return "success";
    }

    private Application getLatest(){
        String sql = "SELECT TOP 1 * FROM [Applications] ORDER BY ID DESC";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return createApplication(rs);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String generateId(String latestId){
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(latestId);
        int number = 0;
        if (matcher.find()) {
            number = Integer.parseInt(matcher.group()) + 1;
        }
        DecimalFormat decimalFormat = new DecimalFormat("000000");
        String result = decimalFormat.format(number);
        return "APP" + result;
    }


    public String processApplication(Application application) {
        String sql = "update [Applications] set status = ?, process_note = ?, processed_at = ?, processed_by = ? where id = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, application.getStatus());
            preparedStatement.setString(2, application.getProcessNote());
            preparedStatement.setString(3, Helper.convertDateToLocalDate(application.getProcessedAt()).toString());
            preparedStatement.setString(4, application.getProcessedBy().getId());
            preparedStatement.setString(5, application.getId());
            preparedStatement.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
            return "Xử lý đơn thất bại! Vui lòng thử lại sau";
        }
        return "success";
    }


    public List<Application> getSentApplications(String senderUserId){
        String sql = "select * from [Applications] where created_by = ? order by id desc";
        List<Application> applications = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, senderUserId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Application application = createApplication(resultSet);
                applications.add(application);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return applications;
    }


    public List<Application> getForPersonnelWithStatus(String role, String status) {
        List<Application> applications = new ArrayList<>();
        String sql = "SELECT a.*\n" +
                "FROM Applications a\n" +
                "JOIN Application_Types at\n" +
                "ON a.application_type = at.id\n" +
                "WHERE at.receiver_role = ? and status=?\n" +
                "ORDER BY id desc";
        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, role);
            statement.setString(2, status);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Application application = createApplication(resultSet);
                applications.add(application);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return applications;
    }


    public List<Application> getSentApplicationsWithStatus(String senderUserId, String status) {
        String sql = "select * from [Applications] where created_by = ? and status = ? order by id desc";
        List<Application> applications = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, senderUserId);
            preparedStatement.setString(2, status);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Application application = createApplication(resultSet);
                applications.add(application);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return applications;
    }
}
