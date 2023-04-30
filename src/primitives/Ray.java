package primitives;

import java.util.List;

/** This class represents a ray*/
public class Ray {
    final private Point p0;
    final private Vector dir;
    /** Constructor to initialize Ray base on start point and direction
     * @param p the start point
     * @param v the direction*/
    public Ray(Point p, Vector v) {
        this.p0 = p;
        this.dir = v.normalize();
    }
    public Point getPoint(double t){
        return p0.add(dir.scale(t));
    }
    /** get the start point
     * @return  the start point of the ray*/
    public Point getP0() {
        return p0;
    }
    /** get the direction
     * @return the direction of the ray*/
    public Vector getDir() {
        return dir;
    }
    /** get the closet point of a list to the start of the ray
     * @param list list of points on the ray
     * @return the closet point from the points of the list*/
    public Point findClosestPoint(List<Point> list){
        if(list==null){
            return null;
        }
        double closetDistance=Double.POSITIVE_INFINITY;
        Point closetPoint=null;
        double distacne;
        for (Point i:list){
            distacne=i.distanceSquared(this.p0);
            if(distacne<closetDistance){
                closetPoint=i;
                closetDistance=distacne;
            }
        }
        return closetPoint;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Ray other)
            return this.p0.equals(other.p0) && this.dir.equals(other.dir);
        return false;
    }

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }
}

