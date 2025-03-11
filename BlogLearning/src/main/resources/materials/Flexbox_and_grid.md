# Flexbox and Grid in CSS

## Introduction
Flexbox and Grid are powerful CSS layout models that provide flexible and efficient ways to arrange elements on a webpage.

## 1. CSS Flexbox

Flexbox (**Flexible Box Layout**) is used for arranging elements in one-dimensional layouts, either in a row or a column.

### 1.1 Basic Flexbox Structure

```css
.container {
    display: flex;
    flex-direction: row; /* or column */
}
```

```html
<div class="container">
    <div class="item">Item 1</div>
    <div class="item">Item 2</div>
    <div class="item">Item 3</div>
</div>
```

### 1.2 Flexbox Properties

#### **Container Properties**

- `display: flex;` → Enables Flexbox.
- `flex-direction` → Defines the main axis (`row`, `column`, `row-reverse`, `column-reverse`).
- `justify-content` → Aligns items along the main axis.
  - `flex-start`, `flex-end`, `center`, `space-between`, `space-around`, `space-evenly`
- `align-items` → Aligns items along the cross axis.
  - `flex-start`, `flex-end`, `center`, `stretch`, `baseline`
- `flex-wrap` → Allows items to wrap to the next line (`nowrap`, `wrap`, `wrap-reverse`).
- `align-content` → Controls spacing between wrapped lines (`flex-start`, `flex-end`, `center`, `space-between`, `space-around`, `stretch`).

#### **Item Properties**

- `flex-grow` → Defines how much an item can grow relative to others.
- `flex-shrink` → Defines how much an item can shrink relative to others.
- `flex-basis` → Defines the initial size of an item.
- `align-self` → Aligns a specific item (`auto`, `flex-start`, `flex-end`, `center`, `baseline`, `stretch`).

### 1.3 Example

```css
.container {
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.item {
    flex: 1;
    padding: 10px;
    background-color: lightblue;
    margin: 5px;
}
```

## 2. CSS Grid

CSS Grid is a two-dimensional layout system that allows you to control both rows and columns.

### 2.1 Basic Grid Structure

```css
.container {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    grid-template-rows: auto;
}
```

```html
<div class="container">
    <div class="item">Item 1</div>
    <div class="item">Item 2</div>
    <div class="item">Item 3</div>
</div>
```

### 2.2 Grid Properties

#### **Container Properties**

- `display: grid;` → Enables Grid.
- `grid-template-columns` → Defines the number and size of columns.
- `grid-template-rows` → Defines the number and size of rows.
- `grid-gap` / `row-gap` / `column-gap` → Defines spacing between grid items.
- `justify-items` → Aligns items horizontally (`start`, `end`, `center`, `stretch`).
- `align-items` → Aligns items vertically (`start`, `end`, `center`, `stretch`).
- `place-items` → Shortcut for `align-items` and `justify-items`.
- `grid-auto-flow` → Controls automatic item placement (`row`, `column`, `dense`).

#### **Item Properties**

- `grid-column` → Specifies which columns an item spans (`grid-column: 1 / 3;`).
- `grid-row` → Specifies which rows an item spans (`grid-row: 1 / 2;`).
- `justify-self` → Aligns a specific item horizontally (`start`, `end`, `center`, `stretch`).
- `align-self` → Aligns a specific item vertically (`start`, `end`, `center`, `stretch`).
- `place-self` → Shortcut for `align-self` and `justify-self`.

### 2.3 Example

```css
.container {
    display: grid;
    grid-template-columns: 1fr 2fr 1fr;
    grid-gap: 10px;
}

.item {
    padding: 20px;
    background-color: lightcoral;
}
```

## 3. Flexbox vs. Grid

| Feature | Flexbox | Grid |
|---------|--------|------|
| Layout Type | One-dimensional | Two-dimensional |
| Axis Control | Main axis | Rows & columns |
| Item Alignment | `justify-content`, `align-items` | `justify-items`, `align-items` |
| Best for | Aligning items in a row or column | Creating full page layouts |

## Conclusion
Flexbox and Grid are essential tools for modern web design. Use **Flexbox** for simpler, one-dimensional layouts and **Grid** for complex, two-dimensional designs.

