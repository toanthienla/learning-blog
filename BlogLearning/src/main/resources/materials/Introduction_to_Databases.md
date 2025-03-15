# Introduction to Databases

## What is a Database?
A database is an organized collection of data that allows efficient storage, retrieval, and management.

## Types of Databases
- **Relational Databases (RDBMS)**: MySQL, PostgreSQL, SQL Server
- **NoSQL Databases**: MongoDB, Cassandra

## SQL vs NoSQL
| Feature | SQL | NoSQL |
|---------|-----|-------|
| Structure | Tables & Rows | Documents, Key-Value |
| Schema | Fixed | Dynamic |
| Scalability | Vertical | Horizontal |

## Basic SQL Commands
```sql
CREATE TABLE Users (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100)
);

INSERT INTO Users (id, name, email) VALUES (1, 'Alice', 'alice@example.com');

SELECT * FROM Users;
```

## Conclusion
Databases are essential for storing and managing data. Understanding SQL and NoSQL helps in choosing the right database for your application.

