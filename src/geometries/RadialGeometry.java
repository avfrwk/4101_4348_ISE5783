package geometries;

import primitives.Point;
import primitives.Vector;

import java.util.Objects;

/**This class will serve as the basis for all the radial geometries shapes*/
abstract class RadialGeometry extends Geometry {
    /**the radius of the RadialGeometry*/
    protected final double radius;

    /** Constructor to initialize RadialGeometry based on radius
     * @param Radius the radius*/
    RadialGeometry(double Radius){
        this.radius=Radius;
    }
    /** get the radius
     * @return the radius*/
    public double getRadius() {
        return radius;
    }
    @Override
    abstract public Vector getNormal(Point point);
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RadialGeometry that = (RadialGeometry) o;
        return Double.compare(that.radius, radius) == 0;
    }
    @Override
    public int hashCode() {
        return Objects.hash(radius);
    }
}