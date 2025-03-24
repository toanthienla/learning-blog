USE ElearningWeb;
GO

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


/*Test*/
Select * from Enroll
Select * from Study
SELECT * FROM Users
SELECT * FROM Courses


Delete Study
WHERE UserId = 2 AND MaterialId IN (
SELECT MaterialId From Materials m
JOIN Modules mo ON mo.ModuleId = m.ModuleId
Where mo.CourseId = 102)

DELETE Enroll
WHERE UserId = 2 AND CourseId = 102



Update Study
Set CompleteDate = GETDATE()
Where MaterialId = 301 AND UserId = 2

SELECT COUNT(*) AS [Top] FROM Users 
WHERE Point >= (SELECT Point FROM Users WHERE UserId = 2) AND Role = 1

SELECT TOP 3 * FROM Users
Where Role = 1
ORDER BY Point DESC

SELECT * FROM Courses
Where AuthorId = 1

SELECT e.Progress, e.CourseId, c.CourseName, c.PublicDate, c.LastUpdate, c.AuthorId, mo.ModuleId, mo.ModuleName, mo.LastUpdate AS ModuleLastUpdate, m.[Location], m.MaterialId, m.MaterialName, m.[Type], m.LastUpdate AS MaterialLastUpdate, s.CompleteDate FROM Enroll e
JOIN Courses c ON e.CourseId = c.CourseId
JOIN Modules mo ON c.CourseId = mo.CourseId
JOIN Materials m ON mo.ModuleId = m.ModuleId
JOIN Study s ON m.MaterialId = s.MaterialId
WHERE e.UserId = 2 AND s.UserId = 2

SELECT * FROM Materials
JOIN Modules ON Materials.ModuleId = Modules.ModuleId
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