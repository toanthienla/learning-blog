# SQL & Normalization

## Introduction
Structured Query Language (SQL) is used to manage and manipulate relational databases. Normalization organizes data efficiently.

## SQL Basics
- **SELECT**: Retrieve data.
```sql
SELECT * FROM employees;
```
- **INSERT**: Add new records.
```sql
INSERT INTO employees (id, name, salary) VALUES (1, 'Alice', 50000);
```
- **UPDATE**: Modify records.
```sql
UPDATE employees SET salary = 60000 WHERE id = 1;
```
- **DELETE**: Remove records.
```sql
DELETE FROM employees WHERE id = 1;
```

## Database Normalization
Normalization eliminates redundancy and improves data integrity.

### Normal Forms:
1. **1NF**: Each column contains atomic values.
2. **2NF**: No partial dependencies (depends on full primary key).
3. **3NF**: No transitive dependencies (non-key attributes depend only on primary key).

## Example of Normalization
**Unnormalized Table:**
| OrderID | Customer | Items |
|---------|---------|-------|
| 1       | John    | Pen, Notebook |

**Normalized Tables:**
- Orders: (`OrderID`, `CustomerID`)
- Order_Items: (`OrderID`, `Item`)

## Conclusion
Understanding SQL and normalization ensures efficient data management and reduces redundancy, improving database performance.

