package application;

import javafx.scene.control.Alert;

public class ErrorAlert {
    public static void show(String message) {

	Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();

    }
}
