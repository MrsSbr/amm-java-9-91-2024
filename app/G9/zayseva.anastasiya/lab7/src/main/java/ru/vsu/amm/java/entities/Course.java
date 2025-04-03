package ru.vsu.amm.java.entities;

import java.util.Objects;

public class Course {
    private Long courseId;
    private String title;
    private String description;

    public Course() {
    }

    public Course(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Course(Long courseId, String title, String description) {
        this.courseId = courseId;
        this.title = title;
        this.description = description;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(courseId, course.courseId) &&
                Objects.equals(title, course.title) &&
                Objects.equals(description, course.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, title, description);
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseId=" + courseId +
                ", title=' " + title + '\'' +
                ", description=' " + description + '\'' +
                '}';
    }
}
