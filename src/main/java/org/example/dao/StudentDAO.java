package org.example.dao;

import org.example.db.DBConnection;

import java.util.List;
import java.util.Map;

public class StudentDAO {
    private DBConnection dbConnection;

    public StudentDAO() {
        dbConnection = new DBConnection();
        dbConnection.connect(); // 데이터베이스 연결
    }

    // 학생이 강의를 신청하는 메서드
    public boolean registerCourse(int studentId, String lectureName) {
        String sql = "INSERT INTO course_registrations (student_id, lecture_id) " +
                "SELECT ?, lecture_id FROM lectures WHERE lecture_name = ? " +
                "AND remaining_capacity > 0";
        int affectedRows = dbConnection.insertWithParams(sql, studentId, lectureName);
        return affectedRows > 0; // 삽입이 성공했으면 true 반환, 실패했으면 false 반환
    }

    // 학생의 시간표를 조회하는 메서드
    public List<Map<String, Object>> viewTimetable(int studentId) {
        String sql = "SELECT l.lecture_name FROM lectures l " +
                "JOIN course_registrations cr ON l.lecture_id = cr.lecture_id " +
                "WHERE cr.student_id = ?";
        return dbConnection.selectRowsWithParams(sql, studentId);
    }

    // 학생의 과목과 성적을 조회하는 메서드
    public List<Map<String, Object>> viewSubjectsAndGrades(int studentId) {
        String sql = "SELECT l.lecture_name, g.grade FROM lectures l " +
                "JOIN grades g ON l.lecture_id = g.lecture_id " +
                "WHERE g.student_id = ?";
        return dbConnection.selectRowsWithParams(sql, studentId);
    }
}
