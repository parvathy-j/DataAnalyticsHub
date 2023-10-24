package application;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class bulkImportPosts {
    public static void bulkImportPosts() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        File csvFile = fileChooser.showOpenDialog(new Stage());

        if (csvFile != null) {
            try {
                FileReader fileReader = new FileReader(csvFile);
                BufferedReader bufferedReader = new BufferedReader(fileReader);


                String line;
                boolean isFirstRow = true; // Flag to skip the first row

                while ((line = bufferedReader.readLine()) != null) {
                	 if (isFirstRow) {
                         isFirstRow = false;
                         continue; // Skip the first row
                     }
                    // Process each line of the CSV file
                    String[] data = line.split(",");

                    if (data.length == 6) {
                        // Assuming the CSV file has the following columns:
                        int id = Integer.parseInt(data[0]);
                        String content = data[1];
                        String author = data[2];
                        int likes = Integer.parseInt(data[3]);
                        int shares = Integer.parseInt(data[4]);
                        String dateTime = data[5];
                        if (PostsDatabase.postExists(id)) {
                            // Handle the case where the post with the same ID already exists
                            System.err.println("Post with ID " + id + " already exists. Skipping.");
                            ErrorAlert.show("Bulk Import Failed,Post Exists");
                        } else {
                            // Insert data into PostsDatabase
                            PostsDatabase.insertPost(id, content, author, likes, shares, dateTime);Alert alert = new Alert(AlertType.INFORMATION);
                         // From any other class
                            SuccessAlert.show("Bulk Import Sucessful");
                        }
                    }
                }

                bufferedReader.close();
                // Provide feedback to the user about the successful import
                
            } catch (IOException e) {
                e.printStackTrace();
                // Handle exceptions that may occur during import, e.g., incorrect file format
                System.err.println("Error during bulk import: " + e.getMessage());
            }
        }
    }
}
