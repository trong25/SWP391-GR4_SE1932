/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.accountant;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import model.personnel.Personnel;
import model.personnel.PersonnelDAO;
import model.role.Role;

/**
 * ListPersonnelServlet chịu trách nhiệm lấy tất cả nhân viên sử dụng hàm
 * getAllPersonnel, hiển thị bảng lương của tất cả nhân viên, hiển thị họ tên,
 * ảnh, trạng thái, lương cơ bản, tổng lương.
 *
 * @author TrongNV
 * @version 1.0
 */
public class ListPersonnelServlett extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ListPersonnelServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ListPersonnelServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String message = (String) session.getAttribute("message");
        String type = (String) session.getAttribute("type");
        String xfirstname = (String) session.getAttribute("firstname");
        String xlastname = (String) session.getAttribute("lastname");
        String xbirthday = (String) session.getAttribute("birthday");
        String xaddress = (String) session.getAttribute("address");
        String xgender = (String) session.getAttribute("gender");
        String xemail = (String) session.getAttribute("email");
        String xphone = (String) session.getAttribute("phone");
        String xrole = (String) session.getAttribute("role");
        String xavatar = (String) session.getAttribute("avatar");

        List<Personnel> persons = new ArrayList<>();
        List<Role> roles = new ArrayList<>();
        List<String> statuss = new ArrayList<>();
        List<Personnel> waitlist = new ArrayList<>();
        PersonnelDAO personnelDAO = new PersonnelDAO();
        persons = personnelDAO.getAllPersonnelsWithSalary();
        List<Personnel> activePersonnels = personnelDAO.getActivePersonnels(); // Thêm dòng này

        roles = personnelDAO.getAllPersonnelRole();
        statuss = personnelDAO.getAllStatus();

        request.setAttribute("message", message);
        request.setAttribute("type", type);
        if (type != null) {
            if (type.equalsIgnoreCase("fail")) {
                request.setAttribute("firstname", xfirstname);
                request.setAttribute("lastname", xlastname);
                request.setAttribute("birthday", xbirthday);
                request.setAttribute("address", xaddress);
                request.setAttribute("gender", xgender);
                request.setAttribute("email", xemail);
                request.setAttribute("phone", xphone);
                request.setAttribute("role", xrole);
                request.setAttribute("avatar", xavatar);
            }
            session.removeAttribute("firstname");
            session.removeAttribute("lastname");
            session.removeAttribute("birthday");
            session.removeAttribute("address");
            session.removeAttribute("gender");
            session.removeAttribute("email");
            session.removeAttribute("phone");
            session.removeAttribute("role");
            session.removeAttribute("avatar");
        }
        request.setAttribute("selectedstatus", "all");
        request.setAttribute("selectedrole", "all");
        request.setAttribute("persons", persons);
        request.setAttribute("roles", roles);
        request.setAttribute("waitlist", waitlist);
        request.setAttribute("statuss", statuss);
        request.setAttribute("activePersonnels", activePersonnels); // Set activePersonnels
        request.setAttribute("currentYear", java.time.Year.now().getValue());

        request.getRequestDispatcher("listPersonnel.jsp").forward(request, response);
        session.removeAttribute("message");
        session.removeAttribute("type");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String message = (String) session.getAttribute("message");
        String type = (String) session.getAttribute("type");
        String role = request.getParameter("role");
        String status = request.getParameter("status");
        String month = request.getParameter("month");
        String search = request.getParameter("search");
        List<Personnel> persons = new ArrayList<>();
        List<Role> roles = new ArrayList<>();
        PersonnelDAO personnelDAO = new PersonnelDAO();
        roles = personnelDAO.getAllPersonnelRole();
        List<Personnel> activePersonnels = personnelDAO.getActivePersonnels(); // Thêm dòng này

        if (status.equalsIgnoreCase("all") && role.equalsIgnoreCase("all") && (month == null || month.equalsIgnoreCase("all"))) {
            persons = personnelDAO.getAllPersonnelsWithSalary();
        } else if (!status.equalsIgnoreCase("all") && role.equalsIgnoreCase("all") && (month == null || month.equalsIgnoreCase("all"))) {
            persons = personnelDAO.getPersonnelByStatus(status);
        } else if (!role.equalsIgnoreCase("all") && status.equalsIgnoreCase("all") && (month == null || month.equalsIgnoreCase("all"))) {
            try {
                int xrole = Integer.parseInt(role);
                persons = personnelDAO.getPersonnelByRole(xrole);
            } catch (NumberFormatException e) {
                persons = personnelDAO.getPersonnelByRole(-1);
            }
        } else if (month != null && !month.trim().isEmpty() && !month.equalsIgnoreCase("all") && status.equalsIgnoreCase("all") && role.equalsIgnoreCase("all")) {
            try {
                int m = Integer.parseInt(month);
                persons = personnelDAO.getPersonnelByMonthWithSalary(m);
            } catch (NumberFormatException e) {
                persons = new ArrayList<>();
            }
        } else {
            persons = personnelDAO.getPersonnelByStatusRoleMonth(status, role, month);
        }

        List<String> statuss = new ArrayList<>();
        statuss = personnelDAO.getAllStatus();
        request.setAttribute("statuss", statuss);
        List<Personnel> waitlist = new ArrayList<>();
        request.setAttribute("searchdata", search);
        request.setAttribute("selectedstatus", status);
        request.setAttribute("selectedrole", role);
        request.setAttribute("message", message);
        request.setAttribute("type", type);
        request.setAttribute("selectedmonth", month);
        request.setAttribute("roles", roles);
        request.setAttribute("persons", persons);
        request.setAttribute("activePersonnels", activePersonnels); // Set activePersonnels
        request.setAttribute("currentYear", java.time.Year.now().getValue());

        request.getRequestDispatcher("listPersonnel.jsp").forward(request, response);
        session.removeAttribute("message");
        session.removeAttribute("type");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}