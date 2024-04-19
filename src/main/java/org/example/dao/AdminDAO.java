package org.example.dao;

import org.example.db.DBConnection;

import java.sql.ResultSet;
import java.util.*;

public class AdminDAO {

    private static final Scanner scanner = new Scanner(System.in);
    private static final DBConnection dbConnection = new DBConnection();
    private static List<String> notices = new ArrayList<>();

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

    public static void removeNotice(int noticeNumber) {
        System.out.println("삭제할 공지사항의 번호를 입력하세요:");

        // DB에서 공지사항 정보 삭제
        try {
            List<Map<String, String>> existingNotices = getNotices(); // 연결을 여기서 열어서 사용
            if (noticeNumber < 1 || noticeNumber > existingNotices.size()) {
                System.out.println("잘못된 번호입니다.");
                return;
            }
            String title = existingNotices.get(noticeNumber - 1).get("title");

            dbConnection.connect(); // 연결 상태 확인 후 연결
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

    public static List<Map<String, String>> getNotices() {
        List<Map<String, String>> notices = new ArrayList<>();

        try {
            dbConnection.connect();
            String selectQuery = "SELECT title, content FROM notices";
            List<Map<String, Object>> resultSet = dbConnection.selectRows(selectQuery);

            for (Map<String, Object> row : resultSet) {
                Map<String, String> notice = new HashMap<>();
                notice.put("title", (String) row.get("title"));
                notice.put("content", (String) row.get("content"));
                notices.add(notice);
            }
        } catch (Exception e) {
            System.err.println("공지사항 조회 중 오류가 발생했습니다: " + e.getMessage());
        } finally {
            dbConnection.close();
        }

        return notices;
    }
}