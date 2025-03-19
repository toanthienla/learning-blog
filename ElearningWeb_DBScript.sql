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

/*Auto delete all materials in study table when user unenroll the course*/
CREATE OR ALTER TRIGGER trg_AutoDeleteStudy
ON Enroll
AFTER DELETE
AS
BEGIN
    SET NOCOUNT ON;

	DELETE s
    FROM Study s
    JOIN deleted d ON s.UserId = d.UserId
    JOIN Materials ma ON s.MaterialId = ma.MaterialId
    JOIN Modules m ON ma.ModuleId = m.ModuleId
    WHERE m.CourseId = d.CourseId;

END;

GO

/* Auto update progress of course when material is update*/
CREATE OR ALTER TRIGGER trg_UpdateProgress
ON Study
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @CompleteCourse INT
	DECLARE @TotalCourse INT
	DECLARE @AUserId INT
	DECLARE @ACourseId INT

	SELECT @AUserId = i.UserId, @ACourseId = CourseId FROM inserted i
	JOIN Materials m ON m.MaterialId = i.MaterialId
	JOIN Modules md ON m.ModuleId = md.ModuleId

	SELECT @CompleteCourse = COUNT(*) FROM Study s
	JOIN Materials m ON m.MaterialId = s.MaterialId
	JOIN Modules md ON m.ModuleId = md.ModuleId
	WHERE s.CompleteDate IS NOT NULL AND UserId = @AUserId AND CourseId = @ACourseId 

	SELECT @TotalCourse = COUNT(*) FROM Courses c
	JOIN Modules md ON md.CourseId = c.CourseId
	JOIN Materials m ON m.ModuleId = md.ModuleId
	WHERE c.CourseId = @ACourseId
	

    UPDATE Enroll
    SET Progress = CAST(@CompleteCourse AS DECIMAL(5,2)) / @TotalCourse * 100
	WHERE UserId = @AUserId AND CourseId = @ACourseId
END;


GO

