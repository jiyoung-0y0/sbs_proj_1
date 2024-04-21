package org.example.Controller;

import org.example.Main;
import org.example.service.StudentService;
import java.util.Scanner;

public class StudentController {
    private final StudentService studentService;
    private String studentUsername; // 로그인한 학생의 학번

    public StudentController() {
        this.studentService = new StudentService(); // StudentService 객체 생성
    }

    public void showPage(String studentUsername) {
        this.studentUsername = studentUsername; // 로그인한 학생의 학번 설정
        studentService.setStudentUsername(studentUsername); // StudentService에 학생 학번 설정

        System.out.println("학생 페이지로 이동합니다. 환영합니다, " + studentUsername + "님!");

        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) { // 학생이 로그아웃할 때까지 반복
            System.out.println("0. 로그아웃");
            System.out.println("1. 강의 신청");
            System.out.println("2. 시간표 확인");
            System.out.println("3. 과목 및 성적 확인");
            System.out.println("4. 공지 확인");
            System.out.print("선택: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // 엔터키 소비

            switch (choice) {
                case 1:
                    studentService.registerLecture(); // 강의 신청
                    break;
                case 2:
                    studentService.viewTimetable(); // 시간표 확인
                    break;
                case 3:
                    studentService.viewSubjectsAndGrades(); // 과목 및 성적 확인
                    break;
                case 4:
                    System.out.println("관리자가 작성한 공지를 확인합니다."); // 공지 확인
                    break;
                case 0:
                    System.out.println("로그아웃합니다.");
                    Main.showLoginPage(); // 메인 페이지로 돌아가기
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택하세요.");
            }
        }
    }
}
