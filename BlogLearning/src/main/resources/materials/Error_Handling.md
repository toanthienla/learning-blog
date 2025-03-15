# Error Handling in JavaScript

## Introduction
Error handling is crucial for writing robust and maintainable JavaScript applications. JavaScript provides several mechanisms for handling errors gracefully.

## Types of Errors
1. **Syntax Errors**: Incorrect syntax.
```javascript
console.log("Hello) // Missing closing quote
```
2. **Runtime Errors**: Errors occurring during execution.
```javascript
console.log(undefinedVariable);
```
3. **Logical Errors**: Bugs in logic leading to unexpected results.

## Try...Catch...Finally
- Handling exceptions using `try...catch`.
```javascript
try {
    let result = riskyFunction();
    console.log(result);
} catch (error) {
    console.error("An error occurred:", error.message);
} finally {
    console.log("Execution finished.");
}
```

## Throwing Custom Errors
- Using `throw` to create custom error messages.
```javascript
function divide(a, b) {
    if (b === 0) {
        throw new Error("Cannot divide by zero");
    }
    return a / b;
}
```

## Handling Errors in Async Code
- Using `.catch()` in Promises.
```javascript
fetch("https://api.example.com/data")
    .then(response => response.json())
    .catch(error => console.error("Fetch error:", error));
```
- Using `try...catch` with `async/await`.
```javascript
async function fetchData() {
    try {
        let response = await fetch("https://api.example.com/data");
        let data = await response.json();
        console.log(data);
    } catch (error) {
        console.error("Async error:", error);
    }
}
fetchData();
```

## Conclusion
Error handling ensures applications remain stable and provide meaningful feedback when things go wrong. Use `try...catch`, `throw`, and proper async error handling techniques.

