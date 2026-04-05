package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;

@WebServlet("/projects")
public class ProjectServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User currentUser = session == null ? null : (User) session.getAttribute("user");

        if (currentUser == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String level = request.getParameter("level");
        if (level == null || level.trim().isEmpty()) {
            response.sendRedirect("project-level.jsp");
            return;
        }

        String normalizedLevel = level.trim().toLowerCase();
        List<ProjectIdea> projects = getProjectsByLevel(normalizedLevel);
        if (projects.isEmpty()) {
            request.setAttribute("errorMessage", "No project ideas are available for the selected level right now.");
            request.getRequestDispatcher("/project-level.jsp").forward(request, response);
            return;
        }

        request.setAttribute("selectedLevel", normalizedLevel);
        request.setAttribute("projects", projects);
        request.getRequestDispatcher("/project-list.jsp").forward(request, response);
    }

    private List<ProjectIdea> getProjectsByLevel(String level) {
        switch (level) {
            case "basic":
                return Arrays.asList(
                        project("Simple Calculator",
                                "Build a basic calculator that performs addition, subtraction, multiplication, and division with a clean input form and result display.",
                                tech("Java", "JSP", "HTML", "CSS"),
                                steps("Create input fields for two numbers.", "Add buttons for math operations.",
                                        "Process the selected operation in Java.", "Show the result neatly on the page.")),
                        project("To-Do List App",
                                "Create a lightweight task manager where users can add, view, and remove daily tasks in a simple interface.",
                                tech("Java", "JSP", "Servlet", "CSS"),
                                steps("Build a form to add new tasks.", "Store tasks in memory or session.",
                                        "Display the task list dynamically.", "Add a remove action for completed tasks.")),
                        project("Number Guessing Game",
                                "Make a fun game where the user guesses a secret number and gets hints whether the guess is too high or too low.",
                                tech("Java", "Servlet", "JSP", "HTML"),
                                steps("Generate a random number.", "Take guess input from the user.",
                                        "Compare the guess with the secret number.", "Show hints until the correct answer is found.")),
                        project("Student Record System",
                                "Create a starter record manager that stores student names, roll numbers, and marks with a basic add and view flow.",
                                tech("Java", "JSP", "Servlet", "CSS"),
                                steps("Design a form for student details.", "Create a model class for student data.",
                                        "Store submitted records in a list.", "Render the records in a table view.")),
                        project("Static Portfolio Website",
                                "Design a personal portfolio page with sections for profile, skills, projects, and contact details.",
                                tech("HTML", "CSS", "Bootstrap"),
                                steps("Plan the page sections.", "Build the layout using HTML.",
                                        "Style the design with CSS.", "Add project cards and contact information.")),
                        project("Basic Quiz App",
                                "Develop a simple quiz interface with multiple-choice questions and a score display after submission.",
                                tech("Java", "Servlet", "JSP", "CSS"),
                                steps("Prepare a few quiz questions.", "Render questions with radio options.",
                                        "Check answers in the backend.", "Display the total score on a result page.")),
                        project("Unit Converter",
                                "Build a converter for common units like kilometers to miles or kilograms to pounds with a user-friendly form.",
                                tech("Java", "JSP", "HTML", "CSS"),
                                steps("Choose the unit pairs to support.", "Create an input form and selection list.",
                                        "Apply conversion formulas in Java.", "Show the converted result clearly.")),
                        project("Login Form (No DB)",
                                "Create a mock login system that validates hardcoded username and password values and displays success or error messages.",
                                tech("Java", "Servlet", "JSP", "HTML"),
                                steps("Build login form fields.", "Check credentials in the servlet.",
                                        "Show an error for invalid input.", "Redirect to a welcome page on success.")),
                        project("Temperature Converter",
                                "Develop a mini tool that converts between Celsius, Fahrenheit, and Kelvin with instant output on submit.",
                                tech("Java", "JSP", "HTML", "CSS"),
                                steps("Add input for temperature value.", "Offer unit options in a dropdown.",
                                        "Write conversion logic in Java.", "Display converted values neatly.")),
                        project("Simple Interest Calculator",
                                "Create a finance beginner project that calculates simple interest from principal, rate, and time values.",
                                tech("Java", "JSP", "HTML", "CSS"),
                                steps("Create fields for principal, rate, and time.", "Submit the form to a servlet.",
                                        "Apply the simple interest formula.", "Show both interest and total amount.")));
            case "medium":
                return Arrays.asList(
                        project("Quiz Web App",
                                "Build a full quiz platform with login, levels, score tracking, and database connectivity similar to your current project.",
                                tech("Java", "Servlet", "JSP", "MySQL", "CSS"),
                                steps("Create login and registration flow.", "Load quiz questions from the database.",
                                        "Calculate scores after submission.", "Store user results for tracking.")),
                        project("Student Management System (CRUD)",
                                "Develop a student management dashboard that supports create, read, update, and delete operations on student data.",
                                tech("Java", "Servlet", "JSP", "MySQL", "Bootstrap"),
                                steps("Design the student table in MySQL.", "Create forms for add and edit actions.",
                                        "Implement CRUD servlets.", "Render student records in a dashboard table.")),
                        project("Blog Website",
                                "Create a blog application where users can write posts, view articles, and manage content from a simple admin view.",
                                tech("Java", "JSP", "MySQL", "HTML", "CSS"),
                                steps("Create blog tables for posts.", "Build a form to add blog entries.",
                                        "Display posts on the main page.", "Add edit and delete functionality.")),
                        project("Online Voting System",
                                "Build a voting platform where users can choose candidates and results are tracked securely in a database.",
                                tech("Java", "Servlet", "JSP", "MySQL"),
                                steps("Create candidate and vote tables.", "Display candidates on the voting page.",
                                        "Record one vote per user.", "Show total results in an admin view.")),
                        project("Notes App with Database",
                                "Design a notes manager that lets users add, update, search, and delete personal notes stored in MySQL.",
                                tech("Java", "JSP", "Servlet", "MySQL"),
                                steps("Create a notes table.", "Build note add and edit forms.",
                                        "Implement database insert and update logic.", "Display notes in a searchable list.")),
                        project("Weather App",
                                "Integrate a weather API to show live temperature, forecast, and city search results in a polished interface.",
                                tech("Java", "Servlet", "API", "HTML", "CSS"),
                                steps("Choose a weather API.", "Send city input from the form.",
                                        "Fetch data from the API.", "Format and display weather details on the page.")),
                        project("E-commerce Basic Site",
                                "Create a starter shopping site with product listings, simple cart flow, and order summary screens.",
                                tech("Java", "JSP", "Servlet", "MySQL"),
                                steps("Create product and order tables.", "Display product cards on the home page.",
                                        "Add a cart in session or database.", "Build a checkout summary page.")),
                        project("Chat App",
                                "Develop a basic chat experience using polling or sockets to exchange short text messages between users.",
                                tech("Java", "Servlet", "JSP", "Sockets"),
                                steps("Design the message model.", "Create a screen to send and receive messages.",
                                        "Refresh chat updates regularly.", "Store or stream messages for both users.")),
                        project("File Upload System",
                                "Build a file upload module that allows users to submit documents and see an uploaded file list.",
                                tech("Java", "Servlet", "JSP", "HTML"),
                                steps("Create a multipart upload form.", "Handle files in the servlet.",
                                        "Save uploaded files securely.", "Display uploaded file names to the user.")),
                        project("URL Shortener",
                                "Create a utility that turns long web links into short codes and redirects users to the original URL.",
                                tech("Java", "Servlet", "JSP", "MySQL"),
                                steps("Create a table for short and original URLs.", "Generate unique short codes.",
                                        "Save the mapping in MySQL.", "Redirect when the short link is opened.")));
            case "advanced":
                return Arrays.asList(
                        project("Full E-commerce Platform",
                                "Design a complete shopping system with user accounts, carts, payments, order history, and admin product management.",
                                tech("Java", "Servlet", "JSP", "MySQL", "Bootstrap"),
                                steps("Design tables for users, products, carts, and orders.", "Create user and admin modules.",
                                        "Implement checkout and order tracking.", "Build reports for sales and inventory.")),
                        project("Online Learning Platform",
                                "Create a learning portal with login, course categories, progress tracking, quizzes, and video-based lessons.",
                                tech("Java", "Servlet", "JSP", "MySQL", "CSS"),
                                steps("Plan modules for courses and lessons.", "Create learner dashboards and progress tracking.",
                                        "Add course content pages and quizzes.", "Store completion status in the database.")),
                        project("Real-time Chat App",
                                "Build a chat platform with instant messaging, typing indicators, and multi-user rooms using WebSocket support.",
                                tech("Java", "WebSocket", "JSP", "MySQL"),
                                steps("Set up WebSocket endpoints.", "Create chat room interfaces.",
                                        "Handle live message broadcasting.", "Store chat history for later viewing.")),
                        project("Social Media Clone",
                                "Develop a social platform with profiles, posts, likes, comments, and follower relationships.",
                                tech("Java", "Servlet", "JSP", "MySQL"),
                                steps("Design tables for users, posts, likes, and comments.", "Build profile and feed pages.",
                                        "Implement interactions like likes and follows.", "Add notifications or activity tracking.")),
                        project("Online Code Compiler",
                                "Create a platform where users can write code in the browser, run it, and view output securely.",
                                tech("Java", "Servlet", "JSP", "API", "Docker"),
                                steps("Build an editor interface.", "Send code to a secure execution service.",
                                        "Capture output and errors.", "Show results cleanly and safely.")),
                        project("Project Management Tool",
                                "Design a productivity platform with projects, tasks, team members, deadlines, and progress tracking.",
                                tech("Java", "Servlet", "JSP", "MySQL", "CSS"),
                                steps("Create project and task data models.", "Build dashboards for team task status.",
                                        "Add assignment and deadline features.", "Track completion progress visually.")),
                        project("Multi-user Blog Platform",
                                "Build a collaborative blogging site with multiple authors, categories, comments, and role-based dashboards.",
                                tech("Java", "JSP", "Servlet", "MySQL"),
                                steps("Set up user roles and blog tables.", "Build author dashboards for content management.",
                                        "Enable comments and categories.", "Add moderation and profile features.")),
                        project("AI-based Recommendation System",
                                "Create a recommendation module that suggests content or products based on user preferences and stored behavior.",
                                tech("Java", "MySQL", "Python API", "REST"),
                                steps("Collect preference or usage data.", "Create a recommendation logic service.",
                                        "Connect Java with the recommendation API.", "Display personalized suggestions to users.")),
                        project("Resume Builder Web App",
                                "Develop a resume builder where users enter details, choose templates, and export professional resumes.",
                                tech("Java", "Servlet", "JSP", "MySQL", "CSS"),
                                steps("Create forms for personal and career details.", "Build multiple resume templates.",
                                        "Store resume data in MySQL.", "Generate printable or downloadable output.")),
                        project("Job Portal System",
                                "Build a hiring platform with employer accounts, job postings, candidate applications, and admin oversight.",
                                tech("Java", "Servlet", "JSP", "MySQL", "Bootstrap"),
                                steps("Design employer, job, and applicant tables.", "Create job posting and browsing flows.",
                                        "Build application submission screens.", "Add admin review and status management.")));
            default:
                return new ArrayList<>();
        }
    }

    private ProjectIdea project(String title, String description, List<String> technologies, List<String> steps) {
        return new ProjectIdea(title, description, technologies, steps);
    }

    private List<String> tech(String... items) {
        return Arrays.asList(items);
    }

    private List<String> steps(String... items) {
        return Arrays.asList(items);
    }

    public static class ProjectIdea {
        private final String title;
        private final String description;
        private final List<String> technologies;
        private final List<String> steps;

        public ProjectIdea(String title, String description, List<String> technologies, List<String> steps) {
            this.title = title;
            this.description = description;
            this.technologies = technologies;
            this.steps = steps;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        public List<String> getTechnologies() {
            return technologies;
        }

        public List<String> getSteps() {
            return steps;
        }
    }
}
