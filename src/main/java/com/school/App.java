package com.school;

import com.school.exceptions.*;
import com.school.services.*;

import java.util.Scanner;

public class App {

    private static final Scanner sc = new Scanner(System.in);
    private static final StudentService studentService = new StudentService();
    private static final TeacherService teacherService = new TeacherService();
    private static final SubjectService subjectService = new SubjectService();
    private static final AttendanceService attendanceService = new AttendanceService();

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n===== SCHOOL MANAGEMENT SYSTEM =====");
            System.out.println("1. Student");
            System.out.println("2. Teacher");
            System.out.println("3. Subject");
            System.out.println("4. Attendance");
            System.out.println("0. Exit");

            int choice = sc.nextInt();
            sc.nextLine();

            try {
                switch (choice) {
                    case 1 -> studentMenu();
                    case 2 -> teacherMenu();
                    case 3 -> subjectMenu();
                    case 4 -> attendanceMenu();
                    case 0 -> System.exit(0);
                    default -> System.out.println("Invalid choice");
                }
            } catch (RuntimeException e) {
                System.out.println("❌ " + e.getMessage());
            }
        }
    }

    private static void studentMenu() {
        System.out.println("1 Add  2 View  3 Update  4 Delete");
        switch (sc.nextInt()) {
            case 1 -> studentService.addStudent();
            // case 2 -> studentService.viewStudents();
            case 3 -> studentService.updateStudent();
            case 4 -> studentService.deleteStudent();
        }
    }

    private static void teacherMenu() {
        System.out.println("1 Add  2 View  3 Update  4 Delete");
        switch (sc.nextInt()) {
            case 1 -> teacherService.addTeacher();
            // case 2 -> teacherService.viewTeachers();
            case 3 -> teacherService.updateTeacherSubject();
            case 4 -> teacherService.deleteTeacher();
        }
    }

    private static void subjectMenu() {
        System.out.println("1 Add  2 View");
        switch (sc.nextInt()) {
            case 1 -> subjectService.addSubject();
            // case 2 -> subjectService.viewSubjects();
        }
    }

    private static void attendanceMenu() {
        attendanceService.markAttendance();
    }
}
