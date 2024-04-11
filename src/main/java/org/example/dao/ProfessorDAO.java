// ProfessorDAO.java
package org.example.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProfessorDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/university";
    private static final String USER = "username";
    private static final String PASSWORD = "password";

    static {
        try {
            // MySQL JDBC 드라이버 클래스를 로드합니다.
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("MySQL JDBC 드라이버를 찾을 수 없습니다.");
        }
    }

    public void saveLecture(String lectureName, String lectureCode) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
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
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "DELETE FROM lectures WHERE lecture_name = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, lectureName);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
