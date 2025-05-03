Inventory Management System - Class Documentation
Table of Contents
















Introduction
This document provides a detailed explanation of each class in the Inventory Management System project. The system is designed to manage inventory items with features like adding, viewing, updating, and deleting items, as well as saving and loading data from files. It also implements the Command pattern for undo/redo functionality and uses custom data structures.
Main.java
Purpose
Main.java serves as the entry point for the Inventory Management System application.
Code Explanation
package src;

public class Main {
 public static void main(String[] args) {
 System.out.println("Starting Inventory Management System...");
 
 // Create and start the CLI
 CLI cli = new CLI();
 cli.showMenu();
 }
}
Key Points
Package Declaration: package src; indicates that this class belongs to the "src" package, helping organize related classes.
Main Method: The main method is the entry point for Java applications. When you run the program, Java looks for this method to start execution.
CLI Instantiation: Creates a new instance of the CLI (Command Line Interface) class, which handles user interaction.
Method Call: Calls the showMenu() method on the CLI instance to display the menu and start the application.
For Beginners
Think of Main.java as the "front door" to your application. When someone wants to use your program, they enter through this door. Its job is simple: print a welcome message and hand control over to the CLI class, which will handle all user interactions.
CLI.java
Purpose
CLI.java (Command Line Interface) manages all user interactions, collecting input and displaying output. It acts as the interface between the user and the system's functionality.
Imports
import java.util.Scanner;
import src.command.*;
java.util.Scanner: Used to read user input from the console. The Scanner class provides methods to read different types of data (integers, strings, etc.) from various sources.
src.command.: Imports all classes from the command package, which contains classes related to the Command pattern implementation for undo/redo functionality.
Fields
private InventoryManager manager;
private Scanner scanner;
private CommandManager commandManager;
manager: An instance of InventoryManager that handles inventory operations.
scanner: Used to read user input from the console.
commandManager: Manages the execution, undoing, and redoing of commands.
Constructor
public CLI() {
 manager = new InventoryManager();
 scanner = new Scanner(System.in);
 commandManager = new CommandManager();
}
The constructor initializes the fields:
Creates a new InventoryManager to manage inventory items
Creates a Scanner that reads from standard input (keyboard)
Creates a CommandManager to handle undo/redo operations
Methods
showMenu()
public void showMenu() {
 int choice;
 do {
 displayMenu();
 choice = getValidIntInput("Enter your choice: ", 0, 10);

 switch (choice) {
 case 1:
 addItem();
 break;
 // Other cases...
 case 0:
 System.out.println("Exiting the program...");
 break;
 default:
 System.out.println("Invalid choice! Please try again.");
 }
 } while (choice != 0);
}
This method:
Displays the menu options using displayMenu()
Gets a valid integer input from the user using getValidIntInput()
Uses a switch statement to call the appropriate method based on the user's choice
Continues this process until the user chooses to exit (choice = 0)
Input Validation Methods
private int getValidIntInput(String prompt, int min, int max) {
 // Implementation...
}

private double getValidDoubleInput(String prompt) {
 // Implementation...
}

private String getValidStringInput(String prompt) {
 // Implementation...
}
These methods ensure that user input is valid:
getValidIntInput(): Ensures the input is an integer within a specified range
getValidDoubleInput(): Ensures the input is a valid double
getValidStringInput(): Ensures the input is a non-empty string
CRUD Operations
public void addItem() {
 // Implementation...
}

public void viewItem() {
 // Implementation...
}

public void updateItem() {
 // Implementation...
}

public void deleteItem() {
 // Implementation...
}
These methods handle the Create, Read, Update, and Delete operations for inventory items:
addItem(): Collects item details from the user, creates a new InventoryItem, and adds it to the inventory using the Command pattern
viewItem(): Displays details of a specific item
updateItem(): Updates an existing item's details
deleteItem(): Removes an item from the inventory
File Operations
public void saveToFile() {
 // Implementation...
}

