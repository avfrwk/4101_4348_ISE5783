package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
class Cylinder extends Tube{
    final private double height;
    Cylinder(double Radius, Ray ray, double Height){
        super(Radius, ray);
        this.height=Height;
    }
    public double getHeight() {
        return height;
    }
    public Vector getNormal(Point point){
        return null;
    }
}