package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.chart.PieChart;

public class UserDatabase {
	private static final String DATABASE_URL = "jdbc:sqlite:user_database.db";

	public UserDatabase() {
		// Create the user database and initialize it if it doesn't exist
		initializeDatabase();
	}

	public void initializeDatabase() {
		String createTableSQL = "CREATE TABLE IF NOT EXISTS users (username TEXT UNIQUE NOT NULL, password TEXT, first_name TEXT, last_name TEXT,vip INTEGER DEFAULT 0)";
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

	public void upgradeToVIP(String username) {
		try (Connection connection = DriverManager.getConnection(DATABASE_URL)) {
			String sql = "UPDATE users SET vip = 1 WHERE username = ?";
			try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
				preparedStatement.setString(1, username);
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void registerUser(String username, String password, String firstName, String lastName) {
		String insertSQL = "INSERT INTO users (username, password,first_name,last_name) VALUES (?, ?,?,?)";
		try (Connection connection = DriverManager.getConnection(DATABASE_URL);
				PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			preparedStatement.setString(3, firstName);
			preparedStatement.setString(4, lastName);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean updateUserProfile(String oldUsername, String newPassword,String newFirstName, String newLastName, String newUsername) {
		String updateSQL = "UPDATE users SET username = ?, password = ?, first_name = ?, last_name = ? WHERE username =? ";
		try (Connection connection = DriverManager.getConnection(DATABASE_URL);
				PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {

			preparedStatement.setString(1, newUsername);
			preparedStatement.setString(2, newPassword);
			preparedStatement.setString(3, newFirstName);
			preparedStatement.setString(4, newLastName);
			preparedStatement.setString(5, oldUsername);

			int rowsAffected = preparedStatement.executeUpdate();

			return rowsAffected > 0; // If one or more rows were updated, consider it a success
		} catch (SQLException e) {
			e.printStackTrace();
			return false; // Update failed
		}
	}

	public int isUserVIP(String username) {
		int vipStatus = 0; // Default value, e.g., -1 indicates an error or non-existent user.
		try (Connection connection = DriverManager.getConnection(DATABASE_URL);
				PreparedStatement preparedStatement = connection
						.prepareStatement("SELECT * FROM users WHERE username = ?")) {
			preparedStatement.setString(1, username);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					vipStatus = resultSet.getInt("vip");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// Handle the exception appropriately, e.g., logging or throwing a custom
			// exception.
		}
		return vipStatus;
	}

	public boolean isUsernameTaken(String username) {
		String selectSQL = "SELECT * FROM users WHERE username = ?";
		try (Connection connection = DriverManager.getConnection(DATABASE_URL);
				PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
			preparedStatement.setString(1, username);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				return resultSet.next(); // Returns true if the username is found
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false; // Return false if an exception occurs (e.g., database connection issue)
	}

	    public List<Integer> fetchShareCounts() {
	                List<Integer> shareCounts = new ArrayList<>();

	                try (Connection connection = DriverManager.getConnection(DATABASE_URL);
	                     PreparedStatement preparedStatement = connection.prepareStatement("SELECT shares FROM posts")) {
	                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                        while (resultSet.next()) {
	                            int shareCount = resultSet.getInt("shares");
	                            shareCounts.add(shareCount);
	                        }
	                    }
	                } catch (SQLException e) {
	                    e.printStackTrace();
	                }

	                return shareCounts;
	            }

	            public List<PieChart.Data> categorizeShareCounts(List<Integer> shareCounts) {
	                List<PieChart.Data> categorizedData = new ArrayList<>();
	                int count0to99 = 0;
	                int count100to999 = 0;
	                int count1000Plus = 0;

	                for (int shareCount : shareCounts) {
	                    if (shareCount >= 0 && shareCount <= 99) {
	                        count0to99++;
	                    } else if (shareCount >= 100 && shareCount <= 999) {
	                        count100to999++;
	                    } else if (shareCount >= 1000) {
	                        count1000Plus++;
	                    }
	                }

	                categorizedData.add(new PieChart.Data("0-99 Shares", count0to99));
	                categorizedData.add(new PieChart.Data("100-999 Shares", count100to999));
	                categorizedData.add(new PieChart.Data("1000+ Shares", count1000Plus));

	                return categorizedData;
	            }
	            
	}



