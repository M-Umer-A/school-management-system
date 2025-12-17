package com.school.model;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Indexed;
import org.bson.types.ObjectId;

@Entity("students")
public class Student {

    @Id
    private ObjectId id;

    @Indexed(options = @dev.morphia.annotations.IndexOptions(unique = true))
    private int studentId;

    private String name;
    private String grade; // Class/Grade level (e.g., "Grade 10", "Grade 11")
    private String section; // Section (e.g., "A", "B")
    private String email;
    private String phone;

    public Student() {
    }

    public Student(int studentId, String name, String grade, String section, String email, String phone) {
        this.studentId = studentId;
        this.name = name;
        this.grade = grade;
        this.section = section;
        this.email = email;
        this.phone = phone;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "ID: " + studentId + " | " + name + " | Grade: " + grade + "-" + section + " | Email: " + email;
    }
}