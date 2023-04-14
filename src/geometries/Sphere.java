package geometries;
import primitives.Point;
import primitives.Vector;

import java.util.Objects;

/** This class represent a sphere*/
public class Sphere extends RadialGeometry{
    final private Point center;

    /** Constructor to initialize Tube based on radius and direction
     * @param Radius the radius length
     * @param Center the central point of the tube*/
    Sphere(double Radius, Point Center){
        super(Radius);
        this.center=Center;
    }
    /** get the center of the sphere
     * @return the central point of the sphere*/
    public Point getCenter() {
        return center;
    }
    /** get the normal to the sphere at specific point
    *  @param point the point of normal's head
     * @return normal to the sphere at specific point*/
    public Vector getNormal(Point point){
        return point.subtract(this.center).normalize();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Sphere sphere = (Sphere) o;
        return Objects.equals(center, sphere.center);
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), center);
    }
}