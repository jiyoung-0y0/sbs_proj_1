package org.example.service;

import org.example.dao.ProfessorDAO;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ProfessorService {
    private Map<String, String> lectures = new HashMap<>();
    private ProfessorDAO professorDAO = new ProfessorDAO();
    private StudentService studentService;

    public ProfessorService(StudentService studentService) {
        this.studentService = studentService;
    }

    public void registerLecture() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("추가할 강의 이름을 입력하세요: ");
        String lectureName = scanner.nextLine();
        System.out.print("추가할 강의 코드를 입력하세요: ");
        String lectureCode = scanner.nextLine();
        lectures.put(lectureName, lectureCode);
        professorDAO.saveLecture(lectureName, lectureCode);
        studentService.setLectures(lectures); // 학생 서비스에 강의 목록 설정
        System.out.println("강의 \"" + lectureName + "\"을(를) 등록했습니다.");
    }

    public void removeLecture() {
        Scanner scanner = new Scanner(System.in);
        if (lectures.isEmpty()) {
            System.out.println("삭제할 강의가 없습니다.");
            return;
        }
        System.out.println("삭제할 강의 목록:");
        for (String lectureName : lectures.keySet()) {
            System.out.println("- " + lectureName);
        }
        System.out.print("삭제할 강의 이름을 입력하세요: ");
        String lectureName = scanner.nextLine();
        if (!lectures.containsKey(lectureName)) {
            System.out.println("해당 강의가 존재하지 않습니다.");
            return;
        }
        lectures.remove(lectureName);
        professorDAO.deleteLecture(lectureName);
        System.out.println("강의 \"" + lectureName + "\"을(를) 삭제했습니다.");
    }

    public void manageGrades() {
        Scanner scanner = new Scanner(System.in);
        if (lectures.isEmpty()) {
            System.out.println("등록된 강의가 없습니다. 강의를 먼저 등록하세요.");
            return;
        }
        System.out.println("등록된 강의 목록:");
        for (String lectureName : lectures.keySet()) {
            System.out.println("- " + lectureName);
        }
        System.out.print("성적을 입력할 강의 이름을 선택하세요: ");
        String selectedLecture = scanner.nextLine();
        if (!lectures.containsKey(selectedLecture)) {
            System.out.println("해당 강의가 존재하지 않습니다.");
            return;
        }
        System.out.print("성적을 입력할 학생의 학번을 입력하세요: ");
        String studentId = scanner.nextLine();
        System.out.print("성적을 입력하세요 (A+, A0, B+, B0, C+, C0, D+, D0, F): ");
        String grade = scanner.nextLine();
        try {
            professorDAO.saveGrade(selectedLecture, studentId, grade);
            System.out.println("성적이 성공적으로 입력되었습니다.");
        } catch (SQLException e) {
            System.out.println("성적 입력 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    public void viewStudentGrades() {
        Scanner scanner = new Scanner(System.in);
        if (lectures.isEmpty()) {
            System.out.println("등록된 강의가 없습니다.");
            return;
        }
        System.out.println("등록된 강의 목록:");
        for (String lectureName : lectures.keySet()) {
            System.out.println("- " + lectureName);
        }
        System.out.print("성적을 확인할 강의 이름을 선택하세요: ");
        String selectedLecture = scanner.nextLine();
        if (!lectures.containsKey(selectedLecture)) {
            System.out.println("해당 강의가 존재하지 않습니다.");
            return;
        }
        try {
            Map<String, String> grades = professorDAO.getGrades(selectedLecture);
            if (grades.isEmpty()) {
                System.out.println("등록된 학생 성적이 없습니다.");
                return;
            }
            System.out.println(selectedLecture + " 과목의 성적:");
            for (Map.Entry<String, String> entry : grades.entrySet()) {
                System.out.println("학번: " + entry.getKey() + ", 성적: " + entry.getValue());
            }
        } catch (SQLException e) {
            System.out.println("성적 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
