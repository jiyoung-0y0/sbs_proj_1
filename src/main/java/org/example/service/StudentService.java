package org.example.service;

import org.example.dao.StudentDAO;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StudentService {
    private Map<String, String> lectures = new HashMap<>();
    private StudentDAO studentDAO = new StudentDAO();
    private int studentId; // 학생의 ID를 저장하기 위한 변수

    // 학생의 ID를 설정하는 메서드
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

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
            // 여기서 studentId와 lectureId를 적절히 지정해야 합니다.
            String lectureId = lectures.get(selectedLecture); // 강의 이름에 해당하는 강의 ID를 가져옵니다.
            studentDAO.registerCourse(studentId, lectureId); // 학생이 해당 강의를 신청합니다.
        } else {
            System.out.println("해당 강의가 존재하지 않습니다.");
        }
    }

    public void viewTimetable() {
        // 여기에 시간표 확인 기능을 구현합니다.
        System.out.println("시간표를 확인합니다.");
        studentDAO.viewTimetable(studentId); // 학생의 ID를 전달하여 해당 학생의 시간표를 조회합니다.
    }

    public void viewSubjectsAndGrades() {
        // 여기에 과목과 성적 확인 기능을 구현합니다.
        System.out.println("과목과 성적을 확인합니다.");
        studentDAO.viewSubjectsAndGrades(studentId); // 학생의 ID를 전달하여 해당 학생의 과목과 성적을 조회합니다.
    }

    public void setLectures(Map<String, String> professorLectures) {
        this.lectures = professorLectures;
    }
}
