package ru.vsu.amm.java.repository;

import ru.vsu.amm.java.connection.DataSourceProvider;
import ru.vsu.amm.java.entities.UserCourse;
import ru.vsu.amm.java.enams.EnrollmentStatus;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserCourseRepository implements CrudRepository<UserCourse> {
    private static final Logger logger = Logger.getLogger(UserCourseRepository.class.getName());

    @Override
    public UserCourse getById(long id) {
        UserCourse userCourse = null;
        final String sql = "SELECT UserCourseId, UserId, CourseId, SubscriptionDate, EnrollmentStatus FROM UsersCourses WHERE UserCourseId = ?";

        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                userCourse = new UserCourse();
                userCourse.setUserCourseId(rs.getLong("UserCourseId"));
                userCourse.setUserId(rs.getLong("UserId"));
                userCourse.setCourseId(rs.getLong("CourseId"));
                userCourse.setSubscriptionDate(rs.getDate("SubscriptionDate").toLocalDate());
                userCourse.setEnrollmentStatus(EnrollmentStatus.valueOf(rs.getString("EnrollmentStatus")));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return userCourse;
    }

    @Override
    public List<UserCourse> getAll() {
        List<UserCourse> userCourses = new ArrayList<>();
        final String sql = "SELECT UserCourseId, UserId, CourseId, SubscriptionDate, EnrollmentStatus FROM UsersCourses";

        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                UserCourse userCourse = new UserCourse();
                userCourse.setUserCourseId(rs.getLong("UserCourseId"));
                userCourse.setUserId(rs.getLong("UserId"));
                userCourse.setCourseId(rs.getLong("CourseId"));
                userCourse.setSubscriptionDate(rs.getDate("SubscriptionDate").toLocalDate());
                userCourse.setEnrollmentStatus(EnrollmentStatus.valueOf(rs.getString("EnrollmentStatus")));
                userCourses.add(userCourse);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return userCourses;
    }

    @Override
    public void save(UserCourse userCourse) {
        String sql = "INSERT INTO UsersCourses (UserId, CourseId, SubscriptionDate, EnrollmentStatus) VALUES (?, ?, ?, ?)";

        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setLong(1, userCourse.getUserId());
            ps.setLong(2, userCourse.getCourseId());
            ps.setDate(3, Date.valueOf(userCourse.getSubscriptionDate()));
            ps.setString(4, userCourse.getEnrollmentStatus().name());
            ps.executeUpdate();

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    userCourse.setUserCourseId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(UserCourse userCourse) {
        String sql = "UPDATE UsersCourses SET EnrollmentStatus = ? WHERE UserCourseId = ?";

        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, userCourse.getEnrollmentStatus().name());
            ps.setLong(2, userCourse.getUserCourseId());
            ps.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    @Override
    public void delete(long id) {
        String sql = "DELETE FROM UsersCourses WHERE UserCourseId = ?";

        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public List<UserCourse> findByUserId(long userId) {
        List<UserCourse> userCourses = new ArrayList<>();
        final String sql = "SELECT UserCourseId, UserId, CourseId, SubscriptionDate, EnrollmentStatus FROM UsersCourses WHERE UserId = ?";

        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                UserCourse userCourse = new UserCourse();
                userCourse.setUserCourseId(rs.getLong("UserCourseId"));
                userCourse.setUserId(rs.getLong("UserId"));
                userCourse.setCourseId(rs.getLong("CourseId"));
                userCourse.setSubscriptionDate(rs.getDate("SubscriptionDate").toLocalDate());
                userCourse.setEnrollmentStatus(EnrollmentStatus.valueOf(rs.getString("EnrollmentStatus")));
                userCourses.add(userCourse);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return userCourses;
    }

    public List<UserCourse> findByCourseId(long courseId) {
        List<UserCourse> userCourses = new ArrayList<>();
        final String sql = "SELECT UserCourseId, UserId, CourseId, SubscriptionDate, EnrollmentStatus FROM UsersCourses WHERE CourseId = ?";

        try (Connection conn = DataSourceProvider.getDataSource().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, courseId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                UserCourse userCourse = new UserCourse();
                userCourse.setUserCourseId(rs.getLong("UserCourseId"));
                userCourse.setUserId(rs.getLong("UserId"));
                userCourse.setCourseId(rs.getLong("CourseId"));
                userCourse.setSubscriptionDate(rs.getDate("SubscriptionDate").toLocalDate());
                userCourse.setEnrollmentStatus(EnrollmentStatus.valueOf(rs.getString("EnrollmentStatus")));
                userCourses.add(userCourse);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        return userCourses;
    }
}