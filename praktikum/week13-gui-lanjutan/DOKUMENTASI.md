# Week 13 - GUI Lanjutan - COMPLETED ✅

## Summary
Week 13 "GUI Lanjutan" (Advanced GUI) implementation completed successfully with full Java Swing application featuring JTable, menu bars, dialogs, and CRUD operations.

---

## Files Created

### 1. Product.java
- **Location**: `src/main/java/com/upb/agripos/Product.java`
- **Purpose**: Model class for product data
- **Features**:
  - Attributes: id, name, price, stock
  - Full getter/setter implementation
  - toString() method for debugging
  - Constructor with all parameters

### 2. ProductTableFrame.java
- **Location**: `src/main/java/com/upb/agripos/ProductTableFrame.java`
- **Purpose**: Main GUI application window with JTable
- **Size**: 13.7 KB (13,711 bytes)
- **Features**:
  - JTable with DefaultTableModel (4 columns: ID, Name, Price, Stock)
  - JMenuBar with File, Edit, Help menus
  - Toolbar with search functionality
  - Button panel (Add, Edit, Delete, Refresh)
  - Modal dialogs for Add/Edit operations
  - Confirmation dialogs for delete
  - In-memory ArrayList for data storage
  - Sample data initialization (5 products)
  - Case-insensitive search filtering
  - Input validation with error handling

### 3. laporan.md
- **Location**: `laporan.md`
- **Purpose**: Complete practical report
- **Size**: 11.7 KB (11,742 bytes)
- **Content**:
  - Objectives and goals
  - Theoretical foundation (JTable, MenuBar, Dialog, Lambda)
  - Implementation steps (5 langkah)
  - Code examples
  - Analysis and comparison with Week 12
  - Troubleshooting guide
  - 8 quiz questions with answers
  - References to official documentation

---

## Key Features Implemented

### GUI Components
- ✅ **JTable**: Structured data display with 4 columns
- ✅ **DefaultTableModel**: Dynamic data model
- ✅ **JMenuBar**: Professional menu navigation
- ✅ **JMenu/JMenuItem**: File, Edit, Help menus
- ✅ **JDialog**: Modal dialogs for add/edit
- ✅ **JOptionPane**: Message and confirmation dialogs
- ✅ **JTextField**: Input fields for search and form
- ✅ **JButton**: Action buttons (Add, Edit, Delete, Refresh)
- ✅ **JLabel**: Labels for UI elements
- ✅ **JScrollPane**: Scrollable table
- ✅ **JToolBar**: Search toolbar with text field

### Functionality
- ✅ **Create (Add)**: Modal dialog with validation, duplicate ID check
- ✅ **Read (Display)**: JTable showing all products
- ✅ **Update (Edit)**: Modal dialog with pre-filled data, read-only ID
- ✅ **Delete**: Confirmation dialog, remove from ArrayList
- ✅ **Search**: Filter by ID or name, case-insensitive
- ✅ **Refresh**: Reload table data
- ✅ **Menu Navigation**: File → Exit, Edit → Add/Delete, Help → About

### Event Handling
- ✅ ActionListener for buttons
- ✅ ActionListener for menu items
- ✅ Lambda expressions for concise code
- ✅ Selection handling for edit/delete

### Data Validation
- ✅ Empty field check
- ✅ Duplicate ID prevention
- ✅ Number format validation (try-catch)
- ✅ User feedback via JOptionPane

---

## Architecture

```
ProductTableFrame (extends JFrame)
├── createMenuBar()
│   ├── File Menu (Exit)
│   ├── Edit Menu (Add, Delete)
│   └── Help Menu (About)
├── createToolbar()
│   └── Search Panel (TextField + Button)
├── createTablePanel()
│   └── JTable with DefaultTableModel
├── createButtonPanel()
│   ├── Add Button
│   ├── Edit Button
│   ├── Delete Button
│   └── Refresh Button
├── showAddDialog() → Modal JDialog
├── showEditDialog() → Modal JDialog
├── deleteProduct() → Confirmation Dialog
├── searchProducts() → Filter ArrayList
└── loadTableData() → Refresh JTable
```

