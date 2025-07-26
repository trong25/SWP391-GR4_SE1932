/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller.academicstaff;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import model.notification.Notification;
import model.notification.NotificationDAO;
import model.notification.NotificationDetails;
import model.personnel.PersonnelDAO;
import model.user.User;
import model.user.UserDAO;

/**
 *
 * @author PC
 */
@WebServlet(name = "/academicstaff/CreateNotificationServlet", urlPatterns = {"/academicstaff/createnotifi"})
public class CreateNotificationServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        String toastType = "", toastMessage = "";
        if (session.getAttribute("toastType") != null) {
            toastType = session.getAttribute("toastType").toString();
            toastMessage = session.getAttribute("toastMessage").toString();
        }
        session.removeAttribute("toastType");
        session.removeAttribute("toastMessage");
        request.setAttribute("toastType", toastType);
        request.setAttribute("toastMessage", toastMessage);
        request.getRequestDispatcher("createNotification.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userid = request.getParameter("userid");
        NotificationDAO notifiDAO = new NotificationDAO();
        String id = "N000001";
        Notification latestNotification = notifiDAO.getLatest();
        if (latestNotification != null) {
            String generatedId = notifiDAO.generateId(latestNotification.getId());
            if (generatedId != null) {
                id = generatedId;
            }
        }
        String heading = request.getParameter("heading");
        String content = request.getParameter("content");
        String create_by = request.getParameter("userid");
        String submitDateStr = request.getParameter("submitDate");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Định dạng ngày bạn mong muốn
        Date create_at = null;
        String[] listrole_id = request.getParameterValues("role_id");
        try {
            create_at = dateFormat.parse(submitDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        HttpSession session = request.getSession();
        if (listrole_id == null || listrole_id.length == 0) {
            session.setAttribute("toastType", "error");
            session.setAttribute("toastMessage", "Bạn Chưa Chọn Người Nhận");
            response.sendRedirect("createnotifi");
        }
        try {
            for (String s : listrole_id) {
                int roleid = Integer.parseInt(s);
                Notification notifi = new Notification(id, heading.trim(), content.trim(), new PersonnelDAO().getPersonnel(create_by), create_at);
                boolean succes = notifiDAO.createNoti(notifi);
                List<User> user = new UserDAO().getUserByRole(roleid);
                for (User u : user) {

                    NotificationDetails notifidetails = new NotificationDetails(id, u.getId());

                    boolean success = notifiDAO.createNotiDetails(notifidetails);
                    if (!succes && !success) {
                        session.setAttribute("toastType", "error");
                        session.setAttribute("toastMessage", "Gửi Thông Báo Thất Bại");
                    } else {
                        session.setAttribute("toastType", "success");
                        session.setAttribute("toastMessage", "Gửi Thông Báo Thành Công");
                    }
                }
            }
            response.sendRedirect("createnotifi");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
