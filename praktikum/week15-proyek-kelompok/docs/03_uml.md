# UML Diagrams - Agri-POS System
## Week 15 - Proyek Kelompok

---

## 1. Use Case Diagram

```
┌─────────────────────────────────────────────────────────┐
│                    AGRI-POS SYSTEM                      │
│                                                          │
│  ┌──────────┐                   ┌──────────┐           │
│  │ Cashier  │                   │  Admin   │           │
│  │  (User)  │                   │  (User)  │           │
│  └────┬─────┘                   └────┬─────┘           │
│       │                              │                  │
│       │              ┌───────────────┴────────┐         │
│       │              │                        │         │
│       └──────────┬───┴──────────────┬─────────┘         │
│                  │                  │                   │
│        ┌─────────▼──────────┐ ┌──────▼──────────┐      │
│        │   Login to System  │ │ Login to System │      │
│        └────────────────────┘ └─────────────────┘      │
│                                                         │
│  ┌─────────────────────────────────┐                   │
│  │  Manage Product (CRUD)          │◄─────┐            │
│  │  FR-1                           │      │ Admin only │
│  └─────────────────────────────────┘      │            │
│                                            │            │
│  ┌─────────────────────────────────┐      │            │
│  │  Create Shopping Cart           │      │            │
│  │  - Add product to cart          │◄─────┤            │
│  │  - Update quantity              │      │            │
│  │  - Remove item                  │      │ Cashier    │
│  │  FR-2                           │      │            │
│  └───────────┬─────────────────────┘      │            │
│              │                             │            │
│  ┌───────────▼─────────────────────┐      │            │
│  │  Checkout & Payment             │◄─────┤            │
│  │  - Select payment method        │      │            │
│  │  - Process payment              │      │            │
│  │  - Generate receipt             │      │            │
│  │  FR-2, FR-3, FR-4              │      │            │
│  └─────────────────────────────────┘      │            │
│                                            │            │
│  ┌─────────────────────────────────┐      │            │
│  │  View Sales Report              │◄─────┘ Admin only │
│  │  - Daily sales summary          │                   │
│  │  - Payment breakdown            │                   │
│  │  FR-4                           │                   │
│  └─────────────────────────────────┘                   │
│                                                          │
└──────────────────────────────────────────────────────────┘
```

**Use Cases** (FR Mapping):
| Use Case | FR | Actor | Description |
|---|---|---|---|
| Login to System | FR-5 | Cashier, Admin | Authenticate user |
| Manage Product (CRUD) | FR-1 | Admin | Create, Read, Update, Delete products |
| Create Shopping Cart | FR-2 | Cashier | Build transaction |
| Add/Update/Remove Item | FR-2 | Cashier | Manage cart contents |
| Checkout & Payment | FR-2/3/4 | Cashier | Complete transaction & get receipt |
| View Sales Report | FR-4 | Admin | Analyze sales data |

---

## 2. Class Diagram (Detailed)

### 2.1 Model Layer Classes

