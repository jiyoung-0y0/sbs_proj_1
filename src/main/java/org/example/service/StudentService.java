package org.example.service;

import org.example.dao.StudentDAO;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StudentService {
    private Map<String, String> lectures = new HashMap<>();
    private StudentDAO studentDAO = new StudentDAO();

    public void registerLecture() {
        if (lectures.isEmpty()) {
            System.out.println("교수가 아직 강의를 등록하지 않았습니다. 신청할 수 있는 강의가 없습니다.");
            return;
        }
        // 강의 신청 기능을 구현하세요
        System.out.println("강의를 신청합니다.");
    }

    public void viewTimetable() {
        // 시간표 확인 기능을 구현하세요
        System.out.println("시간표를 확인합니다.");
    }

    public void viewSubjectsAndGrades() {
        // 과목과 성적 확인 기능을 구현하세요
        System.out.println("과목과 성적을 확인합니다.");
    }

    public void setLectures(Map<String, String> professorLectures) {
        this.lectures = professorLectures;
    }
}
