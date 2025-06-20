--CREATE DATABASE 'Planner-app';

CREATE TABLE IF NOT EXISTS "users" (
"UserID" UUID NOT NULL,
"Name" VARCHAR(30) NOT NULL,
"Email" VARCHAR(320) NOT NULL,
"Password" TEXT NOT NULL,
CONSTRAINT "PK_User" PRIMARY KEY("UserID"),
CONSTRAINT "UNIQUE_User_Name" UNIQUE("Name"),
CONSTRAINT "UNIQUE_User_Email" UNIQUE("Email"),
);

CREATE TABLE IF NOT EXISTS "boards" (
"BoardID" UUID NOT NULL,
"UserID" UUID NOT NULL,
"BoardTitle" TEXT NOT NULL,
"BoardDescription" TEXT,
CONSTRAINT "PK_Board" PRIMARY KEY("BoardID"),
CONSTRAINT "FK_BoardToUser" FOREIGN KEY("UserID") REFERENCES "users"("UserID")
);

CREATE TABLE IF NOT EXISTS "columns" (
"ColumnID" UUID NOT NULL,
"BoardID" UUID NOT NULL,
"ColumnTitle" TEXT NOT NULL,
CONSTRAINT "PK_Column" PRIMARY KEY("ColumnID"),
CONSTRAINT "FK_ColumnToBoard" FOREIGN KEY("BoardID") REFERENCES "boards"("BoardID") ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS "tasks" (
"TaskID" UUID NOT NULL,
"ColumnID" UUID NOT NULL,
"TaskTitle" TEXT NOT NULL,
"TaskDescription" TEXT,
"StartDate" DATE,
"EndDate" DATE,
CONSTRAINT "PK_Task" PRIMARY KEY("TaskID"),
CONSTRAINT "FK_TaskToColumn" FOREIGN KEY("ColumnID") REFERENCES "columns"("ColumnID") ON DELETE CASCADE
);


CREATE INDEX "search_users_email" ON "users" USING HASH("Email");

CREATE INDEX "search_boards_user" ON "boards"("UserID");

CREATE INDEX "search_tasks_column" ON "tasks"("ColumnID");
 