# Software Requirements Specification (SRS)
## Agri-POS System - Week 15 Proyek Kelompok

---

## 1. Executive Summary

**Project Name**: Agri-POS (Agricultural Point of Sale System)
**Phase**: Week 15 - Proyek Kelompok (Group Project)
**Status**: Development & Documentation
**Scope**: Complete POS system for agricultural retail with inventory, sales, and reporting

---

## 2. System Overview

### Purpose
Agri-POS is a complete Point-of-Sale (POS) system designed specifically for agricultural product retailers. It provides comprehensive functionality for inventory management, sales transactions, multiple payment methods, and business reporting.

### Key Objectives
1. Simplify agricultural product sales transactions
2. Maintain accurate inventory tracking
3. Provide sales analytics and reporting
4. Ensure secure user access with role-based permissions
5. Support flexible payment methods

---

## 3. Functional Requirements (FR)

### FR-1: Manajemen Produk (Product Management)
**Description**: Admin users can manage agricultural products with full CRUD operations.

**Detailed Requirements**:
- FR-1.1: Add new product with attributes: code, name, category, price, stock
- FR-1.2: View list of all products with pagination
- FR-1.3: Update existing product (name, category, price, stock)
- FR-1.4: Delete product from system
- FR-1.5: Search product by code or name
- FR-1.6: Validation: code must be unique, price > 0, stock >= 0

**Acceptance Criteria**:
- Admin can perform all CRUD operations successfully
- Duplicate product codes are rejected with error message
- Product data is persisted to PostgreSQL
- Data appears immediately in product list after CRUD operation

---

### FR-2: Transaksi Penjualan (Sales Transaction)
**Description**: Cashiers can create sales transactions by managing shopping carts.

**Detailed Requirements**:
- FR-2.1: Create new transaction / shopping cart
- FR-2.2: Add product to cart with quantity validation
- FR-2.3: Modify product quantity in cart
- FR-2.4: Remove product from cart
- FR-2.5: Auto-calculate cart total (sum of all items)
- FR-2.6: Prevent adding quantity exceeding available stock (OutOfStockException)
- FR-2.7: Clear cart after checkout

**Acceptance Criteria**:
- Cashier can add multiple different products to cart
- Cart total updates automatically
- Out-of-stock error appears when quantity exceeds stock
- Cart can be cleared without completing transaction

---

### FR-3: Metode Pembayaran (Payment Methods)
**Description**: System supports multiple extensible payment methods following OCP (Open-Closed Principle).

**Detailed Requirements**:
- FR-3.1: Support CASH payment method (no fees)
- FR-3.2: Support E-WALLET payment (with 2.5% fee)
- FR-3.3: Payment method extensible without modifying core checkout code
- FR-3.4: Calculate total with payment method fee factor
- FR-3.5: Process payment and return success/failure

**Design Pattern**: Strategy Pattern for PaymentMethod interface
- `PaymentMethod` interface defines: getType(), processPayment(), getFeeFactor()
- `CashPayment` implements with feeFactor = 1.0
- `EWalletPayment` implements with feeFactor = 1.025

**Acceptance Criteria**:
- New payment methods can be added without changing TransactionService
- Payment fee is calculated correctly
- System accepts both cash and e-wallet payments

---

### FR-4: Struk dan Laporan (Receipt & Reporting)
**Description**: System displays receipt after successful payment and provides sales reporting.

**Detailed Requirements**:
- FR-4.1: Generate detailed receipt after checkout with:
  - Transaction ID
  - Date/time
  - Item list (product, quantity, unit price, subtotal)
  - Total amount
  - Payment method
  - Change money
- FR-4.2: Display receipt in UI (print preview style)
- FR-4.3: Admin can view daily sales report
- FR-4.4: Admin can view periodic sales report (date range)
- FR-4.5: Report shows: transaction count, total sales, payment breakdown

**Acceptance Criteria**:
- Receipt displays correctly formatted information
- Receipt contains all required details
- Admin can filter transactions by date
- Report calculations are accurate

---

### FR-5: Login dan Hak Akses (Authentication & Authorization)
**Description**: System enforces user authentication and role-based access control.

