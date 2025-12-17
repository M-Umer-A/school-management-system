package com.school.model;

import java.util.Date;

public class Grade {
    private int studentId;
    private String subjectCode;
    private int teacherId;
    private String grade;
    private double marks;
    private String term;
    private Date date;

    
    public Grade(int studentId, String subjectCode, int teacherId,
            String grade, double marks, String term, String date2) {
        this.studentId = studentId;
        this.subjectCode = subjectCode;
        this.teacherId = teacherId;
        this.grade = grade;
        this.marks = marks;
        this.term = term;
        this.date = date;
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

    public double getMarks() {
        return marks;
    }

    public void setMarks(double marks) {
        this.marks = marks;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
