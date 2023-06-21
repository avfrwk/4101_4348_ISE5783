package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * This interface represents intersectable object
 */
public abstract class Intersectable {
    /**
     * get list of intersection between ray and Intersectable
     * @param ray the ray
     * @return list of intersections
     */
    public List<Point> findIntersections(Ray ray) {
        var geoList = this.findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }
    /**get list of intersection between ray and geometry
     * @param ray the ray
     * @return list of intersections with the geometry
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }
    /**get list of intersection between ray and geometry
     * @param ray the ray
     * @param maxDistance the maximum allowed distance to return the geopoint
     * @return list of intersections with the geometry
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }
    /**get list of intersection between ray and geometry
     * @param ray the ray
     * @param maxDistance the maximum allowed distance to return the geopoint
     * @return list of intersections with the geometry
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);


    /**
     * this class represent geometry and point
     */
    public static class GeoPoint {
        /** the geometry*/
        public Geometry geometry;
        /**the point*/
        public Point point;
        public GeoPoint(Geometry geometry, Point point) {
            this.point = point;
            this.geometry = geometry;
        }
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return geometry.equals(geoPoint.geometry) && point.equals(geoPoint.point);
        }
        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }

    /**return the bounds
     * @return list of Doubles
     */
    public List<Double> minMaxPoints(){
        return null;
    }

}
