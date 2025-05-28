/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import model.user.User;

/**
 *
 * @author HuyDV
 */
public class LoginFilter implements Filter{


    public LoginFilter() {
    }


    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);
        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
        boolean loggedIn = session != null && session.getAttribute("user") != null;
        boolean loginRequest = httpRequest.getRequestURI().endsWith("login.jsp") || path.equals("/login") || path.equals("/register") || path.equals("/");
        if (loggedIn) {
            User user = (User) session.getAttribute("user");
            if (hasAccess(user, path)) {
                chain.doFilter(request, response);
            } else {
                session.invalidate();
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            }
        } else if (loginRequest) {
            chain.doFilter(request, response);
        } else {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        }
    }

    private boolean hasAccess(User user, String path) {
        int roleId = user.getRoleId();
        switch (roleId) {
            case 0: // Admin
                return path.startsWith("/admin/");
            case 1: // Head Teacher
                return path.startsWith("/director/");
            case 2: // Academic Staff
                return path.startsWith("/academicstaff/");
            case 3: // Teacher
                return path.startsWith("/teacher/");

            case 4: // student
                return path.startsWith("/student/");
            case 5: 
                return path.startsWith("/accountant/");

        

            default:
                return false;
        }
    }


}
