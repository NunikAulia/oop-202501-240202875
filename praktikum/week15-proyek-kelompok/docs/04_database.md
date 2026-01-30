# Database Design - Agri-POS System
## Week 15 - Proyek Kelompok

---

## 1. Entity Relationship Diagram (ERD)

```
┌─────────────────┐         ┌────────────────────┐
│    users        │         │    products        │
├─────────────────┤         ├────────────────────┤
│ username (PK)   │         │ code (PK)          │
│ password        │         │ name               │
│ name            │         │ category           │
│ role            │         │ price              │
└─────────────────┘         │ stock              │
        ▲                    └────────────────────┘
        │                            ▲
        │                            │
        │                    (referenced by)
        │                            │
┌───────┴──────────────────────────────────┐
│        transactions                       │
├──────────────────────────────────────────┤
│ id (PK)                                  │
│ timestamp                                │
│ total                                    │
│ payment_method                           │
│ status                                   │
└──────────────────────────────────────────┘
        ▼
┌──────────────────────────────────────────┐
│     transaction_items                    │
├──────────────────────────────────────────┤
│ id (PK)                                  │
│ transaction_id (FK)                      │
│ product_code (FK)                        │
│ quantity                                 │
│ unit_price                               │
└──────────────────────────────────────────┘
```

---

## 2. Database Schema (DDL)

```sql
-- Create users table (FR-5: Login & Access Control)
CREATE TABLE users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('CASHIER', 'ADMIN')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create products table (FR-1: Product Management)
CREATE TABLE products (
    code VARCHAR(20) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(50),
    price DECIMAL(12, 2) NOT NULL CHECK (price > 0),
    stock INT NOT NULL CHECK (stock >= 0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create transactions table (FR-2 & FR-4: Sales Transaction & Receipt)
CREATE TABLE transactions (
    id VARCHAR(50) PRIMARY KEY,
    timestamp TIMESTAMP NOT NULL,
    total DECIMAL(12, 2) NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('COMPLETED', 'PENDING', 'CANCELLED')),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create transaction_items table (detail items per transaction)
CREATE TABLE transaction_items (
    id SERIAL PRIMARY KEY,
    transaction_id VARCHAR(50) NOT NULL,
    product_code VARCHAR(20) NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    unit_price DECIMAL(12, 2) NOT NULL,
    subtotal DECIMAL(12, 2) NOT NULL,
    FOREIGN KEY (transaction_id) REFERENCES transactions(id),
    FOREIGN KEY (product_code) REFERENCES products(code)
);

-- Create indexes for better query performance
CREATE INDEX idx_transactions_timestamp ON transactions(timestamp);
CREATE INDEX idx_transactions_status ON transactions(status);
CREATE INDEX idx_transaction_items_transaction ON transaction_items(transaction_id);
CREATE INDEX idx_product_category ON products(category);
```

---

## 3. Sample Data (SEED)

```sql
-- Insert default users (FR-5)
INSERT INTO users (username, password, name, role) VALUES
('kasir01', 'kasir123', 'Budi Kasir', 'CASHIER'),
('kasir02', 'kasir456', 'Ani Kasir', 'CASHIER'),
('admin01', 'admin123', 'Admin Utama', 'ADMIN'),
('admin02', 'admin456', 'Admin Backup', 'ADMIN');

-- Insert sample products (FR-1)
INSERT INTO products (code, name, category, price, stock) VALUES
('BNH-001', 'Benih Padi Premium', 'Benih', 25000, 100),
('BNH-002', 'Benih Jagung Hibrida', 'Benih', 35000, 80),
('BNH-003', 'Benih Cabai Merah', 'Benih', 45000, 50),
('FER-001', 'Pupuk NPK 16-16-16', 'Pupuk', 75000, 200),
('FER-002', 'Pupuk Urea', 'Pupuk', 55000, 250),
('FER-003', 'Pupuk Kompos Organik', 'Pupuk', 30000, 300),
('PES-001', 'Insektisida Sintetik 500ml', 'Pestisida', 65000, 120),
('PES-002', 'Fungisida Organik 1L', 'Pestisida', 85000, 80),
('TLS-001', 'Selang Irigasi 20m', 'Alat', 180000, 50),
('TLS-002', 'Pompa Air 1HP', 'Alat', 2500000, 15);
```

---

## 4. Database Setup Steps

### Prerequisite:
- PostgreSQL 12+ installed
- psql command-line tool or pgAdmin available

### Steps:

1. **Create database:**
   ```bash
   createdb agripos
   ```

2. **Connect to database:**
   ```bash
   psql -U postgres agripos
   ```

3. **Run DDL (create tables):**
   ```bash
   psql -U postgres agripos -f schema.sql
   ```

4. **Run seed data:**
   ```bash
   psql -U postgres agripos -f seed.sql
   ```

5. **Verify tables created:**
   ```sql
   \dt
   ```

---

## 5. Access Pattern via DAO

### ProductDAO Access
- **Create**: Insert into `products` table
- **Read**: SELECT from `products` WHERE code = ?
- **Update**: UPDATE `products` SET ... WHERE code = ?
- **Delete**: DELETE FROM `products` WHERE code = ?
- **ReadAll**: SELECT * FROM `products` ORDER BY code

### UserDAO Access
- **Create**: Insert into `users` table (hashing password recommended for production)
- **Read**: SELECT from `users` WHERE username = ?
- **Update**: UPDATE `users` SET password, name, role WHERE username = ?
- **Delete**: DELETE FROM `users` WHERE username = ?

### TransactionDAO Access
- **Save**: Insert into `transactions` + `transaction_items`
- **GetById**: SELECT from `transactions` + join with `transaction_items`
- **GetByDateRange**: SELECT WHERE timestamp BETWEEN ? AND ?

---

## 6. Connection Configuration

**Database URL**: `jdbc:postgresql://localhost:5432/agripos`
**Default User**: `postgres`
**Default Password**: `1234` (dapat diubah)

Connection setup di Java:
```java
String url = "jdbc:postgresql://localhost:5432/agripos";
String user = "postgres";
String password = "1234";
Connection conn = DriverManager.getConnection(url, user, password);
```

---

## 7. Notes & Constraints

- **Data Integrity**: Foreign keys ensure referential integrity
- **Stock Management**: CHECK constraint ensures stock >= 0
- **Price Validation**: CHECK constraint ensures price > 0
- **Timestamps**: Automatic tracking of created_at/updated_at
- **Transaction Status**: Only allows COMPLETED, PENDING, CANCELLED
- **User Roles**: Only CASHIER and ADMIN roles allowed
- **Indexes**: Created for frequently queried columns (timestamp, category)

---

## 8. Backup & Recovery

**Backup database:**
```bash
pg_dump agripos > agripos_backup.sql
```

**Restore database:**
```bash
createdb agripos_restored
psql -U postgres agripos_restored < agripos_backup.sql
```
