package application;

import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RetrieveTopPostView {
    private static GridPane root;
    private TextField numberOfPostsField;
    private TextArea postDetailsLabel;


    public RetrieveTopPostView() {
        root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(10, 10, 10, 10));

        Label titleLabel = new Label("Retrieve Top Posts by Likes");

        numberOfPostsField = new TextField();
        numberOfPostsField.setPromptText("Enter the number of posts to retrieve");
        postDetailsLabel = new TextArea(); // Initialize postDetailsLabel
        postDetailsLabel.setEditable(false); // Make it non-editable
        

        Button retrieveTopPostsButton = new Button("Retrieve Top Posts");
        retrieveTopPostsButton.setOnAction(event -> retrieveTopPostsByLikes());

        GridPane.setConstraints(titleLabel, 0, 0, 2, 1);
        GridPane.setConstraints(numberOfPostsField, 0, 1);
        GridPane.setConstraints(retrieveTopPostsButton, 0, 2);

        root.getChildren().addAll(titleLabel, numberOfPostsField, retrieveTopPostsButton,postDetailsLabel);
    }

    public static Parent getView() {
        return root;
    }

    private void retrieveTopPostsByLikes() {
        try {
            int numberOfPosts = Integer.parseInt(numberOfPostsField.getText());

            // Call the method to retrieve the top N posts by likes
            List<SocialMediaPost> topPosts;
			
				topPosts = PostsDatabase.getTopPostsByLikes(numberOfPosts);


            if (topPosts.isEmpty()) {
            	ErrorAlert.show("No posts found");
            } else {
            	postDetailsLabel.clear(); // Clear previous entries before adding new ones
            	int number = 1;
            for (SocialMediaPost post : topPosts) {
                postDetailsLabel.appendText("Post: " + number + "\nContent: " + post.getContent() + "\nAuthor: " + post.getAuthor() +
                        "\nLikes: " + post.getLikes() + "\nShares: " + post.getShares() + "\nDate/Time: " + post.getDateTime() + "\n\n");
                number++;
            }
            }
        } catch (NumberFormatException e) {
        	ErrorAlert.show("Invalid input. Please enter a valid post ID.");

        }
    }

}
