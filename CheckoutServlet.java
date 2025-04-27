package com.bookshop.servlets;

import com.bookshop.dao.CartDAO;
import com.bookshop.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser != null) {
            CartDAO cartDAO = new CartDAO();
            boolean cleared = cartDAO.clearCart(currentUser.getId());

            if (cleared) {
                response.sendRedirect("order_success.html");
            } else {
                response.sendRedirect("error_page.html");
            }
        } else {
            response.sendRedirect("login.html");
        }
    }
}
