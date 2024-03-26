import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Table model for displaying products in a JTable.
 */
public class ProductTableModel extends AbstractTableModel {
    private final String[] columnNames = {"Product ID", "Name", "Category", "Price(£)", "Info"};
    private ArrayList<Product> productsList;

    public ProductTableModel(ArrayList<Product> productsList) {
        this.productsList = productsList;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return productsList.size();
    }

    @Override
    public Object getValueAt(int row, int col) {
        Object temp = null;
        switch (col) {
            case 0:
                temp = productsList.get(row).getProductId();
                break;
            case 1:
                temp = productsList.get(row).getProductName();
                break;
            case 2:
                temp = productsList.get(row).getCategory();
                break;
            case 3:
                temp = productsList.get(row).getPrice();
                break;
            case 4:
                temp = productsList.get(row).getOtherInfo();
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
        if (col == 3) {
            return Double.class;
        } else {
            return String.class;
        }
    }

    /**
     * Sorts the table based on the specified column index.
     */
    public void sortTableByColumn(int columnIndex) {
        if (columnIndex == 0) {
            Collections.sort(productsList);
        }
        fireTableDataChanged();
    }

    /**
     * Gets the product at the specified row index.
     */
    public Product getProductAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < productsList.size()) {
            return productsList.get(rowIndex);
        } else {
            return null;
        }
    }
}
