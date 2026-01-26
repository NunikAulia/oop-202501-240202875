package com.upb.agripos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * GUI Form untuk Input Product - Contoh GUI Dasar
 */
public class ProductFrame extends JFrame {
    private JLabel labelId, labelName, labelPrice, labelStock;
    private JTextField textId, textName, textPrice, textStock;
    private JButton buttonSave, buttonReset, buttonCancel;
    private JTextArea textAreaOutput;

    public ProductFrame() {
        setTitle("AgriPOS - Product Input Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 450);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title Panel
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Form Input Produk");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titlePanel.add(titleLabel);

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Data Produk"));

        // Product ID
        labelId = new JLabel("ID Produk:");
        textId = new JTextField();
        inputPanel.add(labelId);
        inputPanel.add(textId);

        // Product Name
        labelName = new JLabel("Nama Produk:");
        textName = new JTextField();
        inputPanel.add(labelName);
        inputPanel.add(textName);

        // Price
        labelPrice = new JLabel("Harga (Rp):");
        textPrice = new JTextField();
        inputPanel.add(labelPrice);
        inputPanel.add(textPrice);

        // Stock
        labelStock = new JLabel("Stok:");
        textStock = new JTextField();
        inputPanel.add(labelStock);
        inputPanel.add(textStock);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));

        buttonSave = new JButton("Simpan");
        buttonReset = new JButton("Reset");
        buttonCancel = new JButton("Batal");

        buttonSave.setPreferredSize(new Dimension(100, 30));
        buttonReset.setPreferredSize(new Dimension(100, 30));
        buttonCancel.setPreferredSize(new Dimension(100, 30));

        buttonPanel.add(buttonSave);
        buttonPanel.add(buttonReset);
        buttonPanel.add(buttonCancel);

        // Output Panel
        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createTitledBorder("Output"));

        textAreaOutput = new JTextArea(5, 50);
        textAreaOutput.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textAreaOutput);
        outputPanel.add(scrollPane, BorderLayout.CENTER);

        // Add panels to main
        mainPanel.add(titlePanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(inputPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(buttonPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(outputPanel);

        // Add main panel to frame
        add(mainPanel);

        // Add action listeners
        addActionListeners();
    }

    private void addActionListeners() {
        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveProduct();
            }
        });

        buttonReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetForm();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void saveProduct() {
        try {
            String id = textId.getText();
            String name = textName.getText();
            int price = Integer.parseInt(textPrice.getText());
            int stock = Integer.parseInt(textStock.getText());

            if (id.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "ID dan Nama tidak boleh kosong!", "Peringatan", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Product product = new Product(id, name, price, stock);
            textAreaOutput.setText("Data Produk Berhasil Disimpan:\n" + product.toString());
            
            JOptionPane.showMessageDialog(this, "Produk berhasil disimpan!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Harga dan Stok harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetForm() {
        textId.setText("");
        textName.setText("");
        textPrice.setText("");
        textStock.setText("");
        textAreaOutput.setText("");
        textId.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ProductFrame frame = new ProductFrame();
                frame.setVisible(true);
            }
        });
    }
}
