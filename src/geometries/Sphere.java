package geometries;
import primitives.Point;
import primitives.Vector;

class Sphere extends RadialGeometry{
    final private Point center;
    //private double radius;
    Sphere(double Radius, Point Center){
        super(Radius);
        this.center=Center;
    }
    public Point getCenter() {
        return center;
    }
    public Vector getNormal(Point point){
        return null;
    }
}