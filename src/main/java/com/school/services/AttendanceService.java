package com.school.services;

import com.school.database.DBConnection;
import com.school.model.Attendance;
import com.school.model.Student;
import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class AttendanceService {

    private final Datastore ds = DBConnection.getDatastore();
    private final Scanner sc = new Scanner(System.in);
    private final StudentService studentService = new StudentService();

    public void markAttendance(int teacherId) {
        System.out.print("Student ID: ");
        int studentId = sc.nextInt();
        sc.nextLine();

        Student student = studentService.getStudentById(studentId);
        if (student == null) {
            System.out.println("❌ Student not found!");
            return;
        }

        System.out.print("Subject Code (e.g., MATH101): ");
        String subjectCode = sc.nextLine();

        System.out.print("Date (YYYY-MM-DD) or press Enter for today: ");
        String date = sc.nextLine();
        if (date.isEmpty()) {
            date = LocalDate.now().toString();
        }

        System.out.print("Present? (yes/no): ");
        String presentInput = sc.nextLine();
        boolean present = presentInput.equalsIgnoreCase("yes");

        System.out.print("Remarks (optional): ");
        String remarks = sc.nextLine();

        Attendance attendance = new Attendance(studentId, subjectCode, teacherId, date, present, remarks);

        try {
            ds.save(attendance);
            System.out.println("✅ Attendance marked successfully!");
        } catch (Exception e) {
            System.err.println("❌ Error marking attendance: " + e.getMessage());
        }
    }

    public void viewAttendanceByStudent() {
        System.out.print("Student ID: ");
        int studentId = sc.nextInt();
        sc.nextLine();

        List<Attendance> attendanceList = ds.find(Attendance.class)
                .filter(Filters.eq("studentId", studentId))
                .iterator().toList();

        if (attendanceList.isEmpty()) {
            System.out.println("⚠ No attendance records found!");
            return;
        }

        System.out.println("\n========== ATTENDANCE FOR STUDENT ID: " + studentId + " ==========");
        attendanceList.forEach(System.out::println);

        long totalDays = attendanceList.size();
        long presentDays = attendanceList.stream().filter(Attendance::isPresent).count();
        double percentage = (presentDays * 100.0) / totalDays;

        System.out.println("\nTotal Days: " + totalDays);
        System.out.println("Present: " + presentDays);
        System.out.println("Absent: " + (totalDays - presentDays));
        System.out.println("Attendance Percentage: " + String.format("%.2f", percentage) + "%");
        System.out.println("================================================================\n");
    }

    public void viewAttendanceByTeacher(int teacherId) {
        List<Attendance> attendanceList = ds.find(Attendance.class)
                .filter(Filters.eq("teacherId", teacherId))
                .iterator().toList();

        if (attendanceList.isEmpty()) {
            System.out.println("⚠ No attendance records found!");
            return;
        }

        System.out.println("\n========== YOUR ATTENDANCE RECORDS ==========");
        attendanceList.forEach(System.out::println);
        System.out.println("=============================================\n");
    }

    public void viewAttendanceByDate() {
        System.out.print("Enter Date (YYYY-MM-DD): ");
        String date = sc.nextLine();

        List<Attendance> attendanceList = ds.find(Attendance.class)
                .filter(Filters.eq("date", date))
                .iterator().toList();

        if (attendanceList.isEmpty()) {
            System.out.println("⚠ No attendance records found for this date!");
            return;
        }

        System.out.println("\n========== ATTENDANCE FOR " + date + " ==========");
        attendanceList.forEach(System.out::println);
        System.out.println("=================================================\n");
    }

    public void updateAttendance(int teacherId) {
        System.out.print("Student ID: ");
        int studentId = sc.nextInt();
        sc.nextLine();

        System.out.print("Date (YYYY-MM-DD): ");
        String date = sc.nextLine();

        System.out.print("Subject Code: ");
        String subjectCode = sc.nextLine();

        Attendance attendance = ds.find(Attendance.class)
                .filter(
                        Filters.eq("studentId", studentId),
                        Filters.eq("date", date),
                        Filters.eq("subjectCode", subjectCode),
                        Filters.eq("teacherId", teacherId))
                .first();

        if (attendance == null) {
            System.out.println("❌ Attendance record not found or you don't have permission!");
            return;
        }

        System.out.print("Change to Present? (yes/no): ");
        String presentInput = sc.nextLine();
        boolean present = presentInput.equalsIgnoreCase("yes");

        System.out.print("New Remarks (optional): ");
        String remarks = sc.nextLine();

        ds.find(Attendance.class)
                .filter(
                        Filters.eq("studentId", studentId),
                        Filters.eq("date", date),
                        Filters.eq("subjectCode", subjectCode))
                .update(
                        dev.morphia.query.updates.UpdateOperators.set("present", present),
                        dev.morphia.query.updates.UpdateOperators.set("remarks", remarks))
                .execute();

        System.out.println("✅ Attendance updated successfully!");
    }

    public void deleteAttendance(int teacherId) {
        System.out.print("Student ID: ");
        int studentId = sc.nextInt();
        sc.nextLine();

        System.out.print("Date (YYYY-MM-DD): ");
        String date = sc.nextLine();

        System.out.print("Subject Code: ");
        String subjectCode = sc.nextLine();

        Attendance attendance = ds.find(Attendance.class)
                .filter(
                        Filters.eq("studentId", studentId),
                        Filters.eq("date", date),
                        Filters.eq("subjectCode", subjectCode),
                        Filters.eq("teacherId", teacherId))
                .first();

        if (attendance == null) {
            System.out.println("❌ Attendance record not found or you don't have permission!");
            return;
        }

        ds.find(Attendance.class)
                .filter(
                        Filters.eq("studentId", studentId),
                        Filters.eq("date", date),
                        Filters.eq("subjectCode", subjectCode))
                .delete();

        System.out.println("✅ Attendance deleted successfully!");
    }
}