package application;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AddSocialMediaPostView {
    private GridPane root;
    private TextField idField;
    private TextArea contentField; 
    private TextField authorField;
    private TextField likesField;
    private TextField sharesField;
    private TextField dateTimeField;

    public AddSocialMediaPostView() {
        root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(10, 10, 10, 10));

        Label titleLabel = new Label("Add Social Media Post");
        titleLabel.setFont(Font.font("Arial", 20));
        titleLabel.setId("title-label");

        Label idLabel = new Label("ID:");
        idField = new TextField();
        idField.setPromptText("Enter post ID");

        Label contentLabel = new Label("Content:");
        contentField = new TextArea();
        contentField.setPromptText("Enter post content");

        Label authorLabel = new Label("Author:");
        authorField = new TextField();
        authorField.setPromptText("Enter post author");

        Label likesLabel = new Label("Likes:");
        likesField = new TextField();
        likesField.setPromptText("Enter the number of likes");

        Label sharesLabel = new Label("Shares:");
        sharesField = new TextField();
        sharesField.setPromptText("Enter the number of shares");

        Label dateTimeLabel = new Label("Date & Time:");
        dateTimeField = new TextField();
        dateTimeField.setPromptText("Enter date & time");

        Button addPostButton = new Button("Add Post");
        addPostButton.setOnAction(event -> addSocialMediaPost());

        titleLabel.setStyle("-fx-font-size: 20px;");
        titleLabel.setId("title-label");
        // Set positions and constraints for UI elements
        GridPane.setConstraints(titleLabel, 0, 0, 2, 1);
        GridPane.setConstraints(idLabel, 0, 1);
        GridPane.setConstraints(idField, 1, 1);
        GridPane.setConstraints(contentLabel, 0, 2);
        GridPane.setConstraints(contentField, 1, 2);
        GridPane.setConstraints(authorLabel, 0, 3);
        GridPane.setConstraints(authorField, 1, 3);
        GridPane.setConstraints(likesLabel, 0, 4);
        GridPane.setConstraints(likesField, 1, 4);
        GridPane.setConstraints(sharesLabel, 0, 5);
        GridPane.setConstraints(sharesField, 1, 5);
        GridPane.setConstraints(dateTimeLabel, 0, 6);
        GridPane.setConstraints(dateTimeField, 1, 6);
        GridPane.setConstraints(addPostButton, 1, 7);

        root.getChildren().addAll(titleLabel, idLabel, idField, contentLabel, contentField, authorLabel, authorField,
                likesLabel, likesField, sharesLabel, sharesField, dateTimeLabel, dateTimeField, addPostButton);
    }

    public Parent getView() {
        return root;
    }

    // Implement the method to add a social media post
    private void addSocialMediaPost() {
        try {
            // Get the values from the text fields
            int id = Integer.parseInt(idField.getText());
            PostsDatabase postsDatabase = new PostsDatabase();
            if (PostsDatabase.getPostById(id) != null) {
                ErrorAlert.show("A post with the same ID already exists. Please choose a different ID.");
                return; // Exit the method
            }
            String content = contentField.getText();
            String author = authorField.getText();
            int likes = Integer.parseInt(likesField.getText());
            int shares = Integer.parseInt(sharesField.getText());
            String dateTime = dateTimeField.getText();
            

            // Create a SocialMediaPost instance
            SocialMediaPost post = new SocialMediaPost(id, content, author, likes, shares, dateTime);
         // Add the post to the database
            postsDatabase.addSocialMediaPost(post);

            // Clear the input fields for the next post
            idField.clear();
            contentField.clear();
            authorField.clear();
            likesField.clear();
            sharesField.clear();
            dateTimeField.clear();
            SuccessAlert.show("Post added successfully");

        } catch (NumberFormatException e) {
            // Handles the case where id, likes, or shares are not valid integers
            ErrorAlert.show("Invalid input. ID,Likes and shares must be valid integers.");
        }
    }
    
    
 
}
