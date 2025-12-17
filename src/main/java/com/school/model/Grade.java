package com.school.model;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;

@Entity("grades")
public class Grade {

    @Id
    private ObjectId id;
    private int studentId;
    private String subjectCode;
    private int teacherId;
    private String grade; // A+, A, B+, B, etc.
    private int marks; // 0-100
    private String term; // "Mid-Term", "Final", "Quiz-1", etc.
    private String date;

    public Grade() {
    }

    public Grade(int studentId, String subjectCode, int teacherId, String grade, int marks, String term, String date) {
        this.studentId = studentId;
        this.subjectCode = subjectCode;
        this.teacherId = teacherId;
        this.grade = grade;
        this.marks = marks;
        this.term = term;
        this.date = date;
    }

    public ObjectId getId() {
        return id;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Student ID: " + studentId + " | Subject: " + subjectCode +
                " | " + term + " | Marks: " + marks + " | Grade: " + grade;
    }
}