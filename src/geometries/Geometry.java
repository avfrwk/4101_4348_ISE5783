package geometries;
import primitives.Point;
import primitives.Vector;

/**This interface will serve as the basis for all the geometric shapes*/
public interface Geometry{
    public Vector getNormal(Point point);
}
