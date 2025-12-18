package com.school.gui;

import com.school.model.Student;
import com.school.model.Teacher;
import com.school.services.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class AdminDashboard {

    private Stage stage;
    private StudentService studentService = new StudentService();
    private TeacherService teacherService = new TeacherService();
    private SubjectService subjectService = new SubjectService();
    private AttendanceService attendanceService = new AttendanceService();
    private GradeService gradeService = new GradeService();

    public AdminDashboard(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f7fa;");

        // ===== TOP BAR =====
        HBox topBar = new HBox(15);
        topBar.setPadding(new Insets(15, 20, 15, 20));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-background-color: #667eea;");

        Label title = new Label("👨‍💼 ADMIN DASHBOARD");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        title.setTextFill(Color.WHITE);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button logoutBtn = new Button("Logout");
        logoutBtn.setStyle("-fx-background-color: white; -fx-text-fill: #667eea; -fx-font-weight: bold; -fx-padding: 8 20; -fx-background-radius: 5;");
        logoutBtn.setOnAction(e -> new SchoolManagementGUI().start(stage));

        topBar.getChildren().addAll(title, spacer, logoutBtn);

        // ===== LEFT MENU =====
        VBox leftMenu = new VBox(10);
        leftMenu.setPadding(new Insets(20));
        leftMenu.setPrefWidth(220);
        leftMenu.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-width: 0 1 0 0;");

        Button homeBtn = createMenuButton("🏠 Home");
        Button studentsBtn = createMenuButton("🎓 Students");
        Button teachersBtn = createMenuButton("👩‍🏫 Teachers");
        Button subjectsBtn = createMenuButton("📚 Subjects");
        Button attendanceBtn = createMenuButton("📊 Attendance");
        Button gradesBtn = createMenuButton("📝 Grades");

        leftMenu.getChildren().addAll(homeBtn, studentsBtn, teachersBtn, subjectsBtn, attendanceBtn, gradesBtn);

        // ===== CENTER CONTENT =====
        StackPane center = new StackPane();
        center.setPadding(new Insets(20));

        VBox welcomeScreen = createWelcomeScreen();
        center.getChildren().add(welcomeScreen);

        // ===== BUTTON ACTIONS =====
        homeBtn.setOnAction(e -> {
            center.getChildren().clear();
            center.getChildren().add(createWelcomeScreen());
        });

        studentsBtn.setOnAction(e -> {
            center.getChildren().clear();
            center.getChildren().add(createStudentManagement());
        });

        teachersBtn.setOnAction(e -> {
            center.getChildren().clear();
            center.getChildren().add(createTeacherManagement());
        });

        subjectsBtn.setOnAction(e -> {
            center.getChildren().clear();
            center.getChildren().add(createSubjectManagement());
        });

        attendanceBtn.setOnAction(e -> {
            center.getChildren().clear();
            center.getChildren().add(createAttendanceManagement());
        });

        gradesBtn.setOnAction(e -> {
            center.getChildren().clear();
            center.getChildren().add(createGradeManagement());
        });

        root.setTop(topBar);
        root.setLeft(leftMenu);
        root.setCenter(center);

        Scene scene = new Scene(root, 1200, 700);
        stage.setScene(scene);
        stage.show();
    }

    private Button createMenuButton(String text) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #333; -fx-font-size: 14; -fx-padding: 12;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #edf2f7; -fx-text-fill: #667eea; -fx-font-weight: bold; -fx-padding: 12; -fx-cursor: hand;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #333; -fx-font-size: 14; -fx-padding: 12;"));
        return btn;
    }

    private VBox createWelcomeScreen() {
        VBox welcome = new VBox(20);
        welcome.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Welcome to Admin Dashboard");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        titleLabel.setTextFill(Color.web("#667eea"));

        Label infoLabel = new Label("Select an option from the left menu to manage the system");
        infoLabel.setFont(Font.font("Arial", 16));
        infoLabel.setTextFill(Color.GRAY);

        // Stats Cards
        HBox statsBox = new HBox(20);
        statsBox.setAlignment(Pos.CENTER);
        statsBox.getChildren().addAll(
            createStatCard("👨‍🎓 Students", "Click Students Menu", "#4299e1"),
            createStatCard("👩‍🏫 Teachers", "Click Teachers Menu", "#48bb78"),
            createStatCard("📚 Subjects", "Click Subjects Menu", "#ed8936")
        );

        welcome.getChildren().addAll(titleLabel, infoLabel, statsBox);
        return welcome;
    }

    private VBox createStatCard(String title, String value, String color) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(20));
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);");
        card.setPrefWidth(200);
        card.setPrefHeight(120);

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        titleLabel.setTextFill(Color.web(color));

        Label valueLabel = new Label(value);
        valueLabel.setFont(Font.font("Arial", 12));
        valueLabel.setTextFill(Color.GRAY);

        card.getChildren().addAll(titleLabel, valueLabel);
        return card;
    }

    private VBox createStudentManagement() {
        VBox container = new VBox(15);
        container.setPadding(new Insets(20));

        Label title = new Label("👨‍🎓 Student Management");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        HBox buttonBar = new HBox(10);
        Button viewAllBtn = new Button("📋 View All Students");
        Button addBtn = new Button("➕ Add Student");
        styleButton(viewAllBtn, "#4299e1");
        styleButton(addBtn, "#48bb78");

        buttonBar.getChildren().addAll(viewAllBtn, addBtn);

        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        outputArea.setPrefHeight(400);
        outputArea.setStyle("-fx-font-family: monospace; -fx-font-size: 12;");

        viewAllBtn.setOnAction(e -> {
            outputArea.clear();
            var students = studentService.getAllStudents();
            if (students.isEmpty()) {
                outputArea.setText("⚠ No students found!");
            } else {
                outputArea.setText("========== ALL STUDENTS ==========\n\n");
                students.forEach(student -> outputArea.appendText(student.toString() + "\n"));
            }
        });

        addBtn.setOnAction(e -> showAddStudentDialog());

        container.getChildren().addAll(title, buttonBar, outputArea);
        return container;
    }

    private VBox createTeacherManagement() {
        VBox container = new VBox(15);
        container.setPadding(new Insets(20));

        Label title = new Label("👩‍🏫 Teacher Management");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        HBox buttonBar = new HBox(10);
        Button viewAllBtn = new Button("📋 View All Teachers");
        Button addBtn = new Button("➕ Add Teacher");
        styleButton(viewAllBtn, "#4299e1");
        styleButton(addBtn, "#48bb78");

        buttonBar.getChildren().addAll(viewAllBtn, addBtn);

        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        outputArea.setPrefHeight(400);
        outputArea.setStyle("-fx-font-family: monospace; -fx-font-size: 12;");

        viewAllBtn.setOnAction(e -> {
            outputArea.clear();
            var teachers = teacherService.getAllTeachers();
            if (teachers.isEmpty()) {
                outputArea.setText("⚠ No teachers found!");
            } else {
                outputArea.setText("========== ALL TEACHERS ==========\n\n");
                teachers.forEach(teacher -> outputArea.appendText(teacher.toString() + "\n"));
            }
        });

        addBtn.setOnAction(e -> showAddTeacherDialog());

        container.getChildren().addAll(title, buttonBar, outputArea);
        return container;
    }

    private VBox createSubjectManagement() {
        VBox container = new VBox(15);
        container.setPadding(new Insets(20));

        Label title = new Label("📚 Subject Management");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        HBox buttonBar = new HBox(10);
        Button viewAllBtn = new Button("📋 View All Subjects");
        Button addBtn = new Button("➕ Add Subject");
        styleButton(viewAllBtn, "#4299e1");
        styleButton(addBtn, "#48bb78");

        buttonBar.getChildren().addAll(viewAllBtn, addBtn);

        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        outputArea.setPrefHeight(400);
        outputArea.setStyle("-fx-font-family: monospace; -fx-font-size: 12;");

        viewAllBtn.setOnAction(e -> {
            outputArea.clear();
            var subjects = subjectService.getAllSubjects();
            if (subjects.isEmpty()) {
                outputArea.setText("⚠ No subjects found!");
            } else {
                outputArea.setText("========== ALL SUBJECTS ==========\n\n");
                subjects.forEach(subject -> outputArea.appendText(subject.toString() + "\n"));
            }
        });

        addBtn.setOnAction(e -> showAddSubjectDialog());

        container.getChildren().addAll(title, buttonBar, outputArea);
        return container;
    }

    private VBox createAttendanceManagement() {
        VBox container = new VBox(15);
        container.setPadding(new Insets(20));

        Label title = new Label("📊 Attendance Management");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        Label info = new Label("Use console app for detailed attendance management");
        info.setTextFill(Color.GRAY);

        container.getChildren().addAll(title, info);
        return container;
    }

    private VBox createGradeManagement() {
        VBox container = new VBox(15);
        container.setPadding(new Insets(20));

        Label title = new Label("📝 Grade Management");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        Label info = new Label("Use console app for detailed grade management");
        info.setTextFill(Color.GRAY);

        container.getChildren().addAll(title, info);
        return container;
    }

    private void styleButton(Button btn, String color) {
        btn.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 5;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: derive(" + color + ", -10%); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 5; -fx-cursor: hand;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 5;"));
    }

    private void showAddStudentDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add Student");
        dialog.setHeaderText("Enter Student Details");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField gradeField = new TextField();
        TextField sectionField = new TextField();
        TextField emailField = new TextField();
        TextField phoneField = new TextField();

        grid.add(new Label("Student ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Name:"), 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(new Label("Grade:"), 0, 2);
        grid.add(gradeField, 1, 2);
        grid.add(new Label("Section:"), 0, 3);
        grid.add(sectionField, 1, 3);
        grid.add(new Label("Email:"), 0, 4);
        grid.add(emailField, 1, 4);
        grid.add(new Label("Phone:"), 0, 5);
        grid.add(phoneField, 1, 5);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    Student student = new Student(id, nameField.getText(), gradeField.getText(), 
                                                  sectionField.getText(), emailField.getText(), phoneField.getText());
                    studentService.addStudent(student);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Student added successfully!");
                } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
                }
            }
        });
    }

    private void showAddTeacherDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add Teacher");
        dialog.setHeaderText("Enter Teacher Details");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField deptField = new TextField();
        TextField subjectField = new TextField();
        TextField emailField = new TextField();
        TextField phoneField = new TextField();

        grid.add(new Label("Teacher ID:"), 0, 0);
        grid.add(idField, 1, 0);
        grid.add(new Label("Name:"), 0, 1);
        grid.add(nameField, 1, 1);
        grid.add(new Label("Department:"), 0, 2);
        grid.add(deptField, 1, 2);
        grid.add(new Label("Subject Code:"), 0, 3);
        grid.add(subjectField, 1, 3);
        grid.add(new Label("Email:"), 0, 4);
        grid.add(emailField, 1, 4);
        grid.add(new Label("Phone:"), 0, 5);
        grid.add(phoneField, 1, 5);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    Teacher teacher = new Teacher(id, nameField.getText(), deptField.getText(),
                                                  subjectField.getText(), emailField.getText(), phoneField.getText());
                    teacherService.addTeacher(teacher);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Teacher added successfully!");
                } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
                }
            }
        });
    }

    private void showAddSubjectDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Add Subject");
        dialog.setHeaderText("Enter Subject Details");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        TextField codeField = new TextField();
        TextField nameField = new TextField();

        grid.add(new Label("Subject Code:"), 0, 0);
        grid.add(codeField, 1, 0);
        grid.add(new Label("Subject Name:"), 0, 1);
        grid.add(nameField, 1, 1);

        dialog.getDialogPane().setContent(grid);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    subjectService.addSubject(codeField.getText(), nameField.getText());
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Subject added successfully!");
                } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, "Error", e.getMessage());
                }
            }
        });
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}