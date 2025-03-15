# Functions in JavaScript

## Introduction
Functions are reusable blocks of code that perform a specific task. JavaScript supports different types of functions, including regular functions, arrow functions, and higher-order functions.

## Function Declaration vs Function Expression
- **Function Declaration:**
```javascript
function greet() {
    console.log("Hello, world!");
}
```
- **Function Expression:**
```javascript
const greet = function() {
    console.log("Hello, world!");
};
```

## Arrow Functions
- A concise syntax for writing functions:
```javascript
const add = (a, b) => a + b;
```

## Callback Functions
- A function passed as an argument to another function:
```javascript
function fetchData(callback) {
    setTimeout(() => {
        console.log("Data fetched");
        callback();
    }, 2000);
}
```

## Higher-Order Functions
- Functions that take another function as an argument or return a function:
```javascript
function multiplier(factor) {
    return number => number * factor;
}
const double = multiplier(2);
console.log(double(5)); // Output: 10
```

## Function Scope and Hoisting
- Functions can access variables within their scope but not from outer scopes.
- Function declarations are hoisted, meaning they can be called before being defined.

## Conclusion
Functions are a core part of JavaScript programming, enabling code reusability and modularity. Understanding them is crucial for building efficient applications.

