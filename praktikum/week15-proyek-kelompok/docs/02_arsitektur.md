# Arsitektur Sistem - Agri-POS
## Week 15 - Proyek Kelompok

---

## 1. Architectural Pattern: Layered Architecture (N-Tier)

Sistem Agri-POS menggunakan **Layered Architecture** dengan 4 lapisan utama:

```
┌─────────────────────────────────────────────────┐
│           PRESENTATION LAYER                    │
│      (JavaFX GUI / User Interface)              │
│   - View classes (ProductTableView, etc.)       │
│   - Event handlers                              │
│   - User interaction logic                      │
└────────────────────┬────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────┐
│         CONTROLLER LAYER                        │
│  (MVC Controllers / Application Logic)          │
│   - ProductController                           │
│   - TransactionController                       │
│   - LoginController                             │
│   - Request routing                             │
│   - Input validation (basic)                    │
└────────────────────┬────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────┐
│           SERVICE LAYER                         │
│    (Business Logic / Domain Services)           │
│   - ProductService                              │
│   - CartService                                 │
│   - TransactionService                          │
│   - AuthService                                 │
│   - Validation logic                            │
│   - Business rules enforcement                  │
└────────────────────┬────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────┐
│            DAO LAYER                            │
│   (Data Access Object / Persistence)            │
│   - ProductDAO (interface)                      │
│   - JdbcProductDAO (JDBC implementation)        │
│   - UserDAO (interface)                         │
│   - JdbcUserDAO (JDBC implementation)           │
│   - TransactionDAO                              │
│   - CRUD operations                             │
│   - SQL execution (PreparedStatement)           │
└────────────────────┬────────────────────────────┘
                     │
┌────────────────────▼────────────────────────────┐
│          DATABASE LAYER                         │
│        (PostgreSQL Persistence)                 │
│   - users table                                 │
│   - products table                              │
│   - transactions table                          │
│   - transaction_items table                     │
└─────────────────────────────────────────────────┘
```

---

## 2. Package Structure

```
com/upb/agripos/
├── model/                      # Domain Models
│   ├── Product.java           # FR-1: Product entity (code, name, category, price, stock)
│   ├── Cart.java              # FR-2: Shopping cart container
│   ├── ItemTransaksi.java     # FR-2: Cart item details (product + qty)
│   ├── Transaction.java       # FR-2/FR-4: Transaction entity
│   ├── User.java              # FR-5: User entity (username, password, role)
│   ├── PaymentMethod.java     # FR-3: Strategy interface for payments
│   ├── CashPayment.java       # FR-3: Cash payment (no fee)
│   ├── EWalletPayment.java    # FR-3: E-wallet payment (2.5% fee)
│   ├── PaymentResult.java     # Payment operation result
│   └── Promo.java             # Promotional discount (optional)
│
├── exception/                  # Custom Exceptions
│   ├── ProductNotFoundException.java
│   ├── OutOfStockException.java
│   └── CartEmptyException.java
│
├── dao/                        # Data Access Objects
│   ├── ProductDAO.java        # Interface (DIP)
│   ├── JdbcProductDAO.java    # JDBC implementation
│   ├── ProductDAOImpl.java     # Alternative implementation
│   ├── UserDAO.java           # Interface
│   ├── JdbcUserDAO.java       # JDBC implementation
│   ├── TransactionDAO.java    # Interface
│   ├── JdbcConnection.java    # Singleton connection manager
│   └── ...
│
├── service/                    # Business Logic Layer
│   ├── ProductService.java    # Product CRUD & validation
│   ├── CartService.java       # Cart add/remove/total operations
│   ├── TransactionService.java # Checkout & payment processing
│   ├── AuthService.java       # Authentication & authorization
│   ├── InventoryService.java  # Stock management
│   ├── ReceiptService.java    # Receipt generation
│   ├── PromoService.java      # Promotional logic
│   └── PaymentMethod.java     # Strategy interface
│
├── controller/                 # Controller Layer
│   ├── ProductController.java  # Manages product CRUD operations
│   ├── TransactionController.java # Manages cart & checkout
│   ├── LoginController.java    # Handles authentication
│   └── ...
│
├── view/                       # Presentation Layer (JavaFX)
│   ├── MainApp.java           # Main application window
│   ├── LoginView.java         # Login screen
│   ├── KasirView.java         # Cashier transaction view
│   └── ...
│
├── util/                       # Utility & Testing
│   ├── JdbcConnection.java    # Database connection manager
│   ├── TestConnection.java    # Connection test
│   ├── MigrateDatabase.java   # Schema migration
│   └── InsertInitialData.java # Data seeding
│
├── App.java                    # Application Entry Point
└── MainApp.java               # JavaFX Application Start
```

