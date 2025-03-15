# Encapsulation & Inheritance in Java

## Encapsulation
Encapsulation is the principle of restricting direct access to object data and providing controlled access via methods.

### Example:
```java
class Person {
    private String name;
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}
```

## Inheritance
Inheritance allows a class to derive properties and methods from another class.

### Example:
```java
class Animal {
    void makeSound() {
        System.out.println("Some sound");
    }
}

class Dog extends Animal {
    void bark() {
        System.out.println("Woof!");
    }
}
```

## Benefits of Encapsulation & Inheritance
- **Encapsulation** improves security and maintainability.
- **Inheritance** promotes code reuse and organization.

## Conclusion
Encapsulation and inheritance are key OOP principles that lead to better software design and modularity.

