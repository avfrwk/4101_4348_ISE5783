package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/** This class represent a triangle*/
public class Triangle extends Polygon{
    /** Constructor to initialize Triangle based on three points(vertices)
     * @param p1 first point
     * @param p2 second point
     * @param p3 third point */
    public Triangle(Point p1,Point p2,Point p3) {
        super(p1, p2, p3);
    }
    /** get list of intersection between ray and Triangle
     * @param ray the ray
     * @return list of intersections
     * */
    @Override
    public List<GeoPoint>findGeoIntersectionsHelper(Ray ray) {
        List<Point> planeInsect=this.plane.findIntersections(ray);
        if(planeInsect!=null){
            Point p0=planeInsect.get(0);
            Vector v0=this.vertices.get(1).subtract(this.vertices.get(0));
            Vector v1=this.vertices.get(2).subtract(this.vertices.get(0));
            Vector v2= p0.subtract(this.vertices.get(0));
            double d00 = v0.dotProduct(v0);
            double d01 = v0.dotProduct(v1);
            double d11 = v1.dotProduct(v1);
            double d20 = v2.dotProduct(v0);
            double d21 = v2.dotProduct(v1);
            double invDenom = 1 / (d00 * d11 - d01 * d01);
            double u = (d11 * d20 - d01 * d21) * invDenom;
            double v = (d00 * d21 - d01 * d20) * invDenom;
            double w = 1 - u - v;
            if (u > 0 && v > 0 && w > 0) {
                return List.of(new GeoPoint(this,p0));
            }
        }
        return null;
    }
}