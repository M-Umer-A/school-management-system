package com.school;

import com.school.model.Student;
import com.school.model.Teacher;
import com.school.model.User;
import com.school.services.*;

import java.util.Scanner;

public class App {

    private static final Scanner sc = new Scanner(System.in);
    private static final AuthService authService = new AuthService();
    private static final StudentService studentService = new StudentService();
    private static final TeacherService teacherService = new TeacherService();
    private static final SubjectService subjectService = new SubjectService();
    private static final AttendanceService attendanceService = new AttendanceService();
    private static final GradeService gradeService = new GradeService();
    private static final LectureNoteService lectureNoteService = new LectureNoteService();

    public static void main(String[] args) {
        System.out.println("🏫 WELCOME TO SCHOOL MANAGEMENT SYSTEM 🏫\n");

        // Create default admin if doesn't exist
        authService.createDefaultAdmin();

        while (true) {
            System.out.println("\n===== LOGIN MENU =====");
            System.out.println("1. Login");
            System.out.println("2. Register New User (Admin Only)");
            System.out.println("0. Exit");
            System.out.print("Choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            try {
                switch (choice) {
                    case 1 -> login();
                    case 2 -> registerUser();
                    case 0 -> {
                        System.out.println("👋 Thank you for using the system!");
                        System.exit(0);
                    }
                    default -> System.out.println("❌ Invalid choice");
                }
            } catch (RuntimeException e) {
                System.out.println("❌ " + e.getMessage());
            }
        }
    }