/* Example DATA */
INSERT INTO Users (UserId, UserName, Email, [Password], [Role], Point) VALUES
(1, 'john123', 'john@example.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 0, 50),
(2, 'jane789', 'jane@example.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 1, 80),
(3, 'alice456', 'alice@example.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 1, 30);


INSERT INTO Courses (CourseId, CourseName, PublicDate, LastUpdate, AuthorId) VALUES
(101, 'Web Development Basics', '2024-01-01', '2024-02-01', 1),
(102, 'Advanced Java Programming', '2024-01-15', '2024-02-10', 1);

INSERT INTO Modules (ModuleId, ModuleName, LastUpdate, CourseId) VALUES
(201, 'HTML & CSS Basics', '2024-02-05', 101),
(202, 'JavaScript Fundamentals', '2024-02-06', 101),
(203, 'Advanced JavaScript', '2024-02-07', 101),
(204, 'Java OOP Concepts', '2024-02-12', 102),
(205, 'Spring Boot Basics', '2024-02-14', 102),
(206, 'Database Design', '2024-02-16', 102);


INSERT INTO Materials (MaterialId, MaterialName, [Location], [Type], LastUpdate, ModuleId) VALUES
-- HTML & CSS Basics
(301, 'Introduction to HTML', 'materials/html_intro.xml', 'Video', '2024-02-07', 201),
(302, 'HTML Tags & Elements', 'materials/html_tags.xml', 'Article', '2024-02-08', 201),
(303, 'CSS Selectors', 'materials/css_selectors.xml', 'PDF', '2024-02-09', 201),
(304, 'Flexbox & Grid', 'materials/css_flexbox.xml', 'Video', '2024-02-10', 201),

-- JavaScript Fundamentals
(305, 'JavaScript Basics', 'materials/js_basics.xml', 'Article', '2024-02-11', 202),
(306, 'Functions in JS', 'materials/js_functions.xml', 'Video', '2024-02-12', 202),
(307, 'DOM Manipulation', 'materials/js_dom.xml', 'Article', '2024-02-13', 202),
(308, 'ES6 Features', 'materials/js_es6.xml', 'PDF', '2024-02-14', 202),

-- Advanced JavaScript
(309, 'Closures & Scope', 'materials/js_closures.xml', 'Video', '2024-02-15', 203),
(310, 'Async & Promises', 'materials/js_async.xml', 'Article', '2024-02-16', 203),
(311, 'JavaScript Modules', 'materials/js_modules.xml', 'PDF', '2024-02-17', 203),
(312, 'Error Handling', 'materials/js_error.xml', 'Article', '2024-02-18', 203),

-- Java OOP Concepts
(313, 'Introduction to OOP', 'materials/java_oop.xml', 'Video', '2024-02-19', 204),
(314, 'Encapsulation & Inheritance', 'materials/java_encapsulation.xml', 'PDF', '2024-02-20', 204),
(315, 'Polymorphism & Abstraction', 'materials/java_polymorphism.xml', 'Article', '2024-02-21', 204),

-- Spring Boot Basics
(316, 'Spring Boot Overview', 'materials/spring_overview.xml', 'Video', '2024-02-22', 205),
(317, 'Dependency Injection', 'materials/spring_di.xml', 'Article', '2024-02-23', 205),
(318, 'Spring Boot REST API', 'materials/spring_rest.xml', 'PDF', '2024-02-24', 205),

-- Database Design
(319, 'Introduction to Databases', 'materials/db_intro.xml', 'Video', '2024-02-25', 206),
(320, 'SQL & Normalization', 'materials/db_sql.xml', 'Article', '2024-02-26', 206);

INSERT INTO Enroll (UserId, CourseId) VALUES
(2, 101),
(2, 102),
(3, 102);

UPDATE Materials
SET Location = 'materials/Introduction_to_HTML.md'
WHERE MaterialId = 301
UPDATE Materials
SET Location = 'materials/HTML_tags_and_elements.md'
WHERE MaterialId = 302
UPDATE Materials
SET Location = 'materials/CSS_selectors.md'
WHERE MaterialId = 303
UPDATE Materials
SET Location = 'materials/Flexbox_and_grid.md'
WHERE MaterialId = 304
UPDATE Materials
SET Location = 'materials/JavaScript_basic.md'
WHERE MaterialId = 305
UPDATE Materials
SET Location = 'materials/Functions_in_JS.md'
WHERE MaterialId = 306
UPDATE Materials
SET Location = 'materials/DOM_Manipulation.md'
WHERE MaterialId = 307
UPDATE Materials
SET Location = 'materials/ES6_Features.md'
WHERE MaterialId = 308
UPDATE Materials
SET Location = 'materials/Closures_and_Scope.md'
WHERE MaterialId = 309
UPDATE Materials
SET Location = 'materials/Async_and_Promises.md'
WHERE MaterialId = 310
UPDATE Materials
SET Location = 'materials/JavaScript_Modules.md'
WHERE MaterialId = 311
UPDATE Materials
SET Location = 'materials/Error_Handling.md'
WHERE MaterialId = 312
UPDATE Materials
SET Location = 'materials/Introduction_to_OOP.md'
WHERE MaterialId = 313
UPDATE Materials
SET Location = 'materials/Encapsulation_and_Inheritance.md'
WHERE MaterialId = 314
UPDATE Materials
SET Location = 'materials/Polymorphism_and_Abstraction.md'
WHERE MaterialId = 315
UPDATE Materials
SET Location = 'materials/Spring_Boot_Overview.md'
WHERE MaterialId = 316
UPDATE Materials
SET Location = 'materials/Dependency_Injection.md'
WHERE MaterialId = 317
UPDATE Materials
SET Location = 'materials/Spring_Boot_REST_API.md'
WHERE MaterialId = 318
UPDATE Materials
SET Location = 'materials/Introduction_to_Databases.md'
WHERE MaterialId = 319
UPDATE Materials
SET Location = 'materials/SQL_and_Normalization.md'
WHERE MaterialId = 320

GO