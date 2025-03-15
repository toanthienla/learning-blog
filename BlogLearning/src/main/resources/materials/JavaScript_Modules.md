# JavaScript Modules

## Introduction
Modules help organize code into reusable blocks. ES6 introduced a standardized module system in JavaScript.

## Creating a Module
- **Exporting a function:**
```javascript
export function greet(name) {
    return `Hello, ${name}!`;
}
```
- **Exporting multiple values:**
```javascript
export const pi = 3.14;
export function square(x) { return x * x; }
```

## Importing Modules
- **Named Imports:**
```javascript
import { greet, pi } from "./module.js";
console.log(greet("Alice"));
console.log(pi);
```
- **Default Import:**
```javascript
export default function() {
    console.log("Default Export");
}
```
```javascript
import myFunction from "./module.js";
myFunction();
```

## Dynamic Imports
- Importing modules dynamically using `import()`:
```javascript
async function loadModule() {
    const module = await import("./module.js");
    console.log(module.greet("Bob"));
}
```

## Using Modules in Node.js
- Use `require()` instead of `import`.
```javascript
const fs = require("fs");
```

## Conclusion
JavaScript modules improve code structure and reusability, making them an essential tool for modern web development.

