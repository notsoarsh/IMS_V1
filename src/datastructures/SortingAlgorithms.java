package src.datastructures;

import src.InventoryItem;
import java.util.Comparator;

/**
 * Custom sorting algorithms implementation
 */
public class SortingAlgorithms {

    /**
     * Merge sort implementation for CustomArrayList
     * @param list The list to sort
     * @param comparator The comparator to use for comparison
     */
    public static <T> void mergeSort(CustomArrayList<T> list, Comparator<T> comparator) {
        if (list.size() <= 1) {
            return;
        }
        
        // Create temporary array for merging
        Object[] temp = new Object[list.size()];
        
        // Call the recursive merge sort
        mergeSort(list, 0, list.size() - 1, temp, comparator);
    }
    
    private static <T> void mergeSort(CustomArrayList<T> list, int low, int high, Object[] temp, Comparator<T> comparator) {
        if (low < high) {
            int mid = low + (high - low) / 2;
            
            // Sort first and second halves
            mergeSort(list, low, mid, temp, comparator);
            mergeSort(list, mid + 1, high, temp, comparator);
            
            // Merge the sorted halves
            merge(list, low, mid, high, temp, comparator);
        }
    }
    
    private static <T> void merge(CustomArrayList<T> list, int low, int mid, int high, Object[] temp, Comparator<T> comparator) {
        // Copy data to temp arrays
        for (int i = low; i <= high; i++) {
            temp[i] = list.get(i);
        }
        
        int i = low;      // Initial index of first subarray
        int j = mid + 1;  // Initial index of second subarray
        int k = low;      // Initial index of merged subarray
        
        // Merge the temp arrays back into list
        while (i <= mid && j <= high) {
            if (comparator.compare((T)temp[i], (T)temp[j]) <= 0) {
                list.set(k, (T)temp[i]);
                i++;
            } else {
                list.set(k, (T)temp[j]);
                j++;
            }
            k++;
        }
        
        // Copy the remaining elements of left subarray, if any
        while (i <= mid) {
            list.set(k, (T)temp[i]);
            i++;
            k++;
        }
        
        // Copy the remaining elements of right subarray, if any
        while (j <= high) {
            list.set(k, (T)temp[j]);
            j++;
            k++;
        }
    }
    
    /**
     * Quick sort implementation for CustomArrayList
     * @param list The list to sort
     * @param comparator The comparator to use for comparison
     */
    public static <T> void quickSort(CustomArrayList<T> list, Comparator<T> comparator) {
        if (list.size() <= 1) {
            return;
        }
        
        quickSort(list, 0, list.size() - 1, comparator);
    }
    
    private static <T> void quickSort(CustomArrayList<T> list, int low, int high, Comparator<T> comparator) {
        if (low < high) {
            // Partition the array and get the pivot index
            int pivotIndex = partition(list, low, high, comparator);
            
            // Sort the elements before and after the pivot
            quickSort(list, low, pivotIndex - 1, comparator);
            quickSort(list, pivotIndex + 1, high, comparator);
        }
    }
    
    private static <T> int partition(CustomArrayList<T> list, int low, int high, Comparator<T> comparator) {
        // Choose the rightmost element as pivot
        T pivot = list.get(high);
        
        // Index of smaller element
        int i = low - 1;
        
        for (int j = low; j < high; j++) {
            // If current element is smaller than or equal to pivot
            if (comparator.compare(list.get(j), pivot) <= 0) {
                i++;
                
                // Swap list[i] and list[j]
                T temp = list.get(i);
                list.set(i, list.get(j));
                list.set(j, temp);
            }
        }
        
        // Swap list[i+1] and list[high] (or pivot)
        T temp = list.get(i + 1);
        list.set(i + 1, list.get(high));
        list.set(high, temp);
        
        return i + 1;
    }
}
