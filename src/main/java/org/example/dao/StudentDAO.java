package org.example.dao;

import org.example.db.DBConnection;

import java.util.*;

public class StudentDAO {
    private final DBConnection dbConnection;

    public StudentDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public boolean authenticateStudent(String studentUsername, String password) {
        String sql = "SELECT student_password FROM students WHERE student_username = ?";
        List<Map<String, Object>> result = dbConnection.selectRowsWithParams(sql, new Object[]{studentUsername});

        if (result.isEmpty()) {
            return false;
        }

        String storedPassword = (String) result.get(0).get("student_password");
        return storedPassword.equals(password);
    }

    public boolean registerCourse(String studentUsername, int lectureId) {
        String studentCheckSQL = "SELECT student_id FROM students WHERE student_username = ?";
        List<Map<String, Object>> studentCheckResult = dbConnection.selectRowsWithParams(studentCheckSQL, new Object[]{studentUsername});

        if (studentCheckResult.isEmpty()) {
            throw new IllegalArgumentException("학생이 존재하지 않습니다: " + studentUsername);
        }

        int studentId = (Integer) studentCheckResult.get(0).get("student_id");

        String sql = "INSERT INTO course_registrations (student_id, lecture_id) VALUES (?, ?)";
        int affectedRows = dbConnection.insertWithParams(sql, new Object[]{studentId, lectureId});

        return affectedRows > 0;
    }

    public List<Map<String, Object>> viewSubjectsAndGrades(String studentUsername) {
        String sql = "SELECT l.lecture_name, g.grade " +
                "FROM grades g " +
                "JOIN students s ON s.student_id = g.student_id " +
                "JOIN lectures l ON l.lecture_id = g.lecture_id " +
                "WHERE s.student_username = ?";

        List<Map<String, Object>> result = dbConnection.selectRowsWithParams(sql, new Object[]{studentUsername});

        return result;
    }

    public List<Map<String, Object>> getAvailableLectures() {
        String sql = "SELECT lecture_id, lecture_name FROM lectures";
        List<Map<String, Object>> result = dbConnection.selectRows(sql);

        return result;
    }

    public List<Map<String, Object>> getNotices() {
        String sql = "SELECT title, content FROM notices";
        List<Map<String, Object>> result = dbConnection.selectRows(sql);

        return result;
    }
}