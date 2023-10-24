package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.event.ActionEvent;

import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class loginController {
    @FXML
    private TextField usernameField;
    
    @FXML
    private PasswordField passwordField;
    private static final String DATABASE_URL = "jdbc:sqlite:user_database.db";

    // This method is called when the user clicks the login button
    public void loginUser() throws SQLException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        
        if (!username.isEmpty() && !password.isEmpty()) {
            checkUserCredentials(username, password);
            
			
        } else {
            // Handle case when one or both fields are empty
            showError("Please enter both username and password.");
        }
    }
    public void checkUserCredentials(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
            	
            	if (resultSet.next()) {
            		String firstname=resultSet.getString("first_name");
                	String lastname=resultSet.getString("last_name");
                	String fullname=firstname+" " +lastname;
    			    // User authentication successful, you can navigate to the dashboard view
    			    // For now, let's just show a success message
    				usernameField.clear();
    	            passwordField.clear();
    			    showSuccess("Login Successful");
    			    app.showDashboard(fullname,username);
    			} else {
    			    // Handle authentication failure (wrong username or password)
    				usernameField.clear();
    	            passwordField.clear();
    			    showError("Invalid username or password.");
    			}
                 // If a row is returned, the user exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
           
        }}

    public void openRegisterScreen(ActionEvent event) {
    	 RegisterView registerView = new RegisterView();
    	 registerView.showRegisterView(); // Call this method to initialize the root
         Scene registerScene = new Scene(registerView.getView());
         Stage registerStage = new Stage();
         registerStage.setScene(registerScene);
         registerStage.show();
    }
    // Show an error message using an alert dialog
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Show a success message using an alert dialog
    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
