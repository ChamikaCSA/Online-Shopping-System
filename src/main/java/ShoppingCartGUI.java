import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.text.DecimalFormat;

/**
 * Represents a graphical user interface for displaying the shopping cart contents and pricing information.
 */
public class ShoppingCartGUI extends JFrame {
    private JTable jtblCart;
    private JTextArea jtaPricing;
    private JTextArea jtaFinal;
    private User currentUser;

    public ShoppingCartGUI(User currentUser) {
        this.currentUser = currentUser;

        jtblCart = new JTable();
        jtaPricing = new JTextArea(4, 40);
        jtaPricing.setEditable(false);
        jtaFinal = new JTextArea(2, 40);
        jtaFinal.setEditable(false);

        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(jtblCart);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 27, 40));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(jtaPricing, BorderLayout.CENTER);
        bottomPanel.add(jtaFinal, BorderLayout.SOUTH);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        updateTableModel();
        updatePricingArea();

        jtblCart.getColumnModel().getColumn(0).setCellRenderer(new MultiLineTableCellRenderer());
    }

    /**
     * Custom TableCellRenderer for rendering multi-line cells in the table.
     * Utilizes a JList to handle multi-line text rendering within table cells.
     */
    private static class MultiLineTableCellRenderer extends JList<String> implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            if (value instanceof String[]) {
                setListData((String[]) value);
            }
            return this;
        }
    }

    /**
     * Updates the table model with the current shopping cart contents.
     */
    private void updateTableModel() {
        CartTableModel cartTableModel = new CartTableModel(currentUser.getShoppingCart().getShoppingMap());
        jtblCart.setModel(cartTableModel);
        jtblCart.setRowHeight(60);

        TableColumnModel columnModel = jtblCart.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(150);
        columnModel.getColumn(1).setPreferredWidth(100);
        columnModel.getColumn(2).setPreferredWidth(100);
    }

    /**
     * Updates the pricing information displayed in the GUI.
     */
    private void updatePricingArea() {
        DecimalFormat df = new DecimalFormat("#.##");
        double totalCost = currentUser.getShoppingCart().calculateTotalCost();
        double finalTotal = totalCost;

        Font boldFont = new Font(jtaFinal.getFont().getFamily(), Font.BOLD, jtaFinal.getFont().getSize());
        Insets margins = new Insets(0, 60, 0, 60);

        jtaPricing.setText("Total                                                                        "
                + df.format(totalCost) + " £");
        jtaPricing.setMargin(margins);

        //Apply 10% discount for the very first purchase.
        if (currentUser.getPurchaseHistory().getPurchases().isEmpty()) {
            double firstPurchaseDiscount = totalCost * 0.1;
            finalTotal -= firstPurchaseDiscount;
            jtaPricing.append("\nFirst Purchase Discount (10%)                             -"
                    + df.format(firstPurchaseDiscount) + " £");
        }

        //Apply 20% discount when user buys at least three products of the same category.
        if (currentUser.getShoppingCart().ifCategoryDiscount()) {
            double categoryDiscount = totalCost * 0.2;
            finalTotal -= categoryDiscount;
            jtaPricing.append("\nThree Items in same Category Discount (20%)    -"
                    + df.format(categoryDiscount) + " £");
        }


        jtaFinal.setFont(boldFont);
        jtaFinal.setMargin(margins);
        jtaFinal.setText("Final Total                                                              "
                + df.format(finalTotal) + " £");
    }
}