    private static void login() {
        System.out.print("Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        User user = authService.login(username, password);

        if (user == null) {
            System.out.println("❌ Invalid username or password!");
            return;
        }

        System.out.println("✅ Login successful! Welcome, " + username);

        switch (user.getRole()) {
            case "ADMIN" -> adminModule();
            case "TEACHER" -> teacherModule(user.getReferenceId());
            case "STUDENT" -> studentModule(user.getReferenceId());
            default -> System.out.println("❌ Invalid role!");
        }
    }

    private static void registerUser() {
        System.out.print("Admin password to proceed: ");
        String adminPass = sc.nextLine();

        User admin = authService.login("admin", adminPass);
        if (admin == null || !admin.getRole().equals("ADMIN")) {
            System.out.println("❌ Unauthorized!");
            return;
        }

        System.out.print("New Username: ");
        String username = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        System.out.print("Role (TEACHER/STUDENT): ");
        String role = sc.nextLine().toUpperCase();

        if (!role.equals("TEACHER") && !role.equals("STUDENT")) {
            System.out.println("❌ Invalid role!");
            return;
        }

        System.out.print("Enter " + role + " ID: ");
        int refId = sc.nextInt();
        sc.nextLine();

        authService.registerUser(username, password, role, refId);
    }

    // ============== ADMIN MODULE ==============
    private static void adminModule() {
        while (true) {
            System.out.println("\n===== ADMIN MODULE =====");
            System.out.println("1. Student Management");
            System.out.println("2. Teacher Management");
            System.out.println("3. Subject Management");
            System.out.println("4. Attendance Management");
            System.out.println("5. Grade Management");
            System.out.println("6. Lecture Notes Management");
            System.out.println("7. View Reports");
            System.out.println("0. Logout");
            System.out.print("Choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            try {
                switch (choice) {
                    case 1 -> studentManagement();
                    case 2 -> teacherManagement();
                    case 3 -> subjectManagement();
                    case 4 -> attendanceManagementAdmin();
                    case 5 -> gradeManagementAdmin();
                    case 6 -> lectureNoteManagementAdmin();
                    case 7 -> viewReports();
                    case 0 -> {
                        return;
                    }
                    default -> System.out.println("❌ Invalid choice");
                }
            } catch (RuntimeException e) {
                System.out.println("❌ " + e.getMessage());
            }
        }
    }

    private static void studentManagement() {
        System.out.println("\n1. Add  2. View All  3. View by ID  4. Update  5. Delete");
        System.out.print("Choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1 -> studentService.addStudent();
            case 2 -> studentService.viewAllStudents();
            case 3 -> studentService.viewStudentById();
            case 4 -> studentService.updateStudent();
            case 5 -> studentService.deleteStudent();
        }
    }

    private static void teacherManagement() {
        System.out.println("\n1. Add  2. View All  3. View by ID  4. Update  5. Delete");
        System.out.print("Choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1 -> teacherService.addTeacher();
            case 2 -> teacherService.viewAllTeachers();
            case 3 -> teacherService.viewTeacherById();
            case 4 -> teacherService.updateTeacher();
            case 5 -> teacherService.deleteTeacher();
        }
    }

    private static void subjectManagement() {
        System.out.println("\n1. Add  2. View All  3. View by Code  4. Update  5. Delete");
        System.out.print("Choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1 -> subjectService.addSubject();
            case 2 -> subjectService.viewAllSubjects();
            case 3 -> subjectService.viewSubjectByCode();
            case 4 -> subjectService.updateSubject();
            case 5 -> subjectService.deleteSubject();
        }
    }

    private static void attendanceManagementAdmin() {
        System.out.println("\n1. View by Student  2. View by Date");
        System.out.print("Choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1 -> attendanceService.viewAttendanceByStudent();
            case 2 -> attendanceService.viewAttendanceByDate();
        }
    }

    private static void gradeManagementAdmin() {
        System.out.println("\n1. View Grades by Student");
        System.out.print("Choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) {
            gradeService.viewGradesByStudent();
        }
    }

    private static void lectureNoteManagementAdmin() {
        System.out.println("\n1. View All Lecture Notes");
        System.out.print("Choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        if (choice == 1) {
            lectureNoteService.viewLectureNoteDetails();
        }
    }

    private static void viewReports() {
        System.out.println("\n===== REPORTS =====");
        System.out.println("1. Student Attendance Report");
        System.out.println("2. Student Grade Report");
        System.out.print("Choice: ");
        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {
            case 1 -> attendanceService.viewAttendanceByStudent();
            case 2 -> gradeService.viewGradesByStudent();
        }
    }

    // ============== TEACHER MODULE ==============
    private static void teacherModule(int teacherId) {
        Teacher teacher = teacherService.getTeacherById(teacherId);
        if (teacher == null) {
            System.out.println("❌ Teacher profile not found!");
            return;
        }

        System.out.println("\n👨‍🏫 Welcome, " + teacher.getName() + "!");

        while (true) {
            System.out.println("\n===== TEACHER MODULE =====");
            System.out.println("1. Mark Attendance");
            System.out.println("2. View My Attendance Records");
            System.out.println("3. Update Attendance");
            System.out.println("4. Add Grade");
            System.out.println("5. View Grades I've Given");
            System.out.println("6. Update Grade");
            System.out.println("7. Add Lecture Note");
            System.out.println("8. View My Lecture Notes");
            System.out.println("9. Update Lecture Note");
            System.out.println("10. View Students");
            System.out.println("0. Logout");
            System.out.print("Choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            try {
                switch (choice) {
                    case 1 -> attendanceService.markAttendance(teacherId);
                    case 2 -> attendanceService.viewAttendanceByTeacher(teacherId);
                    case 3 -> attendanceService.updateAttendance(teacherId);
                    case 4 -> gradeService.addGrade(teacherId);
                    case 5 -> gradeService.viewGradesByTeacher(teacherId);
                    case 6 -> gradeService.updateGrade(teacherId);
                    case 7 -> lectureNoteService.addLectureNote(teacherId);
                    case 8 -> lectureNoteService.viewLectureNotesByTeacher(teacherId);
                    case 9 -> lectureNoteService.updateLectureNote(teacherId);
                    case 10 -> studentService.viewAllStudents();
                    case 0 -> {
                        return;
                    }
                    default -> System.out.println("❌ Invalid choice");
                }
            } catch (RuntimeException e) {
                System.out.println("❌ " + e.getMessage());
            }
        }
    }

    // ============== STUDENT MODULE ==============
    private static void studentModule(int studentId) {
        Student student = studentService.getStudentById(studentId);
        if (student == null) {
            System.out.println("❌ Student profile not found!");
            return;
        }

        System.out.println("\n👨‍🎓 Welcome, " + student.getName() + "!");

        while (true) {
            System.out.println("\n===== STUDENT MODULE =====");
            System.out.println("1. View My Profile");
            System.out.println("2. View My Attendance");
            System.out.println("3. View My Grades");
            System.out.println("4. View Lecture Notes");
            System.out.println("5. View All Subjects");
            System.out.println("0. Logout");
            System.out.print("Choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            try {
                switch (choice) {
                    case 1 -> System.out.println("\n" + student);
                    case 2 -> {
                        System.out.println("Viewing attendance for Student ID: " + studentId);
                        attendanceService.viewAttendanceByStudent();
                    }
                    case 3 -> {
                        System.out.println("Viewing grades for Student ID: " + studentId);
                        gradeService.viewGradesByStudent();
                    }
                    case 4 -> lectureNoteService.viewLectureNotesByGrade(student.getGrade());
                    case 5 -> subjectService.viewAllSubjects();
                    case 0 -> {
                        return;
                    }
                    default -> System.out.println("❌ Invalid choice");
                }
            } catch (RuntimeException e) {
                System.out.println("❌ " + e.getMessage());
            }
        }
    }
}