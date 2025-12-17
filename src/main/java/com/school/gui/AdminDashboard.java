package com.school.gui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class AdminDashboard {

    private Stage stage;

    public AdminDashboard(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f7fa;");

        // ===== TOP BAR =====
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(15));
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setStyle("-fx-background-color: #2d3748;");

        Label title = new Label("👨‍💼 ADMIN DASHBOARD");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        title.setTextFill(Color.WHITE);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> new SchoolManagementGUI().start(stage));

        topBar.getChildren().addAll(title, spacer, logoutBtn);

        // ===== LEFT MENU =====
        VBox leftMenu = new VBox(10);
        leftMenu.setPadding(new Insets(20));
        leftMenu.setPrefWidth(220);
        leftMenu.setStyle("-fx-background-color: white;");

        Button studentsBtn = createMenuButton("🎓 Students");
        Button teachersBtn = createMenuButton("👩‍🏫 Teachers");
        Button subjectsBtn = createMenuButton("📚 Subjects");
        Button usersBtn = createMenuButton("🔐 Users");
        Button reportsBtn = createMenuButton("📊 Reports");

        leftMenu.getChildren().addAll(studentsBtn, teachersBtn, subjectsBtn, usersBtn, reportsBtn);

        // ===== CENTER =====
        StackPane center = new StackPane();
        center.setPadding(new Insets(20));
        center.getChildren().add(new Label("Welcome Admin"));

        root.setTop(topBar);
        root.setLeft(leftMenu);
        root.setCenter(center);

        stage.setScene(new Scene(root, 1200, 700));
        stage.show();
    }

    private Button createMenuButton(String text) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setStyle("-fx-background-color: transparent; -fx-padding: 12;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #edf2f7; -fx-font-weight: bold;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent;"));
        return btn;
    }
}
