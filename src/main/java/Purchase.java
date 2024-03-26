import java.util.ArrayList;
import java.util.Date;

/**
 * Represents a single purchase.
 */
public class Purchase {
    private ArrayList<Product> purchasedProducts;
    private Date date;

    public Purchase(ArrayList<Product> purchasedProducts, Date date) {
        this.purchasedProducts = new ArrayList<>(purchasedProducts);
        this.date = date;
    }

    public ArrayList<Product> getPurchasedProducts() {
        return new ArrayList<>(purchasedProducts);
    }

    public Date getDate() {
        return date;
    }
}
