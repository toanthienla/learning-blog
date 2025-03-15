# Spring Boot REST API

## Introduction
Spring Boot makes it easy to create RESTful web services with minimal configuration.

## Creating a REST Controller
- Use `@RestController` to define a RESTful API.
```java
@RestController
@RequestMapping("/api")
public class UserController {
    @GetMapping("/users")
    public List<String> getUsers() {
        return List.of("Alice", "Bob", "Charlie");
    }
}
```

## Handling HTTP Methods
- **GET**: Retrieve data.
- **POST**: Create new resources.
- **PUT**: Update existing resources.
- **DELETE**: Remove resources.
```java
@PostMapping("/users")
public String createUser(@RequestBody String user) {
    return "User " + user + " created";
}
```

## Exception Handling
- Use `@ExceptionHandler` for custom error responses.
```java
@ControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
    }
}
```

## Conclusion
Spring Boot REST APIs are easy to implement and scalable, making them ideal for microservices and web applications.

