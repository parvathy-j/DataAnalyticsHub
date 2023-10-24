package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class app extends Application {
    private Stage primaryStage;
    private app app; // Reference to the main application

//j
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        UserDatabase userDB = new UserDatabase();
        userDB.initializeDatabase();
        // Load the LoginView.fxml
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginView.fxml"));
            Parent root = loader.load();

            
           

            Scene scene = new Scene(root, 400, 200);
            primaryStage.setTitle("Data Analytics Hub");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void registerUser(String username, String password,String lastName,String firstName) {
        // Implement your registration logic here, e.g., adding the user to the database
        UserDatabase userDB = new UserDatabase();
        userDB.registerUser(username, password,lastName,firstName);

        
        showSuccess("Registration successful. You can now log in.");
    }

    public void showSuccess(String message) {
        showMessage("Success", message, AlertType.INFORMATION);
    }

    public void showError(String message) {
        showMessage("Error", message, AlertType.ERROR);
    }

    private void showMessage(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public void showLoginView() throws IOException {
        // Create the LoginView
    	// Load the LoginView.fxml
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginView.fxml"));
        Parent root = loader.load();

     // Create a Scene for the LoginView
        Scene loginScene = new Scene(root);

        // Create a Stage for the LoginView
        Stage loginStage = new Stage();
        loginStage.setScene(loginScene);
        loginStage.setTitle("Login"); // Set the title for the login stage

        // Show the LoginView
        loginStage.show();
    }
    public static void showDashboard(String fullName,String username) {
        // Create a dashboard view
        DashboardView dashboardView = new DashboardView(fullName,username);

        // Create a Scene for the dashboard view
        Scene dashboardScene = new Scene(dashboardView.getView());

        // Create a Stage for the dashboard view
        Stage dashboardStage = new Stage();
        dashboardStage.setScene(dashboardScene);
        dashboardStage.setTitle("Dashboard"); // Set the title for the dashboard

        // Show the dashboard view
        dashboardStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
	public static void showRegisterView() {
		    RegisterView registerView = new RegisterView();
		    Scene registerScene = new Scene(registerView.getView());
		    Stage registerStage = new Stage();
		    registerStage.setScene(registerScene);
		    registerStage.show();
		}		
	
	}
