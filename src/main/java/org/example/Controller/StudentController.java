package org.example.Controller;

import org.example.Main;
import org.example.service.StudentService;

import java.util.InputMismatchException;
import java.util.Scanner;

public class StudentController {
    private final StudentService studentService;

    public StudentController() {
        this.studentService = new StudentService(); // StudentService 객체 생성
    }

    public void showPage(String studentUsername) {
        studentService.setStudentUsername(studentUsername); // StudentService에 학생 학번 설정
        System.out.println("학생 페이지로 이동합니다. 환영합니다, " + studentUsername + "님!");

        Scanner scanner = new Scanner(System.in);

        while (true) { // 학생이 로그아웃할 때까지 반복
            int choice = -1;

            try {
                System.out.println("0. 로그아웃");
                System.out.println("1. 강의 신청");
                System.out.println("2. 과목 및 성적 확인");
                System.out.println("3. 공지 확인");
                System.out.print("선택: ");

                choice = scanner.nextInt(); // 예외 발생 가능
                scanner.nextLine(); // 엔터키 소모

                switch (choice) {
                    case 0: // 로그아웃
                        System.out.println("로그아웃합니다.");
                        Main.showLoginPage(); // 메인 페이지로 돌아가기
                        return;
                    case 1: // 강의 신청
                        studentService.registerLecture();
                        break;
                    case 2: // 과목 및 성적 확인
                        studentService.viewSubjectsAndGrades();
                        break;
                    case 3: // 공지 확인
                        studentService.viewNotices(); // 공지 확인 기능 호출
                        break;
                    default:
                        System.out.println("잘못된 선택입니다. 다시 선택하세요."); // 잘못된 선택 처리
                }
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력하세요."); // 예외 처리
                scanner.nextLine(); // 잘못된 입력 제거
            }
        }
    }
}