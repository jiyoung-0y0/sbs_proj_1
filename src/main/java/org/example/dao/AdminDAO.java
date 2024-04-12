package org.example.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AdminDAO {
    private static final Scanner scanner = new Scanner(System.in);
    private static List<String> studentInfo = new ArrayList<>();
    private static List<String> scheduleAndNotice = new ArrayList<>();

    public static void addStudent() {
        // 학생 추가 기능을 구현
    }

    public static void removeStudent() {
        // 학생 삭제 기능을 구현
    }

    public static void addSchedule() {
        System.out.println("일정을 추가합니다.");
        // 일정 추가 기능을 구현
    }

    public static void addNotice() {
        System.out.println("공지사항을 추가합니다.");
        // 공지사항 추가 기능을 구현
    }

    public static void removeSchedule() {
        System.out.println("일정을 삭제합니다.");
        // 일정 삭제 기능을 구현
    }

    public static void removeNotice() {
        System.out.println("공지사항을 삭제합니다.");
        // 공지사항 삭제 기능을 구현
    }
}
