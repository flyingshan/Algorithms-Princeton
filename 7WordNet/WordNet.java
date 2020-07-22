package wordNet;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
//import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.ResizingArrayQueue;
import java.util.Iterator;
//import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Topological;

public class WordNet {
       private Bag<String>[] nodeNouns;
       private SET<String> wordNouns;
       private Digraph G;
       private int vNum;
       private SAP s;
	   // constructor takes the name of the two input files
	   public WordNet(String synsets, String hypernyms) {
		   if (synsets == null || hypernyms == null) throw new java.lang.IllegalArgumentException("The arguments of the constructor is null!");
		   In synsetIn = new In(synsets);
		   In hypernymIn = new In(hypernyms);
		   // build the wordnoun for checking new noun words, and the nodenouns for finding the id of an noun
		   vNum = 0;
		   wordNouns = new SET<String>();
		   ResizingArrayQueue<Bag<String>> nodeNounsQueue = new ResizingArrayQueue<Bag<String>>();
		   while(!synsetIn.isEmpty()) {
			   String lineString = synsetIn.readLine();
			   String synWord = lineString.split(",")[1];
			   String[] synWords = synWord.split(" ");
			   Bag<String> nodeWords = new Bag<String>(); // to store a bag array when not knowing the exact size of the array
			   for (int i = 0; i < synWords.length; i++) {
//				   StdOut.println(synWords[i]);
//				   StdOut.println(this.wordNouns.contains("1"));
				   wordNouns.add(synWords[i]);
				   nodeWords.add(synWords[i]);
			   }
			   nodeNounsQueue.enqueue(nodeWords);
			   vNum ++;
		   }
		   nodeNouns = (Bag<String>[]) new Bag[vNum];
		   for (int i = 0; i < vNum; i++) nodeNouns[i] = nodeNounsQueue.dequeue();
		   // build the adjID bag array
		   Bag<Integer>[] adjID = (Bag<Integer>[]) new Bag[vNum];
//		   Bag<Integer> tt = new Bag<Integer>();
//		   StdOut.println(tt);
		   for (int j = 0; j < vNum; j++) {
			   adjID[j] = new Bag<Integer>();
		   }

		   while(!hypernymIn.isEmpty()) {
			   String lineString = hypernymIn.readLine();
			   String[] hyperWordString = lineString.split(",");
			   int id = Integer.parseInt(hyperWordString[0]);
			   for (int i = 1; i < hyperWordString.length; i++) {
				   int adj = Integer.parseInt(hyperWordString[i]);
				   adjID[id].add(adj);;
			   }
		   }
		   // build the digraph model
		   G = new Digraph(vNum);
		   for (int i = 0; i < vNum; i++) {
			   for(int j:adjID[i]) {
				   G.addEdge(i, j);
			   }
		   }
		   Topological t = new Topological(G);
		   if (!t.hasOrder()) throw new java.lang.IllegalArgumentException("G isn't a DAG!");
		   
		   s = new SAP(G);
	   }

	   // returns all WordNet nouns
	   public Iterable<String> nouns() {
		   return wordNouns;
	   }

	   // is the word a WordNet noun?
	   public boolean isNoun(String word) {
		   return wordNouns.contains(word);
	   }

	   // distance between nounA and nounB (defined below)
	   public int distance(String nounA, String nounB) {
		   if ((!this.isNoun(nounA)) || (!this.isNoun(nounB))) {
			   throw new java.lang.IllegalArgumentException("The arguments of distance is not a word of wordnet!");
		   }
		   Bag<Integer> idA = new Bag<Integer>();
		   Bag<Integer> idB = new Bag<Integer>();
		   // search for the synset(the node number) of nounA
		   for (int i = 0; i < vNum; i++) {
			   for (String j:nodeNouns[i]) {
				   if (nounA.equals(j)) {
					   idA.add(i);
					   break;
				   }
			   }
		   }
		   // search for the synset(the node number) of nounB
		   for (int i = 0; i < vNum; i++) {
			   for (String j:nodeNouns[i]) {
				   if (nounB.equals(j)) {
					   idB.add(i);
					   break;
				   }
			   }
		   }
		   int minDist = s.length(idA, idB);
		   return minDist;
	   }

	   // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
	   // in a shortest ancestral path (defined below)
	   public String sap(String nounA, String nounB) {
		   if ((!this.isNoun(nounA)) || (!this.isNoun(nounB))) {
			   throw new java.lang.IllegalArgumentException("The arguments of sap is not a word of wordnet!");
		   }
		   Bag<Integer> idA = new Bag<Integer>();
		   Bag<Integer> idB = new Bag<Integer>();
		   // search for the IDs(the node number) of nounA
		   for (int i = 0; i < vNum; i++) {
			   for (String j:nodeNouns[i]) {
				   if (nounA.equals(j)) {
					   idA.add(i);
					   break;
				   }
			   }
		   }
		   // search for the IDs(the node number) of nounB
		   for (int i = 0; i < vNum; i++) {
			   for (String j:nodeNouns[i]) {
				   if (nounB.equals(j)) {
					   idB.add(i);
					   break;
				   }
			   }
		   }
		   // try to return the path
		   int minDistN = s.ancestor(idA, idB);
//		   StdOut.println(minDistN);
		   String sapString = "";
//		   for (String i : nodeNouns[minDistN]) {
////			   StdOut.println(i);
//			   if (nodeNouns[minDistN].iterator().hasNext()) {  //this iterator object is not the exact iterator of the above for-each loop.
//				   sapString = " " + i + sapString; 
//			   }
//			   else {
//				   sapString = i + sapString;
//			   }
//		   }
		   int sizeN = nodeNouns[minDistN].size();
		   Iterator<String> itN = nodeNouns[minDistN].iterator();
		   for (int i = 0; i < sizeN; i++) {
			   if (i < sizeN - 1) sapString = " " + itN.next() + sapString; 
			   else sapString = itN.next() + sapString;
		   }
		   
		   return sapString;
	   }

	   // do unit testing of this class
	   public static void main(String[] args) {
		   WordNet w = new WordNet(args[0],args[1]);
		   StdOut.println(w.distance("worm", "bird"));
//		   StdOut.println(w.isNoun("CD"));
		   StdOut.println(w.sap("worm", "bird"));
	   }
	}