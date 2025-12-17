package com.school.model;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Indexed;
import org.bson.types.ObjectId;

@Entity("teachers")
public class Teacher {

    @Id
    private ObjectId id;

    @Indexed(options = @dev.morphia.annotations.IndexOptions(unique = true))
    private int teacherId;

    private String name;
    private String department;
    private String subjectCode;
    private String email;
    private String phone;

    public Teacher() {
    }

    public Teacher(int teacherId, String name, String department, String subjectCode, String email, String phone) {
        this.teacherId = teacherId;
        this.name = name;
        this.department = department;
        this.subjectCode = subjectCode;
        this.email = email;
        this.phone = phone;
    }

    public ObjectId getId() {
        return id;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
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
        return "ID: " + teacherId + " | " + name + " | Dept: " + department +
                " | Subject: " + subjectCode + " | Email: " + email;
    }
}