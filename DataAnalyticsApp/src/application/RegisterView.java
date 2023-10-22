package application;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class RegisterView {
    private app app;
    private TextField usernameField;
    private PasswordField passwordField;
    private TextField firstNameField;
    private TextField lastNameField;
    private GridPane root;

    public void showRegisterView() {
        root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(10, 10, 10, 10));

        Label titleLabel = new Label("Data Analytics Hub");
        titleLabel.setFont(Font.font("Arial", 20));
        titleLabel.setId("title-label");

        Label usernameLabel = new Label("Username:");
        usernameField = new TextField();
        usernameField.setPromptText("Enter your username");

        Label passwordLabel = new Label("Password:");
        passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");

        Label firstNameLabel = new Label("First Name:");
        firstNameField = new TextField();
        firstNameField.setPromptText("Enter your first name");

        Label lastNameLabel = new Label("Last Name:");
        lastNameField = new TextField();
        lastNameField.setPromptText("Enter your last name");

        Button registerButton = new Button("Register");
        registerButton.setOnAction(event -> registerUser());
        registerButton.getStyleClass().add("register-button");

        titleLabel.setStyle("-fx-font-size: 20px;");
        titleLabel.setId("title-label");

        usernameLabel.setStyle("-fx-font-size: 14px;");
        usernameField.setStyle("-fx-font-size: 14px;");
        passwordLabel.setStyle("-fx-font-size: 14px;");
        passwordField.setStyle("-fx-font-size: 14px;");
        firstNameLabel.setStyle("-fx-font-size: 14px;");
        firstNameField.setStyle("-fx-font-size: 14px;");
        lastNameLabel.setStyle("-fx-font-size: 14px;");
        lastNameField.setStyle("-fx-font-size: 14px;");
        registerButton.setStyle("-fx-font-size: 14px;");

        GridPane.setConstraints(titleLabel, 0, 0, 2, 1);
        GridPane.setConstraints(usernameLabel, 0, 1);
        GridPane.setConstraints(usernameField, 1, 1);
        GridPane.setConstraints(passwordLabel, 0, 2);
        GridPane.setConstraints(passwordField, 1, 2);
        GridPane.setConstraints(firstNameLabel, 0, 3);
        GridPane.setConstraints(firstNameField, 1, 3);
        GridPane.setConstraints(lastNameLabel, 0, 4);
        GridPane.setConstraints(lastNameField, 1, 4);
        GridPane.setConstraints(registerButton, 1, 5);

        root.getChildren().addAll(titleLabel, usernameLabel, usernameField, passwordLabel, passwordField,
                firstNameLabel, firstNameField, lastNameLabel, lastNameField, registerButton);
    }

    public Parent getView() {
        return root;
    }

    public void setApp(app app) {
        this.app = app;
    }

    private void registerUser() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();

        if (!username.isEmpty() && !password.isEmpty()) {
            UserDatabase userDB = new UserDatabase();
            userDB.registerUser(username, password, firstName, lastName);
            System.out.println("Registration successfull");
            closeRegistrationScreen();
            
            

        } else {
            // Handle case when one or more fields are empty
            app.showError("Please fill out all the fields.");
        }
    }
    private void closeRegistrationScreen() {
        // Add code to close the registration screen, e.g., close the registration stage
        Stage stage = (Stage) usernameField.getScene().getWindow();
        stage.close();
    }
}
