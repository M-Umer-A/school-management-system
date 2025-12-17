package com.school.model;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;

@Entity("students")
public class Student {

    @Id
    private ObjectId id;

    private int studentId;
    private String name;
    private String grade;
    private String subject;

    public Student() {
    }

    public Student(int studentId, String name, String grade) {
        this.studentId = studentId;
        this.name = name;
        this.grade = grade;
    }

    public static int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public String getGrade() {
        return grade;
    }

    public String getSubject() {
        return subject;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return studentId + " | " + name + " | Grade: " + grade + " | Subject: " + subject;
    }
}
