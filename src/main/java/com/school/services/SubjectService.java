package com.school.services;

import com.school.database.DBConnection;
import com.school.model.Subject;
import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;

import java.util.Scanner;

public class SubjectService {

    private Datastore ds = DBConnection.getDatastore();
    private Scanner sc = new Scanner(System.in);

    public void addSubject() {
        System.out.print("Subject Code: ");
        String code = sc.nextLine();

        System.out.print("Subject Name: ");
        String name = sc.nextLine();

        ds.save(new Subject(code, name));
        System.out.println("✔ Subject added");
    }

    public void deleteSubject() {
        System.out.print("Subject Code: ");
        String code = sc.nextLine();

        ds.find(Subject.class)
                .filter(Filters.eq("code", code))
                .delete();

        System.out.println("✔ Subject deleted");
    }

}
