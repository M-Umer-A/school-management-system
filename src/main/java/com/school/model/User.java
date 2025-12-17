package com.school.model;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Indexed;
import org.bson.types.ObjectId;

@Entity("users")
public class User {

    @Id
    private ObjectId id;

    @Indexed(options = @dev.morphia.annotations.IndexOptions(unique = true))
    private String username;

    private String password;
    private String role; // ADMIN, TEACHER, STUDENT
    private Integer referenceId; // Links to studentId or teacherId

    public User() {
    }

    public User(String username, String password, String role, Integer referenceId) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.referenceId = referenceId;
    }

    public ObjectId getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Integer referenceId) {
        this.referenceId = referenceId;
    }
}