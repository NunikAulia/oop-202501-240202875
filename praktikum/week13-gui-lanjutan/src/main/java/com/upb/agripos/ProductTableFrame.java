package com.upb.agripos;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * GUI Lanjutan untuk Product Management dengan JTable
 */
public class ProductTableFrame extends JFrame {
    private JTable productTable;
    private DefaultTableModel tableModel;
    private List<Product> productList;
    private JButton buttonAdd, buttonEdit, buttonDelete, buttonRefresh;
    private JTextField searchField;

    public ProductTableFrame() {
        setTitle("AgriPOS - Product Management (Advanced GUI)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(true);

        // Initialize product list with sample data
        productList = new ArrayList<>();
        initSampleData();

        // Create menu bar
        createMenuBar();

        // Create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create toolbar
        JPanel toolbarPanel = createToolbar();
        mainPanel.add(toolbarPanel, BorderLayout.NORTH);

        // Create table panel
        JPanel tablePanel = createTablePanel();
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        // Create button panel
        JPanel buttonPanel = createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void initSampleData() {
        productList.add(new Product("BNH-001", "Benih Padi Premium", 25000, 100));
        productList.add(new Product("BNH-002", "Benih Jagung Hibrida", 15000, 150));
        productList.add(new Product("PUP-001", "Pupuk Urea 50kg", 250000, 50));
        productList.add(new Product("PUP-002", "Pupuk NPK 25kg", 180000, 75));
        productList.add(new Product("ALT-001", "Cangkul Baja", 75000, 25));
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // File Menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        // Edit Menu
        JMenu editMenu = new JMenu("Edit");
        JMenuItem addItem = new JMenuItem("Add Product");
        addItem.addActionListener(e -> showAddDialog());
        JMenuItem deleteItem = new JMenuItem("Delete Selected");
        deleteItem.addActionListener(e -> deleteProduct());
        editMenu.add(addItem);
        editMenu.addSeparator();
        editMenu.add(deleteItem);

        // Help Menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "AgriPOS - Advanced Product Management\nVersion 1.0",
                "About", JOptionPane.INFORMATION_MESSAGE));
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }

    private JPanel createToolbar() {
        JPanel toolbarPanel = new JPanel();
        toolbarPanel.setLayout(new BorderLayout());
        toolbarPanel.setBorder(BorderFactory.createTitledBorder("Search & Filter"));

        JLabel searchLabel = new JLabel("Search by Name:");
        searchField = new JTextField();
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> searchProducts());

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        toolbarPanel.add(searchPanel, BorderLayout.WEST);
        return toolbarPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Product List"));

        // Create table model
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"ID", "Name", "Price (Rp)", "Stock"});
        
        // Create table
        productTable = new JTable(tableModel);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        productTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Add scroll pane
        JScrollPane scrollPane = new JScrollPane(productTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Load data into table
        loadTableData();

        return tablePanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        buttonAdd = new JButton("Add Product");
        buttonAdd.setPreferredSize(new Dimension(120, 35));
        buttonAdd.addActionListener(e -> showAddDialog());

        buttonEdit = new JButton("Edit Product");
        buttonEdit.setPreferredSize(new Dimension(120, 35));
        buttonEdit.addActionListener(e -> showEditDialog());

        buttonDelete = new JButton("Delete Product");
        buttonDelete.setPreferredSize(new Dimension(120, 35));
        buttonDelete.addActionListener(e -> deleteProduct());

        buttonRefresh = new JButton("Refresh");
        buttonRefresh.setPreferredSize(new Dimension(120, 35));
        buttonRefresh.addActionListener(e -> loadTableData());

        buttonPanel.add(buttonAdd);
        buttonPanel.add(buttonEdit);
        buttonPanel.add(buttonDelete);
        buttonPanel.add(buttonRefresh);

        return buttonPanel;
    }

    private void loadTableData() {
        // Clear existing rows
        tableModel.setRowCount(0);

        // Add all products to table
        for (Product product : productList) {
            tableModel.addRow(new Object[]{
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getStock()
            });
        }
    }

    private void showAddDialog() {
        JDialog dialog = new JDialog(this, "Add New Product", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel labelId = new JLabel("Product ID:");
        JTextField textId = new JTextField();
        JLabel labelName = new JLabel("Product Name:");
        JTextField textName = new JTextField();
        JLabel labelPrice = new JLabel("Price (Rp):");
        JTextField textPrice = new JTextField();
        JLabel labelStock = new JLabel("Stock:");
        JTextField textStock = new JTextField();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        panel.add(labelId);
        panel.add(textId);
        panel.add(labelName);
        panel.add(textName);
        panel.add(labelPrice);
        panel.add(textPrice);
        panel.add(labelStock);
        panel.add(textStock);
        panel.add(saveButton);
        panel.add(cancelButton);

        saveButton.addActionListener(e -> {
            if (textId.getText().isEmpty() || textName.getText().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "ID and Name cannot be empty!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                String id = textId.getText();
                String name = textName.getText();
                int price = Integer.parseInt(textPrice.getText());
                int stock = Integer.parseInt(textStock.getText());

                // Check if ID already exists
                for (Product p : productList) {
                    if (p.getId().equals(id)) {
                        JOptionPane.showMessageDialog(dialog, "Product ID already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                Product newProduct = new Product(id, name, price, stock);
                productList.add(newProduct);
                loadTableData();
                dialog.dispose();

                JOptionPane.showMessageDialog(this, "Product added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Price and Stock must be numbers!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showEditDialog() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a product to edit!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Product selectedProduct = productList.get(selectedRow);

        JDialog dialog = new JDialog(this, "Edit Product", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel labelId = new JLabel("Product ID:");
        JTextField textId = new JTextField(selectedProduct.getId());
        textId.setEditable(false);
        JLabel labelName = new JLabel("Product Name:");
        JTextField textName = new JTextField(selectedProduct.getName());
        JLabel labelPrice = new JLabel("Price (Rp):");
        JTextField textPrice = new JTextField(String.valueOf(selectedProduct.getPrice()));
        JLabel labelStock = new JLabel("Stock:");
        JTextField textStock = new JTextField(String.valueOf(selectedProduct.getStock()));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        panel.add(labelId);
        panel.add(textId);
        panel.add(labelName);
        panel.add(textName);
        panel.add(labelPrice);
        panel.add(textPrice);
        panel.add(labelStock);
        panel.add(textStock);
        panel.add(saveButton);
        panel.add(cancelButton);

        saveButton.addActionListener(e -> {
            if (textName.getText().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Name cannot be empty!", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                selectedProduct.setName(textName.getText());
                selectedProduct.setPrice(Integer.parseInt(textPrice.getText()));
                selectedProduct.setStock(Integer.parseInt(textStock.getText()));
                loadTableData();
                dialog.dispose();

                JOptionPane.showMessageDialog(this, "Product updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Price and Stock must be numbers!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void deleteProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a product to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this product?",
                "Confirm Delete", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            productList.remove(selectedRow);
            loadTableData();
            JOptionPane.showMessageDialog(this, "Product deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void searchProducts() {
        String keyword = searchField.getText().toLowerCase();
        if (keyword.isEmpty()) {
            loadTableData();
            return;
        }

        tableModel.setRowCount(0);
        for (Product product : productList) {
            if (product.getName().toLowerCase().contains(keyword) || product.getId().toLowerCase().contains(keyword)) {
                tableModel.addRow(new Object[]{
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getStock()
                });
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ProductTableFrame frame = new ProductTableFrame();
                frame.setVisible(true);
            }
        });
    }
}
