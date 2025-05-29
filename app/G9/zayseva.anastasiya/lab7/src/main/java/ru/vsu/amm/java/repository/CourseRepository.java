package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.connection.DataSourceProvider;
import ru.vsu.amm.java.entities.Course;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CourseRepository implements CrudRepository<Course> {
    private static final Logger logger = Logger.getLogger(CourseRepository.class.getName());

    @Override
    public Course getById(long id) {
        Course course = null;
        final String sql = "SELECT CourseId, Title, Description FROM Courses WHERE CourseId = ?";

        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                course = new Course();
                course.setCourseId(rs.getLong("CourseId"));
                course.setTitle(rs.getString("Title"));
                course.setDescription(rs.getString("Description"));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return course;
    }

    @Override
    public List<Course> getAll() {
        List<Course> courses = new ArrayList<>();
        final String sql = "SELECT CourseId, Title, Description FROM Courses";

        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Course course = new Course();
                course.setCourseId(rs.getLong("CourseId"));
                course.setTitle(rs.getString("Title"));
                course.setDescription(rs.getString("Description"));
                courses.add(course);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return courses;
    }

    @Override
    public void save(Course course) {
        String sql = "INSERT INTO Courses (Title, Description) VALUES (?, ?)";

        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, course.getTitle());
            ps.setString(2, course.getDescription());
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    course.setCourseId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Course course) {
        String sql = "UPDATE Courses SET Title = ?, Description = ? WHERE CourseId = ?";

        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, course.getTitle());
            ps.setString(2, course.getDescription());
            ps.setLong(3, course.getCourseId());
            ps.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM Courses WHERE CourseId = ?";

        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }
}