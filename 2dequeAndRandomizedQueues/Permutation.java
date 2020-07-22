//package QueueAssignment;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;
public class Permutation {
   public static void main(String[] args) {
	   int K = Integer.parseInt(args[0]);
	   RandomizedQueue<String> rq = new RandomizedQueue<String>();
	   while(!StdIn.isEmpty()) {
	       rq.enqueue(StdIn.readString());
	   }
	   for(int i=0; i < K; i++)
		   StdOut.println(rq.dequeue());
	   }
   }

   
