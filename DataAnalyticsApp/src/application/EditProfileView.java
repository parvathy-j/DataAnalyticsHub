package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

public class EditProfileView {
    private GridPane root;

    public EditProfileView() {
        root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(10, 10, 10, 10));

        Label titleLabel = new Label("Edit Profile");
        titleLabel.setFont(Font.font("Arial", 20));
        titleLabel.setId("title-label");

        Label firstNameLabel = new Label("First Name:");
        TextField firstNameField = new TextField();
        firstNameField.setPromptText("Enter your new first name");

        Label lastNameLabel = new Label("Last Name:");
        TextField lastNameField = new TextField();
        lastNameField.setPromptText("Enter your new last name");

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your new username");

        Label passwordLabel = new Label("Password:");
        TextField passwordField = new TextField();
        passwordField.setPromptText("Enter your new password");

        Button updateButton = new Button("update");
        updateButton.setOnAction(event -> updateProfileChanges(firstNameField.getText(), lastNameField.getText(), usernameField.getText(), passwordField.getText()));

        titleLabel.setStyle("-fx-font-size: 20px;");
        titleLabel.setId("title-label");

        // Style labels, text fields, and the save button

        GridPane.setConstraints(titleLabel, 0, 0, 2, 1);
        GridPane.setConstraints(firstNameLabel, 0, 1);
        GridPane.setConstraints(firstNameField, 1, 1);
        GridPane.setConstraints(lastNameLabel, 0, 2);
        GridPane.setConstraints(lastNameField, 1, 2);
        GridPane.setConstraints(usernameLabel, 0, 3);
        GridPane.setConstraints(usernameField, 1, 3);
        GridPane.setConstraints(passwordLabel, 0, 4);
        GridPane.setConstraints(passwordField, 1, 4);
        GridPane.setConstraints(updateButton, 1, 5);

        root.getChildren().addAll(titleLabel, firstNameLabel, firstNameField, lastNameLabel, lastNameField, usernameLabel, usernameField, passwordLabel, passwordField, updateButton);
    }

    public Parent getView() {
        return root;
    }

    private void updateProfileChanges(String newFirstName, String newLastName, String newUsername, String newPassword) {
        UserDatabase userDB = new UserDatabase();
        boolean success = userDB.updateUserProfile(newUsername, newPassword, newFirstName, newLastName);

        if (success) {
            // Provide feedback to the user about the success
        	System.out.println("Profile updated successfully.");

        } else {
            // Handle the case when the profile update fails
        }
    }
}
