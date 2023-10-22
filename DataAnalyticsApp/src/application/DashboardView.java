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
        addPostButton.setOnAction(event -> addSocialMediaPost());
        Button retrievePostButton = new Button("Retrieve Post by ID");
        retrievePostButton.setOnAction(event->retrievePost());

        Button removePostButton = new Button("Remove Post by ID");
        removePostButton.setOnAction(event->removePost());
        
        Button retrieveTopPostsButton = new Button("Retrieve Top N Posts");
        retrieveTopPostsButton.setOnAction(event->retrieveTopPost());

        Button exportPostButton = new Button("Export Post to File");
        exportPostButton.setOnAction(event->exportPostToCSV());
        
        Button logOutButton = new Button("Log Out");
        logOutButton.setOnAction(event->logOut()); 

        GridPane.setConstraints(titleLabel, 0, 0, 2, 1);
        GridPane.setConstraints(welcomeLabel, 0, 1);
     // Add the buttons to the layout
        GridPane.setConstraints(editProfileButton, 0, 2);
        GridPane.setConstraints(addPostButton, 0, 3);
        GridPane.setConstraints(retrievePostButton, 0, 4);
        GridPane.setConstraints(removePostButton, 0, 5);
        GridPane.setConstraints(retrieveTopPostsButton, 0, 6);
        GridPane.setConstraints(exportPostButton, 0, 7);
        GridPane.setConstraints(logOutButton, 0, 8);


        root.getChildren().addAll(titleLabel, welcomeLabel, editProfileButton, addPostButton,
            retrievePostButton, removePostButton, retrieveTopPostsButton, exportPostButton,logOutButton);
    }
  
    
	private void logOut() {
		Stage stage = (Stage) root.getScene().getWindow();
	    stage.close();
	}


	private void retrieveTopPost() {
    	RetrieveTopPostView removePostView = new RetrieveTopPostView();
        Stage retrieveTopPostStage = new Stage();
        retrieveTopPostStage.setScene(new Scene(RetrieveTopPostView.getView()));
        retrieveTopPostStage.setTitle("Retrieve Post by ID");
        retrieveTopPostStage.show();
	}

	private void removePost() {
    	RemovePostByIDView removePostView = new RemovePostByIDView();
        Stage removePostStage = new Stage();
        removePostStage.setScene(new Scene(removePostView.getView()));
        removePostStage.setTitle("Retrieve Post by ID");
        removePostStage.show();
		
	}

	private void retrievePost() {
        RetrievePostbyIDView retrievePostView = new RetrievePostbyIDView();
        Stage retrievePostStage = new Stage();
        retrievePostStage.setScene(new Scene(retrievePostView.getView()));
        retrievePostStage.setTitle("Retrieve Post by ID");
        retrievePostStage.show();
    }

	private void addSocialMediaPost() {
    	// Create an instance of the AddSocialMediaPostView
        AddSocialMediaPostView addPostView = new AddSocialMediaPostView();
        
        // Create a new stage for displaying the AddSocialMediaPostView
        Stage stage = new Stage();
        stage.setTitle("Add Social Media Post");
        stage.setScene(new Scene(addPostView.getView()));
        
        // Show the AddSocialMediaPostView
        stage.show();
	}
	private void openEditProfileView() {
        EditProfileView editProfileView = new EditProfileView();
        Scene editProfileScene = new Scene(editProfileView.getView());
        Stage editProfileStage = new Stage();
        editProfileStage.setScene(editProfileScene);
        editProfileStage.setTitle("Edit Profile");
        editProfileStage.show();
    }
	private void exportPostToCSV() {
        // Assume postId and filePath are provided by the user
        int postId = 1;  // Replace with actual post ID
        String filePath = "path/to/your/exported_post.csv";  // Replace with user-selected file path

        ExportPostView exportPostView = new ExportPostView();
        Scene exportPostScene = new Scene(exportPostView.getView());
        Stage exportPostStage = new Stage();
        exportPostStage.setScene(exportPostScene);
        exportPostStage.setTitle("Edit Profile");
        exportPostStage.show();

	}
    public Parent getView() {
        return root;
    }
}
