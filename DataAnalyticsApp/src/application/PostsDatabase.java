package application;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class PostsDatabase {
    private static final String DATABASE_URL = "jdbc:sqlite:user_database.db";

    public PostsDatabase() {
        // Initialize the posts table if it doesn't exist
        initializeDatabase();
    }

    public void initializeDatabase() {
        String createPostsTableSQL = "CREATE TABLE IF NOT EXISTS posts (id INTEGER PRIMARY KEY, content TEXT, author TEXT, likes INTEGER, shares INTEGER, date_time TEXT)";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(createPostsTableSQL)) {

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addSocialMediaPost(SocialMediaPost post) {
        String insertPostSQL = "INSERT INTO posts (id,content, author, likes, shares, date_time) VALUES (?,?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(insertPostSQL)) {
            preparedStatement.setInt(1, post.getId());
            preparedStatement.setString(2, post.getContent());
            preparedStatement.setString(3, post.getAuthor());
            preparedStatement.setInt(4, post.getLikes());
            preparedStatement.setInt(5, post.getShares());
            preparedStatement.setString(6, post.getDateTime());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static SocialMediaPost getPostById(int postId) {
        String selectPostSQL = "SELECT * FROM posts WHERE id = ?";
        
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(selectPostSQL)) {
            preparedStatement.setInt(1, postId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String content = resultSet.getString("content");
                    String author = resultSet.getString("author");
                    int likes = resultSet.getInt("likes");
                    int shares = resultSet.getInt("shares");
                    String dateTime = resultSet.getString("date_time");

                    return new SocialMediaPost(id, content, author, likes, shares, dateTime);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        // If no post was found with the given ID, return null
        return null;
    }
    public List<SocialMediaPost> getAllSocialMediaPosts() {
        List<SocialMediaPost> posts = new ArrayList<>();

        String selectAllPostsSQL = "SELECT * FROM posts";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(selectAllPostsSQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String content = resultSet.getString("content");
                String author = resultSet.getString("author");
                int likes = resultSet.getInt("likes");
                int shares = resultSet.getInt("shares");
                String dateTime = resultSet.getString("date_time");

                SocialMediaPost post = new SocialMediaPost(id, content, author, likes, shares, dateTime);
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        

        return posts;
    }
    public void removePostById(int postId) {
        String deletePostSQL = "DELETE FROM posts WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(deletePostSQL)) {
            preparedStatement.setInt(1, postId);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                // Post removed successfully
                SuccessAlert.show("Post with ID " + postId + " has been removed.");
            } else {
                // No post found with the given ID
                ErrorAlert.show("Post with ID " + postId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static List<SocialMediaPost> getTopPostsByLikes(int n) {
        List<SocialMediaPost> topPosts = new ArrayList<>();

        String selectTopPostsSQL = "SELECT * FROM posts ORDER BY likes DESC LIMIT ?";
        
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(selectTopPostsSQL)) {
            preparedStatement.setInt(1, n);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String content = resultSet.getString("content");
                    String author = resultSet.getString("author");
                    int likes = resultSet.getInt("likes");
                    int shares = resultSet.getInt("shares");
                    String dateTime = resultSet.getString("date_time");

                    SocialMediaPost post = new SocialMediaPost(id, content, author, likes, shares, dateTime);
                    topPosts.add(post);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return topPosts;
    }

    public static void insertPost(int id, String content, String author, int likes, int shares, String dateTime) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL)) {
            String sql = "INSERT INTO posts (id, content, author, likes, shares, date_time) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, content);
                preparedStatement.setString(3, author);
                preparedStatement.setInt(4, likes);
                preparedStatement.setInt(5, shares);
                preparedStatement.setString(6, dateTime);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately (e.g., logging, showing error messages).
        }}

    public static boolean postExists(int id) {
        try {
            // Create a SQL query to check if a post with the given ID exists
            String query = "SELECT COUNT(*) FROM posts WHERE id = ?";

            // Create a prepared statement
            Connection connection = DriverManager.getConnection(DATABASE_URL);
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);

            // Execute the query
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // If count > 0, a post with the same ID exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception appropriately, e.g., logging or throwing a custom exception
        }

        return false; // Return false by default if there was an error or the post doesn't exist
    }
    
}
