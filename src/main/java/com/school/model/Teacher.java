package com.school.model;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;

@Entity("teachers")
public class Teacher {

    @Id
    private ObjectId id;
    private int teacherId;
    private String name;
    private String department;
    private String subject;
    private String subjectCode;
    private String email;
    private String phone;

    public Teacher() {
    }

    public Teacher(int teacherId, String name, String department, String subjectCode, String email, String phone) {
        this.teacherId = teacherId;
        this.name = name;
        this.department = department;
        this.email = email;
        this.phone = phone;
        this.subjectCode = subjectCode;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public String getName() {
        return name;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return teacherId + " | " + name + " | Dept: " + department + " | Subject: " + subject;
    }
}
