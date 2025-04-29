package src;
import java.io.*;
import src.datastructures.CustomArrayList;

public class FileManager {

    // Write items to a file
    public static void writeToFile(String filename, CustomArrayList<InventoryItem> items) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(items);
            System.out.println("Data saved successfully to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Read items from a file
    @SuppressWarnings("unchecked")
    public static CustomArrayList<InventoryItem> readFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("File not found: " + filename);
            return new CustomArrayList<>();
        }
        
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            CustomArrayList<InventoryItem> items = (CustomArrayList<InventoryItem>) in.readObject();
            return items;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
            e.printStackTrace();
            return new CustomArrayList<>();
        }
    }
    
    // Check if a file exists
    public static boolean fileExists(String filename) {
        File file = new File(filename);
        return file.exists() && file.isFile();
    }
    
    // Delete a file
    public static boolean deleteFile(String filename) {
        File file = new File(filename);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }
}
