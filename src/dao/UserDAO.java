package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.User;

public class UserDAO {

    public boolean registerUser(User user) {
        String sql = "INSERT INTO users(username, email, password) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection()) {
            if (con == null) {
                return false;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getEmail());
                ps.setString(3, user.getPassword());
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Unable to register user: " + e.getMessage());
            return false;
        }
    }

    public User getUserByEmailAndPassword(String email, String password) {
        String sql = "SELECT id, username, email, password FROM users WHERE email = ? AND password = ?";

        try (Connection con = DBConnection.getConnection()) {
            if (con == null) {
                return null;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, email);
                ps.setString(2, password);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        User user = new User();
                        user.setId(rs.getInt("id"));
                        user.setUsername(rs.getString("username"));
                        user.setEmail(rs.getString("email"));
                        user.setPassword(rs.getString("password"));
                        return user;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Unable to fetch user for login: " + e.getMessage());
        }

        return null;
    }
}
