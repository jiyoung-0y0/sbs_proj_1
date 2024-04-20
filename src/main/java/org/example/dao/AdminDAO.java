package org.example.dao;

import org.example.db.DBConnection;

import java.util.*;

public class AdminDAO {

    private static final DBConnection dbConnection = new DBConnection(); // 데이터베이스 연결 객체

    public static void addStudent() {
        System.out.println("새로운 학생 정보를 추가합니다.");
        System.out.print("학생의 이름을 입력하세요: ");
        String name = new Scanner(System.in).nextLine();
        System.out.print("학번을 입력하세요: ");
        String studentId = new Scanner(System.in).nextLine();
        System.out.print("비밀번호를 입력하세요: ");
        String password = new Scanner(System.in).nextLine();

        try {
            dbConnection.connect(); // 데이터베이스 연결
            String insertQuery = String.format(
                    "INSERT INTO students (student_name, student_username, student_password) VALUES ('%s', '%s', '%s')",
                    name, studentId, password
            );
            dbConnection.insert(insertQuery);
            System.out.println("학생 정보가 성공적으로 추가되었습니다.");
        } catch (Exception e) {
            System.err.println("학생 정보 추가 중 오류가 발생했습니다: " + e.getMessage());
        } finally {
            dbConnection.close(); // 데이터베이스 연결 종료
        }
    }

    public static void removeStudent(int studentIndex) {
        List<Map<String, String>> students = getStudents(); // 학생 목록 가져오기
        if (studentIndex < 1 || studentIndex > students.size()) {
            System.out.println("잘못된 학생 번호입니다.");
            return;
        }

        String studentId = students.get(studentIndex - 1).get("student_username");

        try {
            dbConnection.connect(); // 데이터베이스 연결
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
            dbConnection.close(); // 데이터베이스 연결 종료
        }
    }

    public static void addNotice(String title, String content) {
        try {
            dbConnection.connect(); // 데이터베이스 연결
            String insertQuery = String.format("INSERT INTO notices (title, content) VALUES ('%s', '%s')", title, content);
            dbConnection.insert(insertQuery);
            System.out.println("공지사항이 성공적으로 추가되었습니다.");
        } catch (Exception e) {
            System.err.println("공지사항 추가 중 오류가 발생했습니다: " + e.getMessage());
        } finally {
            dbConnection.close(); // 데이터베이스 연결 종료
        }
    }

    public static void removeNotice(int noticeIndex) {
        List<Map<String, String>> notices = getNotices();
        if (noticeIndex < 1 || noticeIndex > notices.size()) {
            System.out.println("잘못된 공지사항 번호입니다.");
            return;
        }

        String title = notices.get(noticeIndex - 1).get("title");

        try {
            dbConnection.connect(); // 데이터베이스 연결
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
            dbConnection.close(); // 데이터베이스 연결 종료
        }
    }

    public static List<Map<String, String>> getNotices() {
        List<Map<String, String>> notices = new ArrayList<>();

        try {
            dbConnection.connect(); // 데이터베이스 연결
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
            dbConnection.close(); // 데이터베이스 연결 종료
        }

        return notices;
    }

    public static List<Map<String, String>> getStudents() {
        List<Map<String, String>> students = new ArrayList<>();

        try {
            dbConnection.connect(); // 데이터베이스 연결
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
            dbConnection.close(); // 데이터베이스 연결 종료
        }

        return students;
    }
}
