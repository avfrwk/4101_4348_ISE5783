/*package geometries;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;
public class Box extends Geometry{
    protected List<Polygon> polygons=null;
    public Box(Point p1,Point p2,Point p3,Point p4,Point p5,Point p6,Point p7,Point p8){
        Polygon r1 = new Polygon(p1,p2,p3,p4);
        Polygon r2 = new Polygon(p1,p2,p6,p5);
        Polygon r3 = new Polygon(p5,p6,p7,p8);
        Polygon r4 = new Polygon(p8,p7,p3,p4);
        Polygon r5 = new Polygon(p2,p3,p7,p6);
        Polygon r6 = new Polygon(p1,p4,p8,p5);
        polygons.add(0,r1);
        polygons.add(1,r2);
        polygons.add(2,r3);
        polygons.add(3,r4);
        polygons.add(4,r5);
        polygons.add(5,r6);
    }

    @Override
    public Vector getNormal(Point point) {
        for(int i=0;i<polygons.size();i++){

        }
    }

    @Override
    protected List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        return null;
    }
}
*/