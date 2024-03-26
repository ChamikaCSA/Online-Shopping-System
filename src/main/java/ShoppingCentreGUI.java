import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a graphical user interface for managing and displaying products in the shopping centre.
 */
public class ShoppingCentreGUI extends JFrame {
    private JComboBox<String> jcbProductType;
    private JTable jtblProducts;
    private JTextArea jtaProductDetails;
    private JTextArea jtaProductTitle;
    private JButton jbtnAddToCart;
    private JButton jbtnViewCart;
    private User currentUser;

    public ShoppingCentreGUI(User currentUser) {
        this.currentUser = currentUser;

        jcbProductType = new JComboBox<>(new String[]{"ALL", "Electronics", "Clothing"});
        jtblProducts = new JTable();
        jtaProductDetails = new JTextArea(6, 50);
        jtaProductDetails.setEditable(false);
        jtaProductTitle = new JTextArea(2, 50);
        jtaProductTitle.setEditable(false);
        jbtnAddToCart = new JButton("Add to Shopping Cart");
        jbtnViewCart = new JButton("Shopping Cart");

        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel viewButtonPanel = new JPanel();
        JPanel categoryPanel = new JPanel(new FlowLayout());
        categoryPanel.add(new JLabel("Select Product Category: "));
        categoryPanel.add(jcbProductType);
        viewButtonPanel.add(jbtnViewCart);
        topPanel.add(viewButtonPanel, BorderLayout.EAST);
        topPanel.add(categoryPanel, BorderLayout.CENTER);

        JScrollPane scrollPane = new JScrollPane(jtblProducts);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(60, 15, 33, 15));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel detailPanel = new JPanel(new BorderLayout());
        JPanel addButtonPanel = new JPanel();
        addButtonPanel.add(jbtnAddToCart);
        detailPanel.add(jtaProductTitle, BorderLayout.CENTER);
        detailPanel.add(jtaProductDetails, BorderLayout.SOUTH);
        bottomPanel.add(detailPanel, BorderLayout.CENTER);
        bottomPanel.add(addButtonPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        updateTableModel();

        jtblProducts.setDefaultRenderer(Object.class, new AvailabilityCellRenderer());
        jtblProducts.setDefaultRenderer(Double.class, new AvailabilityCellRenderer());

        EventHandler eventHandler = new EventHandler();
        jcbProductType.addActionListener(eventHandler);
        jtblProducts.getTableHeader().addMouseListener(eventHandler);
        jbtnAddToCart.addActionListener(eventHandler);
        jbtnViewCart.addActionListener(eventHandler);

        jtblProducts.getSelectionModel().addListSelectionListener(new TableSelectionListener());
    }

