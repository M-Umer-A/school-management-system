package com.school.services;

import com.school.database.DBConnection;
import com.school.model.LectureNote;
import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class LectureNoteService {

    private final Datastore ds = DBConnection.getDatastore();
    private final Scanner sc = new Scanner(System.in);

    public void addLectureNote(int teacherId) {
        System.out.print("Subject Code (e.g., MATH101): ");
        String subjectCode = sc.nextLine();

        System.out.print("Grade/Class (e.g., Grade 10): ");
        String grade = sc.nextLine();

        System.out.print("Title: ");
        String title = sc.nextLine();

        System.out.print("Content (lecture note details): ");
        String content = sc.nextLine();

        String date = LocalDate.now().toString();

        LectureNote note = new LectureNote(subjectCode, teacherId, title, content, date, grade);

        try {
            ds.save(note);
            System.out.println("✅ Lecture note added successfully!");
        } catch (Exception e) {
            System.err.println("❌ Error adding lecture note: " + e.getMessage());
        }
    }

    public void viewLectureNotesByTeacher(int teacherId) {
        List<LectureNote> notes = ds.find(LectureNote.class)
                .filter(Filters.eq("teacherId", teacherId))
                .iterator().toList();

        if (notes.isEmpty()) {
            System.out.println("⚠ No lecture notes found!");
            return;
        }

        System.out.println("\n========== YOUR LECTURE NOTES ==========");
        int index = 1;
        for (LectureNote note : notes) {
            System.out.println(index++ + ". " + note);
        }
        System.out.println("========================================\n");
    }

    public void viewLectureNotesByGrade(String grade) {
        List<LectureNote> notes = ds.find(LectureNote.class)
                .filter(Filters.eq("grade", grade))
                .iterator().toList();

        if (notes.isEmpty()) {
            System.out.println("⚠ No lecture notes found for your grade!");
            return;
        }

        System.out.println("\n========== LECTURE NOTES FOR " + grade + " ==========");
        int index = 1;
        for (LectureNote note : notes) {
            System.out.println(index++ + ". " + note);
        }
        System.out.println("====================================================\n");
    }

    public void viewLectureNoteDetails() {
        System.out.print("Enter note number to view details: ");
        // This would require storing and tracking note IDs
        // For simplicity, we'll show all notes with full content
        System.out.print("Enter Subject Code: ");
        String subjectCode = sc.nextLine();

        List<LectureNote> notes = ds.find(LectureNote.class)
                .filter(Filters.eq("subjectCode", subjectCode))
                .iterator().toList();

        if (notes.isEmpty()) {
            System.out.println("⚠ No lecture notes found!");
            return;
        }

        System.out.println("\n========== DETAILED LECTURE NOTES ==========");
        for (LectureNote note : notes) {
            System.out.println("\nSubject: " + note.getSubjectCode());
            System.out.println("Title: " + note.getTitle());
            System.out.println("Date: " + note.getDate());
            System.out.println("Grade: " + note.getGrade());
            System.out.println("Content:\n" + note.getContent());
            System.out.println("--------------------------------------------");
        }
        System.out.println("============================================\n");
    }

    public void updateLectureNote(int teacherId) {
        System.out.print("Subject Code: ");
        String subjectCode = sc.nextLine();

        System.out.print("Title of note to update: ");
        String title = sc.nextLine();

        LectureNote note = ds.find(LectureNote.class)
                .filter(
                        Filters.eq("teacherId", teacherId),
                        Filters.eq("subjectCode", subjectCode),
                        Filters.eq("title", title))
                .first();

        if (note == null) {
            System.out.println("❌ Lecture note not found or you don't have permission!");
            return;
        }

        System.out.print("New Content: ");
        String newContent = sc.nextLine();

        ds.find(LectureNote.class)
                .filter(
                        Filters.eq("teacherId", teacherId),
                        Filters.eq("subjectCode", subjectCode),
                        Filters.eq("title", title))
                .update(dev.morphia.query.updates.UpdateOperators.set("content", newContent))
                .execute();

        System.out.println("✅ Lecture note updated successfully!");
    }

    public void deleteLectureNote(int teacherId) {
        System.out.print("Subject Code: ");
        String subjectCode = sc.nextLine();

        System.out.print("Title of note to delete: ");
        String title = sc.nextLine();

        LectureNote note = ds.find(LectureNote.class)
                .filter(
                        Filters.eq("teacherId", teacherId),
                        Filters.eq("subjectCode", subjectCode),
                        Filters.eq("title", title))
                .first();

        if (note == null) {
            System.out.println("❌ Lecture note not found or you don't have permission!");
            return;
        }

        ds.find(LectureNote.class)
                .filter(
                        Filters.eq("teacherId", teacherId),
                        Filters.eq("subjectCode", subjectCode),
                        Filters.eq("title", title))
                .delete();

        System.out.println("✅ Lecture note deleted successfully!");
    }
}