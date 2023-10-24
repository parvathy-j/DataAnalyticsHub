package application;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RemovePostByIDView {
    private GridPane root;
    private TextField postIdField;

    public RemovePostByIDView() {
        root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(10, 10, 10, 10));

        Label titleLabel = new Label("Remove Post by ID");

        postIdField = new TextField();
        postIdField.setPromptText("Enter post ID to remove");

        Button removePostButton = new Button("Remove Post");
        removePostButton.setOnAction(event -> removePostById());

        GridPane.setConstraints(titleLabel, 0, 0, 2, 1);
        GridPane.setConstraints(postIdField, 0, 1);
        GridPane.setConstraints(removePostButton, 0, 2);

        root.getChildren().addAll(titleLabel, postIdField, removePostButton);
    }

    public Parent getView() {
        return root;
    }

    private void removePostById() {
        try {
            int postId = Integer.parseInt(postIdField.getText());

            // Call the removePostById method from PostsDatabase to remove the post
            PostsDatabase postsDatabase = new PostsDatabase();
            postsDatabase.removePostById(postId);

            //  display a success message or handle the case when the post is not found
        } catch (NumberFormatException e) {
        	ErrorAlert.show("Invalid input. Please enter a valid post ID.");
            // Handling the case where the user enters an invalid post ID
        }
    }
}
