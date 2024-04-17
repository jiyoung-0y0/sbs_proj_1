package org.example.model;

public class Lecture {
    private int lectureId;
    private String lectureName;
    private int professorId;
    private int capacity;
    private int remainingCapacity;

    public Lecture(int lectureId, String lectureName, int professorId, int capacity, int remainingCapacity) {
        this.lectureId = lectureId;
        this.lectureName = lectureName;
        this.professorId = professorId;
        this.capacity = capacity;
        this.remainingCapacity = remainingCapacity;
    }

    public int getLectureId() {
        return lectureId;
    }

    public void setLectureId(int lectureId) {
        this.lectureId = lectureId;
    }

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public int getProfessorId() {
        return professorId;
    }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getRemainingCapacity() {
        return remainingCapacity;
    }

    public void setRemainingCapacity(int remainingCapacity) {
        this.remainingCapacity = remainingCapacity;
    }
}
