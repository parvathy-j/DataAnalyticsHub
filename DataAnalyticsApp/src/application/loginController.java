package application;

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

    // This method is called when the user clicks the login button
    public void loginUser() throws SQLException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (!username.isEmpty() && !password.isEmpty()) {
            UserDatabase userDB = new UserDatabase();
			if (userDB.checkUserCredentials(username, password)) {
			    // User authentication successful, you can navigate to the dashboard view
			    // For now, let's just show a success message
			    showSuccess("Login Successful");
			    app.showDashboard(username);
			} else {
			    // Handle authentication failure (wrong username or password)
			    showError("Invalid username or password.");
			}
        } else {
            // Handle case when one or both fields are empty
            showError("Please enter both username and password.");
        }
    }

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
