import edu.princeton.cs.algs4.Point2D;	
// import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Bag;


public class KdTree {
   private int size;
   private Node root;
   private boolean flag; // the simplified version of mod operation
   
   public KdTree() {                              // construct an empty set of points 
	   this.size = 0;
	   flag = false;
   }
   
   private static class Node {
	   private Point2D p;      // the point
	   private RectHV rect;    // the axis-aligned rectangle corresponding to this node
	   private Node lb;        // the left/bottom subtree
	   private Node rt;        // the right/top subtree
	   public Node(double x, double y, double xmin, double ymin, double xmax, double ymax) {
		   p = new Point2D(x,y);
		   this.rect = new RectHV(xmin,ymin,xmax,ymax);
	   }
	}
   
   public boolean isEmpty() {                     // is the set empty? 
	   return size() == 0;
   }
   public int size()  {                       // number of points in the set 
	   return this.size;
   }

   public void insert(Point2D p)  {            // add the point to the set (if it is not already in the set)
	   if (p == null) throw new java.lang.IllegalArgumentException();
	   root = insert(root, p, null, 0, false);
   }
   private Node insert(Node x, Point2D p, Node xPrev, int orientation, boolean split)  {            // add the point to the set (if it is not already in the set)
	   if (x == null) {
		   if (xPrev == null) {
			   this.size ++;
			   return new Node(p.x(),p.y(), 0, 0, 1, 1);
		   }
		   else {
			   this.size ++;
			   if (split) { 
				   if (orientation == -1) return new Node(p.x(),p.y(),xPrev.rect.xmin(), xPrev.rect.ymin(), xPrev.p.x(), xPrev.rect.ymax()); // the left subtree
				   else return new Node(p.x(),p.y(),xPrev.p.x(), xPrev.rect.ymin(), xPrev.rect.xmax(), xPrev.rect.ymax()); // the right subtree
			   }
			   else { // left and right
				   if (orientation == -1) return new Node(p.x(),p.y(),xPrev.rect.xmin(), xPrev.rect.ymin(), xPrev.rect.xmax(), xPrev.p.y()); // the left subtree
				   else return new Node(p.x(),p.y(),xPrev.rect.xmin(), xPrev.p.y(), xPrev.rect.xmax(), xPrev.rect.ymax()); // the right subtree
			   }
			   }
			   // what if flag == false
		   }

	   int cmp = 0;
	   
	   // IF split == TRUE, then split top/bottom
	   // IF split == FALSE, then split left/right
	   if (!split) {
		   if (p.x() < x.p.x()) cmp = -1;
		   else if (p.x() == x.p.x()) {
			   if (p.y() == x.p.y()) cmp = 0; // if they are the same , cmp == 0
			   else cmp = 1; // if their x coordinate is equal, then go right subtree.
		   }
		   else cmp = 1;
	   }
	   else {
		   if (p.y() < x.p.y()) cmp = -1; 
		   else if (p.y() == x.p.y()) {
			   if (p.x() == x.p.x()) cmp = 0;
			   else cmp = 1;
		   }
		   else cmp = 1;
	   }
	   if (cmp < 0) x.lb = insert(x.lb, p, x, -1, !split );
	   else if (cmp > 0) x.rt = insert(x.rt, p, x, 1, !split);
	   return x;
   }
   public boolean contains(Point2D p)  {          // does the set contain point p? 
	   if(p == null) throw new IllegalArgumentException("argument to contains() is null");
	   return get(p) != null;
   }
   
   private Node get (Point2D p) {
	   this.flag = false;
	   return get(root,p);
   }
   
   private Node get (Node x, Point2D p) {
	   if (p == null) throw new IllegalArgumentException("calls get() with a null key");
	   if (x == null) return null;
	   flag = !flag;
	   int cmp = 0;
	   if (flag) {
		   if (p.x() < x.p.x()) cmp = -1;
		   else if (p.x() == x.p.x()) {
			   if (p.y() == x.p.y()) cmp = 0; // if they are the same , cmp == 0
			   else cmp = 1; // if their x coordinate is equal, then go right subtree.
		   }
		   else cmp = 1;
	   }
	   else {
		   if (p.y() < x.p.y()) cmp = -1; 
		   else if (p.y() == x.p.y()) {
			   if (p.x() == x.p.x()) cmp = 0;
			   else cmp = 1;
		   }
		   else cmp = 1;
	   }
	   if (cmp < 0) return get(x.lb, p);
	   else if (cmp > 0) return get(x.rt, p);
	   else return x;
   }
   
   
   
   public void draw()  {                       // draw all points to standard draw 
	   Queue<Node> qNode = new Queue<Node>();
	   Queue<Boolean> qFlag = new Queue<Boolean>();
	   flag = false;
	   enqueue(qNode, qFlag, root, flag); //recursively obtain the nodes
	   int qSize = qNode.size();
	   for(int i = 0; i < qSize; i++) {
		   Node x = qNode.dequeue();
		   boolean f = qFlag.dequeue();
		   if (f) {
			   // draw the point
			   StdDraw.setPenColor(StdDraw.BLACK);
			   StdDraw.setPenRadius(0.01);
			   StdDraw.point(x.p.x(), x.p.y());
			   // draw the line
			   StdDraw.setPenColor(StdDraw.BLUE);
			   StdDraw.setPenRadius();
			   StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
		   }
		   else {
			   // draw the point
			   StdDraw.setPenColor(StdDraw.BLACK);
			   StdDraw.setPenRadius(0.01);
			   StdDraw.point(x.p.x(), x.p.y());
			   // draw the line
			   StdDraw.setPenColor(StdDraw.RED);
			   StdDraw.setPenRadius();
			   StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
		   }
	   }
   }
   private void enqueue(Queue<Node> q1, Queue<Boolean> q2, Node x, boolean flag) {
	   if (x != null) {
		   q1.enqueue(x);
		   q2.enqueue(flag);
		   if (x.lb != null) enqueue(q1, q2, x.lb, !flag);
		   if (x.rt != null) enqueue(q1, q2, x.rt, !flag);
	   }
   }
   
