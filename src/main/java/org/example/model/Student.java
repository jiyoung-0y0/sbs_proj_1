package org.example.model;

public class Student {
    private int studentId;
    private String studentName;
    private String studentUsername;
    private String studentPassword;
    private int totalCoursesAllowed;

    public Student(int studentId, String studentName, String studentUsername, String studentPassword, int totalCoursesAllowed) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentUsername = studentUsername;
        this.studentPassword = studentPassword;
        this.totalCoursesAllowed = totalCoursesAllowed;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentUsername() {
        return studentUsername;
    }

    public void setStudentUsername(String studentUsername) {
        this.studentUsername = studentUsername;
    }

    public String getStudentPassword() {
        return studentPassword;
    }

    public void setStudentPassword(String studentPassword) {
        this.studentPassword = studentPassword;
    }

    public int getTotalCoursesAllowed() {
        return totalCoursesAllowed;
    }

    public void setTotalCoursesAllowed(int totalCoursesAllowed) {
        this.totalCoursesAllowed = totalCoursesAllowed;
    }
}