```
package com.upb.agripos.model
─────────────────────────────────────────────────────────

┌────────────────────────────────┐
│        Product                 │
├────────────────────────────────┤
│ - productId: int               │
│ - code: String                 │
│ - name: String                 │
│ - category: String             │
│ - price: double                │
│ - stock: int                   │
├────────────────────────────────┤
│ + Product(code, name, price,   │
│    stock)                      │
│ + getCode()                    │
│ + getName()                    │
│ + getCategory()                │
│ + getPrice()                   │
│ + getStock()                   │
│ + setPrice()                   │
│ + setStock()                   │
│ + reduceStock(qty)             │
│ + restoreStock(qty)            │
└────────────────────────────────┘
        ▲
        │ 1
        │
        │ 0..*
        │
┌────────────────────────────────┐         ┌──────────────────────────┐
│     ItemTransaksi              │         │       Cart               │
├────────────────────────────────┤         ├──────────────────────────┤
│ - product: Product             │◄────────│ - items: List<Item..>    │
│ - qty: int                     │    1    ├──────────────────────────┤
├────────────────────────────────┤         │ + addItem(Product, qty)  │
│ + ItemTransaksi(product, qty)  │         │ + getItems()             │
│ + getProduct()                 │         │ + getTotal()             │
│ + getQty()                     │         │ + clear()                │
│ + getSubtotal()                │         │ + isEmpty()              │
│ + setQty(int)                  │         └──────────────────────────┘
└────────────────────────────────┘


┌──────────────────────────────────┐
│      Transaction                 │
├──────────────────────────────────┤
│ - id: String                     │
│ - timestamp: LocalDateTime       │
│ - items: List<ItemTransaksi>     │
│ - total: double                  │
│ - paymentMethod: String          │
│ - status: String                 │
├──────────────────────────────────┤
│ + getId()                        │
│ + getTimestamp()                 │
│ + getItems()                     │
│ + getTotal()                     │
│ + getPaymentMethod()             │
│ + getStatus()                    │
│ + setStatus(String)              │
│ + toString()                     │
└──────────────────────────────────┘


┌──────────────────────────────┐
│         User                 │
├──────────────────────────────┤
│ - id: int                    │
│ - username: String           │
│ - password: String           │
│ - role: String               │
├──────────────────────────────┤
│ + User(id, username, pwd,    │
│    role)                     │
│ + getId()                    │
│ + getUsername()              │
│ + getPassword()              │
│ + getRole()                  │
└──────────────────────────────┘


┌──────────────────────────────┐
│  PaymentMethod (Interface)   │
├──────────────────────────────┤
│ + pay(amount: double)        │
│    : boolean                 │
└──────────────────────────────┘
      ▲              ▲
      │              │
      │ implements   │ implements
      │              │
┌─────┴─────┐    ┌──┴──────────┐
│ CashPayment   │ EWalletPayment│
├────────────┤  ├────────────────┤
│ + pay()    │  │ + pay()        │
│   (no fee) │  │  (2.5% fee)    │
└────────────┘  └────────────────┘


┌──────────────────────────────┐
│    PaymentResult             │
├──────────────────────────────┤
│ - success: boolean           │
│ - message: String            │
│ - amount: double             │
├──────────────────────────────┤
│ + PaymentResult(...)         │
│ + isSuccess()                │
│ + getMessage()               │
│ + getAmount()                │
└──────────────────────────────┘


┌──────────────────────────────┐
│       Promo                  │
├──────────────────────────────┤
│ - code: String               │
│ - discountPercent: double    │
│ - startDate: LocalDate       │
│ - endDate: LocalDate         │
├──────────────────────────────┤
│ + getCode()                  │
│ + getDiscountPercent()       │
│ + isActive()                 │
│ + apply(amount)              │
└──────────────────────────────┘
```

### 2.2 DAO Layer (Data Persistence)

```
package com.upb.agripos.dao
─────────────────────────────────────────────────────────

┌──────────────────────────────────────┐
│   ProductDAO (Interface)             │
├──────────────────────────────────────┤
│ + save(Product): void                │
│ + findById(String): Product          │
│ + findAll(): List<Product>           │
│ + update(Product): void              │
│ + delete(String): void               │
└──────────────────────────────────────┘
        ▲
        │ implements
        │
┌──────────────────────────────────────┐
│   JdbcProductDAO                     │
├──────────────────────────────────────┤
│ - connection: Connection             │
├──────────────────────────────────────┤
│ + save(Product)                      │
│ + findById(String)                   │
│ + findAll()                          │
│ + update(Product)                    │
│ + delete(String)                     │
│ - mapRowToProduct(ResultSet)         │
└──────────────────────────────────────┘


┌──────────────────────────────────────┐
│   UserDAO (Interface)                │
├──────────────────────────────────────┤
│ + getUserByUsername(String): User    │
│ + saveUser(User): void               │
│ + updateUser(User): void             │
│ + getAllUsers(): List<User>          │
└──────────────────────────────────────┘
        ▲
        │ implements
        │
┌──────────────────────────────────────┐
│   JdbcUserDAO                        │
├──────────────────────────────────────┤
│ - connection: Connection             │
├──────────────────────────────────────┤
│ + getUserByUsername(String)          │
│ + saveUser(User)                     │
│ + updateUser(User)                   │
│ + getAllUsers()                      │
└──────────────────────────────────────┘


┌──────────────────────────────────────┐
│   TransactionDAO (Interface)         │
├──────────────────────────────────────┤
│ + saveTransaction(Transaction)       │
│ + findTransactionById(String)        │
│ + getAllTransactions()               │
│ + updateTransactionStatus(...)       │
└──────────────────────────────────────┘
        ▲
        │ implements
        │
┌──────────────────────────────────────┐
│   JdbcTransactionDAO                 │
├──────────────────────────────────────┤
│ - connection: Connection             │
├──────────────────────────────────────┤
│ + saveTransaction(Transaction)       │
│ + findTransactionById(String)        │
│ + getAllTransactions()               │
│ + updateTransactionStatus(...)       │
└──────────────────────────────────────┘
```

