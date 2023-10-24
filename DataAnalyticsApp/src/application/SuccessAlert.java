package application;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class SuccessAlert {

    public static void show(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}
