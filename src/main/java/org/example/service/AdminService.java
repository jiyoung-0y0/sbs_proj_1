package org.example.service;

import org.example.dao.AdminDAO;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class AdminService {
    private static final Scanner scanner = new Scanner(System.in);

    public static void manageStudentInfo() {
        int choice;
        do {
            System.out.println("1. 학생 추가");
            System.out.println("2. 학생 삭제");
            System.out.println("3. 뒤로 가기");
            System.out.print("선택: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    AdminDAO.addStudent();
                    break;
                case 2:
                    AdminDAO.removeStudent();
                    break;
                case 3:
                    System.out.println("학생 정보 관리를 종료합니다.");
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택하세요.");
            }
        } while (true);
    }

    public static void manageScheduleAndNotice() {
        int choice;
        do {
            System.out.println("1. 공지사항 추가");
            System.out.println("2. 공지사항 삭제");
            System.out.println("3. 공지사항 확인");
            System.out.println("4. 뒤로 가기");
            System.out.print("선택: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addNotice();
                    break;
                case 2:
                    removeNotice();
                    break;
                case 3:
                    viewNotices();
                    break;
                case 4:
                    System.out.println("일정 및 공지사항 관리를 종료합니다.");
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택하세요.");
            }
        } while (true);
    }

    private static void addNotice() {
        System.out.print("추가할 공지사항 제목을 입력하세요: ");
        String title = scanner.nextLine();
        System.out.print("추가할 공지사항 내용을 입력하세요: ");
        String content = scanner.nextLine();
        AdminDAO.addNotice(title, content);
    }

    private static void removeNotice() {
        System.out.print("삭제할 공지사항 번호를 입력하세요: ");
        int noticeNumber = Integer.parseInt(scanner.nextLine());
        AdminDAO.removeNotice(noticeNumber);
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
