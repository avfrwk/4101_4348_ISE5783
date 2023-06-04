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
        if(Util.isZero(t)) O=this.ray.getP0();
        else O=this.ray.getP0().add(this.ray.getDir().scale(t));
        Vector n=point.subtract(O);
        return n.normalize();
    }
    /** get list of intersection between ray and Tube
     * @param ray the ray
     * @param maxDistance the maximum allowed distance to return the geopoint
     * @return list of intersections
     * */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance){
        Vector D=ray.getDir();
        Vector V=this.ray.getDir();
        Vector X;
        if(!ray.getP0().equals(this.ray.getP0())){
            X=ray.getP0().subtract(this.ray.getP0());
        }else{
            X=ray.getP0().add(new Vector(0,0,0.0001)).subtract(this.ray.getP0());
        }//
        double dx=D.dotProduct(X);
        double dv=D.dotProduct(V);
        double xv=X.dotProduct(V);
        //
        double a=Util.alignZero(D.dotProduct(D)-dv*dv);
        double c=Util.alignZero(X.dotProduct(X)-xv*xv-this.radius*this.radius);
        double b=Util.alignZero(2*(dx-dv*xv));
        //
        double insideRoot=Util.alignZero(b*b-4*a*c);
        if(insideRoot>0){
            double root=Math.sqrt(insideRoot);
            double t1=Util.alignZero((-b+root)/(2*a));
            double t2=Util.alignZero((-b-root)/(2*a));
            if(t1>0){
                if(t2>0){
                    return List.of(new GeoPoint(this,ray.getPoint(t1)),
                            new GeoPoint(this,ray.getPoint(t2)) );
                }
                return List.of(new GeoPoint(this,ray.getPoint(t1)));
            }if(t2>0){
                return List.of(new GeoPoint(this,ray.getPoint(t2)));
            }
        }else if(insideRoot==0){
            double t1=Util.alignZero((-b)/(2*a));
            if(t1>0) {
                return List.of(new GeoPoint(this,ray.getPoint(t1)));
            }
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