/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package accountant;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Lớp {@code InformationAccountantServlet} chịu trách nhiệm hiển thị trang thông tin kế toán.
 * 
 * Trong phương thức {@code doGet}, servlet lấy thông tin thông báo (toast) 
 * từ phiên làm việc (session) nếu có, rồi chuyển tiếp (forward) đến trang JSP "information.jsp" 
 * để hiển thị cho người dùng.
 * 
 * 
 * Đây là servlet thuộc tầng Controller trong mô hình MVC, kết nối giữa view (JSP) và backend.
 * 
 * @author HuyDV
 * @version 1.0
 */

public class InformationAccountantServlet extends HttpServlet {
   
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
        request.getRequestDispatcher("information.jsp").forward(request, response);
    } 

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
     
    }


}