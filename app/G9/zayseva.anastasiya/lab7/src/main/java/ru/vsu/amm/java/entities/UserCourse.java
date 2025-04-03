package ru.vsu.amm.java.entities;

import ru.vsu.amm.java.enams.EnrollmentStatus;

import java.time.LocalDate;

public class UserCourse {
    private Long userCourseId;
    private Long userId;
    private Long courseId;
    private LocalDate subscriptionDate;
    private EnrollmentStatus enrollmentStatus = EnrollmentStatus.ACTIVE;

    public UserCourse() {
    }

    public UserCourse(Long userId, Long courseId, LocalDate subscriptionDate) {
        this.userId = userId;
        this.courseId = courseId;
        this.subscriptionDate = subscriptionDate;
    }

    public Long getUserCourseId() {
        return userCourseId;
    }

    public void setUserCourseId(Long userCourseId) {
        this.userCourseId = userCourseId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public LocalDate getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(LocalDate subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public EnrollmentStatus getEnrollmentStatus() {
        return enrollmentStatus;
    }

    public void setEnrollmentStatus(EnrollmentStatus enrollmentStatus) {
        this.enrollmentStatus = enrollmentStatus;
    }

    public void completeCourse() {
        this.enrollmentStatus = EnrollmentStatus.COMPLETED;
    }

    public void cancelEnrollment() {
        this.enrollmentStatus = EnrollmentStatus.CANCELLED;
    }

    @Override
    public String toString() {
        return "UserCourse{" +
                "userCourseId=" + courseId +
                ", userId=" + userId +
                ", courseId=" + courseId +
                ", subscriptionDate=" + subscriptionDate +
                ", enrollmentStatus=" + enrollmentStatus +
                '}';
    }
}
