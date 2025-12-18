package com.school.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class AdminDashboard {

    public void show() {

        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f7fa;");

        // ===== TOP BAR =====
        HBox topBar = new HBox(15);
        topBar.setPadding(new Insets(15, 20, 15, 20));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-background-color: #2d3748;");

        Label title = new Label("👨‍💼 ADMIN DASHBOARD");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        title.setTextFill(Color.WHITE);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button logoutBtn = new Button("Logout");
        logoutBtn.setStyle(
                "-fx-background-color: white; -fx-text-fill: #2d3748; -fx-font-weight: bold; -fx-padding: 8 20;");
        logoutBtn.setOnAction(e -> new SchoolManagementGUI().showLoginScreen());

        topBar.getChildren().addAll(title, spacer, logoutBtn);

        // ===== LEFT MENU =====
        VBox leftMenu = new VBox(10);
        leftMenu.setPadding(new Insets(20));
        leftMenu.setPrefWidth(220);
        leftMenu.setStyle("-fx-background-color: white; -fx-border-color: #e0e0e0; -fx-border-width: 0 1 0 0;");

        Button studentsBtn = createMenuButton("🎓 Students");
        Button teachersBtn = createMenuButton("👩‍🏫 Teachers");
        Button subjectsBtn = createMenuButton("📚 Subjects");
        Button usersBtn = createMenuButton("🔐 Users");
        Button reportsBtn = createMenuButton("📊 Reports");

        leftMenu.getChildren().addAll(studentsBtn, teachersBtn, subjectsBtn, usersBtn, reportsBtn);

        // ===== CENTER CONTENT =====
        StackPane center = new StackPane();
        center.setPadding(new Insets(20));

        Label welcome = new Label("Welcome, Admin 👋");
        welcome.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        welcome.setTextFill(Color.web("#2d3748"));
        center.getChildren().add(welcome);

        // ===== BUTTON ACTIONS =====
        studentsBtn.setOnAction(e -> {
            center.getChildren().clear();
            center.getChildren().add(new Label("📋 Manage Students"));
        });

        teachersBtn.setOnAction(e -> {
            center.getChildren().clear();
            center.getChildren().add(new Label("📋 Manage Teachers"));
        });

        subjectsBtn.setOnAction(e -> {
            center.getChildren().clear();
            center.getChildren().add(new Label("📋 Manage Subjects"));
        });

        usersBtn.setOnAction(e -> {
            center.getChildren().clear();
            center.getChildren().add(new Label("📋 Manage Users"));
        });

        reportsBtn.setOnAction(e -> {
            center.getChildren().clear();
            center.getChildren().add(new Label("📊 Reports & Analytics"));
        });

        root.setTop(topBar);
        root.setLeft(leftMenu);
        root.setCenter(center);

        Scene scene = new Scene(root, 1200, 700);
        SceneManager.setScene(scene);
    }

    private Button createMenuButton(String text) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: #333; -fx-font-size: 14; -fx-padding: 12;");
        btn.setOnMouseEntered(e -> btn.setStyle(
                "-fx-background-color: #edf2f7; -fx-text-fill: #2d3748; -fx-font-weight: bold; -fx-padding: 12; -fx-cursor: hand;"));
        btn.setOnMouseExited(e -> btn.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: #333; -fx-font-size: 14; -fx-padding: 12;"));
        return btn;
    }
}
