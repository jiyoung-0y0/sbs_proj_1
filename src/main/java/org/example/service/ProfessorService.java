package org.example.service;

import org.example.dao.ProfessorDAO;

import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class ProfessorService {
    private final ProfessorDAO professorDAO = new ProfessorDAO();

    public void registerLecture() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("추가할 강의 이름을 입력하세요: ");
        String lectureName = scanner.nextLine();
        try {
            professorDAO.saveLecture(lectureName);
            System.out.println("강의 \"" + lectureName + "\"을(를) 등록했습니다.");
        } catch (SQLException e) {
            System.out.println("강의 등록 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    public void removeLecture() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("삭제할 강의 번호를 입력하세요: ");
        int lectureId = scanner.nextInt();
        try {
            professorDAO.deleteLecture(lectureId);
            System.out.println("강의를 삭제했습니다.");
        } catch (SQLException e) {
            System.out.println("강의 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    public void manageGrades() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("성적을 입력할 강의 번호를 선택하세요: ");
        int selectedLectureId = scanner.nextInt();
        scanner.nextLine(); // 버퍼 비우기
        System.out.print("성적을 입력할 학생의 학번을 입력하세요: ");
        String studentId = scanner.nextLine();
        System.out.print("성적을 입력하세요 (A+, A0, B+, B0, C+, C0, D+, D0, F): ");
        String grade = scanner.nextLine();
        try {
            professorDAO.saveGrade(selectedLectureId, studentId, grade);
            System.out.println("성적이 성공적으로 입력되었습니다.");
        } catch (SQLException e) {
            System.out.println("성적 입력 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    public void viewStudentGrades() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("성적을 확인할 강의 번호를 선택하세요: ");
        int selectedLectureId = scanner.nextInt();
        scanner.nextLine(); // 버퍼 비우기
        try {
            Map<String, String> grades = professorDAO.getGrades(selectedLectureId);
            if (grades.isEmpty()) {
                System.out.println("등록된 학생 성적이 없습니다.");
                return;
            }
            System.out.println("과목의 성적:");
            for (Map.Entry<String, String> entry : grades.entrySet()) {
                System.out.println("학번: " + entry.getKey() + ", 성적: " + entry.getValue());
            }
        } catch (SQLException e) {
            System.out.println("성적 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
