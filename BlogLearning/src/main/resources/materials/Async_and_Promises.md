# Async & Promises in JavaScript

## Introduction
Asynchronous programming allows JavaScript to handle operations like API requests and timers without blocking execution.

## Callbacks vs Promises
- **Callbacks**: Functions passed as arguments to execute later.
```javascript
function fetchData(callback) {
    setTimeout(() => {
        callback("Data received");
    }, 2000);
}
fetchData(console.log);
```

- **Promises**: An alternative to callbacks that provides better readability.
```javascript
let promise = new Promise((resolve, reject) => {
    let success = true;
    setTimeout(() => {
        success ? resolve("Success") : reject("Error");
    }, 2000);
});

promise.then(console.log).catch(console.error);
```

## Async/Await Syntax
- A cleaner way to handle asynchronous operations.
```javascript
async function fetchData() {
    try {
        let response = await fetch("https://api.example.com/data");
        let data = await response.json();
        console.log(data);
    } catch (error) {
        console.error(error);
    }
}
fetchData();
```

## Error Handling in Promises
- Use `.catch()` to handle errors gracefully.
```javascript
promise
    .then(data => console.log(data))
    .catch(error => console.error("Error:", error));
```

## Conclusion
Promises and async/await make handling asynchronous code more readable and maintainable, improving performance in modern JavaScript applications.

