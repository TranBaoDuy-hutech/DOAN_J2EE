package com.travel3d.vietlutravel.config;

import com.travel3d.vietlutravel.model.Customer;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Locale;

public class RoleAccessInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {

        String contextPath = request.getContextPath() != null ? request.getContextPath() : "";
        String uri = request.getRequestURI() != null ? request.getRequestURI() : "";
        String path = uri.startsWith(contextPath) ? uri.substring(contextPath.length()) : uri;
        if (path.isEmpty()) path = "/";

        // Always allow static/auth plumbing endpoints
        if (isAlwaysAllowed(path)) {
            return true;
        }

        HttpSession session = request.getSession(false);
        Customer customer = session != null ? (Customer) session.getAttribute("user") : null;

        if (customer == null) {
            // Not logged in: allow public pages, but protect role-specific areas
            if (isPublicForAnonymous(path)) return true;
            response.sendRedirect("/login");
            return false;
        }

        String role = normalizeRole(customer.getRole());

        // Logged in: restrict whole site by role (ADMIN/STAFF can't enter user-facing pages including "/")
        if (!isAllowedForRole(role, path)) {
            if (session != null) {
                session.setAttribute("errorMessage",
                        "Bạn không có quyền truy cập trang này. Vui lòng đăng nhập đúng tài khoản.");
            }
            response.sendRedirect("/login");
            return false;
        }

        return true;
    }

    private static boolean isAlwaysAllowed(String path) {
        return path.startsWith("/css/")
                || path.startsWith("/js/")
                || path.startsWith("/image/")
                || path.startsWith("/images/")
                || path.startsWith("/webjars/")
                || path.startsWith("/assets/")
                || path.equals("/login")
                || path.equals("/register")
                || path.equals("/logout")
                || path.startsWith("/forgot-password")
                || path.startsWith("/oauth2/")
                || path.startsWith("/login/oauth2/")
                || path.startsWith("/error");
    }

    private static boolean isPublicForAnonymous(String path) {
        // Anonymous users can still see the public user site
        return path.equals("/") || isAlwaysAllowed(path);
    }

    private static boolean isAdminPath(String path) {
        return path.startsWith("/admin");
    }

    private static boolean isStaffPath(String path) {
        return path.startsWith("/staff");
    }

    private static boolean isAllowedForRole(String role, String path) {
        if ("ADMIN".equals(role)) {
            // Admin only stays in admin area (and always-allowed endpoints)
            return isAdminPath(path) || isAlwaysAllowed(path);
        }
        if ("STAFF".equals(role)) {
            // Staff only stays in staff area (and always-allowed endpoints)
            return isStaffPath(path) || isAlwaysAllowed(path);
        }
        // USER can access user site + user-only pages, but not admin/staff areas
        if (isAdminPath(path) || isStaffPath(path)) return false;
        return true;
    }

    private static String normalizeRole(String role) {
        if (role == null) return "USER";
        String r = role.trim().toUpperCase(Locale.ROOT);
        return r.isEmpty() ? "USER" : r;
    }

    // Forbidden responses are handled by Spring Boot error page (templates/error/403.html if present).
}
