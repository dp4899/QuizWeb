package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;

@WebServlet("/language")
public class LanguageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User currentUser = session == null ? null : (User) session.getAttribute("user");

        if (currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String language = request.getParameter("language");
        if (language == null || language.trim().isEmpty()) {
            response.sendRedirect("learning.jsp");
            return;
        }

        LanguageContent content = buildContent(language.trim().toLowerCase());
        if (content == null) {
            request.setAttribute("errorMessage", "The selected learning topic is not available right now.");
            request.getRequestDispatcher("/learning.jsp").forward(request, response);
            return;
        }

        request.setAttribute("languageKey", language.trim().toLowerCase());
        request.setAttribute("languageName", content.name);
        request.setAttribute("languageDescription", content.description);
        request.setAttribute("youtubeLinks", content.youtubeLinks);
        request.getRequestDispatcher("/language.jsp").forward(request, response);
    }

    private LanguageContent buildContent(String language) {
        switch (language) {
            case "java":
                return new LanguageContent(
                        "Java",
                        "Java is a powerful object-oriented programming language used for web, desktop, mobile, and enterprise applications. It is known for portability, strong community support, and a rich ecosystem for real-world development.",
                        createLinks(
                                "Java Full Course for Beginners", "https://www.youtube.com/watch?v=eIrMbAQSU34",
                                "Java Tutorial for Beginners", "https://www.youtube.com/watch?v=grEKMHGYyns",
                                "Object Oriented Programming in Java", "https://www.youtube.com/watch?v=6T_HgnjoYwM",
                                "Java Projects and Practice", "https://www.youtube.com/watch?v=xk4_1vDrzzo"));
            case "python":
                return new LanguageContent(
                        "Python",
                        "Python is a beginner-friendly language known for its simple syntax and wide use in automation, data science, web development, and AI. It helps learners move quickly from basics to practical projects.",
                        createLinks(
                                "Python Tutorial for Beginners", "https://www.youtube.com/watch?v=_uQrJ0TkZlc",
                                "Python Full Course", "https://www.youtube.com/watch?v=rfscVS0vtbw",
                                "Python Practice Projects", "https://www.youtube.com/watch?v=8ext9G7xspg",
                                "Learn Python OOP", "https://www.youtube.com/watch?v=Ej_02ICOIgs"));
            case "c++":
                return new LanguageContent(
                        "C++",
                        "C++ is a high-performance language used in systems programming, game development, and competitive programming. It gives strong control over memory and speed while supporting object-oriented design.",
                        createLinks(
                                "C++ Full Course", "https://www.youtube.com/watch?v=vLnPwxZdW4Y",
                                "C++ Tutorial for Beginners", "https://www.youtube.com/watch?v=ZzaPdXTrSb8",
                                "C++ OOP Concepts", "https://www.youtube.com/watch?v=wN0x9eZLix4",
                                "C++ STL Explained", "https://www.youtube.com/watch?v=RRVYpIET_RU"));
            case "c":
                return new LanguageContent(
                        "C",
                        "C is a foundational programming language that helps learners understand memory, variables, pointers, and core problem solving. It is widely used in operating systems, embedded development, and performance-critical software.",
                        createLinks(
                                "C Programming Tutorial for Beginners", "https://www.youtube.com/watch?v=KJgsSFOSQv0",
                                "C Full Course", "https://www.youtube.com/watch?v=irqbmMNs2Bo",
                                "Pointers in C", "https://www.youtube.com/watch?v=zuegQmMdy8M",
                                "C Programming Practice", "https://www.youtube.com/watch?v=87SH2Cn0s9A"));
            case "html":
                return new LanguageContent(
                        "HTML",
                        "HTML is the structure layer of the web and is used to build pages with headings, forms, tables, media, and semantic content. It is the first step for anyone interested in websites and front-end development.",
                        createLinks(
                                "HTML Full Course", "https://www.youtube.com/watch?v=pQN-pnXPaVg",
                                "HTML Tutorial for Beginners", "https://www.youtube.com/watch?v=qz0aGYrrlhU",
                                "Build a Website with HTML", "https://www.youtube.com/watch?v=PlxWf493en4",
                                "Semantic HTML Explained", "https://www.youtube.com/watch?v=kGW8Al_cga4"));
            default:
                return null;
        }
    }

    private List<VideoLink> createLinks(String title1, String url1, String title2, String url2,
            String title3, String url3, String title4, String url4) {
        List<VideoLink> links = new ArrayList<>();
        links.add(new VideoLink(title1, url1));
        links.add(new VideoLink(title2, url2));
        links.add(new VideoLink(title3, url3));
        links.add(new VideoLink(title4, url4));
        return links;
    }

    public static class VideoLink {
        private final String title;
        private final String url;

        public VideoLink(String title, String url) {
            this.title = title;
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public String getUrl() {
            return url;
        }
    }

    private static class LanguageContent {
        private final String name;
        private final String description;
        private final List<VideoLink> youtubeLinks;

        private LanguageContent(String name, String description, List<VideoLink> youtubeLinks) {
            this.name = name;
            this.description = description;
            this.youtubeLinks = youtubeLinks;
        }
    }
}
