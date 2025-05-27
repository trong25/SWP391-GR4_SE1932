/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

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
 * @author ASUS VIVOBOOK
 */
public class LoginFilter {
     private static final boolean debug = true;

  
    private FilterConfig filterConfig = null;

    public LoginFilter() {
    }

    private void doBeforeProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("LoginFilter:DoBeforeProcessing");
        }

    }

    private void doAfterProcessing(ServletRequest request, ServletResponse response)
            throws IOException, ServletException {
        if (debug) {
            log("LoginFilter:DoAfterProcessing");
        }

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
                return path.startsWith("/headteacher/");
            case 2: // Academic Staff
                return path.startsWith("/academicstaff/");
            case 3: // Teacher
                return path.startsWith("/teacher/");
            case 4: // Parent
                return path.startsWith("/parent/");
            default:
                return false;
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }
    
        public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }
}
