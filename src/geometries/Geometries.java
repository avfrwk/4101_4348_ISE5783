package geometries;

import primitives.Ray;

import java.util.List;
import java.util.LinkedList;

public class Geometries extends Intersectable {
    List<Intersectable> geometries;

    /**
     * Constructor to initialize empty Geometries object
     */
    public Geometries() {
        this.geometries = new LinkedList<>();
    }

    /**
     * Constructor to initialize Geometries object that contains the Intersectables that provided
     *
     * @param geometries array of Intersectable
     */
    public Geometries(Intersectable... geometries) {
        this.geometries = new LinkedList<>();
        for (Intersectable i : geometries) {
            this.geometries.add(i);
        }
    }

    /**
     * add Intersectables to the Geometries object
     *
     * @param geometries array of Intersectable to add
     */
    public void add(Intersectable... geometries) {
        for (Intersectable i : geometries) {
            this.geometries.add(i);
        }
    }
    /**get list of intersection between ray and the Intersectables inside the Geometries object
     * @param ray the ray
     * @param maxDistance the maximum allowed distance to return the geopoint
     * @return list of intersections
     */
    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance){
        List<GeoPoint> insects = null;
        List<GeoPoint> localInsects;
        for (Intersectable geometry : this.geometries) {
            localInsects = geometry.findGeoIntersectionsHelper(ray,maxDistance);
            if (localInsects != null) {
                if (insects != null)
                    insects.addAll(localInsects);//-------------------------------------
                else insects = new LinkedList<>(localInsects);
            }
        }
        return insects;
    }
}
