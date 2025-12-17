package com.school.gui;

import com.school.model.Teacher;
import com.school.services.TeacherService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class TeacherDashboard {

    private Stage stage;
    private int teacherId;
    private TeacherService teacherService = new TeacherService();

    public TeacherDashboard(Stage stage, int teacherId) {
        this.stage = stage;
        this.teacherId = teacherId;
    }

    public void show() {
        Teacher teacher = teacherService.getTeacherById(teacherId);

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f7fa;");

        // ===== TOP BAR =====
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(15, 20, 15, 20));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-background-color: #3182ce;");

        Label titleLabel = new Label("👩‍🏫 TEACHER DASHBOARD");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        titleLabel.setTextFill(Color.WHITE);

        Label nameLabel = new Label(teacher != null ? "Welcome, " + teacher.getName() : "Welcome");
        nameLabel.setTextFill(Color.WHITE);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button logoutBtn = new Button("Logout");
        logoutBtn.setStyle("-fx-background-color: white; -fx-text-fill: #3182ce; -fx-font-weight: bold;");
        logoutBtn.setOnAction(e -> new SchoolManagementGUI().start(stage));

        topBar.getChildren().addAll(titleLabel, nameLabel, spacer, logoutBtn);

        // ===== LEFT MENU =====
        VBox leftMenu = new VBox(10);
        leftMenu.setPadding(new Insets(20));
        leftMenu.setPrefWidth(220);
        leftMenu.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0;");

        Button profileBtn = createMenuButton("👤 My Profile");
        Button studentsBtn = createMenuButton("🎓 My Students");
        Button attendanceBtn = createMenuButton("📊 Attendance");
        Button gradesBtn = createMenuButton("📝 Manage Grades");
        Button subjectsBtn = createMenuButton("📚 Subjects");

        leftMenu.getChildren().addAll(profileBtn, studentsBtn, attendanceBtn, gradesBtn, subjectsBtn);

        // ===== CENTER CONTENT =====
        StackPane center = new StackPane();
        center.setPadding(new Insets(20));
        center.getChildren().add(createWelcomeScreen(teacher));

        profileBtn.setOnAction(e -> center.getChildren().setAll(createProfileView(teacher)));
        studentsBtn.setOnAction(e -> center.getChildren().setAll(createStudentsView()));
        attendanceBtn.setOnAction(e -> center.getChildren().setAll(createAttendanceView()));
        gradesBtn.setOnAction(e -> center.getChildren().setAll(createGradesView()));
        subjectsBtn.setOnAction(e -> center.getChildren().setAll(createSubjectsView()));

        root.setTop(topBar);
        root.setLeft(leftMenu);
        root.setCenter(center);

        stage.setScene(new Scene(root, 1200, 700));
        stage.show();
    }

    // ===== COMMON UI METHODS =====
    private Button createMenuButton(String text) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setStyle("-fx-background-color: transparent; -fx-padding: 12;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #ebf8ff; -fx-font-weight: bold;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent;"));
        return btn;
    }

    private VBox createWelcomeScreen(Teacher teacher) {
        VBox box = new VBox(15);
        box.setAlignment(Pos.CENTER);

        Label welcome = new Label("Welcome, " + (teacher != null ? teacher.getName() : "Teacher"));
        welcome.setFont(Font.font("Arial", FontWeight.BOLD, 30));
        welcome.setTextFill(Color.web("#3182ce"));

        box.getChildren().add(welcome);
        return box;
    }

    private VBox createProfileView(Teacher teacher) {
        VBox box = new VBox(10);
        box.getChildren().add(new Label("👤 Teacher Profile"));
        return box;
    }

    private VBox createStudentsView() {
        return new VBox(new Label("🎓 Manage Students"));
    }

    private VBox createAttendanceView() {
        return new VBox(new Label("📊 Attendance Management"));
    }

    private VBox createGradesView() {
        return new VBox(new Label("📝 Grade Management"));
    }

    private VBox createSubjectsView() {
        return new VBox(new Label("📚 Assigned Subjects"));
    }
}
