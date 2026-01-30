# Test Plan dan Test Cases
## Agri-POS System - Week 15 Proyek Kelompok

---

## 1. Test Strategy Overview

### Testing Levels
1. **Unit Tests** (Service Layer) - Automated with JUnit & Mockito
2. **Integration Tests** (DAO Layer) - Manual with test database
3. **System Tests** (End-to-End) - Manual GUI testing
4. **UAT (User Acceptance Testing)** - Manual workflow testing

### Testing Approach
- Black-box testing for user workflows
- White-box testing for business logic validation
- Mock objects for DAO dependencies in unit tests
- Real database for integration tests (optional)

---

## 2. Manual Test Cases (GUI Level)

### TC-Login-01: Login with Valid Credentials (Cashier)
**Precondition**: Application running, login screen visible  
**Scenario**: Cashier logs in with correct credentials

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Enter username: `kasir01` | Field accepts input |
| 2 | Enter password: `kasir123` | Field accepts input (masked) |
| 3 | Click "Login" button | Login button enabled |
| 4 | - | System validates credentials |
| 5 | - | Main application screen opens |
| 6 | - | Cashier menu items visible (Transactions) |
| 7 | - | Product management menu disabled |
| **Result** | ✓ PASS | Cashier can access transaction features |

---

### TC-Login-02: Login with Invalid Credentials
**Precondition**: Application running, login screen visible  
**Scenario**: User attempts login with wrong password

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Enter username: `kasir01` | Field accepts input |
| 2 | Enter password: `wrongpassword` | Field accepts input |
| 3 | Click "Login" button | - |
| 4 | - | Error dialog appears: "Invalid password" |
| 5 | - | Login screen remains open |
| 6 | User can retry | - |
| **Result** | ✓ PASS | Wrong credentials rejected |

---

### TC-Login-03: Login with Admin Credentials
**Precondition**: Application running, login screen visible  
**Scenario**: Admin logs in successfully

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Enter username: `admin01` | - |
| 2 | Enter password: `admin123` | - |
| 3 | Click "Login" button | - |
| 4 | - | Main application screen opens |
| 5 | - | Both "Products" AND "Transactions" tabs visible |
| 6 | - | "Reports" tab visible |
| **Result** | ✓ PASS | Admin can access all features |

---

### TC-Product-01: Add New Product
**Precondition**: Admin logged in, on Products tab  
**Scenario**: Admin adds a new agricultural product

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Click "Add Product" button | Add Product dialog opens |
| 2 | Enter code: `BNH-NEW-001` | Field accepts input |
| 3 | Enter name: `Benih Padi Baru` | - |
| 4 | Enter category: `Benih` | - |
| 5 | Enter price: `28000` | - |
| 6 | Enter stock: `150` | - |
| 7 | Click "Save" button | Product saved to database |
| 8 | - | Product list refreshes |
| 9 | - | New product appears in list |
| **Result** | ✓ PASS | FR-1: Product created successfully |

---

### TC-Product-02: Add Product with Duplicate Code
**Precondition**: Admin logged in, product with code `BNH-001` exists  
**Scenario**: Attempt to add product with duplicate code

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Click "Add Product" button | Dialog opens |
| 2 | Enter code: `BNH-001` (duplicate) | - |
| 3 | Enter other details | - |
| 4 | Click "Save" button | Error message: "Product with code BNH-001 already exists" |
| 5 | - | Product NOT added to database |
| **Result** | ✓ PASS | Duplicate prevention works |

---

### TC-Product-03: Delete Product
**Precondition**: Admin logged in, product list visible  
**Scenario**: Delete a product from system

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Click product `BNH-001` in list | Product selected (highlighted) |
| 2 | Click "Delete" button | Confirmation dialog appears |
| 3 | Click "Yes" to confirm | - |
| 4 | - | Product deleted from database |
| 5 | - | Product list refreshes |
| 6 | - | Product no longer in list |
| **Result** | ✓ PASS | FR-1: Product deleted successfully |

---

### TC-Transaction-01: Add Product to Cart
**Precondition**: Cashier logged in, on Transactions tab  
**Scenario**: Add product to shopping cart

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Click "New Transaction" button | New cart created, cart cleared |
| 2 | Select product: `BNH-001` (Benih Padi) | Product appears in dropdown |
| 3 | Enter quantity: `5` | Quantity field accepts input |
| 4 | Click "Add to Cart" button | - |
| 5 | - | Product appears in cart list |
| 6 | - | Cart shows: "Benih Padi x5 = 125000" |
| 7 | - | Cart total updated: 125000 |
| **Result** | ✓ PASS | FR-2: Product added to cart |

---

