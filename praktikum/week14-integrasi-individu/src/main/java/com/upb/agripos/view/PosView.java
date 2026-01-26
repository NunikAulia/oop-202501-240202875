package com.upb.agripos.view;

import com.upb.agripos.controller.PosController;
import com.upb.agripos.model.Product;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * PosView provides the GUI for the Agri-POS application
 * Implements proper separation of concerns (View does not access DAO directly)
 */
public class PosView extends JFrame {
    private final PosController controller;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JTextArea cartArea;
    private JLabel cartTotalLabel;
    private JButton buttonAddProduct, buttonEditProduct, buttonDeleteProduct;
    private JButton buttonAddToCart, buttonClearCart;

    public PosView(PosController controller) {
        this.controller = controller;
        initializeUI();
        loadProductsData();
    }

    private void initializeUI() {
        setTitle("Agri-POS - Point of Sale System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(true);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create menu bar
        createMenuBar();

        // Top panel - Products section
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createTitledBorder("Products"));
        
        // Product table
        tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(new String[]{"Code", "Name", "Price", "Stock"});
        productTable = new JTable(tableModel);
        productTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(productTable);
        topPanel.add(scrollPane, BorderLayout.CENTER);

        // Product buttons
        JPanel productButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonAddProduct = new JButton("Add Product");
        buttonEditProduct = new JButton("Edit Product");
        buttonDeleteProduct = new JButton("Delete Product");

        buttonAddProduct.addActionListener(e -> showAddProductDialog());
        buttonEditProduct.addActionListener(e -> showEditProductDialog());
        buttonDeleteProduct.addActionListener(e -> deleteProduct());

        productButtonPanel.add(buttonAddProduct);
        productButtonPanel.add(buttonEditProduct);
        productButtonPanel.add(buttonDeleteProduct);
        topPanel.add(productButtonPanel, BorderLayout.SOUTH);

        // Bottom panel - Cart section
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createTitledBorder("Shopping Cart"));

        // Cart area and summary
        JPanel cartContentPanel = new JPanel(new BorderLayout());
        cartArea = new JTextArea(8, 50);
        cartArea.setEditable(false);
        JScrollPane cartScroll = new JScrollPane(cartArea);
        cartContentPanel.add(cartScroll, BorderLayout.CENTER);

        JPanel cartSummaryPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        cartTotalLabel = new JLabel("Total: Rp 0.00");
        cartTotalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        cartSummaryPanel.add(cartTotalLabel);
        cartContentPanel.add(cartSummaryPanel, BorderLayout.SOUTH);

        bottomPanel.add(cartContentPanel, BorderLayout.CENTER);

        // Cart buttons
        JPanel cartButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonAddToCart = new JButton("Add to Cart");
        buttonClearCart = new JButton("Clear Cart");

        buttonAddToCart.addActionListener(e -> showAddToCartDialog());
        buttonClearCart.addActionListener(e -> clearCart());

        cartButtonPanel.add(buttonAddToCart);
        cartButtonPanel.add(buttonClearCart);
        bottomPanel.add(cartButtonPanel, BorderLayout.SOUTH);

        // Split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel, bottomPanel);
        splitPane.setDividerLocation(400);
        mainPanel.add(splitPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // File menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        // Product menu
        JMenu productMenu = new JMenu("Product");
        JMenuItem addProdItem = new JMenuItem("Add Product");
        addProdItem.addActionListener(e -> showAddProductDialog());
        JMenuItem deleteProdItem = new JMenuItem("Delete Product");
        deleteProdItem.addActionListener(e -> deleteProduct());
        productMenu.add(addProdItem);
        productMenu.addSeparator();
        productMenu.add(deleteProdItem);

        // Cart menu
        JMenu cartMenu = new JMenu("Cart");
        JMenuItem addCartItem = new JMenuItem("Add to Cart");
        addCartItem.addActionListener(e -> showAddToCartDialog());
        JMenuItem clearCartItem = new JMenuItem("Clear Cart");
        clearCartItem.addActionListener(e -> clearCart());
        cartMenu.add(addCartItem);
        cartMenu.addSeparator();
        cartMenu.add(clearCartItem);

        menuBar.add(fileMenu);
        menuBar.add(productMenu);
        menuBar.add(cartMenu);
        setJMenuBar(menuBar);
    }

