package dao;

import java.sql.*;
import java.util.*;
import model.Question;

public class QuestionDAO {
    private static final Map<String, List<Question>> FALLBACK_QUESTIONS = createFallbackQuestions();

    public List<Question> getQuestionsByLevel(String level) {
        String normalizedLevel = normalizeLevel(level);
        List<Question> list = new ArrayList<>();

        String sql = "SELECT * FROM questions WHERE level = ?";

        try (Connection con = DBConnection.getConnection()) {
            if (con == null) {
                return getFallbackQuestions(normalizedLevel);
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, normalizedLevel);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    Question q = new Question();
                    q.setId(rs.getInt("id"));
                    q.setQuestion(rs.getString("question"));
                    q.setOption1(rs.getString("option1"));
                    q.setOption2(rs.getString("option2"));
                    q.setOption3(rs.getString("option3"));
                    q.setOption4(rs.getString("option4"));
                    q.setCorrectAnswer(rs.getString("correct_answer"));
                    q.setLevel(normalizedLevel);
                    list.add(q);
                }
            } 

            if(list.isEmpty()) {
                System.out.println("No questions found for level: " + normalizedLevel + ". Using fallback set.");
                return getFallbackQuestions(normalizedLevel);
            }

        } catch (Exception e) {
            System.err.println("Unable to read questions from database: " + e.getMessage());
            return getFallbackQuestions(normalizedLevel);
        }

        return list;
    }

    private String normalizeLevel(String level) {
        if (level == null || level.trim().isEmpty()) {
            return "basic";
        }

        String normalizedLevel = level.trim().toLowerCase(Locale.ENGLISH);
        if (!FALLBACK_QUESTIONS.containsKey(normalizedLevel)) {
            return "basic";
        }
        return normalizedLevel;
    }

    private List<Question> getFallbackQuestions(String level) {
        List<Question> fallback = FALLBACK_QUESTIONS.get(level);
        if (fallback == null) {
            fallback = FALLBACK_QUESTIONS.get("basic");
        }
        return new ArrayList<>(fallback);
    }

    private static Map<String, List<Question>> createFallbackQuestions() {
        Map<String, List<Question>> data = new HashMap<>();
        data.put("basic", Arrays.asList(
                createQuestion(1, "What is the capital of France?", "Paris", "London", "Berlin", "Madrid", "Paris", "basic"),
                createQuestion(2, "Which number comes after 9?", "10", "11", "8", "12", "10", "basic"),
                createQuestion(3, "Java is primarily a ...", "Programming language", "Database", "Browser", "Game engine", "Programming language", "basic")
        ));
        data.put("medium", Arrays.asList(
                createQuestion(4, "Which collection does not allow duplicates?", "Set", "List", "Queue", "ArrayList", "Set", "medium"),
                createQuestion(5, "Which HTTP method is usually used to submit form data securely?", "POST", "GET", "TRACE", "HEAD", "POST", "medium"),
                createQuestion(6, "Which SQL clause filters rows before grouping?", "WHERE", "ORDER BY", "HAVING", "JOIN", "WHERE", "medium")
        ));
        data.put("advanced", Arrays.asList(
                createQuestion(7, "Which JVM area stores class metadata in modern Java?", "Metaspace", "Heap", "Stack", "Register", "Metaspace", "advanced"),
                createQuestion(8, "What does ACID stand for in databases?", "Atomicity, Consistency, Isolation, Durability", "Access, Control, Integrity, Distribution", "Asynchronous, Consistent, Indexed, Durable", "Atomic, Concurrent, Isolated, Distributed", "Atomicity, Consistency, Isolation, Durability", "advanced"),
                createQuestion(9, "Which design pattern creates an object while hiding creation logic?", "Factory", "Observer", "Adapter", "Decorator", "Factory", "advanced")
        ));
        return data;
    }

    private static Question createQuestion(int id, String questionText, String option1, String option2,
            String option3, String option4, String correctAnswer, String level) {
        Question question = new Question();
        question.setId(id);
        question.setQuestion(questionText);
        question.setOption1(option1);
        question.setOption2(option2);
        question.setOption3(option3);
        question.setOption4(option4);
        question.setCorrectAnswer(correctAnswer);
        question.setLevel(level);
        return question;
    }
}

//tomcat Start
//$env:CATALINA_HOME="C:\Users\Arman\OneDrive\Desktop\Java_Programming_Language\QuizWeb\apache-tomcat-9.0.116"
//>> $env:CATALINA_BASE=$env:CATALINA_HOME
//>> Start-Process -FilePath "$env:CATALINA_HOME\bin\catalina.bat" -ArgumentList "run" -WorkingDirectory "$env:CATALINA_HOME\bin"
//http://127.0.0.1:8080/QuizApps/