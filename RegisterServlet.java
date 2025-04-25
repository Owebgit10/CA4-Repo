package com.bookshop.servlets;

import com.bookshop.dao.UserDAO;
import com.bookshop.models.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Read form data
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String password = request.getParameter("password");
        String role = "customer"; // Default role
        String shipping = request.getParameter("shipping");
        String payment = request.getParameter("payment");

        // Create User object
        User user = new User(name, email, phone, password, role, shipping, payment);

        // DAO insert
        UserDAO dao = new UserDAO();
        boolean registered = dao.registerUser(user);

        // Redirect or show error
        if (registered) {
            response.sendRedirect("login.html"); // Redirect to login page after successful registration
        } else {
            response.getWriter().println("Registration failed. Try again.");
        }
    }
}

