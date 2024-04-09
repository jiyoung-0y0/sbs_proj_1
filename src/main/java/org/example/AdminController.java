package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminController {
    private static List<String> studentInfo = new ArrayList<>();
    private static List<String> scheduleAndNotice = new ArrayList<>();

    public static void showPage(String username) {
        System.out.println("관리자 페이지로 이동합니다. 환영합니다, " + username + "님!");
        // 관리자 페이지의 기능을 선택할 수 있는 메뉴 표시
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("1. 학생 정보 관리");
            System.out.println("2. 일정 및 공지사항 관리");
            System.out.println("3. 로그아웃");
            System.out.print("선택: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // 엔터키 소비

            switch (choice) {
                case 1:
                    manageStudentInfo();
                    break;
                case 2:
                    manageScheduleAndNotice();
                    break;
                case 3:
                    System.out.println("로그아웃합니다.");
                    return; // 메서드 종료
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택하세요.");
            }
        } while (true); // 무한 루프
    }

    // 학생 정보 관리 기능
    private static void manageStudentInfo() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("1. 학생 추가");
            System.out.println("2. 학생 삭제");
            System.out.println("3. 뒤로 가기");
            System.out.print("선택: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // 엔터키 소비

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    removeStudent();
                    break;
                case 3:
                    System.out.println("학생 정보 관리를 종료합니다.");
                    return; // 메서드 종료
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택하세요.");
            }
        } while (true); // 무한 루프
    }

    // 학생 추가 기능
    private static void addStudent() {
        // 학생 추가 기능을 구현하세요
        System.out.println("학생을 추가합니다.");
    }

    // 학생 삭제 기능
    private static void removeStudent() {
        // 학생 삭제 기능을 구현하세요
        System.out.println("학생을 삭제합니다.");
    }

    // 일정 및 공지사항 관리 기능
    private static void manageScheduleAndNotice() {
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("1. 일정 추가");
            System.out.println("2. 공지사항 추가");
            System.out.println("3. 일정 삭제");
            System.out.println("4. 공지사항 삭제");
            System.out.println("5. 뒤로 가기");
            System.out.print("선택: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // 엔터키 소비

            switch (choice) {
                case 1:
                    addSchedule();
                    break;
                case 2:
                    addNotice();
                    break;
                case 3:
                    removeSchedule();
                    break;
                case 4:
                    removeNotice();
                    break;
                case 5:
                    System.out.println("일정 및 공지사항 관리를 종료합니다.");
                    return; // 메서드 종료
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택하세요.");
            }
        } while (true); // 무한 루프
    }

    // 일정 추가 기능
    private static void addSchedule() {
        // 일정 추가 기능을 구현하세요
        System.out.println("일정을 추가합니다.");
    }

    // 공지사항 추가 기능
    private static void addNotice() {
        // 공지사항 추가 기능을 구현하세요
        System.out.println("공지사항을 추가합니다.");
    }

    // 일정 삭제 기능
    private static void removeSchedule() {
        // 일정 삭제 기능을 구현하세요
        System.out.println("일정을 삭제합니다.");
    }

    // 공지사항 삭제 기능
    private static void removeNotice() {
        // 공지사항 삭제 기능을 구현하세요
        System.out.println("공지사항을 삭제합니다.");
    }
}