---

## 3. Dependency Inversion Principle (DIP) Implementation

### Problem
Concrete implementations (e.g., `JdbcProductDAO`) should NOT be tightly coupled to services.

### Current Implementation
```java
// ✓ DIP Applied (ProductService depends on interface)
public class ProductService {
    private final ProductDAO productDAO = new JdbcProductDAO();

    public void addProduct(Product product) {
        productDAO.save(product);
    }

    public List<Product> getAllProducts() {
        return productDAO.findAll();
    }
    // ... other methods
}
```

**Note**: Current implementation creates JdbcProductDAO directly. For full DIP compliance, consider 
using dependency injection (constructor injection or factory pattern) in future refactoring.

```java
// ✓ BETTER: Full DIP with constructor injection
public class ProductService {
    private final ProductDAO productDAO;
    
    public ProductService(ProductDAO dao) {
        this.productDAO = dao;  // Injected, not created
    }
}
```

### Benefits of DIP
1. Easy to test (mock DAO with test implementation)
2. Easy to swap implementations (different DB)
3. Follow SOLID principles
4. Improved maintainability

---

## 4. Data Flow Examples

### FR-1: Add Product Flow
```
LoginView / MainApp (GUI)
    ↓
ProductController.addProduct()
    ↓
ProductService.addProduct(Product)
    ├─ Validate: code not empty, price > 0, stock >= 0
    ↓
ProductDAO.save(Product)
    ├─ Prepare SQL: INSERT INTO products(code, name, category, price, stock)...
    ├─ Execute with PreparedStatement (prevents SQL injection)
    ↓
PostgreSQL Database
    ↓
products table (persisted successfully)
```

### FR-2 & FR-3: Add to Cart & Checkout Flow
```
KasirView / MainApp (GUI)
    ↓
TransactionController.addToCart(Product, qty)
    ↓
CartService.add(Product, qty)
    ├─ Validate: qty > 0
    ├─ Cart.addItem(Product, qty) creates ItemTransaksi
    ↓
Cart (in-memory, not persisted yet)
    
--- Later: Checkout ---
    ↓
TransactionController.checkout(PaymentMethod)
    ↓
TransactionService.checkout(paymentMethod)
    ├─ Calculate total = Σ(ItemTransaksi.getSubtotal())
    ├─ Apply fee factor from PaymentMethod
    ├─ paymentMethod.pay(finalAmount)
    ↓
PaymentMethod.pay() (Strategy Pattern)
    ├─ CashPayment: feeFactor = 1.0 (no fee)
    ├─ EWalletPayment: feeFactor = 1.025 (2.5% fee)
    ↓
ReceiptService.generateReceipt(Transaction)
    ├─ Format receipt with ID, items, total, payment method
    ↓
TransactionDAO.save(Transaction)
    ├─ Insert into transactions table
    ├─ Insert each item into transaction_items table
    ↓
PostgreSQL Database
    ├─ transactions table (persisted)
    ├─ transaction_items table (detail items persisted)
    ↓
Stock Updated in products table (qty reduced)
```

### FR-5: Login Flow
```
LoginView (GUI)
    ↓
LoginController.handleLogin(username, password)
    ↓
AuthService.login(username, password)
    ├─ Validate inputs (not null/empty)
    ↓
UserDAO.getUserByUsername(username)
    ├─ Query: SELECT * FROM users WHERE username = ?
    ↓
PostgreSQL Database
    ├─ User found? 
    ├─ Password matches?
    ↓
AuthService.currentUser = User
    ↓
Redirect to MainApp
    ├─ Load appropriate menu based on role
    ├─ CASHIER: Can create transactions only
    ├─ ADMIN: Can manage products, create transactions, view reports
```

