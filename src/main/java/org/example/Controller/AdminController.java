package org.example.Controller;

import org.example.Main;
import org.example.service.AdminService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminController {
    private static final Scanner scanner = new Scanner(System.in);

    public static void showPage(String username) {
        System.out.println("관리자 페이지로 이동합니다. 환영합니다, " + username + "님!");

        while (true) {
            int choice = -1;
            try {
                System.out.println("1. 학생 정보 관리");
                System.out.println("2. 일정 및 공지사항 관리");
                System.out.println("3. 로그아웃");
                System.out.print("선택: ");

                choice = scanner.nextInt(); // 예외 발생 가능
                scanner.nextLine(); // 엔터키 소모

                switch (choice) {
                    case 1:
                        AdminService.manageStudentInfo(); // 학생 정보 관리
                        break;
                    case 2:
                        AdminService.manageScheduleAndNotice(); // 일정 및 공지사항 관리
                        break;
                    case 3:
                        System.out.println("로그아웃합니다.");
                        Main.showLoginPage(); // 로그인 페이지로 돌아가기
                        return; // 루프를 벗어나기
                    default:
                        System.out.println("잘못된 선택입니다. 다시 선택하세요.");
                }
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력하세요.");
                scanner.nextLine(); // 잘못된 입력 제거
            }
        }
    }
}