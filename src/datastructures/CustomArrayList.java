
package src.datastructures;

import java.io.Serializable;

public class CustomArrayList<T> implements Serializable {
   private static final long serialVersionUID = 1L;
   private static final int DEFAULT_CAPACITY = 10;
   private Object[] elements;
   private int size;

   public CustomArrayList() {
      this.elements = new Object[10];
      this.size = 0;
   }

   public CustomArrayList(int initialCapacity) {
      if (initialCapacity < 0) {
         throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
      } else {
         this.elements = new Object[initialCapacity];
         this.size = 0;
      }
   }

   public void add(T element) {
      this.ensureCapacity(this.size + 1);
      this.elements[this.size++] = element;
   }

   public T get(int index) {
      if (index >= 0 && index < this.size) {
         return this.elements[index];
      } else {
         throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
      }
   }

   public T remove(int index) {
      if (index >= 0 && index < this.size) {
         T removedElement = this.elements[index];

         for(int i = index; i < this.size - 1; ++i) {
            this.elements[i] = this.elements[i + 1];
         }

         this.elements[--this.size] = null;
         return removedElement;
      } else {
         throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
      }
   }

   public boolean remove(T element) {
      for(int i = 0; i < this.size; ++i) {
         if (element.equals(this.elements[i])) {
            this.remove(i);
            return true;
         }
      }

      return false;
   }

   public void set(int index, T element) {
      if (index >= 0 && index < this.size) {
         this.elements[index] = element;
      } else {
         throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
      }
   }

   public int size() {
      return this.size;
   }

   public boolean isEmpty() {
      return this.size == 0;
   }

   public void clear() {
      for(int i = 0; i < this.size; ++i) {
         this.elements[i] = null;
      }

      this.size = 0;
   }

   public boolean contains(T element) {
      return this.indexOf(element) >= 0;
   }

   public int indexOf(T element) {
      int i;
      if (element == null) {
         for(i = 0; i < this.size; ++i) {
            if (this.elements[i] == null) {
               return i;
            }
         }
      } else {
         for(i = 0; i < this.size; ++i) {
            if (element.equals(this.elements[i])) {
               return i;
            }
         }
      }

      return -1;
   }

   private void ensureCapacity(int minCapacity) {
      if (minCapacity > this.elements.length) {
         int newCapacity = Math.max(this.elements.length * 2, minCapacity);
         Object[] newElements = new Object[newCapacity];

         for(int i = 0; i < this.size; ++i) {
            newElements[i] = this.elements[i];
         }

         this.elements = newElements;
      }

   }
}
