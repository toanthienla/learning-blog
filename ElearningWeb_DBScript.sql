USE [master]
GO

/*******************************************************************************
   Drop database if it exists
********************************************************************************/
IF EXISTS (SELECT name FROM master.dbo.sysdatabases WHERE name = N'ElearningWeb')
BEGIN
	ALTER DATABASE [ElearningWeb] SET OFFLINE WITH ROLLBACK IMMEDIATE;
	ALTER DATABASE [ElearningWeb] SET ONLINE;
	DROP DATABASE [ElearningWeb];
END

GO

CREATE DATABASE [ElearningWeb]
GO

USE [ElearningWeb]
GO

/*******************************************************************************
	Drop tables if exists
*******************************************************************************/
DECLARE @sql nvarchar(MAX) 
SET @sql = N'' 

SELECT @sql = @sql + N'ALTER TABLE ' + QUOTENAME(KCU1.TABLE_SCHEMA) 
    + N'.' + QUOTENAME(KCU1.TABLE_NAME) 
    + N' DROP CONSTRAINT ' -- + QUOTENAME(rc.CONSTRAINT_SCHEMA)  + N'.'  -- not in MS-SQL
    + QUOTENAME(rc.CONSTRAINT_NAME) + N'; ' + CHAR(13) + CHAR(10) 
FROM INFORMATION_SCHEMA.REFERENTIAL_CONSTRAINTS AS RC 

INNER JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE AS KCU1 
    ON KCU1.CONSTRAINT_CATALOG = RC.CONSTRAINT_CATALOG  
    AND KCU1.CONSTRAINT_SCHEMA = RC.CONSTRAINT_SCHEMA 
    AND KCU1.CONSTRAINT_NAME = RC.CONSTRAINT_NAME 

EXECUTE(@sql) 

GO
DECLARE @sql2 NVARCHAR(max)=''

SELECT @sql2 += ' Drop table ' + QUOTENAME(TABLE_SCHEMA) + '.'+ QUOTENAME(TABLE_NAME) + '; '
FROM   INFORMATION_SCHEMA.TABLES
WHERE  TABLE_TYPE = 'BASE TABLE'

Exec Sp_executesql @sql2 
GO


/*******************************************************************************
   Create table
********************************************************************************/
CREATE TABLE Users
(
  UserId INT NOT NULL,
  UserName NVARCHAR(40) NOT NULL,
  Email VARCHAR(30),
  [Password] VARCHAR(255) NOT NULL,
  [Role] INT NOT NULL,
  Point INT DEFAULT 0,
  PRIMARY KEY (UserId),
  UNIQUE (UserName),
  CHECK ([Role] IN (0,1))
);

CREATE TABLE Courses
(
  CourseId INT NOT NULL,
  CourseName NVARCHAR(40),
  PublicDate DATE,
  LastUpdate DATE,
  AuthorId INT NOT NULL,
  PRIMARY KEY (CourseId),
  FOREIGN KEY (AuthorId) REFERENCES Users(UserId)
);

CREATE TABLE Modules
(
  ModuleId INT NOT NULL,
  ModuleName NVARCHAR(40),
  LastUpdate DATE,
  CourseId INT NOT NULL,
  PRIMARY KEY (ModuleId),
  FOREIGN KEY (CourseId) REFERENCES Courses(CourseId) ON DELETE CASCADE
);

CREATE TABLE Materials
(
  [Location] VARCHAR(255) NOT NULL,
  MaterialId INT NOT NULL,
  MaterialName NVARCHAR(40),
  [Type] VARCHAR(30) NOT NULL,
  LastUpdate DATE,
  ModuleId INT NOT NULL,
  PRIMARY KEY (MaterialId),
  FOREIGN KEY (ModuleId) REFERENCES Modules(ModuleId) ON DELETE CASCADE,
  UNIQUE (Location)
);

CREATE TABLE Enroll
(
  Progress DECIMAL(5,2) DEFAULT 0.00,
  UserId INT NOT NULL,
  CourseId INT NOT NULL,
  PRIMARY KEY (UserId, CourseId),
  FOREIGN KEY (UserId) REFERENCES Users(UserId) ON DELETE CASCADE,
  FOREIGN KEY (CourseId) REFERENCES Courses(CourseId) ON DELETE CASCADE,
  CHECK (Progress >= 0 AND Progress <= 100)
);

CREATE TABLE Study
(
  CompleteDate DATE,
  UserId INT NOT NULL,
  MaterialId INT NOT NULL,
  PRIMARY KEY (UserId, MaterialId),
  FOREIGN KEY (UserId) REFERENCES Users(UserId) ON DELETE CASCADE,
  FOREIGN KEY (MaterialId) REFERENCES Materials(MaterialId) ON DELETE CASCADE
);
GO

/*******************************************************************************
   TRIGGER
********************************************************************************/

/*Auto insert all materials into study table to keep track when user enroll a course*/
CREATE OR ALTER TRIGGER trg_AutoInsertStudy
ON Enroll
AFTER INSERT
AS
BEGIN
    SET NOCOUNT ON;

    INSERT INTO Study (UserId, MaterialId, CompleteDate)
    SELECT 
        i.UserId, 
        m.MaterialId, 
		NULL
    FROM inserted i
    INNER JOIN Courses c ON i.CourseId = c.CourseId
    INNER JOIN Modules mo ON mo.CourseId = c.CourseId
    INNER JOIN Materials m ON m.ModuleId = mo.ModuleId;
END;

GO


SELECT * FROM Users
DELETE Users
SELECT max(UserId) AS maxID FROM Users
SELECT * FROM Courses
SELECT * FROM Materials

UPDATE Materials
SET Location = 'materials/Introduction_to_HTML.md'
WHERE MaterialId = 301