public void loadFromFile() {
 // Implementation...
}
These methods handle saving and loading inventory data to/from files.
Undo/Redo Operations
public void undoOperation() {
 // Implementation...
}

public void redoOperation() {
 // Implementation...
}
These methods handle undoing and redoing operations using the Command pattern.
For Beginners
The CLI class is like a receptionist at a hotel. It:
Greets users (displays the menu)
Takes their requests (gets input)
Validates their requests (input validation)
Forwards requests to the appropriate department (calls methods on other classes)
Provides feedback (displays results)
The input validation methods are particularly important because they prevent invalid data from entering the system, which could cause errors or unexpected behavior.
InventoryItem.java
Purpose
InventoryItem.java represents an individual item in the inventory with its attributes and behaviors.
Imports
import java.io.Serializable;
java.io.Serializable: This is an interface that allows objects to be converted to a byte stream (serialized) and back (deserialized). This is necessary for saving objects to files and reading them back.
Fields
private static final long serialVersionUID = 1L;
private int itemId;
private String name;
private String category;
private int quantity;
private double price;
private String supplier;
serialVersionUID: A unique identifier for the serialized class. It helps ensure that the serialized and deserialized objects are compatible.
itemId: A unique identifier for the item
name: The name of the item
category: The category the item belongs to
quantity: The number of items in stock
price: The price of the item
supplier: The supplier of the item
Constructor
public InventoryItem(int itemId, String name, String category, int quantity, double price, String supplier) {
 this.itemId = itemId;
 this.name = name;
 this.category = category;
 this.quantity = quantity;
 this.price = price;
 this.supplier = supplier;
}
The constructor initializes all fields with the provided values. The this keyword is used to distinguish between the class fields and the constructor parameters when they have the same name.
Methods
// Getter and setter methods for each attribute
public int getItemId() {
 return itemId;
}

public void setItemId(int itemId) {
 this.itemId = itemId;
}

// Other getters and setters...

@Override
public String toString() {
 return "InventoryItem{" +
 "itemId='" + itemId + '\'' +
 ", name='" + name + '\'' +
 ", category='" + category + '\'' +
 ", quantity=" + quantity +
 ", price=" + price +
 ", supplier='" + supplier + '\'' +
 '}';
}
Getters and Setters: These methods provide controlled access to the private fields of the class, following the encapsulation principle of object-oriented programming.
toString(): Overrides the default toString() method from the Object class to provide a string representation of the item. This is useful for displaying item details.
For Beginners
Think of InventoryItem as a digital representation of a physical item in your inventory. Just as a real item has properties like name, price, and quantity, this class stores these details in variables.
The Serializable interface is like giving your object the ability to be "frozen" and "unfrozen" - it can be saved to a file (frozen) and later loaded back into memory (unfrozen) with all its properties intact.
Getters and setters are like controlled access points to the object's data. Instead of directly accessing the fields (which could lead to errors), other classes must use these methods, which can include validation or other logic.
InventoryManager.java
Purpose
InventoryManager.java manages the collection of inventory items and provides methods for CRUD operations, sorting, filtering, and file operations.
Imports
import src.datastructures.CustomArrayList;
import src.datastructures.SortingAlgorithms;
import java.util.Comparator;
src.datastructures.CustomArrayList: A custom implementation of an array list to store inventory items.
src.datastructures.SortingAlgorithms: Provides custom sorting algorithms for the inventory items.
java.util.Comparator: Used to define custom comparison logic for sorting items.
Fields
private CustomArrayList items;
items: A custom array list that stores all inventory items.
Constructor
public InventoryManager() {
 items = new CustomArrayList<>();
}
The constructor initializes the items field with a new empty CustomArrayList.
Methods
CRUD Operations
public void addItem(InventoryItem item) {
 items.add(item);
}

