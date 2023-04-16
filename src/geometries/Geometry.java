package geometries;
import primitives.Point;
import primitives.Vector;

/**This interface will serve as the basis for all the geometric shapes*/
public interface Geometry extends Intersectable{
    /** get normal to specific point on the Geometry
     * @param point the point on the Geometry
     * @return the normal to the point
     * */
    public Vector getNormal(Point point);
}
