package src;
import java.io.*;
import java.util.Scanner;
import src.datastructures.CustomArrayList;

/**
 * Handles file operations for the inventory system
 * Uses CSV format for storing inventory data
 */
public class FileManager {

    private static final String CSV_DIRECTORY = "inventory_data/";
    private static final String CSV_EXTENSION = ".csv";

    /**
     * Ensures the directory for storing CSV files exists
     */
    private static void ensureDirectoryExists() {
        File directory = new File(CSV_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    /**
     * Gets the file path for a specific category
     * @param category The category name
     * @return The full file path
     */
    private static String getFilePath(String category) {
        return CSV_DIRECTORY + category + CSV_EXTENSION;
    }

    /**
     * Writes an item to its category CSV file
     * @param item The item to write
     */
    public static void writeItemToFile(InventoryItem item) {
        ensureDirectoryExists();
        String filePath = getFilePath(item.getCategory());
        
        // Check if file exists and if the item already exists (for updates)
        boolean itemExists = false;
        CustomArrayList<InventoryItem> existingItems = readItemsFromCategory(item.getCategory());
        
        for (int i = 0; i < existingItems.size(); i++) {
            if (existingItems.get(i).getItemId() == item.getItemId()) {
                existingItems.set(i, item); // Update existing item
                itemExists = true;
                break;
            }
        }
        
        if (!itemExists) {
            existingItems.add(item); // Add new item
        }
        
        // Write all items back to file
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            // Write header
            writer.println("itemId,name,category,quantity,price,supplier");
            
            // Write items
            for (int i = 0; i < existingItems.size(); i++) {
                InventoryItem currentItem = existingItems.get(i);
                writer.println(
                    currentItem.getItemId() + "," +
                    escapeCSV(currentItem.getName()) + "," +
                    escapeCSV(currentItem.getCategory()) + "," +
                    currentItem.getQuantity() + "," +
                    currentItem.getPrice() + "," +
                    escapeCSV(currentItem.getSupplier())
                );
            }
            System.out.println("Item saved successfully to " + filePath);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Deletes an item from its category file
     * @param category The category of the item
     * @param itemId The ID of the item to delete
     * @return true if deletion was successful
     */
    public static boolean deleteItemFromFile(String category, int itemId) {
        ensureDirectoryExists();
        String filePath = getFilePath(category);
        File file = new File(filePath);
        
        if (!file.exists()) {
            return false;
        }
        
        CustomArrayList<InventoryItem> items = readItemsFromCategory(category);
        boolean deleted = false;
        
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getItemId() == itemId) {
                items.remove(i);
                deleted = true;
                break;
            }
        }
        
        if (deleted) {
            try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
                // Write header
                writer.println("itemId,name,category,quantity,price,supplier");
                
                // Write remaining items
                for (int i = 0; i < items.size(); i++) {
                    InventoryItem item = items.get(i);
                    writer.println(
                        item.getItemId() + "," +
                        escapeCSV(item.getName()) + "," +
                        escapeCSV(item.getCategory()) + "," +
                        item.getQuantity() + "," +
                        item.getPrice() + "," +
                        escapeCSV(item.getSupplier())
                    );
                }
                System.out.println("Item deleted successfully from " + filePath);
                return true;
            } catch (IOException e) {
                System.out.println("Error updating file after deletion: " + e.getMessage());
                e.printStackTrace();
            }
        }
        
        return false;
    }

    /**
     * Reads all items from a specific category file
     * @param category The category to read
     * @return ArrayList of items in that category
     */
    public static CustomArrayList<InventoryItem> readItemsFromCategory(String category) {
        ensureDirectoryExists();
        String filePath = getFilePath(category);
        CustomArrayList<InventoryItem> items = new CustomArrayList<>();
        File file = new File(filePath);
        
        if (!file.exists()) {
            return items; // Return empty list if file doesn't exist
        }
        
        try (Scanner scanner = new Scanner(file)) {
            // Skip header
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }
            
            // Read items
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                InventoryItem item = parseCSVLine(line);
                if (item != null) {
                    items.add(item);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filePath);
        } catch (Exception e) {
            System.out.println("Error reading data: " + e.getMessage());
            e.printStackTrace();
        }
        
        return items;
    }

    /**
     * Reads all items from all category files
     * @return ArrayList of all inventory items
     */
    public static CustomArrayList<InventoryItem> readAllItems() {
        ensureDirectoryExists();
        CustomArrayList<InventoryItem> allItems = new CustomArrayList<>();
        File directory = new File(CSV_DIRECTORY);
        
        if (!directory.exists() || !directory.isDirectory()) {
            return allItems; // Return empty list if directory doesn't exist
        }
        
        File[] files = directory.listFiles((dir, name) -> name.endsWith(CSV_EXTENSION));
        
        if (files != null) {
            for (File file : files) {
                String category = file.getName().replace(CSV_EXTENSION, "");
                CustomArrayList<InventoryItem> categoryItems = readItemsFromCategory(category);
                
                for (int i = 0; i < categoryItems.size(); i++) {
                    allItems.add(categoryItems.get(i));
                }
            }
        }
        
        return allItems;
    }

    /**
     * Reads a specific item by ID
     * @param itemId The ID of the item to find
     * @return The item if found, null otherwise
     */
    public static InventoryItem readItemById(int itemId) {
        CustomArrayList<InventoryItem> allItems = readAllItems();
        
        for (int i = 0; i < allItems.size(); i++) {
            if (allItems.get(i).getItemId() == itemId) {
                return allItems.get(i);
            }
        }
        
        return null;
    }

    /**
     * Parses a CSV line into an InventoryItem
     * @param line The CSV line to parse
     * @return The parsed InventoryItem
     */
    private static InventoryItem parseCSVLine(String line) {
        try {
            String[] parts = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); // Split by comma, respecting quotes
            
            if (parts.length >= 6) {
                int itemId = Integer.parseInt(parts[0].trim());
                String name = unescapeCSV(parts[1].trim());
                String category = unescapeCSV(parts[2].trim());
                int quantity = Integer.parseInt(parts[3].trim());
                double price = Double.parseDouble(parts[4].trim());
                String supplier = unescapeCSV(parts[5].trim());
                
                return new InventoryItem(itemId, name, category, quantity, price, supplier);
            }
        } catch (NumberFormatException e) {
            System.out.println("Error parsing CSV line: " + e.getMessage());
        }
        
        return null;
    }

    /**
     * Escapes special characters for CSV format
     * @param value The string to escape
     * @return The escaped string
     */
    private static String escapeCSV(String value) {
        if (value == null) {
            return "";
        }
        
        // If the value contains comma, newline or double quote, wrap in quotes and escape internal quotes
        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        
        return value;
    }

    /**
     * Unescapes CSV formatted string
     * @param value The string to unescape
     * @return The unescaped string
     */
    private static String unescapeCSV(String value) {
        if (value == null) {
            return "";
        }
        
        // If the value is wrapped in quotes, remove them and unescape internal quotes
        if (value.startsWith("\"") && value.endsWith("\"")) {
            return value.substring(1, value.length() - 1).replace("\"\"", "\"");
        }
        
        return value;
    }
}
