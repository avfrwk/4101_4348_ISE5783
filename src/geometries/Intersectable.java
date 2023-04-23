package geometries;

import primitives.Point;
import primitives.Ray;
import java.util.List;

/**This interface will */

public interface Intersectable {
     /** get list of intersection between ray and geometry
     * @param ray the ray
     * @return list of intersections
     * */
     List<Point> findIntersections(Ray ray);

}
