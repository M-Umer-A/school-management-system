package com.school.model;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;

@Entity("attendance")
public class Attendance {

    @Id
    private ObjectId id;
    private int studentId;
    private String subjectCode;
    private int teacherId;
    private String date;
    private boolean present;
    private String remarks;

    public Attendance() {
    }

    public Attendance(int studentId, String subjectCode, int teacherId, String date, boolean present, String remarks) {
        this.studentId = studentId;
        this.subjectCode = subjectCode;
        this.teacherId = teacherId;
        this.date = date;
        this.present = present;
        this.remarks = remarks;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String toString() {
        return "Student ID: " + studentId + " | Subject: " + subjectCode + " | Date: " + date +
                " | " + (present ? "Present" : "Absent") +
                (remarks != null && !remarks.isEmpty() ? " | Remarks: " + remarks : "");
    }
}