CREATE TABLE Users
(
    UserId      BIGSERIAL PRIMARY KEY,
    Password    TEXT NOT NULL,
    PhoneNumber TEXT NOT NULL UNIQUE,
    Email       TEXT,
    Login       TEXT NOT NULL UNIQUE,
    Username    TEXT
);

CREATE TABLE Courses
(
    CourseId    BIGSERIAL PRIMARY KEY,
    Title       TEXT NOT NULL,
    Description TEXT
);

CREATE TABLE UsersCourses
(
    UserCourseId     BIGSERIAL PRIMARY KEY,
    UserID           BIGINT NOT NULL,
    CourseID         BIGINT NOT NULL,
    SubscriptionDate DATE   NOT NULL,
    EnrollmentStatus VARCHAR(50) DEFAULT 'Active',
    CONSTRAINT FK_UsersCourses_Users FOREIGN KEY (UserId) REFERENCES Users (UserId),
    CONSTRAINT FK_UsersCourses_Courses FOREIGN KEY (CourseId) REFERENCES Courses (CourseId),
    CONSTRAINT CHK_EnrollmentStatus CHECK (EnrollmentStatus IN ('ACTIVE', 'COMPLETED', 'CANCELLED', 'PAUSED'))
);

CREATE INDEX IDX_UsersCourses_UserId ON UsersCourses (UserId);
CREATE INDEX IDX_UsersCourses_CourseId ON UsersCourses (CourseId);
CREATE INDEX IDX_UsersCourses_UserId_CourseId ON UsersCourses (UserId, CourseId);
