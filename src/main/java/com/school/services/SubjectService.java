package com.school.services;

import com.school.database.DBConnection;
import com.school.exceptions.DuplicateRecordException;
import com.school.exceptions.RecordNotFoundException;
import com.school.model.Subject;
import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;

import java.util.List;
import java.util.Scanner;

public class SubjectService {

    private final Datastore ds = DBConnection.getDatastore();
    private final Scanner sc = new Scanner(System.in);

    public void addSubject() {
        System.out.print("Subject Code (e.g., MATH101): ");
        String code = sc.nextLine();

        // Check for duplicate
        Subject existing = ds.find(Subject.class)
                .filter(Filters.eq("code", code))
                .first();

        if (existing != null) {
            throw new DuplicateRecordException("Subject code already exists!");
        }

        System.out.print("Subject Name: ");
        String name = sc.nextLine();

        System.out.print("Description: ");
        String description = sc.nextLine();

        Subject subject = new Subject(code, name);

        try {
            ds.save(subject);
            System.out.println("✅ Subject added successfully!");
        } catch (Exception e) {
            System.err.println("❌ Error adding subject: " + e.getMessage());
        }
    }

    public void viewAllSubjects() {
        List<Subject> subjects = ds.find(Subject.class).iterator().toList();

        if (subjects.isEmpty()) {
            System.out.println("⚠ No subjects found!");
            return;
        }

        System.out.println("\n========== ALL SUBJECTS ==========");
        subjects.forEach(System.out::println);
        System.out.println("==================================\n");
    }

    public void viewSubjectByCode() {
        System.out.print("Enter Subject Code: ");
        String code = sc.nextLine();

        Subject subject = ds.find(Subject.class)
                .filter(Filters.eq("code", code))
                .first();

        if (subject == null) {
            throw new RecordNotFoundException("Subject not found!");
        }

        System.out.println("\n" + subject);
    }

    public void updateSubject() {
        System.out.print("Subject Code: ");
        String code = sc.nextLine();

        Subject subject = ds.find(Subject.class)
                .filter(Filters.eq("code", code))
                .first();

        if (subject == null) {
            throw new RecordNotFoundException("Subject not found!");
        }

        System.out.println("What do you want to update?");
        System.out.println("1. Name");
        System.out.println("2. Description");
        System.out.print("Choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1 -> {
                System.out.print("New Name: ");
                String name = sc.nextLine();
                ds.find(Subject.class)
                        .filter(Filters.eq("code", code))
                        .update(dev.morphia.query.updates.UpdateOperators.set("name", name))
                        .execute();
            }
            case 2 -> {
                System.out.print("New Description: ");
                String description = sc.nextLine();
                ds.find(Subject.class)
                        .filter(Filters.eq("code", code))
                        .update(dev.morphia.query.updates.UpdateOperators.set("description", description))
                        .execute();
            }
            default -> {
                System.out.println("Invalid choice!");
                return;
            }
        }

        System.out.println("✅ Subject updated successfully!");
    }

    public void deleteSubject() {
        System.out.print("Subject Code: ");
        String code = sc.nextLine();

        Subject subject = ds.find(Subject.class)
                .filter(Filters.eq("code", code))
                .first();

        if (subject == null) {
            throw new RecordNotFoundException("Subject not found!");
        }

        System.out.print("Are you sure you want to delete " + subject.getName() + "? (yes/no): ");
        String confirm = sc.nextLine();

        if (confirm.equalsIgnoreCase("yes")) {
            ds.find(Subject.class)
                    .filter(Filters.eq("code", code))
                    .delete();
            System.out.println("✅ Subject deleted successfully!");
        } else {
            System.out.println("❌ Deletion cancelled!");
        }
    }
}