package org.example;

import org.example.Controller.AdminController;
import org.example.Controller.ProfessorController;
import org.example.Controller.StudentController;
import org.example.service.StudentService;
import org.example.db.DBConnection;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Map<String, String> professorCredentials = new HashMap<>();
    private static final Map<String, String> adminCredentials = new HashMap<>();

    public static void main(String[] args) {
        try {
            initializeDB();
            initializeCredentials();
            showLoginPage();
        } catch (SQLException e) {
            System.out.println("DB 연결 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    private static void initializeDB() throws SQLException {
        DBConnection.DB_NAME = "sbs_proj_1";
        DBConnection.DB_USER = "sbsst";
        DBConnection.DB_PASSWORD = "sbs123414";
        DBConnection.DB_PORT = 3306;

        DBConnection dbConnection = new DBConnection();
        dbConnection.connect();
    }

    private static void initializeCredentials() {
        adminCredentials.put("admin", "adminpassword"); // 관리자 로그인 정보

        // 교수 로그인 정보
        for (int i = 1; i <= 3; i++) {
            professorCredentials.put("p1000" + i, "ppassword" + i);
        }
    }

    public static void showLoginPage() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            int choice = -1;

            try {
                System.out.println("사용자 유형을 선택하세요:");
                System.out.println("1. 학생");
                System.out.println("2. 교수");
                System.out.println("3. 관리자");
                System.out.println("0. 종료");
                System.out.print("선택: ");

                choice = scanner.nextInt(); // 사용자가 숫자가 아닌 값을 입력하면 예외 발생 가능
                scanner.nextLine(); // 엔터키 소모
            } catch (InputMismatchException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력하세요.");
                scanner.nextLine(); // 잘못된 입력 제거
                continue; // 다시 선택을 요청합니다.
            }

            if (choice == 0) {
                System.out.println("프로그램을 종료합니다.");
                System.exit(0);
            }

            System.out.print("사용자 이름을 입력하세요: ");
            String username = scanner.nextLine(); // 사용자 이름 입력

            System.out.print("비밀번호를 입력하세요: ");
            String password = scanner.nextLine(); // 비밀번호 입력

            switch (choice) {
                case 1: // 학생 로그인
                    StudentService studentService = new StudentService();
                    boolean isAuthenticated = studentService.authenticateStudent(username, password); // 학생 인증
                    if (isAuthenticated) {
                        StudentController studentController = new StudentController();
                        studentController.showPage(username); // 학생 페이지로 이동
                    } else {
                        System.out.println("로그인 실패. 사용자 이름 또는 비밀번호가 잘못되었습니다.");
                    }
                    break;

                case 2: // 교수 로그인
                    if (login(username, password, professorCredentials)) {
                        ProfessorController professorController = new ProfessorController();
                        professorController.showPage(username); // 교수 페이지로 이동
                    } else {
                        System.out.println("로그인 실패. 사용자 이름 또는 비밀번호가 잘못되었습니다.");
                    }
                    break;

                case 3: // 관리자 로그인
                    if (login(username, password, adminCredentials)) {
                        AdminController adminController = new AdminController();
                        adminController.showPage(username); // 관리자 페이지로 이동
                    } else {
                        System.out.println("로그인 실패. 사용자 이름 또는 비밀번호가 잘못되었습니다.");
                    }
                    break;

                default:
                    System.out.println("잘못된 선택입니다. 다시 시도하십시오.");
                    continue; // 다시 선택을 요청합니다.
            }
        }
    }

    private static boolean login(String username, String password, Map<String, String> credentials) {
        return credentials.containsKey(username) && credentials.get(username).equals(password);
    }
}