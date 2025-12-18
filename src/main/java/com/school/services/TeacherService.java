package com.school.services;

import com.school.database.DBConnection;
import com.school.exceptions.DuplicateRecordException;
import com.school.exceptions.RecordNotFoundException;
import com.school.model.Teacher;
import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;

import java.util.List;
import java.util.Scanner;

public class TeacherService {

    private final Datastore ds = DBConnection.getDatastore();
    private final Scanner sc = new Scanner(System.in);

    public void addTeacher(Teacher t) {
        System.out.print("Teacher ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        // Check for duplicate
        Teacher existing = ds.find(Teacher.class)
                .filter(Filters.eq("teacherId", id))
                .first();

        if (existing != null) {
            throw new DuplicateRecordException("Teacher ID already exists!");
        }

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Department: ");
        String department = sc.nextLine();

        System.out.print("Subject Code (e.g., MATH101): ");
        String subjectCode = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Phone: ");
        String phone = sc.nextLine();

        Teacher teacher = new Teacher(id, name, department, subjectCode, email, phone);

        try {
            ds.save(teacher);
            System.out.println("✅ Teacher added successfully!");
        } catch (Exception e) {
            System.err.println("❌ Error adding teacher: " + e.getMessage());
        }
    }

    public void viewAllTeachers() {
        List<Teacher> teachers = ds.find(Teacher.class).iterator().toList();

        if (teachers.isEmpty()) {
            System.out.println("⚠ No teachers found!");
            return;
        }

        System.out.println("\n========== ALL TEACHERS ==========");
        teachers.forEach(System.out::println);
        System.out.println("==================================\n");
    }

    public void viewTeacherById() {
        System.out.print("Enter Teacher ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        Teacher teacher = ds.find(Teacher.class)
                .filter(Filters.eq("teacherId", id))
                .first();

        if (teacher == null) {
            throw new RecordNotFoundException("Teacher not found!");
        }

        System.out.println("\n" + teacher);
    }

    public void updateTeacher() {
        System.out.print("Teacher ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        Teacher teacher = ds.find(Teacher.class)
                .filter(Filters.eq("teacherId", id))
                .first();

        if (teacher == null) {
            throw new RecordNotFoundException("Teacher not found!");
        }

        System.out.println("What do you want to update?");
        System.out.println("1. Name");
        System.out.println("2. Department");
        System.out.println("3. Subject");
        System.out.println("4. Email");
        System.out.println("5. Phone");
        System.out.print("Choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1 -> {
                System.out.print("New Name: ");
                String name = sc.nextLine();
                ds.find(Teacher.class)
                        .filter(Filters.eq("teacherId", id))
                        .update(dev.morphia.query.updates.UpdateOperators.set("name", name))
                        .execute();
            }
            case 2 -> {
                System.out.print("New Department: ");
                String dept = sc.nextLine();
                ds.find(Teacher.class)
                        .filter(Filters.eq("teacherId", id))
                        .update(dev.morphia.query.updates.UpdateOperators.set("department", dept))
                        .execute();
            }
            case 3 -> {
                System.out.print("New Subject Code: ");
                String subject = sc.nextLine();
                ds.find(Teacher.class)
                        .filter(Filters.eq("teacherId", id))
                        .update(dev.morphia.query.updates.UpdateOperators.set("subjectCode", subject))
                        .execute();
            }
            case 4 -> {
                System.out.print("New Email: ");
                String email = sc.nextLine();
                ds.find(Teacher.class)
                        .filter(Filters.eq("teacherId", id))
                        .update(dev.morphia.query.updates.UpdateOperators.set("email", email))
                        .execute();
            }
            case 5 -> {
                System.out.print("New Phone: ");
                String phone = sc.nextLine();
                ds.find(Teacher.class)
                        .filter(Filters.eq("teacherId", id))
                        .update(dev.morphia.query.updates.UpdateOperators.set("phone", phone))
                        .execute();
            }
            default -> {
                System.out.println("Invalid choice!");
                return;
            }
        }

        System.out.println("✅ Teacher updated successfully!");
    }

    public void deleteTeacher() {
        System.out.print("Teacher ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        Teacher teacher = ds.find(Teacher.class)
                .filter(Filters.eq("teacherId", id))
                .first();

        if (teacher == null) {
            throw new RecordNotFoundException("Teacher not found!");
        }

        System.out.print("Are you sure you want to delete " + teacher.getName() + "? (yes/no): ");
        String confirm = sc.nextLine();

        if (confirm.equalsIgnoreCase("yes")) {
            ds.find(Teacher.class)
                    .filter(Filters.eq("teacherId", id))
                    .delete();
            System.out.println("✅ Teacher deleted successfully!");
        } else {
            System.out.println("❌ Deletion cancelled!");
        }
    }

    public Teacher getTeacherById(int id) {
        return ds.find(Teacher.class)
                .filter(Filters.eq("teacherId", id))
                .first();
    }
}