### 2.3 Service Layer (Business Logic)

```
package com.upb.agripos.service
─────────────────────────────────────────────────────────

┌────────────────────────────────────┐
│   ProductService                   │
├────────────────────────────────────┤
│ - productDAO: ProductDAO           │
├────────────────────────────────────┤
│ + addProduct(Product): void        │
│ + getProduct(String): Product      │
│ + getAllProducts(): List<Product>  │
│ + updateProduct(Product): void     │
│ + deleteProduct(String): void      │
│ + updateStock(String, qty): void   │
└────────────────────────────────────┘


┌────────────────────────────────────┐
│   CartService                      │
├────────────────────────────────────┤
│ - cart: Cart                       │
├────────────────────────────────────┤
│ + add(Product, qty): void          │
│ + total(): double                  │
│ + getCart(): Cart                  │
│ + clear(): void                    │
└────────────────────────────────────┘


┌────────────────────────────────────┐
│   TransactionService               │
├────────────────────────────────────┤
│ - cartService: CartService         │
│ - transactionDAO: TransactionDAO   │
├────────────────────────────────────┤
│ + checkout(PaymentMethod)          │
│ + generateReceipt(Transaction)     │
│ + saveTransaction(Transaction)     │
│ + getAllTransactions()             │
└────────────────────────────────────┘


┌────────────────────────────────────┐
│   ReceiptService                   │
├────────────────────────────────────┤
│ + generateReceipt(Transaction)     │
│    : String                        │
│ + generateDailyReport(LocalDate)   │
│    : String                        │
│ + generatePeriodReport(from, to)   │
│    : String                        │
└────────────────────────────────────┘


┌────────────────────────────────────┐
│   AuthService                      │
├────────────────────────────────────┤
│ - userDAO: UserDAO                 │
│ - currentUser: User                │
├────────────────────────────────────┤
│ + login(String, String): User      │
│ + logout(): void                   │
│ + getCurrentUser(): User           │
│ + isLoggedIn(): boolean            │
│ + canManageProducts(): boolean     │
│ + canCreateTransaction(): boolean  │
│ + canViewReports(): boolean        │
└────────────────────────────────────┘


┌────────────────────────────────────┐
│   InventoryService                 │
├────────────────────────────────────┤
│ - productService: ProductService   │
├────────────────────────────────────┤
│ + checkStock(String, qty)          │
│    : boolean                       │
│ + decreaseStock(String, qty)       │
│ + increaseStock(String, qty)       │
│ + getLowStockItems(): List<Prod>   │
└────────────────────────────────────┘


┌────────────────────────────────────┐
│   PromoService                     │
├────────────────────────────────────┤
│ - activePromos: List<Promo>        │
├────────────────────────────────────┤
│ + addPromo(Promo): void            │
│ + getActivePromo(String): Promo    │
│ + applyPromo(code, amount)         │
│    : double                        │
└────────────────────────────────────┘
```

### 2.4 Custom Exception Hierarchy

