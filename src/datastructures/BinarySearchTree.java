package src.datastructures;

import src.InventoryItem;
import java.io.Serializable;
import java.util.Comparator;
import java.util.function.Consumer;

/**
 * Binary Search Tree implementation for storing inventory items
 * Uses itemId as the key for ordering
 */
public class BinarySearchTree<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Node root;
    private int size;
    private final Comparator<T> comparator;

    private class Node {
        T data;
        Node left;
        Node right;

        Node(T data) {
            this.data = data;
            this.left = null;
            this.right = null;
        }
    }

    /**
     * Constructor with custom comparator
     * @param comparator Comparator to order items
     */
    public BinarySearchTree(Comparator<T> comparator) {
        this.root = null;
        this.size = 0;
        this.comparator = comparator;
    }

    /**
     * Adds an item to the BST
     * @param item The item to add
     */
    public void add(T item) {
        root = addRecursive(root, item);
        size++;
    }

    private Node addRecursive(Node current, T item) {
        if (current == null) {
            return new Node(item);
        }

        int compareResult = comparator.compare(item, current.data);
        if (compareResult < 0) {
            current.left = addRecursive(current.left, item);
        } else if (compareResult > 0) {
            current.right = addRecursive(current.right, item);
        } else {
            // Update existing item
            current.data = item;
            size--; // Adjust size since we're replacing
        }
        return current;
    }

    /**
     * Removes an item by its key (itemId for InventoryItem)
     * @param key The key to identify the item
     * @return true if item was removed
     */
    public boolean remove(Object key) {
        int initialSize = size;
        root = removeRecursive(root, key);
        return size < initialSize;
    }

    private Node removeRecursive(Node current, Object key) {
        if (current == null) {
            return null;
        }

        InventoryItem item = (InventoryItem) current.data;
        int compareResult = Integer.compare(((InventoryItem) key).getItemId(), item.getItemId());
        if (compareResult < 0) {
            current.left = removeRecursive(current.left, key);
        } else if (compareResult > 0) {
            current.right = removeRecursive(current.right, key);
        } else {
            size--;
            if (current.left == null) {
                return current.right;
            } else if (current.right == null) {
                return current.left;
            } else {
                Node successor = findMin(current.right);
                current.data = successor.data;
                current.right = removeRecursive(current.right, successor.data);
            }
        }
        return current;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /**
     * Finds an item by its key (itemId for InventoryItem)
     * @param key The key to search for
     * @return The item if found, null otherwise
     */
    public T find(Object key) {
        return findRecursive(root, key);
    }

    private T findRecursive(Node current, Object key) {
        if (current == null) {
            return null;
        }

        InventoryItem item = (InventoryItem) current.data;
        int compareResult = Integer.compare(((InventoryItem) key).getItemId(), item.getItemId());
        if (compareResult == 0) {
            return current.data;
        } else if (compareResult < 0) {
            return findRecursive(current.left, key);
        } else {
            return findRecursive(current.right, key);
        }
    }

    /**
     * Performs an in-order traversal, applying the consumer to each item
     * @param consumer The consumer to process each item
     */
    public void inOrderTraversal(Consumer<T> consumer) {
        inOrderTraversalRecursive(root, consumer);
    }

    private void inOrderTraversalRecursive(Node current, Consumer<T> consumer) {
        if (current != null) {
            inOrderTraversalRecursive(current.left, consumer);
            consumer.accept(current.data);
            inOrderTraversalRecursive(current.right, consumer);
        }
    }

    /**
     * Returns the number of items in the BST
     * @return The size
     */
    public int size() {
        return size;
    }

    /**
     * Checks if the BST is empty
     * @return true if empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the BST
     */
    public void clear() {
        root = null;
        size = 0;
    }
}
