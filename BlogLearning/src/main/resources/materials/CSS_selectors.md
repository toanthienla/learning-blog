# CSS Selectors

## What are CSS Selectors?

CSS selectors define the elements to which a set of CSS rules apply. They are used to target specific HTML elements based on their attributes, relationships, or positions in the document.

## Types of CSS Selectors

### 1. Universal Selector (`*`)
The universal selector applies styles to all elements on the page.

```css
* {
    margin: 0;
    padding: 0;
}
```

### 2. **Element Selector**
Selects elements by their tag name.

```css
p {
    color: blue;
}
```

### 3. **Class Selector (`.`)**
Targets elements with a specific class.

```css
.highlight {
    background-color: yellow;
}
```

HTML:
```html
<p class="highlight">This text is highlighted.</p>
```

### 4. **ID Selector (`#`)**
Targets an element with a specific ID (must be unique).

```css
#main-heading {
    font-size: 24px;
}
```

HTML:
```html
<h1 id="main-heading">Welcome</h1>
```

### 5. **Group Selector (`A, B, C`)**
Applies the same styles to multiple elements.

```css
h1, h2, h3 {
    font-family: Arial, sans-serif;
}
```

### 6. **Descendant Selector (`A B`)**
Selects elements inside a specific parent.

```css
div p {
    color: red;
}
```

HTML:
```html
<div>
    <p>This paragraph is red.</p>
</div>
```

### 7. **Child Selector (`A > B`)**
Targets direct children of a parent.

```css
ul > li {
    list-style-type: square;
}
```

### 8. **Adjacent Sibling Selector (`A + B`)**
Selects an element immediately following another.

```css
h1 + p {
    font-style: italic;
}
```

### 9. **General Sibling Selector (`A ~ B`)**
Selects all siblings after a specific element.

```css
div ~ p {
    color: green;
}
```

### 10. **Attribute Selector**
Targets elements based on attributes.

```css
a[href] {
    color: blue;
}

input[type="text"] {
    border: 1px solid black;
}
```

### 11. **Pseudo-Classes**
Target elements in a specific state.

```css
a:hover {
    color: red;
}

input:focus {
    background-color: lightgray;
}
```

### 12. **Pseudo-Elements**
Style specific parts of an element.

```css
p::first-line {
    font-weight: bold;
}

p::before {
    content: "★ ";
    color: gold;
}
```

## Conclusion
CSS selectors allow precise styling control over elements. Understanding them helps create well-structured and visually appealing web pages.

