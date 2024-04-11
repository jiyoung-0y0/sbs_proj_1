// ProfessorController.java
package org.example.Controller;

import org.example.service.ProfessorService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ProfessorController {
    private static Map<String, String> studentCredentials = new HashMap<>();
    private ProfessorService professorService = new ProfessorService();

    public static void setStudentCredentials(Map<String, String> credentials) {
        studentCredentials = credentials;
    }

    public void showPage(String username) {
        System.out.println("교수 페이지로 이동합니다. 환영합니다, " + username + "님!");
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("1. 강의 등록");
            System.out.println("2. 강의 삭제");
            System.out.println("3. 성적 입력/수정/삭제");
            System.out.println("4. 자신의 과목 학생들 성적 출력");
            System.out.println("5. 관리자가 작성한 공지 확인");
            System.out.println("6. 로그아웃");
            System.out.print("선택: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // 엔터키 소비

            switch (choice) {
                case 1:
                    professorService.registerLecture();
                    break;
                case 2:
                    professorService.removeLecture();
                    break;
                case 3:
                    professorService.manageGrades();
                    break;
                case 4:
                    professorService.viewStudentGrades();
                    break;
                case 5:
                    // 관리자가 작성한 공지 확인 기능을 구현하세요
                    System.out.println("관리자가 작성한 공지를 확인합니다.");
                    break;
                case 6:
                    System.out.println("로그아웃합니다.");
                    break;
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택하세요.");
            }
        } while (choice != 6);
    }
}
