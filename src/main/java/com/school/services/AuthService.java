package com.school.services;

import com.school.database.DBConnection;
import com.school.model.User;
import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;

public class AuthService {

    private final Datastore ds = DBConnection.getDatastore();

    public User login(String username, String password) {
        User user = ds.find(User.class)
                .filter(Filters.eq("username", username))
                .first();

        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public void createDefaultAdmin() {
        // Check if admin already exists
        User existingAdmin = ds.find(User.class)
                .filter(Filters.eq("role", "ADMIN"))
                .first();

        if (existingAdmin == null) {
            User admin = new User("admin", "admin123", "ADMIN", null);
            ds.save(admin);
            System.out.println("✅ Default admin created (username: admin, password: admin123)");
        }
    }

    public void registerUser(String username, String password, String role, Integer referenceId) {
        User existingUser = ds.find(User.class)
                .filter(Filters.eq("username", username))
                .first();

        if (existingUser != null) {
            throw new RuntimeException("Username already exists!");
        }

        User newUser = new User(username, password, role, referenceId);
        ds.save(newUser);
        System.out.println("✅ User registered successfully!");
    }

    public void changePassword(String username, String oldPassword, String newPassword) {
        User user = ds.find(User.class)
                .filter(Filters.eq("username", username))
                .first();

        if (user == null || !user.getPassword().equals(oldPassword)) {
            throw new RuntimeException("Invalid username or password!");
        }

        ds.find(User.class)
                .filter(Filters.eq("username", username))
                .update(dev.morphia.query.updates.UpdateOperators.set("password", newPassword))
                .execute();

        System.out.println("✅ Password changed successfully!");
    }
}