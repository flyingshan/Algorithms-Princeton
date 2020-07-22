//package QueueAssignment;
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
   private Item[] deq; // double-ended queue
   private int n; // number of items in deque
   private int first; // first item index
   private int last; // last item index
   
   public Deque()                           // construct an empty deque
   {
	   deq = (Item[]) new Object[2];
	   n = 0;
	   first = 0;
	   last = 0;
   }
   public boolean isEmpty()                 // is the deque empty?
   {
	   return (n == 0);
   }
   public int size()                        // return the number of items on the deque
   {
	   return n;
   }
   private void resize(int capacity) { //resizing array implement should have a "resize function"
      assert capacity >= n;
	  Item[] temp = (Item[]) new Object[capacity];
	  for (int i = 0; i < n; i++) {
		  temp[i] = deq[(first + i) % deq.length];
	  }
	  deq = temp;
	  first = 0; // 这里有个问题，采用array实现的话，如果在队列首添加元素，应该要把索引指向array尾端
	  last  = n;
}
   public void addFirst(Item item)          // add the item to the front
   {
	  if(item == null) throw new java.lang.IllegalArgumentException();
      // double size of array if necessary and recopy to front of array
      if (n == deq.length) resize(2*deq.length);   // double size of array if necessary
	  if (first == 0) {
		 first = deq.length - 1;
	     deq[first] = item;
	  }
	  else deq[--first] = item;                        // add item
      n++;
   }
   public void addLast(Item item)           // add the item to the end
   {
	  if(item == null) throw new java.lang.IllegalArgumentException();
      // double size of array if necessary and recopy to front of array
      if (n == deq.length) resize(2*deq.length);   // double size of array if necessary
      deq[last++] = item;                        // add item
      if (last == deq.length) last = 0;          // wrap-around
      n++;
   }
   public Item removeFirst()                // remove and return the item from the front
   {
      if (isEmpty()) throw new NoSuchElementException("Dequeue underflow");
      Item item = deq[first];
      deq[first] = null;                            // to avoid loitering (don't understand)
      n--;
      first++;
      if (first == deq.length) first = 0;           // wrap-around
      // shrink size of array if necessary
      if (n > 0 && n == deq.length/4) resize(deq.length/2); 
      return item;
   }

   public Item removeLast()                 // remove and return the item from the end
   {
	  Item item;
      if (isEmpty()) throw new NoSuchElementException("Dequeue underflow");
	  if (last == 0) {
		 last = deq.length-1;
	     item = deq[last];
		 deq[last] = null;                            // to avoid loitering (don't understand)
		 
	  }
	  else {
		 item = deq[--last];
		 deq[last] = null;                            // to avoid loitering (don't understand)
	  }
      n--;
      if (n > 0 && n == deq.length/4) resize(deq.length/2); 
      return item;
   }
   public Iterator<Item> iterator()         // return an iterator over items in order from front to end
   {
	   return new ArrayIterator();
   }
   private class ArrayIterator implements Iterator<Item> {
       private int i = 0;
       public boolean hasNext()  { return i < n; }
       public void remove()      { throw new UnsupportedOperationException(); }
       public Item next() {
           if (!hasNext()) throw new NoSuchElementException();
           Item item = deq[(i + first) % deq.length];  //quyu does good!
           i++;
           return item;
       }
   }
   public static void main(String[] args)   // unit testing (optional)
   {
	   Deque<Integer> qu = new Deque<Integer>();
	   qu.addFirst(12);
	   qu.addLast(32);
	   qu.removeFirst();
	   qu.removeLast();
	   StdOut.println(qu.isEmpty());
	   qu.addFirst(1);
	   qu.addFirst(2);
	   qu.addFirst(3);
	   for (int i : qu) StdOut.println(i);
	   StdOut.println(qu.size());
		   
   }
}