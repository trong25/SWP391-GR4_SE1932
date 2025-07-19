/*
 * Servlet xử lý tạo bảng lương mới cho nhân viên
 * Tính lương tự động dựa trên năm kinh nghiệm và trình độ
 * @author MSI
 */
package Controller.accountant;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.Salaries.Salary;
import model.Salaries.SalaryDAO;
import model.personnel.Personnel;
import model.personnel.PersonnelDAO;
import model.role.Role;

public class CreateSalaryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("listpersonnell");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String action = request.getParameter("action");

        if ("create".equals(action)) {
            // Lấy dữ liệu từ form
            String personnelId = request.getParameter("personnelId");
            int salaryMonth;
            int salaryYear;
            String paymentStatus = request.getParameter("paymentStatus");
            String paymentDateStr = request.getParameter("paymentDate");

            // Validate dữ liệu
            try {
                salaryMonth = Integer.parseInt(request.getParameter("salaryMonth"));
                salaryYear = Integer.parseInt(request.getParameter("salaryYear"));
                if (salaryMonth < 1 || salaryMonth > 12 || salaryYear < 2020 || salaryYear > 2100) {
                    throw new NumberFormatException("Tháng hoặc năm không hợp lệ!");
                }
            } catch (NumberFormatException e) {
                session.setAttribute("message", "Dữ liệu tháng hoặc năm không hợp lệ!");
                session.setAttribute("type", "fail");
                response.sendRedirect("listpersonnell");
                return;
            }

            // Kiểm tra ngày thanh toán
            Date paymentDate = null;
            if (paymentDateStr != null && !paymentDateStr.isEmpty()) {
                try {
                    paymentDate = java.sql.Date.valueOf(paymentDateStr);
                } catch (IllegalArgumentException e) {
                    session.setAttribute("message", "Ngày thanh toán không hợp lệ!");
                    session.setAttribute("type", "fail");
                    response.sendRedirect("listpersonnell");
                    return;
                }
            }

            // Kiểm tra trùng tháng/năm
            SalaryDAO salaryDAO = new SalaryDAO();
            boolean existed = salaryDAO.checkExistingSalary(personnelId, salaryMonth, salaryYear);
            if (existed) {
                session.setAttribute("message", "Bảng lương đã tồn tại cho nhân viên này trong tháng/năm đã chọn!");
                session.setAttribute("type", "fail");
                response.sendRedirect("listpersonnell");
                return;
            }

            // Lấy thông tin nhân viên để tính lương
            PersonnelDAO personnelDAO = new PersonnelDAO();
            Personnel personnel = personnelDAO.getPersonnel(personnelId);
            if (personnel == null) {
                session.setAttribute("message", "Không tìm thấy nhân viên với ID: " + personnelId);
                session.setAttribute("type", "fail");
                response.sendRedirect("listpersonnell");
                return;
            }

            // Tính lương cơ bản dựa trên trình độ
            float baseSalary;
            String qualification = personnel.getQualification() != null ? personnel.getQualification().toLowerCase() : "";
            switch (qualification) {
                case "cử nhân":
                    baseSalary = 7_000_000;
                    break;
                case "thạc sĩ":
                    baseSalary = 12_000_000;
                    break;
                case "tiến sĩ":
                    baseSalary = 15_000_000;
                    break;
                default:
                    baseSalary = 5_000_000; // Giá trị mặc định nếu trình độ không xác định
                    break;
            }

            // Điều chỉnh lương dựa trên năm kinh nghiệm
            Integer teachingYears = personnel.getTeaching_years();
            if (teachingYears != null) {
                if (teachingYears >= 5 && teachingYears <= 10) {
                    baseSalary *= 1.5; // +20%
                } else if (teachingYears > 10) {
                    baseSalary *= 2.0; // +50%
                }
            }

            // Tổng lương (giả định bằng lương cơ bản, có thể thêm phụ cấp sau)
            float totalSalary = baseSalary;

            // Lưu bảng lương mới
            Salary salary = new Salary();
            salary.setPersonnelId(personnelId);
            salary.setSalaryMonth(salaryMonth);
            salary.setSalaryYear(salaryYear);
            salary.setBaseSalary(baseSalary);
            salary.setTotalSalary(totalSalary);
            salary.setPaymentStatus(paymentStatus);
            salary.setPaymentDate(paymentDate);

            try {
                salaryDAO.insertSalary(salary);
                session.setAttribute("message", "Tạo bảng lương thành công! Lương cơ bản: " + baseSalary + " VNĐ");
                session.setAttribute("type", "success");
            } catch (Exception e) {
                session.setAttribute("message", "Lỗi khi tạo bảng lương: " + e.getMessage());
                session.setAttribute("type", "fail");
            }

            // Lấy bộ lọc hiện tại (nếu có)
            String role = request.getParameter("role");
            String status = request.getParameter("status");
            String month = request.getParameter("month");

            List<Personnel> persons;
            List<Role> roles = personnelDAO.getAllPersonnelRole();
            List<String> statuss = personnelDAO.getAllStatus();
            List<Personnel> activePersonnels = personnelDAO.getActivePersonnels();

            if (status == null) status = "all";
            if (role == null) role = "all";
            if (month == null) month = "all";

            if (status.equalsIgnoreCase("all") && role.equalsIgnoreCase("all") && month.equalsIgnoreCase("all")) {
                persons = personnelDAO.getAllPersonnelsWithSalary();
            } else if (!status.equalsIgnoreCase("all") && role.equalsIgnoreCase("all") && month.equalsIgnoreCase("all")) {
                persons = personnelDAO.getPersonnelByStatus(status);
            } else if (!role.equalsIgnoreCase("all") && status.equalsIgnoreCase("all") && month.equalsIgnoreCase("all")) {
                try {
                    int xrole = Integer.parseInt(role);
                    persons = personnelDAO.getPersonnelByRole(xrole);
                } catch (NumberFormatException e) {
                    persons = personnelDAO.getPersonnelByRole(-1);
                }
            } else if (!month.equalsIgnoreCase("all")) {
                try {
                    int m = Integer.parseInt(month);
                    persons = personnelDAO.getPersonnelByMonthWithSalary(m);
                } catch (NumberFormatException e) {
                    persons = new ArrayList<>();
                }
            } else {
                persons = personnelDAO.getPersonnelByStatusRoleMonth(status, role, month);
            }

            request.setAttribute("persons", persons);
            request.setAttribute("roles", roles);
            request.setAttribute("statuss", statuss);
            request.setAttribute("activePersonnels", activePersonnels);
            request.setAttribute("selectedstatus", status);
            request.setAttribute("selectedrole", role);
            request.setAttribute("selectedmonth", month);
            request.setAttribute("currentYear", java.time.Year.now().getValue());
            request.getRequestDispatcher("listPersonnel.jsp").forward(request, response);
        }
    }
}