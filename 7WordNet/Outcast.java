package wordNet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
	   private WordNet w;
	   public Outcast(WordNet wordnet) {        // constructor takes a WordNet object
		   w = wordnet;
	   }
	   
	   public String outcast(String[] nouns) {  // given an array of WordNet nouns, return an outcast
		   int numN = nouns.length;
		   int[] d = new int[numN];
		   for(int i = 0; i < numN; i++) {
			   for (int j = 0; j < numN; j++) {
				   d[i] = d[i] + w.distance(nouns[i], nouns[j]);
			   }
		   }
		   int maxID = -1;
		   int maxD = Integer.MIN_VALUE;
		   for (int i = 0; i < numN; i++) {
			   if (d[i] > maxD) {
				   maxD = d[i];
				   maxID = i;
			   }
		   }
		   return nouns[maxID];
	   }
	   
	   public static void main(String[] args) {
		    WordNet wordnet = new WordNet(args[0], args[1]);
		    Outcast outcast = new Outcast(wordnet);
		    for (int t = 2; t < args.length; t++) {
		        In in = new In(args[t]);
		        String[] nouns = in.readAllStrings();
		        StdOut.println(args[t] + ": " + outcast.outcast(nouns));
		    }
		}
	}