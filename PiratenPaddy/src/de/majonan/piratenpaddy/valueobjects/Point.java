package de.majonan.piratenpaddy.valueobjects;

import java.util.List;

public class Point {

	public static final double EPSILON = 0.00001d;
	
	public int x;
	public int y;
	
	public Point(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public boolean isBetween(Point a, Point b){
		Point c = this;
		return (c.x <= Math.max(a.x, b.x) && c.x >= Math.min(a.x, b.x) && c.y <= Math.max(a.y, b.y) && c.y >= Math.min(a.y, b.y));
	}
	

	 
	// To find orientation of ordered triplet (p, q, r).
	// The function returns following values
	// 0 --> p, q and r are colinear
	// 1 --> Clockwise
	// 2 --> Counterclockwise
	public static int orientation(Point p, Point q, Point r)
	{
	    // See 10th slides from following link for derivation of the formula
	    // http://www.dcs.gla.ac.uk/~pat/52233/slides/Geometry1x1.pdf
	    int val = (q.y - p.y) * (r.x - q.x) -
	              (q.x - p.x) * (r.y - q.y);
	 
	    if (val == 0) return 0;  // colinear
	 
	    return (val > 0)? 1: 2; // clock or counterclock wise
	}
	 
	// The main function that returns true if line segment 'p1q1'
	// and 'p2q2' intersect.
	public static boolean doIntersect(Point p1, Point p2, Point q1, Point q2)
	{
	    // Find the four orientations needed for general and
	    // special cases
	    int o1 = orientation(p1, q1, p2);
	    int o2 = orientation(p1, q1, q2);
	    int o3 = orientation(p2, q2, p1);
	    int o4 = orientation(p2, q2, q1);
	 
	    // General case
	    if (o1 != o2 && o3 != o4)
	        return true;
	 
	    // Special Cases
	    // p1, q1 and p2 are colinear and p2 lies on segment p1q1
	    if (o1 == 0 && p2.isBetween(p1, q1)) return true;
	 
	    // p1, q1 and p2 are colinear and q2 lies on segment p1q1
	    if (o2 == 0 && q2.isBetween(p1, q1)) return true;
	 
	    // p2, q2 and p1 are colinear and p1 lies on segment p2q2
	    if (o3 == 0 && p1.isBetween(p2, q2)) return true;
	 
	     // p2, q2 and q1 are colinear and q1 lies on segment p2q2
	    if (o4 == 0 && q1.isBetween(p2, q2)) return true;
	 
	    return false; // Doesn't fall in any of the above cases
	}
	
	public double distanceTo(Point p){
		return Math.sqrt(Math.pow(p.x-this.x, 2) + Math.pow(p.y-this.y, 2));
	}
	
	public boolean isInPolygon(List<Point> polygon) {
		  Point point = this;
		  Point[] points = new Point[polygon.size()];
		  polygon.toArray(points);
		  int i, j, nvert = points.length;
		  boolean c = false;

		  for(i = 0, j = nvert - 1; i < nvert; j = i++) {
		    if( ( (points[i].y >= point.y ) != (points[j].y >= point.y) ) && (point.x <= (points[j].x - points[i].x) * (point.y - points[i].y) / (points[j].y - points[i].y) + points[i].x))
		      c = !c;
		  }

		  return c;
		}
	
	public String toString(){
		return "["+x+","+y+"]";
	}
}
