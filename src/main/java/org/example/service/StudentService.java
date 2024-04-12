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

        // 여기에 강의를 신청하는 기능을 구현합니다.
        Scanner scanner = new Scanner(System.in);
        System.out.println("신청할 강의를 선택하세요:");
        for (String lecture : lectures.keySet()) {
            System.out.println(lecture);
        }
        System.out.print("강의 이름을 입력하세요: ");
        String selectedLecture = scanner.nextLine();

        if (lectures.containsKey(selectedLecture)) {
            System.out.println(selectedLecture + " 강의를 신청했습니다.");
        } else {
            System.out.println("해당 강의가 존재하지 않습니다.");
        }
    }

    public void viewTimetable() {
        // 여기에 시간표 확인 기능을 구현합니다.
        System.out.println("시간표를 확인합니다.");
        studentDAO.viewTimetable();
    }

    public void viewSubjectsAndGrades() {
        // 여기에 과목과 성적 확인 기능을 구현합니다.
        System.out.println("과목과 성적을 확인합니다.");
        studentDAO.viewSubjectsAndGrades();
    }

    public void setLectures(Map<String, String> professorLectures) {
        this.lectures = professorLectures;
    }
}