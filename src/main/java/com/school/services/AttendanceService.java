package com.school.services;

import com.school.database.DBConnection;
import com.school.model.Attendance;
import dev.morphia.Datastore;

import java.util.Scanner;

public class AttendanceService {

    private Datastore ds = DBConnection.getDatastore();
    private Scanner sc = new Scanner(System.in);

    public void markAttendance() {
        System.out.print("Student ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Date (YYYY-MM-DD): ");
        String date = sc.nextLine();
        System.out.print("Present (true/false): ");
        boolean present = sc.nextBoolean();

        ds.save(new Attendance(id, date, present));
        System.out.println("✔ Attendance saved");
    }

    public void viewAttendance() {
        ds.find(Attendance.class).forEach(System.out::println);
    }
}
