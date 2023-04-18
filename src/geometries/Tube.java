package geometries;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;

import java.util.List;
import java.util.Objects;

/** This class represent a tube*/
public class Tube extends RadialGeometry{
    /** the central axis of the Tube*/
    protected final Ray ray;
    /** Constructor to initialize Tube based on radius and direction
     * @param Radius the radius length
     * @param ray the central axis of the Tube*/
    Tube(double Radius,Ray ray){
        super(Radius);
        this.ray=ray;
    }
    /** get the central axis of the Tube
     * @return the central axis of the Tube*/
    public Ray getRay() {
        return ray;
    }
    /** get the normal to the tube at specific point
     *  @param point the point of normal's head
     * @return normal to the tube at specific point*/
    @Override
    public Vector getNormal(Point point){
        double t=this.ray.getDir().dotProduct(point.subtract(this.ray.getP0()));
        Point O;
        if(t==0) O=this.ray.getP0();
        else O=this.ray.getP0().add(this.ray.getDir().scale(t));
        Vector n=point.subtract(O);
        return n.normalize();
    }
    /** get list of intersection between ray and Tube
     * @param ray the ray
     * @return list of intersections
     * */
    @Override
    public List<Point> findIntsersections(Ray ray){


        return null;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Tube tube = (Tube) o;
        return Objects.equals(ray, tube.ray);
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), ray);
    }
}