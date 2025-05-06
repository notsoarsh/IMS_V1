package src;

/**
 * Main entry point for the Inventory Management System
 * Initializes the system with dummy data and starts the CLI
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Inventory Management System...");
        
        // Add dummy data
        addDummyData();
        
        // Create and start the CLI
        CLI cli = new CLI();
        cli.showMenu();
    }
    
    /**
     * Adds dummy data to the inventory system
     * Creates 5 items of different categories
     */
    private static void addDummyData() {
        InventoryManager manager = new InventoryManager();
        
        // Create 5 items of different categories
        InventoryItem item1 = new InventoryItem(101, "Laptop", "Electronics", 50, 999.99, "TechSupplier");
        InventoryItem item2 = new InventoryItem(202, "Office Chair", "Furniture", 30, 149.99, "FurnitureWorld");
        InventoryItem item3 = new InventoryItem(303, "Notebook", "Stationery", 200, 4.99, "PaperCo");
        InventoryItem item4 = new InventoryItem(404, "Coffee Maker", "Appliances", 25, 79.99, "HomeGoods");
        InventoryItem item5 = new InventoryItem(505, "First Aid Kit", "Medical", 100, 29.99, "HealthSupplies");
        
        // Add items to inventory
        manager.createItem(item1);
        manager.createItem(item2);
        manager.createItem(item3);
        manager.createItem(item4);
        manager.createItem(item5);
        
        System.out.println("Dummy data added successfully.");
    }
}
