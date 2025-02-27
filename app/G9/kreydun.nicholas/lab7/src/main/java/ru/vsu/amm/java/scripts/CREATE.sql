CREATE TABLE Users (
    "ID" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    Username VARCHAR(50) NOT NULL,
    Email VARCHAR(100) NOT NULL,
    "Password" VARCHAR(255) NOT NULL,
    Created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE Posts (
    "ID" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    User_ID UUID,
    "Content" TEXT NOT NULL,
    Created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE "Comments" (
    "ID" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    Post_ID UUID,
    User_ID UUID,
    Text TEXT NOT NULL,
    Created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE Likes (
    "ID" UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    Post_ID UUID,
    User_ID UUID
);

CREATE TABLE Loggers (
    "Event" TEXT
);