
package Controller.accountant;

import java.io.IOException;
import java.util.Date;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Salaries.Salary;
import model.personnel.Personnel;
import model.personnel.PersonnelDAO;


public class ViewPersonnelSalaryServlet extends HttpServlet {

    private float getSalaryPerDay(Personnel personnel) {
        String qualification = personnel.getQualification() != null ? personnel.getQualification() : "";
        int teachingYears = personnel.getTeaching_years();

        switch (qualification.toLowerCase()) {
            case "cử nhân":
                if (teachingYears < 3) return 100_000;
                else if (teachingYears <= 5) return 150_000;
                else return 180_000;
            case "thạc sĩ":
                if (teachingYears < 3) return 150_000;
                else if (teachingYears <= 5) return 180_000;
                else return 200_000;
            case "tiến sĩ":
                if (teachingYears < 3) return 200_000;
                else if (teachingYears <= 5) return 250_000;
                else return 300_000;
            default:
                return 100_000;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String xid = request.getParameter("id");
        String month = request.getParameter("month");
        String year = request.getParameter("year");

        System.out.println("doGet - Parameters: id=" + xid + ", month=" + month + ", year=" + year);

        if (xid == null || xid.trim().isEmpty()) {
            request.getSession().setAttribute("message", "Mã nhân viên không hợp lệ!");
            request.getSession().setAttribute("type", "fail");
            response.sendRedirect("listpersonnel");
            return;
        }
        if (month == null || year == null) {
            request.getSession().setAttribute("message", "Thiếu thông tin tháng hoặc năm!");
            request.getSession().setAttribute("type", "fail");
            response.sendRedirect("listpersonnell");
            return;
        }

        int salaryMonth, salaryYear;
        try {
            salaryMonth = Integer.parseInt(month);
            salaryYear = Integer.parseInt(year);
            if (salaryMonth < 1 || salaryMonth > 12 || salaryYear < 2020 || salaryYear > 2100) {
                throw new NumberFormatException("Tháng hoặc năm ngoài phạm vi hợp lệ!");
            }
            request.setAttribute("selectedMonth", salaryMonth);
            request.setAttribute("selectedYear", salaryYear);
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("message", "Tháng hoặc năm không hợp lệ!");
            request.getSession().setAttribute("type", "fail");
            response.sendRedirect("listpersonnell");
            return;
        }

        PersonnelDAO personnelDAO = new PersonnelDAO();
        Personnel person = personnelDAO.getPersonnel(xid);

        if (person == null) {
            request.getSession().setAttribute("message", "Không tìm thấy nhân viên với ID: " + xid);
            request.getSession().setAttribute("type", "fail");
            response.sendRedirect("listpersonnell");
            return;
        }

        for (Salary salary : person.getSalaries()) {
            if (salary.getSalaryMonth() == salaryMonth && salary.getSalaryYear() == salaryYear) {
                int workingDays = personnelDAO.getWorkingDaysByMonth(xid, salaryMonth, salaryYear);
                System.out.println("Working Days for ID " + xid + ", Month " + salaryMonth + ", Year " + salaryYear + ": " + workingDays);
                salary.setWorkingDays(workingDays);
                float salaryPerDay = getSalaryPerDay(person);
                float totalSalary = salaryPerDay * workingDays;
                salary.setTotalSalary(totalSalary);
                personnelDAO.updateTotalSalary(xid, salaryMonth, salaryYear, totalSalary);
            }
        }

        request.setAttribute("person", person);
        request.getRequestDispatcher("viewPersonnelSalarylnformation.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String month = request.getParameter("month");
        String year = request.getParameter("year");

        System.out.println("doPost - Parameters: id=" + id + ", month=" + month + ", year=" + year);

        if (id == null || id.trim().isEmpty() || month == null || year == null) {
            request.getSession().setAttribute("message", "Thông tin đầu vào không hợp lệ!");
            request.getSession().setAttribute("type", "fail");
            response.sendRedirect("viewpersonnel?id=" + id + "&month=" + month + "&year=" + year);
            return;
        }

        try {
            int salaryMonth = Integer.parseInt(month);
            int salaryYear = Integer.parseInt(year);
            PersonnelDAO personnelDAO = new PersonnelDAO();
            boolean updated = personnelDAO.updateSalaryStatus(id, "đã thanh toán", salaryMonth, salaryYear, new Date());

            if (updated) {
                request.getSession().setAttribute("message", "Cập nhật trạng thái thanh toán thành công!");
                request.getSession().setAttribute("type", "success");
            } else {
                request.getSession().setAttribute("message", "Không tìm thấy bảng lương cho tháng/năm này!");
                request.getSession().setAttribute("type", "fail");
            }
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("message", "Tháng hoặc năm không hợp lệ!");
            request.getSession().setAttribute("type", "fail");
        } catch (Exception e) {
            request.getSession().setAttribute("message", "Lỗi khi cập nhật trạng thái thanh toán!");
            request.getSession().setAttribute("type", "fail");
            System.out.println("Error in doPost: " + e.getMessage());
        }

        response.sendRedirect("viewpersonnel?id=" + id + "&month=" + month + "&year=" + year);
    }

    @Override
    public String getServletInfo() {
        return "ViewPersonnelSalaryServlet for displaying and updating salary information";
    }
}
