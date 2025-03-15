# Closures & Scope in JavaScript

## Introduction
Scope determines the accessibility of variables in JavaScript, while closures allow functions to retain access to their lexical scope.

## Global vs Local Scope
- **Global Scope**: Variables accessible anywhere in the code.
- **Local Scope**: Variables declared inside a function.

Example:
```javascript
let globalVar = "I am global";

function testScope() {
    let localVar = "I am local";
    console.log(globalVar); // Accessible
    console.log(localVar); // Accessible
}

console.log(localVar); // Error: Not defined
```

## Lexical Scope
- Functions have access to variables from their parent scope.
```javascript
function outer() {
    let outerVar = "Outer";
    function inner() {
        console.log(outerVar); // Accessible
    }
    inner();
}
outer();
```

## Closures
- A closure is a function that remembers its surrounding scope even after execution.
```javascript
function counter() {
    let count = 0;
    return function() {
        count++;
        console.log(count);
    };
}

let increment = counter();
increment(); // Output: 1
increment(); // Output: 2
```

## Use Cases of Closures
- Data privacy
- Function factories
- Maintaining state

## Conclusion
Closures and scope are fundamental to JavaScript. Understanding them helps in writing more efficient and maintainable code.

