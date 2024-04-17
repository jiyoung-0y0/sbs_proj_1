package org.example.model;

public class Professor {
    private int professorId;
    private String professorName;
    private String professorUsername;
    private String professorPassword;

    public Professor(int professorId, String professorName, String professorUsername, String professorPassword) {
        this.professorId = professorId;
        this.professorName = professorName;
        this.professorUsername = professorUsername;
        this.professorPassword = professorPassword;
    }

    public int getProfessorId() {
        return professorId;
    }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public String getProfessorUsername() {
        return professorUsername;
    }

    public void setProfessorUsername(String professorUsername) {
        this.professorUsername = professorUsername;
    }

    public String getProfessorPassword() {
        return professorPassword;
    }

    public void setProfessorPassword(String professorPassword) {
        this.professorPassword = professorPassword;
    }
}
