# Dependency Injection in Spring Boot

## Introduction
Dependency Injection (DI) is a design pattern used in Spring Boot to manage object dependencies efficiently, promoting loose coupling.

## Types of Dependency Injection
1. **Constructor Injection**
```java
@Service
class UserService {
    private final UserRepository userRepository;
    
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
```

2. **Setter Injection**
```java
@Service
class ProductService {
    private ProductRepository productRepository;
    
    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }
}
```

3. **Field Injection**
```java
@Service
class OrderService {
    @Autowired
    private OrderRepository orderRepository;
}
```

## Benefits of Dependency Injection
- Reduces coupling between components.
- Improves testability.
- Manages object creation efficiently.

## Conclusion
Spring Boot’s DI mechanism simplifies application development by efficiently managing dependencies using annotations like `@Autowired`.

