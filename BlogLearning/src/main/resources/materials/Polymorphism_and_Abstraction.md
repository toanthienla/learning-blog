# Polymorphism & Abstraction in Java

## Polymorphism
Polymorphism allows the same interface to be used for different types of actions.

### Method Overloading
Multiple methods with the same name but different parameters.
```java
class MathUtil {
    int add(int a, int b) {
        return a + b;
    }
    double add(double a, double b) {
        return a + b;
    }
}
```

### Method Overriding
A subclass provides a specific implementation of a method from its parent class.
```java
class Animal {
    void makeSound() {
        System.out.println("Animal makes sound");
    }
}

class Dog extends Animal {
    void makeSound() {
        System.out.println("Woof!");
    }
}
```

## Abstraction
Abstraction hides implementation details and only exposes necessary functionality.

### Abstract Classes
```java
abstract class Vehicle {
    abstract void start();
}
class Car extends Vehicle {
    void start() {
        System.out.println("Car is starting");
    }
}
```

### Interfaces
```java
interface Flyable {
    void fly();
}
class Bird implements Flyable {
    public void fly() {
        System.out.println("Bird is flying");
    }
}
```

## Conclusion
Polymorphism enhances code flexibility, and abstraction simplifies complexity, making them essential in Java programming.

