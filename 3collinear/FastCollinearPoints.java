import edu.princeton.cs.algs4.LinkedStack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MergeX;
public class FastCollinearPoints {

   private Point[] points;
   
   public FastCollinearPoints(Point[] points) {// finds all line segments containing 4 points
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

	   LinkedStack<LineSegment> tempSegmentStack = new LinkedStack<LineSegment>();

	   for(int i=0; i<points.length; i++) { 		   
		   Point po = points[i]; 

		   Point[] newPoints = new Point[points.length-1];
		   if(i == 0) {
			   for (int j=1; j<points.length; j++) newPoints[j-1] = points[j];
		   }
		   else if (i == points.length-1) {
			   for (int j=0; j<points.length-1; j++) newPoints[j] = points[j];
		   }
		   else {
			   for(int j=0; j<i; j++) newPoints[j] = points[j];
			   for(int j=i+1; j<points.length; j++) newPoints[j-1] = points[j];
		   }

		   MergeX.sort(newPoints, po.slopeOrder());

		   double lastSlope = points[i].slopeTo(points[i]);

		   int ns = 0;
		   for(int m=0; m<newPoints.length; m++) {

			   if(newPoints[m].slopeTo(po) == lastSlope) {
				   ns++;
				   if (ns >= 2 && m == newPoints.length-1) {
					   Point[] tempPoints = new Point[ns+2];
					   for(int t=ns+1; t>0; t--)  tempPoints[t-1]=newPoints[m-t+1];

					   tempPoints[ns+1] = po;
					   MergeX.sort(tempPoints);
					   LineSegment tempSegment = new LineSegment(tempPoints[0],tempPoints[ns+1]);
					   tempSegmentStack.push(tempSegment);	 
				   }
			   }
			   else {
				   lastSlope = newPoints[m].slopeTo(points[i]);
				   if(ns >= 2) {
					   Point[] tempPoints = new Point[ns+2];

					   for(int t=ns+1; t>0; t--)  tempPoints[t-1]=newPoints[m-t];

					   tempPoints[ns+1] = po;
					   MergeX.sort(tempPoints);
					   LineSegment tempSegment = new LineSegment(tempPoints[0],tempPoints[ns+1]);
					   tempSegmentStack.push(tempSegment);		
					   //StdOut.println(tempSegment);
				   }

				   ns = 0;			   
			   }
		   }
	   }

	   LineSegment[] midSegment = new LineSegment[tempSegmentStack.size()];
	   int ssize = tempSegmentStack.size();
	   for(int i=0; i<ssize; i++) {
		   midSegment[i] = tempSegmentStack.pop();
		   //StdOut.println(midSegment[i]);
	   }
	   //temp stack size == 0

	   for(int i=0; i<midSegment.length; i++) {
		   int flag = 0;
		   if (i == 0) tempSegmentStack.push(midSegment[i]);
		   for (LineSegment t : tempSegmentStack) {
			   if(t.toString().equals(midSegment[i].toString())) {
				   flag = 1;
				   
				   break;
			   }
		   }
		   if (flag == 0) {
			   tempSegmentStack.push(midSegment[i]);
			   //StdOut.println(midSegment[i]);
		   }
	   }
	   ssize = tempSegmentStack.size();
	   LineSegment[] finalSegment = new LineSegment[ssize];
	   for(int i=0; i<ssize; i++) finalSegment[i] = tempSegmentStack.pop();

	   return finalSegment;
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
	    FastCollinearPoints collinear = new FastCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	    }
	    StdOut.println(collinear.numberOfSegments());
	}
   }