/**
 * Represents a clothing product, extending the base Product class.
 */
public class Clothing extends Product {
    private String size;
    private String colour;

    public Clothing(String productId, String productName, int availableItems, double price, String size, String colour) {
        super(productId, productName, availableItems, price);
        this.size = size;
        this.colour = colour;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    public String getOtherInfo() {
        return getSize() + ", " + getColour();
    }

    @Override
    public String getProductInfo() {
        return (getCategory() + " - ID: " + getProductId() + ", Name: " + getProductName() + ", Available Items: " +
                getAvailableItems() + ", Price: £" + getPrice() + ", Size: " + size + ", Colour: " + colour);
    }

    @Override
    public String toString() {
        return getCategory() + ", " + getProductId() + ", " + getProductName() + ", " + getAvailableItems() + ", " +
                getPrice() + ", " + size + ", " + colour;
    }


}
