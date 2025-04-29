package src;

import src.datastructures.CustomArrayList;
import src.datastructures.SortingAlgorithms;
import java.util.Comparator;

public class InventoryManager {

    private CustomArrayList<InventoryItem> items;

    public InventoryManager() {
        items = new CustomArrayList<>();
    }

    public void addItem(InventoryItem item) {
        items.add(item);
    }

    public InventoryItem viewItem(String id) {
        for (int i = 0; i < items.size(); i++) {
            InventoryItem item = items.get(i);
            if (item.getItemId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public boolean deleteItem(String id) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getItemId().equals(id)) {
                items.remove(i);
                return true;
            }
        }
        return false;
    }

    public void viewAllItems() {
        if (items.isEmpty()) {
            System.out.println("No items in inventory.");
            return;
        }
        
        // Create a copy of the items list for sorting
        CustomArrayList<InventoryItem> sortedItems = new CustomArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            sortedItems.add(items.get(i));
        }
        
        // Sort items by category and then by name using custom merge sort
        SortingAlgorithms.mergeSort(sortedItems, new Comparator<InventoryItem>() {
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
        
        for (int i = 0; i < sortedItems.size(); i++) {
            InventoryItem item = sortedItems.get(i);
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
    
    // Helper method to truncate strings for display formatting
    private String truncateString(String str, int maxLength) {
        if (str.length() <= maxLength) {
            return str;
        }
        return str.substring(0, maxLength - 3) + "...";
    }

    public void saveToFile(String filename) {
        FileManager.writeToFile(filename, items);
    }

    public void loadFromFile(String filename) {
        CustomArrayList<InventoryItem> loadedItems = FileManager.readFromFile(filename);
        if (loadedItems != null && !loadedItems.isEmpty()) {
            items = loadedItems;
            System.out.println("Loaded " + items.size() + " items from " + filename);
        }
    }

    public void updateItem(String id, InventoryItem newItem) {
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getItemId().equals(id)) {
                items.set(i, newItem);
                break;
            }
        }
    }
    
    // Get total inventory value
    public double getTotalInventoryValue() {
        double total = 0;
        for (int i = 0; i < items.size(); i++) {
            InventoryItem item = items.get(i);
            total += (item.getPrice() * item.getQuantity());
        }
        return total;
    }
    
    // Get items by category
    public CustomArrayList<InventoryItem> getItemsByCategory(String category) {
        CustomArrayList<InventoryItem> categoryItems = new CustomArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            InventoryItem item = items.get(i);
            if (item.getCategory().equalsIgnoreCase(category)) {
                categoryItems.add(item);
            }
        }
        return categoryItems;
    }
    
    // Get low stock items (items with quantity below threshold)
    public CustomArrayList<InventoryItem> getLowStockItems(int threshold) {
        CustomArrayList<InventoryItem> lowStockItems = new CustomArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            InventoryItem item = items.get(i);
            if (item.getQuantity() < threshold) {
                lowStockItems.add(item);
            }
        }
        return lowStockItems;
    }
    
    // Display low stock items
    public void viewLowStockItems(int threshold) {
        CustomArrayList<InventoryItem> lowStockItems = getLowStockItems(threshold);
        
        if (lowStockItems.isEmpty()) {
            System.out.println("No items below the threshold of " + threshold + ".");
            return;
        }
        
        System.out.println("\n------------------------------ LOW STOCK ITEMS ------------------------------");
        System.out.printf("%-10s %-20s %-15s %-10s %-10s %-15s%n", 
                "ID", "NAME", "CATEGORY", "QUANTITY", "PRICE", "SUPPLIER");
        System.out.println("--------------------------------------------------------------------------");
        
        for (int i = 0; i < lowStockItems.size(); i++) {
            InventoryItem item = lowStockItems.get(i);
            System.out.printf("%-10s %-20s %-15s %-10d $%-9.2f %-15s%n", 
                    item.getItemId(), 
                    truncateString(item.getName(), 20),
                    truncateString(item.getCategory(), 15),
                    item.getQuantity(), 
                    item.getPrice(), 
                    truncateString(item.getSupplier(), 15));
        }
        System.out.println("--------------------------------------------------------------------------");
    }
}
