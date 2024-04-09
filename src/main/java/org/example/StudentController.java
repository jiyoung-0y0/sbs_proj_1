package org.example;

import java.util.Scanner;

public class StudentController {
    public static void showPage(String username) {
        System.out.println("학생 페이지로 이동합니다. 환영합니다, " + username + "님!");
        // 학생 페이지의 기능을 선택할 수 있는 메뉴 표시
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("1. 강의신청");
            System.out.println("2. 자기 시간표 확인");
            System.out.println("3. 자신의 과목과 성적 확인");
            System.out.println("4. 관리자가 작성한 공지 확인");
            System.out.println("5. 로그아웃");
            System.out.print("선택: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // 엔터키 소비

            switch (choice) {
                case 1:
                    registerLecture();
                    break;
                case 2:
                    viewTimetable();
                    break;
                case 3:
                    viewSubjectsAndGrades();
                    break;
                case 4:
                    // 관리자가 작성한 공지 확인 기능을 구현하세요
                    System.out.println("관리자가 작성한 공지를 확인합니다.");
                    break;
                case 0:
                    System.out.println("프로그램을 종료합니다.");
                    break;
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택하세요.");
            }
        } while (choice != 0);
    }

    // 강의 신청 기능
    private static void registerLecture() {
        // 강의 신청 기능을 구현하세요
        System.out.println("강의를 신청합니다.");
    }

    // 시간표 확인 기능
    private static void viewTimetable() {
        // 시간표 확인 기능을 구현하세요
        System.out.println("시간표를 확인합니다.");
    }

    // 과목과 성적 확인 기능
    private static void viewSubjectsAndGrades() {
        // 과목과 성적 확인 기능을 구현하세요
        System.out.println("과목과 성적을 확인합니다.");
    }
}
