package org.example.dao;

import org.example.db.DBConnection;
import java.util.*;

public class StudentDAO {
    private final DBConnection dbConnection;

    public StudentDAO(DBConnection dbConnection) {
        this.dbConnection = dbConnection;
    }

    public boolean authenticateStudent(int studentId, String password) {
        String sql = "SELECT student_password FROM students WHERE student_id = ?";
        List<Map<String, Object>> result = dbConnection.selectRowsWithParams(sql, new Object[]{studentId});

        if (result.isEmpty()) {
            return false;
        }

        String storedPassword = (String) result.get(0).get("student_password");
        return storedPassword.equals(password);
    }

    public boolean registerCourse(int studentId, int lectureId) {
        String sql = "INSERT INTO course_registrations (student_id, lecture_id) VALUES (?, ?)";
        int affectedRows = dbConnection.insertWithParams(sql, new Object[]{studentId, lectureId});

        return affectedRows > 0;
    }

    public List<Map<String, Object>> viewTimetable(int studentId) {
        String sql = "SELECT l.lecture_name FROM lectures l " +
                "JOIN course_registrations cr ON l.lecture_id = cr.lecture_id " +
                "WHERE cr.student_id = ?";
        List<Map<String, Object>> result = dbConnection.selectRowsWithParams(sql, new Object[]{studentId});

        return result;
    }

    public List<Map<String, Object>> viewSubjectsAndGrades(int studentId) {
        String sql = "SELECT l.lecture_name, g.grade FROM lectures l " +
                "JOIN grades g ON l.lecture_id = g.lecture_id " +
                "WHERE g.student_id = ?";
        List<Map<String, Object>> result = dbConnection.selectRowsWithParams(sql, new Object[]{studentId});

        return result;
    }

    public List<Map<String, Object>> getAvailableLectures() {
        String sql = "SELECT lecture_id, lecture_name FROM lectures";
        List<Map<String, Object>> result = dbConnection.selectRows(sql);

        return result;
    }
}
