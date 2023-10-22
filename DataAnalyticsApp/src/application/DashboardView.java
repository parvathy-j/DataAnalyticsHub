package application;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class DashboardView {
    private String fullName;
    private GridPane root;

    public DashboardView(String fullName) {
        this.fullName = fullName;
        root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(10, 10, 10, 10));

        Label titleLabel = new Label("Data Analytics Hub - Dashboard");
        titleLabel.setFont(Font.font("Arial", 20));
        titleLabel.setId("title-label");

        Label welcomeLabel = new Label("Welcome, " + fullName + "!");
        welcomeLabel.setFont(Font.font("Arial", 16));

        Button analyzeButton = new Button("Analyze Social Media Posts");

        titleLabel.setStyle("-fx-font-size: 20px;");
        titleLabel.setId("title-label");
        welcomeLabel.setStyle("-fx-font-size: 16px;");
        Button editProfileButton = new Button("Edit Profile");
        editProfileButton.setOnAction(event -> openEditProfileView());
        Button addPostButton = new Button("Add Social Media Post");
        Button retrievePostButton = new Button("Retrieve Post by ID");
        Button removePostButton = new Button("Remove Post by ID");
        Button retrieveTopPostsButton = new Button("Retrieve Top N Posts");
        Button exportPostButton = new Button("Export Post to File");
        GridPane.setConstraints(titleLabel, 0, 0, 2, 1);
        GridPane.setConstraints(welcomeLabel, 0, 1);
     // Add the buttons to the layout
        GridPane.setConstraints(editProfileButton, 0, 2);
        GridPane.setConstraints(addPostButton, 0, 3);
        GridPane.setConstraints(retrievePostButton, 0, 4);
        GridPane.setConstraints(removePostButton, 0, 5);
        GridPane.setConstraints(retrieveTopPostsButton, 0, 6);
        GridPane.setConstraints(exportPostButton, 0, 7);

        root.getChildren().addAll(titleLabel, welcomeLabel, editProfileButton, addPostButton,
            retrievePostButton, removePostButton, retrieveTopPostsButton, exportPostButton);
    }
    private void openEditProfileView() {
        EditProfileView editProfileView = new EditProfileView();
        Scene editProfileScene = new Scene(editProfileView.getView());
        Stage editProfileStage = new Stage();
        editProfileStage.setScene(editProfileScene);
        editProfileStage.setTitle("Edit Profile");
        editProfileStage.show();
    }

    public Parent getView() {
        return root;
    }
}
