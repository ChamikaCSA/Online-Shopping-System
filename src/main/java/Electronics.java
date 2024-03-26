/**
 * Represents an electronics product, extending the base Product class.
 */
public class Electronics extends Product {
    private String brand;
    private int warrantyPeriod;

    public Electronics(String productId, String productName, int availableItems, double price, String brand, int warrantyPeriod) {
        super(productId, productName, availableItems, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    @Override
    public String getOtherInfo() {
        return getBrand() + ", " + getWarrantyPeriod() + " weeks warranty";
    }


    @Override
    public String getProductInfo() {
        return (getCategory() + " - ID: " + getProductId() + ", Name: " + getProductName() + ", Available Items: " +
                getAvailableItems() + ", Price: £" + getPrice() + ", Brand: " + brand + ", Warranty Period: " + warrantyPeriod + " weeks");
    }

    @Override
    public String toString() {
        return getCategory() + ", " + getProductId() + ", " + getProductName() + ", " + getAvailableItems() + ", " +
                getPrice() + ", " + brand + ", " + warrantyPeriod;
    }
}
