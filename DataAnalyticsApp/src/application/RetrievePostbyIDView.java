package application;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RetrievePostbyIDView {
    private GridPane root;
    private TextField postIdField;
    private TextArea postDetailsLabel;

    public RetrievePostbyIDView() {
        root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(10, 10, 10, 10));

        Label titleLabel = new Label("Retrieve Post by ID");

        postIdField = new TextField();
        postIdField.setPromptText("Enter post ID");

        postDetailsLabel = new TextArea(); // Initialize postDetailsLabel
        postDetailsLabel.setEditable(false); // Make it non-editable
        
        
        Button retrievePostButton = new Button("Retrieve Post");
        retrievePostButton.setOnAction(event -> retrievePostById());

        GridPane.setConstraints(titleLabel, 0, 0, 2, 1);
        GridPane.setConstraints(postIdField, 0, 1);
        GridPane.setConstraints(postDetailsLabel, 0, 2);
        GridPane.setConstraints(retrievePostButton, 0, 4);

        root.getChildren().addAll(titleLabel, postIdField, postDetailsLabel, retrievePostButton);
    }

    public Parent getView() {
        return root;
    }

    private void retrievePostById() {
        try {
            int postId = Integer.parseInt(postIdField.getText());

            // Retrieve and display the post based on the post ID
            SocialMediaPost post = PostsDatabase.getPostById(postId);

            if (post != null) {
                postDetailsLabel.setText("Content: " + post.getContent() + "\nAuthor: " + post.getAuthor() +
                        "\nLikes: " + post.getLikes() + "\nShares: " + post.getShares() + "\nDate/Time: " + post.getDateTime());

                // Set the post content
            } else {
                postDetailsLabel.setText("Post not found.");
          }
        } catch (NumberFormatException e) {
            postDetailsLabel.setText("Invalid post ID.");
        }
    }
}
