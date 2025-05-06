package src;

import src.datastructures.CustomArrayList;
import src.datastructures.SortingAlgorithms;
import java.util.Comparator;

/**
 * Manages inventory operations including CRUD operations
 * and displaying inventory items
 */
public class InventoryManager {

    /**
     * Creates a new inventory item and saves it to file
     * @param item The item to create
     */
    public void createItem(InventoryItem item) {
        FileManager.writeItemToFile(item);
    }

    /**
     * Retrieves an item by its ID
     * @param id The ID of the item to retrieve
     * @return The item if found, null otherwise
     */
    public InventoryItem readItem(int id) {
        return FileManager.readItemById(id);
    }

    /**
     * Updates an existing item
     * @param id The ID of the item to update
     * @param updatedItem The updated item data
     * @return true if update was successful
     */
    public boolean updateItem(int id, InventoryItem updatedItem) {
        InventoryItem existingItem = readItem(id);
        if (existingItem != null) {
            // Ensure the ID remains the same
            updatedItem.setItemId(id);
            FileManager.writeItemToFile(updatedItem);
            return true;
        }
        return false;
    }

    /**
     * Deletes an item by its ID
     * @param id The ID of the item to delete
     * @return true if deletion was successful
     */
    public boolean deleteItem(int id) {
        InventoryItem item = readItem(id);
        if (item != null) {
            return FileManager.deleteItemFromFile(item.getCategory(), id);
        }
        return false;
    }

    /**
     * Displays all inventory items sorted by category and name
     */
    public void viewAllItems() {
        CustomArrayList<InventoryItem> items = FileManager.readAllItems();
        
        if (items.isEmpty()) {
            System.out.println("No items in inventory.");
            return;
        }
        
        // Sort items by category and then by name using custom merge sort
        SortingAlgorithms.mergeSort(items, new Comparator<InventoryItem>() {
            @Override
            public int compare(InventoryItem item1, InventoryItem item2) {
                int categoryComparison = item1.getCategory().compareTo(item2.getCategory());
                if (categoryComparison != 0) {
                    return categoryComparison;
                }
                return item1.getName().compareTo(item2.getName());
            }
        });
        
        // Display items in a formatted table
        System.out.println("\n------------------------------ INVENTORY ITEMS ------------------------------");
        System.out.printf("%-10s %-20s %-15s %-10s %-10s %-15s%n", 
                "ID", "NAME", "CATEGORY", "QUANTITY", "PRICE", "SUPPLIER");
        System.out.println("--------------------------------------------------------------------------");
        
        for (int i = 0; i < items.size(); i++) {
            InventoryItem item = items.get(i);
            System.out.printf("%-10s %-20s %-15s %-10d $%-9.2f %-15s%n", 
                    item.getItemId(), 
                    truncateString(item.getName(), 20),
                    truncateString(item.getCategory(), 15),
                    item.getQuantity(), 
                    item.getPrice(), 
                    truncateString(item.getSupplier(), 15));
        }
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Total Items: " + items.size());
    }
    
    /**
     * Helper method to truncate strings for display formatting
     * @param str The string to truncate
     * @param maxLength The maximum length
     * @return The truncated string
     */
    private String truncateString(String str, int maxLength) {
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength - 3) + "...";
    }
}