**Detailed Requirements**:
- FR-5.1: Two user roles: CASHIER and ADMIN
- FR-5.2: Login with username and password
- FR-5.3: Logout functionality
- FR-5.4: CASHIER role can:
  - Create transactions (use cart & checkout)
  - Cannot manage products or view detailed reports
- FR-5.5: ADMIN role can:
  - Manage products (CRUD)
  - View reports and analytics
  - Create transactions (same as CASHIER)
- FR-5.6: Unauthorized access attempt shows error message
- FR-5.7: Session management (remember current logged-in user)

**Acceptance Criteria**:
- Login rejects invalid credentials
- Menu items are disabled for unauthorized users
- Role-based feature access is enforced
- Logout clears current user session

---

## 4. Non-Functional Requirements (NFR)

| NFR ID | Category | Requirement | Target |
|--------|----------|-------------|--------|
| NFR-1 | Performance | Response time for CRUD operations | < 500ms |
| NFR-2 | Performance | Cart calculations | < 100ms |
| NFR-3 | Scalability | Support up to 1000 products | Without degradation |
| NFR-4 | Usability | User interface easy to learn | < 5 minutes training |
| NFR-5 | Reliability | Data persistence (no data loss) | 100% |
| NFR-6 | Security | Password stored securely | Hashed (production) |
| NFR-7 | Maintainability | Code follows SOLID principles | DIP, OCP, SRP |
| NFR-8 | Maintainability | Clear separation of concerns | View, Controller, Service, DAO |
| NFR-9 | Compatibility | Cross-platform GUI | JavaFX on Windows/Linux/Mac |
| NFR-10 | Data Integrity | Referential integrity | Foreign key constraints |

---

## 5. System Constraints

| Constraint | Description |
|-----------|-------------|
| **Technology Stack** | Java 11+, JavaFX, PostgreSQL, JDBC |
| **Database** | PostgreSQL with ACID properties |
| **GUI Framework** | JavaFX (no web UI in this phase) |
| **Architecture** | Layered (View → Controller → Service → DAO → DB) |
| **Design Patterns** | Minimum: Strategy (Payment), Singleton (DB Connection) |
| **Code Quality** | No SQL in GUI layer, DIP compliance required |
| **Testing** | Minimum 1 JUnit test, 8+ manual test cases |

---

## 6. Scope & Out of Scope

### In Scope:
- CRUD product management
- Shopping cart and checkout
- Multiple payment methods (extensible design)
- Receipt generation
- Sales reporting (basic)
- User authentication & role-based access
- Database persistence
- Basic error handling with custom exceptions

### Out of Scope:
- Physical printer integration (receipt printing)
- Email/SMS notifications
- Mobile app version
- Advanced inventory features (expiry dates, barcode scanning)
- Third-party payment gateway integration (production)
- Customer loyalty program (optional FR)
- Advanced analytics/BI

---

## 7. Traceability Matrix

See detailed traceability table in `laporan.md` section 7.

---

## 8. Change Log

| Version | Date | Changes |
|---------|------|---------|
| 1.0 | Jan 29, 2026 | Initial SRS creation |
| 1.1 | Jan 29, 2026 | Added NFR details & constraints |

---

## 9. Approvals & Sign-off

| Role | Name | Signature | Date |
|------|------|-----------|------|
| Project Lead | [Team Lead] | _____ | _____ |
| Architect | [Architect] | _____ | _____ |
| QA Lead | [QA] | _____ | _____ |

---

## Appendix: Glossary

| Term | Definition |
|------|-----------|
| **Agri-POS** | Agricultural Point-of-Sale system |
| **CRUD** | Create, Read, Update, Delete operations |
| **DAO** | Data Access Object pattern for database abstraction |
| **DIP** | Dependency Inversion Principle (SOLID) |
| **OCP** | Open-Closed Principle (SOLID) |
| **Transaction** | A single sales event with multiple products |
| **Payment Method** | Strategy for payment processing (Cash/EWallet) |
| **Authorization** | Checking if user has permission to access feature |
| **Authentication** | Verifying user identity via login |