### FR-4: Generate Receipt
```
After successful checkout:
    ↓
ReceiptService.generateReceipt(Transaction)
    ├─ Header: "AGRI-POS RECEIPT"
    ├─ Transaction ID, Date/Time
    ├─ Item List Format:
    │   [Product Code] [Name] [Qty] × [Unit Price] = [Subtotal]
    ├─ Total Amount
    ├─ Payment Method Used
    ├─ Amount Paid
    ├─ Change (if applicable)
    └─ Footer: "Terima Kasih - Belanja Kembali"
    ↓
Display in MainApp GUI (copy to clipboard option)
```

---

## 5. Design Patterns Used

### 5.1 Strategy Pattern (FR-3: Payment Methods)
**Purpose**: Allow flexible payment method selection without modifying checkout code.

**Interface Definition**:
```java
package com.upb.agripos.service;

public interface PaymentMethod {
    boolean pay(double amount);
}
```

**Concrete Implementations**:
```java
// Cash Payment - no fees
public class CashPayment implements PaymentMethod {
    @Override
    public boolean pay(double amount) {
        // Process cash payment
        return true;
    }
}

// E-Wallet Payment - 2.5% fee
public class EWalletPayment implements PaymentMethod {
    @Override
    public boolean pay(double amount) {
        double feeAmount = amount * 0.025;
        // Process e-wallet with fee
        return true;
    }
}
```

**Usage in TransactionService**:
```java
public class TransactionService {
    public void checkout(PaymentMethod paymentMethod) {
        double total = cartService.total();
        paymentMethod.pay(total);  // Polymorphism: works with any PaymentMethod
    }
}
```

**OCP Benefit**: 
- New payment methods (QRIS, Bank Transfer, Cryptocurrency) can be added WITHOUT changing TransactionService
- Just implement PaymentMethod interface and pass to checkout()
- Extends functionality without modifying existing code

---

### 5.2 Singleton Pattern (Database Connection)
**Purpose**: Ensure single database connection throughout application lifecycle.

```java
public class DatabaseConnection {
    private static DatabaseConnection instance;
    private Connection connection;
    
    private DatabaseConnection() {
        // Initialize connection
    }
    
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }
    
    public Connection getConnection() {
        return connection;
    }
}
```

---

### 5.3 DAO Pattern (Data Persistence)
**Purpose**: Abstract database operations from business logic, enable easy testing & swapping implementations.

**Interface Definition** (from actual implementation):
```java
package com.upb.agripos.dao;

public interface ProductDAO {
    void save(Product product);           // Create
    Product findById(String id);          // Read
    List<Product> findAll();              // Read all
    void update(Product product);         // Update
    void delete(String id);               // Delete
}
```

**JDBC Implementation** (actual code):
```java
public class JdbcProductDAO implements ProductDAO {
    @Override
    public void save(Product product) {
        String sql = "INSERT INTO products(code, name, category, price, stock) VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, product.getCode());
            pstmt.setString(2, product.getName());
            pstmt.setString(3, product.getCategory());
            pstmt.setDouble(4, product.getPrice());
            pstmt.setInt(5, product.getStock());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public Product findById(String id) {
        String sql = "SELECT * FROM products WHERE code = ?";
        // ... query execution
    }
    // ... other methods
}
```

**Benefits**:
- **Testability**: Easy to mock DAO in unit tests without real database
- **Flexibility**: Can swap JdbcProductDAO with alternative implementation (e.g., MongoDB, File-based)
- **Separation of Concerns**: Service logic stays separate from database logic
- **Reusability**: Same DAO can be used by multiple services
- **Security**: PreparedStatement prevents SQL injection attacks

**Alternative Implementation** (e.g., in future):
```java
public class MongoProductDAO implements ProductDAO {
    // MongoDB-based implementation
    // Same interface, different storage mechanism
}

// Service works with either implementation
public class ProductService {
    private ProductDAO productDAO;  // Works with any ProductDAO impl
    // ... same logic for MongoDB as JDBC
}
```

---

### 5.4 MVC Pattern (Presentation)
**Purpose**: Separate UI, logic, and data in GUI layer.

```java
// Model: Product, Cart, Transaction
public class Product { ... }

// View: JavaFX components
public class ProductTableView extends VBox { ... }

// Controller: Event handling & coordination
public class ProductController {
    private ProductService service;
    private ProductTableView view;
    
    public void handleAddProduct(...) { ... }
}
```

---

## 6. Exception Handling

