package org.example.service;

import org.example.dao.AdminDAO;
import java.util.*;

public class AdminService {
    private static final Scanner scanner = new Scanner(System.in);

    public static void manageStudentInfo() {
        while (true) {
            int choice = -1;

            try {
                System.out.println("1. 학생 추가");
                System.out.println("2. 학생 삭제");
                System.out.println("3. 학생 목록 보기");
                System.out.println("4. 뒤로 가기");
                System.out.print("선택: ");

                choice = Integer.parseInt(scanner.nextLine()); // 예외 발생 가능

                switch (choice) {
                    case 1:
                        AdminDAO.addStudent(); // 학생 추가
                        break;
                    case 2:
                        AdminDAO.removeStudent(); // 학생 삭제
                        break;
                    case 3:
                        viewStudents(); // 학생 목록 보기
                        break;
                    case 4:
                        System.out.println("학생 정보 관리를 종료합니다.");
                        return; // 종료
                    default:
                        System.out.println("잘못된 선택입니다. 다시 선택하세요."); // 잘못된 선택
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력하세요."); // 예외 처리
            }
        }
    }

    private static void viewStudents() {
        System.out.println("<< 학생 목록 >>");
        List<Map<String, String>> students = AdminDAO.getStudents();
        if (students.isEmpty()) {
            System.out.println("등록된 학생이 없습니다.");
        } else {
            for (int i = 0; i < students.size(); i++) {
                Map<String, String> student = students.get(i);
                System.out.println((i + 1) + ". 이름: " + student.get("student_name"));
                System.out.println("   학번: " + student.get("student_username"));
            }
        }
    }

    public static void manageScheduleAndNotice() {
        while (true) {
            int choice = -1;

            try {
                System.out.println("1. 공지사항 추가");
                System.out.println("2. 공지사항 삭제");
                System.out.println("3. 공지사항 목록 보기");
                System.out.println("4. 뒤로 가기");
                System.out.print("선택: ");

                choice = Integer.parseInt(scanner.nextLine()); // 예외 발생 가능

                switch (choice) {
                    case 1:
                        addNotice(); // 공지사항 추가
                        break;
                    case 2:
                        int noticeIndex = selectNoticeIndexToDelete(); // 삭제할 공지사항 선택
                        if (noticeIndex != -1) {
                            AdminDAO.removeNotice(noticeIndex); // 공지사항 삭제
                        }
                        break;
                    case 3:
                        viewNotices(); // 공지사항 목록 보기
                        break;
                    case 4:
                        System.out.println("일정 및 공지사항 관리를 종료합니다.");
                        return; // 종료
                    default:
                        System.out.println("잘못된 선택입니다. 다시 선택하세요."); // 잘못된 선택
                }
            } catch (NumberFormatException e) {
                System.out.println("잘못된 입력입니다. 숫자를 입력하세요."); // 예외 처리
            }
        }
    }

    private static int selectNoticeIndexToDelete() {
        viewNotices(); // 공지사항 목록 보기
        System.out.print("삭제할 공지사항의 번호를 선택하세요: ");

        int noticeIndex = -1;
        try {
            noticeIndex = Integer.parseInt(scanner.nextLine()); // 예외 발생 가능
        } catch (NumberFormatException e) {
            System.out.println("올바른 번호를 선택하세요."); // 잘못된 입력
        }

        return noticeIndex;
    }

    private static void addNotice() {
        System.out.print("추가할 공지사항 제목을 입력하세요: ");
        String title = scanner.nextLine(); // 공지사항 제목 입력
        System.out.print("추가할 공지사항 내용을 입력하세요: ");
        String content = scanner.nextLine(); // 공지사항 내용 입력
        AdminDAO.addNotice(title, content); // 공지사항 추가
    }

    private static void viewNotices() {
        System.out.println("<< 공지사항 목록 >>");
        List<Map<String, String>> notices = AdminDAO.getNotices();
        if (notices.isEmpty()) {
            System.out.println("등록된 공지사항이 없습니다.");
        } else {
            for (int i = 0; i < notices.size(); i++) {
                Map<String, String> notice = notices.get(i);
                System.out.println((i + 1) + ". 제목: " + notice.get("title"));
                System.out.println("   내용: " + notice.get("content"));
            }
        }
    }
}