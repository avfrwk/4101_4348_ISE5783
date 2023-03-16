package geometries;
import primitives.Point;
import primitives.Vector;

import java.util.Objects;

/** This class represent a plane*/
public class Plane implements Geometry {
    final private Vector normal;
    final private Point p0;
    /** Constructor to initialize Plane based on the normal to the plane and point on the plane
     * @param p point on the plane
     * @param v the normal to the plane*/
    public Plane(Point p,Vector v){
        this.p0=p;
        this.normal=v.normalize();
    }
    /** Constructor to initialize Plane based on three points
     * @param p1 first point on the plane
     * @param p2 second point on the plane
     * @param p3 third point on the plane
     * */
    public Plane(Point p1,Point p2,Point p3){
        this.p0=p1;
        this.normal=null;
    }
    /** get the normal
     * @return normal to the plane*/
    public Vector getNormal() {
        return normal;
    }
    /** get point from the plane
     * @return point on the plane*/
    public Point getP0() {
        return p0;
    }
    /** get the normal to the plane at specific point
     *  @param point the point of normal's head
     * @return normal to the plane at specific point*/
    public Vector getNormal(Point point){
        return normal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plane plane = (Plane) o;
        return Objects.equals(normal, plane.normal) && Objects.equals(p0, plane.p0);
    }
    @Override
    public int hashCode() {
        return Objects.hash(normal, p0);
    }
}