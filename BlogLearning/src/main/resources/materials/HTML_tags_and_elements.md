# HTML Tags and Elements

## What are HTML Tags and Elements?

HTML uses **tags** to define elements on a webpage. Tags are enclosed in angle brackets (`<>`) and usually come in pairs: an **opening tag** and a **closing tag**. The content between these tags is called an **element**.

### Example:
```html
<p>This is a paragraph.</p>
```
- `<p>` is the opening tag.
- `</p>` is the closing tag.
- `This is a paragraph.` is the content.
- The whole structure is an **HTML element**.

## Types of HTML Tags

### 1. **Structural Tags**
These tags define the structure of an HTML document.

- `<html>`: The root tag of an HTML document.
- `<head>`: Contains metadata like title and links to styles.
- `<body>`: Holds the visible content of the page.

Example:
```html
<html>
<head>
    <title>My Page</title>
</head>
<body>
    <h1>Welcome!</h1>
</body>
</html>
```

### 2. **Heading Tags**
HTML provides six levels of headings, from `<h1>` (largest) to `<h6>` (smallest).

Example:
```html
<h1>Main Heading</h1>
<h2>Subheading</h2>
```

### 3. **Text Formatting Tags**
- `<p>`: Paragraph
- `<b>`: Bold text
- `<i>`: Italic text
- `<u>`: Underlined text
- `<small>`: Smaller text
- `<strong>`: Important text (bold by default)
- `<em>`: Emphasized text (italic by default)

Example:
```html
<p>This is <b>bold</b> and <i>italic</i> text.</p>
```

### 4. **List Tags**
#### **Unordered List (`<ul>`)**
```html
<ul>
    <li>Item 1</li>
    <li>Item 2</li>
</ul>
```

#### **Ordered List (`<ol>`)**
```html
<ol>
    <li>First item</li>
    <li>Second item</li>
</ol>
```

### 5. **Link and Image Tags**
- `<a href="URL">`: Creates a hyperlink.
- `<img src="URL" alt="Description">`: Displays an image.

Example:
```html
<a href="https://www.example.com">Visit Example</a>
<img src="image.jpg" alt="Sample Image">
```

### 6. **Table Tags**
Tables organize data in rows and columns.
```html
<table>
    <tr>
        <th>Name</th>
        <th>Age</th>
    </tr>
    <tr>
        <td>John</td>
        <td>30</td>
    </tr>
</table>
```

### 7. **Form Tags**
Forms collect user input.
```html
<form>
    <label for="name">Name:</label>
    <input type="text" id="name" name="name">
    <button type="submit">Submit</button>
</form>
```

### 8. **Div and Span Tags**
- `<div>`: A block-level container.
- `<span>`: An inline container.

Example:
```html
<div style="background:lightgray; padding:10px;">
    <p>This is inside a div.</p>
</div>
<span style="color:red;">This is a span.</span>
```

## Self-Closing Tags
Some tags do not have a closing tag.
- `<br>`: Line break.
- `<hr>`: Horizontal rule.
- `<img>`: Image.
- `<meta>`: Metadata.

Example:
```html
<p>First line<br>Second line</p>
```

## Conclusion
HTML tags and elements are the building blocks of web pages. Understanding their usage is essential for structuring and designing a webpage effectively.

