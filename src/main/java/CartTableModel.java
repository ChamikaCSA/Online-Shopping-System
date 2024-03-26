import javax.swing.table.AbstractTableModel;
import java.util.Map;

/**
 * Table model for representing the shopping cart in a JTable.
 */
public class CartTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Product", "Quantity", "Price"};
    private Map<Product, Integer> shoppingMap;

    public CartTableModel(Map<Product, Integer> shoppingMap) {
        this.shoppingMap = shoppingMap;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return shoppingMap.size();
    }

    @Override
    public Object getValueAt(int row, int col) {
        Object temp = null;
        Product product = (Product) shoppingMap.keySet().toArray()[row];
        switch (col) {
            case 0:
                //Get an array of product information for the "Product" column
                temp = new String[]{
                        product.getProductId(),
                        product.getProductName(),
                        product.getOtherInfo()
                };
                break;
            case 1:
                temp = shoppingMap.values().toArray()[row];
                break;
            case 2:
                temp = product.getPrice() * (int) shoppingMap.values().toArray()[row];
                break;
            default:
                break;
        }
        return temp;
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }

    @Override
    public Class<?> getColumnClass(int col) {
        return switch (col) {
            case 0 -> String[].class;
            case 1 -> Integer.class;
            case 2 -> Double.class;
            default -> String.class;
        };
    }
}