public InventoryItem viewItem(int id) {
 // Implementation...
}

public boolean deleteItem(int id) {
 // Implementation...
}

public void updateItem(int id, InventoryItem newItem) {
 // Implementation...
}
These methods handle the basic CRUD operations:
addItem(): Adds a new item to the inventory
viewItem(): Returns an item with the specified ID
deleteItem(): Removes an item with the specified ID
updateItem(): Updates an existing item with new details
Display Methods
public void viewAllItems() {
 // Implementation...
}

public void viewLowStockItems(int threshold) {
 // Implementation...
}
These methods display items in a formatted table:
viewAllItems(): Displays all items, sorted by category and name
viewLowStockItems(): Displays items with quantity below a specified threshold
File Operations
public void saveToFile(String filename) {
 FileManager.writeToFile(filename, items);
}

public void loadFromFile(String filename) {
 CustomArrayList loadedItems = FileManager.readFromFile(filename);
 if (loadedItems != null && !loadedItems.isEmpty()) {
 items = loadedItems;
 System.out.println("Loaded " + items.size() + " items from " + filename);
 }
}
These methods handle saving and loading inventory data to/from files using the FileManager class.
Query Methods
public double getTotalInventoryValue() {
 // Implementation...
}

public CustomArrayList getItemsByCategory(String category) {
 // Implementation...
}

public CustomArrayList getLowStockItems(int threshold) {
 // Implementation...
}
These methods provide additional functionality:
getTotalInventoryValue(): Calculates the total value of all items in the inventory
getItemsByCategory(): Returns items belonging to a specific category
getLowStockItems(): Returns items with quantity below a specified threshold
For Beginners
The InventoryManager is like a warehouse manager who keeps track of all items in the warehouse. It:
Stores all items in a list
Provides methods to add, view, update, and delete items
Can display items in different ways (all items, low stock items)
Can save the inventory to a file and load it back
Can answer questions about the inventory (total value, items by category)
The use of a custom array list (CustomArrayList) instead of Java's built-in ArrayList is for educational purposes, demonstrating how data structures work under the hood.
FileManager.java
Purpose
FileManager.java handles file operations for saving and loading inventory data.
Imports
import java.io.*;
import src.datastructures.CustomArrayList;
java.io.: Provides classes for input and output operations, including file operations.
src.datastructures.CustomArrayList: The custom array list used to store inventory items.
Methods
writeToFile()
public static void writeToFile(String filename, CustomArrayList items) {
 try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
 out.writeObject(items);
 System.out.println("Data saved successfully to " + filename);
 } catch (IOException e) {
 System.out.println("Error saving data: " + e.getMessage());
 e.printStackTrace();
 }
}
This method:
Creates an ObjectOutputStream to write objects to a file
Writes the items object to the file
Handles any IOException that might occur
The try-with-resources statement ensures that the stream is closed properly, even if an exception occurs.
readFromFile()
@SuppressWarnings("unchecked")
public static CustomArrayList readFromFile(String filename) {
 File file = new File(filename);
 if (!file.exists()) {
 System.out.println("File not found: " + filename);
 return new CustomArrayList<>();
 }
 
 try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
 CustomArrayList items = (CustomArrayList) in.readObject();
 return items;
 } catch (IOException | ClassNotFoundException e) {
 System.out.println("Error loading data: " + e.getMessage());
 e.printStackTrace();
 return new CustomArrayList<>();
 }
}
This method:
Checks if the file exists
Creates an ObjectInputStream to read objects from the file
Reads and returns the CustomArrayList object from the file
Handles any exceptions that might occur
The @SuppressWarnings("unchecked") annotation suppresses warnings about the unchecked cast from Object to CustomArrayList.
Utility Methods
public static boolean fileExists(String filename) {
 File file = new File(filename);
 return file.exists() && file.isFile();
}

