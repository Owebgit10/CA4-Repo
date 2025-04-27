package com.bookshop.dao;

import com.bookshop.models.Review;
import com.bookshop.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReviewDAO {

    private Connection conn;

    public ReviewDAO() {
        conn = DBConnection.getInstance().getConnection();
    }

    // Add a new review
    public boolean addReview(Review review) {
        String sql = "INSERT INTO reviews (book_id, user_id, rating, comment, timestamp) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, review.getBookId());
            stmt.setInt(2, review.getUserId());
            stmt.setInt(3, review.getRating());
            stmt.setString(4, review.getComment());
            stmt.setTimestamp(5, review.getTimestamp());
            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all reviews for a book
    public List<Review> getReviewsByBookId(int bookId) {
        List<Review> reviews = new ArrayList<>();
        String sql = "SELECT * FROM reviews WHERE book_id = ? ORDER BY timestamp DESC";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Review review = new Review(
                    rs.getInt("id"),
                    rs.getInt("book_id"),
                    rs.getInt("user_id"),
                    rs.getInt("rating"),
                    rs.getString("comment"),
                    rs.getTimestamp("timestamp")
                );
                reviews.add(review);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reviews;
    }

    // Calculate average rating for a book
    public double getAverageRating(int bookId) {
        String sql = "SELECT AVG(rating) AS avg_rating FROM reviews WHERE book_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("avg_rating");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0.0;
    }
}

