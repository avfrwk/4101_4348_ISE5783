package geometries;

import primitives.Point;
import primitives.Vector;

abstract class RadialGeometry implements Geometry {
    protected final double radius;
    RadialGeometry(double Radius){
        this.radius=Radius;
    }
    public double getRadius() {
        return radius;
    }
}