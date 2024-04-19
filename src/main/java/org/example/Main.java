package org.example;

import org.example.Controller.AdminController;
import org.example.Controller.ProfessorController;
import org.example.Controller.StudentController;
import org.example.db.DBConnection;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class Main {

    private static Map<String, String> studentCredentials = new HashMap<>();
    private static Map<String, String> professorCredentials = new HashMap<>();
    private static Map<String, String> adminCredentials = new HashMap<>();

    public static void main(String[] args) {
        try {
            initializeDB();
            initializeCredentials();
            ProfessorController.setStudentCredentials(studentCredentials);
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
        try {
            DBConnection dbConnection = new DBConnection();
            dbConnection.connect();
            studentCredentials = dbConnection.getStudentCredentials();
            dbConnection.disconnect();
        } catch (SQLException e) {
            System.out.println("학생 정보를 가져오는 중 오류가 발생했습니다: " + e.getMessage());
        }

        for (int i = 1; i <= 3; i++) {
            professorCredentials.put("p1000" + i, "ppassword" + i);
        }

        adminCredentials.put("admin", "adminpassword");
    }

    public static void showLoginPage() {
        Scanner scanner = new Scanner(System.in);

        boolean loginSuccess = false;
        int choice;
        do {
            choice = -1; // 초기화를 -1로 변경하여 잘못된 입력을 감지할 수 있도록 함
            System.out.println("사용자 유형을 선택하세요: ");
            System.out.println("1. 학생");
            System.out.println("2. 교수");
            System.out.println("3. 관리자");
            System.out.println("0. 종료");
            System.out.print("선택: ");
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                if (choice < 0 || choice > 3) {
                    System.out.println("잘못된 선택입니다. 0부터 3 사이의 숫자를 입력하세요.");
                } else if (choice == 0) {
                    System.out.println("프로그램을 종료합니다.");
                    return; // 종료
                } else {
                    String username;
                    do {
                        System.out.print("사용자 이름을 입력하세요: ");
                        username = scanner.nextLine();
                    } while (!validateUsername(username));

                    System.out.print("비밀번호를 입력하세요: ");
                    String password = scanner.nextLine();

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
                    if (!loginSuccess) {
                        System.out.println("로그인 실패. 잘못된 사용자 이름 또는 비밀번호입니다. 다시 시도하세요.");
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("잘못된 선택입니다. 숫자를 입력하세요.");
                scanner.nextLine();
            }
        } while (choice != 0 || !loginSuccess);

        scanner.close();
    }

    private static boolean validateUsername(String username) {
        return studentCredentials.containsKey(username) ||
                professorCredentials.containsKey(username) ||
                adminCredentials.containsKey(username);
    }

    private static boolean login(String username, String password, Map<String, String> credentials) {
        return credentials.containsKey(username) && credentials.get(username).equals(password);
    }
}
