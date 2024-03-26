/**
 * An abstract class representing a generic product in the Shopping application.
 * Implements the Comparable interface for sorting based on product ID.
 */
public abstract class Product implements Comparable<Product> {
    private String productId;
    private String productName;
    private int availableItems;
    private double price;

    public Product(String productId, String productName, int availableItems, double price) {
        this.productId = productId;
        this.productName = productName;
        this.availableItems = availableItems;
        this.price = price;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getAvailableItems() {
        return availableItems;
    }

    public void setAvailableItems(int availableItems) {
        this.availableItems = availableItems;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return getClass().getSimpleName();
    }

    public abstract String getOtherInfo();

    public abstract String getProductInfo();

    /**
     * Compares a product with another product based on their product IDs.
     */
    @Override
    public int compareTo(Product otherProduct) {
        return this.productId.compareTo(otherProduct.productId);
    }
}
