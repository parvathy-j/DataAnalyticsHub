package application;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDatabase {
    private static final String DATABASE_URL = "jdbc:sqlite:user_database.db";

    public UserDatabase() {
        // Create the user database and initialize it if it doesn't exist
        initializeDatabase();
    }

    public boolean checkUserCredentials(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // If a row is returned, the user exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }}
   




    public void initializeDatabase() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS users (username TEXT PRIMARY KEY, password TEXT, first_name TEXT, last_name TEXT)";
        String createPostsTableSQL = "CREATE TABLE IF NOT EXISTS posts (id INTEGER PRIMARY KEY, content TEXT, author TEXT, likes INTEGER, shares INTEGER, date_time TEXT)";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement userTableStatement = connection.prepareStatement(createTableSQL);
             PreparedStatement postsTableStatement = connection.prepareStatement(createPostsTableSQL)) {
            userTableStatement.executeUpdate();
            postsTableStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void registerUser(String username, String password,String firstName,String lastName) {
        String insertSQL = "INSERT INTO users (username, password,first_name,last_name) VALUES (?, ?,?,?)";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, firstName);
            preparedStatement.setString(4,lastName);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public boolean updateUserProfile(String newFirstName, String newLastName, String newUsername, String newPassword) {
        String updateSQL = "UPDATE users SET username = ?, password = ?, first_name = ?, last_name = ? ";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

            preparedStatement.setString(1, newUsername);
            preparedStatement.setString(2, newPassword);
            preparedStatement.setString(3, newFirstName);
            preparedStatement.setString(4, newLastName);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0; // If one or more rows were updated, consider it a success
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Update failed
        }
    }
}
