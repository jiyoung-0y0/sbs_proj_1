package org.example.model;

public class CourseRegistration {
    private int registrationId;
    private int studentId;
    private int lectureId;

    public CourseRegistration(int registrationId, int studentId, int lectureId) {
        this.registrationId = registrationId;
        this.studentId = studentId;
        this.lectureId = lectureId;
    }

    public int getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(int registrationId) {
        this.registrationId = registrationId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getLectureId() {
        return lectureId;
    }

    public void setLectureId(int lectureId) {
        this.lectureId = lectureId;
    }
}
