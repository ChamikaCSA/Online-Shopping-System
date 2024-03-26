import java.util.ArrayList;
import java.util.Date;

/**
 * Represents the purchase history of a customer, tracking category counts and total items purchased.
 */
public class PurchaseHistory {
    private ArrayList<Purchase> purchases;

    public PurchaseHistory() {
        this.purchases = new ArrayList<>();
    }

    /**
     * Adds a purchase to the history.
     */
    public void addPurchase(ArrayList<Product> purchasedProducts, Date date) {
        Purchase purchase = new Purchase(purchasedProducts, date);
        purchases.add(purchase);
    }

    /**
     * Gets a copy of the list containing all purchases.
     */
    public ArrayList<Purchase> getPurchases() {
        return new ArrayList<>(purchases);
    }
}
