package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.UserDAO;
import model.User;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (request.getSession(false) != null && request.getSession(false).getAttribute("user") != null) {
            response.sendRedirect("dashboard.jsp");
            return;
        }

        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (isBlank(username) || isBlank(email) || isBlank(password)) {
            request.setAttribute("errorMessage", "All fields are required.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("/register.jsp");
            dispatcher.forward(request, response);
            return;
        }

        User user = new User();
        user.setUsername(username.trim());
        user.setEmail(email.trim());
        user.setPassword(password.trim());

        UserDAO userDAO = new UserDAO();
        boolean registered = userDAO.registerUser(user);

        if (!registered) {
            request.setAttribute("errorMessage", "Registration failed. Please check the database and try again.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        request.getSession().setAttribute("successMessage", "Registration successful. Please login to continue.");
        response.sendRedirect("login.jsp");
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
