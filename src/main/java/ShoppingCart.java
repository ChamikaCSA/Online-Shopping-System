import java.util.HashMap;
import java.util.Map;

/**
 * Represents a shopping cart that holds products and tracks purchase history.
 */
public class ShoppingCart {
    private Map<Product, Integer> shoppingMap;

    public ShoppingCart() {
        this.shoppingMap = new HashMap<>();
    }

    /**
     * Adds a product to the shopping cart and updates purchase history.
     */
    public void addProduct(Product product) {
        this.shoppingMap.put(product, this.shoppingMap.getOrDefault(product, 0) + 1);

    }

    /**
     * Removes a product from the shopping cart.
     */
    public void removeProduct(Product product) {
        int currentQuantity = this.shoppingMap.getOrDefault(product, 0);
        this.shoppingMap.put(product, currentQuantity - 1);
    }

    /**
     * Calculates the total cost of all products in the shopping cart.
     */
    public double calculateTotalCost() {
        double totalCost = 0.0;
        for (Map.Entry<Product, Integer> entry : shoppingMap.entrySet()) {
            totalCost += entry.getKey().getPrice() * entry.getValue();
        }
        return totalCost;
    }

    /**
     * Gets a copy of the products in the shoppingMap.
     */
    public Map<Product, Integer> getShoppingMap() {
        return new HashMap<>(shoppingMap);
    }

    /**
     * Checks if a category discount should be applied
     * based on the number of products in the shopping cart for each category.
     */
    public boolean ifCategoryDiscount() {
        Map<String, Integer> categoryCount = new HashMap<>();

        for (Map.Entry<Product, Integer> entry : shoppingMap.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            String category = product.getCategory();
            categoryCount.put(category, categoryCount.getOrDefault(category, 0) + quantity);
        }
        boolean applyDiscount = false;
        for (Map.Entry<String, Integer> entry : categoryCount.entrySet()) {
            if (entry.getValue() >= 3) {
                applyDiscount = true;
            }
        }
        return applyDiscount;
    }
}
