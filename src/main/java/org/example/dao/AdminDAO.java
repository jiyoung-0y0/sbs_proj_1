package org.example.dao;

import org.example.db.DBConnection;
import java.util.*;

public class AdminDAO {
    private static final DBConnection dbConnection = new DBConnection();

    public static void addStudent() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("새로운 학생의 이름을 입력하세요: ");
        String name = scanner.nextLine();
        System.out.print("학번을 입력하세요: ");
        String studentId = scanner.nextLine();
        System.out.print("비밀번호를 입력하세요: ");
        String password = scanner.nextLine();

        try {
            dbConnection.connect();

            String checkQuery = String.format("SELECT * FROM students WHERE student_username = '%s'", studentId);
            List<Map<String, Object>> existingStudent = dbConnection.selectRows(checkQuery);

            if (!existingStudent.isEmpty()) {
                System.out.println("이미 등록된 학번입니다. 다른 학번을 사용하세요.");
                return;
            }

            String insertQuery = String.format(
                    "INSERT INTO students (student_name, student_username, student_password) VALUES ('%s', '%s', '%s')",
                    name, studentId, password
            );

            dbConnection.insert(insertQuery);
            System.out.println("학생 정보가 성공적으로 추가되었습니다.");
        } catch (Exception e) {
            if (e.getMessage().contains("Only 5 students can be registered.")) {
                System.out.println("학생 등록 수 제한에 도달했습니다. 최대 5명까지만 등록할 수 있습니다.");
            } else {
                System.err.println("학생 정보 추가 중 오류가 발생했습니다: " + e.getMessage());
            }
        } finally {
            dbConnection.close();
        }
    }

    public static List<Map<String, String>> getStudents() {
        List<Map<String, String>> students = new ArrayList<>();
        try {
            dbConnection.connect();
            String selectQuery = "SELECT student_username, student_name FROM students";
            List<Map<String, Object>> resultSet = dbConnection.selectRows(selectQuery);

            for (Map<String, Object> row : resultSet) {
                Map<String, String> student = new HashMap<>();
                student.put("student_username", (String) row.get("student_username"));
                student.put("student_name", (String) row.get("student_name"));
                students.add(student);
            }
        } catch (Exception e) {
            System.err.println("학생 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
        } finally {
            dbConnection.close();
        }

        return students;
    }

    public static void removeStudent() {
        List<Map<String, String>> students = getStudents();
        if (students.isEmpty()) {
            System.out.println("등록된 학생이 없습니다.");
            return;
        }

        System.out.println("학생 목록:");
        for (int i = 0; i < students.size(); i++) {
            Map<String, String> student = students.get(i);
            System.out.printf("%d. %s (학번: %s)\n", i + 1, student.get("student_name"), student.get("student_username"));
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("삭제할 학생 번호를 선택하세요: ");
        int studentIndex = scanner.nextInt();

        if (studentIndex < 1 || studentIndex > students.size()) {
            System.out.println("잘못된 학생 번호입니다.");
            return;
        }

        String studentUsername = students.get(studentIndex - 1).get("student_username");

        try {
            dbConnection.connect();
            String deleteQuery = String.format("DELETE FROM students WHERE student_username = '%s'", studentUsername);
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

    public static void addNotice(String title, String content) {
        try {
            dbConnection.connect();
            String insertQuery = String.format(
                    "INSERT INTO notices (title, content) VALUES ('%s', '%s')",
                    title, content
            );
            dbConnection.insert(insertQuery);
            System.out.println("공지사항이 성공적으로 추가되었습니다.");
        } catch (Exception e) {
            System.err.println("공지사항 추가 중 오류가 발생했습니다: " + e.getMessage());
        } finally {
            dbConnection.close();
        }
    }

    public static void removeNotice(int noticeIndex) {
        List<Map<String, String>> notices = getNotices();
        if (notices.isEmpty()) {
            System.out.println("등록된 공지사항이 없습니다.");
            return;
        }

        if (noticeIndex < 1 || noticeIndex > notices.size()) {
            System.out.println("잘못된 공지사항 번호입니다.");
            return;
        }

        String title = notices.get(noticeIndex - 1).get("title");

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
