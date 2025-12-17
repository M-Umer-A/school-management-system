package com.school.services;

import com.school.database.DBConnection;
import com.school.model.Grade;
import com.school.model.Student;
import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class GradeService {

    private final Datastore ds = DBConnection.getDatastore();
    private final Scanner sc = new Scanner(System.in);
    private final StudentService studentService = new StudentService();

    public void addGrade(int teacherId) {
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

        System.out.print("Term (Mid-Term/Final/Quiz-1, etc.): ");
        String term = sc.nextLine();

        System.out.print("Marks (0-100): ");
        int marks = sc.nextInt();
        sc.nextLine();

        String grade = calculateGrade(marks);

        String date = LocalDate.now().toString();

        Grade gradeObj = new Grade(studentId, subjectCode, teacherId, grade, marks, term, date);

        try {
            ds.save(gradeObj);
            System.out.println("✅ Grade added successfully! Grade: " + grade);
        } catch (Exception e) {
            System.err.println("❌ Error adding grade: " + e.getMessage());
        }
    }

    public void viewGradesByStudent() {
        System.out.print("Student ID: ");
        int studentId = sc.nextInt();
        sc.nextLine();

        List<Grade> grades = ds.find(Grade.class)
                .filter(Filters.eq("studentId", studentId))
                .iterator().toList();

        if (grades.isEmpty()) {
            System.out.println("⚠ No grades found for this student!");
            return;
        }

        System.out.println("\n========== GRADES FOR STUDENT ID: " + studentId + " ==========");
        grades.forEach(System.out::println);
        System.out.println("==========================================================\n");
    }

    public void viewGradesByTeacher(int teacherId) {
        List<Grade> grades = ds.find(Grade.class)
                .filter(Filters.eq("teacherId", teacherId))
                .iterator().toList();

        if (grades.isEmpty()) {
            System.out.println("⚠ No grades found!");
            return;
        }

        System.out.println("\n========== YOUR GRADED STUDENTS ==========");
        grades.forEach(System.out::println);
        System.out.println("==========================================\n");
    }

    public void updateGrade(int teacherId) {
        System.out.print("Student ID: ");
        int studentId = sc.nextInt();
        sc.nextLine();

        System.out.print("Subject Code: ");
        String subjectCode = sc.nextLine();

        System.out.print("Term: ");
        String term = sc.nextLine();

        Grade grade = ds.find(Grade.class)
                .filter(
                        Filters.eq("studentId", studentId),
                        Filters.eq("subjectCode", subjectCode),
                        Filters.eq("term", term),
                        Filters.eq("teacherId", teacherId))
                .first();

        if (grade == null) {
            System.out.println("❌ Grade record not found or you don't have permission!");
            return;
        }

        System.out.print("New Marks (0-100): ");
        int newMarks = sc.nextInt();
        sc.nextLine();

        String newGrade = calculateGrade(newMarks);

        ds.find(Grade.class)
                .filter(
                        Filters.eq("studentId", studentId),
                        Filters.eq("subjectCode", subjectCode),
                        Filters.eq("term", term))
                .update(
                        dev.morphia.query.updates.UpdateOperators.set("marks", newMarks),
                        dev.morphia.query.updates.UpdateOperators.set("grade", newGrade))
                .execute();

        System.out.println("✅ Grade updated successfully! New Grade: " + newGrade);
    }

    public void deleteGrade(int teacherId) {
        System.out.print("Student ID: ");
        int studentId = sc.nextInt();
        sc.nextLine();

        System.out.print("Subject Code: ");
        String subjectCode = sc.nextLine();

        System.out.print("Term: ");
        String term = sc.nextLine();

        Grade grade = ds.find(Grade.class)
                .filter(
                        Filters.eq("studentId", studentId),
                        Filters.eq("subjectCode", subjectCode),
                        Filters.eq("term", term),
                        Filters.eq("teacherId", teacherId))
                .first();

        if (grade == null) {
            System.out.println("❌ Grade record not found or you don't have permission!");
            return;
        }

        ds.find(Grade.class)
                .filter(
                        Filters.eq("studentId", studentId),
                        Filters.eq("subjectCode", subjectCode),
                        Filters.eq("term", term))
                .delete();

        System.out.println("✅ Grade deleted successfully!");
    }

    private String calculateGrade(int marks) {
        if (marks >= 90)
            return "A+";
        else if (marks >= 85)
            return "A";
        else if (marks >= 80)
            return "A-";
        else if (marks >= 75)
            return "B+";
        else if (marks >= 70)
            return "B";
        else if (marks >= 65)
            return "B-";
        else if (marks >= 60)
            return "C+";
        else if (marks >= 55)
            return "C";
        else if (marks >= 50)
            return "C-";
        else if (marks >= 45)
            return "D";
        else
            return "F";
    }
}