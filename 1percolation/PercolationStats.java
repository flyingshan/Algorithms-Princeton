import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
public class PercolationStats {
   private final int N;
   private final int T;
   private final double[] prob;
   public PercolationStats(int n, int trials)    // perform trials independent experiments on an n-by-n grid
   {
	  if (n <= 0||trials <= 0)
		  throw new java.lang.IllegalArgumentException();
      N = n;
      T = trials;
      prob = new double[T];
      //do T experiments
      for(int i=0;i<T;i++)
      {
         Percolation p = new Percolation(N);
         while(!p.percolates())// while not percolated randomly choose one place to open
         {   
            int row_t = StdRandom.uniform(N)+1;
            int col_t = StdRandom.uniform(N)+1;
            p.open(row_t,col_t);
         }
         double prob_t = ((double)p.numberOfOpenSites())/(N*N);
         prob[i] = prob_t;
      }
   }
   public double mean()                          // sample mean of percolation threshold
   {return StdStats.mean(prob);}
   public double stddev()                        // sample standard deviation of percolation threshold
   {return StdStats.stddev(prob);}
   public double confidenceLo()                  // low  endpoint of 95% confidence interval
   {return (StdStats.mean(prob)-1.96*StdStats.stddev(prob)/Math.sqrt(T));}
   public double confidenceHi()                  // high endpoint of 95% confidence interval
   {return (StdStats.mean(prob)+1.96*StdStats.stddev(prob)/Math.sqrt(T));}
   public static void main(String[] args)        // test client (described below)
   {
      int N = Integer.parseInt(args[0]);
      int T = Integer.parseInt(args[1]);
      PercolationStats ps = new PercolationStats(N,T);
      StdOut.println("mean = "+ps.mean());
      StdOut.println("stddev = "+ps.stddev());
      StdOut.println("95% confidence interval = ["+ps.confidenceLo()+","+ps.confidenceHi()+"]");
   }
}