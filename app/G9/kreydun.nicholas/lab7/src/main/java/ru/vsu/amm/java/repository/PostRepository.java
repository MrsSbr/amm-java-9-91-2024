package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.dbManage.DbConnection;
import ru.vsu.amm.java.entities.Post;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static ru.vsu.amm.java.services.Logg.logger;

public class PostRepository implements Repository<Post> {

    @Override
    public Post getById(UUID id) {
        Post post = null;
        String sql = "SELECT \"ID\", User_ID, \"Content\", Created_at FROM Posts WHERE \"ID\" = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setObject(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    post = new Post();
                    post.setId(UUID.fromString(rs.getString("ID")));
                    post.setUserId(UUID.fromString(rs.getString("User_ID")));
                    post.setContent(rs.getString("Content"));
                    post.setCreatedAt(rs.getTime("Created_at").toLocalTime());
                }
            }
        } catch (SQLException e) {
            logger.warning("Error getting post by ID: " + e.getMessage());
        }
        return post;
    }

    @Override
    public List<Post> getAll() {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT \"ID\", User_ID, \"Content\", Created_at FROM Posts";

        try (Connection conn = DbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Post post = new Post();
                post.setId(UUID.fromString(rs.getString("ID")));
                post.setUserId(UUID.fromString(rs.getString("User_ID")));
                post.setContent(rs.getString("Content"));
                post.setCreatedAt(rs.getTime("Created_at").toLocalTime());
                posts.add(post);
            }

        } catch (SQLException e) {
            logger.warning("Error getting all posts: " + e.getMessage());
        }
        return posts;
    }

    @Override
    public UUID create(Post post) {
        UUID newId = post.getId();
        String sql = "INSERT INTO Posts (\"ID\", User_ID, \"Content\") VALUES (?, ?, ?)";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setObject(1, newId);
            pstmt.setObject(2, post.getUserId());
            pstmt.setString(3, post.getContent());

            return pstmt.executeUpdate() > 0 ? newId : null;

        } catch (SQLException e) {
            logger.warning("Error creating post: " + e.getMessage());
            return null;
        }
    }

    @Override
    public boolean update(Post post) {
        String sql = "UPDATE Posts SET \"Content\" = ? WHERE \"ID\" = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, post.getContent());
            pstmt.setObject(2, post.getId());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.warning("Error updating post: " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(UUID id) {
        String sql = "DELETE FROM Posts WHERE \"ID\"= ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setObject(1, id);

            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;

        } catch (SQLException e) {
            logger.warning("Error deleting post: " + e.getMessage());
            return false;
        }
    }

    public List<Post> getPostsByUserId(UUID userId) {
        List<Post> posts = new ArrayList<>();
        String sql = "SELECT \"ID\", \"Content\", user_Id FROM Posts WHERE user_Id = ?";

        try (Connection conn = DbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setObject(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                UUID postId = (UUID) rs.getObject("ID");
                String content = rs.getString("Content");
                UUID ownerId = (UUID) rs.getObject("user_Id");

                Post post = new Post();
                post.setContent(content);
                post.setUserId(userId);
                post.setId(postId);

                posts.add(post);
            }

        } catch (SQLException e) {
            logger.warning("Error fetching posts for user: " + e.getMessage());
        }
        return posts;
    }

}
