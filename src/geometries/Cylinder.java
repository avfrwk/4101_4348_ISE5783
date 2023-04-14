package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Objects;

/** This class represent a cylinder*/

public class Cylinder extends Tube{
    final private double height;
    /** Constructor to initialize Cylinder based on radius, the center axis and height
     * @param Radius first number value
     * @param ray second number value
     * @param Height third number value */
    Cylinder(double Radius, Ray ray, double Height){
        super(Radius, ray);
        this.height=Height;
    }

    /** get the height of the cylinder
     * @return the height of the cylinder*/
    public double getHeight() {
        return height;
    }

    /** get the normal to the cylinder at specific point
     *  @param point the point of normal's head
     * @return normal to the cylinder at specific point*/
    public Vector getNormal(Point point){
        Vector dir=this.ray.getDir();

        if(this.ray.getP0().distanceSquared(point)<this.radius*this.radius){
            return dir.scale(-1);

        }if (this.ray.getP0().add(dir.scale(this.height)).distanceSquared(point)<this.radius*this.radius) {
            return dir;
        }
        /*if(point.equals(this.ray.getP0())||point.subtract(this.ray.getP0()).dotProduct(dir)==0){
            return dir.scale(-1);
        }
        Point heightP0=this.ray.getP0().add(dir.scale(this.height));
        if(point.equals(heightP0)||point.subtract(heightP0).dotProduct(dir)==0){
            return dir;
        }*/
        return super.getNormal(point);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Cylinder cylinder = (Cylinder) o;
        return Double.compare(cylinder.height, height) == 0;
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), height);
    }
}