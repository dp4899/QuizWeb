package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.util.List;
import model.Question;
import dao.QuestionDAO;

@WebServlet("/quiz")
public class QuizServlet extends HttpServlet {
    private static final String DEFAULT_LEVEL = "basic";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String level = request.getParameter("level");

        if (level == null || level.trim().isEmpty()) {
            level = DEFAULT_LEVEL;
        }

        QuestionDAO dao = new QuestionDAO();
        List<Question> questions = dao.getQuestionsByLevel(level);

        if (questions == null || questions.isEmpty()) {
            request.setAttribute("errorMessage", "Questions are not available right now. Please try again.");
            request.getRequestDispatcher("/index.html").forward(request, response);
            return;
        }

        request.setAttribute("questions", questions);
        request.setAttribute("selectedLevel", level);
        request.getSession().setAttribute("questions", questions);
        request.getRequestDispatcher("/quiz.jsp").forward(request, response);
    }
}

//Then open:

//http://127.0.0.1:8080/QuizApps/