DROP TABLE Comments;
DROP TABLE Likes;
DROP TABLE Posts;
DROP TABLE Users;


CREATE TABLE Users (
    ID UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    Username VARCHAR(50) NOT NULL,
    Email VARCHAR(100) NOT NULL,
    Password VARCHAR(255) NOT NULL,
    Created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Posts (
    ID UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    User_ID UUID,
    Content TEXT NOT NULL,
    Created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE Posts
ADD CONSTRAINT User_ID_fk 
FOREIGN KEY (User_ID)
REFERENCES Users (ID);

CREATE TABLE 'Comments' (
    'ID' UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    Post_ID UUID,
    User_ID UUID,
    Text TEXT NOT NULL,
    Created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE 'Comments'
ADD CONSTRAINT Post_ID_fk
FOREIGN KEY (Post_ID)
REFERENCES Posts (ID);

ALTER TABLE 'Comments'
ADD CONSTRAINT User_ID_fk
FOREIGN KEY (User_ID)
REFERENCES Users (ID);

CREATE TABLE Likes (
    ID UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    Post_ID UUID,
    User_ID UUID
);

ALTER TABLE Likes
ADD CONSTRAINT Post_ID_fk
FOREIGN KEY (Post_ID)
REFERENCES Posts ('ID');

ALTER TABLE Likes
ADD CONSTRAINT User_ID_fk
FOREIGN KEY (User_ID)
REFERENCES Users ('ID');
