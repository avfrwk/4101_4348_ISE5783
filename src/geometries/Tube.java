package geometries;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;

class Tube extends RadialGeometry{
    protected final Ray ray;
    Tube(double Radius,Ray ray){
        super(Radius);
        this.ray=ray;
    }
    public Ray getRay() {
        return ray;
    }
    public Vector getNormal(Point point){
        return null;
    }
}