### TC-Transaction-02: Add Product Out of Stock
**Precondition**: Cashier in transaction, product `BNH-003` (stock: 50)  
**Scenario**: Attempt to add quantity exceeding stock

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Select product: `BNH-003` | - |
| 2 | Enter quantity: `100` (exceeds 50 stock) | - |
| 3 | Click "Add to Cart" button | Error dialog appears: "Out of Stock" |
| 4 | - | Shows: "Product BNH-003: requested 100 but only 50 available" |
| 5 | - | Product NOT added to cart |
| **Result** | ✓ PASS | OutOfStockException thrown correctly |

---

### TC-Transaction-03: Update Cart Quantity
**Precondition**: Cart has item `BNH-001` x5 (125000)  
**Scenario**: Change quantity of item in cart

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | In cart list, select item `BNH-001 x5` | Item highlighted |
| 2 | Change quantity to `10` | Quantity field updated |
| 3 | Click "Update" button | - |
| 4 | - | Cart updates: `BNH-001 x10 = 250000` |
| 5 | - | Cart total recalculates: `250000` |
| **Result** | ✓ PASS | FR-2: Quantity updated correctly |

---

### TC-Transaction-04: Remove Item from Cart
**Precondition**: Cart has 2 items  
**Scenario**: Remove one item from cart

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Select item in cart | Item highlighted |
| 2 | Click "Remove" button | Confirmation dialog |
| 3 | Click "Yes" to confirm | - |
| 4 | - | Item removed from cart |
| 5 | - | Cart list refreshes (1 item remains) |
| 6 | - | Total recalculated |
| **Result** | ✓ PASS | Item removed successfully |

---

### TC-Transaction-05: Checkout with Cash Payment
**Precondition**: Cashier has items in cart (total: 200000)  
**Scenario**: Complete transaction with cash payment

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Click "Checkout" button | Checkout dialog appears |
| 2 | Select payment method: `CASH` | Radio button selected |
| 3 | Enter cash amount: `250000` | Field accepts input |
| 4 | Click "Process Payment" button | - |
| 5 | - | Payment processed (no fee: 200000) |
| 6 | - | Transaction ID generated (e.g., TXN-12345) |
| 7 | - | Receipt dialog appears |
| 8 | - | Receipt shows all items, total, change (50000) |
| **Result** | ✓ PASS | FR-3, FR-4: Checkout & receipt complete |

---

### TC-Transaction-06: Checkout with E-Wallet Payment
**Precondition**: Cashier has items in cart (total: 100000)  
**Scenario**: Complete transaction with e-wallet (2.5% fee)

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Click "Checkout" button | Checkout dialog appears |
| 2 | Select payment method: `E-WALLET` | Radio button selected |
| 3 | Select wallet: `GOPAY` | Dropdown opened |
| 4 | Click "Process Payment" button | - |
| 5 | - | Amount calculated: 100000 x 1.025 = 102500 |
| 6 | - | Transaction saved |
| 7 | - | Receipt shows: "Payment (EWALLET-GOPAY): 102500" |
| **Result** | ✓ PASS | FR-3: E-wallet with fee calculated |

---

### TC-Transaction-07: Checkout with Empty Cart
**Precondition**: Cashier on transactions tab, cart is empty  
**Scenario**: Attempt checkout with no items

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Click "Checkout" button | - |
| 2 | - | Error dialog: "Cart is empty. Add items first." |
| 3 | - | Checkout dialog does NOT open |
| **Result** | ✓ PASS | Empty cart validation works |

---

### TC-Report-01: View Daily Sales Report
**Precondition**: Admin logged in, on Reports tab, transactions exist  
**Scenario**: View sales report for today

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Click "Daily Report" button | Report dialog opens |
| 2 | - | Shows: Total transactions (e.g., 5) |
| 3 | - | Shows: Total sales (e.g., 2500000) |
| 4 | - | Shows: Payment breakdown |
| 5 | - | Shows: Cash vs E-Wallet totals |
| **Result** | ✓ PASS | FR-4: Daily report displays |

---

### TC-Authorization-01: Cashier Cannot Access Product Management
**Precondition**: Cashier logged in  
**Scenario**: Verify menu restrictions

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Look at menu tabs | "Products" tab is DISABLED or hidden |
| 2 | Look at menu tabs | "Transactions" tab is ENABLED |
| 3 | Look at menu tabs | "Reports" tab is DISABLED or hidden |
| **Result** | ✓ PASS | FR-5: Cashier access control enforced |

---

### TC-Authorization-02: Admin Can Access All Features
**Precondition**: Admin logged in  
**Scenario**: Verify all features available to admin

