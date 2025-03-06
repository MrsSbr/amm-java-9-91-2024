CREATE INDEX User_ID_Ix1 ON Posts (User_ID);
CREATE INDEX User_ID_Ix2 ON "Comments"(User_ID);
CREATE INDEX Post_ID_Ix1 ON "Comments"(Post_ID);
CREATE INDEX Post_ID_Ix2 ON Likes(Post_ID);
CREATE INDEX User_ID_Ix3 ON Likes(Post_ID);