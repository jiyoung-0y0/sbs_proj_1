package org.example;

import org.example.Controller.AdminController;
import org.example.Controller.ProfessorController;
import org.example.Controller.StudentController;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    // 각 유저의 아이디와 비밀번호를 저장하는 맵
    private static Map<String, String> studentCredentials = new HashMap<>();
    private static Map<String, String> professorCredentials = new HashMap<>();
    private static Map<String, String> adminCredentials = new HashMap<>();

    public static void main(String[] args) {
        // 유저 정보 초기화
        initializeCredentials();
        // 교수 페이지에서 사용할 학생 정보 설정
        ProfessorController.setStudentCredentials(studentCredentials);
        // 로그인 페이지 표시
        showLoginPage();
    }

    // 유저 정보 초기화 메서드
    private static void initializeCredentials() {
        // 학생 계정 정보 초기화
        for (int i = 1; i <= 6; i++) {
            studentCredentials.put("s2021000" + i, "spassword" + i);
        }

        // 교수 계정 정보 초기화
        for (int i = 1; i <= 3; i++) {
            professorCredentials.put("p1000" + i, "ppassword" + i);
        }

        // 관리자 계정 정보 초기화
        adminCredentials.put("admin", "adminpassword");
    }

    // 로그인 페이지 표시 및 로그인 시도 메서드
    private static void showLoginPage() {
        Scanner scanner = new Scanner(System.in);

        boolean loginSuccess = false;
        do {
            int choice;
            // 사용자가 올바른 선택을 할 때까지 반복해서 선택하도록 하는 루프
            do {
                choice = 0; // 초기화
                System.out.println("사용자 유형을 선택하세요: ");
                System.out.println("1. 학생");
                System.out.println("2. 교수");
                System.out.println("3. 관리자");
                System.out.print("선택: ");
                try {
                    choice = scanner.nextInt(); // 정수 입력 받기
                    scanner.nextLine(); // 엔터키 소비
                    if (choice < 1 || choice > 3) {
                        System.out.println("잘못된 선택입니다. 1부터 3 사이의 숫자를 입력하세요.");
                    }
                } catch (Exception e) {
                    System.out.println("잘못된 선택입니다. 숫자를 입력하세요.");
                    scanner.nextLine(); // 잘못된 입력을 소비하여 무한루프를 방지합니다.
                }
            } while (choice < 1 || choice > 3);

            System.out.print("사용자 이름을 입력하세요: ");
            String username = scanner.nextLine();

            // 아이디가 존재하는 경우에만 비밀번호 입력을 요구하도록 함
            if (validateUsername(username)) {
                System.out.print("비밀번호를 입력하세요: ");
                String password = scanner.nextLine();

                // 선택된 유저 타입에 따라 로그인 시도
                switch (choice) {
                    case 1:
                        StudentController studentController = new StudentController();
                        loginSuccess = login(username, password, studentCredentials);
                        if (loginSuccess) {
                            studentController.showPage(username);
                        }
                        break;
                    case 2:
                        ProfessorController professorController = new ProfessorController();
                        loginSuccess = login(username, password, professorCredentials);
                        if (loginSuccess) {
                            professorController.showPage(username);
                        }
                        break;
                    case 3:
                        AdminController adminController = new AdminController();
                        loginSuccess = login(username, password, adminCredentials);
                        if (loginSuccess) {
                            adminController.showPage(username);
                        }
                        break;
                }
            } else {
                System.out.println("잘못된 사용자 이름입니다. 다시 시도하세요.");
            }

            // 로그인 실패 시 메시지 출력 및 재시도
            if (!loginSuccess) {
                System.out.println("로그인 실패. 잘못된 사용자 이름 또는 비밀번호입니다. 다시 시도하세요.");
            }
        } while (!loginSuccess); // 로그인이 성공할 때까지 반복

        scanner.close(); // Scanner 닫기
    }

    // 입력된 사용자 이름이 유효한지 확인하는 메서드
    private static boolean validateUsername(String username) {
        return studentCredentials.containsKey(username) ||
                professorCredentials.containsKey(username) ||
                adminCredentials.containsKey(username);
    }

    // 유저 로그인을 확인하는 메서드
    private static boolean login(String username, String password, Map<String, String> credentials) {
        return credentials.containsKey(username) && credentials.get(username).equals(password);
    }
}
