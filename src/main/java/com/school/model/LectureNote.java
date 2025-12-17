package com.school.model;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;

@Entity("lecture_notes")
public class LectureNote {

    @Id
    private ObjectId id;
    private String subjectCode;
    private int teacherId;
    private String title;
    private String content;
    private String date;
    private String grade; 

    public LectureNote() {
    }

    public LectureNote(String subjectCode, int teacherId, String title, String content, String date, String grade) {
        this.subjectCode = subjectCode;
        this.teacherId = teacherId;
        this.title = title;
        this.content = content;
        this.date = date;
        this.grade = grade;
    }

    public ObjectId getId() {
        return id;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "Subject: " + subjectCode + " | " + title + " | " + date + " | Grade: " + grade;
    }
}