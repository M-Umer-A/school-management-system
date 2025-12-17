package com.school.gui;

import com.school.model.User;
import com.school.services.AuthService;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SchoolManagementGUI extends Application {

    private Stage primaryStage;
    private AuthService authService = new AuthService();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("School Management System");
        
        // Create default admin
        authService.createDefaultAdmin();
        
        showLoginScreen();
    }

    private void showLoginScreen() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(40));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #667eea 0%, #764ba2 100%);");

        // Title
        Label titleLabel = new Label("🏫 SCHOOL MANAGEMENT SYSTEM");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        titleLabel.setTextFill(Color.WHITE);

        Label subtitleLabel = new Label("Welcome! Please login to continue");
        subtitleLabel.setFont(Font.font("Arial", 14));
        subtitleLabel.setTextFill(Color.LIGHTGRAY);

        // Login Form Card
        VBox loginCard = new VBox(15);
        loginCard.setPadding(new Insets(30));
        loginCard.setMaxWidth(400);
        loginCard.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 5);");

        Label loginTitle = new Label("Login");
        loginTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setStyle("-fx-font-size: 14; -fx-padding: 10;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setStyle("-fx-font-size: 14; -fx-padding: 10;");

        Label messageLabel = new Label();
        messageLabel.setTextFill(Color.RED);
        messageLabel.setWrapText(true);

        Button loginButton = new Button("LOGIN");
        loginButton.setMaxWidth(Double.MAX_VALUE);
        loginButton.setStyle("-fx-background-color: #667eea; -fx-text-fill: white; -fx-font-size: 14; -fx-font-weight: bold; -fx-padding: 12; -fx-background-radius: 5;");
        loginButton.setOnMouseEntered(e -> loginButton.setStyle("-fx-background-color: #5568d3; -fx-text-fill: white; -fx-font-size: 14; -fx-font-weight: bold; -fx-padding: 12; -fx-background-radius: 5; -fx-cursor: hand;"));
        loginButton.setOnMouseExited(e -> loginButton.setStyle("-fx-background-color: #667eea; -fx-text-fill: white; -fx-font-size: 14; -fx-font-weight: bold; -fx-padding: 12; -fx-background-radius: 5;"));

        loginButton.setOnAction(e -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText();

            if (username.isEmpty() || password.isEmpty()) {
                messageLabel.setText("❌ Please enter both username and password");
                return;
            }

            User user = authService.login(username, password);

            if (user == null) {
                messageLabel.setText("❌ Invalid username or password!");
                passwordField.clear();
            } else {
                messageLabel.setTextFill(Color.GREEN);
                messageLabel.setText("✅ Login successful!");
                
                // Open appropriate dashboard based on role
                switch (user.getRole()) {
                    case "ADMIN" -> new AdminDashboard(primaryStage).show();
                    case "TEACHER" -> new TeacherDashboard(primaryStage, user.getReferenceId()).show();
                    case "STUDENT" -> new StudentDashboard(primaryStage, user.getReferenceId()).show();
                }
            }
        });

        // Default credentials hint
        Label hintLabel = new Label("💡 Default Admin: username: admin | password: admin123");
        hintLabel.setFont(Font.font("Arial", 11));
        hintLabel.setTextFill(Color.GRAY);
        hintLabel.setWrapText(true);
        hintLabel.setAlignment(Pos.CENTER);

        loginCard.getChildren().addAll(loginTitle, usernameField, passwordField, messageLabel, loginButton, hintLabel);
        root.getChildren().addAll(titleLabel, subtitleLabel, loginCard);

        Scene scene = new Scene(root, 600, 550);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Enter key to login
        passwordField.setOnAction(e -> loginButton.fire());
    }
}