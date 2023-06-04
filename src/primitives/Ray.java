package primitives;
import geometries.Intersectable.GeoPoint;
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
        Double3 temp=this.dir.xyz.scale(t);
        if(temp.equals(Double3.ZERO)){
            return this.p0;
        }
        return this.p0.add(new Vector(temp));
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
     * @param points list of points on the ray
     * @return the closet point from the points of the list*/
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    /** get the closet point of GeoPoint in a list to the start of the ray
     * @param points list of GeoPoints on the ray
     * @return the GeoPoint from the GeoPoints list that his point is the closet to the start of the ray*/
    public GeoPoint findClosestGeoPoint(List<GeoPoint> points){
        if(points==null){
            return null;
        }
        double closetDistance=Double.POSITIVE_INFINITY;
        GeoPoint closetPoint=null;
        double distacne;
        for (GeoPoint i:points){
            distacne=i.point.distanceSquared(this.p0);
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

