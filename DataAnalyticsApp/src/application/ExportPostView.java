package application;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExportPostView {
    private GridPane root;
    private TextField postIdField;
    private TextField fileNameField;
    private Button exportButton;

    public ExportPostView() {
        root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(10, 10, 10, 10));

        Label titleLabel = new Label("Export Post to CSV");
        Label postIdLabel = new Label("Post ID:");
        postIdField = new TextField();
        Label fileNameLabel = new Label("File Name:");
        fileNameField = new TextField();
        exportButton = new Button("Export Post");

        GridPane.setConstraints(titleLabel, 0, 0, 2, 1);
        GridPane.setConstraints(postIdLabel, 0, 1);
        GridPane.setConstraints(postIdField, 1, 1);
        GridPane.setConstraints(fileNameLabel, 0, 2);
        GridPane.setConstraints(fileNameField, 1, 2);
        GridPane.setConstraints(exportButton, 1, 3);

        exportButton.setOnAction(event -> exportPostToCSV());

        root.getChildren().addAll(titleLabel, postIdLabel, postIdField, fileNameLabel, fileNameField, exportButton);
    }

    public Parent getView() {
        return root;
    }

    private void exportPostToCSV() {
        try {
            int postId = Integer.parseInt(postIdField.getText());
            String filePath = fileNameField.getText();

            // Retrieve the post details based on the post ID (you can use PostsDatabase)
            SocialMediaPost post = PostsDatabase.getPostById(postId);

            if (post != null) {
                // Create and write the CSV content
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                	writer.write("Content\tAuthor\tLikes\tShares\tDate/Time\n");
                    writer.write(String.format("%s\t%s\t%d\t%d\t%s\n",
                            post.getContent(), post.getAuthor(), post.getLikes(), post.getShares(), post.getDateTime()));
                    
                } catch (IOException e) {
                    // Handle the case where file writing fails
                    e.printStackTrace();
                }

                showSuccessAlert("Post exported successfully");
            } else {
                // Handle the case where the post with the specified ID is not found
                showFailureAlert("Post not found");
            }
        } catch (NumberFormatException e) {
            // Handle the case where the user enters an invalid post ID
            showFailureAlert("Invalid post ID");
        }
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showFailureAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
