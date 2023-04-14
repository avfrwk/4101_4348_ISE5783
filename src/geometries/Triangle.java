package geometries;
import primitives.Point;

/** This class represent a triangle*/
public class Triangle extends Polygon{
    /** Constructor to initialize Triangle based on three points(vertices)
     * @param p1 first point
     * @param p2 second point
     * @param p3 third point */
    public Triangle(Point p1,Point p2,Point p3) {
        super(p1, p2, p3);
    }
}