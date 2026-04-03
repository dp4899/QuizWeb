package dao;

import java.sql.*;
import model.Score;

public class ScoreDAO {

    public boolean saveScore(Score s) {
        String sql = "INSERT INTO scores(username, score) VALUES(?, ?)";

        try (Connection con = DBConnection.getConnection()) {
            if (con == null) {
                return false;
            }

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, s.getUsername());
                ps.setInt(2, s.getScore());

                ps.executeUpdate();
                return true;
            }

        } catch (Exception e) {
            System.err.println("Unable to save score: " + e.getMessage());
            return false;
        }
    }
}
