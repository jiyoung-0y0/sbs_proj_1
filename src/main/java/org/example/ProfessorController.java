package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProfessorController {
    private static List<String> lectureList = new ArrayList<>();
    private static List<String> grades = new ArrayList<>();
    private static List<String> studentGrades = new ArrayList<>();

    public static void showPage(String username) {
        System.out.println("교수 페이지로 이동합니다. 환영합니다, " + username + "님!");
        // 교수 페이지의 기능을 선택할 수 있는 메뉴 표시
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("1. 강의 등록");
            System.out.println("2. 성적 입력/수정/삭제");
            System.out.println("3. 자신의 과목 학생들 성적 출력");
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
                    manageGrades();
                    break;
                case 3:
                    viewStudentGrades();
                    break;
                case 4:
                    // 관리자가 작성한 공지 확인 기능을 구현하세요
                    System.out.println("관리자가 작성한 공지를 확인합니다.");
                    break;
                case 5:
                    System.out.println("로그아웃합니다.");
                    break;
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택하세요.");
            }
        } while (choice != 5);
    }

    // 강의 등록 기능
    private static void registerLecture() {
        // 강의 등록 기능을 구현하세요
        System.out.println("강의를 등록합니다.");
    }

    // 성적 입력/수정/삭제 기능
    private static void manageGrades() {
        // 성적 입력/수정/삭제 기능을 구현하세요
        System.out.println("성적을 입력/수정/삭제합니다.");
    }

    // 자신의 과목 학생들 성적 출력 기능
    private static void viewStudentGrades() {
        // 자신의 과목 학생들 성적 출력 기능을 구현하세요
        System.out.println("자신의 과목 학생들 성적을 출력합니다.");
    }
}
