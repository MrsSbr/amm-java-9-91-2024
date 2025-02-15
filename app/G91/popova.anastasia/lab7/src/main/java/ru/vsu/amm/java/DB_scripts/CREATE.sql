--CREATE DATABASE 'Planner-app';

CREATE TABLE IF NOT EXISTS "users" (
"UserID" UUID NOT NULL,
"Name" VARCHAR(30) NOT NULL,
"Email" VARCHAR(320) NOT NULL,
"PasswordHash" VARCHAR(128) NOT NULL,
CONSTRAINT "PK_User" PRIMARY KEY("UserID"),
CONSTRAINT "UNIQUE_User_Name" UNIQUE("Name"),
CONSTRAINT "UNIQUE_User_Email" UNIQUE("Email"),
CONSTRAINT "CHECK_Name" CHECK("Name" ~ '^[a-zA-Z0-9._-]{3,30}$'),
CONSTRAINT "CHECK_Email" CHECK("Email" ~ '^[a-zA-Z0-9._-]+@{1}[a-zA-Z.-]+\.[a-zA-Z]{2,}$')
);

CREATE TABLE IF NOT EXISTS "boards" (
"BoardID" UUID NOT NULL,
"UserID" UUID NOT NULL,
"BoardTitle" VARCHAR(60) NOT NULL,
"BoardDescription" TEXT,
CONSTRAINT "PK_Board" PRIMARY KEY("BoardID"),
CONSTRAINT "FK_BoardToUser" FOREIGN KEY("UserID") REFERENCES "users"("UserID")
);

CREATE TABLE IF NOT EXISTS "columns" (
"ColumnID" UUID NOT NULL,
"BoardID" UUID NOT NULL,
"UserID" UUID NOT NULL,
"ColumnTitle" VARCHAR(60) NOT NULL,
CONSTRAINT "PK_Column" PRIMARY KEY("ColumnID"),
CONSTRAINT "FK_ColumnToBoard" FOREIGN KEY("BoardID") REFERENCES "boards"("BoardID"),
CONSTRAINT "FK_ColumnToUser" FOREIGN KEY("UserID") REFERENCES "users"("UserID")
);

CREATE TABLE IF NOT EXISTS "urgencies" (
"Urgency" VARCHAR(55) NOT NULL,
"UrgencyColor" VARCHAR(55) NOT NULL,
CONSTRAINT "PK_Urgency" PRIMARY KEY("Urgency"),
CONSTRAINT "UNIQUE_UrgencyColor" UNIQUE("UrgencyColor")
);

CREATE TABLE IF NOT EXISTS "tasks" (
"TaskID" UUID NOT NULL,
"ColumnID" UUID NOT NULL,
"TaskTitle" VARCHAR(65) NOT NULL,
"TaskUrgency" VARCHAR(55),
"TaskDescription" TEXT,
CONSTRAINT "PK_Task" PRIMARY KEY("TaskID"),
CONSTRAINT "FK_TaskToColumn" FOREIGN KEY("ColumnID") REFERENCES "columns"("ColumnID"),
CONSTRAINT "FK_TaskToUrgency" FOREIGN KEY("TaskUrgency") REFERENCES "urgencies"("Urgency")
);


CREATE INDEX "search_users_email" ON "users"("Email");

CREATE INDEX "search_boards_user" ON "boards"("UserID");

CREATE INDEX "search_columns_board" ON "columns"("BoardID");

CREATE INDEX "search_tasks_column" ON "tasks"("ColumnID");

CREATE INDEX "search_tasks_urgency" ON "tasks"("TaskUrgency");
 