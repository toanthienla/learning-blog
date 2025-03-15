# DOM Manipulation in JavaScript

## Introduction
The Document Object Model (DOM) allows JavaScript to interact with HTML and CSS, enabling dynamic web content.

## Selecting Elements
- `document.getElementById("id")`
- `document.querySelector(".class")`
- `document.querySelectorAll("tag")`

Example:
```javascript
let title = document.getElementById("main-title");
console.log(title.innerText);
```

## Modifying Elements
- Change text content:
```javascript
document.getElementById("main-title").innerText = "New Title";
```
- Change attributes:
```javascript
document.querySelector("img").setAttribute("src", "new-image.jpg");
```

## Event Listeners
- Handling user interactions:
```javascript
document.querySelector("button").addEventListener("click", function() {
    alert("Button clicked!");
});
```

## Creating and Removing Elements
- Create a new element:
```javascript
let newDiv = document.createElement("div");
newDiv.innerText = "Hello World!";
document.body.appendChild(newDiv);
```
- Remove an element:
```javascript
document.body.removeChild(newDiv);
```

## Modifying CSS
- Change styles dynamically:
```javascript
document.querySelector("p").style.color = "blue";
```

## Conclusion
DOM manipulation allows JavaScript to dynamically update content, respond to user interactions, and create dynamic user interfaces.

