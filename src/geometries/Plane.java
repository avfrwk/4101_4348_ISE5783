package geometries;
import primitives.Point;
import primitives.Vector;
public class Plane implements Geometry {
    final private Vector normal;
    final private Point p0;
    public Plane(Point p,Vector v){
        this.p0=p;
        this.normal=v.normalize();
    }
    public Plane(Point p1,Point p2,Point p3){
        this.p0=p1;
        this.normal=null;
    }
    public Vector getNormal() {
        return normal;
    }
    public Point getP0() {
        return p0;
    }
    public Vector getNormal(Point point){
        return normal;
    }
}