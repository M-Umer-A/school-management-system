package com.school.gui;

import com.school.model.Student;
import com.school.services.StudentService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class StudentDashboard {

    private Stage stage;
    private int studentId;
    private StudentService studentService = new StudentService();

    public StudentDashboard(Stage stage, int studentId) {
        this.stage = stage;
        this.studentId = studentId;
    }

    public void show() {
        Student student = studentService.getStudentById(studentId);

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f7fa;");

        // Top Bar
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(15, 20, 15, 20));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-background-color: #ed8936;");

        Label titleLabel = new Label("👨‍🎓 STUDENT DASHBOARD");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        titleLabel.setTextFill(Color.WHITE);

        Label nameLabel = new Label(student != null ? "Welcome, " + student.getName() : "Welcome");
        nameLabel.setFont(Font.font("Arial", 14));
        nameLabel.setTextFill(Color.WHITE);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button logoutButton = new Button("Logout");
        logoutButton.setStyle(
                "-fx-background-color: white; -fx-text-fill: #ed8936; -fx-font-weight: bold; -fx-padding: 8 20; -fx-background-radius: 5;");
        logoutButton.setOnAction(e -> new SchoolManagementGUI().start(stage));

        topBar.getChildren().addAll(titleLabel, nameLabel, spacer, logoutButton);

        // Left Menu
        VBox leftMenu = new VBox(10);
        leftMenu.setPadding(new Insets(20));
        leftMenu.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-width: 0 1 0 0;");
        leftMenu.setPrefWidth(200);

        Button profileBtn = createMenuButton("👤 My Profile");
        Button attendanceBtn = createMenuButton("📊 Attendance");
        Button gradesBtn = createMenuButton("📈 Grades");
        Button notesBtn = createMenuButton("📚 Lecture Notes");
        Button subjectsBtn = createMenuButton("📖 Subjects");

        leftMenu.getChildren().addAll(profileBtn, attendanceBtn, gradesBtn, notesBtn, subjectsBtn);

        // Center Content
        StackPane centerContent = new StackPane();
        centerContent.setPadding(new Insets(20));

        VBox welcomeScreen = createWelcomeScreen(student);
        centerContent.getChildren().add(welcomeScreen);

        profileBtn.setOnAction(e -> {
            centerContent.getChildren().clear();
            centerContent.getChildren().add(createProfileView(student));
        });

        attendanceBtn.setOnAction(e -> {
            centerContent.getChildren().clear();
            centerContent.getChildren().add(createAttendanceView());
        });

        gradesBtn.setOnAction(e -> {
            centerContent.getChildren().clear();
            centerContent.getChildren().add(createGradesView());
        });

        notesBtn.setOnAction(e -> {
            centerContent.getChildren().clear();
            centerContent.getChildren().add(createNotesView());
        });

        subjectsBtn.setOnAction(e -> {
            centerContent.getChildren().clear();
            centerContent.getChildren().add(createSubjectsView());
        });

        root.setTop(topBar);
        root.setLeft(leftMenu);
        root.setCenter(centerContent);

        Scene scene = new Scene(root, 1200, 700);
        stage.setScene(scene);
        stage.show();
    }

    private Button createMenuButton(String text) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: #333; -fx-font-size: 14; -fx-padding: 12; -fx-background-radius: 5;");
        btn.setOnMouseEntered(e -> btn.setStyle(
                "-fx-background-color: #f0f0f0; -fx-text-fill: #ed8936; -fx-font-size: 14; -fx-padding: 12; -fx-background-radius: 5; -fx-cursor: hand; -fx-font-weight: bold;"));
        btn.setOnMouseExited(e -> btn.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: #333; -fx-font-size: 14; -fx-padding: 12; -fx-background-radius: 5;"));
        return btn;
    }

    private VBox createWelcomeScreen(Student student) {
        VBox welcome = new VBox(20);
        welcome.setAlignment(Pos.CENTER);

        Label welcomeLabel = new Label("Welcome, " + (student != null ? student.getName() : "Student") + "!");
        welcomeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        welcomeLabel.setTextFill(Color.web("#ed8936"));

        Label infoLabel = new Label("Select an option from the left menu");
        infoLabel.setFont(Font.font("Arial", 16));
        infoLabel.setTextFill(Color.GRAY);

        welcome.getChildren().addAll(welcomeLabel, infoLabel);
        return welcome;
    }

    private VBox createProfileView(Student student) {
        VBox container = new VBox(20);
        container.setPadding(new Insets(20));
        container.setAlignment(Pos.TOP_LEFT);

        Label title = new Label("👤 My Profile");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        if (student != null) {
            VBox profileCard = new VBox(10);
            profileCard.setPadding(new Insets(20));
            profileCard.setStyle(
                    "-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 2);");
            profileCard.setMaxWidth(500);

            Label idLabel = new Label("Student ID: " + student.getStudentId());
            idLabel.setFont(Font.font("Arial", 14));

            Label nameLabel = new Label("Name: " + student.getName());
            nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));

            Label gradeLabel = new Label("Grade: " + student.getGrade() + " - " + student.getSection());
            gradeLabel.setFont(Font.font("Arial", 14));

            Label emailLabel = new Label("Email: " + student.getEmail());
            emailLabel.setFont(Font.font("Arial", 14));

            Label phoneLabel = new Label("Phone: " + student.getPhone());
            phoneLabel.setFont(Font.font("Arial", 14));

            profileCard.getChildren().addAll(nameLabel, idLabel, gradeLabel, emailLabel, phoneLabel);
            container.getChildren().addAll(title, profileCard);
        } else {
            Label errorLabel = new Label("Profile not found");
            errorLabel.setTextFill(Color.RED);
            container.getChildren().addAll(title, errorLabel);
        }

        return container;
    }

    private VBox createAttendanceView() {
        VBox container = new VBox(15);
        container.setPadding(new Insets(10));

        Label title = new Label("📊 My Attendance");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        Label info = new Label("View your attendance records and percentage");
        info.setTextFill(Color.GRAY);

        container.getChildren().addAll(title, info);
        return container;
    }

    private VBox createGradesView() {
        VBox container = new VBox(15);
        container.setPadding(new Insets(10));

        Label title = new Label("📈 My Grades");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        Label info = new Label("View your grades across all subjects");
        info.setTextFill(Color.GRAY);

        container.getChildren().addAll(title, info);
        return container;
    }

    private VBox createNotesView() {
        VBox container = new VBox(15);
        container.setPadding(new Insets(10));

        Label title = new Label("📚 Lecture Notes");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        Label info = new Label("Access lecture notes shared by your teachers");
        info.setTextFill(Color.GRAY);

        container.getChildren().addAll(title, info);
        return container;
    }

    private VBox createSubjectsView() {
        VBox container = new VBox(15);
        container.setPadding(new Insets(10));

        Label title = new Label("📖 Subjects");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        Label info = new Label("View all available subjects");
        info.setTextFill(Color.GRAY);

        container.getChildren().addAll(title, info);
        return container;
    }
}