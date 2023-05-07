package geometries;
import primitives.*;

import java.util.List;
import java.util.Objects;

/** This class represent a tube*/
public class Tube extends RadialGeometry{
    /** the central axis of the Tube*/
    protected final Ray ray;
    /** Constructor to initialize Tube based on radius and direction
     * @param Radius the radius length
     * @param ray the central axis of the Tube*/
    public Tube(double Radius,Ray ray){
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
    public List<GeoPoint>findGeoIntersectionsHelper(Ray ray) {
        Point rayp0=ray.getP0();
        Point tubep0=this.ray.getP0();
        if(rayp0.equals(tubep0)){
            rayp0=ray.getPoint(0.000000001);
        }
        Vector Dp = rayp0.subtract(tubep0);
        Vector v = ray.getDir();
        Vector va = this.ray.getDir();
        double temp = v.dotProduct(va);
        Vector vtemp = v;
        if (!Util.isZero(temp)) {
            vtemp = va.scale(temp);
            if (vtemp.equals(v)) {
                return null;
            }
            vtemp = v.subtract(vtemp);
        }

        double A = vtemp.dotProduct(vtemp);
        temp = Dp.dotProduct(va);
        Vector vtemp2 = Dp;
        if (!Util.isZero(temp)) {
            vtemp2 = va.scale(temp);
            vtemp2 = Dp.subtract(vtemp2);
        }
        double B = 2 * vtemp.dotProduct(vtemp2);
        temp = Dp.dotProduct(va);
        vtemp = Dp;
        if (!Util.isZero(temp)) {
            vtemp = va.scale(temp);
            vtemp = Dp.subtract(vtemp);
        }
        double C = vtemp.dotProduct(vtemp) - this.radius * this.radius;
        double root = B * B - 4 * A * C;
        if (root < 0)
            return null;
        root = Math.sqrt(root);
        double p0 = (-B + root) / 2 * A;
        double p1 = (-B - root) / 2 * A;
        if(Util.alignZero(p0)>0){
            if(Util.alignZero(p1)>0){
                return List.of(new GeoPoint(this,ray.getPoint(p0)),
                        new GeoPoint(this,ray.getPoint(p1)));
            }
            return List.of(new GeoPoint(this,ray.getPoint(p0)));
        }
        if(Util.alignZero(p1)>0) {
            return List.of(new GeoPoint(this,ray.getPoint(p1)));
        }
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