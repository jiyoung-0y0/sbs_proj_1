package org.example.service;

import org.example.dao.AdminDAO;

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
            choice = scanner.nextInt();
            scanner.nextLine();

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
            System.out.println("1. 일정 추가");
            System.out.println("2. 공지사항 추가");
            System.out.println("3. 일정 삭제");
            System.out.println("4. 공지사항 삭제");
            System.out.println("5. 뒤로 가기");
            System.out.print("선택: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    AdminDAO.addSchedule();
                    break;
                case 2:
                    AdminDAO.addNotice();
                    break;
                case 3:
                    AdminDAO.removeSchedule();
                    break;
                case 4:
                    AdminDAO.removeNotice();
                    break;
                case 5:
                    System.out.println("일정 및 공지사항 관리를 종료합니다.");
                    return;
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택하세요.");
            }
        } while (true);
    }
}
