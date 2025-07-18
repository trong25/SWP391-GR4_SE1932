package Controller.accountant;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.personnel.Personnel;
import model.personnel.PersonnelDAO;

/**
 * ViewPersonnelSalaryServlet chịu trách nhiệm lấy thông tin chi tiết của nhân viên để hiển thị
 * trên trang viewPersonnelSalaryInformation.jsp và cập nhật trạng thái thanh toán lương.
 * Sử dụng hàm getPersonnel() để lấy thông tin nhân viên và updateSalaryStatus() để cập nhật trạng thái.
 *
 * @author TrongNV
 * @version 1.1
 */
public class ViewPersonnelSalaryServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String xid = request.getParameter("id");
        String month = request.getParameter("month");
        String year = request.getParameter("year");

        // Ghi log để debug
        System.out.println("doGet - Parameters: id=" + xid + ", month=" + month + ", year=" + year);

        // Kiểm tra tham số đầu vào
        if (xid == null || xid.trim().isEmpty()) {
            request.getSession().setAttribute("message", "Mã nhân viên không hợp lệ!");
            request.getSession().setAttribute("type", "fail");
            response.sendRedirect("listpersonnell");
            return;
        }
        if (month == null || year == null) {
            request.getSession().setAttribute("message", "Thiếu thông tin tháng hoặc năm!");
            request.getSession().setAttribute("type", "fail");
            response.sendRedirect("listpersonnell");
            return;
        }

        // Kiểm tra định dạng tháng và năm
        try {
            int salaryMonth = Integer.parseInt(month);
            int salaryYear = Integer.parseInt(year);
            if (salaryMonth < 1 || salaryMonth > 12 || salaryYear < 2020 || salaryYear > 2100) {
                throw new NumberFormatException("Tháng hoặc năm ngoài phạm vi hợp lệ!");
            }
            request.setAttribute("selectedMonth", month);
            request.setAttribute("selectedYear", year);
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("message", "Tháng hoặc năm không hợp lệ!");
            request.getSession().setAttribute("type", "fail");
            response.sendRedirect("listpersonnell");
            return;
        }

        // Lấy thông tin nhân viên
        PersonnelDAO personnelDAO = new PersonnelDAO();
        Personnel person = personnelDAO.getPersonnel(xid);

        // Kiểm tra nhân viên có tồn tại không
        if (person == null) {
            request.getSession().setAttribute("message", "Không tìm thấy nhân viên với ID: " + xid);
            request.getSession().setAttribute("type", "fail");
            response.sendRedirect("listpersonnell");
            return;
        }

        // Thiết lập thuộc tính và chuyển tiếp tới JSP
        request.setAttribute("person", person);
        
        request.getRequestDispatcher("viewPersonnelSalarylnformation.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String id = request.getParameter("id");
        String month = request.getParameter("month");
        String year = request.getParameter("year");

        // Ghi log để debug
        System.out.println("doPost - Parameters: id=" + id + ", month=" + month + ", year=" + year);

        // Kiểm tra tham số
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
            boolean updated = personnelDAO.updateSalaryStatus(id, "đã thanh toán", salaryMonth, salaryYear);

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
}