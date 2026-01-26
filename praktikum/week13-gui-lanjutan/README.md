# Week 13 - GUI Lanjutan (Advanced GUI)

## 🎯 Project Overview

Advanced GUI application implementing Java Swing with:
- **JTable** for structured data display
- **JMenuBar** for application navigation
- **JDialog** for user interactions
- **CRUD Operations** (Create, Read, Update, Delete)
- **Search & Filter** functionality

## 📁 Project Structure

```
week13-gui-lanjutan/
├── src/main/java/com/upb/agripos/
│   ├── Product.java                    # Model class
│   └── ProductTableFrame.java          # Main GUI application
├── laporan.md                          # Complete practical report
├── DOKUMENTASI.md                      # Project documentation
└── README.md                           # This file
```

## ⚙️ Compilation & Execution

### Prerequisites
- Java 8+ (tested with Java 17)
- Command line/terminal access

### Compile
```bash
cd week13-gui-lanjutan/src/main/java
javac -d . com/upb/agripos/Product.java com/upb/agripos/ProductTableFrame.java
```

### Run
```bash
java com.upb.agripos.ProductTableFrame
```

## ✨ Features

### GUI Components
| Component | Purpose | Implementation |
|-----------|---------|-----------------|
| **JTable** | Display products in structured table | 4-column grid (ID, Name, Price, Stock) |
| **JMenuBar** | Application menu | File, Edit, Help menus |
| **JDialog** | Modal dialogs | Add/Edit product forms |
| **JToolBar** | Search functionality | Text field + search button |
| **JButton** | Action buttons | Add, Edit, Delete, Refresh |
| **JOptionPane** | Messages & confirmations | Info, warning, confirm dialogs |

### Operations
- ✅ **ADD** - Create new product with validation
- ✅ **READ** - Display all products in JTable
- ✅ **EDIT** - Modify existing product
- ✅ **DELETE** - Remove product with confirmation
- ✅ **SEARCH** - Filter by name or ID (case-insensitive)
- ✅ **REFRESH** - Reload table data

### Sample Data
The application starts with 5 pre-loaded products:
1. **BNH-001** - Benih Padi Premium (25,000 Rp, 100 unit)
2. **BNH-002** - Benih Jagung Hibrida (15,000 Rp, 150 unit)
3. **PUP-001** - Pupuk Urea 50kg (250,000 Rp, 50 unit)
4. **PUP-002** - Pupuk NPK 25kg (180,000 Rp, 75 unit)
5. **ALT-001** - Cangkul Baja (75,000 Rp, 25 unit)

## 📋 Usage Guide

### Add Product
1. Click "Add Product" button or Menu → Edit → Add Product
2. Fill in ID, Name, Price, Stock
3. Click "Save" to add (ID must be unique)
4. Click "Cancel" to discard

### Edit Product
1. Select product in table
2. Click "Edit Product" button
3. Modify Name, Price, Stock (ID is read-only)
4. Click "Save" to update

### Delete Product
1. Select product in table
2. Click "Delete Product" button
3. Confirm deletion in dialog
4. Product is removed from table

### Search Products
1. Type product name or ID in search field
2. Click "Search" button
3. Table shows filtered results
4. Clear search field and click "Search" to reset

### Menu Navigation
- **File → Exit**: Close application
- **Edit → Add Product**: Open add dialog
- **Edit → Delete Selected**: Delete selected product
- **Help → About**: Show about information

## 🔍 Code Structure

### Product.java (Model)
```java
public class Product {
    private String id;
    private String name;
    private int price;
    private int stock;
    // getter, setter, toString
}
```

### ProductTableFrame.java (View & Controller)
- `createMenuBar()` - Setup menu bar
- `createToolbar()` - Setup search toolbar
- `createTablePanel()` - Setup JTable
- `createButtonPanel()` - Setup buttons
- `loadTableData()` - Populate table from ArrayList
- `showAddDialog()` - Modal dialog for adding
- `showEditDialog()` - Modal dialog for editing
- `deleteProduct()` - Delete with confirmation
- `searchProducts()` - Filter products

## 📝 Input Validation

The application validates:
- ✓ Empty field check (ID, Name required)
- ✓ Duplicate ID prevention
- ✓ Number format validation (Price, Stock must be numeric)
- ✓ User-friendly error messages

## 🎓 Learning Outcomes

After completing this practical, students can:
1. Create and manage JTable with DefaultTableModel
2. Implement menu bars and navigation
3. Create modal dialogs for user input
4. Handle events with lambda expressions
5. Validate user input and provide feedback
6. Implement search/filter functionality
7. Manage in-memory data with ArrayList
8. Build professional GUI applications

## 📚 Documentation

- **laporan.md** - Complete practical report with:
  - Objectives and theory
  - Implementation steps
  - Code examples
  - Analysis and comparison
  - Quiz with answers
  
- **DOKUMENTASI.md** - Project documentation with:
  - File descriptions
  - Features list
  - Testing checklist
  - Architecture diagram

## 🔧 Technical Details

- **Language**: Java
- **API**: Java Swing (javax.swing)
- **Data Structure**: ArrayList<Product>
- **Architecture**: MVC-like pattern
- **Event Handling**: ActionListener with lambda expressions
- **UI Paradigm**: Event-driven programming

## 📦 Dependencies

- Java SE 8+ (includes Swing)
- No external libraries required

## ✅ Quality Assurance

- ✓ Compiles without errors (Java 17)
- ✓ No runtime exceptions (tested)
- ✓ Input validation implemented
- ✓ Error handling with try-catch
- ✓ User feedback via dialogs
- ✓ Code documentation

## 🚀 Running the Application

### Step 1: Navigate to project directory
```bash
cd d:\oop-202501-240202875\praktikum\week13-gui-lanjutan
```

### Step 2: Compile
```bash
cd src/main/java
javac -d . com/upb/agripos/Product.java com/upb/agripos/ProductTableFrame.java
```

### Step 3: Run
```bash
java com.upb.agripos.ProductTableFrame
```

### Expected Output
A GUI window appears with:
- Title: "AgriPOS - Product Management (Advanced GUI)"
- Size: 900x600 pixels
- Menu bar at top
- Search toolbar
- Product table in center
- Action buttons at bottom

## 🔗 Comparison with Week 12

| Aspect | Week 12 | Week 13 |
|--------|---------|---------|
| **Display Component** | JTextArea | JTable ✅ |
| **User Navigation** | Single form | Menu bar ✅ |
| **Input Method** | Form only | Dialogs ✅ |
| **Data Structure** | Text output | Structured table ✅ |
| **Available Operations** | Add only | Add/Edit/Delete ✅ |
| **Search Capability** | None | Yes ✅ |
| **Dialog Boxes** | No | Yes ✅ |
| **Modern Features** | Basic | Lambda expressions ✅ |

## 📞 Support

For issues or questions:
1. Check error messages in console
2. Verify Java version: `javac -version`
3. Ensure all files in correct directories
4. Review laporan.md for detailed explanation
5. Check DOKUMENTASI.md for troubleshooting

## 📄 License

Educational material - Universitas Pendidikan Bhinneka

---

**Status**: ✅ COMPLETE AND TESTED

Last Updated: 2026-01-20