public static boolean deleteFile(String filename) {
 File file = new File(filename);
 if (file.exists()) {
 return file.delete();
 }
 return false;
}
These utility methods:
fileExists(): Checks if a file exists
deleteFile(): Deletes a file if it exists
For Beginners
The FileManager is like a librarian who handles the storage and retrieval of books (data). It:
Saves data to files (like putting books on shelves)
Loads data from files (like retrieving books from shelves)
Checks if files exist (like checking if a book is in the library)
Deletes files (like removing books from the library)
The use of static methods means you don't need to create an instance of FileManager to use its methods - you can call them directly on the class, like FileManager.writeToFile(...).
Command Pattern Classes
Command.java
Purpose
Command.java defines the interface for the Command pattern, which is used to implement undo/redo functionality.
Code Explanation
package src.command;

public interface Command {
 void execute();
 void undo();
}
Key Points
Interface Declaration: Defines a contract that all command classes must follow.
execute() Method: This method performs the command's action.
undo() Method: This method reverses the command's action.
For Beginners
Think of the Command interface as a blueprint for creating "action" objects. Each action in your program (like adding or deleting an item) can be wrapped in a command object that knows how to perform the action (execute()) and how to reverse it (undo()).
This is similar to a remote control where each button performs a specific action, and some remotes have an "undo" button to reverse the last action.
CommandManager.java
Purpose
CommandManager.java manages the execution, undoing, and redoing of commands.
Imports
import src.datastructures.CustomStack;
src.datastructures.CustomStack: A custom implementation of a stack used to store commands for undo/redo operations.
Fields
private CustomStack undoStack;
private CustomStack redoStack;
undoStack: Stores commands that have been executed and can be undone.
redoStack: Stores commands that have been undone and can be redone.
Constructor
public CommandManager() {
 undoStack = new CustomStack<>();
 redoStack = new CustomStack<>();
}
The constructor initializes the undoStack and redoStack fields with new empty CustomStack instances.
Methods
public void executeCommand(Command command) {
 command.execute();
 undoStack.push(command);
 redoStack.clear(); // Clear redo stack when a new command is executed
}

public boolean canUndo() {
 return !undoStack.isEmpty();
}

public boolean canRedo() {
 return !redoStack.isEmpty();
}

public void undo() {
 if (canUndo()) {
 Command command = undoStack.pop();
 command.undo();
 redoStack.push(command);
 }
}

public void redo() {
 if (canRedo()) {
 Command command = redoStack.pop();
 command.execute();
 undoStack.push(command);
 }
}
These methods handle the execution, undoing, and redoing of commands:
executeCommand(): Executes a command, adds it to the undo stack, and clears the redo stack
canUndo() and canRedo(): Check if undo/redo operations are possible
undo(): Undoes the last executed command and moves it to the redo stack
redo(): Redoes the last undone command and moves it back to the undo stack
For Beginners
The CommandManager is like a history keeper who remembers all the actions you've taken and can help you go back in time (undo) or forward again (redo). It:
Keeps track of executed commands in the undo stack
Keeps track of undone commands in the redo stack
Executes new commands and adds them to the undo stack
Undoes commands by moving them from the undo stack to the redo stack
Redoes commands by moving them from the redo stack back to the undo stack
This is similar to how the undo/redo functionality works in text editors or other applications.
AddItemCommand.java
Purpose
AddItemCommand.java implements the Command interface for adding an item to the inventory.
Imports
import src.InventoryItem;
import src.InventoryManager;
src.InventoryItem: The class representing an inventory item.
src.InventoryManager: The class managing the inventory.
Fields
private InventoryManager manager;
private InventoryItem item;
manager: The inventory manager that will perform the actual add operation.
item: The item to be added.
Constructor
public AddItemCommand(InventoryManager manager, InventoryItem item) {
 this.manager = manager;
 this.item = item;
}
The constructor initializes the manager and item fields with the provided values.
Methods
@Override
public void execute() {
 manager.addItem(item);
}

