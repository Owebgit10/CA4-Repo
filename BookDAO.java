package com.bookshop.dao;

import com.bookshop.models.Book;
import com.bookshop.utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    private Connection conn;

    public BookDAO() {
        conn = DBConnection.getInstance().getConnection();
    }

    // List all books
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                books.add(extractBook(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    // Search books by title, author, publisher or category
    public List<Book> searchBooks(String keyword) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ? OR publisher LIKE ? OR category LIKE ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            String searchKeyword = "%" + keyword + "%";
            stmt.setString(1, searchKeyword);
            stmt.setString(2, searchKeyword);
            stmt.setString(3, searchKeyword);
            stmt.setString(4, searchKeyword);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                books.add(extractBook(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    // Get single book details
    public Book getBookById(int id) {
        String sql = "SELECT * FROM books WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return extractBook(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Helper method to extract a Book from ResultSet
    private Book extractBook(ResultSet rs) throws SQLException {
        return new Book(
            rs.getInt("id"),
            rs.getString("title"),
            rs.getString("author"),
            rs.getString("publisher"),
            rs.getDouble("price"),
            rs.getString("category"),
            rs.getString("isbn"),
            rs.getInt("stock"),
            rs.getString("image")
        );
    }
}

