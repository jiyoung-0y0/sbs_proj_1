// ProfessorDAO.java
package org.example.dao;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ProfessorDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/university";
    private static final String DB_USER = "your_username";
    private static final String DB_PASSWORD = "your_password";

    public void saveLecture(String lectureName, String lectureCode) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO lectures (lecture_name, lecture_code) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, lectureName);
                pstmt.setString(2, lectureCode);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteLecture(String lectureName) {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "DELETE FROM lectures WHERE lecture_name = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, lectureName);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveGrade(String lectureName, String studentId, String grade) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO grades (lecture_name, student_id, grade) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, lectureName);
                pstmt.setString(2, studentId);
                pstmt.setString(3, grade);
                pstmt.executeUpdate();
            }
        }
    }

    public Map<String, String> getGrades(String lectureName) throws SQLException {
        Map<String, String> grades = new HashMap<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT student_id, grade FROM grades WHERE lecture_name = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, lectureName);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        String studentId = rs.getString("student_id");
                        String grade = rs.getString("grade");
                        grades.put(studentId, grade);
                    }
                }
            }
        }
        return grades;
    }
}
