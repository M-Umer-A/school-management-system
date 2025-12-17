package com.school.services;

import com.school.database.DBConnection;
import com.school.model.Teacher;

import dev.morphia.Datastore;
import java.util.Scanner;

public class TeacherService {

    private final Datastore ds = DBConnection.getDatastore();
    private final Scanner sc = new Scanner(System.in);

    public void addTeacher() {
        System.out.print("ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Name: ");
        String name = sc.nextLine();

        System.out.print("Department: ");
        String department = sc.nextLine();

        ds.save(new Teacher(id, name, department));
        System.out.println("✔ Teacher added");
    }

    public void updateTeacherSubject() {
        System.out.print("Teacher ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("New Subject: ");
        String subject = sc.nextLine();

        ds.find(Teacher.class)
                .filter(dev.morphia.query.filters.Filters.eq("_id", id))
                .update(dev.morphia.query.updates.UpdateOperators.set("subject", subject))
                .execute();

        System.out.println("✔ Updated");
    }

    public void deleteTeacher() {
        System.out.print("Teacher ID: ");
        int id = sc.nextInt();
        ds.find(Teacher.class)
                .filter(dev.morphia.query.filters.Filters.eq("_id", id))
                .delete();
        System.out.println("✔ Deleted");
    }
}
