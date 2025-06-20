/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package Controller.admin;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.user.User;
import model.user.UserDAO;

/**
 *
 * @author ASUS VIVOBOOK
 */
@WebServlet(name="CategoryRoleManagerServlet", urlPatterns={"/admin/categoryRoleManager"})
public class CategoryRoleManagerServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
         Map<Integer, String> roleMap = new HashMap<>();
        Map<Byte, String> roleDis = new HashMap<>();
        roleMap.put(0, "NHÂN VIÊN IT");
        roleMap.put(1, "HIỆU TRƯỞNG");
        roleMap.put(2, "GIÁO VỤ");
        roleMap.put(3, "GIÁO VIÊN");
        roleMap.put(4, "HỌC SINH");
        roleMap.put(5, "KẾ TOÁN");
        
         roleDis.put((byte) 0, "HOẠT ĐỘNG");
        roleDis.put((byte) 1, "KHÔNG HOẠT ĐỘNG");
        String id = request.getParameter("role");
        if (id.equals("6")) {
            response.sendRedirect("manageruser");
        } else {
            UserDAO userDAO = new UserDAO();

            List<User> list = userDAO.getUserByRole(Integer.parseInt(id));
            request.setAttribute("list", list);
            request.setAttribute("roleMap", roleMap);
            request.setAttribute("roleDis", roleDis);
            request.getRequestDispatcher("../admin/managerUser.jsp").forward(request, response);
        }
    } 

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
     
    }


}