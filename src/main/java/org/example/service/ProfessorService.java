// ProfessorService.java
package org.example.service;

import org.example.dao.ProfessorDAO;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ProfessorService {
    private Map<String, String> lectures = new HashMap<>();
    private ProfessorDAO professorDAO = new ProfessorDAO();

    public void registerLecture() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("추가할 강의 이름을 입력하세요: ");
        String lectureName = scanner.nextLine();
        System.out.print("추가할 강의 코드를 입력하세요: ");
        String lectureCode = scanner.nextLine();
        lectures.put(lectureName, lectureCode);
        professorDAO.saveLecture(lectureName, lectureCode);
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
        // 성적 입력/수정/삭제 기능을 구현하세요
        System.out.println("성적 입력/수정/삭제 기능을 구현하세요");
    }

    public void viewStudentGrades() {
        // 학생들 성적 출력 기능을 구현하세요
        System.out.println("학생들 성적 출력 기능을 구현하세요");
    }
}
