package org.example.dao;

import java.sql.*;
import java.util.*;

public class ProfessorDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/sbs_proj_1";
    private static final String DB_USER = "sbsst";
    private static final String DB_PASSWORD = "sbs123414";

    // 강의 등록
    public void saveLecture(String lectureName) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO lectures (lecture_name) VALUES (?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setString(1, lectureName);
                pstmt.executeUpdate();
            }
        }
    }

    // 강의 삭제
    public void deleteLecture(int lectureId) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // 먼저 course_registrations 및 grades에서 관련 레코드를 삭제
            String deleteRegistrations = "DELETE FROM course_registrations WHERE lecture_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteRegistrations)) {
                pstmt.setInt(1, lectureId);
                pstmt.executeUpdate();
            }

            String deleteGrades = "DELETE FROM grades WHERE lecture_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteGrades)) {
                pstmt.setInt(1, lectureId);
                pstmt.executeUpdate();
            }

            // 그 후 lectures에서 삭제
            String deleteLecture = "DELETE FROM lectures WHERE lecture_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(deleteLecture)) {
                pstmt.setInt(1, lectureId);
                pstmt.executeUpdate();
            }
        }
    }

    // 강의별 학생 목록 조회
    public List<Map<String, String>> getStudentsForLecture(int lectureId) throws SQLException {
        List<Map<String, String>> studentList = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT s.student_username, s.student_name " +
                    "FROM students s " +
                    "JOIN course_registrations cr ON s.student_id = cr.student_id " +
                    "WHERE cr.lecture_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, lectureId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        Map<String, String> studentInfo = new HashMap<>();
                        studentInfo.put("student_username", rs.getString("student_username"));
                        studentInfo.put("student_name", rs.getString("student_name"));
                        studentList.add(studentInfo);
                    }
                }
            }
        }
        return studentList;
    }

    // 성적 입력
    public void saveGrade(int lectureId, String studentUsername, String grade) throws SQLException {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "INSERT INTO grades (lecture_id, student_id, grade) " +
                    "VALUES (?, (SELECT student_id FROM students WHERE student_username = ?), ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, lectureId);
                pstmt.setString(2, studentUsername);
                pstmt.setString(3, grade);
                pstmt.executeUpdate();
            }
        }
    }

    // 강의별 학생 성적 조회
    public Map<String, String> getGrades(int lectureId) throws SQLException {
        Map<String, String> grades = new HashMap<>();
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String query = "SELECT s.student_username, g.grade FROM grades g " +
                    "JOIN students s ON g.student_id = s.student_id " +
                    "WHERE g.lecture_id = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(query)) {
                pstmt.setInt(1, lectureId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        String studentUsername = rs.getString("student_username");
                        String grade = rs.getString("grade");
                        grades.put(studentUsername, grade);
                    }
                }
            }
        }
        return grades;
    }

    // 전체 강의 목록 조회
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