| Step | Action | Expected Result |
|------|--------|-----------------|
| 1 | Look at menu tabs | All tabs visible: Products, Transactions, Reports |
| 2 | Try to add product | "Add Product" button works |
| 3 | Go to Transactions | Can create new transaction |
| 4 | Go to Reports | Can view reports |
| **Result** | ✓ PASS | FR-5: Admin has full access |

---

## 3. Unit Test Execution Results

### Test Suite: ProductServiceTest
```
[INFO] Running com.upb.agripos.service.ProductServiceTest
[PASS] testAddProductSuccess
[PASS] testAddProductWithEmptyCode
[PASS] testAddProductWithNegativePrice
[PASS] testAddProductWithNegativeStock
[PASS] testAddDuplicateProduct
[PASS] testGetProduct
[PASS] testGetAllProducts
[PASS] testUpdateProduct
[PASS] testDeleteProduct
[PASS] testDeleteNonExistentProduct

Tests run: 10, Failures: 0, Errors: 0
```

### Test Suite: CartServiceTest
```
[INFO] Running com.upb.agripos.service.CartServiceTest
[PASS] testAddProductToCartSuccess
[PASS] testAddProductOutOfStock
[PASS] testAddSameProductTwice
[PASS] testUpdateCartItemQuantity
[PASS] testRemoveProductFromCart
[PASS] testClearCart
[PASS] testCalculateCartTotalCorrectly

Tests run: 7, Failures: 0, Errors: 0
```

### Test Suite: TransactionServiceTest
```
[INFO] Running com.upb.agripos.service.TransactionServiceTest
[PASS] testCheckoutWithCashPayment
[PASS] testCheckoutWithEWalletPayment
[PASS] testCheckoutWithEmptyCart
[PASS] testCheckoutWithNullPaymentMethod
[PASS] testGenerateReceipt
[PASS] testGenerateReceiptWithNullTransaction
[PASS] testFeeFactorCalculation

Tests run: 7, Failures: 0, Errors: 0
```

---

## 4. Test Coverage Summary

| Layer | Component | Test Type | Coverage |
|-------|-----------|-----------|----------|
| **Service** | ProductService | Unit (JUnit) | 10 test cases |
| **Service** | CartService | Unit (JUnit) | 7 test cases |
| **Service** | TransactionService | Unit (JUnit) | 7 test cases |
| **Service** | AuthService | Manual | Login/Logout flows |
| **DAO** | ProductDAO | Integration | CRUD operations |
| **DAO** | UserDAO | Integration | User management |
| **Controller** | ProductController | Manual | UI interaction |
| **Controller** | TransactionController | Manual | Checkout flow |
| **View** | MainView | Manual | GUI workflows |
| **Exception** | ValidationException | Unit | Input validation |
| **Exception** | OutOfStockException | Unit | Stock validation |
| **Model** | Product, Cart, Transaction | Unit | Object behavior |
| **Payment** | Strategy Pattern | Unit | Fee calculations |

**Total: 24 Unit Tests + 7 Manual Test Scenarios = 31 test cases**

---

## 5. Defect Reporting

### Severity Levels
- **Critical**: Feature does not work, data loss possible
- **Major**: Feature partially broken, workaround available
- **Minor**: UI cosmetic issue, functionality intact
- **Trivial**: Documentation error, typo

### Known Issues / To Fix
1. (None reported during testing)

---

## 6. Test Execution Checklist

- [x] Unit tests written and passing
- [x] Manual test cases documented
- [x] Test data prepared
- [x] All FR requirements tested
- [x] Exception flows tested
- [x] Authorization tested
- [x] Payment method extensibility tested
- [x] Receipt generation tested

---

## 7. Sign-off

| Date | Tester | Status | Notes |
|------|--------|--------|-------|
| 2026-01-29 | QA Team | ✓ PASS | All tests passed, ready for UAT |

---

## 8. Recommendations

1. **Automated Testing**: Expand unit test coverage for DAO layer
2. **Integration Testing**: Setup test database for DAO integration tests
3. **Performance Testing**: Load test with 1000+ products
4. **Security Testing**: SQL injection, password handling
5. **Usability Testing**: User feedback on GUI design
6. **Regression Testing**: Repeat all tests after bug fixes

---

## Appendix: Test Data

### Default Credentials
```
Cashier: kasir01 / kasir123
Admin:   admin01 / admin123
```

### Sample Products (for testing)
```
BNH-001: Benih Padi Premium - 25000 (stock: 100)
BNH-002: Benih Jagung Hibrida - 35000 (stock: 80)
FER-001: Pupuk NPK - 75000 (stock: 200)
PES-001: Insektisida - 65000 (stock: 120)
TLS-001: Selang Irigasi - 180000 (stock: 50)
```
