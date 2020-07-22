import edu.princeton.cs.algs4.SET;	
import edu.princeton.cs.algs4.Point2D;	
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.RectHV;

public class PointSET {
	private SET<Point2D> pointSet;
   public         PointSET() {                              // construct an empty set of points
	   pointSet = new SET<Point2D>();
   }
   public boolean isEmpty() {                     // is the set empty? 
	   return pointSet.isEmpty();
   }
   public int size()   {                      // number of points in the set 
	   return pointSet.size();
   }
   public void insert(Point2D p)    {          // add the point to the set (if it is not already in the set)
	   if (p == null) throw new java.lang.IllegalArgumentException();
	   pointSet.add(p);
   }
   public boolean contains(Point2D p)  {          // does the set contain point p?
	   if (p == null) throw new java.lang.IllegalArgumentException();
	   return pointSet.contains(p);
   }
   public void draw()     {             // draw all points to standard draw 
	   for(Point2D i:pointSet) {
		   i.draw();
	   }
   }
   public Iterable<Point2D> range(RectHV rect)   {          // all points that are inside the rectangle (or on the boundary)
	   if (rect == null) throw new java.lang.IllegalArgumentException();
	   Bag<Point2D> pointBag = new Bag<Point2D>();
	   for(Point2D i:pointSet) {
		   if(rect.contains(i)) {
			   pointBag.add(i);
		   }
	   }
	   return pointBag;
	   
   }
   public Point2D nearest(Point2D p)    {         // a nearest neighbor in the set to point p; null if the set is empty
	   if (p == null) throw new java.lang.IllegalArgumentException();
	   double minD = Double.POSITIVE_INFINITY;
	   double D = 0.0;
	   Point2D nearestP = null;
	   for(Point2D i:pointSet) {
		   D = i.distanceTo(p);
		   if (D < minD) {
			   minD = D;
			   nearestP = i;
		   }
	   }
	   return nearestP;
   }

   public static void main(String[] args)      {            // unit testing of the methods (optional) 
	   
   }
}