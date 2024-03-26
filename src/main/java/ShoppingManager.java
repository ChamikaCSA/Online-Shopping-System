/**
 * Interface for managing shopping operations.
 */
public interface ShoppingManager {
    void addNewProduct();

    void deleteProduct();

    void printProductsList();

    void saveToFile();

    void loadFromFile();
}