   private void check(Bag<Point2D> b, RectHV rect, Node x, boolean flag) {
	   if (x != null) {
		   if (flag) {
			   if ((x.p.y()>=rect.ymin())&&(x.p.y()<=rect.ymax())) {
				   if ((x.p.x()>=rect.xmin())&&(x.p.x()<=rect.xmax())) {
					   b.add(x.p);
					   check(b, rect, x.lb, !flag);
					   check(b, rect, x.rt, !flag);
				   }
				   else {
					   check(b, rect, x.lb, !flag);
					   check(b, rect, x.rt, !flag);
				   }
			   }
			   else if (x.p.y() < rect.ymin()) {
				   check (b, rect, x.rt, !flag);
			   }
			   else {
				   check (b, rect, x.lb, !flag);
			   }
		   }
		   else { // if it's a vertical line
			   if ((x.p.x()>=rect.xmin())&&(x.p.x()<=rect.xmax())) {
				   if ((x.p.y()>=rect.ymin())&&(x.p.y()<=rect.ymax())) {
					   b.add(x.p);
					   check(b, rect, x.lb, !flag);
					   check(b, rect, x.rt, !flag);
				   }
				   else {
					   check(b, rect, x.lb, !flag);
					   check(b, rect, x.rt, !flag);
				   }
			   }
			   else if (x.p.x() < rect.xmin()) {
				   check (b, rect, x.rt, !flag);
			   }
			   else {
				   check (b, rect, x.lb, !flag);
			   }
			   }
		   }
	   }
	   
   
   public Iterable<Point2D> range(RectHV rect)  {           // all points that are inside the rectangle (or on the boundary) 
	   if (rect == null) throw new java.lang.IllegalArgumentException();
	   Bag<Point2D> b = new Bag<Point2D>();
	   check(b, rect, root, false);
	   return b;
   }
   public Point2D nearest(Point2D p)  {           // a nearest neighbor in the set to point p; null if the set is empty 
	   if (p == null) throw new java.lang.IllegalArgumentException();
	   // first version
	   Point2D[] np = new Point2D[1];
	   double minD = findNearest(root, p, np, false, Double.POSITIVE_INFINITY);
	   return np[0];
   }
   
   private double findNearest(Node x, Point2D p, Point2D[] np, boolean flag, double minD) {
	   if (x != null) {
		   if (distance(x.p, p) < minD) {
			   np[0] = x.p;
			   minD = distance(x.p, p);
		   }
		   if (flag) {
			   if (p.y() < x.p.y()) {
				   if (x.lb != null) {
					   if (minD > x.lb.rect.distanceSquaredTo(p)) minD = findNearest(x.lb, p, np, !flag, minD);
				   }
				   
				   if (x.rt != null) {
					   if (minD > x.rt.rect.distanceSquaredTo(p)) minD = findNearest(x.rt, p, np, !flag, minD);
				   }
			   }
			   else {
				   if (x.rt != null) {
					   if (minD > x.rt.rect.distanceSquaredTo(p)) minD = findNearest(x.rt, p, np, !flag, minD);
				   }
				   if (x.lb != null) {
					   if (minD > x.lb.rect.distanceSquaredTo(p)) minD = findNearest(x.lb, p, np, !flag, minD);
				   }
			     }
		   }
		   else {
			   if (p.x() < x.p.x()) {
				   if (x.lb != null) {
					   if (minD > x.lb.rect.distanceSquaredTo(p)) minD = findNearest(x.lb, p, np, !flag, minD);
				   }
				   
				   if (x.rt != null) {
					   if (minD > x.rt.rect.distanceSquaredTo(p)) minD = findNearest(x.rt, p, np, !flag, minD);
				   }
			   }
			   else {
				   if (x.rt != null) {
					   if (minD > x.rt.rect.distanceSquaredTo(p)) minD = findNearest(x.rt, p, np, !flag, minD);
				   }
				   if (x.lb != null) {
					   if (minD > x.lb.rect.distanceSquaredTo(p)) minD = findNearest(x.lb, p, np, !flag, minD);
				   }
			     }  
		   }

		   
	   }
	   return minD;

   }
   
   
   private double distance(Point2D ti, Point2D ta) {
	   return (ti.x()-ta.x())*(ti.x()-ta.x()) + (ti.y()-ta.y())*(ti.y()-ta.y());
   }

   public static void main(String[] args)  {                // unit testing of the methods (optional) 
	   KdTree kd = new KdTree();
	   Point2D p1 = new Point2D(0.5,0.2);
	   Point2D p2 = new Point2D(0.4,0.3);
	   Point2D p3 = new Point2D(0.9,0.9);
	   Point2D p4 = new Point2D(0.4,0.1);
	   Point2D p5 = new Point2D(0.7,0.4);	  
	   Point2D p6 = new Point2D(0.5,0.6);
	   Point2D p7 = new Point2D(0.6,0.8);
	   kd.insert(p1);
	   kd.insert(p2);
	   kd.insert(p3);
	   kd.insert(p4);
	   kd.insert(p5);
	   kd.insert(p6);
	   kd.insert(p7);
//	   if (kd.contains(p5)) StdOut.print("True");
//	   else StdOut.print("False");
//	   if (kd.contains(p6)) StdOut.print("True");
//	   else StdOut.print("False");
	   kd.draw();
	   
	   
   }
   


}