    private void loadProductsData() {
        try {
            tableModel.setRowCount(0);
            List<Product> products = controller.getAllProducts();
            for (Product p : products) {
                tableModel.addRow(new Object[]{
                        p.getCode(),
                        p.getName(),
                        String.format("%.2f", p.getPrice()),
                        p.getStock()
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading products: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showAddProductDialog() {
        JDialog dialog = new JDialog(this, "Add Product", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel codeLabel = new JLabel("Code:");
        JTextField codeField = new JTextField();
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel priceLabel = new JLabel("Price:");
        JTextField priceField = new JTextField();
        JLabel stockLabel = new JLabel("Stock:");
        JTextField stockField = new JTextField();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        panel.add(codeLabel);
        panel.add(codeField);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(priceLabel);
        panel.add(priceField);
        panel.add(stockLabel);
        panel.add(stockField);
        panel.add(saveButton);
        panel.add(cancelButton);

        saveButton.addActionListener(e -> {
            try {
                String code = codeField.getText();
                String name = nameField.getText();
                double price = Double.parseDouble(priceField.getText());
                int stock = Integer.parseInt(stockField.getText());

                controller.addProduct(code, name, price, stock);
                loadProductsData();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Product added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid number format!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showEditProductDialog() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a product to edit!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String code = (String) tableModel.getValueAt(selectedRow, 0);
        
        try {
            Product product = controller.getProduct(code);
            if (product == null) {
                JOptionPane.showMessageDialog(this, "Product not found!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JDialog dialog = new JDialog(this, "Edit Product", true);
            dialog.setSize(400, 250);
            dialog.setLocationRelativeTo(this);

            JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            JLabel codeLabel = new JLabel("Code:");
            JTextField codeField = new JTextField(product.getCode());
            codeField.setEditable(false);
            JLabel nameLabel = new JLabel("Name:");
            JTextField nameField = new JTextField(product.getName());
            JLabel priceLabel = new JLabel("Price:");
            JTextField priceField = new JTextField(String.valueOf(product.getPrice()));
            JLabel stockLabel = new JLabel("Stock:");
            JTextField stockField = new JTextField(String.valueOf(product.getStock()));
            JButton saveButton = new JButton("Save");
            JButton cancelButton = new JButton("Cancel");

            panel.add(codeLabel);
            panel.add(codeField);
            panel.add(nameLabel);
            panel.add(nameField);
            panel.add(priceLabel);
            panel.add(priceField);
            panel.add(stockLabel);
            panel.add(stockField);
            panel.add(saveButton);
            panel.add(cancelButton);

            saveButton.addActionListener(e -> {
                try {
                    String name = nameField.getText();
                    double price = Double.parseDouble(priceField.getText());
                    int stock = Integer.parseInt(stockField.getText());

                    controller.updateProduct(code, name, price, stock);
                    loadProductsData();
                    dialog.dispose();
                    JOptionPane.showMessageDialog(this, "Product updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(dialog, "Invalid number format!", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            cancelButton.addActionListener(e -> dialog.dispose());

            dialog.add(panel);
            dialog.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteProduct() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a product to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String code = (String) tableModel.getValueAt(selectedRow, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this product?", "Confirm", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                controller.deleteProduct(code);
                loadProductsData();
                JOptionPane.showMessageDialog(this, "Product deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showAddToCartDialog() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow < 0) {
            JOptionPane.showMessageDialog(this, "Please select a product!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String code = (String) tableModel.getValueAt(selectedRow, 0);

        JDialog dialog = new JDialog(this, "Add to Cart", true);
        dialog.setSize(300, 150);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(2, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel qtyLabel = new JLabel("Quantity:");
        JTextField qtyField = new JTextField();
        JButton addButton = new JButton("Add");
        JButton cancelButton = new JButton("Cancel");

        panel.add(qtyLabel);
        panel.add(qtyField);
        panel.add(addButton);
        panel.add(cancelButton);

        addButton.addActionListener(e -> {
            try {
                int quantity = Integer.parseInt(qtyField.getText());
                controller.addToCart(code, quantity);
                updateCartDisplay();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Item added to cart!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid quantity!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void clearCart() {
        int confirm = JOptionPane.showConfirmDialog(this, "Clear all items from cart?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            controller.clearCart();
            updateCartDisplay();
            JOptionPane.showMessageDialog(this, "Cart cleared!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void updateCartDisplay() {
        try {
            String summary = controller.getCartSummary();
            double total = controller.getCartTotal();
            cartArea.setText(summary);
            cartTotalLabel.setText(String.format("Total: Rp %.2f", total));
        } catch (Exception e) {
            cartArea.setText("Error: " + e.getMessage());
        }
    }
}
