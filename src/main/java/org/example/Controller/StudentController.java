package org.example.Controller;

import org.example.service.StudentService;

import java.util.Scanner;

public class StudentController {
    private StudentService studentService = new StudentService();

    public void showPage(String username) {
        System.out.println("학생 페이지로 이동합니다. 환영합니다, " + username + "님!");
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("0. 로그아웃");
            System.out.println("1. 강의신청");
            System.out.println("2. 자기 시간표 확인");
            System.out.println("3. 자신의 과목과 성적 확인");
            System.out.println("4. 관리자가 작성한 공지 확인");
            System.out.print("선택: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // 엔터키 소비

            switch (choice) {
                case 1:
                    studentService.registerLecture();
                    break;
                case 2:
                    studentService.viewTimetable();
                    break;
                case 3:
                    studentService.viewSubjectsAndGrades();
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
}
