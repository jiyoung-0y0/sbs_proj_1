package org.example.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ProfessorController {
    private static Map<String, String> lectures = new HashMap<>();
    private static Map<String, Map<String, String>> gradesMap = new HashMap<>();
    private static final String[] GRADES = {"A+", "A0", "B+", "B0", "C+", "C0", "D+", "D0", "F"};
    private static Map<String, String> studentCredentials; // 학생 정보를 가져올 맵

    public static void setStudentCredentials(Map<String, String> credentials) {
        studentCredentials = credentials;
    }

    public static void showPage(String username) {
        System.out.println("교수 페이지로 이동합니다. 환영합니다, " + username + "님!");
        // 교수 페이지의 기능을 선택할 수 있는 메뉴 표시
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("1. 강의 등록");
            System.out.println("2. 강의 삭제");
            System.out.println("3. 성적 입력/수정/삭제");
            System.out.println("4. 자신의 과목 학생들 성적 출력");
            System.out.println("5. 관리자가 작성한 공지 확인");
            System.out.println("6. 로그아웃");
            System.out.print("선택: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // 엔터키 소비

            switch (choice) {
                case 1:
                    registerLecture();
                    break;
                case 2:
                    removeLecture();
                    break;
                case 3:
                    manageGrades();
                    break;
                case 4:
                    viewStudentGrades();
                    break;
                case 5:
                    // 관리자가 작성한 공지 확인 기능을 구현하세요
                    System.out.println("관리자가 작성한 공지를 확인합니다.");
                    break;
                case 6:
                    System.out.println("로그아웃합니다.");
                    break;
                default:
                    System.out.println("잘못된 선택입니다. 다시 선택하세요.");
            }
        } while (choice != 6);
    }

    // 강의 등록 기능
    private static void registerLecture() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("추가할 강의 이름을 입력하세요: ");
        String lectureName = scanner.nextLine();
        System.out.print("추가할 강의 코드를 입력하세요: ");
        String lectureCode = scanner.nextLine();
        lectures.put(lectureName, lectureCode);
        gradesMap.put(lectureName, new HashMap<>());
        System.out.println("강의 \"" + lectureName + "\"을(를) 등록했습니다.");
    }

    // 강의 삭제 기능
    private static void removeLecture() {
        Scanner scanner = new Scanner(System.in);
        if (lectures.isEmpty()) {
            System.out.println("삭제할 강의가 없습니다.");
            return;
        }
        System.out.println("삭제할 강의 목록:");
        for (String lectureName : lectures.keySet()) {
            System.out.println("- " + lectureName);
        }
        System.out.print("삭제할 강의 이름을 입력하세요: ");
        String lectureName = scanner.nextLine();
        if (!lectures.containsKey(lectureName)) {
            System.out.println("해당 강의가 존재하지 않습니다.");
            return;
        }
        lectures.remove(lectureName);
        gradesMap.remove(lectureName);
        System.out.println("강의 \"" + lectureName + "\"을(를) 삭제했습니다.");
    }

    // 성적 입력/수정/삭제 기능
    private static void manageGrades() {
        Scanner scanner = new Scanner(System.in);
        if (lectures.isEmpty()) {
            System.out.println("등록된 강의가 없습니다. 강의를 먼저 등록하세요.");
            return;
        }
        System.out.println("등록된 강의 목록:");
        for (String lectureName : lectures.keySet()) {
            System.out.println("- " + lectureName);
        }
        System.out.print("성적을 입력할 강의 이름을 선택하세요: ");
        String selectedLecture = scanner.nextLine();
        if (!lectures.containsKey(selectedLecture)) {
            System.out.println("해당 강의가 존재하지 않습니다.");
            return;
        }
        System.out.print("성적을 입력할 학생의 학번을 입력하세요: ");
        String studentId = scanner.nextLine();
        if (!studentCredentials.containsKey(studentId)) {
            System.out.println("해당 학생의 학번이 존재하지 않습니다.");
            return;
        }
        System.out.print("성적을 입력하세요 (A+, A0, B+, B0, C+, C0, D+, D0, F): ");
        String grade = scanner.nextLine();
        if (!isValidGrade(grade)) {
            System.out.println("유효하지 않은 성적입니다.");
            return;
        }
        gradesMap.get(selectedLecture).put(studentId, grade);
        System.out.println("성적이 성공적으로 입력되었습니다.");
    }

    // 성적 유효성 검사
    private static boolean isValidGrade(String grade) {
        for (String validGrade : GRADES) {
            if (grade.equals(validGrade)) {
                return true;
            }
        }
        return false;
    }

    // 자신의 과목 학생들 성적 출력 기능
    private static void viewStudentGrades() {
        Scanner scanner = new Scanner(System.in);
        if (lectures.isEmpty()) {
            System.out.println("등록된 강의가 없습니다.");
            return;
        }
        System.out.println("등록된 강의 목록:");
        for (String lectureName : lectures.keySet()) {
            System.out.println("- " + lectureName);
        }
        System.out.print("성적을 확인할 강의 이름을 선택하세요: ");
        String selectedLecture = scanner.nextLine();
        if (!lectures.containsKey(selectedLecture)) {
            System.out.println("해당 강의가 존재하지 않습니다.");
            return;
        }
        Map<String, String> grades = gradesMap.get(selectedLecture);
        if (grades.isEmpty()) {
            System.out.println("등록된 학생 성적이 없습니다.");
            return;
        }
        System.out.println(selectedLecture + " 과목의 성적:");
        for (Map.Entry<String, String> entry : grades.entrySet()) {
            System.out.println("학번: " + entry.getKey() + ", 성적: " + entry.getValue());
        }
    }
}
