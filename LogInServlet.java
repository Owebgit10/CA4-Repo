package com.bookshop.servlets;

import com.bookshop.dao.UserDAO;
import com.bookshop.models.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/login")
public class LogInServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Read form data
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // DAO login check
        UserDAO dao = new UserDAO();
        User user = dao.login(email, password);

        if (user != null) {
            // Login successful
            HttpSession session = request.getSession();
            session.setAttribute("currentUser", user);

            if ("admin".equals(user.getRole())) {
                response.sendRedirect("admin_dashboard.html"); 
            } else {
                response.sendRedirect("shop_home.html"); 
            }
        } else {
            // Login failed
            response.getWriter().println("Login failed. Invalid email or password.");
        }
    }
}

