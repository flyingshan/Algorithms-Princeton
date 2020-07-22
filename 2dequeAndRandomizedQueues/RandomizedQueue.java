//package QueueAssignment;
import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
public class RandomizedQueue<Item> implements Iterable<Item> {
   private int n; // number of items
   private Node last;
   private Node rand;
   public RandomizedQueue(){                 // construct an empty randomized queue
         n = 0;
		 last = null;
		 rand = null;

   }
   private class Node{  //when define a class, no bracket used, when instantiation, bracket used
      private Item item;
      private Node next;
   }
   public boolean isEmpty()                 // is the randomized queue empty?
   { return n == 0; }
   public int size()                        // return the number of items on the randomized queue
   { return n; }
   public void enqueue(Item item){           // add the item
	     if (item == null) throw new java.lang.IllegalArgumentException();
         Node oldlast = last;
		 last = new Node();
		 last.item = item;
		 last.next = oldlast;
		 n++;
   }

   public Item dequeue(){                    // remove and return a random item
      if ( isEmpty() ) throw new NoSuchElementException("Queue Underflow");
      int randn = StdRandom.uniform(n);
      Item randitem = sample(randn);
      //deleteRandomItem(randn);
      n--;
      if (randn == 0) {
    	  if (last.next == null) last = null;
    	  else last = last.next;
      }
      else if (randn == 1){
    	  if (last.next.next == null) last.next = null;
    	  else last.next = last.next.next;
    	  }
      else {
    	  rand = last;
    	  for (int i=0; i < randn-1;i++) {
    		  rand = rand.next;
    		  //StdOut.println(rand.item);
    	  }
    	  if (rand.next.next == null) rand.next = null;
    	  else rand.next = rand.next.next;
    	  //StdOut.println("randn"+":"+randn);
    	  //StdOut.println("randitem:"+rand.item);
    	  //StdOut.println("lastitem:"+last.item);
      }
      return randitem;
   }
   private Item sample(int nRand) {                     // return a random item (but do not remove it)
	      rand = last;
		  for(int i =0; i < nRand; i++){
		     rand = rand.next;
		  }
		  // StdOut.println(rand == last);
		  return rand.item;
	   }  	  
   public Item sample() {                     // return a random item (but do not remove it)
	  if (isEmpty()) throw new java.util.NoSuchElementException();
      int nRand = StdRandom.uniform(n);
      rand = last;
	  for(int i =0; i < nRand; i++){
	     rand = rand.next;
	  }
	  return rand.item;
   }  	  
   public Iterator<Item> iterator(){         // return an independent iterator over items in random order 杩欓噷鏄痯ublic鐨�
      return new randQueueIterator();
   }
   private class randQueueIterator implements Iterator<Item> {
      private int current = 0;
      private int[] nitem = new int[n];
      private randQueueIterator() {
          for(int i = 0; i < n; i++) { nitem[i] = i; }
          StdRandom.shuffle(nitem);
      }
      public boolean hasNext() { return  current < n; }
	  public void remove() { throw new UnsupportedOperationException(); }
	  public Item next() {
		  if (!hasNext())  throw new NoSuchElementException();
		  Item item = sample(nitem[current]);
		  current++;
		  return item;
	  }
   }
   public static void main(String[] args){   // unit testing (optional)
   RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
   for (int i=0; i < 100; i ++) rq.enqueue(i);
   // for(int i : rq) StdOut.println(i);
   for(int i=0; i < 50; i ++) {
	   int c = rq.dequeue();   
	   StdOut.println(c);
   }
   for(int j : rq) StdOut.println(j);
   StdOut.println(rq.size());
   }
}	