@Override
public void undo() {
 manager.deleteItem(item.getItemId());
}
These methods implement the Command interface:
execute(): Adds the item to the inventory
undo(): Removes the item from the inventory
For Beginners
The AddItemCommand is like a note that says "add this item to the inventory." When you execute the command, it adds the item. If you later decide to undo this action, it removes the item.
This class follows the Command pattern, which separates the action (adding an item) from the object that performs the action (the inventory manager). This separation makes it easier to implement features like undo/redo.
UpdateItemCommand.java
Purpose
UpdateItemCommand.java implements the Command interface for updating an item in the inventory.
Imports
import src.InventoryItem;
import src.InventoryManager;
src.InventoryItem: The class representing an inventory item.
src.InventoryManager: The class managing the inventory.
Fields
private InventoryManager manager;
private InventoryItem oldItem;
private InventoryItem newItem;
private int itemId;
manager: The inventory manager that will perform the actual update operation.
oldItem: The original item before the update.
newItem: The updated item.
itemId: The ID of the item to be updated.
Constructor
public UpdateItemCommand(InventoryManager manager, int itemId, InventoryItem newItem) {
 this.manager = manager;
 this.itemId = itemId;
 this.oldItem = manager.viewItem(itemId);
 this.newItem = newItem;
}
The constructor initializes the fields and retrieves the original item from the inventory.
Methods
@Override
public void execute() {
 manager.updateItem(itemId, newItem);
}

@Override
public void undo() {
 if (oldItem != null) {
 manager.updateItem(itemId, oldItem);
 }
}
These methods implement the Command interface:
execute(): Updates the item in the inventory
undo(): Restores the original item
For Beginners
The UpdateItemCommand is like a note that says "update this item with these new details." When you execute the command, it updates the item. If you later decide to undo this action, it restores the original details.
The command stores both the new item and the original item, so it can switch between them when executing and undoing the command.
DeleteItemCommand.java
Purpose
DeleteItemCommand.java implements the Command interface for deleting an item from the inventory.
Imports
import src.InventoryItem;
import src.InventoryManager;
src.InventoryItem: The class representing an inventory item.
src.InventoryManager: The class managing the inventory.
Fields
private InventoryManager manager;
private InventoryItem item;
private int itemId;
manager: The inventory manager that will perform the actual delete operation.
item: The item to be deleted (stored for undo).
itemId: The ID of the item to be deleted.
Constructor
public DeleteItemCommand(InventoryManager manager, int itemId) {
 this.manager = manager;
 this.itemId = itemId;
 this.item = manager.viewItem(itemId);
}
The constructor initializes the fields and retrieves the item from the inventory.
Methods
@Override
public void execute() {
 manager.deleteItem(itemId);
}

@Override
public void undo() {
 if (item != null) {
 manager.addItem(item);
 }
}
These methods implement the Command interface:
execute(): Deletes the item from the inventory
undo(): Adds the item back to the inventory
For Beginners
The DeleteItemCommand is like a note that says "delete this item from the inventory." When you execute the command, it deletes the item. If you later decide to undo this action, it adds the item back.
The command stores a copy of the item before deleting it, so it can restore the item if the delete operation is undone.
Custom Data Structures
CustomArrayList.java
Purpose
CustomArrayList.java is a custom implementation of an array list, which is a dynamic array that can grow or shrink as needed.
Imports
import java.io.Serializable;
java.io.Serializable: This interface allows objects to be converted to a byte stream (serialized) and back (deserialized), which is necessary for saving objects to files.
Fields
private static final long serialVersionUID = 1L;
private static final int DEFAULT\_CAPACITY = 10;
private Object[] elements;
private int size;
serialVersionUID: A unique identifier for the serialized class.
DEFAULT\_CAPACITY: The initial capacity of the array.
elements: The array that stores the elements.
size: The number of elements in the array.
Constructors
public CustomArrayList() {
 elements = new Object[DEFAULT\_CAPACITY];
 size = 0;
}

