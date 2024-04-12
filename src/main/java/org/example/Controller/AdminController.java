package org.example.Controller;

import org.example.Main;
import org.example.service.AdminService;

import java.util.Scanner;

public class AdminController {
    private static final Scanner scanner = new Scanner(System.in);

    public static void showPage(String username) {
        System.out.println("관리자 페이지로 이동합니다. 환영합니다, " + username + "님!");
        int choice;
        do {
            System.out.println("1. 학생 정보 관리");
            System.out.println("2. 일정 및 공지사항 관리");
            System.out.println("3. 로그아웃");
            System.out.print("선택: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    AdminService.manageStudentInfo();
                    break;
                case 2:
                    AdminService.manageScheduleAndNotice();
                    break;
                case 3:
                    System.out.println("로그아웃합니다.");
                    Main.showLoginPage();
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택하세요.");
            }
        } while (true);
    }
}
