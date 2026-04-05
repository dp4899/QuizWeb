package controller;

import java.io.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

import dao.ScoreDAO;
import dao.QuestionDAO;
import model.Score;
import model.Question;
import model.User;

@WebServlet("/score")
public class ScoreServlet extends HttpServlet {

    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Question> questions = (List<Question>) request.getSession().getAttribute("questions");
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        if (questions == null || questions.isEmpty()) {
            String selectedLevel = request.getParameter("level");
            questions = new QuestionDAO().getQuestionsByLevel(selectedLevel);
        }

        if (questions == null || questions.isEmpty()) {
            response.sendRedirect("dashboard.jsp");
            return;
        }

        int score = 0;

        for (Question q : questions) {
            String userAnswer = request.getParameter("q" + q.getId());

            if (userAnswer != null && q.getCorrectAnswer() != null
                    && userAnswer.trim().equalsIgnoreCase(q.getCorrectAnswer().trim())) {
                score++;
            }
        }

        Score s = new Score();
        s.setUsername(currentUser.getUsername());
        s.setScore(score);

        ScoreDAO dao = new ScoreDAO();
        boolean scoreSaved = dao.saveScore(s);

        request.setAttribute("score", score);
        request.setAttribute("totalQuestions", questions.size());
        request.setAttribute("scoreSaved", scoreSaved);
        request.setAttribute("username", currentUser.getUsername());
        request.getSession().removeAttribute("questions");

        RequestDispatcher rd = request.getRequestDispatcher("result.jsp");
        rd.forward(request, response);
    }
}
