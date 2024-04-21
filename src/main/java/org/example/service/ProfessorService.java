package org.example.service;

import org.example.dao.ProfessorDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ProfessorService {
    private final ProfessorDAO professorDAO = new ProfessorDAO();

    // 강의 등록
    public void registerLecture() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("추가할 강의 이름을 입력하세요: ");
        String lectureName = scanner.nextLine();
        try {
            professorDAO.saveLecture(lectureName);
            System.out.println("강의 \"" + lectureName + "\"이(가) 등록되었습니다.");
        } catch (SQLException e) {
            System.out.println("강의 등록 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 강의 삭제
    public void removeLecture() {
        try {
            Map<Integer, String> lectures = professorDAO.getAllLectures();
            if (lectures.isEmpty()) {
                System.out.println("등록된 강의가 없습니다.");
                return;
            }

            System.out.println("삭제할 강의 목록:");
            lectures.forEach((id, name) -> System.out.println(id + ". " + name));

            Scanner scanner = new Scanner(System.in);
            System.out.print("삭제할 강의 번호를 선택하세요: ");
            int lectureId = scanner.nextInt();

            professorDAO.deleteLecture(lectureId);
            System.out.println("강의를 삭제했습니다.");
        } catch (SQLException e) {
            System.out.println("강의 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 성적 관리
    public void manageGrades() {
        try {
            Map<Integer, String> lectures = professorDAO.getAllLectures();
            if (lectures.isEmpty()) {
                System.out.println("등록된 강의가 없습니다.");
                return;
            }

            System.out.println("성적을 입력할 강의 목록:");
            lectures.forEach((id, name) -> System.out.println(id + ". " + name));

            Scanner scanner = new Scanner(System.in);
            System.out.print("성적을 입력할 강의 번호를 선택하세요: ");
            int selectedLectureId = scanner.nextInt();
            scanner.nextLine(); // 버퍼 비우기

            // 선택한 강의의 학생 목록 표시
            List<Map<String, String>> students = professorDAO.getStudentsForLecture(selectedLectureId);
            if (students.isEmpty()) {
                System.out.println("등록된 학생이 없습니다.");
                return;
            }

            System.out.println("강의에 등록된 학생 목록:");
            for (int i = 0; i < students.size(); i++) {
                Map<String, String> student = students.get(i);
                System.out.printf("%d. %s (학번: %s)\n", i + 1, student.get("student_name"), student.get("student_id"));
            }

            System.out.print("성적을 입력할 학생의 학번을 입력하세요: ");
            String studentId = scanner.nextLine();
            System.out.print("성적을 입력하세요 (A+, A0, B+, B0, C+, C0, D+, D0, F): ");
            String grade = scanner.nextLine();

            professorDAO.saveGrade(selectedLectureId, studentId, grade);
            System.out.println("성적이 성공적으로 입력되었습니다.");
        } catch (SQLException e) {
            System.out.println("성적 입력 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 학생 성적 조회
    public void viewStudentGrades() {
        try {
            Map<Integer, String> lectures = professorDAO.getAllLectures();
            if (lectures.isEmpty()) {
                System.out.println("등록된 강의가 없습니다.");
                return;
            }

            System.out.println("성적을 확인할 강의 목록:");
            lectures.forEach((id, name) -> System.out.println(id + ". " + name));

            Scanner scanner = new Scanner(System.in);
            System.out.print("성적을 확인할 강의 번호를 선택하세요: ");
            int selectedLectureId = scanner.nextInt();
            scanner.nextLine(); // 버퍼 비우기

            Map<String, String> grades = professorDAO.getGrades(selectedLectureId);
            if (grades.isEmpty()) {
                System.out.println("등록된 학생 성적이 없습니다.");
                return;
            }

            System.out.println("과목의 학생 성적:");
            for (Map.Entry<String, String> entry : grades.entrySet()) {
                System.out.printf("학번: %s, 성적: %s\n", entry.getKey(), entry.getValue());
            }
        } catch (SQLException e) {
            System.out.println("성적 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