```
package com.upb.agripos.exception
─────────────────────────────────────────────────────────

                    Exception
                        ▲
                        │
            ┌───────────┼───────────┐
            │                       │
   RuntimeException           (checked exceptions)
        ▲
        │
        ├─ ProductNotFoundException
        │  └─ "Product with code X not found"
        │
        ├─ OutOfStockException
        │  └─ "Requested qty exceeds available stock"
        │
        └─ CartEmptyException
           └─ "Cannot checkout with empty cart"
```

### 2.5 Controller Layer

```
package com.upb.agripos.controller
─────────────────────────────────────────────────────────

┌──────────────────────────────────┐
│   ProductController              │
├──────────────────────────────────┤
│ - productService: ProductService │
├──────────────────────────────────┤
│ + handleAddProduct(...)          │
│ + handleDeleteProduct(...)       │
│ + handleUpdateProduct(...)       │
│ + refreshProductList()           │
│ - validateInput(...)             │
│ - showError(message)             │
└──────────────────────────────────┘


┌──────────────────────────────────┐
│   TransactionController          │
├──────────────────────────────────┤
│ - cartService: CartService       │
│ - transactionService: Txn...     │
│ - inventoryService: Inventory... │
├──────────────────────────────────┤
│ + handleAddToCart(...)           │
│ + handleRemoveFromCart(...)      │
│ + handleCheckout(...)            │
│ + handleChangePaymentMethod(...) │
│ + refreshCartDisplay()           │
└──────────────────────────────────┘


┌──────────────────────────────────┐
│   LoginController                │
├──────────────────────────────────┤
│ - authService: AuthService       │
├──────────────────────────────────┤
│ + handleLogin(username, pwd)     │
│ + handleLogout()                 │
│ + showLoginError(message)        │
└──────────────────────────────────┘
```

### 2.6 View Layer (JavaFX)

```
package com.upb.agripos.view
─────────────────────────────────────────────────────────

┌──────────────────────────────────┐
│   MainApp                        │
│   (extends Application)          │
├──────────────────────────────────┤
│ - primaryStage: Stage            │
│ - scene: Scene                   │
│ - authService: AuthService       │
├──────────────────────────────────┤
│ + start(Stage)                   │
│ + showLoginView()                │
│ + showMainApp(User)              │
│ + logout()                       │
└──────────────────────────────────┘


┌──────────────────────────────────┐
│   LoginView                      │
├──────────────────────────────────┤
│ - usernameField: TextField       │
│ - passwordField: PasswordField    │
│ - loginButton: Button            │
├──────────────────────────────────┤
│ + getUsername(): String          │
│ + getPassword(): String          │
│ + showError(message)             │
│ + clearFields()                  │
└──────────────────────────────────┘


┌──────────────────────────────────┐
│   KasirView                      │
│   (Cashier Transaction View)     │
├──────────────────────────────────┤
│ - productCombo: ComboBox         │
│ - qtyField: TextField            │
│ - cartTable: TableView           │
│ - totalLabel: Label              │
│ - paymentCombo: ComboBox         │
├──────────────────────────────────┤
│ + getSelectedProduct(): String   │
│ + getQuantity(): int             │
│ + refreshCart(Cart)              │
│ + updateTotal(double)            │
│ + showReceipt(String)            │
└──────────────────────────────────┘
```
├──────────────────────────────┤
│ - productCode: String        │
│ - requestedQty: int          │
│ - availableQty: int          │
├──────────────────────────────┤
│ + getProductCode()           │
│ + getRequestedQuantity()     │
│ + getAvailableQuantity()     │
└──────────────────────────────┘

┌──────────────────────────────┐
│  AuthenticationException     │
├──────────────────────────────┤
│ + AuthenticationException()  │
└──────────────────────────────┘

