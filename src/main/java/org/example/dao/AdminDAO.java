package org.example.dao;

import org.example.db.DBConnection;

import java.util.Scanner;

public class AdminDAO {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DBConnection dbConnection = new DBConnection();

    public static void addStudent() {
        System.out.println("새로운 학생 정보를 추가합니다.");
        System.out.print("학생의 이름을 입력하세요: ");
        String name = scanner.nextLine();
        System.out.print("학번을 입력하세요: ");
        String studentId = scanner.nextLine();
        System.out.print("비밀번호를 입력하세요: ");
        String password = scanner.nextLine();

        // DB에 학생 정보 추가
        try {
            dbConnection.connect();
            String insertQuery = String.format("INSERT INTO students (student_name, student_username, student_password) VALUES ('%s', '%s', '%s')",
                    name, studentId, password);
            dbConnection.insert(insertQuery);
            System.out.println("학생 정보가 성공적으로 추가되었습니다.");
        } catch (Exception e) {
            System.err.println("학생 정보 추가 중 오류가 발생했습니다: " + e.getMessage());
        } finally {
            dbConnection.close();
        }
    }

    public static void removeStudent() {
        System.out.println("삭제할 학생의 학번을 입력하세요:");
        String studentId = scanner.nextLine();

        // DB에서 학생 정보 삭제
        try {
            dbConnection.connect();
            String deleteQuery = String.format("DELETE FROM students WHERE student_username = '%s'", studentId);
            int deletedRows = dbConnection.delete(deleteQuery);

            if (deletedRows > 0) {
                System.out.println("학생 정보가 성공적으로 삭제되었습니다.");
            } else {
                System.out.println("해당 학생을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            System.err.println("학생 정보 삭제 중 오류가 발생했습니다: " + e.getMessage());
        } finally {
            dbConnection.close();
        }
    }

    public static void addNotice(String title, String content) {
        System.out.println("새로운 공지사항을 추가합니다.");

        // DB에 공지사항 정보 추가
        try {
            dbConnection.connect();
            String insertQuery = String.format("INSERT INTO notices (title, content) VALUES ('%s', '%s')",
                    title, content);
            dbConnection.insert(insertQuery);
            System.out.println("공지사항이 성공적으로 추가되었습니다.");
        } catch (Exception e) {
            System.err.println("공지사항 추가 중 오류가 발생했습니다: " + e.getMessage());
        } finally {
            dbConnection.close();
        }
    }

    public static void removeNotice(String title) {
        System.out.println("삭제할 공지사항의 제목을 입력하세요:");

        // DB에서 공지사항 정보 삭제
        try {
            dbConnection.connect();
            String deleteQuery = String.format("DELETE FROM notices WHERE title = '%s'", title);
            int deletedRows = dbConnection.delete(deleteQuery);

            if (deletedRows > 0) {
                System.out.println("공지사항이 성공적으로 삭제되었습니다.");
            } else {
                System.out.println("해당 공지사항을 찾을 수 없습니다.");
            }
        } catch (Exception e) {
            System.err.println("공지사항 삭제 중 오류가 발생했습니다: " + e.getMessage());
        } finally {
            dbConnection.close();
        }
    }
}
