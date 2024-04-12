package org.example.model;

public class Student {
    private String id;
    private String password;

    public Student(String id, String password) {
        this.id = id;
        this.password = password;
    }

    // getter 및 setter 생략

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