public CustomArrayList(int initialCapacity) {
 if (initialCapacity < 0) {
 throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
 }
 elements = new Object[initialCapacity];
 size = 0;
}
These constructors initialize the elements array with either the default capacity or a specified capacity.
Methods
public void add(T element) {
 ensureCapacity(size + 1);
 elements[size++] = element;
}

public T get(int index) {
 if (index < 0 || index >= size) {
 throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
 }
 return (T) elements[index];
}

// Other methods...

private void ensureCapacity(int minCapacity) {
 if (minCapacity > elements.length) {
 int newCapacity = Math.max(elements.length * 2, minCapacity);
 Object[] newElements = new Object[newCapacity];
 for (int i = 0; i < size; i++) {
 newElements[i] = elements[i];
 }
 elements = newElements;
 }
}
These methods provide the functionality of an array list:
add(): Adds an element to the end of the list
get(): Returns the element at the specified index
remove(): Removes an element at the specified index or the first occurrence of a specific element
set(): Replaces the element at the specified index
size(): Returns the number of elements in the list
isEmpty(): Checks if the list is empty
clear(): Removes all elements from the list
contains(): Checks if the list contains a specific element
indexOf(): Returns the index of the first occurrence of a specific element
ensureCapacity(): Ensures that the array has enough capacity to store the specified number of elements
For Beginners
The CustomArrayList is like a dynamic bookshelf that can hold any type of object. It:
Starts with a fixed number of slots (DEFAULT\_CAPACITY)
Adds books (elements) to the next available slot
Gets books from specific slots
Removes books from specific slots
Checks if a specific book is on the shelf
Expands the shelf when it runs out of slots
This class demonstrates how Java's built-in ArrayList works under the hood. It uses an array to store elements and provides methods to manipulate the array in a more convenient way.
CustomStack.java
Purpose
CustomStack.java is a custom implementation of a stack, which is a Last-In-First-Out (LIFO) data structure.
Imports
import java.io.Serializable;
java.io.Serializable: This interface allows objects to be converted to a byte stream (serialized) and back (deserialized), which is necessary for saving objects to files.
Fields
private static final long serialVersionUID = 1L;
private static final int DEFAULT\_CAPACITY = 10;
private Object[] elements;
private int size;
serialVersionUID: A unique identifier for the serialized class.
DEFAULT\_CAPACITY: The initial capacity of the array.
elements: The array that stores the elements.
size: The number of elements in the stack.
Constructor
public CustomStack() {
 elements = new Object[DEFAULT\_CAPACITY];
 size = 0;
}
The constructor initializes the elements array with the default capacity.
Methods
public void push(T element) {
 ensureCapacity(size + 1);
 elements[size++] = element;
}

public T pop() {
 if (isEmpty()) {
 throw new IllegalStateException("Stack is empty");
 }
 T element = (T) elements[--size];
 elements[size] = null; // Help garbage collection
 return element;
}

// Other methods...

