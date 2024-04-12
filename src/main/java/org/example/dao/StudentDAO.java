package org.example.dao;

import java.util.HashMap;
import java.util.Map;

public class StudentDAO {
    // 예시로 사용할 데이터베이스 대신에 강의 정보를 저장할 맵을 사용합니다.
    private Map<String, String> lectureDatabase = new HashMap<>();

    // 강의 정보를 가져오는 메서드
    public Map<String, String> getLectures() {
        // 실제 데이터베이스에서 강의 정보를 가져오는 로직을 구현합니다.
        // 여기서는 간단히 예시로 맵을 반환합니다.
        return lectureDatabase;
    }

    // 학생의 시간표를 조회하는 메서드
    public void viewTimetable() {
        // 실제 데이터베이스에서 학생의 시간표 정보를 가져오는 로직을 구현합니다.
        // 여기서는 간단히 예시로 출력만 합니다.
        System.out.println("학생의 시간표를 조회합니다.");
    }

    // 학생의 과목과 성적을 조회하는 메서드
    public void viewSubjectsAndGrades() {
        // 실제 데이터베이스에서 학생의 과목과 성적 정보를 가져오는 로직을 구현합니다.
        // 여기서는 간단히 예시로 출력만 합니다.
        System.out.println("학생의 과목과 성적을 조회합니다.");
    }
}