package org.example.dao;

import org.example.db.DBConnection;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ProfessorDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/sbs_proj_1";
    private static final String DB_USER = "sbsst";
    private static final String DB_PASSWORD = "sbs123414";


    public void saveLecture(String lectureName) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO lectures (lecture_name) VALUES (?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, lectureName);
                pstmt.executeUpdate();
            }
        }
    }

    public void deleteLecture(int lectureId) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "DELETE FROM lectures WHERE lecture_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, lectureId);
                pstmt.executeUpdate();
            }
        }
    }

    public void saveGrade(int lectureId, String studentId, String grade) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO grades (lecture_id, student_id, grade) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, lectureId);
                pstmt.setString(2, studentId);
                pstmt.setString(3, grade);
                pstmt.executeUpdate();
            }
        }
    }

    public Map<String, String> getGrades(int lectureId) throws SQLException {
        Map<String, String> grades = new HashMap<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT student_id, grade FROM grades WHERE lecture_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, lectureId);
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

    public Map<Integer, String> getAllLectures() throws SQLException {
        Map<Integer, String> lectures = new HashMap<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT lecture_id, lecture_name FROM lectures";
            try (PreparedStatement pstmt = conn.prepareStatement(query);
                 ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int lectureId = rs.getInt("lecture_id");
                    String lectureName = rs.getString("lecture_name");
                    lectures.put(lectureId, lectureName);
                }
            }
        }
        return lectures;
    }
}