┌──────────────────────────────┐
│  DatabaseException           │
├──────────────────────────────┤
│ + DatabaseException()        │
└──────────────────────────────┘
```

---

## 3. Sequence Diagrams

### 3.1 FR-1: Add Product Sequence (UC-AddProduct)

```
Actor: Admin User
Precondition: Admin logged in
Flow: Add new product

    Admin          ProductView   ProductController   ProductService   ProductDAO   Database
      │                │                │                  │              │            │
      │─ Click Add ────>│                │                  │              │            │
      │                │─ Show Dialog ─>│                  │              │            │
      │                │<──────────────  │                  │              │            │
      │<─ Fill Form ───│                 │                  │              │            │
      │─ Submit ──────>│                 │                  │              │            │
      │                │─ handleAdd() ──>│                  │              │            │
      │                │                │─ Validate ─────>│              │            │
      │                │                │<─ OK ───────────│              │            │
      │                │                │─ addProduct() ─>│              │            │
      │                │                │                 │─ create() ──>│            │
      │                │                │                 │             │─ INSERT ──>│
      │                │                │                 │             │<─ OK ─────│
      │                │                │                 │<─ OK ────────│            │
      │                │                │<─ OK ──────────│              │            │
      │                │<─ Refresh List─│                 │              │            │
      │<─ Show Success─│                 │                  │              │            │
      │                │                │                  │              │            │

Exception Flow (Duplicate Code):
      Admin          ProductView   ProductController   ProductService   ProductDAO
        │                │                │                  │              │
        │─ Submit ──────>│                 │                  │              │
        │                │─ handleAdd() ──>│                  │              │
        │                │                │─ exists() ──────>│              │
        │                │                │<─ TRUE ──────────│              │
        │                │                │─ Throw ValidationException
        │                │                │<─ Exception ─────│
        │                │<─ Exception ───│                  │              │
        │<─ Show Error ──│                 │                  │              │
        │                │                │                  │              │
```

---

### 3.2 FR-2/3/4: Checkout with Payment Sequence (UC-Checkout)

```
Actor: Cashier
Precondition: Items in cart
Flow: Checkout and payment

    Cashier    CheckoutView   TransController   TransService   PaymentMethod   Database
      │             │                │              │              │              │
      │─ Click ────>│                │              │              │              │
      │  Checkout   │                │              │              │              │
      │             │─ Show Payment ─|              │              │              │
      │             │  Options       │              │              │              │
      │<─ Select ───│                │              │              │              │
      │  Payment    │                │              │              │              │
      │─ Pay ──────>│                │              │              │              │
      │             │─ Click Pay ───>│              │              │              │
      │             │                │─ checkout()─>│              │              │
      │             │                │              │─ validate() ─│              │
      │             │                │              │  cart        │              │
      │             │                │              │              │              │
      │             │                │              │─ Calculate ──│──────┐       │
      │             │                │              │  total with  │      │       │
      │             │                │              │  fee factor  │<─────┘       │
      │             │                │              │              │              │
      │             │                │              │──────────────|─────────────>│
      │             │                │              │ processPayment(amount)     │
      │             │                │              │<──────────────────────────│
      │             │                │              │ (CASH: 1.0x, EWALLET: 1.025x)
      │             │                │              │              │              │
      │             │                │              │─────────────────────────>│ 
      │             │                │              │ INSERT transaction       │
      │             │                │              │<──────────────────────────│
      │             │                │              │ (success)                 │
      │             │                │              │              │              │
      │             │                │<──ReceiptInfo──────────────────────────────│
      │             │<─ Show Receipt─|              │              │              │
      │<─ Display ──│                │              │              │              │
      │  Receipt    │                │              │              │              │
      │             │                │              │              │              │

Exception Flow (Out of Stock when adding):
    Cashier    CartView   CartService   ProductService   Exception
      │           │             │               │            │
      │─ Add ────>│             │               │            │
      │           │─ add() ────>│               │            │
      │           │             │─ getProduct()→│            │
      │           │             │<─ Product ────│            │
      │           │             │─ Check stock──│            │
      │           │             │               │─ NOT OK    │
      │           │             │<─ OutOfStockException─────│
      │           │<─ Exception─|               │            │
      │<─ Show ───│             │               │            │
      │  Error    │             │               │            │
      │           │             │               │            │