private void ensureCapacity(int minCapacity) {
 if (minCapacity > elements.length) {
 int newCapacity = Math.max(elements.length * 2, minCapacity);
 Object[] newElements = new Object[newCapacity];
 for (int i = 0; i < size; i++) {
 newElements[i] = elements[i];
 }
 elements = newElements;
 }
}
These methods provide the functionality of a stack:
push(): Adds an element to the top of the stack
pop(): Removes and returns the top element of the stack
peek(): Returns the top element without removing it
isEmpty(): Checks if the stack is empty
size(): Returns the number of elements in the stack
clear(): Removes all elements from the stack
ensureCapacity(): Ensures that the array has enough capacity to store the specified number of elements
For Beginners
The CustomStack is like a stack of plates. It:
Adds plates to the top of the stack (push)
Removes plates from the top of the stack (pop)
Looks at the top plate without removing it (peek)
Checks if there are any plates in the stack (isEmpty)
Counts how many plates are in the stack (size)
Removes all plates from the stack (clear)
This class demonstrates how Java's built-in Stack works under the hood. It uses an array to store elements and provides methods to manipulate the array in a way that follows the LIFO principle.
SortingAlgorithms.java
Purpose
SortingAlgorithms.java provides custom sorting algorithms for the CustomArrayList class.
Imports
import src.InventoryItem;
import java.util.Comparator;
src.InventoryItem: The class representing an inventory item.
java.util.Comparator: Used to define custom comparison logic for sorting items.
Methods
mergeSort()
public static  void mergeSort(CustomArrayList list, Comparator comparator) {
 if (list.size() <= 1) {
 return;
 }
 
 // Create temporary array for merging
 Object[] temp = new Object[list.size()];
 
 // Call the recursive merge sort
 mergeSort(list, 0, list.size() - 1, temp, comparator);
}

private static  void mergeSort(CustomArrayList list, int low, int high, Object[] temp, Comparator comparator) {
 if (low < high) {
 int mid = low + (high - low) / 2;
 
 // Sort first and second halves
 mergeSort(list, low, mid, temp, comparator);
 mergeSort(list, mid + 1, high, temp, comparator);
 
 // Merge the sorted halves
 merge(list, low, mid, high, temp, comparator);
 }
}

private static  void merge(CustomArrayList list, int low, int mid, int high, Object[] temp, Comparator comparator) {
 // Implementation...
}
This method implements the merge sort algorithm, which:
Divides the list into two halves
Recursively sorts each half
Merges the sorted halves
quickSort()
public static  void quickSort(CustomArrayList list, Comparator comparator) {
 if (list.size() <= 1) {
 return;
 }
 
 quickSort(list, 0, list.size() - 1, comparator);
}

private static  void quickSort(CustomArrayList list, int low, int high, Comparator comparator) {
 if (low < high) {
 // Partition the array and get the pivot index
 int pivotIndex = partition(list, low, high, comparator);
 
 // Sort the elements before and after the pivot
 quickSort(list, low, pivotIndex - 1, comparator);
 quickSort(list, pivotIndex + 1, high, comparator);
 }
}

private static  int partition(CustomArrayList list, int low, int high, Comparator comparator) {
 // Implementation...
}
This method implements the quick sort algorithm, which:
Chooses a pivot element
Partitions the list around the pivot
Recursively sorts the partitions
For Beginners
The SortingAlgorithms class provides two different ways to sort a list of items:
Merge Sort: This is like sorting a deck of cards by repeatedly dividing it in half, sorting each half, and then merging the sorted halves. It's stable (preserves the relative order of equal elements) and has a consistent performance (O(n log n)) regardless of the initial order of the elements.
Quick Sort: This is like sorting a deck of cards by choosing a card (pivot), putting all cards lower than the pivot to the left and all cards higher than the pivot to the right, and then recursively sorting the left and right piles. It's generally faster than merge sort in practice but can be slower in the worst case (O(n²)) if the pivot selection is poor.
Both algorithms use a Comparator to determine the order of elements, which allows for flexible sorting criteria (e.g., sort by name, price, or category).
Conclusion
This document has provided a detailed explanation of each class in the Inventory Management System project. The system demonstrates several important programming concepts:
Object-Oriented Programming: The system is organized into classes with clear responsibilities and relationships.
Command Pattern: The system uses the Command pattern to implement undo/redo functionality.
Custom Data Structures: The system implements custom versions of common data structures (ArrayList, Stack) to demonstrate how they work under the hood.
File I/O: The system uses Java's serialization mechanism to save and load data from files.
Input Validation: The system validates user input to ensure data integrity.
By understanding these concepts and how they're applied in this project, you'll be better equipped to design and implement your own Java applications.