---

## Data Model

### Product.java
```
Product
├── id: String
├── name: String
├── price: int
├── stock: int
└── Methods:
    ├── getId/setId
    ├── getName/setName
    ├── getPrice/setPrice
    ├── getStock/setStock
    └── toString()
```

### JTable Structure
| Column | Type | Source |
|--------|------|--------|
| ID | String | product.getId() |
| Name | String | product.getName() |
| Price (Rp) | int | product.getPrice() |
| Stock | int | product.getStock() |

---

## Sample Data

Initial data loaded into application:

1. BNH-001: Benih Padi Premium (25000 Rp, 100 units)
2. BNH-002: Benih Jagung Hibrida (15000 Rp, 150 units)
3. PUP-001: Pupuk Urea 50kg (250000 Rp, 50 units)
4. PUP-002: Pupuk NPK 25kg (180000 Rp, 75 units)
5. ALT-001: Cangkul Baja (75000 Rp, 25 units)

---

## Compilation & Execution

### Compile
```bash
cd d:\oop-202501-240202875\praktikum\week13-gui-lanjutan\src\main\java
javac -d . com/upb/agripos/Product.java com/upb/agripos/ProductTableFrame.java
```

### Run
```bash
java com.upb.agripos.ProductTableFrame
```

### Status: ✅ COMPILES WITHOUT ERRORS

---

## Testing Checklist

- ✅ Application launches and displays table with sample data
- ✅ Menu File → Exit closes application
- ✅ Menu Edit → Add Product opens modal dialog
- ✅ Add Product validates empty fields
- ✅ Add Product prevents duplicate IDs
- ✅ Add Product validates number format
- ✅ Edit Product button and menu work
- ✅ Edit Product has read-only ID field
- ✅ Delete Product shows confirmation dialog
- ✅ Delete Product removes data
- ✅ Search filters by name and ID
- ✅ Search is case-insensitive
- ✅ Refresh button reloads table
- ✅ Help → About shows info dialog
- ✅ Table updates after all operations

---

## Comparison: Week 12 vs Week 13

| Feature | Week 12 (GUI Dasar) | Week 13 (GUI Lanjutan) |
|---------|-------------------|----------------------|
| Main Display | JTextArea | JTable ✅ |
| Menu System | None | JMenuBar ✅ |
| Input Method | Single form | Multiple dialogs ✅ |
| Data Format | Text/String | Structured table ✅ |
| CRUD Operations | Add only | Add/Edit/Delete ✅ |
| Search | None | Yes, case-insensitive ✅ |
| Dialogs | None | Modal add/edit/confirm ✅ |
| Event Handling | Anonymous classes | Lambda expressions ✅ |

---

## Code Quality

- ✅ No compilation errors
- ✅ No runtime errors (tested)
- ✅ Proper error handling (try-catch)
- ✅ Input validation
- ✅ User feedback via dialogs
- ✅ Clean method organization
- ✅ Meaningful variable names
- ✅ Comments where needed

---

## Resources Used

- Java Swing API
- DefaultTableModel for table management
- ArrayList for in-memory storage
- JOptionPane for dialogs
- Lambda expressions for event handling

---

## Next Steps (Week 14)

Possible enhancements for Week 14+:
- Integrate with database (JDBC/JPA)
- Add file import/export (CSV/JSON)
- Implement sorting and filtering in JTable
- Add custom table cell renderers
- Implement service layer pattern
- Add logging functionality
- Implement undo/redo
- Add preferences/settings

---

## Commit Ready

All code is ready to commit with message:
```
feat: add week 13 advanced GUI with JTable and dialogs
- Create ProductTableFrame with JTable for product list
- Implement JMenuBar with File, Edit, Help menus
- Add Add/Edit/Delete product dialogs with validation
- Implement search functionality and table refresh
- Use lambda expressions for event handling
- Complete documentation with theory and examples
```

---

## Status: COMPLETE ✅

Week 13 implementation is complete and fully functional. All requirements for GUI Lanjutan have been met.
