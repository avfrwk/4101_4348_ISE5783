package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;
import java.util.Objects;

/** This class represent a sphere*/
public class Sphere extends RadialGeometry{
    final private Point center;

    /** Constructor to initialize Tube based on radius and direction
     * @param Radius the radius length
     * @param Center the central point of the tube*/
    public Sphere(double Radius, Point Center){
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
    @Override
    public Vector getNormal(Point point){
        return point.subtract(this.center).normalize();
    }
    /** get list of intersection between ray and Sphere
     * @param ray the ray
     * @return list of intersections
     * */
    @Override
    public List<Point> findIntersections(Ray ray){
        Point  rayp0=ray.getP0();
        Vector raydir=ray.getDir();
        Vector u;
        double tm;
        double dsquare;
        double rsquare;
        if(this.center.equals(rayp0)){
            tm=0;
            dsquare=-tm*tm;
            rsquare=this.radius*this.radius;
        }else{
            u=this.center.subtract(rayp0);
            tm=raydir.dotProduct(u);
            dsquare=u.dotProduct(u)-tm*tm;
            rsquare=this.radius*this.radius;
        }
        if(dsquare>=rsquare){
            return null;
        }
        double th=Math.sqrt(rsquare-dsquare);
        if(Util.alignZero(tm+th)>0){
            if(Util.alignZero(tm-th)>0){
                return List.of(ray.getPoint(tm+th),ray.getPoint(tm-th));
            }
            return List.of(ray.getPoint(tm+th));
        }
        if(Util.alignZero(tm-th)>0){
            return List.of(ray.getPoint(tm-th));
        }
        return null;
        //return List.of((tm+th!=0?rayp0.add(raydir.scale(tm+th)):null),rayp0.add(raydir.scale(tm-th)));
    }    @Override
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