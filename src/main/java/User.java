/**
 * Represents a user in the shopping system.
 */
public class User {
    private String username;
    private String password;
    private ShoppingCart shoppingCart;
    private PurchaseHistory purchaseHistory;

    public User() {
        this.shoppingCart = new ShoppingCart();
        this.purchaseHistory = new PurchaseHistory();
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.shoppingCart = new ShoppingCart();
        this.purchaseHistory = new PurchaseHistory();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ShoppingCart getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(ShoppingCart shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public PurchaseHistory getPurchaseHistory() {
        return purchaseHistory;
    }

    public void setPurchaseHistory(PurchaseHistory purchaseHistory) {
        this.purchaseHistory = purchaseHistory;
    }
}
