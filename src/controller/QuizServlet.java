package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.List;
import model.Question;
import model.User;
import dao.QuestionDAO;

@WebServlet("/quiz")
public class QuizServlet extends HttpServlet {
    private static final String DEFAULT_LEVEL = "basic";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String level = request.getParameter("level");

        if (level == null || level.trim().isEmpty()) {
            level = DEFAULT_LEVEL;
        }

        QuestionDAO dao = new QuestionDAO();
        List<Question> questions = dao.getQuestionsByLevel(level);

        if (questions == null || questions.isEmpty()) {
            request.setAttribute("errorMessage", "Questions are not available right now. Please try again.");
            request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
            return;
        }

        request.setAttribute("questions", questions);
        request.setAttribute("selectedLevel", level);
        request.setAttribute("currentUser", currentUser);
        request.getSession().setAttribute("questions", questions);
        request.getRequestDispatcher("/quiz.jsp").forward(request, response);
    }
}
