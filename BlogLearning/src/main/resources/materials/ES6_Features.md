# ES6 Features in JavaScript

## Introduction
ES6 (ECMAScript 2015) introduced new features that improved JavaScript's readability, maintainability, and efficiency.

## Let and Const
- `let`: Block-scoped variable.
- `const`: Constant, cannot be reassigned.
```javascript
let name = "John";
const age = 30;
```

## Template Literals
- String interpolation using backticks.
```javascript
let greeting = `Hello, ${name}!`;
console.log(greeting);
```

## Destructuring Arrays and Objects
- Extract values easily.
```javascript
let [a, b] = [1, 2];
let { name, age } = { name: "Alice", age: 25 };
```

## Spread and Rest Operators
- Spread: Expands an array or object.
```javascript
let nums = [1, 2, 3];
let newNums = [...nums, 4, 5];
```
- Rest: Collects remaining values.
```javascript
function sum(...numbers) {
    return numbers.reduce((a, b) => a + b, 0);
}
```

## Default Parameters
- Define default values for function parameters.
```javascript
function greet(name = "Guest") {
    console.log(`Hello, ${name}`);
}
```

## Modules (Import/Export)
- Exporting a function:
```javascript
export function sayHello() {
    console.log("Hello");
}
```
- Importing a function:
```javascript
import { sayHello } from "./module.js";
sayHello();
```

## Conclusion
ES6 features make JavaScript more powerful and easier to work with. Mastering them is essential for modern web development.

