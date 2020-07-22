package wordNet;
import java.util.Iterator;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class SAP {
       private Digraph G;
       private int V;
	   // constructor takes a digraph (not necessarily a DAG)
	   public SAP(Digraph G) {
		   this.G = new Digraph(G); // immutable should new a digraph
		   this.V = G.V();
	   }

	   // length of shortest ancestral path between v and w; -1 if no such path
	   public int length(int v, int w) {
		   if (v < 0 || v >= V || w < 0 || w >= V) {
			   throw new java.lang.IllegalArgumentException("Index for length out of range!");
		   }
		   // try to return the distance, but how?
		   // NEED TO DETERMINE WHETHER THERE IS A PATH OR NOT
		   // 1. use BFS to return the nodes on the shortest path of set A and B.
		   // 2. find the common nodes on the sp A and B.
		   BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(this.G,v);
		   BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(this.G,w);
		   boolean[] markedV = new boolean[V];
		   boolean[] markedW = new boolean[V];
		   int[] distToV = new int[V];
		   int[] distToW = new int[V];
		   for (int i = 0; i < V; i++) {
			   if (bfsV.hasPathTo(i)) {
				   markedV[i] = true;
				   distToV[i] = bfsV.distTo(i);
				   }
		   }
		   for (int i = 0; i < V; i++) {
			   if (bfsW.hasPathTo(i)) {
				   markedW[i] = true;
				   distToW[i] = bfsW.distTo(i);
				   }
		   }
		   Bag<Integer> commonN = new Bag<Integer>();
		   for (int i = 0; i < V; i++) {
			   if (markedV[i]&&markedW[i]) commonN.add(i);
		   }
		   if (commonN.isEmpty()) return -1;
		   int minDist = Integer.MAX_VALUE;
		   for (int i : commonN) {
			   int totalD = (distToV[i]+distToW[i]);
			   if ( totalD < minDist) {
				   minDist = totalD;
			   }
		   }
		   // what if there is no common node? --impossible
		   return minDist;
	   }

	   // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
	   public int ancestor(int v, int w) {
		   if (v < 0 || v >= V || w < 0 || w >= V) {
			   throw new java.lang.IllegalArgumentException("Index for length out of range!");
		   }
		   // try to return the distance, but how?
		   // NEED TO DETERMINE WHETHER THERE IS A PATH OR NOT
		   // 1. use BFS to return the nodes on the shortest path of set A and B.
		   // 2. find the common nodes on the sp A and B.
		   BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(this.G,v);
		   BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(this.G,w);
		   boolean[] markedV = new boolean[V];
		   boolean[] markedW = new boolean[V];
		   int[] distToV = new int[V];
		   int[] distToW = new int[V];
		   for (int i = 0; i < V; i++) {
			   if (bfsV.hasPathTo(i)) {
				   markedV[i] = true;
				   distToV[i] = bfsV.distTo(i);
				   }
		   }
		   for (int i = 0; i < V; i++) {
			   if (bfsW.hasPathTo(i)) {
				   markedW[i] = true;
				   distToW[i] = bfsW.distTo(i);
				   }
		   }
		   Bag<Integer> commonN = new Bag<Integer>();
		   for (int i = 0; i < V; i++) {
			   if (markedV[i]&&markedW[i]) commonN.add(i);
		   }
		   if (commonN.isEmpty()) return -1;
		   int minDist = Integer.MAX_VALUE;
		   int minDistN = 0;
		   for (int i : commonN) {
			   int totalD = (distToV[i]+distToW[i]);
			   if ( totalD < minDist) {
				   minDist = totalD;
				   minDistN = i;
			   }
		   }
		   // what if there is no common node? --impossible
		   return minDistN;
	   }

	   // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
	   public int length(Iterable<Integer> v, Iterable<Integer> w) {
		   if (v == null || w == null) throw new java.lang.IllegalArgumentException("v,w are null!");
		   
		   Iterator<Integer> itv = v.iterator();
		   while(itv.hasNext()) {
			   if (itv.next() == null) throw new java.lang.IllegalArgumentException("null element of v.");
		   }
		   Iterator<Integer> itw = w.iterator();
		   while(itw.hasNext()) {
			   if (itw.next() == null) throw new java.lang.IllegalArgumentException("null element of w.");
		   }
		   
		   for (int vv : v) {
			   if (vv < 0 || vv >= V) {
				   throw new java.lang.IllegalArgumentException("Index for length out of range!"); 
			   }
		   }
		   for (int ww : w) {
			   if (ww < 0 || ww >= V) {
				   throw new java.lang.IllegalArgumentException("Index for length out of range!"); 
			   }
		   }
		   
		   // try to return the distance, but how?
		   // NEED TO DETERMINE WHETHER THERE IS A PATH OR NOT
		   // 1. use BFS to return the nodes on the shortest path of set A and B.
		   // 2. find the common nodes on the sp A and B.
		   BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(this.G,v);
		   BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(this.G,w);
		   boolean[] markedV = new boolean[V];
		   boolean[] markedW = new boolean[V];
		   int[] distToV = new int[V];
		   int[] distToW = new int[V];
		   for (int i = 0; i < V; i++) {
			   if (bfsV.hasPathTo(i)) {
				   markedV[i] = true;
				   distToV[i] = bfsV.distTo(i);
				   }
		   }
		   for (int i = 0; i < V; i++) {
			   if (bfsW.hasPathTo(i)) {
				   markedW[i] = true;
				   distToW[i] = bfsW.distTo(i);
				   }
		   }
		   Bag<Integer> commonN = new Bag<Integer>();
		   for (int i = 0; i < V; i++) {
			   if (markedV[i]&&markedW[i]) commonN.add(i);
		   }
		   if (commonN.isEmpty()) return -1;
		   int minDist = Integer.MAX_VALUE;
		   for (int i : commonN) {
			   int totalD = (distToV[i]+distToW[i]);
			   if ( totalD < minDist) {
				   minDist = totalD;
			   }
		   }
		   // what if there is no common node? --impossible
		   return minDist;
	   }

	   // a common ancestor that participates in shortest ancestral path; -1 if no such path
	   public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
		   if (v == null || w == null) throw new java.lang.IllegalArgumentException("v,w are null");
		   
		   Iterator<Integer> itv = v.iterator();
		   while(itv.hasNext()) {
			   if (itv.next() == null) throw new java.lang.IllegalArgumentException("null element of v.");
		   }
		   // after running one .next() method, the pointer is automatically point to next element.
		   Iterator<Integer> itw = w.iterator();
		   while(itw.hasNext()) {
			   if (itw.next() == null) throw new java.lang.IllegalArgumentException("null element of w.");
		   }
		   
		   for (int vv : v) {
			   if (vv < 0 || vv >= V) {
				   throw new java.lang.IllegalArgumentException("Index for length out of range!"); 
			   }
		   }
		   for (int ww : w) {
			   if (ww < 0 || ww >= V) {
				   throw new java.lang.IllegalArgumentException("Index for length out of range!"); 
			   }
		   }
		   
		   // try to return the distance, but how?
		   // NEED TO DETERMINE WHETHER THERE IS A PATH OR NOT
		   // 1. use BFS to return the nodes on the shortest path of set A and B.
		   // 2. find the common nodes on the sp A and B.
		   BreadthFirstDirectedPaths bfsV = new BreadthFirstDirectedPaths(this.G,v);
		   BreadthFirstDirectedPaths bfsW = new BreadthFirstDirectedPaths(this.G,w);
		   boolean[] markedV = new boolean[V];
		   boolean[] markedW = new boolean[V];
		   int[] distToV = new int[V];
		   int[] distToW = new int[V];
		   for (int i = 0; i < V; i++) {
			   if (bfsV.hasPathTo(i)) {
				   markedV[i] = true;
				   distToV[i] = bfsV.distTo(i);
				   }
		   }
		   for (int i = 0; i < V; i++) {
			   if (bfsW.hasPathTo(i)) {
				   markedW[i] = true;
				   distToW[i] = bfsW.distTo(i);
				   }
		   }
		   Bag<Integer> commonN = new Bag<Integer>();
		   for (int i = 0; i < V; i++) {
			   if (markedV[i]&&markedW[i]) commonN.add(i);
		   }
		   if (commonN.isEmpty()) return -1;
		   int minDist = Integer.MAX_VALUE;
		   int minDistN = 0;
		   for (int i : commonN) {
			   int totalD = (distToV[i]+distToW[i]);
			   if ( totalD < minDist) {
				   minDist = totalD;
				   minDistN = i;
			   }
		   }
		   // what if there is no common node? --impossible
		   return minDistN;
	   }

	   // do unit testing of this class
	   public static void main(String[] args) {
		    In in = new In(args[0]);
		    Digraph G = new Digraph(in);
		    SAP sap = new SAP(G);
		    while (!StdIn.isEmpty()) {
		        int v = StdIn.readInt();
		        int w = StdIn.readInt();
		        int length   = sap.length(v, w);
		        int ancestor = sap.ancestor(v, w);
		        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
		    }
	}
}

