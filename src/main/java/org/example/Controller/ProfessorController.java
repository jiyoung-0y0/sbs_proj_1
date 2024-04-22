package org.example.Controller;

import org.example.Main;
import org.example.service.ProfessorService;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class ProfessorController {
    private static Map<String, String> studentCredentials;
    private final ProfessorService professorService = new ProfessorService();

    public static void setStudentCredentials(Map<String, String> studentCredentials) {
        ProfessorController.studentCredentials = studentCredentials;
    }

    public void showPage(String username) {
        System.out.println("교수 페이지로 이동합니다. 환영합니다, " + username + "님!");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            int choice = -1;

            try {
                System.out.println("1. 강의 등록");
                System.out.println("2. 강의 삭제");
                System.out.println("3. 성적 입력/수정/삭제");
                System.out.println("4. 자신의 과목 학생들 성적 출력");
                System.out.println("5. 로그아웃");
                System.out.print("선택: ");

                choice = scanner.nextInt(); // 숫자 외의 입력은 예외 발생 가능
                scanner.nextLine(); // 엔터키 소모

                switch (choice) {
                    case 1:
                        professorService.registerLecture(); // 강의 등록
                        break;
                    case 2:
                        professorService.removeLecture(); // 강의 삭제
                        break;
                    case 3:
                        professorService.manageGrades(); // 성적 입력/수정/삭제
                        break;
                    case 4:
                        professorService.viewStudentGrades(); // 자신의 과목 학생들 성적 출력
                        break;
                    case 5:
                        System.out.println("로그아웃합니다.");
                        Main.showLoginPage(); // 메인 페이지로 돌아가기
                        return; // 반복 종료
                    default:
                        System.out.println("잘못된 선택입니다. 다시 선택하세요."); // 유효하지 않은 선택
                }
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력하세요."); // 잘못된 입력 알림
                scanner.nextLine(); // 잘못된 입력 제거
            }
        }
    }
}
