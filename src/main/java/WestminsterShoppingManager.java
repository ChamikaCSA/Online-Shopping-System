import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Manages the Shopping application, providing console-based menu options
 */
public class WestminsterShoppingManager implements ShoppingManager {
    private static final ArrayList<Product> productsList = new ArrayList<>();
    private static User currentUser;

    private static final String fileName = "products.txt";
    private static final Scanner scanner = new Scanner(System.in);;
    private static final WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();

    public static void main(String[] args) {
        currentUser = new User();

        shoppingManager.loadFromFile();

        int choice = -1;
        do {
            displayMenu();

            try {
                choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        // Add a new product
                        shoppingManager.addNewProduct();
                        break;
                    case 2:
                        // Delete a product
                        shoppingManager.deleteProduct();
                        break;
                    case 3:
                        // Print the list of products
                        shoppingManager.printProductsList();
                        break;
                    case 4:
                        // Save to file
                        shoppingManager.saveToFile();
                        break;
                    case 5:
                        // Open GUI
                        openGUI();
                        break;
                    case 0:
                        // Exit the application
                        System.out.println("Exiting the application.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.nextLine(); // Consume the invalid input
            }
        } while (choice != 0);
    }

    /**
     * Displays the menu options for the Shopping application.
     */
    private static void displayMenu() {
        System.out.println("Menu:");
        System.out.println("1. Add a new product");
        System.out.println("2. Delete a product");
        System.out.println("3. Print the list of products");
        System.out.println("4. Save to file");
        System.out.println("5. Open GUI");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    /**
     * Adds a new product to the products list.
     */
    @Override
    public void addNewProduct() {
        if (productsList.size() >= 50) {
            System.out.println("Cannot add more products. Maximum limit reached");
            return;
        }

        try {
            System.out.println("Select product type:");
            System.out.println("1. Electronics");
            System.out.println("2. Clothing");
            System.out.print("Enter your choice: ");
            int productTypeChoice = scanner.nextInt();
            scanner.nextLine();

            String productId;
            String productName;
            int availableItems;
            double price;

            switch (productTypeChoice) {
                case 1:
                    // Add Electronics
                    int warrantyPeriod;
                    String brand;

                    System.out.print("Enter product ID: ");
                    productId = scanner.nextLine();

                    System.out.print("Enter product name: ");
                    productName = scanner.nextLine();

                    do {
                        System.out.print("Enter available items: ");
                        availableItems = scanner.nextInt();
                        if (availableItems < 0) {
                            System.out.println("Invalid input. Please enter a non-negative integer for available items.");
                        }
                    } while (availableItems < 0);

                    do {
                        System.out.print("Enter price: ");
                        price = scanner.nextDouble();
                        scanner.nextLine();
                        if (price < 0) {
                            System.out.println("Invalid input. Please enter a non-negative price.");
                        }
                    } while (price < 0);

                    System.out.print("Enter brand: ");
                    brand = scanner.nextLine();

                    do {
                        System.out.print("Enter warranty period: ");
                        warrantyPeriod = scanner.nextInt();
                        if (warrantyPeriod < 0) {
                            System.out.println("Invalid input. Please enter a non-negative integer for the warranty period.");
                        }
                    } while (warrantyPeriod < 0);

                    Electronics electronics = new Electronics(productId, productName, availableItems, price, brand, warrantyPeriod);
                    productsList.add(electronics);

                    System.out.println("'" + electronics.getProductId() + " - " + electronics.getProductName() + "' added successfully.");
                    break;
                case 2:
                    // Add Clothing
                    String size;
                    String colour;

                    System.out.print("Enter product ID: ");
                    productId = scanner.nextLine();

                    System.out.print("Enter product name: ");
                    productName = scanner.nextLine();

                    do {
                        System.out.print("Enter available items: ");
                        availableItems = scanner.nextInt();
                        if (availableItems < 0) {
                            System.out.println("Invalid input. Please enter a non-negative integer for available items.");
                        }
                    } while (availableItems < 0);

                    do {
                        System.out.print("Enter price: ");
                        price = scanner.nextDouble();
                        scanner.nextLine();
                        if (price < 0) {
                            System.out.println("Invalid input. Please enter a non-negative price.");
                        }
                    } while (price < 0);

                    System.out.print("Enter size: ");
                    size = scanner.nextLine();

                    System.out.print("Enter colour: ");
                    colour = scanner.nextLine();

                    Clothing clothing = new Clothing(productId, productName, availableItems, price, size, colour);
                    productsList.add(clothing);

                    System.out.println("'" + clothing.getProductId() + " - " + clothing.getProductName() + "' added successfully.");
                    break;
                default:
                    System.out.println("Invalid product type choice. Please choose either 1 or 2.");
                    break;
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter valid numeric values.");
            scanner.nextLine(); // Consume the invalid input
        }
    }

    /**
     * Deletes a product from the products list based on its ID.
     */
    @Override
    public void deleteProduct() {
        try {
            System.out.print("Enter product ID to delete: ");
            String productIdToDelete = scanner.next();

            for (Product product : productsList) {
                if (product.getProductId().equals(productIdToDelete)) {
                    productsList.remove(product);
                    System.out.println("'" + product.getProductId() +
                            " - " + product.getProductName() + "' has been deleted.");
                    System.out.println("Total number of products in the system: " + productsList.size());
                    return;
                }
            }
            System.out.println("Product with ID '" + productIdToDelete + "' not found.");

        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid product ID.");
            scanner.nextLine();
        }
    }

    /**
     * Prints the list of products sorted by product ID.
     */
    @Override
    public void printProductsList() {
        if (productsList.isEmpty()) {
            System.out.println("No products available to print.");
        } else {
            ArrayList<Product> sortedProducts = new ArrayList<>(productsList);
            Collections.sort(sortedProducts);
            for (Product product : sortedProducts) {
                System.out.println(product.getProductInfo());
            }
        }
    }

    /**
     * Saves the product information to a file.
     */
    @Override
    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Product product : productsList) {
                writer.write(product.toString());
                writer.newLine();
            }
            System.out.println("Product information saved to file: " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving products to the file: " + e.getMessage());
        }
    }

    /**
     * Loads product information from a file and adds the products to the products list.
     */
    @Override
    public void loadFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            if ((line = reader.readLine()) == null) {
                System.out.println("No products to load.");
                return;
            }

            do {
                try {
                    String[] parts = line.split(",");

                    String productType = parts[0].trim();
                    String productId = parts[1].trim();
                    String productName = parts[2].trim();
                    int availableItems = Integer.parseInt(parts[3].trim());
                    double price = Double.parseDouble(parts[4].trim());

                    if ("Electronics".equals(productType)) {
                        String brand = parts[5].trim();
                        int warrantyPeriod = Integer.parseInt(parts[6].trim());
                        Electronics electronics = new Electronics(productId, productName, availableItems, price, brand, warrantyPeriod);
                        productsList.add(electronics);

                    } else if ("Clothing".equals(productType)) {
                        String size = parts[5].trim();
                        String colour = parts[6].trim();
                        Clothing clothing = new Clothing(productId, productName, availableItems, price, size, colour);
                        productsList.add(clothing);
                    }
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Error parsing line: " + line + ". Skipping line.");
                }
            } while ((line = reader.readLine()) != null);

            System.out.println("Product information loaded from file: " + fileName);

        } catch (IOException e) {
            System.out.println("Error loading products from file: " + e.getMessage());
        }
    }

    /**
     * Gets a copy of the products list.
     */
    public static ArrayList<Product> getProductsList() {
        return new ArrayList<>(productsList);
    }

    /**
     * Opens the GUI for the Westminster Shopping application.
     */
    private static void openGUI() {
        ShoppingCentreGUI shoppingCentreGUI = new ShoppingCentreGUI(currentUser);
        shoppingCentreGUI.setTitle("Westminster Shopping Centre");
        shoppingCentreGUI.setSize(540, 500);
        shoppingCentreGUI.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        shoppingCentreGUI.setVisible(true);
    }
}
