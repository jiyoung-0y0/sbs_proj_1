package org.example.service;

import org.example.dao.StudentDAO;
import org.example.db.DBConnection;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class StudentService {
    private final StudentDAO studentDAO;
    private String studentUsername;

    public StudentService() {
        DBConnection dbConnection = new DBConnection();
        dbConnection.connect();
        this.studentDAO = new StudentDAO(dbConnection);
    }

    public boolean authenticateStudent(String studentUsername, String password) {
        return studentDAO.authenticateStudent(studentUsername, password); // 학생 인증
    }

    public void setStudentUsername(String studentUsername) {
        this.studentUsername = studentUsername; // 로그인한 학생 학번 설정
    }

    // 강의 신청
    public void registerLecture() {
        try {
            List<Map<String, Object>> availableLectures = studentDAO.getAvailableLectures(); // 신청 가능한 강의 목록
            if (availableLectures.isEmpty()) {
                System.out.println("신청할 수 있는 강의가 없습니다.");
                return;
            }

            Scanner scanner = new Scanner(System.in);
            System.out.println("신청할 강의를 선택하세요:");
            for (Map<String, Object> lecture : availableLectures) {
                System.out.println(" - " + lecture.get("lecture_name"));
            }

            System.out.print("강의 이름을 입력하세요: ");
            String selectedLecture = scanner.nextLine();

            int lectureId = -1;
            for (Map<String, Object> lecture : availableLectures) {
                if (selectedLecture.equals(lecture.get("lecture_name"))) {
                    lectureId = (int) lecture.get("lecture_id");
                    break;
                }
            }

            if (lectureId != -1) { // 유효한 강의 ID 확인
                boolean success = studentDAO.registerCourse(studentUsername, lectureId);
                if (success) {
                    System.out.println(selectedLecture + " 강의를 신청했습니다.");
                } else {
                    System.out.println("강의 신청에 실패했습니다.");
                }
            } else {
                System.out.println("해당 강의를 찾을 수 없습니다.");
            }
        } catch (Exception e) { // 일반 예외 처리
            System.out.println("강의 신청 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 학생 성적 조회
    public void viewSubjectsAndGrades() {
        try {
            List<Map<String, Object>> subjectsAndGrades = studentDAO.viewSubjectsAndGrades(studentUsername);

            if (subjectsAndGrades.isEmpty()) {
                System.out.println("조회할 성적이 없습니다.");
                return;
            }

            System.out.println("강의 및 성적:");
            for (Map<String, Object> entry : subjectsAndGrades) {
                System.out.println(" - " + entry.get("lecture_name") + ": " + entry.get("grade"));
            }
        } catch (Exception e) { // 일반 예외 처리
            System.out.println("성적 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 공지사항 확인
    public void viewNotices() {
        try {
            List<Map<String, Object>> notices = studentDAO.getNotices(); // 공지 목록 가져오기
            if (notices.isEmpty()) {
                System.out.println("공지사항이 없습니다.");
                return;
            }

            System.out.println("공지사항 목록:");
            for (Map<String, Object> notice : notices) {
                System.out.println(" - 제목: " + notice.get("title"));
                System.out.println("   내용: " + notice.get("content"));
            }
        } catch (Exception e) { // 일반 예외 처리
            System.out.println("공지사항 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}