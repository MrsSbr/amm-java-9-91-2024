package ru.vsu.amm.java.dbManage;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.sql.*;

import ru.vsu.amm.java.entities.Post;
import ru.vsu.amm.java.entities.User;

import static ru.vsu.amm.java.service.Logg.logger;

public class DbManager {

    private static final String DB_URL = "jdbc:postgresql://localhost:5434/";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "12345";

    public User getUserById(UUID id) {
        User user = null;
        String sql = "SELECT \"ID\", Username, Email, \"Password\", Created_at FROM Users WHERE \"ID\" = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setObject(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setId(UUID.fromString(rs.getString("ID")));
                    user.setUsername(rs.getString("Username"));
                    user.setEmail(rs.getString("Email"));
                    user.setPassword(rs.getString("Password"));
                    user.setCreatedAt(rs.getTime("Created_at").toLocalTime());
                }
            }

        } catch (SQLException e) {
            logger.warning("Error getting user by ID: " + e.getMessage());
            e.printStackTrace();
        }

        return user;
    }


    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT \"ID\", Username, Email, \"Password\", Created_at FROM Users";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User();
                user.setId(UUID.fromString(rs.getString("ID")));
                user.setUsername(rs.getString("Username"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("Password"));
                user.setCreatedAt(rs.getTime("Created_at").toLocalTime());
                users.add(user);
            }

        } catch (SQLException e) {
            logger.warning("Error getting all users: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }


    public UUID createUser(User user) {
        UUID newId = UUID.randomUUID();
        String sql = "INSERT INTO Users (\"ID\", Username, Email, \"Password\") VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setObject(1, newId);
            pstmt.setString(2, user.getUsername());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getPassword());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("User created successfully.");
                return newId;
            } else {
                logger.warning("Failed to create user.");
                return null;
            }

        } catch (SQLException e) {
            System.err.println("Error creating user: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateUser(User user) {
        String sql = "UPDATE Users SET Username = ?, Email = ?, \"Password\" = ? WHERE \"ID\" = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPassword());
            pstmt.setObject(4, user.getId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            logger.warning("Error updating user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    public boolean deleteUser(UUID id) {
        String sql = "DELETE FROM Users WHERE \"ID\" = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setObject(1, id);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {

            logger.warning("Error deleting user: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Post getPostById(UUID id) {
        Post post = null;
        String sql = "SELECT \"ID\", User_ID, \"Content\", Created_at FROM Posts WHERE ID = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setObject(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    post = new Post();
                    post.setId(UUID.fromString(rs.getString("ID")));
                    post.setUserId(UUID.fromString(rs.getString("User_ID")));
                    post.setContent(rs.getString("Content"));
                    post.setCreatedAt(rs.getTime("Created_at").toLocalTime()); // Преобразование Time в LocalTime
                }
            }

        } catch (SQLException e) {
            logger.warning("Error getting post by ID: " + e.getMessage());
            e.printStackTrace(); // Вывод полного стека исключения для отладки
        }

        return post;
    }

    public List<Post> getAllPosts() {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT \"ID\", User_ID, \"Content\", Created_at FROM Posts";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Post post = new Post();
                post.setId(UUID.fromString(rs.getString("ID")));
                post.setUserId(UUID.fromString(rs.getString("User_ID")));
                post.setContent(rs.getString("Content"));
                post.setCreatedAt(rs.getTime("Created_at").toLocalTime()); // Преобразование Time в LocalTime
                posts.add(post);
            }

        } catch (SQLException e) {
            logger.warning("Error getting all posts: " + e.getMessage());
            e.printStackTrace();
        }

        return posts;
    }

    public UUID createPost(Post post) {
        UUID newId = UUID.randomUUID();
        String sql = "INSERT INTO Posts (\"ID\", User_ID, \"Content\") VALUES (?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setObject(1, newId);
            pstmt.setObject(2, post.getUserId());
            pstmt.setString(3, post.getContent());

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                logger.info("Post created successfully.");
                return newId;
            } else {
                logger.warning("Failed to create post.");
                return null;
            }

        } catch (SQLException e) {
            logger.warning("Error creating post: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public boolean updatePost(Post post) {
        String sql = "UPDATE Posts SET User_ID = ?, \"Content\" = ? WHERE \"ID\" = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setObject(1, post.getUserId());
            pstmt.setString(2, post.getContent());
            pstmt.setObject(3, post.getId());

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            logger.warning("Error updating post: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    public boolean deletePost(UUID id) {
        String sql = "DELETE FROM Posts WHERE \"ID\"= ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setObject(1, id);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            logger.warning("Error deleting post: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    private Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.warning("PostgreSQL JDBC driver not found.");
            e.printStackTrace();
            throw new SQLException("PostgreSQL JDBC driver not found.", e);
        }
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
