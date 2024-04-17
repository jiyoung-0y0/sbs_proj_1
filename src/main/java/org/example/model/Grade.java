package org.example.model;

public class Grade {
    private int gradeId;
    private int lectureId;
    private int studentId;
    private double grade;

    public Grade(int gradeId, int lectureId, int studentId, double grade) {
        this.gradeId = gradeId;
        this.lectureId = lectureId;
        this.studentId = studentId;
        this.grade = grade;
    }

    public int getGradeId() {
        return gradeId;
    }

    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public int getLectureId() {
        return lectureId;
    }

    public void setLectureId(int lectureId) {
        this.lectureId = lectureId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }
}