    /**
     * Event handler for mouse and action events in the GUI.
     * Implements ActionListener and handles button clicks and combo box changes.
     */
    private class EventHandler extends MouseAdapter implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent event) {
            if (event.getSource() == jbtnAddToCart) {
                addToCart();
            } else if (event.getSource() == jbtnViewCart) {
                viewShoppingCart();
            } else {
                updateTableModel();
            }
        }

        @Override
        public void mouseClicked(MouseEvent event) {
            int columnIndex = jtblProducts.columnAtPoint(event.getPoint());
            ((ProductTableModel) jtblProducts.getModel()).sortTableByColumn(columnIndex);
        }
    }

    /**
     * List selection listener for handling changes in the selected table row.
     * Updates the displayed product details based on the selected row in the table.
     */
    private class TableSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent event) {
            if (!event.getValueIsAdjusting() && jtblProducts.getSelectedRow() != -1) {
                int selectedRowIndex = jtblProducts.getSelectedRow();
                Product selectedProduct = ((ProductTableModel) jtblProducts.getModel()).getProductAt(selectedRowIndex);
                displayProductDetails(selectedProduct);
            }
        }
    }

    /**
     * Custom cell renderer for indicating availability in the JTable.
     * Changes the background color based on the availability of products.
     */
    private class AvailabilityCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component renderer = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            int availableItems = ((ProductTableModel) table.getModel()).getProductAt(row).getAvailableItems();

            if (availableItems < 3) {
                renderer.setBackground(Color.RED);
            } else {
                renderer.setBackground(table.getBackground());
            }
            return renderer;
        }
    }

    /**
     * Updates the table model based on the selected product category.
     */
    private void updateTableModel() {
        String selectedCategory = (String) jcbProductType.getSelectedItem();
        ArrayList<Product> filteredProducts = new ArrayList<>();

        if (Objects.equals(selectedCategory, "ALL")) {
            filteredProducts.addAll(WestminsterShoppingManager.getProductsList());
        } else {
            for (Product product : WestminsterShoppingManager.getProductsList()) {
                if (product.getCategory().equals(selectedCategory)) {
                    filteredProducts.add(product);
                }
            }
        }

        ProductTableModel tableModel = new ProductTableModel(filteredProducts);
        jtblProducts.setModel(tableModel);
        jtblProducts.setRowHeight(30);

        TableColumnModel columnModel = jtblProducts.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(80);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(80);
        columnModel.getColumn(3).setPreferredWidth(80);
        columnModel.getColumn(4).setPreferredWidth(200);
    }

    /**
     * Displays detailed information about the selected product.
     */
    private void displayProductDetails(Product product) {
        Font boldFont = new Font(jtaProductDetails.getFont().getFamily(), Font.BOLD, jtaProductDetails.getFont().getSize());
        Insets margins = new Insets(0, 40, 0, 40);

        jtaProductTitle.setFont(boldFont);
        jtaProductTitle.setMargin(margins);
        jtaProductTitle.setText("Selected Product - Details");

        String productDetails = "";

        if (product instanceof Clothing clothing) {
            productDetails = "Product Id: " + clothing.getProductId() + "\n"
                    + "Category: " + clothing.getCategory() + "\n"
                    + "Name: " + clothing.getProductName() + "\n"
                    + "Size: " + clothing.getSize() + "\n"
                    + "Colour: " + clothing.getColour() + "\n"
                    + "Items Available: " + clothing.getAvailableItems();
        } else if (product instanceof Electronics electronics) {
            productDetails =
                    "Product Id: " + electronics.getProductId() + "\n"
                            + "Category: " + electronics.getCategory() + "\n"
                            + "Name: " + electronics.getProductName() + "\n"
                            + "Brand: " + electronics.getBrand() + "\n"
                            + "Warranty Period: " + electronics.getWarrantyPeriod() + " weeks\n"
                            + "Items Available: " + electronics.getAvailableItems();
        }

        jtaProductDetails.setMargin(margins);
        jtaProductDetails.setText(productDetails);
    }

    /**
     * Adds the selected product to the shopping cart.
     */
    private void addToCart() {
        int selectedRowIndex = jtblProducts.getSelectedRow();
        if (selectedRowIndex != -1) {
            Product selectedProduct = ((ProductTableModel) jtblProducts.getModel()).getProductAt(selectedRowIndex);

            if (selectedProduct.getAvailableItems() > 0) {
                currentUser.getShoppingCart().addProduct(selectedProduct);
                selectedProduct.setAvailableItems(selectedProduct.getAvailableItems() - 1);

                updateTableModel();

                System.out.println("'" + selectedProduct.getProductId() + " - " + selectedProduct.getProductName() + "' added to the shopping cart.");
            } else {
                System.out.println("'" + selectedProduct.getProductId() + " - " + selectedProduct.getProductName() + "' is out of stock.");
            }
        } else {
            System.out.println("No product selected. Please select a product to add to the shopping cart.");
        }
    }

    /**
     * Displays the shopping cart GUI.
     */
    private void viewShoppingCart() {
        ShoppingCartGUI shoppingCartGUI = new ShoppingCartGUI(currentUser);
        shoppingCartGUI.setTitle("Shopping Cart");
        shoppingCartGUI.setSize(500, 320);
        shoppingCartGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        shoppingCartGUI.setLocationRelativeTo(null);
        shoppingCartGUI.setVisible(true);
    }
}