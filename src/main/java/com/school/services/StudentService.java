package com.school.services;

import com.school.database.DBConnection;
import com.school.exceptions.DuplicateRecordException;
import com.school.exceptions.RecordNotFoundException;
import com.school.model.Student;
import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;

import java.util.List;
import java.util.Scanner;

public class StudentService {

    private final Datastore ds = DBConnection.getDatastore();
    private final Scanner sc = new Scanner(System.in);

    public void addStudent() {
        System.out.print("Student ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        // Check for duplicate
        Student existing = ds.find(Student.class)
                .filter(Filters.eq("studentId", id))
                .first();

        if (existing != null) {
            throw new DuplicateRecordException("Student ID already exists!");
        }

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Grade (e.g., Grade 10): ");
        String grade = sc.nextLine();

        System.out.print("Section (e.g., A, B): ");
        String section = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Phone: ");
        String phone = sc.nextLine();

        Student student = new Student(id, name, grade, section, email, phone);

        try {
            ds.save(student);
            System.out.println("✅ Student added successfully!");
        } catch (Exception e) {
            System.err.println("❌ Error adding student: " + e.getMessage());
        }
    }

    public void viewAllStudents() {
        List<Student> students = ds.find(Student.class).iterator().toList();

        if (students.isEmpty()) {
            System.out.println("⚠ No students found!");
            return;
        }

        System.out.println("\n========== ALL STUDENTS ==========");
        students.forEach(System.out::println);
        System.out.println("==================================\n");
    }

    public void viewStudentById() {
        System.out.print("Enter Student ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        Student student = ds.find(Student.class)
                .filter(Filters.eq("studentId", id))
                .first();

        if (student == null) {
            throw new RecordNotFoundException("Student not found!");
        }

        System.out.println("\n" + student);
    }

    public void updateStudent() {
        System.out.print("Student ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        Student student = ds.find(Student.class)
                .filter(Filters.eq("studentId", id))
                .first();

        if (student == null) {
            throw new RecordNotFoundException("Student not found!");
        }

        System.out.println("What do you want to update?");
        System.out.println("1. Name");
        System.out.println("2. Grade/Section");
        System.out.println("3. Email");
        System.out.println("4. Phone");
        System.out.print("Choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1 -> {
                System.out.print("New Name: ");
                String name = sc.nextLine();
                ds.find(Student.class)
                        .filter(Filters.eq("studentId", id))
                        .update(dev.morphia.query.updates.UpdateOperators.set("name", name))
                        .execute();
            }
            case 2 -> {
                System.out.print("New Grade: ");
                String grade = sc.nextLine();
                System.out.print("New Section: ");
                String section = sc.nextLine();
                ds.find(Student.class)
                        .filter(Filters.eq("studentId", id))
                        .update(
                                dev.morphia.query.updates.UpdateOperators.set("grade", grade),
                                dev.morphia.query.updates.UpdateOperators.set("section", section))
                        .execute();
            }
            case 3 -> {
                System.out.print("New Email: ");
                String email = sc.nextLine();
                ds.find(Student.class)
                        .filter(Filters.eq("studentId", id))
                        .update(dev.morphia.query.updates.UpdateOperators.set("email", email))
                        .execute();
            }
            case 4 -> {
                System.out.print("New Phone: ");
                String phone = sc.nextLine();
                ds.find(Student.class)
                        .filter(Filters.eq("studentId", id))
                        .update(dev.morphia.query.updates.UpdateOperators.set("phone", phone))
                        .execute();
            }
            default -> {
                System.out.println("Invalid choice!");
                return;
            }
        }

        System.out.println("✅ Student updated successfully!");
    }

    public void deleteStudent() {
        System.out.print("Student ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        Student student = ds.find(Student.class)
                .filter(Filters.eq("studentId", id))
                .first();

        if (student == null) {
            throw new RecordNotFoundException("Student not found!");
        }

        System.out.print("Are you sure you want to delete " + student.getName() + "? (yes/no): ");
        String confirm = sc.nextLine();

        if (confirm.equalsIgnoreCase("yes")) {
            ds.find(Student.class)
                    .filter(Filters.eq("studentId", id))
                    .delete();
            System.out.println("✅ Student deleted successfully!");
        } else {
            System.out.println("❌ Deletion cancelled!");
        }
    }

    public Student getStudentById(int id) {
        return ds.find(Student.class)
                .filter(Filters.eq("studentId", id))
                .first();
    }
}