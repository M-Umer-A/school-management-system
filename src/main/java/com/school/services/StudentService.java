package com.school.services;

import com.school.database.DBConnection;
import com.school.exceptions.DuplicateRecordException;
import com.school.exceptions.RecordNotFoundException;
import com.school.model.Student;

import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;

import java.util.Scanner;

public class StudentService {

    private final Datastore ds = DBConnection.getDatastore();
    private final Scanner sc = new Scanner(System.in);

    public void addStudent() {
        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        try {
            ds.save(Student);
            System.out.println("✅ Student added successfully!");
                System.out.println("Student ID: " + Student. getStudentId());
        } catch (Exception e) {
            System.err.println("❌ Error adding student: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Grade: ");
        String grade = sc.nextLine();

        ds.save(new Student(id, name, grade));
        System.out.println("✔ Student added");
    }

    public void updateStudent() {
        System.out.print("Student ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        Student s = ds.find(Student.class)
                .filter(Filters.eq("studentId", id))
                .first();

        if (s == null)
            throw new RecordNotFoundException("Student not found");

        System.out.print("New Subject: ");
        String subject = sc.nextLine();

        ds.find(Student.class)
                .filter(Filters.eq("studentId", id))
                .update(dev.morphia.query.updates.UpdateOperators.set("subject", subject))
                .execute();

        System.out.println("✔ Updated");
    }

    public void deleteStudent() {
        System.out.print("Student ID: ");
        int id = sc.nextInt();

        if (ds.find(Student.class)
                .filter(Filters.eq("studentId", id))
                .first() == null)
            throw new RecordNotFoundException("Student not found");

        ds.find(Student.class)
                .filter(Filters.eq("studentId", id))
                .delete();

        System.out.println("✔ Deleted");
    }

}
