package src;

/**
 * Main entry point for the Inventory Management System
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Starting Inventory Management System...");
        
        // Create and start the CLI
        CLI cli = new CLI();
        cli.showMenu();
    }
}
