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
        dbConnection.connect();
        String insertQuery = String.format("INSERT INTO students (name, student_id, password) VALUES ('%s', '%s', '%s')",
                name, studentId, password);
        dbConnection.insert(insertQuery);
        dbConnection.close();
        System.out.println("학생 정보가 성공적으로 추가되었습니다.");
    }

    public static void removeStudent() {
        System.out.println("삭제할 학생의 학번을 입력하세요:");
        String studentId = scanner.nextLine();

        // DB에서 학생 정보 삭제
        dbConnection.connect();
        String deleteQuery = String.format("DELETE FROM students WHERE student_id = '%s'", studentId);
        int deletedRows = dbConnection.delete(deleteQuery);
        dbConnection.close();

        if (deletedRows > 0) {
            System.out.println("학생 정보가 성공적으로 삭제되었습니다.");
        } else {
            System.out.println("해당 학생을 찾을 수 없습니다.");
        }
    }

    public static void addNotice() {
        System.out.println("새로운 공지사항을 추가합니다.");
        System.out.print("공지 제목을 입력하세요: ");
        String title = scanner.nextLine();
        System.out.print("공지 내용을 입력하세요: ");
        String content = scanner.nextLine();

        // DB에 공지사항 정보 추가
        dbConnection.connect();
        String insertQuery = String.format("INSERT INTO notices (title, content) VALUES ('%s', '%s')",
                title, content);
        dbConnection.insert(insertQuery);
        dbConnection.close();
        System.out.println("공지사항이 성공적으로 추가되었습니다.");
    }

    public static void removeNotice() {
        System.out.println("삭제할 공지사항의 제목을 입력하세요:");
        String title = scanner.nextLine();

        // DB에서 공지사항 정보 삭제
        dbConnection.connect();
        String deleteQuery = String.format("DELETE FROM notices WHERE title = '%s'", title);
        int deletedRows = dbConnection.delete(deleteQuery);
        dbConnection.close();

        if (deletedRows > 0) {
            System.out.println("공지사항이 성공적으로 삭제되었습니다.");
        } else {
            System.out.println("해당 공지사항을 찾을 수 없습니다.");
        }
    }
}
