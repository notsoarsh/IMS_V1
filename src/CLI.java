package src;

import java.util.Scanner;
import src.command.*;

public class CLI {

    private InventoryManager manager;
    private Scanner scanner;
    private CommandManager commandManager;

    // Constructor
    public CLI() {
        manager = new InventoryManager();
        scanner = new Scanner(System.in);
        commandManager = new CommandManager();
    }

    // Main method - kept for backward compatibility
    public static void main(String[] args) {
        CLI app = new CLI();
        app.showMenu();
    }

    // Show menu and handle user choices
    public void showMenu() {
        int choice;
        do {
            displayMenu();
            choice = getValidIntInput("Enter your choice: ", 0, 10);

            switch (choice) {
                case 1:
                    addItem();
                    break;
                case 2:
                    viewItem();
                    break;
                case 3:
                    updateItem();
                    break;
                case 4:
                    deleteItem();
                    break;
                case 5:
                    manager.viewAllItems();
                    break;
                case 6:
                    saveToFile();
                    break;
                case 7:
                    loadFromFile();
                    break;
                case 8:
                    showLowStockItems();
                    break;
                case 9:
                    undoOperation();
                    break;
                case 10:
                    redoOperation();
                    break;
                case 0:
                    System.out.println("Exiting the program...");
                    break;
                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        } while (choice != 0);
    }
    
    // Display the menu options
    private void displayMenu() {
        System.out.println("\n--- Inventory Management System ---");
        System.out.println("1. Add Item");
        System.out.println("2. View Item");
        System.out.println("3. Update Item");
        System.out.println("4. Delete Item");
        System.out.println("5. View All Items");
        System.out.println("6. Save to File");
        System.out.println("7. Load from File");
        System.out.println("8. Show Low Stock Items");
        System.out.println("9. Undo Last Operation");
        System.out.println("10. Redo Last Operation");
        System.out.println("0. Exit");
    }

    // Get valid integer input within a range
    private int getValidIntInput(String prompt, int min, int max) {
        int input = -1;
        boolean validInput = false;
        
        while (!validInput) {
            try {
                System.out.print(prompt);
                String inputStr = scanner.nextLine().trim();
                input = Integer.parseInt(inputStr);
                
                if (input >= min && input <= max) {
                    validInput = true;
                } else {
                    System.out.println("Please enter a number between " + min + " and " + max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        
        return input;
    }
    
    // Get valid double input
    private double getValidDoubleInput(String prompt) {
        double input = 0;
        boolean validInput = false;
        
        while (!validInput) {
            try {
                System.out.print(prompt);
                String inputStr = scanner.nextLine().trim();
                input = Double.parseDouble(inputStr);
                validInput = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        
        return input;
    }

    // Add a new item
    public void addItem() {
        System.out.print("Enter Item ID: ");
        String id = scanner.nextLine().trim();
        
        // Check if ID already exists
        if (manager.viewItem(id) != null) {
            System.out.println("An item with this ID already exists. Use update option to modify it.");
            return;
        }
        
        System.out.print("Enter Item Name: ");
        String name = scanner.nextLine().trim();
        
        System.out.print("Enter Category: ");
        String category = scanner.nextLine().trim();
        
        int quantity = getValidIntInput("Enter Quantity: ", 0, Integer.MAX_VALUE);
        double price = getValidDoubleInput("Enter Price: ");
        
        System.out.print("Enter Supplier: ");
        String supplier = scanner.nextLine().trim();

        InventoryItem item = new InventoryItem(id, name, category, quantity, price, supplier);
        
        // Use command pattern for add operation
        Command addCommand = new AddItemCommand(manager, item);
        commandManager.executeCommand(addCommand);
        
        System.out.println("Item added successfully!");
    }

    // View an item
    public void viewItem() {
        System.out.print("Enter Item ID to view: ");
        String id = scanner.nextLine().trim();
        InventoryItem item = manager.viewItem(id);
        if (item != null) {
            System.out.println(item);
        } else {
            System.out.println("Item not found.");
        }
    }

    // Update an item
    public void updateItem() {
        System.out.print("Enter Item ID to update: ");
        String id = scanner.nextLine().trim();
        InventoryItem existingItem = manager.viewItem(id);
        
        if (existingItem != null) {
            System.out.println("Current item details: " + existingItem);
            System.out.println("Enter new details (press Enter to keep current value):");
            
            System.out.print("Enter New Name [" + existingItem.getName() + "]: ");
            String nameInput = scanner.nextLine().trim();
            String name = nameInput.isEmpty() ? existingItem.getName() : nameInput;
            
            System.out.print("Enter New Category [" + existingItem.getCategory() + "]: ");
            String categoryInput = scanner.nextLine().trim();
            String category = categoryInput.isEmpty() ? existingItem.getCategory() : categoryInput;
            
            int quantity;
            try {
                System.out.print("Enter New Quantity [" + existingItem.getQuantity() + "]: ");
                String quantityInput = scanner.nextLine().trim();
                quantity = quantityInput.isEmpty() ? existingItem.getQuantity() : Integer.parseInt(quantityInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid quantity. Keeping current value.");
                quantity = existingItem.getQuantity();
            }
            
            double price;
            try {
                System.out.print("Enter New Price [" + existingItem.getPrice() + "]: ");
                String priceInput = scanner.nextLine().trim();
                price = priceInput.isEmpty() ? existingItem.getPrice() : Double.parseDouble(priceInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid price. Keeping current value.");
                price = existingItem.getPrice();
            }
            
            System.out.print("Enter New Supplier [" + existingItem.getSupplier() + "]: ");
            String supplierInput = scanner.nextLine().trim();
            String supplier = supplierInput.isEmpty() ? existingItem.getSupplier() : supplierInput;

            InventoryItem updatedItem = new InventoryItem(id, name, category, quantity, price, supplier);
            
            // Use command pattern for update operation  quantity, price, supplier);
            
            // Use command pattern for update operation
            Command updateCommand = new UpdateItemCommand(manager, id, updatedItem);
            commandManager.executeCommand(updateCommand);

            System.out.println("Item updated successfully.");
        } else {
            System.out.println("Item not found.");
        }
    }

    // Delete an item
    public void deleteItem() {
        System.out.print("Enter Item ID to delete: ");
        String id = scanner.nextLine().trim();
        
        InventoryItem item = manager.viewItem(id);
        if (item != null) {
            System.out.println("You are about to delete: " + item);
            System.out.print("Are you sure? (y/n): ");
            String confirmation = scanner.nextLine().trim().toLowerCase();
            
            if (confirmation.equals("y") || confirmation.equals("yes")) {
                // Use command pattern for delete operation
                Command deleteCommand = new DeleteItemCommand(manager, id);
                commandManager.executeCommand(deleteCommand);
                System.out.println("Item deleted successfully.");
            } else {
                System.out.println("Deletion cancelled.");
            }
        } else {
            System.out.println("Item not found.");
        }
    }

    // Save items to file
    public void saveToFile() {
        System.out.print("Enter filename to save (without extension): ");
        String filename = scanner.nextLine().trim();
        
        // Add .dat extension if not provided
        if (!filename.endsWith(".dat")) {
            filename += ".dat";
        }
        
        manager.saveToFile(filename);
    }

    // Load items from file
    public void loadFromFile() {
        System.out.print("Enter filename to load (without extension): ");
        String filename = scanner.nextLine().trim();
        
        // Add .dat extension if not provided
        if (!filename.endsWith(".dat")) {
            filename += ".dat";
        }
        
        manager.loadFromFile(filename);
    }
    
    // Show low stock items
    public void showLowStockItems() {
        int threshold = getValidIntInput("Enter stock threshold: ", 0, Integer.MAX_VALUE);
        manager.viewLowStockItems(threshold);
    }
    
    // Undo last operation
    public void undoOperation() {
        if (commandManager.canUndo()) {
            commandManager.undo();
            System.out.println("Last operation undone successfully.");
        } else {
            System.out.println("Nothing to undo.");
        }
    }
    
    // Redo last undone operation
    public void redoOperation() {
        if (commandManager.canRedo()) {
            commandManager.redo();
            System.out.println("Operation redone successfully.");
        } else {
            System.out.println("Nothing to redo.");
        }
    }
}