```
Application Exception Hierarchy
│
├── ValidationException
│   └── Input validation failed (empty field, invalid price, etc.)
│
├── OutOfStockException
│   └── Requested quantity exceeds available stock
│
├── AuthenticationException
│   └── Login failed (wrong password, user not found)
│
├── DatabaseException
│   └── Database operation failed (connection error, SQL error)
│
└── (Built-in Exceptions)
    ├── IllegalArgumentException
    │   └── Invalid method arguments
    └── Exception (General)
        └── Unchecked errors
```

---

## 7. SOLID Principles Compliance

| Principle | Implementation | Benefit |
|-----------|---|---|
| **S** (Single Responsibility) | Each class has one reason to change (ProductService handles product logic only) | Easy maintenance, testability |
| **O** (Open-Closed) | PaymentMethod interface allows extension without modification | New payment types without code changes |
| **L** (Liskov Substitution) | CashPayment and EWalletPayment are interchangeable PaymentMethods | Polymorphism works correctly |
| **I** (Interface Segregation) | DAO interfaces are focused (ProductDAO, UserDAO, TransactionDAO) | Clients depend only on needed methods |
| **D** (Dependency Inversion) | Services depend on DAO interfaces, not concrete implementations | Loose coupling, easier testing |

---

## 8. Error Handling Strategy

### Layer-by-Layer Handling

**1. DAO Layer**
- Catch SQLException
- Wrap in DatabaseException
- Propagate to Service

**2. Service Layer**
- Validate inputs (throws ValidationException)
- Apply business rules (throws custom exceptions)
- Propagate to Controller

**3. Controller Layer**
- Catch exceptions from Service
- Format error messages for UI
- Display alerts/error dialogs

**4. View Layer**
- Display user-friendly error messages
- Don't expose technical details to user

---

## 9. Database Schema & Normalization

All tables follow **3NF (Third Normal Form)**:
- No redundant data
- Referential integrity via foreign keys
- Proper indexing for performance

---

## 10. Security Considerations

### Current Implementation (Basic)
- Login validation (username/password match)
- Role-based access control (AuthService)
- No sensitive data in logs

### Production Recommendations
- Password hashing (BCrypt/PBKDF2)
- SQL injection prevention via PreparedStatement ✓ (Already done)
- Input sanitization
- Session timeout
- Audit logging
- Encryption at rest (database-level)

---

## 11. Testing Strategy

### Unit Testing (Service Layer)
- Test business logic without UI
- Mock DAO dependencies
- Test validation & exception flows

### Integration Testing (DAO Layer)
- Test database operations
- Use test database
- Verify data persistence

### Manual Testing (GUI)
- Test user workflows end-to-end
- Verify all FR acceptance criteria
- Test error cases

---

## 12. Performance & Scalability

### Current Optimizations
- Database indexes on frequently queried columns (timestamp, product.category)
- Prepared statements prevent parsing overhead
- Connection pooling recommended for production

### Scalability Recommendations
- Implement connection pooling (HikariCP)
- Cache frequently accessed products
- Pagination for large product lists
- Async transaction processing

---

## 13. Deployment Architecture

```
Development Machine
├── Java 11+ Runtime
├── PostgreSQL Database Server
├── Agri-POS Application
│   ├── Maven (build tool)
│   ├── JavaFX (GUI runtime)
│   └── JDBC Driver
└── IDE (IntelliJ/VS Code)

Production Recommendation (Future)
├── Separate Database Server
├── Separate Application Server
├── Load balancer (if multi-instance)
└── Backup & disaster recovery plan
```

---

## 14. Configuration & Environment

**Database Configuration** (`.properties` file - future):
```properties
db.url=jdbc:postgresql://localhost:5432/agripos
db.username=postgres
db.password=1234
db.pool.size=10
db.timeout=30
```

**Application Configuration**:
```properties
app.version=1.0.0
app.name=Agri-POS
app.environment=development
logging.level=INFO
```

---

## Summary

Agri-POS sistem didesain dengan:
- ✓ Clear layered separation (View → Controller → Service → DAO → DB)
- ✓ SOLID principles (terutama DIP)
- ✓ Design patterns (Strategy, Singleton, DAO, MVC)
- ✓ Custom exception handling
- ✓ PreparedStatement for security
- ✓ Extensible payment methods (OCP)
- ✓ Role-based access control
- ✓ Comprehensive documentation

Hasilnya adalah sistem yang **maintainable, testable, scalable, dan secure**.
