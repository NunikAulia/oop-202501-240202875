-- Agri-POS Sample Data (Seed)
-- Week 15 - Proyek Kelompok

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

-- Verify data inserted
SELECT COUNT(*) as user_count FROM users;
SELECT COUNT(*) as product_count FROM products;
