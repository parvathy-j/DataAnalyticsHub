package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        String insertPostSQL = "INSERT INTO posts (content, author, likes, shares, date_time) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(insertPostSQL)) {
            preparedStatement.setString(1, post.getContent());
            preparedStatement.setString(2, post.getAuthor());
            preparedStatement.setInt(3, post.getLikes());
            preparedStatement.setInt(4, post.getShares());
            preparedStatement.setString(5, post.getDateTime());

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
                System.out.println("Post with ID " + postId + " has been removed.");
            } else {
                // No post found with the given ID
                System.out.println("Post with ID " + postId + " not found.");
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
    
}
