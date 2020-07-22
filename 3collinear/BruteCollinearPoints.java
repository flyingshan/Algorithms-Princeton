import edu.princeton.cs.algs4.ResizingArrayStack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MergeX;

public class BruteCollinearPoints {

   private Point[] points;
   
   public BruteCollinearPoints(Point[] points) {// finds all line segments containing 4 points
	   if (points == null) throw new java.lang.IllegalArgumentException();
	   for(int i=0; i<points.length;i++) {
		   if (points[i] == null) throw new java.lang.IllegalArgumentException();			   
	   }
	   for(int i=0; i<points.length-1; i++) {
		   for(int j=i+1; j<points.length; j++) {
			   if (points[i].slopeTo(points[j])==Double.NEGATIVE_INFINITY) throw new java.lang.IllegalArgumentException();		   
		   }
	   }
	   this.points = new Point[points.length];
	   for (int i=0; i<points.length; i++) this.points[i] = points[i];
	   MergeX.sort(this.points);
   }
   public           int numberOfSegments()        // the number of line segments
   {
	   return segments().length;
   }
   public LineSegment[] segments()                // the line segments
   {
	   ResizingArrayStack<LineSegment> lsStack = new ResizingArrayStack<LineSegment>();
	   for(int i=0; i<points.length-3; i++) {
		   for(int j=i+1; j<points.length-2; j++) {
			   for(int m=j+1; m<points.length-1; m++) {
				   for(int n=m+1; n<points.length; n++) {
					   if(points[i].slopeTo(points[j]) == points[i].slopeTo(points[m])&&(points[i].slopeTo(points[j]) == points[i].slopeTo(points[n]))) {
						   lsStack.push(new LineSegment(points[i],points[n]));
					   }			     
				   }
			   }
		   }
	   }
	   LineSegment[] ls = new LineSegment[lsStack.size()];
	   int p = 0;
	   for(LineSegment i:lsStack) {
		   ls[p] = i;
		   p++;
	   }
	   return ls;
   }
	public static void main(String[] args) {

	    // read the n points from a file
	    In in = new In(args[0]);
	    int n = in.readInt();
	    Point[] points = new Point[n];
	    for (int i = 0; i < n; i++) {
	        int x = in.readInt();
	        int y = in.readInt();
	        points[i] = new Point(x, y);
	    }

	    // print and draw the line segments
	    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	    }
	    StdOut.println(collinear.numberOfSegments());
	}
}