```

---

### 3.3 FR-5: Login Sequence (UC-Login)

```
Precondition: Not logged in
Flow: User authentication

    User       LoginView   LoginController   AuthService   UserDAO   Database
      │             │             │              │            │          │
      │─ Enter ────>│             │              │            │          │
      │  Credentials│             │              │            │          │
      │             │─ Click ────>│              │            │          │
      │             │  Login      │              │            │          │
      │             │             │─ login() ───>│            │          │
      │             │             │              │─ read() ──>│          │
      │             │             │              │           │─ SELECT ─>│
      │             │             │              │           │<─ User ──│
      │             │             │              │<─ User ────│          │
      │             │             │              │            │          │
      │             │             │              │ Check      │          │
      │             │             │              │ password   │          │
      │             │             │              │ match      │          │
      │             │             │              │            │          │
      │             │             │<─ OK ────────│            │          │
      │             │             │ (user)       │            │          │
      │             │<─ Success ──│              │            │          │
      │<─ Go to ────│              │              │            │          │
      │  Main App   │              │              │            │          │

Exception Flow (Invalid password):
    User       LoginView   LoginController   AuthService   UserDAO
      │             │             │              │            │
      │─ Enter ────>│             │              │            │
      │  Wrong pwd  │             │              │            │
      │             │─ Click ────>│              │            │
      │             │  Login      │─ login() ───>│            │
      │             │             │              │─ read() ──>│
      │             │             │              │<─ User ────│
      │             │             │              │            │
      │             │             │              │ Check pwd  │
      │             │             │              │ NOT match  │
      │             │             │<─ AuthenticationException──
      │             │<─ Exception─|              │            │
      │<─ Show ─────│              │              │            │
      │  Error msg  │              │              │            │
      │             │              │              │            │
```

---

## 4. Activity Diagrams (untuk key processes)

### 4.1 Add Product Activity

```
Start
  │
  ▼
[Admin fills product form]
  │
  ▼
{Validate inputs}
  ├─ No ──> [Show error message] ──> End
  │
  ▼ Yes
{Product code exists?}
  ├─ Yes ──> [Show "Duplicate code" error] ──> End
  │
  ▼ No
[Create Product object]
  │
  ▼
[DAO.create(product)]
  │
  ▼
{Insert successful?}
  ├─ No ──> [Show "Database error"] ──> End
  │
  ▼ Yes
[Refresh product table]
  │
  ▼
[Show success message]
  │
  ▼
End
```

---

## 5. State Diagram (Transaction States)

```
         ┌─────────────┐
         │   Created   │
         │ (new cart)  │
         └──────┬──────┘
                │
                ▼
         ┌─────────────┐
         │   Active    │  ◄──────┐ (Add/remove items)
         │ (building   │  ────────┘
         │  cart)      │
         └──────┬──────┘
                │
                ▼
         ┌─────────────┐
         │  Ready      │  (items > 0)
         │ (checkout   │
         │  clicked)   │
         └──────┬──────┘
                │
         ┌──────┴───────────┐
         │                  │
         ▼                  ▼
    ┌─────────┐     ┌────────────┐
    │ Payment │     │ Cancelled  │
    │ Process │     │ (user back)│
    └────┬────┘     └────────────┘
         │
    ┌────┴───────────┐
    │                │
    ▼                ▼
┌────────────┐  ┌─────────────┐
│ Completed  │  │   Failed    │
│(saved DB)  │  │(payment err)│
└────────────┘  └─────────────┘
```

---

## Summary

UML Diagrams menunjukkan:
- **Use Cases**: Semua FR terintegrasi dengan login/role
- **Classes**: Model, DAO (interface + impl), Service, Exception
- **Sequences**: Core flows (Add Product, Checkout, Login)
- **Activity**: Detailed process steps
- **States**: Transaction lifecycle

Semua diagram konsisten dengan architecture layered dan design patterns (Strategy, Singleton, DAO, MVC).
